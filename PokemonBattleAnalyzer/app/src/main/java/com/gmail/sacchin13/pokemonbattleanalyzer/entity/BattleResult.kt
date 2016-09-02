package com.gmail.sacchin13.pokemonbattleanalyzer.entity

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl.RankingPokemonSkill
import java.text.NumberFormat

class BattleResult {

    var coverRate: Double = 0.0

    val priority = mutableMapOf<String, Double>()

    var speedOccur = mutableMapOf<Int, Double>(
            0 to 0.0, 1 to 0.0, 2 to 0.0, 3 to 0.0,
            4 to 0.0, 5 to 0.0, 6 to 0.0, 7 to 0.0,
            8 to 0.0, 9 to 0.0, 10 to 0.0, 11 to 0.0
    )

    var defeatTimes = mutableMapOf<Int, Double>(
            1 to 0.0, 2 to 0.0, 3 to 0.0, 4 to 0.0, 5 to 0.0
    )

    var mayOccur = mutableMapOf<BattleStatus.Code, Double>(
            BattleStatus.Code.IMPOSSIBLE to 0.0,
            BattleStatus.Code.DEPEND_EV to 0.0,
            BattleStatus.Code.POSSIBLE to 0.0
    )

    var skillDetail = mutableMapOf<BattleStatus.Code, MutableSet<String>>(
            BattleStatus.Code.WIN to mutableSetOf<String>(),
            BattleStatus.Code.DEFEAT to mutableSetOf<String>(),
            BattleStatus.Code.REVERSE to mutableSetOf<String>(),
            BattleStatus.Code.OWN_HEAD to mutableSetOf<String>(),
            BattleStatus.Code.DRAW to mutableSetOf<String>()
    )

    fun updateDefeatTimes(key: Int, value: Double){
        defeatTimes[key] = defeatTimes[key]!!.plus(value)
    }

    fun updatePossible(rate: Double) {
        println("-- ${rate}")
        mayOccur[BattleStatus.Code.POSSIBLE] = mayOccur[BattleStatus.Code.POSSIBLE]!!.plus(rate)
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

    fun add(opponent: PokemonForBattle, rate: Double){
        val type = Characteristic.correction(opponent.characteristic, "S").times(10).toInt()
        var addValue = rate
        val label = when(type){
            9 -> {
                if(opponent.item.equals("こだわりスカーフ")) {
                    arrayOf(5)
                } else {
                    arrayOf(0, 1)
                }
            }
            10-> {
                if(opponent.item.equals("こだわりスカーフ")) {
                    arrayOf(7, 10)
                } else {
                    arrayOf(2, 3, 6)
                }
            }
            11-> {
                if(opponent.item.equals("こだわりスカーフ")) {
                    arrayOf(8, 11)
                } else {
                    arrayOf(4, 9)
                }
            }
            else -> arrayOf()
        }

        for(temp in label) speedOccur[temp] = speedOccur[temp]!!.plus(rate.div(label.size))
    }

    fun add(skillList: MutableList<RankingPokemonSkill>){
        if(priority.isEmpty()){
            for(temp in skillList){
                if(0 < temp.skill.priority) priority[temp.skill.jname + "(${temp.skill.priority})"] = temp.usageRate.toDouble()
            }
            if(priority.isEmpty()) priority["-"] = 1.0
        }
    }

    fun count(): Int{
        var sum = 0
        for(i in defeatTimes) sum += i.value.toInt()
        return sum
    }

}