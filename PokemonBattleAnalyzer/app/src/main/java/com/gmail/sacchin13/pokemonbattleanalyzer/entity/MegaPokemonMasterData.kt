package com.gmail.sacchin13.pokemonbattleanalyzer.entity

import io.realm.RealmObject

public open class MegaPokemonMasterData (
    public open var pokemonNo: String = "",
    public open var h: Int = 0,
    public open var a: Int = 0,
    public open var b: Int = 0,
    public open var c: Int = 0,
    public open var d: Int = 0,
    public open var s: Int = 0,
    public open var weight: Float = 0f,
    public open var ability: String = "",
    public open var megaType: Int = MEGA_X
): RealmObject(){
    
    companion object {
        const val NOT_MEGA = 0
        const val MEGA_X = 1
        const val MEGA_Y = 2

        fun create(pokemonNo: String, h: Int, a: Int, b: Int, c: Int, d: Int, s: Int,
                   weight: Float, ability: String, megaType: Int): MegaPokemonMasterData{
            return MegaPokemonMasterData(pokemonNo, h, a, b, c, d, s, weight, ability, megaType)
        }
    }
}