package com.gmail.sacchin13.pokemonbattleanalyzer.logic

import android.os.AsyncTask
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.BattleField
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.BattleResult
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.PokemonForBattle

class GeneralCalculator(
        val mine: PokemonForBattle,
        val opponent: PokemonForBattle,
        val field: BattleField,
        val listener: EventListener
) : AsyncTask<Void, Void, BattleResult>() {

    interface EventListener {
        fun onFinish(result: BattleResult)
    }

    override fun onPostExecute(result: BattleResult) {
        listener.onFinish(result)
    }

    override fun doInBackground(vararg params: Void?): BattleResult {
        return calc()
    }

    fun calc(): BattleResult {
        val result = BattleResult()

        result.coverRate = 0.0
        result.prioritySkill(opponent.trend)
        loop@ for (item in opponent.itemTrend()) {
            if (item.name.isNullOrEmpty() || item.usageRate < 0.01) continue
            for (tokusei in opponent.abilityTrend()) {
                if (tokusei.name.isNullOrEmpty() || tokusei.usageRate < 0.01) continue
                for (seikaku in opponent.characteristicTrend()) {
                    if (seikaku.name.isNullOrEmpty() || seikaku.usageRate < 0.01) continue
                    val rate = item.usageRate.times(tokusei.usageRate.times(seikaku.usageRate)).toDouble()
                    result.coverRate = result.coverRate.plus(rate)

                    opponent.item = item.name
                    opponent.ability = tokusei.name
                    opponent.characteristic = seikaku.name

                    //Log.v("getGeneralResult", "${opponent.item}, ${opponent.ability}, ${opponent.characteristic}")
                    result.orderRate(opponent, rate)

                    if (0.9 < result.coverRate) break@loop
                }
            }
        }
        return result
    }
}
