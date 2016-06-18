package com.gmail.sacchin13.pokemonbattleanalyzer

import android.content.ContentValues
import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteException
import android.provider.BaseColumns
import android.util.Log
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.PBAPokemon
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Pokemon
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Type
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.Sort
import java.io.IOException
import java.util.*

class DatabaseHelper (context: Context){

    val util = Util()
    var realm: Realm? = null

    init{
        val realmConfig = RealmConfiguration.Builder(context).build()
        realm = Realm.getInstance(realmConfig);
    }

    fun insertPBAPokemonData(no: String, jname: String, ename: String, h: Int, a: Int, b: Int, c: Int, d: Int, s: Int,
                             ability1: String, ability2: String, abilityd: String, type1: Int, type2: Int, weight: Double) {

        realm!!.executeTransaction(Realm.Transaction() {
            fun execute(realm: Realm) {
                val pokemon = realm.createObject(Pokemon().javaClass)
                pokemon.no = ""
                pokemon.jname = ""
                pokemon.ename = ""
                pokemon.h = h
                pokemon.a = a
                pokemon.b = b
                pokemon.c = c
                pokemon.d = d
                pokemon.s = s
                pokemon.ability1 = ability1
                pokemon.ability2 = ability2
                pokemon.abilityd = abilityd
                pokemon.type1 = type1
                pokemon.type2 = type2
                pokemon.weight = weight.toFloat()
            }
        });
    }


    fun selectPBAPokemon(pokemonNo: String): Pokemon {
        val pokemon = realm!!.where(Pokemon().javaClass).equalTo("no", pokemonNo).findFirst()
        if(pokemon != null) pokemon

        return Pokemon()
    }


    fun selectAllPBAPokemon(): ArrayList<PBAPokemon> {
        val pokemonList = realm!!.where(Pokemon().javaClass).findAllSorted("no", Sort.DESCENDING)

        val result = ArrayList<PBAPokemon>()
        for(p in pokemonList){
            val resourceId = util.pokemonImageResource!![p.no] as Int
            val pbaPokemon = PBAPokemon(resourceId, 0, p, 0)

            result.add(pbaPokemon)
        }

        return result
    }


}
