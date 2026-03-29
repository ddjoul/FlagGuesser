package com.example.flagguesser.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.flagguesser.ui.components.BaseScreen
import com.example.flagguesser.ui.components.MainButton
import com.example.flagguesser.ui.components.TopPanel

@Composable
fun MenuScreen(
    onStartGame: () -> Unit,
    onStatsClick: () -> Unit,
    onLanguageClick: () -> Unit = {}
) {
    BaseScreen {
        Column(modifier = Modifier.fillMaxSize()) {
            TopPanel("FLAG\nGUESSER")
            Spacer(Modifier.weight(1f))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MainButton("Игра", Modifier.fillMaxWidth(), onStartGame)
                Spacer(Modifier.height(35.dp))
                MainButton("Статистика", Modifier.fillMaxWidth(), onStatsClick)
                Spacer(Modifier.height(35.dp))
                MainButton("Язык", Modifier.fillMaxWidth(), onLanguageClick)
            }
            Spacer(Modifier.weight(1f))
        }
    }
}