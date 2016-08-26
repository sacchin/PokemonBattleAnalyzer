package com.gmail.sacchin13.pokemonbattleanalyzer.entity

import java.text.NumberFormat

class BattleResult {

    var coverRate: Double = 0.0

    var mayOccur = mutableMapOf<BattleStatus.Code, Double>(
            BattleStatus.Code.WIN to 0.0,
            BattleStatus.Code.DEFEAT to 0.0,
            BattleStatus.Code.REVERSE to 0.0,
            BattleStatus.Code.OWN_HEAD to 0.0,
            BattleStatus.Code.DRAW to 0.0
    )

    var skillDetail = mutableMapOf<BattleStatus.Code, MutableSet<String>>(
            BattleStatus.Code.WIN to mutableSetOf<String>(),
            BattleStatus.Code.DEFEAT to mutableSetOf<String>(),
            BattleStatus.Code.REVERSE to mutableSetOf<String>(),
            BattleStatus.Code.OWN_HEAD to mutableSetOf<String>(),
            BattleStatus.Code.DRAW to mutableSetOf<String>()
    )

    fun updateWinDefeat(first: PokemonForBattle, second: PokemonForBattle, rate: Double) {
        when (second.side) {
            PartyInBattle.MY_SIDE -> {
                mayOccur[BattleStatus.Code.DEFEAT] = mayOccur[BattleStatus.Code.DEFEAT]!!.plus(rate)
                skillDetail[BattleStatus.Code.DEFEAT]!!.add(first.skill.jname)
            }
            PartyInBattle.OPPONENT_SIDE -> {
                mayOccur[BattleStatus.Code.WIN] = mayOccur[BattleStatus.Code.WIN]!!.plus(rate)
                skillDetail[BattleStatus.Code.WIN]!!.add(first.skill.jname)
            }
        }
    }

    fun updateReverseOwnHeadDefeat(first: PokemonForBattle, second: PokemonForBattle, rate: Double) {
        when (first.side) {
            PartyInBattle.MY_SIDE -> {
                mayOccur[BattleStatus.Code.OWN_HEAD] =
                        mayOccur[BattleStatus.Code.OWN_HEAD]!!.plus(rate)
                skillDetail[BattleStatus.Code.OWN_HEAD]!!.add(second.skill.jname)
            }
            PartyInBattle.OPPONENT_SIDE -> {
                mayOccur[BattleStatus.Code.REVERSE] =
                        mayOccur[BattleStatus.Code.REVERSE]!!.plus(rate)
                skillDetail[BattleStatus.Code.REVERSE]!!.add(second.skill.jname)
            }
        }
    }

    fun updateDraw(rate: Double) {
        mayOccur[BattleStatus.Code.DRAW] =
                mayOccur[BattleStatus.Code.DRAW]!!.plus(rate)
    }

    fun winRate(): String{
        val value = mayOccur[BattleStatus.Code.WIN]!!.plus(mayOccur[BattleStatus.Code.REVERSE]!!)
        if(value < 0 || 1 < value){
            return "ERROR"
        }else if(value.minus(0.0) < 0.00001){
            return "0%"
        }else if(value < 0.001){
            return "極小"
        }else{
            val format = NumberFormat.getInstance()
            format.maximumFractionDigits = 1
            return format.format(value.times(100.0)) + "%"
        }
    }

    fun loseRate(): String{
        val value = mayOccur[BattleStatus.Code.DEFEAT]!!.plus(mayOccur[BattleStatus.Code.OWN_HEAD]!!)
        if(value < 0 || 1 < value){
            return "ERROR"
        }else if(value.minus(0.0) < 0.00001){
            return "0%"
        }else if(value < 0.001){
            return "極小"
        }else{
            val format = NumberFormat.getInstance()
            format.maximumFractionDigits = 1
            return format.format(value.times(100.0)) + "%"
        }
    }

    fun drawRate(): String{
        val value = mayOccur[BattleStatus.Code.DRAW]!!
        if(value < 0 || 1 < value){
            return "ERROR"
        }else if(value.minus(0.0) < 0.00001){
            return "0%"
        }else if(value < 0.001){
            return "極小"
        }else{
            val format = NumberFormat.getInstance()
            format.maximumFractionDigits = 1
            return format.format(value.times(100.0)) + "%"
        }
    }

    fun coverRate(): String{
        if(coverRate < 0 || 1 < coverRate){
            return "ERROR"
        }else if(coverRate.minus(0.0) < 0.00001){
            return "0%"
        }else if(coverRate < 0.001){
            return "極小"
        }else{
            val format = NumberFormat.getInstance()
            format.maximumFractionDigits = 1
            return format.format(coverRate.times(100.0)) + "%"
        }
    }

    fun add(result: BattleResult){
        mayOccur[BattleStatus.Code.WIN] = mayOccur[BattleStatus.Code.WIN]!!.plus(result.mayOccur[BattleStatus.Code.WIN]!!)
        mayOccur[BattleStatus.Code.DEFEAT] = mayOccur[BattleStatus.Code.DEFEAT]!!.plus(result.mayOccur[BattleStatus.Code.DEFEAT]!!)
        mayOccur[BattleStatus.Code.REVERSE] = mayOccur[BattleStatus.Code.REVERSE]!!.plus(result.mayOccur[BattleStatus.Code.REVERSE]!!)
        mayOccur[BattleStatus.Code.OWN_HEAD] = mayOccur[BattleStatus.Code.OWN_HEAD]!!.plus(result.mayOccur[BattleStatus.Code.OWN_HEAD]!!)
        mayOccur[BattleStatus.Code.DRAW] = mayOccur[BattleStatus.Code.DRAW]!!.plus(result.mayOccur[BattleStatus.Code.DRAW]!!)
    }
}