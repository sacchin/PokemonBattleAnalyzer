package com.gmail.sacchin13.pokemonbattleanalyzer.entity

import java.util.ArrayList

class Pokemon (val no: String, val jname: String, val ename: String, val h: Int, val a: Int, val b: Int, val c: Int, val d: Int, val s: Int,
               val ability1: String, val ability2: String, val abilityd: String, val type1: Type.TypeCode, val type2: Type.TypeCode, val weight: Float) {
    var mega: MutableList<Pokemon>? = null

    init {
        /**
         * nnn-mm
         */
        this.mega = ArrayList<Pokemon>()
    }

    /**

     * @param mega
     */
    fun addMega(mega: Pokemon) {
        if (this.mega == null) {
            this.mega = ArrayList<Pokemon>()
        }
        this.mega!!.add(mega)
    }

    fun getHPValue(iv: Int, ev: Int): Int {
        return (h * 2 + iv + ev / 4) / 2 + 60
    }

    fun getAttackValue(iv: Int, ev: Int): Int {
        return ((a * 2 + iv + ev / 4) / 2 + 5).toInt()
    }

    fun getDeffenceValue(iv: Int, ev: Int): Int {
        return ((b * 2 + iv + ev / 4) / 2 + 5).toInt()
    }

    fun getSpecialAttackValue(iv: Int, ev: Int): Int {
        return ((c * 2 + iv + ev / 4) / 2 + 5).toInt()
    }

    fun getSpecialDeffenceValue(iv: Int, ev: Int): Int {
        return ((d * 2 + iv + ev / 4) / 2 + 5).toInt()
    }

    fun getSpeedValue(iv: Int, ev: Int): Int {
        return ((s * 2 + iv + ev / 4) / 2 + 5).toInt()
    }
}
