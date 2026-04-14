package com.example.flagguesser

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import com.example.flagguesser.ui.screens.MenuScreen
import com.example.flagguesser.ui.theme.AppTheme
import org.junit.Rule
import org.junit.Test

class MenuScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun menuScreen_displaysAllButtons() {
        composeTestRule.setContent {
            AppTheme {
                MenuScreen(onStartGame = {}, onStatsClick = {}, onLanguageClick = {})
            }
        }

        composeTestRule.onNodeWithText(context.getString(R.string.btn_game)).assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(R.string.btn_stats)).assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(R.string.btn_language)).assertIsDisplayed()
    }

    @Test
    fun menuScreen_gameButton_triggersCallback() {
        var clicked = false
        composeTestRule.setContent {
            AppTheme {
                MenuScreen(onStartGame = { clicked = true }, onStatsClick = {}, onLanguageClick = {})
            }
        }

        composeTestRule.onNodeWithText(context.getString(R.string.btn_game)).performClick()
        assert(clicked)
    }

    @Test
    fun menuScreen_statsButton_triggersCallback() {
        var clicked = false
        composeTestRule.setContent {
            AppTheme {
                MenuScreen(onStartGame = {}, onStatsClick = { clicked = true }, onLanguageClick = {})
            }
        }

        composeTestRule.onNodeWithText(context.getString(R.string.btn_stats)).performClick()
        assert(clicked)
    }

    @Test
    fun menuScreen_languageButton_triggersCallback() {
        var clicked = false
        composeTestRule.setContent {
            AppTheme {
                MenuScreen(onStartGame = {}, onStatsClick = {}, onLanguageClick = { clicked = true })
            }
        }

        composeTestRule.onNodeWithText(context.getString(R.string.btn_language)).performClick()
        assert(clicked)
    }
}