package com.gmail.sacchin13.pokemonbattleanalyzer.entity

class BattleResult {
    var mayOccur = mutableMapOf<BattleStatus.Code, Double>(
            BattleStatus.Code.WIN to 0.0,
            BattleStatus.Code.DEFEAT to 0.0,
            BattleStatus.Code.REVERSE to 0.0,
            BattleStatus.Code.OWN_HEAD to 0.0,
            BattleStatus.Code.DRAW to 0.0
    )

    var skillDetail = mutableMapOf<BattleStatus.Code, MutableSet<String>>(
            BattleStatus.Code.WIN to mutableSetOf<String>(),
            BattleStatus.Code.DEFEAT to mutableSetOf<String>(),
            BattleStatus.Code.REVERSE to mutableSetOf<String>(),
            BattleStatus.Code.OWN_HEAD to mutableSetOf<String>(),
            BattleStatus.Code.DRAW to mutableSetOf<String>()
    )


    fun updateWinDefeat(first: PokemonForBattle, second: PokemonForBattle, rate: Double) {
        when (second.side) {
            PartyInBattle.MY_SIDE -> {
                mayOccur[BattleStatus.Code.DEFEAT] = mayOccur[BattleStatus.Code.DEFEAT]!!.plus(rate)
                skillDetail[BattleStatus.Code.DEFEAT]!!.add(first.skill.jname)
            }
            PartyInBattle.OPPONENT_SIDE -> {
                mayOccur[BattleStatus.Code.WIN] = mayOccur[BattleStatus.Code.WIN]!!.plus(rate)
                skillDetail[BattleStatus.Code.WIN]!!.add(first.skill.jname)
            }
        }
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

    }

}