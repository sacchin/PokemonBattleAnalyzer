package com.gmail.sacchin13.pokemonbattleanalyzer.entity

import java.util.*
import kotlin.properties.Delegates

class PartyInBattle(val side: Int = 0) {
    companion object {
        val MY_SIDE = 0
        val OPPONENT_SIDE = 1
    }

    var member: MutableList<PokemonForBattle> by Delegates.notNull()
    var selected = 0
    val rank = arrayOf(-6, -5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5, 6)

    var tempStatus: Int = StatusAilment.no(StatusAilment.Code.UNKNOWN)
    var tempHpRatio: Int = 100
    var tempHpValue: Int = 0
    var tempAttack: Int = 0
    var tempDefense: Int = 0
    var tempSpecialAttack: Int = 0
    var tempSpecialDefense: Int = 0
    var tempSpeed: Int = 0
    var tempMega: Boolean = false

    init {
        member = ArrayList<PokemonForBattle>()
    }

    fun add(pokemon: IndividualPBAPokemon): Int {
        this.member.add(PokemonForBattle.create(side, pokemon))
        return member.size
    }

    fun setAttackRank(position: Int) {
        tempAttack = rank[position]
    }

    fun setDefenseRank(position: Int) {
        tempDefense = rank[position]
    }

    fun setSpecialAttackRank(position: Int) {
        tempSpecialAttack = rank[position]
    }

    fun setSpecialDefenseRank(position: Int) {
        tempSpecialDefense = rank[position]
    }

    fun setSpeedRank(position: Int) {
        tempSpeed = rank[position]
    }


    fun apply(): PokemonForBattle {
        member[selected].mega = tempMega
        member[selected].status = tempStatus
        member[selected].hpRatio = tempHpRatio
        member[selected].hpValue = tempHpValue
        member[selected].attackRank = tempAttack
        member[selected].defenseRank = tempDefense
        member[selected].specialAttackRank = tempSpecialAttack
        member[selected].specialDefenseRank = tempSpecialDefense
        member[selected].speedRank = tempSpeed

        return member[selected]
    }
}
