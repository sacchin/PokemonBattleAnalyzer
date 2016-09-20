package com.gmail.sacchin13.pokemonbattleanalyzer.entity

import java.util.*
import kotlin.properties.Delegates

class PartyInBattle(val side: Int = 0) {
    companion object {
        val MY_SIDE = 0
        val OPPONENT_SIDE = 1
    }

    var member: MutableList<PokemonForBattle> by Delegates.notNull()
    var exposed: MutableSet<PokemonForBattle> = mutableSetOf()
    var selected = 0

    var temp: TemporaryStatus = TemporaryStatus()

    init {
        member = ArrayList<PokemonForBattle>()
    }

    fun add(pokemon: IndividualPokemon): Int {
        this.member.add(PokemonForBattle.create(side, pokemon))
        return member.size
    }

    fun add(pokemon: PokemonForBattle): Boolean {
        if (this.exposed.contains(pokemon).not()) {
            this.exposed.add(pokemon)
            return true
        }
        return false
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

    fun load(): PokemonForBattle {
        temp.tempMega = member[selected].mega
        temp.tempStatus = member[selected].status
        temp.tempHpRatio = member[selected].hpRatio
        temp.tempHpValue = member[selected].hpValue
        temp.tempAttack = member[selected].attackRank
        temp.tempDefense = member[selected].defenseRank
        temp.tempSpecialAttack = member[selected].specialAttackRank
        temp.tempSpecialDefense = member[selected].specialDefenseRank
        temp.tempSpeed = member[selected].speedRank

        return member[selected]
    }
}
