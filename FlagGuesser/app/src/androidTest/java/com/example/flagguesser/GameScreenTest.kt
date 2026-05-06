package com.example.flagguesser

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.flagguesser.data.Country
import com.example.flagguesser.data.GameState
import com.example.flagguesser.data.RegionData
import com.example.flagguesser.ui.screens.GameScreen
import com.example.flagguesser.ui.theme.AppTheme
import org.junit.Rule
import org.junit.Test
import androidx.test.platform.app.InstrumentationRegistry

class GameScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun makeGameState(): GameState {
        val countries = listOf(
            Country("France", "", "Europe"),
            Country("Germany", "", "Europe"),
            Country("Italy", "", "Europe"),
            Country("Spain", "", "Europe")
        )
        val region = RegionData("Europe", countries)
        return GameState().startRegion(region)
    }

    @Test
    fun gameScreen_showsFourAnswerOptions() {
        val state = makeGameState()
        composeTestRule.setContent {
            AppTheme {
                GameScreen(
                    gameState = state,
                    onPrev = {},
                    onNext = {},
                    onExit = {},
                    onAnswer = {}
                )
            }
        }

        state.options.forEach { option ->
            composeTestRule.onNodeWithText(option).assertIsDisplayed()
        }
    }

    @Test
    fun gameScreen_clickingAnswer_triggersCallback() {
        val state = makeGameState()
        var answeredWith = ""
        composeTestRule.setContent {
            AppTheme {
                GameScreen(
                    gameState = state,
                    onPrev = {},
                    onNext = {},
                    onExit = {},
                    onAnswer = { answeredWith = it }
                )
            }
        }

        composeTestRule.onNodeWithText(state.options[0]).performClick()
        assert(answeredWith == state.options[0])
    }

    @Test
    fun gameScreen_afterAnswer_buttonsNotClickable() {
        val state = makeGameState().answerSelected(makeGameState().options[0])
        var clickCount = 0
        composeTestRule.setContent {
            AppTheme {
                GameScreen(
                    gameState = state,
                    onPrev = {},
                    onNext = {},
                    onExit = {},
                    onAnswer = { clickCount++ }
                )
            }
        }

        state.options.forEach { option ->
            composeTestRule.onNodeWithText(option).performClick()
        }
        assert(clickCount == 0)
    }

    @Test
    fun gameScreen_backButton_triggersExit() {
        var exited = false
        composeTestRule.setContent {
            AppTheme {
                GameScreen(
                    gameState = makeGameState(),
                    onPrev = {},
                    onNext = {},
                    onExit = { exited = true },
                    onAnswer = {}
                )
            }
        }

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        composeTestRule.onNodeWithText(context.getString(R.string.btn_back_to_regions)).performClick()
        assert(exited)
    }
}