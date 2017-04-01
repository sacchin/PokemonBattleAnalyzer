package com.gmail.sacchin13.pokemonbattleanalyzer.entity.ui

data class SkillForUI(
        var no: Int = -1,
        var jname: String = "unknown",
        var ename: String = "unknown",
        var type: Int = -1,
        var power: Int = -1,
        var accuracy: Double = -1.0,
        var category: Int = -1,
        var pp: Int = -1,
        var priority: Int = 0,
        var contact: Boolean = false,
        var protectable: Boolean = false,
        var aliment: Int = -1,
        var alimentRate: Double = 0.0,
        var myRankUp: Int = -1,
        var myRankUpRate: Double = 0.0,
        var oppoRankUp: Int = -1,
        var oppoRankUpRate: Double = 0.0
) {
}