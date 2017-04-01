package com.gmail.sacchin13.pokemonbattleanalyzer.entity.realm

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.ui.MegaPokemonMasterDataForUI
import io.realm.RealmObject

open class MegaPokemonMasterData(
        open var pokemonNo: String = "",
        open var h: Int = 0,
        open var a: Int = 0,
        open var b: Int = 0,
        open var c: Int = 0,
        open var d: Int = 0,
        open var s: Int = 0,
        open var type1: Int = -1,
        open var type2: Int = -1,
        open var weight: Float = 0f,
        open var ability: String = "",
        open var megaType: Int = MEGA_X
) : RealmObject() {

    companion object {
        const val NOT_MEGA = 0
        const val MEGA_X = 1
        const val MEGA_Y = 2

        fun create(pokemonNo: String, h: Int, a: Int, b: Int, c: Int, d: Int, s: Int, type1: Int, type2: Int,
                   weight: Float, ability: String, megaType: Int): MegaPokemonMasterData {
            return MegaPokemonMasterData(pokemonNo, h, a, b, c, d, s, type1, type2, weight, ability, megaType)
        }
    }

    fun uiObject(): MegaPokemonMasterDataForUI {
        return MegaPokemonMasterDataForUI(pokemonNo,
                h, a, b, c, d, s, type1, type2, weight, ability, megaType)
    }
}