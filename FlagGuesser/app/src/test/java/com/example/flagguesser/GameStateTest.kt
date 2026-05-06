package com.example.flagguesser

import com.example.flagguesser.data.Country
import com.example.flagguesser.data.GameState
import com.example.flagguesser.data.RegionData
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GameStateTest {

    private lateinit var region: RegionData
    private lateinit var gameState: GameState

    @Before
    fun setup() {
        val countries = listOf(
            Country("France", "url1", "Europe"),
            Country("Germany", "url2", "Europe"),
            Country("Italy", "url3", "Europe"),
            Country("Spain", "url4", "Europe"),
            Country("Poland", "url5", "Europe")
        )
        region = RegionData("Europe", countries)
        gameState = GameState().startRegion(region)
    }

    @Test
    fun `startRegion sets index to 0`() {
        assertEquals(0, gameState.currentIndex)
    }

    @Test
    fun `startRegion sets correctAnswers to 0`() {
        assertEquals(0, gameState.correctAnswers)
    }

    @Test
    fun `startRegion generates 4 options`() {
        assertEquals(4, gameState.options.size)
    }

    @Test
    fun `startRegion options contain correct answer`() {
        val correct = region.countries[0].name
        assertTrue(gameState.options.contains(correct))
    }

    @Test
    fun `answerSelected correct answer increments correctAnswers`() {
        val correct = region.countries[0].name
        val newState = gameState.answerSelected(correct)
        assertEquals(1, newState.correctAnswers)
    }

    @Test
    fun `answerSelected wrong answer does not increment correctAnswers`() {
        val wrong = region.countries.first { it.name != region.countries[0].name }.name
        val newState = gameState.answerSelected(wrong)
        assertEquals(0, newState.correctAnswers)
    }

    @Test
    fun `answerSelected sets isAnswered to true`() {
        val newState = gameState.answerSelected(region.countries[0].name)
        assertTrue(newState.isAnswered)
    }

    @Test
    fun `answerSelected twice does not change state`() {
        val after1 = gameState.answerSelected(region.countries[0].name)
        val after2 = after1.answerSelected(region.countries[1].name)
        assertEquals(after1.correctAnswers, after2.correctAnswers)
        assertEquals(after1.selectedAnswer, after2.selectedAnswer)
    }

    @Test
    fun `nextFlag advances index`() {
        val answered = gameState.answerSelected(region.countries[0].name)
        val next = answered.nextFlag()
        assertEquals(1, next.currentIndex)
    }

    @Test
    fun `nextFlag on last flag returns null region`() {
        var state = gameState
        repeat(region.totalFlags - 1) { i ->
            state = state.answerSelected(region.countries[i].name).nextFlag()
        }
        // На последнем флаге отвечаем и жмём next
        state = state.answerSelected(region.countries[region.totalFlags - 1].name)
        val finished = state.nextFlag()
        assertNull(finished.currentRegion)
    }

    @Test
    fun `progress returns 0 initially`() {
        assertEquals(0f, gameState.progress(), 0.001f)
    }

    @Test
    fun `progress returns correct ratio after answers`() {
        var state = gameState
        repeat(2) { i ->
            state = state.answerSelected(region.countries[i].name).nextFlag()
        }
        val expected = 2f / region.totalFlags
        assertEquals(expected, state.progress(), 0.001f)
    }

    @Test
    fun `isGameFinished false at start`() {
        assertFalse(gameState.isGameFinished())
    }

    @Test
    fun `isGameFinished true when on last flag and answered`() {
        var state = gameState
        repeat(region.totalFlags - 1) { i ->
            state = state.answerSelected(region.countries[i].name).nextFlag()
        }
        state = state.answerSelected(region.countries[region.totalFlags - 1].name)
        assertTrue(state.isGameFinished())
    }

    @Test
    fun `generateOptionsForIndex contains correct answer`() {
        val options = gameState.generateOptionsForIndex(2)
        assertTrue(options.contains(region.countries[2].name))
    }

    @Test
    fun `generateOptionsForIndex returns 4 options`() {
        val options = gameState.generateOptionsForIndex(0)
        assertEquals(4, options.size)
    }
}