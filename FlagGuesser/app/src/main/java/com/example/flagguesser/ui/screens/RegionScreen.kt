package com.example.flagguesser.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flagguesser.data.GameState
import com.example.flagguesser.ui.components.BaseScreen
import com.example.flagguesser.ui.components.MainButton
import com.example.flagguesser.ui.components.TopPanel
import com.example.flagguesser.ui.theme.ProgressColor
import com.example.flagguesser.ui.viewmodel.GameUiState
import com.example.flagguesser.ui.viewmodel.GameViewModel

@Composable
fun RegionScreen(
    gameState: GameState,
    onBack: () -> Unit,
    onRegionSelected: (String) -> Unit,
    viewModel: GameViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        if (uiState !is GameUiState.RegionsLoaded && uiState !is GameUiState.Loading) {
        }
    }

    BaseScreen {
        Column(modifier = Modifier.fillMaxSize()) {
            TopPanel("Регион", showBack = true, onBack = onBack)
            Spacer(Modifier.height(52.dp))

            when (uiState) {
                is GameUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is GameUiState.RegionsLoaded -> {
                    val regions = (uiState as GameUiState.RegionsLoaded).regions
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        regions.forEach { region ->
                            MainButton(region, Modifier.fillMaxWidth()) {
                                onRegionSelected(region)
                            }
                            Spacer(Modifier.height(35.dp))
                        }
                    }
                }
                is GameUiState.Error -> {
                    Text(
                        text = (uiState as GameUiState.Error).message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(24.dp)
                    )
                }
                else -> {
                    Text("Выберите регион", modifier = Modifier.padding(24.dp))
                }
            }

            Spacer(Modifier.weight(1f))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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
                Text(
                    text = "${gameState.correctAnswers}/${gameState.currentRegion?.totalFlags ?: 0}",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}