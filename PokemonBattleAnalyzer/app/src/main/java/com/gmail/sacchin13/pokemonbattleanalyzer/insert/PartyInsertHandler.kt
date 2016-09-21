package com.gmail.sacchin13.pokemonbattleanalyzer.insert

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Party

import android.util.Log
import com.gmail.sacchin13.pokemonbattleanalyzer.DatabaseHelper

class PartyInsertHandler(private val databaseHelper: DatabaseHelper?) {

    fun initInsert() {
        val party = Party(System.currentTimeMillis(), "mine", "mine")
        val list = databaseHelper!!.selectAllPokemonMasterData()
        party.addMember(list[220])  //クチート
        party.addMember(list[292])  //バクフーン
        party.addMember(list[268])  //ハガネール
        party.addMember(list[89])   //エルフーン
        party.addMember(list[85])  //ヒヒダルマ
        party.addMember(list[2])  //パンプジン
        databaseHelper.insertPartyData(party)

        databaseHelper.updateMyParty()
        Log.i("PartyInsertHandler", "パーティーを登録しました。")
    }

    fun insertOneParty(party: Party) {
        databaseHelper!!.insertPartyData(party)
    }
}
