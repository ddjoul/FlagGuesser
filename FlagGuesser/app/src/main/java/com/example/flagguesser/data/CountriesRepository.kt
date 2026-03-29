package com.example.flagguesser.data

import com.example.flagguesser.network.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CountriesRepository(private val api: ApiService) {

    suspend fun fetchCountries(): List<Country> {
        val response = api.getAllCountries()
        return response.map {
            Country(
                name = it.name.common,
                flagUrl = it.flags.png,
                region = it.region
            )
        }
    }

    fun getCountriesByRegion(countries: List<Country>, region: String): List<Country> {
        return countries.filter { it.region.equals(region, ignoreCase = true) }
    }

    fun getAllRegions(countries: List<Country>): List<String> {
        return countries.map { it.region }.distinct().sorted()
    }
}