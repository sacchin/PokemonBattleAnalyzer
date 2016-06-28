package com.gmail.sacchin13.pokemonbattleanalyzer.entity

import org.json.JSONException
import org.json.JSONObject

class PokemonSkill(no: Int, name: String, ename: String, type: Type.Code, power: Int,
                   accuracy: Int, category: Int, pp: Int){

    var master: Skill
    var usageRate = 0.0
    var ranking = 0
    var sequenceNumber = 0

    init{
        master = Skill(no, name, ename, Type.no(type), power, accuracy, category, pp)
    }

    companion object {

        fun createPockemonSkill(waza: JSONObject): PokemonSkill? {
            try {
                val name = waza.getString("name")
                if (name == null || name == "null") {
                    return null
                }

                val obj = PokemonSkill(0, waza.getString("name"), waza.getString("name"), Type.code(waza.getInt("typeId")), 0, 0, 0, 0)
                obj.ranking = waza.getInt("ranking")
                obj.usageRate = waza.getDouble("usageRate")
                obj.sequenceNumber = waza.getInt("sequenceNumber")
                return obj
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            return null
        }
    }
}
