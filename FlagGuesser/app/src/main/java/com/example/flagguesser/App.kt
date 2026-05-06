package com.example.flagguesser

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.flagguesser.ui.screens.*
import com.example.flagguesser.ui.viewmodel.GameUiState
import com.example.flagguesser.data.GameState
import com.example.flagguesser.ui.viewmodel.GameViewModel
import com.example.flagguesser.ui.viewmodel.LanguageViewModel
@Composable
fun App(langViewModel: LanguageViewModel = viewModel()) {
    val navController = rememberNavController()
    val gameViewModel: GameViewModel = viewModel()

    NavHost(navController = navController, startDestination = "menu") {
        composable("menu") {
            MenuScreen(
                onStartGame = { navController.navigate("region") },
                onStatsClick = { navController.navigate("stats") },
                onLanguageClick = { navController.navigate("language") }
            )
        }

        composable("region") {
            val uiState by gameViewModel.uiState.collectAsState()
            val currentGameState = (uiState as? GameUiState.GameStarted)?.gameState ?: GameState()

            LaunchedEffect(Unit) {
                if (uiState !is GameUiState.RegionsLoaded && uiState !is GameUiState.Loading) {
                    gameViewModel.backToRegionSelection()
                }
            }

            RegionScreen(
                gameState = currentGameState,
                onBack = { navController.popBackStack() },
                onRegionSelected = { region ->
                    gameViewModel.selectRegion(region)
                    navController.navigate("game")
                },
                viewModel = gameViewModel
            )
        }

        composable("game") {
            val uiState by gameViewModel.uiState.collectAsState()
            when (val state = uiState) {
                is GameUiState.GameStarted -> {
                    GameScreen(
                        gameState = state.gameState,
                        onPrev = { gameViewModel.onPrevFlag() },
                        onNext = { gameViewModel.onNextFlag() },
                        onExit = {
                            gameViewModel.exitGameToRegions()
                            navController.popBackStack("region", false)
                        },
                        onAnswer = { answer ->
                            gameViewModel.onAnswerSelected(answer)
                        }
                    )
                }
                is GameUiState.GameFinished -> {
                    LaunchedEffect(Unit) {
                        gameViewModel.exitGameToRegions()
                        navController.popBackStack("region", false)
                    }
                }
                else -> {
                    androidx.compose.material3.Text("Загрузка...")
                }
            }
        }

        composable("stats") {
            StatsScreen(onBack = { navController.popBackStack() })
        }

        composable("language") {
            LanguageScreen(
                langViewModel = langViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}