package com.gmail.sacchin13.pokemonbattleanalyzer.entity.ui

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.realm.MegaPokemonMasterData
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.realm.PokemonMasterData

open class PokemonMasterDataForUI(
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
        open var megax: MegaPokemonMasterDataForUI? = null,
        open var megay: MegaPokemonMasterDataForUI? = null
) {

    var cacheSpeedValues = arrayOf<Int>()

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

    fun hpFormula(bs: Int, iv: Int, ev: Int): Int {
        val baseStat = if (bs < 0) 1 else bs
        val effort = if (ev < 0 || 252 < ev) 0 else ev
        val individual = if (iv < 0 || 31 < iv) 0 else iv
        return (baseStat * 2 + individual + effort / 4) / 2 + 60
    }

    fun otherFormula(bs: Int, iv: Int, ev: Int): Int {
        val baseStat = if (bs < 0) 1 else bs
        val effort = if (ev < 0 || 252 < ev) 0 else ev
        val individual = if (iv < 0 || 31 < iv) 0 else iv
        return (baseStat * 2 + individual + effort / 4) / 2 + 5
    }

    fun hp(iv: Int, ev: Int): Int = hpFormula(h, iv, ev)
    fun hpX(iv: Int, ev: Int): Int = megax?.let { hpFormula(it.h, iv, ev) } ?: hp(iv, ev)
    fun hpY(iv: Int, ev: Int): Int = megay?.let { hpFormula(it.h, iv, ev) } ?: hp(iv, ev)
    fun attack(iv: Int, ev: Int): Int = otherFormula(a, iv, ev)
    fun attackX(iv: Int, ev: Int): Int = megax?.let { otherFormula(it.a, iv, ev) } ?: attack(iv, ev)
    fun attackY(iv: Int, ev: Int): Int = megay?.let { otherFormula(it.a, iv, ev) } ?: attack(iv, ev)
    fun defense(iv: Int, ev: Int): Int = otherFormula(b, iv, ev)
    fun defenseX(iv: Int, ev: Int): Int = megax?.let { otherFormula(it.b, iv, ev) } ?: defense(iv, ev)
    fun defenseY(iv: Int, ev: Int): Int = megay?.let { otherFormula(it.b, iv, ev) } ?: defense(iv, ev)
    fun specialAttack(iv: Int, ev: Int): Int = otherFormula(c, iv, ev)
    fun specialAttackX(iv: Int, ev: Int): Int = megax?.let { otherFormula(it.c, iv, ev) } ?: specialAttack(iv, ev)
    fun specialAttackY(iv: Int, ev: Int): Int = megay?.let { otherFormula(it.c, iv, ev) } ?: specialAttack(iv, ev)
    fun specialDefense(iv: Int, ev: Int): Int = otherFormula(d, iv, ev)
    fun specialDefenseX(iv: Int, ev: Int): Int = megax?.let { otherFormula(it.d, iv, ev) } ?: specialDefense(iv, ev)
    fun specialDefenseY(iv: Int, ev: Int): Int = megay?.let { otherFormula(it.d, iv, ev) } ?: specialDefense(iv, ev)
    fun speed(iv: Int, ev: Int): Int = otherFormula(s, iv, ev)
    fun speedX(iv: Int, ev: Int): Int = megax?.let { otherFormula(it.s, iv, ev) } ?: speed(iv, ev)
    fun speedY(iv: Int, ev: Int): Int = megay?.let { otherFormula(it.s, iv, ev) } ?: speed(iv, ev)

    fun speedValues(): Array<Int> {
        if (cacheSpeedValues.isEmpty()) {
            cacheSpeedValues = arrayOf(
                    speed(0, 0).times(0.9).toInt(), //最遅
                    speed(31, 0).times(0.9).toInt(), //無振負補正
                    speed(31, 0).toInt(), //無振無補正
                    speed(31, 4).toInt(), //4振無補正
                    speed(31, 0).times(1.1).toInt(), //無振正補正
                    speed(31, 4).times(1.1).toInt(), //4振正補正
                    speed(31, 252).toInt(), //準速
                    speed(31, 252).times(1.1).toInt()) //最速
        }
        return cacheSpeedValues
    }

    fun speedValuesX(): Array<Int> {
        if (cacheSpeedValues.isEmpty()) {
            cacheSpeedValues = arrayOf(
                    speedX(0, 0).times(0.9).toInt(), //最遅
                    speedX(31, 0).times(0.9).toInt(), //無振負補正
                    speedX(31, 0).toInt(), //無振無補正
                    speedX(31, 4).toInt(), //4振無補正
                    speedX(31, 0).times(1.1).toInt(), //無振正補正
                    speedX(31, 4).times(1.1).toInt(), //4振正補正
                    speedX(31, 252).toInt(), //準速
                    speedX(31, 252).times(1.1).toInt()) //最速
        }
        return cacheSpeedValues
    }

    fun speedValuesY(): Array<Int> {
        if (cacheSpeedValues.isEmpty()) {
            cacheSpeedValues = arrayOf(
                    speedY(0, 0).times(0.9).toInt(), //最遅
                    speedY(31, 0).times(0.9).toInt(), //無振負補正
                    speedY(31, 0).toInt(), //無振無補正
                    speedY(31, 4).toInt(), //4振無補正
                    speedY(31, 0).times(1.1).toInt(), //無振正補正
                    speedY(31, 4).times(1.1).toInt(), //4振正補正
                    speedY(31, 252).toInt(), //準速
                    speedY(31, 252).times(1.1).toInt()) //最速
        }
        return cacheSpeedValues
    }
}
