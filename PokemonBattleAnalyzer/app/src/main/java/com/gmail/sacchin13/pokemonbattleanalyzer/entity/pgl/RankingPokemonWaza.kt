package com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl

/**
 * for rankingPokemonDownWaza, rankingPokemonDownWazaOther, rankingPokemonSuffererWaza
 */
data class RankingPokemonWaza(
        val ranking: Int,
        val sequenceNumber: Int,
        val wazaName: String,
        val typeId: Int,
        val usageRate: Float
){
}

