package com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl

data class Info(
        var ranking: Int = 0,
        var usageRate: Float = 0.0f,
        var name: String = "none",
        var sequenceNumber: Int = 0
) {
    fun convertToFew() {
        usageRate = usageRate.div(100.0).toFloat()
    }
}