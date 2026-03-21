package com.example.flagguesser.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.flagguesser.ui.components.*
import androidx.compose.ui.unit.dp

@Composable
fun MenuScreen(onStartGame: () -> Unit) {

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

                // TODO: добавить функционал статистики
                MainButton("Статистика", Modifier.fillMaxWidth())

                Spacer(Modifier.height(35.dp))

                // TODO: добавить смену языка
                MainButton("Язык", Modifier.fillMaxWidth())
            }

            Spacer(Modifier.weight(1f))
        }
    }
}