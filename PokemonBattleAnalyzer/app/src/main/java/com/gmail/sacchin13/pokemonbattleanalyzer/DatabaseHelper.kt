package com.gmail.sacchin13.pokemonbattleanalyzer

import android.content.Context
import android.util.Log
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.*
import com.gmail.sacchin13.pokemonbattleanalyzer.util.Util
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults
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

    fun selectPokemonByName(pokemonName: String): PokemonMasterData {
        val pokemon = realm.where(PokemonMasterData().javaClass).equalTo("jname", pokemonName).findFirst()
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

    fun insertZSkillMasterData(id: Int, skillName: String, type: Int, category: Int, name: String, power: Int, rank: Int) {
        realm.executeTransaction {
            val skill = realm.createObject(ZSkill::class.java)
            skill.no = id
            skill.skillNumber = selectSkillByName(skillName).no
            skill.jname = name
            skill.type = type
            skill.power = power
            skill.category = category
            skill.rank = rank
        }
    }

    fun insertZSkillMasterData(id: Int, skillName: String, type: Int, category: Int, name: String, power: Int) {
        insertZSkillMasterData(id, skillName, type, category, name, power, Rank.no(Rank.Code.UNKNOWN))
    }

    fun insertMegaPokemonDataX(no: String, h: Int, a: Int, b: Int, c: Int, d: Int, s: Int, type1: Int, type2: Int, ability: String, weight: Float) {
        realm.executeTransaction {
            val megaPokemon = realm.createObject(MegaPokemonMasterData::class.java)
            megaPokemon.pokemonNo = no
            megaPokemon.h = h
            megaPokemon.a = a
            megaPokemon.b = b
            megaPokemon.c = c
            megaPokemon.d = d
            megaPokemon.s = s
            megaPokemon.type1 = type1
            megaPokemon.type2 = type2
            megaPokemon.ability = ability
            megaPokemon.weight = weight
            megaPokemon.megaType = MegaPokemonMasterData.MEGA_X

            val master = selectPokemonMasterData(no)
            master.megax = megaPokemon
        }
    }

    fun insertMegaPokemonDataX(no: String, h: Int, a: Int, b: Int, c: Int, d: Int, s: Int, ability: String, weight: Float) {
        insertMegaPokemonDataX(no, h, a, b, c, d, s, Type.no(Type.Code.UNKNOWN), Type.no(Type.Code.UNKNOWN), ability, weight)
    }

    fun insertMegaPokemonDataY(no: String, h: Int, a: Int, b: Int, c: Int, d: Int, s: Int, type1: Int, type2: Int, ability: String, weight: Float) {
        realm.executeTransaction {
            val megaPokemon = realm.createObject(MegaPokemonMasterData::class.java)
            megaPokemon.pokemonNo = no
            megaPokemon.h = h
            megaPokemon.a = a
            megaPokemon.b = b
            megaPokemon.c = c
            megaPokemon.d = d
            megaPokemon.s = s
            megaPokemon.type1 = type1
            megaPokemon.type2 = type2
            megaPokemon.ability = ability
            megaPokemon.weight = weight
            megaPokemon.megaType = MegaPokemonMasterData.MEGA_Y

            val master = selectPokemonMasterData(no)
            master.megay = megaPokemon
        }
    }

    fun insertMegaPokemonDataY(no: String, h: Int, a: Int, b: Int, c: Int, d: Int, s: Int, ability: String, weight: Float) {
        insertMegaPokemonDataY(no, h, a, b, c, d, s, Type.no(Type.Code.UNKNOWN), Type.no(Type.Code.UNKNOWN), ability, weight)
    }

    fun insertConstruction(name: String, type: Construction.Type, list: Array<String>, advantage: Array<String>, warning: String) {
        realm.executeTransaction {

            val construction = realm.createObject(Construction::class.java)
            for (pokemonName in list) {
                val pokemon = selectPokemonByName(pokemonName)
                construction.list.add(pokemon)
            }

            for (pokemonName in advantage) {
                val pokemon = selectPokemonByName(pokemonName)
                construction.advantage.add(pokemon)
            }

            construction.type = Construction.no(type)
            construction.name = name
            construction.warning = warning
        }
    }

    fun selectConstruction(): RealmResults<Construction> {
        return realm.where(Construction().javaClass).findAll()
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

    fun updateMyParty() {
        realm.executeTransaction {
            val inserted = selectParty("mine")

            inserted.member1.ability = "いかく"
            inserted.member1.characteristic = "ようき"
            inserted.member1.item = "クチートナイト"
            inserted.member1.skillNo1 = selectSkillByName("ふいうち")
            inserted.member1.skillNo2 = selectSkillByName("みがわり")
            inserted.member1.skillNo3 = selectSkillByName("じゃれつく")
            inserted.member1.skillNo4 = selectSkillByName("つるぎのまい")
            inserted.member1.hpEv = 4
            inserted.member1.attackEv = 252
            inserted.member1.defenseEv = 0
            inserted.member1.specialAttackEv = 0
            inserted.member1.specialDefenseEv = 0
            inserted.member1.speedEv = 252
            inserted.member1.hpIv = 15
            inserted.member1.attackIv = 31
            inserted.member1.defenseIv = 31
            inserted.member1.specialAttackIv = 15
            inserted.member1.specialDefenseIv = 31
            inserted.member1.speedIv = 31

            inserted.member2.ability = "クリアボディ"
            inserted.member2.characteristic = "いじっぱり"
            inserted.member2.item = "とつげきチョッキ"
            inserted.member2.skillNo1 = selectSkillByName("ステルスロック")
            inserted.member2.skillNo2 = selectSkillByName("ストーンエッジ")
            inserted.member2.skillNo3 = selectSkillByName("じならし")
            inserted.member2.skillNo4 = selectSkillByName("ドレインパンチ")
            inserted.member2.hpEv = 252
            inserted.member2.attackEv = 252
            inserted.member2.defenseEv = 0
            inserted.member2.specialAttackEv = 0
            inserted.member2.specialDefenseEv = 4
            inserted.member2.speedEv = 0
            inserted.member2.hpIv = 15
            inserted.member2.attackIv = 15
            inserted.member2.defenseIv = 31
            inserted.member2.specialAttackIv = 15
            inserted.member2.specialDefenseIv = 31
            inserted.member2.speedIv = 31

            inserted.member3.ability = "あまのじゃく"
            inserted.member3.characteristic = "おくびょう"
            inserted.member3.item = "ゴツゴツメット"
            inserted.member3.skillNo1 = selectSkillByName("リフレクター")
            inserted.member3.skillNo2 = selectSkillByName("リーフストーム")
            inserted.member3.skillNo3 = selectSkillByName("へびにらみ")
            inserted.member3.skillNo4 = selectSkillByName("こうごうせい")
            inserted.member3.hpEv = 156
            inserted.member3.attackEv = 0
            inserted.member3.defenseEv = 180
            inserted.member3.specialAttackEv = 0
            inserted.member3.specialDefenseEv = 0
            inserted.member3.speedEv = 172
            inserted.member3.hpIv = 31
            inserted.member3.attackIv = 15
            inserted.member3.defenseIv = 31
            inserted.member3.specialAttackIv = 15
            inserted.member3.specialDefenseIv = 31
            inserted.member3.speedIv = 31

            inserted.member4.ability = "いかく"
            inserted.member4.characteristic = "やんちゃ"
            inserted.member4.item = "こだわりスカーフ"
            inserted.member4.skillNo1 = selectSkillByName("りゅうせいぐん")
            inserted.member4.skillNo2 = selectSkillByName("かえんほうしゃ")
            inserted.member4.skillNo3 = selectSkillByName("じしん")
            inserted.member4.skillNo4 = selectSkillByName("いわなだれ")
            inserted.member4.hpEv = 104
            inserted.member4.attackEv = 0
            inserted.member4.defenseEv = 0
            inserted.member4.specialAttackEv = 252
            inserted.member4.specialDefenseEv = 0
            inserted.member4.speedEv = 156
            inserted.member4.hpIv = 31
            inserted.member4.attackIv = 31
            inserted.member4.defenseIv = 31
            inserted.member4.specialAttackIv = 31
            inserted.member4.specialDefenseIv = 15
            inserted.member4.speedIv = 15

            inserted.member5.ability = "かそく"
            inserted.member5.characteristic = "ようき"
            inserted.member5.item = "サメハダーナイト"
            inserted.member5.skillNo1 = selectSkillByName("かみくだく")
            inserted.member5.skillNo2 = selectSkillByName("こおりのキバ")
            inserted.member5.skillNo3 = selectSkillByName("どくどくのキバ")
            inserted.member5.skillNo4 = selectSkillByName("まもる")
            inserted.member5.hpEv = 4
            inserted.member5.attackEv = 252
            inserted.member5.defenseEv = 0
            inserted.member5.specialAttackEv = 0
            inserted.member5.specialDefenseEv = 0
            inserted.member5.speedEv = 252
            inserted.member5.hpIv = 15
            inserted.member5.attackIv = 31
            inserted.member5.defenseIv = 31
            inserted.member5.specialAttackIv = 15
            inserted.member5.specialDefenseIv = 31
            inserted.member5.speedIv = 31

            inserted.member6.ability = "すなおこし"
            inserted.member6.characteristic = "ゆうかん"
            inserted.member6.item = "ラムのみ"
            inserted.member6.skillNo1 = selectSkillByName("じしん")
            inserted.member6.skillNo2 = selectSkillByName("あくび")
            inserted.member6.skillNo3 = selectSkillByName("なまける")
            inserted.member6.skillNo4 = selectSkillByName("かみくだく")
            inserted.member6.hpEv = 252
            inserted.member6.attackEv = 252
            inserted.member6.defenseEv = 0
            inserted.member6.specialAttackEv = 0
            inserted.member6.specialDefenseEv = 0
            inserted.member6.speedEv = 4
            inserted.member6.hpIv = 15
            inserted.member6.attackIv = 15
            inserted.member6.defenseIv = 15
            inserted.member6.specialAttackIv = 15
            inserted.member6.specialDefenseIv = 15
            inserted.member6.speedIv = 15
        }
    }
}
