package com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl

import com.gmail.sacchin13.pokemonbattleanalyzer.DatabaseHelper

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
        for (temp in 0..(wazaInfo.size - 2)) {
            if (!wazaInfo[temp].name.equals("null")) {
                skillList.add(RankingPokemonSkill.create(wazaInfo[temp], databaseHelper.selectSkillByName(wazaInfo[temp].name)))
            }
        }
    }

    fun skillNames(): List<String> {
        val result = mutableListOf<String>()
        for (w in skillList) result.add(w.skill.jname)
        return result
    }


    fun createSkillMap(): List<Pair<String, String>> {
        val result = mutableListOf<Pair<String, String>>()
        if (skillList.isEmpty()) {
            result.add(Pair("-", "-"))
            return result
        }
        for (w in skillList) if (w.skill.jname.isNullOrEmpty().not()) result.add(Pair(w.skill.jname, "${w.usageRate}%"))
        return result
    }

    fun createCharacteristicMap(): List<Pair<String, String>> {
        val result = mutableListOf<Pair<String, String>>()
        if (seikakuInfo.isEmpty()) {
            result.add(Pair("-", "-"))
            return result
        }
        for (w in seikakuInfo) if (w.name.isNullOrEmpty().not()) result.add(Pair(w.name, "${w.usageRate}%"))
        return result
    }

    fun createAbilityMap(): List<Pair<String, String>> {
        val result = mutableListOf<Pair<String, String>>()
        if (tokuseiInfo.isEmpty()) {
            result.add(Pair("-", "-"))
            return result
        }
        for (w in tokuseiInfo) if (w.name.isNullOrEmpty().not()) result.add(Pair(w.name, "${w.usageRate}%"))
        return result
    }

    fun createItemMap(): List<Pair<String, String>> {
        val result = mutableListOf<Pair<String, String>>()
        if (itemInfo.isEmpty()) {
            result.add(Pair("-", "-"))
            return result
        }
        for (w in itemInfo) if (w.name.isNullOrEmpty().not()) result.add(Pair(w.name, "${w.usageRate}%"))
        return result
    }
}
