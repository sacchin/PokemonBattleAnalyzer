package com.gmail.sacchin13.pokemonbattleanalyzer.entity

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl.TrendForBattle
import java.text.NumberFormat

class BattleResult {

    var coverRate: Double = 0.0

    val prioritySkills = mutableMapOf<String, Double>()

    var speedOccur = mutableMapOf(
            0 to 0.0, 1 to 0.0, 2 to 0.0, 3 to 0.0,
            4 to 0.0, 5 to 0.0, 6 to 0.0, 7 to 0.0,
            8 to 0.0, 9 to 0.0, 10 to 0.0, 11 to 0.0
    )

    val correctionRate = arrayOf(0.0, 0.0, 0.0)

    var scarfRate = 0.0

    var orderAbilityRate = 0.0

    var defeatedTimes = mutableMapOf<String, MutableMap<Int, Double>>()

    var didAttack = false

    var defeatTimes = mutableMapOf(
            1 to 0.0, 2 to 0.0, 3 to 0.0, 4 to 0.0, 5 to 0.0
    )

    fun updateDefeatTimes(key: Int, value: Double) {
        defeatTimes[key] = defeatTimes[key]!!.plus(value)
    }

    fun updateDefeatedTimes(skillName: String, key: Int, value: Double) {
        if (defeatedTimes[skillName] == null) {
            defeatedTimes[skillName] = mutableMapOf(
                    1 to 0.0, 2 to 0.0, 3 to 0.0, 4 to 0.0, 5 to 0.0
            )
        }

        val a = defeatedTimes[skillName] as MutableMap<Int, Double>
        a[key] = a[key]!!.plus(value)
    }

    fun blow(baseRate: Double): Boolean {
        return if (Math.abs(defeatTimes[1]!!.minus(baseRate)) < 0.001) true else false
    }

    fun little(): Boolean {
        return if (Math.abs(defeatTimes[1]!!.minus(0)) < 0.001 &&
                Math.abs(defeatTimes[2]!!.minus(0)) < 0.001 &&
                Math.abs(defeatTimes[3]!!.minus(0)) < 0.001 &&
                Math.abs(defeatTimes[4]!!.minus(0)) < 0.001)
            true else false
    }

    fun coverRate(): String {
        if (coverRate < 0 || 1 < coverRate) {
            return "ERROR"
        } else if (coverRate.minus(0.0) < 0.00001) {
            return "0%"
        } else if (coverRate < 0.001) {
            return "極小"
        } else {
            val format = NumberFormat.getInstance()
            format.maximumFractionDigits = 1
            return format.format(coverRate.times(100.0)) + "%"
        }
    }

    fun add(result: BattleResult) {
        if (result.didAttack) {
            for (key in defeatTimes.keys) {
                defeatTimes[key] = defeatTimes[key]!!.plus(result.defeatTimes[key]!!)
            }

            for ((key, value) in result.defeatedTimes) {
                if (defeatedTimes[key] == null) {
                    defeatedTimes.put(key, value)
                } else {
                    val r = defeatedTimes[key] as MutableMap<Int, Double>
                    r[1] = r[1]!!.plus(value[1]!!)
                    r[2] = r[2]!!.plus(value[2]!!)
                    r[3] = r[3]!!.plus(value[3]!!)
                    r[4] = r[4]!!.plus(value[4]!!)
                    r[5] = r[5]!!.plus(value[5]!!)
                }
            }
        }

        for (key in speedOccur.keys) {
            speedOccur[key] = speedOccur[key]!!.plus(result.speedOccur[key]!!)
        }

        prioritySkills.putAll(result.prioritySkills)
    }

    fun orderRate(opponent: PokemonForBattle, rate: Double) {
        val type = Characteristic.correction(opponent.characteristic, "S").times(10).toInt()
        when (type) {
            9 -> correctionRate[0] = correctionRate[0] + rate
            11 -> correctionRate[1] = correctionRate[1] + rate
            else -> correctionRate[2] = correctionRate[2] + rate
        }

        if (opponent.item == "こだわりスカーフ") scarfRate += rate
        if (opponent.ability == "すなかき" || opponent.ability == "ようりょくそ"
                || opponent.ability == "すいすい") orderAbilityRate += rate
    }

    fun orderResult(mine: PokemonForBattle, opponent: PokemonForBattle,
                    field: BattleField, myTailWind: Boolean, oppoTailWind: Boolean): String {
        var before = 0
        val result = mutableMapOf<String, Int>()
        val mySpeed = mine.calcSpeedValue(field, myTailWind, false)
        for ((index, speed) in opponent.speedValues(oppoTailWind).withIndex()) {
            val label = BattleStatus.name(index)

            if (before < mySpeed && mySpeed < speed) {
                result.put("mine", mySpeed)
                result.put(label, speed)
            } else {
                result.put(label, speed)
            }

            before = speed
        }

        if (before < mySpeed) result.put("mine", mySpeed)

        if (result.isEmpty()) {
            return "-"
        } else {
            var t = ""
            for ((key, value) in result) {
                if (key == "mine") t += "---> $value <---\n"
                else t += "$key($value)\n"
            }
            return t
        }
    }

    fun prioritySkill(pokemon: TrendForBattle) {
        for ((ranking1, usageRate, name) in pokemon.tokuseiInfo) {
            for ((ranking, sequenceNumber, skill) in pokemon.skillList) {
                var priority = skill.priority
                var key = skill.jname
                var rate = 1.0
                if ((name == "いたずらごころ" && skill.category == 2) ||
                        (name == "はやてのつばさ" && skill.type == Type.no(Type.Code.FLYING)) ||
                        (name == "ヒーリングシフト")) {
                    priority += 1
                    key += ":$name($priority)"
                    rate = usageRate.toDouble()
                } else {
                    key += "($priority)"
                }
                if (0 < priority) prioritySkills[key] = rate
            }
        }
    }

    fun count(): Int {
        var sum = 0
        for ((key, value) in defeatTimes) sum += value.toInt()
        return sum
    }

    fun arrayForBarChart(): FloatArray {
        val denominator = defeatTimes.values.sum()
        if (denominator < 0.1) {
            return floatArrayOf(0f, 0f, 0f, 0f)
        }

        val base4 = 100.div(denominator)
        return floatArrayOf(defeatTimes[1]!!.times(base4).toFloat(),
                defeatTimes[2]!!.times(base4).toFloat(),
                defeatTimes[3]!!.times(base4).toFloat(),
                defeatTimes[4]!!.times(base4).toFloat(),
                defeatTimes[5]!!.times(base4).toFloat())
    }


}