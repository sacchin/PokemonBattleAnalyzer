package com.gmail.sacchin13.pokemonbattleanalyzer.entity

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl.TrendForBattle
import java.text.NumberFormat

class BattleResult {

    var coverRate: Double = 0.0

    val prioritySkills = mutableMapOf<String, Double>()

    var speedOccur = mutableMapOf<Int, Double>(
            0 to 0.0, 1 to 0.0, 2 to 0.0, 3 to 0.0,
            4 to 0.0, 5 to 0.0, 6 to 0.0, 7 to 0.0,
            8 to 0.0, 9 to 0.0, 10 to 0.0, 11 to 0.0
    )

    val correctionRate = arrayOf<Double>(0.0, 0.0, 0.0)

    var scarfRate = 0.0

    var orderAbilityRate = 0.0

    var defeatedTimes = mutableMapOf<String, MutableMap<Int, Double>>()

    var didAttack = false

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

    fun winRate(): String {
        val value = mayOccur[BattleStatus.Code.WIN]!!.plus(mayOccur[BattleStatus.Code.REVERSE]!!)
        if (value < 0 || 1 < value) {
            return "ERROR"
        } else if (value.minus(0.0) < 0.00001) {
            return "0%"
        } else if (value < 0.001) {
            return "極小"
        } else {
            val format = NumberFormat.getInstance()
            format.maximumFractionDigits = 1
            return format.format(value.times(100.0)) + "%"
        }
    }

    fun loseRate(): String {
        val value = mayOccur[BattleStatus.Code.DEFEAT]!!.plus(mayOccur[BattleStatus.Code.OWN_HEAD]!!)
        if (value < 0 || 1 < value) {
            return "ERROR"
        } else if (value.minus(0.0) < 0.00001) {
            return "0%"
        } else if (value < 0.001) {
            return "極小"
        } else {
            val format = NumberFormat.getInstance()
            format.maximumFractionDigits = 1
            return format.format(value.times(100.0)) + "%"
        }
    }

    fun drawRate(): String {
        val value = mayOccur[BattleStatus.Code.DRAW]!!
        if (value < 0 || 1 < value) {
            return "ERROR"
        } else if (value.minus(0.0) < 0.00001) {
            return "0%"
        } else if (value < 0.001) {
            return "極小"
        } else {
            val format = NumberFormat.getInstance()
            format.maximumFractionDigits = 1
            return format.format(value.times(100.0)) + "%"
        }
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

            for (temp in result.defeatedTimes) {
                if (defeatedTimes[temp.key] == null) {
                    defeatedTimes.put(temp.key, temp.value)
                } else {
                    val r = defeatedTimes[temp.key] as MutableMap<Int, Double>
                    r[1] = r[1]!!.plus(temp.value[1]!!)
                    r[2] = r[2]!!.plus(temp.value[2]!!)
                    r[3] = r[3]!!.plus(temp.value[3]!!)
                    r[4] = r[4]!!.plus(temp.value[4]!!)
                    r[5] = r[5]!!.plus(temp.value[5]!!)
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
                    field: BattleField, myTailWind: Boolean, oppoTailWind: Boolean): Map<String, Int> {
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

        return result
    }

    fun prioritySkill(pokemon: TrendForBattle) {
        for (ability in pokemon.tokuseiInfo) {
            for (skill in pokemon.skillList) {
                var priority = skill.skill.priority
                var key = skill.skill.jname
                var rate = 1.0
                if ((ability.name == "いたずらごころ" && skill.skill.category == 2) ||
                        (ability.name == "はやてのつばさ" && skill.skill.type == Type.no(Type.Code.FLYING)) ||
                        (ability.name == "ヒーリングシフト")) {
                    priority += 1
                    key += ":${ability.name}(${priority})"
                    rate = ability.usageRate.toDouble()
                } else {
                    key += "(${priority})"
                }
                if (0 < priority) prioritySkills[key] = rate
            }
        }
    }

    fun count(): Int {
        var sum = 0
        for (i in defeatTimes) sum += i.value.toInt()
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