package com.example.flagguesser.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.flagguesser.data.*
import com.example.flagguesser.ui.components.*
import com.example.flagguesser.ui.theme.*

@Composable
fun RegionScreen(
    gameState: GameState,
    onBack: () -> Unit,
    onRegionSelected: (Region) -> Unit
) {

    // TODO: вынести в отдельный файл или ViewModel
    val regions = listOf(
        Region("Европа", 50),
        Region("Азия", 50),
        Region("Африка", 50),
        Region("Северная Америка", 50),
        Region("Южная Америка", 50),
        Region("Австралия и Океания", 50)
    )

    BaseScreen {

        Column(modifier = Modifier.fillMaxSize()) {

            TopPanel(
                title = "Регион",
                showBack = true,
                onBack = onBack
            )

            Spacer(Modifier.height(52.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                regions.forEach {
                    MainButton(it.name, Modifier.fillMaxWidth()) {
                        onRegionSelected(it)
                    }
                    Spacer(Modifier.height(35.dp))
                }
            }

            Spacer(Modifier.weight(1f))

            // Блок с прогрессом игры
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Прогресс-бар
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(5.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.Gray)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(gameState.progress())
                            .background(ProgressColor)
                    )
                }

                Spacer(Modifier.height(4.dp))

                // Счетчик угаданных флагов
                Text(
                    text = "${gameState.correctAnswers}/${gameState.currentRegion?.totalFlags ?: 0}",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}