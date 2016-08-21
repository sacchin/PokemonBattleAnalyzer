package com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl

data class WazaInfo(
        val ranking: Int,
        var usageRate: Float,
        val name: String,
        val sequenceNumber: Int,
        val typeId: Int) {

    fun convertToFew() {
        usageRate = usageRate.div(100.0).toFloat()
    }
}