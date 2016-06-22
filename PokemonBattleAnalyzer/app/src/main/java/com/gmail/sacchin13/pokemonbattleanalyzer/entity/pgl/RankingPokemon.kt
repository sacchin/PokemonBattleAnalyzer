package com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl

/**
 * RankingPokemonDown, RankingPokemonSufferer, RankingPokemonIn
 */
data class RankingPokemon(
        val monsno: Int,
        val formNo: String,
        val pokemonId: String,
        val ranking: Int,
        val countBattleByForm: Int,
        val battlingChangeFlg: Int,
        val typeId1: Int,
        val typeId2: Int,
        val formName: String,
        val typeName1: String,
        val typeName2: String,
        val name: String,
        val sequenceNumber: Int
){}

