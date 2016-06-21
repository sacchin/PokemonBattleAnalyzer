package com.gmail.sacchin13.pokemonbattleanalyzer.entity

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl.RankingPokemonIn

import java.util.ArrayList

import org.json.JSONArray
import org.json.JSONException

class JSONParser {
    companion object{
        fun createSkillList(wazaInfo: JSONArray?): List<PokemonSkill> {
            val result = ArrayList<PokemonSkill>()
            if (wazaInfo == null || wazaInfo.length() == 0) {
                return result
            }
            for (i in 0..wazaInfo.length() - 1) {
                try {
                    val temp = PokemonSkill.createPockemonSkill(wazaInfo.getJSONObject(i))
                    if (temp != null && 2 < temp.usageRate) {
                        result.add(temp)
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }
            return result
        }

        fun createAbilityList(tokuseiInfo: JSONArray?): List<Ability> {
            val result = ArrayList<Ability>()
            if (tokuseiInfo == null || tokuseiInfo.length() == 0) {
                return result
            }
            for (i in 0..tokuseiInfo.length() - 1) {
                try {
                    val temp = Ability.createAbility(tokuseiInfo.getJSONObject(i))
                    if (temp != null && 2 < temp.usageRate) {
                        result.add(temp)
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }
            return result
        }

        fun createCharacteristicList(seikakuInfo: JSONArray?): List<PokemonCharacteristic> {
            val result = ArrayList<PokemonCharacteristic>()
            if (seikakuInfo == null || seikakuInfo.length() == 0) {
                return result
            }
            for (i in 0..seikakuInfo.length() - 1) {
                try {
                    val temp = PokemonCharacteristic.createCharacteristic(seikakuInfo.getJSONObject(i))
                    if (temp != null && 2 < temp.usageRate) {
                        result.add(temp)
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }
            return result
        }

        fun createItemList(itemInfo: JSONArray?): List<PBAItem> {
            val result = ArrayList<PBAItem>()
            if (itemInfo == null || itemInfo.length() == 0) {
                return result
            }
            for (i in 0..itemInfo.length() - 1) {
                try {
                    val temp = PBAItem.createItem(itemInfo.getJSONObject(i))
                    if (temp != null && 2 < temp.usageRate) {
                        result.add(temp)
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }
            return result
        }

        fun createPokemonRankingList(pbaPokemonRankingInfo: JSONArray?): List<RankingPokemonIn> {
            val result = ArrayList<RankingPokemonIn>()
            if (pbaPokemonRankingInfo == null || pbaPokemonRankingInfo.length() == 0) {
                return result
            }
            for (i in 0..pbaPokemonRankingInfo.length() - 1) {
                try {
                    val temp = RankingPokemonIn.createRankingPokemon(pbaPokemonRankingInfo.getJSONObject(i))
                    result.add(temp)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }
            return result
        }
    }
}

