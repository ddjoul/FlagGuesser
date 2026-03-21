package com.example.flagguesser.data

data class Region(
    val name: String,
    val totalFlags: Int
)

data class GameState(
    val currentRegion: Region? = null,
    val currentIndex: Int = 0,
    val correctAnswers: Int = 0
) {

    fun startRegion(region: Region): GameState {
        return GameState(
            currentRegion = region,
            currentIndex = 0,
            correctAnswers = 0
        )
    }

    fun nextFlag(): GameState {
        val region = currentRegion ?: return this

        val nextIndex = if (currentIndex < region.totalFlags - 1) {
            currentIndex + 1
        } else {
            currentIndex // Не переходим за последний флаг
        }

        return copy(currentIndex = nextIndex)
    }

    fun progress(): Float {
        val region = currentRegion ?: return 0f
        return if (region.totalFlags == 0) 0f
        else correctAnswers.toFloat() / region.totalFlags
    }
}