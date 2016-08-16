package com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Skill

data class RankingPokemonSkill(
        val ranking: Int,
        val sequenceNumber: Int,
        val skill: Skill,
        val typeId: Int,
        val usageRate: Float
) {
    companion object {
        fun create(wazaInfo: WazaInfo, skill: Skill): RankingPokemonSkill {
            return RankingPokemonSkill(wazaInfo.ranking, wazaInfo.sequenceNumber, skill, wazaInfo.typeId, wazaInfo.usageRate)
        }
    }
}

