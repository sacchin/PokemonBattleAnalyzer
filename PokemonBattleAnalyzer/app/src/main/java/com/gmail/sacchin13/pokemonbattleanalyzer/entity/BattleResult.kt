package com.gmail.sacchin13.pokemonbattleanalyzer.entity

class BattleResult {
    var mayOccur = mutableMapOf(
            BattleStatus.Code.WIN to 0.0,
            BattleStatus.Code.DEFEAT to 0.0,
            BattleStatus.Code.REVERSE to 0.0,
            BattleStatus.Code.OWN_HEAD to 0.0,
            BattleStatus.Code.DRAW to 0.0
    )


}