package com.example.flagguesser.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flagguesser.data.*
import com.example.flagguesser.network.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val statsRepository = StatsRepository(application)

    private val api = Retrofit.Builder()
        .baseUrl("https://restcountries.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    private val repository = CountriesRepository(api)

    private val _uiState = MutableStateFlow<GameUiState>(GameUiState.Loading)
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private var allCountries: List<Country> = emptyList()
    private var cachedRegions: List<String> = emptyList()
    private var gameState = GameState()

    init {
        loadCountries()
    }

    private fun loadCountries() {
        viewModelScope.launch {
            _uiState.value = GameUiState.Loading
            try {
                allCountries = repository.fetchCountries()
                cachedRegions = repository.getAllRegions(allCountries)
                _uiState.value = GameUiState.RegionsLoaded(cachedRegions)
            } catch (e: Exception) {
                _uiState.value = GameUiState.Error("Ошибка загрузки: ${e.message}")
            }
        }
    }

    fun selectRegion(regionName: String) {
        val countries = repository.getCountriesByRegion(allCountries, regionName)
        if (countries.isNotEmpty()) {
            val regionData = RegionData(regionName, countries)
            gameState = gameState.startRegion(regionData)
            _uiState.value = GameUiState.GameStarted(gameState)
        } else {
            _uiState.value = GameUiState.Error("Нет стран в регионе $regionName")
        }
    }

    fun onAnswerSelected(answer: String) {
        if (gameState.isAnswered) return
        val currentCountry = gameState.currentRegion?.countries?.getOrNull(gameState.currentIndex)
        val isCorrect = answer.equals(currentCountry?.name, ignoreCase = true)
        viewModelScope.launch {
            statsRepository.updateStats(if (isCorrect) 1 else 0, 1)
        }
        gameState = gameState.answerSelected(answer)
        _uiState.value = GameUiState.GameStarted(gameState)
    }

    fun onNextFlag() {
        val newState = gameState.nextFlag()
        if (newState.currentRegion == null) {
            _uiState.value = GameUiState.GameFinished(gameState.correctAnswers, gameState.currentRegion?.totalFlags ?: 0)
        } else {
            gameState = newState
            _uiState.value = GameUiState.GameStarted(gameState)
        }
    }

    fun onPrevFlag() {
        if (gameState.currentIndex > 0 && gameState.currentRegion != null) {
            val prevIndex = gameState.currentIndex - 1
            gameState = gameState.copy(
                currentIndex = prevIndex,
                isAnswered = false,
                options = gameState.generateOptionsForIndex(prevIndex)
            )
            _uiState.value = GameUiState.GameStarted(gameState)
        }
    }

    fun backToRegionSelection() {
        gameState = GameState()
        if (cachedRegions.isNotEmpty()) {
            _uiState.value = GameUiState.RegionsLoaded(cachedRegions)
        } else {
            loadCountries()
        }
    }

    fun resetToRegionSelection() {
        backToRegionSelection()
    }

    fun exitGameToRegions() {
        backToRegionSelection()
    }
}

sealed class GameUiState {
    object Loading : GameUiState()
    data class RegionsLoaded(val regions: List<String>) : GameUiState()
    data class GameStarted(val gameState: GameState) : GameUiState()
    data class GameFinished(val correct: Int, val total: Int) : GameUiState()
    data class Error(val message: String) : GameUiState()
}