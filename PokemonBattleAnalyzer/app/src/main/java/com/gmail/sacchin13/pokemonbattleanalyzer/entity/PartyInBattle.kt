package com.gmail.sacchin13.pokemonbattleanalyzer.entity

import java.util.*
import kotlin.properties.Delegates

class PartyInBattle{
    var member: MutableList<IndividualPBAPokemon> by Delegates.notNull()
    var selected = 0
    val rank = arrayOf(-6,-5,-4,-3,-2,-1,0,1,2,3,4,5,6)

    var tempStatus: Int = 0
    var tempHpRatio: Int = 0
    var tempAttack: Int = 0
    var tempDefense: Int = 0
    var tempSpecialAttack: Int = 0
    var tempSpecialDefense: Int = 0
    var tempSpeed: Int = 0

    init{
        member = ArrayList<IndividualPBAPokemon>()
    }

    fun add(pokemon: IndividualPBAPokemon): Int{
        this.member.add(pokemon)
        return member.size
    }

    fun setHPRatio(ratio: Int){
        tempHpRatio = ratio
    }

    fun setStatus(position: Int){
        tempStatus = position
    }

    fun setAttackRank(position: Int){
        tempAttack = rank[position]
    }

    fun setDefenseRank(position: Int){
        tempDefense = rank[position]
    }

    fun setSpecialAttackRank(position: Int){
        tempSpecialAttack = rank[position]
    }

    fun setSpecialDefenseRank(position: Int){
        tempSpecialDefense = rank[position]
    }

    fun setSpeedRank(position: Int){
        tempSpeed = rank[position]
    }


    fun apply(){
        if(selected < member.size){
            member[selected].hpRatio = tempHpRatio
//            member[selected].attackRank = tempAttack
//            member[selected].defenseRank = tempDefense
//            member[selected].specialAttackRank = tempSpecialAttack
//            member[selected].specialDefenseRank = tempSpecialDefense
//            member[selected].speedRank = tempSpeed
        }
    }
}