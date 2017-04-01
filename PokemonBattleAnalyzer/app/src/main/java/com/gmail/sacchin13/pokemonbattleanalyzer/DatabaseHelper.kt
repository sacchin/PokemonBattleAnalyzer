package com.gmail.sacchin13.pokemonbattleanalyzer

import android.content.Context
import android.util.Log
import com.gmail.sacchin13.pokemonbattleanalyzer.activity.KpActivity
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Construction
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Rank
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Type
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.realm.*
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
        realm = Realm.getInstance(realmConfig)
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
            pokemon.form = form
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
        pokemonList.filter { it -> util.pokemonImageResource.contains(it.no) }.map { it -> result.add(it) }
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
            list.map { it ->
                val pokemon = selectPokemonByName(it)
                construction.list.add(pokemon)
            }

            advantage.map { it ->
                val pokemon = selectPokemonByName(it)
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

    fun selectZSkill(skillNumber: Int): ZSkill {
        return realm.where(ZSkill().javaClass).equalTo("skillNumber", skillNumber).findFirst()
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

        return party[0]
    }

    fun selectAllParty(activity: KpActivity) {
        realm.where(Party().javaClass).findAllAsync().addChangeListener { it -> activity.onSelect(it.toList()) }
    }

    fun selectAllSkill(): MutableList<Skill> {
        val skill = realm.where(Skill().javaClass).findAllSorted("jname", Sort.ASCENDING)
        return skill.toMutableList()
    }

    fun selectAllItem(): ArrayList<String> {
        val item = realm.where(ItemMasterData().javaClass).findAllSorted("name", Sort.ASCENDING)
        val list = ArrayList<String>()
        item.map { it -> list.add(it.name) }
        return list
    }

    fun updateMyParty() {
        realm.executeTransaction {
            val inserted = selectParty("mine")

            inserted.member1.ability = "ふゆう"
            inserted.member1.characteristic = "おくびょう"
            inserted.member1.item = "いのちのたま"
            inserted.member1.skillNo1 = selectSkillByName("あくのはどう")
            inserted.member1.skillNo2 = selectSkillByName("とんぼがえり")
            inserted.member1.skillNo3 = selectSkillByName("りゅうのはどう")
            inserted.member1.skillNo4 = selectSkillByName("ほえる")
            inserted.member1.hp = 189
            inserted.member1.attack = 104
            inserted.member1.defense = 110
            inserted.member1.specialAttack = 177
            inserted.member1.specialDefense = 110
            inserted.member1.speed = 130

            inserted.member2.ability = "アナライズ"
            inserted.member2.characteristic = "おだやか"
            inserted.member2.item = "こだわりメガネ"
            inserted.member2.skillNo1 = selectSkillByName("１０まんボルト")
            inserted.member2.skillNo2 = selectSkillByName("はかいこうせん")
            inserted.member2.skillNo3 = selectSkillByName("ボルトチェンジ")
            inserted.member2.skillNo4 = selectSkillByName("ラスターカノン")
            inserted.member2.hp = 165
            inserted.member2.attack = 76
            inserted.member2.defense = 135
            inserted.member2.specialAttack = 182
            inserted.member2.specialDefense = 121
            inserted.member2.speed = 85


            inserted.member3.ability = "きもったま"
            inserted.member3.characteristic = "いじっぱり"
            inserted.member3.item = "ガルーラナイト"
            inserted.member3.skillNo1 = selectSkillByName("じしん")
            inserted.member3.skillNo2 = selectSkillByName("ふいうち")
            inserted.member3.skillNo3 = selectSkillByName("すてみタックル")
            inserted.member3.skillNo4 = selectSkillByName("いわなだれ")
            inserted.member3.hp = 212
            inserted.member3.attack = 161
            inserted.member3.defense = 101
            inserted.member3.specialAttack = 53
            inserted.member3.specialDefense = 95
            inserted.member3.speed = 110

            inserted.member4.ability = "さいせいりょく"
            inserted.member4.characteristic = "しんちょう"
            inserted.member4.item = "たべのこし"
            inserted.member4.skillNo1 = selectSkillByName("トーチカ")
            inserted.member4.skillNo2 = selectSkillByName("じこさいせい")
            inserted.member4.skillNo3 = selectSkillByName("まとわりつく")
            inserted.member4.skillNo4 = selectSkillByName("どくどく")
            inserted.member4.hp = 157
            inserted.member4.attack = 80
            inserted.member4.defense = 204
            inserted.member4.specialAttack = 65
            inserted.member4.specialDefense = 179
            inserted.member4.speed = 52


            inserted.member5.ability = "はやてのつばさ"
            inserted.member5.characteristic = "いじっぱり"
            inserted.member5.item = "ヒコウZ"
            inserted.member5.skillNo1 = selectSkillByName("ブレイブバード")
            inserted.member5.skillNo2 = selectSkillByName("はねやすめ")
            inserted.member5.skillNo3 = selectSkillByName("とんぼがえり")
            inserted.member5.skillNo4 = selectSkillByName("フレアドライブ")
            inserted.member5.hp = 154
            inserted.member5.attack = 146
            inserted.member5.defense = 91
            inserted.member5.specialAttack = 80
            inserted.member5.specialDefense = 89
            inserted.member5.speed = 178


            inserted.member6.ability = "さめはだ"
            inserted.member6.characteristic = "いじっぱり"
            inserted.member6.item = "こだわりスカーフ"
            inserted.member6.skillNo1 = selectSkillByName("じしん")
            inserted.member6.skillNo2 = selectSkillByName("ストーンエッジ")
            inserted.member6.skillNo3 = selectSkillByName("げきりん")
            inserted.member6.skillNo4 = selectSkillByName("かえんほうしゃ")
            inserted.member6.hp = 184
            inserted.member6.attack = 200
            inserted.member6.defense = 115
            inserted.member6.specialAttack = 79
            inserted.member6.specialDefense = 100
            inserted.member6.speed = 154

        }
    }
}
