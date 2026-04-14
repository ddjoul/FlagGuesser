package com.example.flagguesser.ui.screens

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.example.flagguesser.R
import com.example.flagguesser.data.LanguageRepository
import com.example.flagguesser.ui.components.BaseScreen
import com.example.flagguesser.ui.components.MainButton
import com.example.flagguesser.ui.components.TopPanel
import com.example.flagguesser.ui.viewmodel.LanguageViewModel
import android.content.Intent
import androidx.compose.ui.platform.LocalContext
import com.example.flagguesser.MainActivity
@Composable
fun LanguageScreen(
    langViewModel: LanguageViewModel,
    onBack: () -> Unit
) {
    val currentLang by langViewModel.currentLanguage.collectAsState()
    val context = LocalContext.current

    fun changeLanguage(code: String) {
        langViewModel.setLanguage(code)
        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.forLanguageTags(code)
        )
        // Перезапуск с чистым стеком навигации
        val intent = Intent(context, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        context.startActivity(intent)
    }

    BaseScreen {
        Column(modifier = Modifier.fillMaxSize()) {
            TopPanel(stringResource(R.string.lang_screen_title), showBack = true, onBack = onBack)
            Spacer(Modifier.height(52.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MainButton(
                    text = if (currentLang == LanguageRepository.LANG_RU)
                        "✓ ${stringResource(R.string.lang_russian)}"
                    else
                        stringResource(R.string.lang_russian),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    changeLanguage(LanguageRepository.LANG_RU)
                }

                Spacer(Modifier.height(35.dp))

                MainButton(
                    text = if (currentLang == LanguageRepository.LANG_EN)
                        "✓ ${stringResource(R.string.lang_english)}"
                    else
                        stringResource(R.string.lang_english),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    changeLanguage(LanguageRepository.LANG_EN)
                }
            }
        }
    }
}