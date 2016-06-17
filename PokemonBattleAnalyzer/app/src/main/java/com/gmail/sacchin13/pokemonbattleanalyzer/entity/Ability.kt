package com.gmail.sacchin13.pokemonbattleanalyzer.entity


import org.json.JSONException
import org.json.JSONObject

import java.util.HashMap

class Ability(val ranking: Int = 0, val usageRate: Double = 0.0, val name: String = "", val sequenceNumber: Int = 0) {

    companion object {
        private var invalidAbility: MutableMap<Type.TypeCode, Array<String>>? = null
        private var scalUpAbility: MutableMap<Type.TypeCode, Array<String>>? = null

        val ELECTRIC_INVALID_ABILITY = arrayOf("ちくでん", "でんきエンジン", "ひらいしん")
        val WATER_INVALID_ABILITY = arrayOf("かんそうはだ", "ちょすい", "よびみず")
        val GRASS_INVALID_ABILITY = arrayOf("そうしょく")
        val FIRE_INVALID_ABILITY = arrayOf("もらいび")
        val GRAND_INVALID_ABILITY = arrayOf("ふゆう")
        val FIRE_SCALE_UP_ABILITY = arrayOf("かんそうはだ")

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

        fun calcTypeScale(ability: String?, type: Type.TypeCode?): Float {
            if (ability == null || type == null) {
                throw NullPointerException()
            }

            invalidAbility = HashMap<Type.TypeCode, Array<String>>()
            invalidAbility!!.put(Type.TypeCode.ELECTRIC, ELECTRIC_INVALID_ABILITY)
            invalidAbility!!.put(Type.TypeCode.WATER, WATER_INVALID_ABILITY)
            invalidAbility!!.put(Type.TypeCode.GRASS, GRASS_INVALID_ABILITY)
            invalidAbility!!.put(Type.TypeCode.FIRE, FIRE_INVALID_ABILITY)
            invalidAbility!!.put(Type.TypeCode.GROUND, GRAND_INVALID_ABILITY)

            scalUpAbility = HashMap<Type.TypeCode, Array<String>>()
            scalUpAbility!!.put(Type.TypeCode.FIRE, FIRE_SCALE_UP_ABILITY)

            var list: Array<String>? = invalidAbility!![type]
            if (list != null) {
                for (temp in list) {
                    if (temp == ability) {
                        return 0f
                    }
                }
            }

            list = scalUpAbility!![type]
            if (list != null) {
                for (temp in list) {
                    if (temp == ability) {
                        return 1.25f
                    }
                }
            }
            return 1f
        }
    }
}
