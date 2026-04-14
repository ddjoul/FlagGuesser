package com.example.flagguesser

import com.example.flagguesser.data.Country
import com.example.flagguesser.data.CountriesRepository
import io.mockk.coEvery
import io.mockk.mockk
import com.example.flagguesser.network.ApiService
import com.example.flagguesser.data.RestCountryResponse
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class CountriesRepositoryTest {

    private val mockApi = mockk<ApiService>()
    private val repository = CountriesRepository(mockApi)

    private val mockResponse = listOf(
        RestCountryResponse(
            name = RestCountryResponse.Name("France"),
            flags = RestCountryResponse.Flags("https://flag.url/fr.png"),
            region = "Europe"
        ),
        RestCountryResponse(
            name = RestCountryResponse.Name("Brazil"),
            flags = RestCountryResponse.Flags("https://flag.url/br.png"),
            region = "Americas"
        )
    )

    @Test
    fun `fetchCountries maps response to Country list`() = runTest {
        coEvery { mockApi.getAllCountries() } returns mockResponse
        val result = repository.fetchCountries()
        assertEquals(2, result.size)
        assertEquals("France", result[0].name)
        assertEquals("Europe", result[0].region)
    }

    @Test
    fun `getCountriesByRegion filters correctly`() {
        val countries = listOf(
            Country("France", "url1", "Europe"),
            Country("Brazil", "url2", "Americas")
        )
        val result = repository.getCountriesByRegion(countries, "Europe")
        assertEquals(1, result.size)
        assertEquals("France", result[0].name)
    }

    @Test
    fun `getCountriesByRegion is case insensitive`() {
        val countries = listOf(Country("France", "url1", "Europe"))
        val result = repository.getCountriesByRegion(countries, "europe")
        assertEquals(1, result.size)
    }

    @Test
    fun `getAllRegions returns distinct sorted regions`() {
        val countries = listOf(
            Country("France", "u1", "Europe"),
            Country("Germany", "u2", "Europe"),
            Country("Brazil", "u3", "Americas")
        )
        val regions = repository.getAllRegions(countries)
        assertEquals(listOf("Americas", "Europe"), regions)
    }
}