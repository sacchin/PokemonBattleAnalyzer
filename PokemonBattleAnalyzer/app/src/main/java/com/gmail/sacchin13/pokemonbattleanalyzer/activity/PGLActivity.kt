package com.gmail.sacchin13.pokemonbattleanalyzer.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.gmail.sacchin13.pokemonbattleanalyzer.DatabaseHelper
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.realm.Party
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl.TrendForBattle
import com.gmail.sacchin13.pokemonbattleanalyzer.http.PokemonTrendDownloader
import kotlin.properties.Delegates

open class PGLActivity : AppCompatActivity() {
    var databaseHelper: DatabaseHelper by Delegates.notNull()
    var opponentParty: Party by Delegates.notNull()
    var myParty: Party by Delegates.notNull()
    var finishCount = 0

    inner class TrendListener : PokemonTrendDownloader.EventListener {
        override fun onFinish(result: TrendForBattle, index: Int) {
            setTrend(result, index)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databaseHelper = DatabaseHelper(this)
    }

    protected fun resetParty(downloadTrend: Boolean) {
        opponentParty = databaseHelper.selectParty("opponent")
        opponentParty.initMember()
        myParty = databaseHelper.selectParty("mine")
        myParty.initMember()

        if (downloadTrend) downloadTrend()

        showParty()
    }

    private fun downloadTrend() {
        finishCount = 0
        for ((i, value) in opponentParty.member.withIndex()) {
            if (value.no.contains("-")) {
                PokemonTrendDownloader(value.no, i, TrendListener()).execute()
            } else {
                val pokemonNo = "${value.no}-0"
                PokemonTrendDownloader(pokemonNo, i, TrendListener()).execute()
            }
        }
    }

    open fun setTrend(result: TrendForBattle, index: Int) {
        Log.w("PGLActivity.setTrend", "don't override!")
    }

    open fun showParty() {
        Log.w("PGLActivity.showParty", "don't override!")
    }
}
