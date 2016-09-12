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

    var temp: TemporaryStatus = TemporaryStatus()

    init {
        member = ArrayList<PokemonForBattle>()
    }

    fun add(pokemon: IndividualPBAPokemon): Int {
        this.member.add(PokemonForBattle.create(side, pokemon))
        return member.size
    }



    fun apply(): PokemonForBattle {
        member[selected].mega = temp.tempMega
        member[selected].status = temp.tempStatus
        member[selected].hpRatio = temp.tempHpRatio
        member[selected].hpValue = temp.tempHpValue
        member[selected].attackRank = temp.tempAttack
        member[selected].defenseRank = temp.tempDefense
        member[selected].specialAttackRank = temp.tempSpecialAttack
        member[selected].specialDefenseRank = temp.tempSpecialDefense
        member[selected].speedRank = temp.tempSpeed

        return member[selected]
    }
}
