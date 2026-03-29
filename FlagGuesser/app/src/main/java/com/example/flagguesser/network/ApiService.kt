package com.example.flagguesser.network

import com.example.flagguesser.data.RestCountryResponse
import retrofit2.http.GET

interface ApiService {
    @GET("v3.1/all?fields=name,flags,region")
    suspend fun getAllCountries(): List<RestCountryResponse>
}