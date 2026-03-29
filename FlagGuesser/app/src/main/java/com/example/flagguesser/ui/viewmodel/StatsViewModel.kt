package com.example.flagguesser.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.flagguesser.data.StatsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class StatsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = StatsRepository(application)

    val totalStats = repository.totalStats.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0 to 0
    )

    fun resetStats() {
        viewModelScope.launch {
            repository.resetStats()
        }
    }

    suspend fun updateStats(correct: Int, total: Int) {
        repository.updateStats(correct, total)
    }
}