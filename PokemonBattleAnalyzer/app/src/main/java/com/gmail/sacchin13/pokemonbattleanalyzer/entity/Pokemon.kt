package com.gmail.sacchin13.pokemonbattleanalyzer.entity

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
public open class Pokemon (
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
        public open var mega: RealmList<Pokemon>? = null
): RealmObject()  {

    init {
        /**
         * nnn-mm
         */
        this.mega = RealmList<Pokemon>()
    }

    fun addMega(mega: Pokemon) {
        if (this.mega == null) {
            this.mega = RealmList<Pokemon>()
        }
        this.mega!!.add(mega)
    }

    fun getHPValue(iv: Int, ev: Int): Int {
        return (h * 2 + iv + ev / 4) / 2 + 60
    }

    fun getAttackValue(iv: Int, ev: Int): Int {
        return ((a * 2 + iv + ev / 4) / 2 + 5).toInt()
    }

    fun getDefenseValue(iv: Int, ev: Int): Int {
        return ((b * 2 + iv + ev / 4) / 2 + 5).toInt()
    }

    fun getSpecialAttackValue(iv: Int, ev: Int): Int {
        return ((c * 2 + iv + ev / 4) / 2 + 5).toInt()
    }

    fun getSpecialDefenseValue(iv: Int, ev: Int): Int {
        return ((d * 2 + iv + ev / 4) / 2 + 5).toInt()
    }

    fun getSpeedValue(iv: Int, ev: Int): Int {
        return ((s * 2 + iv + ev / 4) / 2 + 5).toInt()
    }
}
