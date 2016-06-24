package com.gmail.sacchin13.pokemonbattleanalyzer.entity

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.Ignore
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
        public open var mega: RealmList<PokemonMasterData>? = null
): RealmObject()  {

    @Ignore var resourceId:Int = 0

    fun addMega(mega: PokemonMasterData) {
        if (this.mega == null) {
            this.mega = RealmList<PokemonMasterData>()
        }
        this.mega!!.add(mega)
    }

    fun getHPValue(iv: Int, ev: Int): Int {
        var effort = 0
        if(ev == IndividualPBAPokemon.UNKNOWN) effort = 252
        else effort = ev
        return (h * 2 + iv + effort / 4) / 2 + 60
    }

    fun getAttackValue(iv: Int, ev: Int): Int {
        var effort = 0
        if(ev == IndividualPBAPokemon.UNKNOWN) effort = 252
        else effort = ev
        return ((a * 2 + iv + effort / 4) / 2 + 5).toInt()
    }

    fun getDefenseValue(iv: Int, ev: Int): Int {
        var effort = 0
        if(ev == IndividualPBAPokemon.UNKNOWN) effort = 252
        else effort = ev
        return ((b * 2 + iv + effort / 4) / 2 + 5).toInt()
    }

    fun getSpecialAttackValue(iv: Int, ev: Int): Int {
        var effort = 0
        if(ev == IndividualPBAPokemon.UNKNOWN) effort = 252
        else effort = ev
        return ((c * 2 + iv + effort / 4) / 2 + 5).toInt()
    }

    fun getSpecialDefenseValue(iv: Int, ev: Int): Int {
        var effort = 0
        if(ev == IndividualPBAPokemon.UNKNOWN) effort = 252
        else effort = ev
        return ((d * 2 + iv + effort / 4) / 2 + 5).toInt()
    }

    fun getSpeedValue(iv: Int, ev: Int): Int {
        var effort = 0
        if(ev == IndividualPBAPokemon.UNKNOWN) effort = 252
        else effort = ev
        return ((s * 2 + iv + effort / 4) / 2 + 5).toInt()
    }
}
