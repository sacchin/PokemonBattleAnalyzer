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
    var field: MutableSet<BattleField.Field> = mutableSetOf()
    var selected = 0

    var temp: TemporaryStatus = TemporaryStatus()

    init {
        member = ArrayList<PokemonForBattle>()
    }

    fun add(item: BattleField.Field) {
        field.add(item)
    }

    fun remove(item: BattleField.Field) {
        field.remove(item)
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
        if(temp.tempItem == 1) member[selected].item = PokemonForBattle.NOT_USED
        member[selected].status = temp.tempStatus
        member[selected].hpRatio = temp.tempHpRatio
        member[selected].hpValue = temp.tempHpValue
        member[selected].attackRank = temp.tempAttack
        member[selected].defenseRank = temp.tempDefense
        member[selected].specialAttackRank = temp.tempSpecialAttack
        member[selected].specialDefenseRank = temp.tempSpecialDefense
        member[selected].speedRank = temp.tempSpeed
        member[selected].hitProbabilityRank = temp.tempHitProbability
        member[selected].avoidanceRank = temp.tempAvoidance
        member[selected].criticalRank = temp.tempCritical

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
        temp.tempHitProbability = member[selected].hitProbabilityRank
        temp.tempAvoidance = member[selected].avoidanceRank
        temp.tempCritical = member[selected].criticalRank

        return member[selected]
    }

    fun resetWithChange(): PokemonForBattle {
        member[selected].attackRank = 6
        member[selected].defenseRank = 6
        member[selected].specialAttackRank = 6
        member[selected].specialDefenseRank = 6
        member[selected].speedRank = 6
        member[selected].hitProbabilityRank = 6
        member[selected].avoidanceRank = 6
        member[selected].criticalRank = 6

        return member[selected]
    }

}
