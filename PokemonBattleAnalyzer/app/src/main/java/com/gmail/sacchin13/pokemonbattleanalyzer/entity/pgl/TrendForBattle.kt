package com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl

import android.util.Log
import com.gmail.sacchin13.pokemonbattleanalyzer.DatabaseHelper
import com.gmail.sacchin13.pokemonbattleanalyzer.util.Util

data class TrendForBattle(
        val seikakuInfo: List<Info>,
        val tokuseiInfo: List<Info>,
        val itemInfo: List<Info>,
        val wazaInfo: List<WazaInfo>) {

    var skillList: MutableList<RankingPokemonSkill> = mutableListOf()

    companion object {
        fun create(rankingPokemonTrend: RankingPokemonTrend): TrendForBattle {
            val result = TrendForBattle(rankingPokemonTrend.seikakuInfo,
                    rankingPokemonTrend.tokuseiInfo,
                    rankingPokemonTrend.itemInfo,
                    rankingPokemonTrend.wazaInfo)

            return result
        }
    }

    fun updateSkills(databaseHelper: DatabaseHelper) {
        skillList.clear()
        for (temp in wazaInfo) {
            val skillName = temp.name ?: "null"
            if (skillName != "null") {
                val skill = databaseHelper.selectSkillByName(skillName)
                skillList.add(RankingPokemonSkill.create(temp, skill))
                skillList.add(RankingPokemonSkill.create(temp, databaseHelper.selectZSkill(skill.no)))
            }
        }
    }

    fun skillNames(): List<String> {
        val result = mutableListOf<String>()
        for (w in skillList) result.add(w.skill.jname)
        return result
    }


    fun createSkillMap(util: Util): List<Pair<String, String>> {
        val result = mutableListOf<Pair<String, String>>()
        if (wazaInfo.isEmpty()) {
            Log.v("createSkillMap", "empty")
            result.add(Pair("-", "-"))
            return result
        }
        for (w in wazaInfo) {
            if (w.name.isNullOrEmpty().not() && 0.01 < w.usageRate) {
                Log.v("createSkillMap", "${w.name}:")
                result.add(Pair(w.name, Util.percent(w.usageRate.times(100.0))))
            } else {
                Log.v("createSkillMap", "not(${w.usageRate})")
            }
        }
        return result
    }

    fun createCharacteristicMap(util: Util): List<Pair<String, String>> {
        val result = mutableListOf<Pair<String, String>>()
        if (seikakuInfo.isEmpty()) {
            result.add(Pair("-", "-"))
            return result
        }
        for (w in seikakuInfo) {
            if (w.name.isNullOrEmpty().not() && 0.01 < w.usageRate) {
                result.add(Pair(w.name, Util.percent(w.usageRate.times(100.0))))
            }
        }
        return result
    }

    fun createAbilityMap(util: Util): List<Pair<String, String>> {
        val result = mutableListOf<Pair<String, String>>()
        if (tokuseiInfo.isEmpty()) {
            result.add(Pair("-", "-"))
            return result
        }
        for (w in tokuseiInfo) {
            if (w.name.isNullOrEmpty().not() && 0.01 < w.usageRate) {
                result.add(Pair(w.name, Util.percent(w.usageRate.times(100.0))))
            }
        }
        return result
    }

    fun createItemMap(util: Util): List<Pair<String, String>> {
        val result = mutableListOf<Pair<String, String>>()
        if (itemInfo.isEmpty()) {
            result.add(Pair("-", "-"))
            return result
        }
        for (w in itemInfo) {
            if (w.name.isNullOrEmpty().not() && 0.01 < w.usageRate) {
                result.add(Pair(w.name, Util.percent(w.usageRate.times(100.0))))
            }
        }
        return result
    }
}
