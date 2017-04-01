package com.gmail.sacchin13.pokemonbattleanalyzer.insert

import android.util.Log
import com.gmail.sacchin13.pokemonbattleanalyzer.DatabaseHelper
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.realm.Party

class PartyInsertHandler(private val databaseHelper: DatabaseHelper?) {

    fun initInsert() {
        val party = Party(System.currentTimeMillis(), "mine", "mine")
        val l = databaseHelper!!.selectPokemonByName("サザンドラ")
        party.addMember(l)
        val b = databaseHelper.selectPokemonByName("ジバコイル")
        party.addMember(b)
        val k = databaseHelper.selectPokemonByName("ガルーラ")
        party.addMember(k)
        val c = databaseHelper.selectPokemonByName("ドヒドイデ")
        party.addMember(c)
        val s = databaseHelper.selectPokemonByName("ファイアロー")
        party.addMember(s)
        val j = databaseHelper.selectPokemonByName("ガブリアス")
        party.addMember(j)

        databaseHelper.insertPartyData(party)

        databaseHelper.updateMyParty()
        Log.i("PartyInsertHandler", "パーティーを登録しました。")
    }

    fun insertOneParty(party: Party) {
        databaseHelper!!.insertPartyData(party)
    }
}
