package com.gmail.sacchin13.pokemonbattleanalyzer

import android.content.Context
import android.util.Log
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.*
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.Sort
import org.json.JSONObject
import java.util.*
import kotlin.properties.Delegates

class DatabaseHelper (context: Context){

    val util = Util()
    var realm: Realm by Delegates.notNull()

    init{
        val realmConfig = RealmConfiguration.Builder(context).build()
        realm = Realm.getInstance(realmConfig);
    }

    fun begin(){
        realm.beginTransaction()
    }

    fun commit(){
        realm.commitTransaction()
    }


    fun insertPokemonMasterData(no: String, jname: String, ename: String, h: Int, a: Int, b: Int, c: Int, d: Int, s: Int,
                             ability1: String, ability2: String, abilityd: String, type1: Int, type2: Int, weight: Double) {

        realm.executeTransaction{
            val pokemon = realm.createObject(PokemonMasterData::class.java)
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
        val pokemon = realm.where(PokemonMasterData().javaClass).equalTo("no", pokemonNo).findFirst()
        if(pokemon != null) {
            return pokemon
        }
        return realm.where(PokemonMasterData().javaClass).equalTo("no", "000").findFirst()
    }

    fun selectAllPokemonMasterData(): ArrayList<PokemonMasterData> {
        val pokemonList = realm.where(PokemonMasterData().javaClass).findAllSorted("no", Sort.DESCENDING)

        val result = ArrayList<PokemonMasterData>()
        for(p in pokemonList){
            if(util.pokemonImageResource.contains(p.no)){
                result.add(p)
            }
        }
        return result
    }

    fun insertItemMaterData(name: String) {
        realm.executeTransaction{
            val item = realm.createObject(ItemMasterData::class.java)
            item.name = name
        }
    }

    fun insertSkillMasterData(id: Int, name: String, type: Int, power: Int, accuracy: Int, category: Int, pp: Int, contact: Boolean, protectable: Boolean) {
        insertSkillMasterData(id, name, type, power, accuracy, category, pp, 0, contact, protectable)
    }


    fun insertSkillMasterData(id: Int, name: String, type: Int, power: Int, accuracy: Int, category: Int, pp: Int, priority: Int, contact: Boolean, protectable: Boolean) {
        realm.executeTransaction{
            val skill = realm.createObject(Skill::class.java)
            skill.no = id
            skill.jname = name
            skill.ename = name
            skill.type = type
            skill.power = power
            skill.accuracy = accuracy
            skill.category = category
            skill.pp = pp
            skill.priority = priority
            skill.contact = contact
            skill.protectable = protectable
        }
    }

    fun insertMegaPBAPokemonData(megaPBAPokemon: JSONObject?) {
        realm.executeTransaction{
            val megaPokemon = realm.createObject(MegaPokemonMasterData::class.java)
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

    fun selectSkillByName(name: String): Skill{
        return realm.where(Skill().javaClass).equalTo("jname", name).findFirst()
    }

    fun selectUnknownSkill(): Skill{
        return realm.where(Skill().javaClass).equalTo("no", -1).findFirst()
    }

    fun countIndividualPBAPokemon(): Long{
        return realm.where(IndividualPBAPokemon().javaClass).count()
    }

    fun insertPartyData(selectedParty: Party?) {
        val count = countIndividualPBAPokemon()
        realm.executeTransaction{
            val party = realm.createObject(Party::class.java)
            party.time = System.currentTimeMillis()
            party.memo = selectedParty!!.memo
            party.userName = selectedParty.userName
            insertIndividualPBAPokemon(count + 1, selectedParty.member[0])
            party.member1 = selectIndividualPBAPokemon(count + 1)
            insertIndividualPBAPokemon(count + 2, selectedParty.member[1])
            party.member2 = selectIndividualPBAPokemon(count + 2)
            insertIndividualPBAPokemon(count + 3, selectedParty.member[2])
            party.member3 = selectIndividualPBAPokemon(count + 3)
            insertIndividualPBAPokemon(count + 4, selectedParty.member[3])
            party.member4 = selectIndividualPBAPokemon(count + 4)
            insertIndividualPBAPokemon(count + 5, selectedParty.member[4])
            party.member5 = selectIndividualPBAPokemon(count + 5)
            insertIndividualPBAPokemon(count + 6, selectedParty.member[5])
            party.member6 = selectIndividualPBAPokemon(count + 6)
        }
    }

    fun insertIndividualPBAPokemon(id: Long, master: PokemonMasterData){
        val pokemon = realm.createObject(IndividualPBAPokemon::class.java)
        pokemon.id = id
        pokemon.item = ""
        pokemon.characteristic = ""
        pokemon.ability = ""
        pokemon.skillNo1 = selectUnknownSkill()
        pokemon.skillNo2 = selectUnknownSkill()
        pokemon.skillNo3 = selectUnknownSkill()
        pokemon.skillNo4 = selectUnknownSkill()
        pokemon.hpRatio = 100
        pokemon.master = selectPokemonMasterData(master.no)
    }

    fun selectIndividualPBAPokemon(id: Long): IndividualPBAPokemon{
        return realm.where(IndividualPBAPokemon().javaClass).equalTo("id", id).findFirst()
    }

    fun selectParty(userName: String): Party {
        val party = realm.where(Party().javaClass).equalTo("userName", userName).findAllSorted("time", Sort.DESCENDING)
        if(party == null || party.size < 1){
            Log.e("selectParty", "party is not found")
            return Party()
        }

        Log.v("userName", "${party[0].member1}")
        Log.v("userName", "${party[0].member2}")
        Log.v("userName", "${party[0].member3}")
        Log.v("userName", "${party[0].member4}")
        Log.v("userName", "${party[0].member5}")
        Log.v("userName", "${party[0].member6}")
        return party[0]
    }

   fun selectAllSkill(): MutableList<Skill?> {
        val skill = realm.where(Skill().javaClass).findAllSorted("jname", Sort.ASCENDING)
        return skill.toMutableList()
    }

    fun selectAllItem(): ArrayList<String> {
        val item = realm.where(ItemMasterData().javaClass).findAllSorted("name", Sort.ASCENDING)
        val list = ArrayList<String>()
        for(temp in item){
            list.add(temp.name)
        }
        return list
    }
}
