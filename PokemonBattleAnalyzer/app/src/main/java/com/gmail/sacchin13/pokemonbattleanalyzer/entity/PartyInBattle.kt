package com.gmail.sacchin13.pokemonbattleanalyzer.entity

import java.util.*
import kotlin.properties.Delegates

class PartyInBattle{
    var member: MutableList<IndividualPBAPokemon> by Delegates.notNull()
    var selected = 0
    val rank = arrayOf(-6,-5,-4,-3,-2,-1,0,1,2,3,4,5,6)

    init{
        member = ArrayList<IndividualPBAPokemon>()
    }

    fun setMember(list: MutableList<IndividualPBAPokemon?>): Int{
        for(temp in list){
            if(temp!!.master.resourceId != 0) this.member.add(temp)
        }
        return list.size
    }

    fun setHPRatio(ratio: Int){
        member[selected].hpRatio = ratio
    }

    fun setStatus(position: Int){
        member[selected].status = position
    }

    fun setAttackRank(position: Int){
        member[selected].attackRank = rank[position]
    }

    fun setDefenseRank(position: Int){
        member[selected].defenseRank = rank[position]
    }

    fun setSpecialAttackRank(position: Int){
        member[selected].specialAttackRank = rank[position]
    }

    fun setSpecialDefenseRank(position: Int){
        member[selected].specialDefenseRank = rank[position]
    }

    fun setSpeedRank(position: Int){
        member[selected].speedRank = rank[position]
    }
}
