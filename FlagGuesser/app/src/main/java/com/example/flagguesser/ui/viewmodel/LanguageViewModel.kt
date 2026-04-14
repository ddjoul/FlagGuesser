package com.example.flagguesser.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.flagguesser.data.LanguageRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LanguageViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = LanguageRepository(application)

    val currentLanguage = repository.currentLanguage.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = LanguageRepository.LANG_EN
    )

    fun setLanguage(code: String) {
        viewModelScope.launch {
            repository.setLanguage(code)
        }
    }
}