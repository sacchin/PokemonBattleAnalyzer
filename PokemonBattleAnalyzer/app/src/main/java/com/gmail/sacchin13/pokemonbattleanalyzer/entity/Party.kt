package com.gmail.sacchin13.pokemonbattleanalyzer.entity

import java.sql.Timestamp
import java.util.ArrayList

class Party (time: Timestamp, member1: IndividualPBAPokemon?, member2: IndividualPBAPokemon?,
             member3: IndividualPBAPokemon?, member4: IndividualPBAPokemon?, member5: IndividualPBAPokemon?,
             member6: IndividualPBAPokemon?, memo: String, userName: String){
    var time: Timestamp? = null
    var member: MutableList<IndividualPBAPokemon>? = null

    var userName: String? = null
    var memo: String? = null

    init {
        this.member = ArrayList<IndividualPBAPokemon>()
    }

    fun setMember(pokemon: IndividualPBAPokemon): Int {
        if (this.member != null && this.member!!.size < 6) {
            this.member!!.add(pokemon)
            return this.member!!.size - 1
        }
        return -1
    }

    fun removeMember(PBAPokemon: PBAPokemon?): Int {
        var index = -1
        if (this.member != null && PBAPokemon != null) {
            for (i in this.member!!.indices) {
                if (PBAPokemon.masterRecord.no == this.member!![i].master!!.masterRecord.no) {
                    index = i
                }
            }
            this.member!!.removeAt(index)
            return index
        }
        return -1
    }

    fun clear() {
        if (member != null) {
            member!!.clear()
        }
    }
}
