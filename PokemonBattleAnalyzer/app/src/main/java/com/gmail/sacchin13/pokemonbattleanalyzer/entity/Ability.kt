package com.gmail.sacchin13.pokemonbattleanalyzer.entity

import org.json.JSONException
import org.json.JSONObject

import java.util.HashMap

class Ability(val ranking: Int = 0,
              val usageRate: Double = 0.0,
              val name: String = "", val
              sequenceNumber: Int = 0
) {

    companion object {
        private var invalidAbility: MutableMap<Type.Code, Array<String>> = HashMap<Type.Code, Array<String>>()

        val ELECTRIC_INVALID_ABILITY = arrayOf("ちくでん", "でんきエンジン", "ひらいしん")
        val WATER_INVALID_ABILITY = arrayOf("かんそうはだ", "ちょすい", "よびみず")
        val GRASS_INVALID_ABILITY = arrayOf("そうしょく")
        val FIRE_INVALID_ABILITY = arrayOf("もらいび")
        val GRAND_INVALID_ABILITY = arrayOf("ふゆう")

        init {
            invalidAbility.put(Type.Code.ELECTRIC, ELECTRIC_INVALID_ABILITY)
            invalidAbility.put(Type.Code.WATER, WATER_INVALID_ABILITY)
            invalidAbility.put(Type.Code.GRASS, GRASS_INVALID_ABILITY)
            invalidAbility.put(Type.Code.FIRE, FIRE_INVALID_ABILITY)
            invalidAbility.put(Type.Code.GROUND, GRAND_INVALID_ABILITY)
        }

        fun createAbility(tokusei: JSONObject): Ability {
            try {
                val name = tokusei.getString("name")
                if (name == null || name == "null") {
                    return Ability()
                }
                return Ability(tokusei.getInt("ranking"), tokusei.getDouble("usageRate"), tokusei.getString("name"), tokusei.getInt("sequenceNumber"))
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            return Ability()
        }

        fun calcTypeScale(ability: String, type: Type.Code): Double {
            val list: Array<String> = invalidAbility[type] ?: arrayOf("")
            if (list.isNotEmpty() && list.contains(ability)) return 0.0

            if ("ふしぎなまもり" == ability) {
                if (type === Type.Code.FIRE || type === Type.Code.GHOST || type === Type.Code.FLYING ||
                        type === Type.Code.ROCK || type === Type.Code.DARK) {
                    return  2.0
                } else {
                    return  0.0
                }
            }
            return 1.0
        }
    }
}
