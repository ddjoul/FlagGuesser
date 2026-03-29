package com.example.flagguesser.data

data class RegionData(
    val name: String,
    val countries: List<Country>
) {
    val totalFlags: Int get() = countries.size
}

data class GameState(
    val currentRegion: RegionData? = null,
    val currentIndex: Int = 0,
    val correctAnswers: Int = 0,
    val options: List<String> = emptyList(),
    val isAnswered: Boolean = false,
    val selectedAnswer: String = ""
) {

    fun startRegion(region: RegionData): GameState {
        val firstCountry = region.countries.firstOrNull()
        return copy(
            currentRegion = region,
            currentIndex = 0,
            correctAnswers = 0,
            isAnswered = false,
            options = if (firstCountry != null) generateOptions(region, 0) else emptyList()
        )
    }

    fun answerSelected(answer: String): GameState {
        if (isAnswered) return this
        val currentCountry = currentRegion?.countries?.getOrNull(currentIndex) ?: return this
        val isCorrect = answer.equals(currentCountry.name, ignoreCase = true)
        return copy(
            correctAnswers = if (isCorrect) correctAnswers + 1 else correctAnswers,
            isAnswered = true,
            selectedAnswer = answer
        )
    }

    fun nextFlag(): GameState {
        val region = currentRegion ?: return this
        val nextIndex = if (currentIndex + 1 < region.totalFlags) currentIndex + 1 else currentIndex
        val isLast = currentIndex + 1 >= region.totalFlags
        return if (isLast) {
            copy(currentRegion = null, currentIndex = 0, correctAnswers = 0, isAnswered = false)
        } else {
            copy(
                currentIndex = nextIndex,
                isAnswered = false,
                options = generateOptions(region, nextIndex)
            )
        }
    }

    fun generateOptionsForIndex(index: Int): List<String> {
        val region = currentRegion ?: return emptyList()
        val correct = region.countries[index].name
        val otherCountries = region.countries.filter { it.name != correct }.toMutableList()
        otherCountries.shuffle()
        val wrongAnswers = otherCountries.take(3).map { it.name }
        return (wrongAnswers + correct).shuffled()
    }

    fun progress(): Float {
        val region = currentRegion ?: return 0f
        return if (region.totalFlags == 0) 0f else correctAnswers.toFloat() / region.totalFlags
    }

    fun isGameFinished(): Boolean = currentRegion != null && currentIndex >= (currentRegion?.totalFlags ?: 0) - 1 && isAnswered

    private fun generateOptions(region: RegionData, index: Int): List<String> {
        val correct = region.countries[index].name
        val otherCountries = region.countries.filter { it.name != correct }.toMutableList()
        otherCountries.shuffle()
        val wrongAnswers = otherCountries.take(3).map { it.name }
        val allOptions = (wrongAnswers + correct).shuffled()
        return allOptions
    }
}