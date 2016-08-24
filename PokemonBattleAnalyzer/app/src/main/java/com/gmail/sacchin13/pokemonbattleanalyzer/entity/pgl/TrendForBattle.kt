package com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl

import android.util.Log
import com.gmail.sacchin13.pokemonbattleanalyzer.DatabaseHelper
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Skill
import java.util.*
import kotlin.properties.Delegates

data class TrendForBattle(
        val seikakuInfo: List<Info>,
        val tokuseiInfo: List<Info>,
        val itemInfo: List<Info>,
        val wazaInfo: List<WazaInfo>){

    var skillList: MutableList<RankingPokemonSkill> by Delegates.notNull()

    companion object {
        fun create(rankingPokemonTrend: RankingPokemonTrend): TrendForBattle {
            val result = TrendForBattle(rankingPokemonTrend.seikakuInfo,
                    rankingPokemonTrend.tokuseiInfo,
                    rankingPokemonTrend.itemInfo,
                    rankingPokemonTrend.wazaInfo)

            return result
        }
    }

    fun updateSkills(databaseHelper: DatabaseHelper){
        skillList = mutableListOf()
        for (temp in 0..(wazaInfo.size - 2)) {
            if(!wazaInfo[temp].name.equals("null")){
                skillList.add(RankingPokemonSkill.create(wazaInfo[temp], databaseHelper.selectSkillByName(wazaInfo[temp].name)))
            }
        }
    }
}
