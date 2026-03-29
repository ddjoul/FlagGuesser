package com.example.flagguesser.data

data class Country(
    val name: String,
    val flagUrl: String,
    val region: String
)

data class RestCountryResponse(
    val name: Name,
    val flags: Flags,
    val region: String
) {
    data class Name(val common: String)
    data class Flags(val png: String)
}