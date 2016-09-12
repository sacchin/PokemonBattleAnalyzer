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
    public open var ability: String = "",
    public open var megaType: String = ""
): RealmObject(){
}