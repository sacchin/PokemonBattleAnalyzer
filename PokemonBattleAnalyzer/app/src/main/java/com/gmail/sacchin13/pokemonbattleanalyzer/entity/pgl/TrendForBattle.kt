package com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Skill
import java.util.*
import kotlin.properties.Delegates

data class TrendForBattle(
        val seikakuInfo: List<Info>,
        val tokuseiInfo: List<Info>,
        val itemInfo: List<Info>) {

    var wazaInfo: MutableList<RankingPokemonSkill> by Delegates.notNull()

    companion object {
        fun create(rankingPokemonTrend: RankingPokemonTrend, skills: ArrayList<Skill>): TrendForBattle {
            val result = TrendForBattle(rankingPokemonTrend.seikakuInfo,
                    rankingPokemonTrend.tokuseiInfo,
                    rankingPokemonTrend.itemInfo)

            result.wazaInfo = mutableListOf()
            for (temp in 0..(rankingPokemonTrend.wazaInfo.size - 2)) {
                result.wazaInfo.add(RankingPokemonSkill.create(rankingPokemonTrend.wazaInfo[temp], skills[temp]))
            }

            return result
        }
    }
}
