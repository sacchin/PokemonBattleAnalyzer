package com.gmail.sacchin13.pokemonbattleanalyzer.entity

import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
public open class PokemonMasterData(
        public open var no: String = "none",
        public open var jname: String = "none",
        public open var ename: String = "none",
        public open var h: Int = -1,
        public open var a: Int = -1,
        public open var b: Int = -1,
        public open var c: Int = -1,
        public open var d: Int = -1,
        public open var s: Int = -1,
        public open var ability1: String = "none",
        public open var ability2: String = "none",
        public open var abilityd: String = "none",
        public open var type1: Int = -1,
        public open var type2: Int = -1,
        public open var weight: Float = -1f,
        public open var megax: MegaPokemonMasterData? = null,
        public open var megay: MegaPokemonMasterData? = null
): RealmObject()  {

    fun getHPValue(iv: Int, ev: Int): Int {
        val effort = if(ev == IndividualPBAPokemon.UNKNOWN) 252 else ev
        return (h * 2 + iv + effort / 4) / 2 + 60
    }

    fun getAttackValue(iv: Int, ev: Int): Int {
        val effort = if(ev == IndividualPBAPokemon.UNKNOWN) 252 else ev
        return ((a * 2 + iv + effort / 4) / 2 + 5).toInt()
    }

    fun getDefenseValue(iv: Int, ev: Int): Int {
        val effort = if(ev == IndividualPBAPokemon.UNKNOWN) 252 else ev
        return ((b * 2 + iv + effort / 4) / 2 + 5).toInt()
    }

    fun getSpecialAttackValue(iv: Int, ev: Int): Int {
        val effort = if(ev == IndividualPBAPokemon.UNKNOWN) 252 else ev
        return ((c * 2 + iv + effort / 4) / 2 + 5).toInt()
    }

    fun getSpecialDefenseValue(iv: Int, ev: Int): Int {
        val effort = if(ev == IndividualPBAPokemon.UNKNOWN) 252 else ev
        return ((d * 2 + iv + effort / 4) / 2 + 5).toInt()
    }

    fun getSpeedValue(iv: Int, ev: Int): Int {
        val effort = if(ev == IndividualPBAPokemon.UNKNOWN) 252 else ev
        return ((s * 2 + iv + effort / 4) / 2 + 5).toInt()
    }
}
