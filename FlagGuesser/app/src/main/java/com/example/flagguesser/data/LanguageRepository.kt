package com.example.flagguesser.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.langDataStore by preferencesDataStore(name = "language")

class LanguageRepository(private val context: Context) {

    companion object {
        private val LANGUAGE_KEY = stringPreferencesKey("language_code")
        const val LANG_EN = "en"
        const val LANG_RU = "ru"
    }

    val currentLanguage: Flow<String> = context.langDataStore.data.map { prefs ->
        prefs[LANGUAGE_KEY] ?: LANG_EN
    }

    suspend fun setLanguage(code: String) {
        context.langDataStore.edit { prefs ->
            prefs[LANGUAGE_KEY] = code
        }
    }
}