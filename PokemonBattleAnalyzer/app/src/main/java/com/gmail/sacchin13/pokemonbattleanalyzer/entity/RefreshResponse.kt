package com.gmail.sacchin13.pokemonbattleanalyzer.entity

import com.squareup.moshi.Json

data class RefreshResponse (
    @Json(name = "access_token") val token: String,
    @Json(name = "token_type") val bearer: String,
    @Json(name = "expires_in") val expires: Int
){
}