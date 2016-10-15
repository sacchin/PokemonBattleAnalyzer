package com.gmail.sacchin13.pokemonbattleanalyzer.entity

import io.realm.RealmObject
import io.realm.annotations.RealmClass
import java.util.*

@RealmClass
open class Skill(
        open var no: Int = -1,
        open var jname: String = "unknown",
        open var ename: String = "unknown",
        open var type: Int = -1,
        open var power: Int = -1,
        open var accuracy: Double = -1.0,
        open var category: Int = -1,
        open var pp: Int = -1,
        open var priority: Int = 0,
        open var contact: Boolean = false,
        open var protectable: Boolean = false,
        open var aliment: Int = -1,
        open var alimentRate: Double = 0.0,
        open var myRankUp: Int = -1,
        open var myRankUpRate: Double = 0.0,
        open var oppoRankUp: Int = -1,
        open var oppoRankUpRate: Double = 0.0

) : RealmObject() {

    companion object {
        fun create(no: Int, jname: String, ename: String, type: Int, power: Int, accuracy: Double,
                   category: Int, pp: Int, priority: Int, contact: Boolean, protectable: Boolean,
                   aliment: Int, alimentRate: Double, myRankUp: Int, myRankUpRate: Double,
                   oppoRankUp: Int, oppoRankUpRate: Double): Skill {
            return Skill(no, jname, ename, type, power, accuracy, category, pp, priority, contact,
                    protectable, aliment, alimentRate, myRankUp, myRankUpRate, oppoRankUp, oppoRankUpRate)
        }

        fun skillNameList(list: MutableList<Skill>): MutableList<String> {
            val result = ArrayList<String>()
            for (temp in list) {
                result.add(temp.jname)
            }
            return result
        }
    }

    override fun toString(): String {
        return "${no},${jname},${ename},${type},${power},${accuracy},${category},${pp},${priority},${contact},${protectable}"
    }
}

