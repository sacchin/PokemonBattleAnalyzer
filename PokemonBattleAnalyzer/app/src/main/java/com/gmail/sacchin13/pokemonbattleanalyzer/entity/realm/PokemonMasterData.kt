package com.gmail.sacchin13.pokemonbattleanalyzer.entity.realm

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.ui.PokemonMasterDataForUI
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class PokemonMasterData(
        open var no: String = "none",
        open var jname: String = "none",
        open var ename: String = "none",
        open var form: String = "none",
        open var h: Int = -1,
        open var a: Int = -1,
        open var b: Int = -1,
        open var c: Int = -1,
        open var d: Int = -1,
        open var s: Int = -1,
        open var ability1: String = "none",
        open var ability2: String = "none",
        open var abilityd: String = "none",
        open var type1: Int = -1,
        open var type2: Int = -1,
        open var weight: Float = -1f,
        open var megax: MegaPokemonMasterData? = null,
        open var megay: MegaPokemonMasterData? = null
) : RealmObject() {

    companion object {
        fun create(no: String, jname: String, ename: String, form: String,
                   h: Int, a: Int, b: Int, c: Int, d: Int, s: Int,
                   ability1: String, ability2: String, abilityd: String,
                   type1: Int, type2: Int, weight: Float): PokemonMasterData {
            return create(no, jname, ename, form, h, a, b, c, d, s,
                    ability1, ability2, abilityd, type1, type2, weight, null, null)
        }

        fun create(no: String, jname: String, ename: String, form: String,
                   h: Int, a: Int, b: Int, c: Int, d: Int, s: Int,
                   ability1: String, ability2: String, abilityd: String,
                   type1: Int, type2: Int, weight: Float, megax: MegaPokemonMasterData?): PokemonMasterData {
            return create(no, jname, ename, form, h, a, b, c, d, s,
                    ability1, ability2, abilityd, type1, type2, weight, megax, null)
        }

        fun create(no: String, jname: String, ename: String, form: String,
                   h: Int, a: Int, b: Int, c: Int, d: Int, s: Int,
                   ability1: String, ability2: String, abilityd: String,
                   type1: Int, type2: Int, weight: Float, megax: MegaPokemonMasterData?, megay: MegaPokemonMasterData?): PokemonMasterData {
            return PokemonMasterData(no, jname, ename, form, h, a, b, c, d, s,
                    ability1, ability2, abilityd, type1, type2, weight, megax, megay)
        }
    }

    fun uiObject(): PokemonMasterDataForUI {
        val x = megax?.uiObject()
        val y = megay?.uiObject()

        return PokemonMasterDataForUI(no, jname, ename, form,
                h, a, b, c, d, s, ability1, ability2, abilityd,
                type1, type2, weight, x, y)
    }

    fun battling(): Array<String> {
        val result = mutableListOf("-")
        if (megax != null) result.add(
                when (no) {
                    "555" -> "D"
                    "681" -> "B"
                    else -> "X"
                })
        if (megay != null) result.add("Y")

        return result.toTypedArray()
    }
}
