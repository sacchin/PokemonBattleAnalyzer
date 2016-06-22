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
        opponentParty.addMember(IndividualPBAPokemon(PBAPokemon(0, 0,databaseHelper.selectPokemonMasterData(opponentParty.member1), 0)))
        opponentParty.addMember(IndividualPBAPokemon(PBAPokemon(0, 0,databaseHelper.selectPokemonMasterData(opponentParty.member2), 0)))
        opponentParty.addMember(IndividualPBAPokemon(PBAPokemon(0, 0,databaseHelper.selectPokemonMasterData(opponentParty.member3), 0)))
        opponentParty.addMember(IndividualPBAPokemon(PBAPokemon(0, 0,databaseHelper.selectPokemonMasterData(opponentParty.member4), 0)))
        opponentParty.addMember(IndividualPBAPokemon(PBAPokemon(0, 0,databaseHelper.selectPokemonMasterData(opponentParty.member5), 0)))
        opponentParty.addMember(IndividualPBAPokemon(PBAPokemon(0, 0,databaseHelper.selectPokemonMasterData(opponentParty.member6), 0)))

        myParty.addMember(IndividualPBAPokemon(PBAPokemon(0, 0, databaseHelper.selectPokemonMasterData(myParty.member1), 0)))
        myParty.addMember(IndividualPBAPokemon(PBAPokemon(0, 0, databaseHelper.selectPokemonMasterData(myParty.member2), 0)))
        myParty.addMember(IndividualPBAPokemon(PBAPokemon(0, 0, databaseHelper.selectPokemonMasterData(myParty.member3), 0)))
        myParty.addMember(IndividualPBAPokemon(PBAPokemon(0, 0, databaseHelper.selectPokemonMasterData(myParty.member4), 0)))
        myParty.addMember(IndividualPBAPokemon(PBAPokemon(0, 0, databaseHelper.selectPokemonMasterData(myParty.member5), 0)))
        myParty.addMember(IndividualPBAPokemon(PBAPokemon(0, 0, databaseHelper.selectPokemonMasterData(myParty.member6), 0)))

        if(downloadTrend) downloadTrend()

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
}
