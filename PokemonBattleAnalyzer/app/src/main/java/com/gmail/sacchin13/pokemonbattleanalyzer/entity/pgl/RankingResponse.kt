package com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl

import com.squareup.moshi.Json

data class RankingResponse (
        @Json(name = "status_code") val statusCode: String,
        val beforePokemonId: String,
        val nextPokemonId: String,
        val timezoneName: String,
        val rankingPokemonSuffererWaza: List<RankingPokemonWaza>,
        val rankingPokemonDownWaza: List<RankingPokemonWaza>,
        val rankingPokemonDownWazaOther: RankingPokemonWaza,

        val rankingPokemonDown: List<RankingPokemon>,
        val rankingPokemonSufferer: List<RankingPokemon>,
        val rankingPokemonIn: List<RankingPokemon>,

        val rankingPokemonInfo: RankingPokemonMaster,
        val rankingPokemonTrend: RankingPokemonTrend
        ){
}