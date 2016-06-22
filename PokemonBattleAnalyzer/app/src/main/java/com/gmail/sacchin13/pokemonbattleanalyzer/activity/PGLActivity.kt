package com.gmail.sacchin13.pokemonbattleanalyzer.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.gmail.sacchin13.pokemonbattleanalyzer.DatabaseHelper
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.IndividualPBAPokemon
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.PBAPokemon
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Party
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl.RankingResponse
import com.gmail.sacchin13.pokemonbattleanalyzer.http.PokemonTrendDownloader
import kotlin.properties.Delegates

open class PGLActivity: AppCompatActivity() {
    var databaseHelper: DatabaseHelper by Delegates.notNull()
    var opponentParty: Party by Delegates.notNull()
    var myParty: Party by Delegates.notNull()

    inner class TrendListener: PokemonTrendDownloader.EventListener{
        override  fun onFinish(result: RankingResponse){
//            setTrend()
        }
    }

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
        opponentParty.addMember(IndividualPBAPokemon(databaseHelper.selectPBAPokemonData(opponentParty.member1)))
        opponentParty.addMember(IndividualPBAPokemon(databaseHelper.selectPBAPokemonData(opponentParty.member2)))
        opponentParty.addMember(IndividualPBAPokemon(databaseHelper.selectPBAPokemonData(opponentParty.member3)))
        opponentParty.addMember(IndividualPBAPokemon(databaseHelper.selectPBAPokemonData(opponentParty.member4)))
        opponentParty.addMember(IndividualPBAPokemon(databaseHelper.selectPBAPokemonData(opponentParty.member5)))
        opponentParty.addMember(IndividualPBAPokemon(databaseHelper.selectPBAPokemonData(opponentParty.member6)))

        myParty.addMember(IndividualPBAPokemon(databaseHelper.selectPBAPokemonData(myParty.member1)))
        myParty.addMember(IndividualPBAPokemon(databaseHelper.selectPBAPokemonData(myParty.member2)))
        myParty.addMember(IndividualPBAPokemon(databaseHelper.selectPBAPokemonData(myParty.member3)))
        myParty.addMember(IndividualPBAPokemon(databaseHelper.selectPBAPokemonData(myParty.member4)))
        myParty.addMember(IndividualPBAPokemon(databaseHelper.selectPBAPokemonData(myParty.member5)))
        myParty.addMember(IndividualPBAPokemon(databaseHelper.selectPBAPokemonData(myParty.member6)))

        if(downloadTrend) downloadTrend()

        showParty()
    }

    private fun downloadTrend() {
        for (i in 0..opponentParty.member.size - 1) {
            val p = opponentParty.member[i] as PBAPokemon
            val pokemonNo = p.masterRecord.no
            PokemonTrendDownloader(pokemonNo + "-0", i, TrendListener()).execute()
        }
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
    open fun setTrend(){}

    open fun showParty(){}
}
