package com.gmail.sacchin13.pokemonbattleanalyzer.insert

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Party

import android.util.Log
import com.gmail.sacchin13.pokemonbattleanalyzer.DatabaseHelper

class PartyInsertHandler(private val databaseHelper: DatabaseHelper?) {

    fun initInsert() {
        val party = Party(System.currentTimeMillis(), "mine", "mine")
        val c = databaseHelper!!.selectPokemonByName("クチート")
        party.addMember(c)
        val l = databaseHelper.selectPokemonByName("レジロック")
        party.addMember(l)
        val j = databaseHelper.selectPokemonByName("ジャローダ")
        party.addMember(j)
        val b = databaseHelper.selectPokemonByName("ボーマンダ")
        party.addMember(b)
        val s = databaseHelper.selectPokemonByName("サメハダー")
        party.addMember(s)
        val k = databaseHelper.selectPokemonByName("カバルドン")
        party.addMember(k)

        databaseHelper.insertPartyData(party)

        databaseHelper.updateMyParty()
        Log.i("PartyInsertHandler", "パーティーを登録しました。")
    }

    fun insertOneParty(party: Party) {
        databaseHelper!!.insertPartyData(party)
    }
}
