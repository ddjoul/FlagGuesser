package com.example.flagguesser

import androidx.compose.runtime.*
import androidx.navigation.compose.*
import com.example.flagguesser.data.*
import com.example.flagguesser.ui.screens.*

@Composable
fun App() {
    val navController = rememberNavController()

    var gameState by remember { mutableStateOf(GameState()) }

    NavHost(navController = navController, startDestination = "menu") {

        composable("menu") {
            MenuScreen(
                onStartGame = { navController.navigate("region") }
            )
        }

        composable("region") {
            RegionScreen(
                gameState = gameState,
                onBack = { navController.popBackStack() },
                onRegionSelected = { region ->
                    gameState = gameState.startRegion(region)
                    navController.navigate("game")
                }
            )
        }

        composable("game") {
            GameScreen(
                gameState = gameState,
                onPrev = {
                    // если текущий индекс > 0, то -1, иначе последний флаг
                    val prevIndex = if (gameState.currentIndex > 0) gameState.currentIndex - 1 else (gameState.currentRegion?.totalFlags ?: 1) - 1
                    gameState = gameState.copy(currentIndex = prevIndex)
                },
                onNext = {
                    val nextIndex = if (gameState.currentIndex < (gameState.currentRegion?.totalFlags
                            ?: 1) - 1) gameState.currentIndex + 1 else 0
                    gameState = gameState.copy(currentIndex = nextIndex)
                },
                onExit = { navController.popBackStack("region", false) }
            )
        }
    }
}