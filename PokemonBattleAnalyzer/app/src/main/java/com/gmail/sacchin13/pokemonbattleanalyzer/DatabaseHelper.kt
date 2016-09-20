package com.gmail.sacchin13.pokemonbattleanalyzer

import android.content.Context
import android.util.Log
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.*
import com.gmail.sacchin13.pokemonbattleanalyzer.util.Util
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.Sort
import java.util.*
import kotlin.properties.Delegates

class DatabaseHelper(context: Context) {
    val util = Util()
    var realm: Realm by Delegates.notNull()

    init {
        val realmConfig = RealmConfiguration.Builder(context).build()
        realm = Realm.getInstance(realmConfig);
    }

    fun begin() {
        realm.beginTransaction()
    }

    fun commit() {
        realm.commitTransaction()
    }

    fun insertPokemonMasterData(no: String, jname: String, ename: String, h: Int, a: Int, b: Int, c: Int, d: Int, s: Int,
                                ability1: String, ability2: String, abilityd: String, type1: Int, type2: Int, weight: Double) {
        insertPokemonMasterData(no, jname, ename, "-", h, a, b, c, d, s, ability1, ability2, abilityd, type1, type2, weight)
    }

    fun insertPokemonMasterData(no: String, jname: String, ename: String, form: String, h: Int, a: Int, b: Int, c: Int, d: Int, s: Int,
                                ability1: String, ability2: String, abilityd: String, type1: Int, type2: Int, weight: Double) {
        realm.executeTransaction {
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
        if (pokemon != null) {
            return pokemon
        }
        return realm.where(PokemonMasterData().javaClass).equalTo("no", "000").findFirst()
    }

    fun selectAllPokemonMasterData(): ArrayList<PokemonMasterData> {
        val pokemonList = realm.where(PokemonMasterData().javaClass).findAllSorted("no", Sort.DESCENDING)

        val result = ArrayList<PokemonMasterData>()
        for (p in pokemonList) {
            if (util.pokemonImageResource.contains(p.no)) {
                result.add(p)
            }
        }
        return result
    }

    fun insertItemMaterData(name: String) {
        realm.executeTransaction {
            val item = realm.createObject(ItemMasterData::class.java)
            item.name = name
        }
    }

    fun insertSkillMasterData(id: Int, name: String, type: Int, power: Int, accuracy: Int, category: Int, pp: Int, contact: Boolean, protectable: Boolean,
                              aliment: Int, alimentRate: Double, myRankUp: Int, myRankUpRate: Double, oppoRankUp: Int, oppoRankUpRate: Double) {
        insertSkillMasterData(id, name, type, power, accuracy, category, pp, 0, contact, protectable, aliment, alimentRate, myRankUp, myRankUpRate, oppoRankUp, oppoRankUpRate)
    }


    fun insertSkillMasterData(id: Int, name: String, type: Int, power: Int, accuracy: Int, category: Int, pp: Int, priority: Int,
                              contact: Boolean, protectable: Boolean, aliment: Int, alimentRate: Double, myRankUp: Int, myRankUpRate: Double, oppoRankUp: Int, oppoRankUpRate: Double) {
        realm.executeTransaction {
            val skill = realm.createObject(Skill::class.java)
            skill.no = id
            skill.jname = name
            skill.ename = name
            skill.type = type
            skill.power = power
            skill.accuracy = accuracy.div(100.0)
            skill.category = category
            skill.pp = pp
            skill.priority = priority
            skill.contact = contact
            skill.protectable = protectable
            skill.aliment = aliment
            skill.alimentRate = alimentRate.div(100.0)
            skill.myRankUp = myRankUp
            skill.myRankUpRate = myRankUpRate.div(100.0)
            skill.oppoRankUp = oppoRankUp
            skill.oppoRankUpRate = oppoRankUpRate.div(100.0)
        }
    }

    fun insertMegaPokemonDataX(no: String, h: Int, a: Int, b: Int, c: Int, d: Int, s: Int, ability: String, weight: Float) {
        realm.executeTransaction {
            val megaPokemon = realm.createObject(MegaPokemonMasterData::class.java)
            megaPokemon.pokemonNo = no
            megaPokemon.h = h
            megaPokemon.a = a
            megaPokemon.b = b
            megaPokemon.c = c
            megaPokemon.d = d
            megaPokemon.s = s
            megaPokemon.ability = ability
            megaPokemon.weight = weight
            megaPokemon.megaType = MegaPokemonMasterData.MEGA_X

            val master = selectPokemonMasterData(no)
            master.megax = megaPokemon
        }
    }

    fun insertMegaPokemonDataY(no: String, h: Int, a: Int, b: Int, c: Int, d: Int, s: Int, ability: String, weight: Float) {
        realm.executeTransaction {
            val megaPokemon = realm.createObject(MegaPokemonMasterData::class.java)
            megaPokemon.pokemonNo = no
            megaPokemon.h = h
            megaPokemon.a = a
            megaPokemon.b = b
            megaPokemon.c = c
            megaPokemon.d = d
            megaPokemon.s = s
            megaPokemon.ability = ability
            megaPokemon.weight = weight
            megaPokemon.megaType = MegaPokemonMasterData.MEGA_Y

            val master = selectPokemonMasterData(no)
            master.megay = megaPokemon
        }
    }

    fun selectSkillByName(name: String): Skill {
        return realm.where(Skill().javaClass).equalTo("jname", name).findFirst()
    }

    fun selectUnknownSkill(): Skill {
        return realm.where(Skill().javaClass).equalTo("no", -1).findFirst()
    }

    fun countIndividualPBAPokemon(): Long {
        return realm.where(IndividualPokemon().javaClass).count()
    }

    fun insertPartyData(selectedParty: Party?) {
        val count = countIndividualPBAPokemon()
        realm.executeTransaction {
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

    fun insertIndividualPBAPokemon(id: Long, master: PokemonMasterData) {
        val pokemon = realm.createObject(IndividualPokemon::class.java)
        pokemon.id = id
        pokemon.item = ""
        pokemon.characteristic = ""
        pokemon.ability = ""
        pokemon.skillNo1 = selectUnknownSkill()
        pokemon.skillNo2 = selectUnknownSkill()
        pokemon.skillNo3 = selectUnknownSkill()
        pokemon.skillNo4 = selectUnknownSkill()
        pokemon.master = selectPokemonMasterData(master.no)
    }

    fun selectIndividualPBAPokemon(id: Long): IndividualPokemon {
        return realm.where(IndividualPokemon().javaClass).equalTo("id", id).findFirst()
    }

    fun selectParty(userName: String): Party {
        val party = realm.where(Party().javaClass).equalTo("userName", userName).findAllSorted("time", Sort.DESCENDING)
        if (party == null || party.size < 1) {
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

    fun selectAllSkill(): MutableList<Skill> {
        val skill = realm.where(Skill().javaClass).findAllSorted("jname", Sort.ASCENDING)
        return skill.toMutableList()
    }

    fun selectAllItem(): ArrayList<String> {
        val item = realm.where(ItemMasterData().javaClass).findAllSorted("name", Sort.ASCENDING)
        val list = ArrayList<String>()
        for (temp in item) {
            list.add(temp.name)
        }
        return list
    }

    fun updateMyParty(){
        realm.executeTransaction {
            val inserted = selectParty("mine")
            val skill = selectAllSkill()

            inserted.member1.ability = "いかく"
            inserted.member1.characteristic = "いじっぱり"
            inserted.member1.item = "クチートナイト"
            inserted.member1.skillNo1 = skill[300]//ふいうち
            inserted.member1.skillNo2 = skill[384]//アイアンヘッド
            inserted.member1.skillNo3 = skill[157]//じゃれつく
            inserted.member1.skillNo4 = skill[210]//つるぎのまい
            inserted.member1.hpValue = 100
            inserted.member1.attackValue = 100
            inserted.member1.defenseValue = 100
            inserted.member1.specialAttackValue = 100
            inserted.member1.specialDefenseValue = 100
            inserted.member1.speedValue = 100

            inserted.member2.ability = "もうか"
            inserted.member2.characteristic = "おくびょう"
            inserted.member2.item = "こだわりスカーフ"
            inserted.member2.skillNo1 = skill[65]//かえんほうしゃ
            inserted.member2.skillNo2 = skill[308]//ふんか
            inserted.member2.skillNo3 = skill[4]//あくのはどう
            inserted.member2.skillNo4 = skill[353]//めざめるパワー
            inserted.member2.hpValue = 100
            inserted.member2.attackValue = 100
            inserted.member2.defenseValue = 100
            inserted.member2.specialAttackValue = 100
            inserted.member2.specialDefenseValue = 100
            inserted.member2.speedValue = 100

            inserted.member3.ability = "がんじょう"
            inserted.member3.characteristic = "ずぶとい"
            inserted.member3.item = "ハガネールナイト"
            inserted.member3.skillNo1 = skill[469]//ステルスロック
            inserted.member3.skillNo2 = skill[383]//アイアンテール
            inserted.member3.skillNo3 = skill[470]//ストーンエッジ
            inserted.member3.skillNo4 = skill[152]//じしん
            inserted.member3.hpValue = 100
            inserted.member3.attackValue = 100
            inserted.member3.defenseValue = 100
            inserted.member3.specialAttackValue = 100
            inserted.member3.specialDefenseValue = 100
            inserted.member3.speedValue = 100

            inserted.member4.ability = "いたずらごころ"
            inserted.member4.characteristic = "ずぶとい"
            inserted.member4.item = "たべのこし"
            inserted.member4.skillNo1 = skill[359]//やどりぎのタネ
            inserted.member4.skillNo2 = skill[334]//みがわり
            inserted.member4.skillNo3 = skill[325]//ぼうふう
            inserted.member4.skillNo4 = skill[423]//ギガドレイン
            inserted.member4.hpValue = 100
            inserted.member4.attackValue = 100
            inserted.member4.defenseValue = 100
            inserted.member4.specialAttackValue = 100
            inserted.member4.specialDefenseValue = 100
            inserted.member4.speedValue = 100

            inserted.member5.ability = "ダルマモード"
            inserted.member5.characteristic = "しんちょう"
            inserted.member5.item = "とつげきチョッキ"
            inserted.member5.skillNo1 = skill[551]//フレアドライブ
            inserted.member5.skillNo2 = skill[235]//とんぼがえり
            inserted.member5.skillNo3 = skill[291]//ばかじから
            inserted.member5.skillNo4 = skill[152]//じしん
            inserted.member5.hpValue = 100
            inserted.member5.attackValue = 100
            inserted.member5.defenseValue = 100
            inserted.member5.specialAttackValue = 100
            inserted.member5.specialDefenseValue = 100
            inserted.member5.speedValue = 100

            inserted.member6.ability = "おみとおし"
            inserted.member6.characteristic = "ずぶとい"
            inserted.member6.item = "ゴツゴツメット"
            inserted.member6.skillNo1 = skill[37]//いわなだれ
            inserted.member6.skillNo2 = skill[477]//タネばくだん
            inserted.member6.skillNo3 = skill[68]//かげうち
            inserted.member6.skillNo4 = skill[55]//おにび
            inserted.member6.hpValue = 100
            inserted.member6.attackValue = 100
            inserted.member6.defenseValue = 100
            inserted.member6.specialAttackValue = 100
            inserted.member6.specialDefenseValue = 100
            inserted.member6.speedValue = 100
        }
    }
}
