package com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl

import java.util.Comparator
import java.util.HashMap
import java.util.TreeMap

import org.json.JSONObject

data class RankingPokemonTrend(
        val wazaInfo: List<WazaInfo>,
        val seikakuInfo: List<Info>,
        val tokuseiInfo: List<Info>,
        val itemInfo: List<Info>) {

    fun createSkillMap(): TreeMap<String, Array<String>> {
        val result = HashMap<String, Array<String>>()
//        for (pokemonSkill in wazaInfo!!) {
//            if (pokemonSkill.master.jname == null || pokemonSkill.master.jname!!.isEmpty()) {
//                continue
//            }
//
//            val temp = arrayOfNulls<String>(2)
//            temp[0] = pokemonSkill.master.jname
//            temp[1] = pokemonSkill.usageRate.toInt().toString()
//            result.put(pokemonSkill.sequenceNumber.toString(), temp)
//        }
        val treeMap = TreeMap<String, Array<String>>(UsageMapComparator(result))
       treeMap.putAll(result)

        return treeMap
    }

    fun createAbilityMap(): TreeMap<String, Array<String>> {
        val result = HashMap<String, Array<String>>()
//        for (ability in abilityList!!) {
//            if (ability.name == null || ability.name.isEmpty()) {
//                continue
//            }
//            val temp = arrayOfNulls<String>(2)
//            temp[0] = ability.name
//            temp[1] = ability.usageRate.toInt().toString()
//            result.put(ability.sequenceNumber.toString(), temp)
//        }
        val treeMap = TreeMap<String, Array<String>>(UsageMapComparator(result))
        treeMap.putAll(result)

        return treeMap
    }

    fun createCharacteristicMap(): TreeMap<String, Array<String>> {
        val result = HashMap<String, Array<String>>()
//        for (pokemonCharacteristic in characteristicList!!) {
//            if (pokemonCharacteristic.name == null || pokemonCharacteristic.name.isEmpty()) {
//                continue
//            }
//            val temp = arrayOfNulls<String>(2)
//            temp[0] = pokemonCharacteristic.name
//            temp[1] = pokemonCharacteristic.usageRate.toInt().toString()
//            result.put(pokemonCharacteristic.sequenceNumber.toString(), temp)
//        }
        val treeMap = TreeMap<String, Array<String>>(UsageMapComparator(result))
        treeMap.putAll(result)

        return treeMap
    }

    fun createItemMap(): TreeMap<String, Array<String>> {
        val result = HashMap<String, Array<String>>()
//        for (item in itemList!!) {
//            //			if(item.getName() == null || item.getName().isEmpty()){
//            //				continue;
//            //			}
//            val temp = arrayOfNulls<String>(2)
//            temp[0] = item.name.name
//            temp[1] = item.usageRate.toInt().toString()
//            result.put(item.sequenceNumber.toString(), temp)
//        }
        val treeMap = TreeMap<String, Array<String>>(UsageMapComparator(result))
        treeMap.putAll(result)

        return treeMap
    }


    internal inner class UsageMapComparator(private val map: Map<String, Array<String>>) : Comparator<String> {
        override fun compare(key1: String, key2: String): Int {
//            try {
//                val value1 = Integer.parseInt(map[key1][1])
//                val value2 = Integer.parseInt(map[key2][1])
//                if (value1 == value2) {
//                    return key1.toLowerCase().compareTo(key2.toLowerCase())
//                } else if (value1 < value2) {
//                    return 1
//                } else {
//                    return -1
//                }
//            } catch (e: NumberFormatException) {
//                e.printStackTrace()
                return 0
//            }

        }
    }

    companion object {
        fun createRankingPokemonTrend(rankingPokemonTrend: JSONObject): RankingPokemonTrend? {
//            try {
//                val wazaInfo = rankingPokemonTrend.getJSONArray("wazaInfo")
//                val seikakuInfo = rankingPokemonTrend.getJSONArray("seikakuInfo")
//                val tokuseiInfo = rankingPokemonTrend.getJSONArray("tokuseiInfo")
//                val itemInfo = rankingPokemonTrend.getJSONArray("itemInfo")
//                return RankingPokemonTrend(wazaInfo, seikakuInfo, tokuseiInfo, itemInfo)
//            } catch (e: JSONException) {
//                e.printStackTrace()
//            }

            return null
        }
    }

    fun convertToFew(){
        wazaInfo.forEach { it.convertToFew() }
        seikakuInfo.forEach { it.convertToFew() }
        tokuseiInfo.forEach { it.convertToFew() }
        itemInfo.forEach { it.convertToFew() }
    }

}
