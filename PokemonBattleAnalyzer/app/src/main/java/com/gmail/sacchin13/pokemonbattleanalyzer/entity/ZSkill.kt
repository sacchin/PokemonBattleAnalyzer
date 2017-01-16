package com.gmail.sacchin13.pokemonbattleanalyzer.entity

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl.RankingPokemonSkill
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl.WazaInfo
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class ZSkill (
        open var no: Int = -1,
        open var skillNumber: Int = -1,
        open var jname: String = "unknown",
        open var type: Int = -1,
        open var power: Int = -1,
        open var category: Int = -1,
        open var rank: Int = -1
) : RealmObject() {


    fun convert(): Skill {
        val skillName = when(category){
            2 -> "Z".plus(jname)
            else -> Type.name(Type.code(type)).plus("Z")
        }

        return Skill(-1, skillName, skillName, type, power, 100.0, category, 1, 0, false, false, -1, 0.0, -1, 0.0, -1, 0.0)
    }
}