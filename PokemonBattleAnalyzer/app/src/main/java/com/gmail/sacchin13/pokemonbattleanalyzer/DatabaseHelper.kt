package com.gmail.sacchin13.pokemonbattleanalyzer

import android.content.Context
import android.util.Log
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.*
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.Sort
import org.json.JSONObject
import java.util.*

class DatabaseHelper (context: Context){

    val util = Util()
    var realm: Realm? = null

    init{
        val realmConfig = RealmConfiguration.Builder(context).build()
        realm = Realm.getInstance(realmConfig);
    }

    fun insertPokemonMasterData(no: String, jname: String, ename: String, h: Int, a: Int, b: Int, c: Int, d: Int, s: Int,
                             ability1: String, ability2: String, abilityd: String, type1: Int, type2: Int, weight: Double) {

        realm!!.executeTransaction{
            val pokemon = realm!!.createObject(PokemonMasterData::class.java)
            pokemon.no = no
            pokemon.jname = jname
            pokemon.ename = ename
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

    }

    fun selectPokemonMasterData(pokemonNo: String?): PokemonMasterData {
        val pokemon = realm!!.where(PokemonMasterData().javaClass).equalTo("no", pokemonNo).findFirst()
        if(pokemon != null) return pokemon
        return PokemonMasterData()
    }

    fun selectAllPBAPokemon(): ArrayList<PBAPokemon> {
        val pokemonList = realm!!.where(PokemonMasterData().javaClass).findAllSorted("no", Sort.DESCENDING)

        val result = ArrayList<PBAPokemon>()
        for(p in pokemonList){
            if(util.pokemonImageResource[p.no] != null) {
                result.add(PBAPokemon(util.pokemonImageResource[p.no], 0, p, 0))
            }
        }
        return result
    }

    fun insertItemMaterData(name: String) {
        realm!!.executeTransaction{
            val item = realm!!.createObject(ItemMasterData::class.java)
            item.name = name
        }
    }

    fun insertSkillMasterData(name: String?, type: Int, power: Int, accuracy: Int, category: Int, pp: Int, contact: Boolean, protectable: Boolean) {
        realm!!.executeTransaction{
            val skill = realm!!.createObject(Skill::class.java)
            skill.jname = name
            skill.ename = name
            skill.type = type
            skill.power = power
            skill.accuracy = accuracy
            skill.category = category
            skill.pp = pp
            skill.contact = contact
            skill.protectable = protectable
        }
    }

    fun insertMegaPBAPokemonData(megaPBAPokemon: JSONObject?) {
        realm!!.executeTransaction{
            val megaPokemon = realm!!.createObject(MegaPokemonMasterData::class.java)
            megaPokemon.h = megaPBAPokemon!!.getInt("h")
            megaPokemon.a = megaPBAPokemon.getInt("a")
            megaPokemon.b = megaPBAPokemon.getInt("b")
            megaPokemon.c = megaPBAPokemon.getInt("c")
            megaPokemon.d = megaPBAPokemon.getInt("d")
            megaPokemon.s = megaPBAPokemon.getInt("s")
            megaPokemon.characteristic = megaPBAPokemon.getString("characteristic")
            megaPokemon.megaType = megaPBAPokemon.getString("megaType")
        }
    }

    fun insertPartyData(selectedParty: Party?) {
        realm!!.executeTransaction{
            val party = realm!!.createObject(Party::class.java)
            party.memo = selectedParty!!.memo
            party.member1 = selectedParty.member[0]!!.masterRecord.no
            party.member2 = selectedParty.member[1]!!.masterRecord.no
            party.member3 = selectedParty.member[2]!!.masterRecord.no
            party.member4 = selectedParty.member[3]!!.masterRecord.no
            party.member5 = selectedParty.member[4]!!.masterRecord.no
            party.member6 = selectedParty.member[5]!!.masterRecord.no
            party.userName = selectedParty.userName
        }
    }


    fun selectOpponentParty(): Party {
        val party = realm!!.where(Party().javaClass).findAll()[0]
        if(party == null){
            Log.e("selectOpponentParty", "party is null")
        }

        val result = ArrayList<PBAPokemon>()
        val member1 = selectPokemonMasterData(party!!.member1)
        result.add(PBAPokemon(util.pokemonImageResource[member1.no], 0, member1, 0))
        val member2 = selectPokemonMasterData(party.member2)
        result.add(PBAPokemon(util.pokemonImageResource[member2.no], 0, member2, 0))
        val member3 = selectPokemonMasterData(party.member3)
        result.add(PBAPokemon(util.pokemonImageResource[member3.no], 0, member3, 0))
        val member4 = selectPokemonMasterData(party.member4)
        result.add(PBAPokemon(util.pokemonImageResource[member4.no], 0, member4, 0))
        val member5 = selectPokemonMasterData(party.member5)
        result.add(PBAPokemon(util.pokemonImageResource[member5.no], 0, member5, 0))
        val member6 = selectPokemonMasterData(party.member6)
        result.add(PBAPokemon(util.pokemonImageResource[member6.no], 0, member6, 0))

        return party
    }
}
