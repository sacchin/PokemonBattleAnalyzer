package com.gmail.sacchin13.pokemonbattleanalyzer.entity

import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.RealmClass

@RealmClass
public open class PokemonMasterData(
        public open var no: String = "none",
        public open var jname: String = "none",
        public open var ename: String = "none",
        public open var form: String = "none",
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

    @Ignore
    var cacheSpeedValues = arrayOf<Int>()

    companion object {
        fun create(no: String, jname: String, ename: String, form: String,
                   h: Int, a: Int, b: Int, c: Int, d: Int, s: Int,
                   ability1: String, ability2: String, abilityd: String,
                   type1: Int, type2: Int, weight: Float): PokemonMasterData {
            return PokemonMasterData.create(no, jname, ename, form, h, a, b, c, d, s,
                    ability1, ability2, abilityd, type1, type2, weight, null, null)
        }

        fun create(no: String, jname: String, ename: String, form: String,
                   h: Int, a: Int, b: Int, c: Int, d: Int, s: Int,
                   ability1: String, ability2: String, abilityd: String,
                   type1: Int, type2: Int, weight: Float, megax: MegaPokemonMasterData?): PokemonMasterData {
            return PokemonMasterData.create(no, jname, ename, form, h, a, b, c, d, s,
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

    fun hp(iv: Int, ev: Int): Int {
        val effort = if(ev == IndividualPokemon.UNKNOWN) 252 else ev
        return (h * 2 + iv + effort / 4) / 2 + 60
    }

    fun hpX(iv: Int, ev: Int): Int {
        if(megax!= null){
            val effort = if(ev == IndividualPokemon.UNKNOWN) 252 else ev
            return (megax!!.h * 2 + iv + effort / 4) / 2 + 60
        }else{
            return hp(iv, ev)
        }
    }

    fun hpY(iv: Int, ev: Int): Int {
        if(megay!= null){
            val effort = if(ev == IndividualPokemon.UNKNOWN) 252 else ev
            return (megay!!.h * 2 + iv + effort / 4) / 2 + 60
        }else{
            return hp(iv, ev)
        }
    }

    fun attack(iv: Int, ev: Int): Int {
        val effort = if(ev == IndividualPokemon.UNKNOWN) 252 else ev
        return ((a * 2 + iv + effort / 4) / 2 + 5).toInt()
    }

    fun attackX(iv: Int, ev: Int): Int {
        if(megax!= null) {
            val effort = if(ev == IndividualPokemon.UNKNOWN) 252 else ev
            return ((megax!!.a * 2 + iv + effort / 4) / 2 + 5).toInt()
        } else {
            return attack(iv, ev)
        }
    }

    fun attackY(iv: Int, ev: Int): Int {
        if(megay!= null) {
            val effort = if(ev == IndividualPokemon.UNKNOWN) 252 else ev
            return ((megay!!.a * 2 + iv + effort / 4) / 2 + 5).toInt()
        } else {
            return attack(iv, ev)
        }
    }

    fun defense(iv: Int, ev: Int): Int {
        val effort = if(ev == IndividualPokemon.UNKNOWN) 252 else ev
        return ((b * 2 + iv + effort / 4) / 2 + 5).toInt()
    }

    fun defenseX(iv: Int, ev: Int): Int {
        if(megax!= null) {
            val effort = if(ev == IndividualPokemon.UNKNOWN) 252 else ev
            return ((megax!!.b * 2 + iv + effort / 4) / 2 + 5).toInt()
        } else {
            return defense(iv, ev)
        }
    }

    fun defenseY(iv: Int, ev: Int): Int {
        if(megay!= null) {
            val effort = if(ev == IndividualPokemon.UNKNOWN) 252 else ev
            return ((megay!!.b * 2 + iv + effort / 4) / 2 + 5).toInt()
        } else {
            return defense(iv, ev)
        }
    }

    fun specialAttack(iv: Int, ev: Int): Int {
        val effort = if(ev == IndividualPokemon.UNKNOWN) 252 else ev
        return ((c * 2 + iv + effort / 4) / 2 + 5).toInt()
    }

    fun specialAttackX(iv: Int, ev: Int): Int {
        if(megax!= null) {
            val effort = if(ev == IndividualPokemon.UNKNOWN) 252 else ev
            return ((megax!!.c * 2 + iv + effort / 4) / 2 + 5).toInt()
        }else{
            return specialAttack(iv, ev)
        }
    }

    fun specialAttackY(iv: Int, ev: Int): Int {
        if(megay!= null) {
            val effort = if(ev == IndividualPokemon.UNKNOWN) 252 else ev
            return ((megay!!.c * 2 + iv + effort / 4) / 2 + 5).toInt()
        }else{
            return specialAttack(iv, ev)
        }
    }

    fun specialDefense(iv: Int, ev: Int): Int {
        val effort = if(ev == IndividualPokemon.UNKNOWN) 252 else ev
        return ((d * 2 + iv + effort / 4) / 2 + 5).toInt()
    }

    fun specialDefenseX(iv: Int, ev: Int): Int {
        if(megax!= null) {
            val effort = if(ev == IndividualPokemon.UNKNOWN) 252 else ev
            return ((megax!!.d * 2 + iv + effort / 4) / 2 + 5).toInt()
        }else{
            return specialDefense(iv, ev)
        }
    }

    fun specialDefenseY(iv: Int, ev: Int): Int {
        if(megay!= null) {
            val effort = if(ev == IndividualPokemon.UNKNOWN) 252 else ev
            return ((megay!!.d * 2 + iv + effort / 4) / 2 + 5).toInt()
        }else{
            return specialDefense(iv, ev)
        }
    }

    fun speed(iv: Int, ev: Int): Int {
        val effort = if(ev == IndividualPokemon.UNKNOWN) 252 else ev
        return ((s * 2 + iv + effort / 4) / 2 + 5).toInt()
    }

    fun speedValues(): Array<Int>{
        if(cacheSpeedValues.isEmpty()){
            cacheSpeedValues = arrayOf(
                    speed(0, 0).times(0.9).toInt(),                 //最遅
                    speed(31, 0).times(0.9).toInt(),                //無振負補正
                    speed(31, 0).toInt(),                           //無振無補正
                    speed(31, 4).toInt(),                           //4振無補正
                    speed(31, 0).times(1.1).toInt(),                //無振正補正
                    speed(31, 0).times(0.9).times(1.5).toInt(),     //無振負補正スカーフ
                    speed(31, 252).toInt(),                         //準速
                    speed(31, 0).times(1.5).toInt(),                //無振無補正スカーフ
                    speed(31, 0).times(1.1).times(1.5).toInt(),     //無振正補正スカーフ
                    speed(31, 252).times(1.1).toInt(),              //最速
                    speed(31, 252).times(1.5).toInt(),              //準速スカーフ
                    speed(31, 252).times(1.1).times(1.5).toInt())   //最速スカーフ
        }
        return cacheSpeedValues
    }

    fun speedX(iv: Int, ev: Int): Int {
        if(megax!= null) {
            val effort = if(ev == IndividualPokemon.UNKNOWN) 252 else ev
            return ((megax!!.s * 2 + iv + effort / 4) / 2 + 5).toInt()
        }else{
            return speed(iv, ev)
        }
    }

    fun speedY(iv: Int, ev: Int): Int {
        if(megay!= null) {
            val effort = if(ev == IndividualPokemon.UNKNOWN) 252 else ev
            return ((megay!!.s * 2 + iv + effort / 4) / 2 + 5).toInt()
        }else{
            return speed(iv, ev)
        }
    }

}
