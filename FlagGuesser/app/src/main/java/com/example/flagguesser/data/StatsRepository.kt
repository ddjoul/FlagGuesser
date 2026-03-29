package com.example.flagguesser.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "stats")

class StatsRepository(private val context: Context) {

    companion object {
        private val TOTAL_CORRECT = intPreferencesKey("total_correct")
        private val TOTAL_ATTEMPTS = intPreferencesKey("total_attempts")
    }

    val totalStats: Flow<Pair<Int, Int>> = context.dataStore.data.map { prefs ->
        (prefs[TOTAL_CORRECT] ?: 0) to (prefs[TOTAL_ATTEMPTS] ?: 0)
    }

    suspend fun updateStats(correct: Int, total: Int) {
        context.dataStore.edit { prefs ->
            val prevCorrect = prefs[TOTAL_CORRECT] ?: 0
            val prevTotal = prefs[TOTAL_ATTEMPTS] ?: 0
            prefs[TOTAL_CORRECT] = prevCorrect + correct
            prefs[TOTAL_ATTEMPTS] = prevTotal + total
        }
    }

    suspend fun resetStats() {
        context.dataStore.edit { prefs ->
            prefs[TOTAL_CORRECT] = 0
            prefs[TOTAL_ATTEMPTS] = 0
        }
    }
}