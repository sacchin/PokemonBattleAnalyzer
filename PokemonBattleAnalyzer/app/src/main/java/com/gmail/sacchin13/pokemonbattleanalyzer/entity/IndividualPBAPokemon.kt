package com.gmail.sacchin13.pokemonbattleanalyzer.entity

import java.sql.Timestamp
import java.util.ArrayList
import java.util.Collections
import java.util.HashMap

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl.RankingPokemonTrend

open class IndividualPBAPokemon (val master : PBAPokemon?) {

    companion object {
        const val UNKNOWN_EV = -1
    }

    var id: Long = 0
    var item = ""
    var characteristic: String? = null
    var ability: String? = null
    var skillNo1 = ""
    var skillNo2 = ""
    var skillNo3 = ""
    var skillNo4 = ""
    var hpEffortValue = UNKNOWN_EV
    var attackEffortValue = UNKNOWN_EV
    var deffenceEffortValue = UNKNOWN_EV
    var specialAttackEffortValue = UNKNOWN_EV
    var specialDeffenceEffortValue = UNKNOWN_EV
    var speedEffortValue = UNKNOWN_EV

//    var trend: RankingPokemonTrend? = null

    private fun valueSort(statistics: Map<String, Int>): List<Any> {
        val entries = ArrayList(statistics.entries)
        Collections.sort(entries) { o1, o2 -> o2.value.compareTo(o1.value)}
        return entries
    }

    val hpValue: Int
        get() {
            if (hpEffortValue == UNKNOWN_EV) {
                return master!!.masterRecord.getHPValue(31, 252)
            }
            return master!!.masterRecord.getHPValue(31, hpEffortValue)
        }

    val attackValue: Int
        get() {
            if (attackEffortValue == UNKNOWN_EV) {
                return master!!.masterRecord.getAttackValue(31, 252)
            }
            return master!!.masterRecord.getAttackValue(31, attackEffortValue)
        }

    val deffenceValue: Int
        get() {
            if (deffenceEffortValue == UNKNOWN_EV) {
                return master!!.masterRecord.getDefenseValue(31, 252)
            }
            return master!!.masterRecord.getDefenseValue(31, deffenceEffortValue)
        }

    val specialAttackValue: Int
        get() {
            if (specialAttackEffortValue == UNKNOWN_EV) {
                return master!!.masterRecord.getSpecialAttackValue(31, 252)
            }
            return master!!.masterRecord.getSpecialAttackValue(31, specialAttackEffortValue)
        }

    val specialDeffenceValue: Int
        get() {
            if (specialDeffenceEffortValue == UNKNOWN_EV) {
                return master!!.masterRecord.getSpecialDefenseValue(31, 252)
            }
            return master!!.masterRecord.getSpecialDefenseValue(31, specialDeffenceEffortValue)
        }

    val speedValue: Int
        get() {
            if (speedEffortValue == UNKNOWN_EV) {
                return master!!.masterRecord.getSpeedValue(31, 252)
            }
            return master!!.masterRecord.getSpeedValue(31, speedEffortValue)
        }

    fun getSpeedValue(characteristicNo: Int): Int {
        val rate = Characteristic.CHARACTERISTIC_TABLE[characteristicNo]
        if (rate != null && 4 < rate.size) {
            return (speedValue.times(rate[4] as Float)).toInt()
        } else {
            return speedValue
        }
    }

    fun calcDamage(attackSide: IndividualPBAPokemon, skill: Skill): Map<Float, Int> {
//        val dSideCharacteristics = trend!!.characteristicList
//        val aSideCharacteristics = attackSide.trend!!.getCharacteristicList()

        val resultMap = HashMap<Float, Int>()
//        for (dc in dSideCharacteristics) {
//            val dRevision = dc.revision
//            for (ac in aSideCharacteristics) {
//                val aRevision = ac.revision
//                val rate = (dc.usageRate * ac.usageRate).toFloat()
//                var damage = 0
//                when (skill.category) {
//                    0 -> {
//                        damage = (22f * skill.power.toFloat() * attackSide.attackValue.toFloat() * aRevision[0] / deffenceValue * dRevision[1] / 50 + 2).toInt()
//                        resultMap.put(rate, damage)
//                    }
//                    1 -> {
//                        damage = (22f * skill.power.toFloat() * attackSide.specialAttackValue.toFloat() * aRevision[2] / specialDeffenceValue * dRevision[3] / 50 + 2).toInt()
//                        resultMap.put(rate, damage)
//                    }
//                    else -> {
//                    }
//                }//this is not attack
//            }
//        }
        return resultMap
    }

    override fun toString(): String {
        return "id:$id, No:$master!!.no, Name:$master!!.jname, item:$item, ability:$ability, characteristic:$characteristic, skill1$skillNo1, skill2$skillNo2, skill3$skillNo3, skill4$skillNo4, H:$hpEffortValue, A:$attackEffortValue, B:$deffenceEffortValue, C:$specialAttackEffortValue, D:$specialDeffenceEffortValue, S:$speedEffortValue"
    }


}
