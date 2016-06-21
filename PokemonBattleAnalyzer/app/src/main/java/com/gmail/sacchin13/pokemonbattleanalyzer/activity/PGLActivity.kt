package com.gmail.sacchin13.pokemonbattleanalyzer.activity

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.gmail.sacchin13.pokemonbattleanalyzer.DatabaseHelper
import com.gmail.sacchin13.pokemonbattleanalyzer.PartyDatabaseHelper
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.IndividualPBAPokemon
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Party
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl.RankingPokemonTrend
import com.gmail.sacchin13.pokemonbattleanalyzer.http.PokemonTrendDownloader
import java.io.IOException
import kotlin.properties.Delegates

open class PGLActivity: AppCompatActivity() {
    var databaseHelper: DatabaseHelper by Delegates.notNull()
    var opponentParty: Party by Delegates.notNull()
    var myParty: Party by Delegates.notNull()

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databaseHelper = DatabaseHelper(this)
    }

    protected fun resetParty(downloadTrend: Boolean) {
        myParty = databaseHelper.selectOpponentParty()
        opponentParty = databaseHelper.selectOpponentParty()
    }

    private fun downloadTrend() {
//        val handler = Handler()
//        for (i in 0..party.getMember().size() - 1) {
//            val p = party.getMember().get(i)
//            var pokemonNo = p.master!!.masterRecord.no
//            if (!pokemonNo.contains("-")) {
//                try {
//                    pokemonNo = Integer.parseInt(p.master!!.masterRecord.no).toString() + "-0"
//                } catch (e: NumberFormatException) {
//                    e.printStackTrace()
//                }
//
//            }
//            executorService.execute(
//                    PokemonTrendDownloader(pokemonNo, this, i, handler))
//        }
    }

//    fun getIndividualPBAPokemon(index: Int): IndividualPBAPokemon {
//        return party.getMember().get(index)
//    }
//
//    fun finishDownload(index: Int, trend: RankingPokemonTrend) {
//        if (party == null || party.getMember() == null || party.getMember().size() < index) {
//            return
//        }
//        //party.getMember().get(index).getMaster().getMasterRecord().setTrend(trend);
//    }
//
//    abstract fun finishAllDownload()
//    abstract fun setTrend()
}
