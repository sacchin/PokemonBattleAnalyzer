package com.gmail.sacchin13.pokemonbattleanalyzer

import java.io.IOException
import java.lang.reflect.Field
import java.sql.Timestamp
import java.util.ArrayList

import org.json.JSONException
import org.json.JSONObject

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl.RankingPokemonIn

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log
import com.gmail.sacchin13.pokemonbattleanalyzer.Util
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.*

class PartyDatabaseHelper(context: Context) : SQLiteOpenHelper(context, PartyDatabaseHelper.DB_FILE, null, 1) {
    private val DATABASE_TABLE_NAMES: Array<String>

    private val util = Util()

    init {

        val list = ArrayList<String>()
        val fields = PartyDatabaseHelper::class.java.declaredFields
        for (f in fields) {
            if (f.name.endsWith("_TABLE_NAME") && String::class.java == f.type) {
                try {
                    list.add(f.get(null).toString())
                } catch (e: IllegalArgumentException) {
                    Log.e(javaClass.simpleName, "This is a bug.", e)
                    System.exit(-1)
                } catch (e: IllegalAccessException) {
                    Log.e(javaClass.simpleName, "This is a bug.", e)
                    System.exit(-1)
                }

            }
        }
        DATABASE_TABLE_NAMES = list.toTypedArray()
    }

    @Synchronized override fun onCreate(sqlitedatabase: SQLiteDatabase) {
        createTablesIfNotExist(sqlitedatabase)
    }

    private fun createTablesIfNotExist(sqlitedatabase: SQLiteDatabase) {
        try {
            dropTablesIfExist(sqlitedatabase)
        } catch (e1: IOException) {
            e1.printStackTrace()
        }

        for (sql in DATABASE_DEFINITIONS) {
            try {
                Log.v("createTablesIfNotExist", "CREATE TABLE IF NOT EXISTS " + sql)
                sqlitedatabase.execSQL("CREATE TABLE IF NOT EXISTS " + sql)
            } catch (e: IllegalStateException) {
                Log.w(javaClass.simpleName,
                        "perhaps, service was restarted or un/reinstalled.", e)
            }

        }
    }

    @Throws(IOException::class)
    protected fun dropTablesIfExist(db: SQLiteDatabase) {
        if (db.isReadOnly) {
            throw IOException("Cannot get writable access to DB.")
        }
        for (sql in DATABASE_TABLE_NAMES) {
            try {
                Log.w("dropTablesIfExist", sql)
                db.execSQL("DROP TABLE IF EXISTS " + sql)
            } catch (e: IllegalStateException) {
                Log.w(javaClass.simpleName,
                        "perhaps, service was restarted or un/reinstalled.", e)
            }

        }
    }

    @Synchronized @Throws(IOException::class, SQLException::class)
    fun insertPBAPokemonData(no: Int, ｊname: String, ename: String, h: Int, a: Int, b: Int, c: Int, d: Int, s: Int,
                             ability1: String, ability2: String, abilityd: String, type1: Int, type2: Int, weight: Double) {
        insertPBAPokemonData(no.toString(), ｊname, ename, h, a, b, c, d, s, ability1, ability2, abilityd, type1, type2, weight)
    }

    @Synchronized @Throws(IOException::class, SQLException::class)
    fun insertPBAPokemonData(no: String, ｊname: String, ename: String, h: Int, a: Int, b: Int, c: Int, d: Int, s: Int,
                             ability1: String, ability2: String, abilityd: String, type1: Int, type2: Int, weight: Double) {
        val db = writableDatabase
        if (db.isReadOnly) {
            throw IOException("Cannot get writable access to DB.")
        }
        try {
            val values = ContentValues()
            values.put("no", no)
            values.put("jname", ｊname)
            values.put("ename", ename)
            values.put("h", h)
            values.put("a", a)
            values.put("b", b)
            values.put("c", c)
            values.put("d", d)
            values.put("s", s)
            values.put("ability1", ability1)
            values.put("ability2", ability2)
            values.put("abilityd", abilityd)
            values.put("type1", type1)
            values.put("type2", type2)
            values.put("weight", weight)
            values.put("count", 1000)

            db.insert(POKEMON_MASTER_TABLE_NAME, null, values)
        } catch (e: IllegalStateException) {
            Log.w(javaClass.simpleName, "perhaps, service was restarted or un/reinstalled.", e)
        }

    }

    @Synchronized @Throws(IOException::class, SQLException::class)
    fun insertIndividualPBAPokemonData(p: IndividualPBAPokemon?, time: Timestamp?): Long {
        val db = writableDatabase
        if (p == null || time == null) {
            throw NullPointerException()
        }
        if (db.isReadOnly) {
            throw IOException("Cannot get writable access to DB.")
        }
        try {
            val values = ContentValues()
            values.put("time", time.time)
            values.put("item", p!!.item)
            values.put("characteristic", p!!.characteristic)
            values.put("skillNo1", p!!.skillNo1)
            values.put("skillNo2", p!!.skillNo2)
            values.put("skillNo3", p!!.skillNo3)
            values.put("skillNo4", p!!.skillNo4)
            values.put("evh", 0)
            values.put("eva", 0)
            values.put("evb", 0)
            values.put("evc", 0)
            values.put("evd", 0)
            values.put("evs", 0)
//            values.put("pokemonNo", p!!.rowId)

            return db.insert(POKEMON_INDIVIDUAL_TABLE_NAME, null, values)
        } catch (e: IllegalStateException) {
            Log.w(javaClass.simpleName, "perhaps, service was restarted or un/reinstalled.", e)
        }

        return -1
    }

    @Synchronized @Throws(IOException::class, SQLException::class)
    fun insertSkillData(name: String?, type: Int, power: Int, accuracy: Int, category: Int, pp: Int, direcr: Boolean, isMamoru: Boolean) {
        if (name == null) {
            throw NullPointerException("argument is null.")
        }

        val db = writableDatabase
        if (db.isReadOnly) {
            throw IOException("Cannot get writable access to DB.")
        }
        try {
            val values = ContentValues()
            values.put("skillName", name)
            values.put("type", type)
            values.put("power", power)
            values.put("accuracy", accuracy)
            values.put("category", category)
            values.put("pp", pp)
            db.insert(SKILL_MASTER_TABLE_NAME, null, values)
        } catch (e: IllegalStateException) {
            Log.w(javaClass.simpleName, "perhaps, service was restarted or un/reinstalled.", e)
        }

    }

    @Synchronized @Throws(IOException::class, SQLException::class)
    fun insertItemData(name: String?) {
        if (name == null) {
            throw NullPointerException("argument is null.")
        }

        val db = writableDatabase
        if (db.isReadOnly) {
            throw IOException("Cannot get writable access to DB.")
        }
        try {
            val values = ContentValues()
            values.put("itemName", name)
            db.insert(ITEM_MASTER_TABLE_NAME, null, values)
        } catch (e: IllegalStateException) {
            Log.w(javaClass.simpleName, "perhaps, service was restarted or un/reinstalled.", e)
        }

    }

    @Synchronized @Throws(IOException::class)
    fun selectAllPBAPokemon(): ArrayList<PBAPokemon> {
        val db = readableDatabase
        val cur = db.query(POKEMON_MASTER_TABLE_NAME, null, null, null, null, null, "count desc, " + BaseColumns._ID + " desc", null)

        val list = ArrayList<PBAPokemon>()
        try {
            while (cur.moveToNext()) {
                val p = createPBAPokemon(cur)
                p.rowId = cur.getInt(0)

                val id = util.pokemonImageResource!!.get(p.masterRecord.no.toString()) ?: continue
                p.resourceId = id
                list.add(p)
            }
        } catch (e: SQLiteException) {
            Log.e("selectAllPBAPokemon", "not found")
        }

        return list
    }

    @Synchronized @Throws(IOException::class)
    fun selectPBAPokemon(pokemonNo: String): PBAPokemon? {
        val db = readableDatabase
        val values = arrayOf(pokemonNo)
        val cur = db.query(POKEMON_MASTER_TABLE_NAME, null, "no = ?", values, null, null, null, null)
        try {
            if (cur.moveToNext()) {
                val p = createPBAPokemon(cur)
                p.rowId = cur.getInt(0)

                val id = util.pokemonImageResource!!.get(p.masterRecord.no.toString())
                if (id != null) {
                    p.resourceId = id
                }
                return p
            }
        } catch (e: SQLiteException) {
            Log.e("selectPBAPokemon", "not found")
        }

        return null
    }

    @Synchronized @Throws(IOException::class)
    fun selectAllSkill(): ArrayList<String> {
        val db = readableDatabase
        val cur = db.query(SKILL_MASTER_TABLE_NAME, arrayOf("skillName"), null, null, null, null, BaseColumns._ID + " asc", null)
        val list = ArrayList<String>()
        try {
            while (cur.moveToNext()) {
                list.add(cur.getString(0))
            }
        } catch (e: SQLiteException) {
            Log.e("selectAllSkill", "not found")
        }

        cur.close()
        return list
    }

    @Synchronized @Throws(IOException::class)
    fun selectAllItem(): ArrayList<String> {
        val db = readableDatabase
        val cur = db.query(ITEM_MASTER_TABLE_NAME, arrayOf("itemName"), null, null, null, null, BaseColumns._ID + " asc", null)
        val list = ArrayList<String>()
        try {
            while (cur.moveToNext()) {
                list.add(cur.getString(0))
            }
        } catch (e: SQLiteException) {
            Log.e("selectAllItem", "not found")
        }

        cur.close()
        return list
    }

    @Synchronized @Throws(IOException::class)
    fun selectIndividualPBAPokemonByID(rowId: Long): IndividualPBAPokemon? {
        val db = readableDatabase
        val individualCur = db.query(POKEMON_INDIVIDUAL_TABLE_NAME, null, BaseColumns._ID + " = ?", arrayOf(rowId.toString()), null, null, null, null)
        try {
            if (individualCur.moveToNext()) {
                val id = individualCur.getLong(0)
                val time = Timestamp(individualCur.getLong(1))
                val item = individualCur.getString(2)
                //				String ability = individualCur.getString(3);
                val characteristic = individualCur.getString(3)
                val skillNo1 = individualCur.getString(4)
                val skillNo2 = individualCur.getString(5)
                val skillNo3 = individualCur.getString(6)
                val skillNo4 = individualCur.getString(7)
                val evh = individualCur.getInt(8)
                val eva = individualCur.getInt(9)
                val evb = individualCur.getInt(10)
                val evc = individualCur.getInt(11)
                val evd = individualCur.getInt(12)
                val evs = individualCur.getInt(13)
                val rowNo = individualCur.getString(14)
                individualCur.close()

                val p = createPBAPokemon(rowNo)
                val result = IndividualPBAPokemon(p)
                result.id = id
                result.item = item
                result.ability = ""
                result.characteristic = characteristic
                result.skillNo1 = skillNo1
                result.skillNo2 = skillNo2
                result.skillNo3 = skillNo3
                result.skillNo4 = skillNo4

                result.hpEffortValue = evh
                result.attackEffortValue = eva
                result.deffenceEffortValue = evb
                result.specialAttackEffortValue = evc
                result.specialDeffenceEffortValue = evd
                result.speedEffortValue = evs

                val resourceId = util.pokemonImageResource!!.get(result.master!!.masterRecord.no)
                if (resourceId != null) {
                    result.master.resourceId = resourceId
                }
                Log.v("selectIndividualPBAPok", result.toString())

                return result
            } else {
                Log.e("selectPBAPokemonByNo", "not found")
            }
        } catch (e: SQLiteException) {
            Log.e("selectPBAPokemonByNo", "not found")
        }

        return null
    }

    private fun createPBAPokemon(pokemonNo: String): PBAPokemon? {
        val db = readableDatabase
        val cur = db.rawQuery("select * from " + POKEMON_MASTER_TABLE_NAME + " left outer join " + MEGA_POKEMON_TABLE_NAME + " on " +
                POKEMON_MASTER_TABLE_NAME + "." + BaseColumns._ID + " = " +
                MEGA_POKEMON_TABLE_NAME + ".pokemonNo where " + BaseColumns._ID + " = ?", arrayOf(pokemonNo.toString()))

        var result: PBAPokemon? = null
        while (cur.moveToNext()) {
            val p = Pokemon()

            if (result == null) {
                val no = cur.getString(1)
                val jname = cur.getString(2)
                val ename = cur.getString(3)
                val h = cur.getInt(4)
                val a = cur.getInt(5)
                val b = cur.getInt(6)
                val c = cur.getInt(7)
                val d = cur.getInt(8)
                val s = cur.getInt(9)
                val ability1 = cur.getString(10)
                val ability2 = cur.getString(11)
                val abilityd = cur.getString(12)


                result = PBAPokemon(0, 0, p, 0)
            }
            if (0 < cur.getInt(17)) {
                val mega = PBAPokemon(0, 0, p, 0)
//                        result!!.masterRecord.no, "メガ" + result!!.masterRecord.jname, "Mega " + result!!.masterRecord.ename,
//                        cur.getInt(19), cur.getInt(20), cur.getInt(21), cur.getInt(22), cur.getInt(23), cur.getInt(24), cur.getString(25),
//                        "", "", Type.TypeCode.UNKNOWN, Type.TypeCode.UNKNOWN, 0f, 0)

                val resouceId = util.pokemonImageResource!!.get(result!!.masterRecord.no + "m" + cur.getString(26))
                if (resouceId != null) {
                    mega.resourceId = resouceId
                }
//                result!!.masterRecord.addMega(mega)
            }
        }
        cur.close()

        return result
    }

    @Synchronized @Throws(IOException::class, SQLException::class)
    fun insertPartyData(party: Party?) {
        if (party == null) {
            throw NullPointerException("argument is null.")
        }

        val db = writableDatabase
        if (db.isReadOnly) {
            throw IOException("Cannot get writable access to DB.")
        }
        try {
            val values = ContentValues()

            values.put("time", System.currentTimeMillis())
            values.put("userName", party!!.userName)
            values.put("memo", "")

            for (i in 0..party!!.member!!.size - 1) {
                val id = this.insertIndividualPBAPokemonData(party.member!!.get(i), party!!.time)
                values.put("member" + (i + 1), id)
            }

            db.insert(PARTY_TABLE_NAME, null, values)
        } catch (e: IllegalStateException) {
            Log.w(javaClass.simpleName, "perhaps, service was restarted or un/reinstalled.", e)
        }

    }

    @Synchronized @Throws(IOException::class, SQLException::class)
    fun updatePBAPokemonRanking(rankingList: List<RankingPokemonIn>?) {
        if (rankingList == null) {
            throw NullPointerException("argument is null.")
        }

        val db = writableDatabase
        if (db.isReadOnly) {
            throw IOException("Cannot get writable access to DB.")
        }
        try {
            for (temp in rankingList) {
                val values = ContentValues()
                val rank = if (temp.getRanking() !== 0) temp.getRanking() else 1000
                values.put("count", rank)

                var pn = temp.getPokemonNo().split("-")[0]
                val intPokemonNo = Integer.parseInt(pn)
                if (intPokemonNo < 10) {
                    pn = "00" + pn
                } else if (9 < intPokemonNo && intPokemonNo < 100) {
                    pn = "0" + pn
                } else if (intPokemonNo == 479 || intPokemonNo == 641 || intPokemonNo == 642 || intPokemonNo == 645) {
                    pn = temp.getPokemonNo()
                }

                db.update(POKEMON_MASTER_TABLE_NAME, values, "no = ?", arrayOf<String>(pn))
            }
        } catch (e: IllegalStateException) {
            Log.w(javaClass.simpleName, "perhaps, service was restarted or un/reinstalled.", e)
        }

    }

    @Synchronized @Throws(IOException::class, SQLException::class)
    fun updateIndividualPBAPokemonData(
            id: Long, item: String, characteristic: String,
            skill1: String, skill2: String, skill3: String, skill4: String,
            h: Int, a: Int, b: Int, c: Int, d: Int, s: Int) {

        val db = writableDatabase
        if (db.isReadOnly) {
            throw IOException("Cannot get writable access to DB.")
        }
        try {
            val values = ContentValues()
            values.put("item", item)
            values.put("characteristic", characteristic)
            values.put("skillNo1", skill1)
            values.put("skillNo2", skill2)
            values.put("skillNo3", skill3)
            values.put("skillNo4", skill4)
            values.put("evh", h)
            values.put("eva", a)
            values.put("evb", b)
            values.put("evc", c)
            values.put("evd", d)
            values.put("evs", s)

            db.update(POKEMON_INDIVIDUAL_TABLE_NAME, values, BaseColumns._ID + " = ?", arrayOf(id.toString()))

            Log.v("updateIndividual", values.toString())
        } catch (e: IllegalStateException) {
            Log.w(javaClass.simpleName, "perhaps, service was restarted or un/reinstalled.", e)
        }

    }

    @Synchronized @Throws(IOException::class)
    fun selectOpponentParty(): Party? {
        val db = readableDatabase
        val selectArg = arrayOf("opponent")
        val cur = db.query(PARTY_TABLE_NAME, arrayOf("time", "userName", "member1", "member2", "member3", "member4", "member5", "member6", "memo"),
                "userName=?", selectArg, null, null, "time desc", null)
        try {
            if (cur.moveToNext()) {
                val member1 = selectIndividualPBAPokemonByID(cur.getLong(2))
                val member2 = selectIndividualPBAPokemonByID(cur.getLong(3))
                val member3 = selectIndividualPBAPokemonByID(cur.getLong(4))
                val member4 = selectIndividualPBAPokemonByID(cur.getLong(5))
                val member5 = selectIndividualPBAPokemonByID(cur.getLong(6))
                val member6 = selectIndividualPBAPokemonByID(cur.getLong(7))

                return Party(Timestamp(cur.getLong(0)),
                        member1, member2, member3, member4, member5, member6,
                        cur.getString(8), cur.getString(1))

            }
        } catch (e: SQLiteException) {
            Log.e("selectPBAPokemonByNo", "not found")
        }

        cur.close()
        return null
    }

    @Synchronized @Throws(IOException::class)
    fun selectMyParty(): Party? {
        val db = readableDatabase
        val selectArg = arrayOf("mine")
        val cur = db.query(PARTY_TABLE_NAME, arrayOf("time", "userName", "member1", "member2", "member3", "member4", "member5", "member6", "memo"),
                "userName=?", selectArg, null, null, "time desc", null)
        try {
            if (cur.moveToNext()) {
                val member1 = selectIndividualPBAPokemonByID(cur.getLong(2))
                val member2 = selectIndividualPBAPokemonByID(cur.getLong(3))
                val member3 = selectIndividualPBAPokemonByID(cur.getLong(4))
                val member4 = selectIndividualPBAPokemonByID(cur.getLong(5))
                val member5 = selectIndividualPBAPokemonByID(cur.getLong(6))
                val member6 = selectIndividualPBAPokemonByID(cur.getLong(7))

                return Party(Timestamp(cur.getLong(0)),
                        member1, member2, member3, member4, member5, member6,
                        cur.getString(8), cur.getString(1))
            } else {
                Log.e("selectPBAPokemonByNo", "not found")
            }
        } catch (e: SQLiteException) {
            Log.e("selectPBAPokemonByNo", "not found")
        }

        cur.close()
        return null
    }

    @Synchronized @Throws(IOException::class)
    fun selectMyChoicedParty(): Party? {
        val db = readableDatabase
        val selectArg = arrayOf("choiced")
        val cur = db.query(PARTY_TABLE_NAME, arrayOf("time", "userName", "member1", "member2", "member3", "memo"),
                "userName=?", selectArg, null, null, "time desc", null)
        try {
            if (cur.moveToNext()) {
                val member1 = selectIndividualPBAPokemonByID(cur.getLong(2))
                val member2 = selectIndividualPBAPokemonByID(cur.getLong(3))
                val member3 = selectIndividualPBAPokemonByID(cur.getLong(4))

                return Party(Timestamp(cur.getLong(0)),
                        member1, member2, member3, null, null, null,
                        "", cur.getString(1))

            }
        } catch (e: SQLiteException) {
            Log.e("selectPBAPokemonByNo", "not found")
        }

        cur.close()
        return null
    }

    @Synchronized @Throws(IOException::class, SQLException::class)
    fun insertIndividualPBAPokemonData(individualPBAPokemon: JSONObject?): Long {
        val db = writableDatabase
        if (individualPBAPokemon == null) {
            throw NullPointerException()
        }
        if (db.isReadOnly) {
            throw IOException("Cannot get writable access to DB.")
        }
        try {
            val values = ContentValues()
            values.put(BaseColumns._ID, individualPBAPokemon.getInt(BaseColumns._ID))
            values.put("time", individualPBAPokemon.getLong("time"))
            values.put("item", individualPBAPokemon.getString("item"))
            values.put("characteristic", individualPBAPokemon.getString("characteristic"))
            values.put("skillNo1", individualPBAPokemon.getString("skillNo1"))
            values.put("skillNo2", individualPBAPokemon.getString("skillNo2"))
            values.put("skillNo3", individualPBAPokemon.getString("skillNo3"))
            values.put("skillNo4", individualPBAPokemon.getString("skillNo4"))
            values.put("pokemonNo", individualPBAPokemon.getString("pokemonNo"))

            return db.insert(POKEMON_INDIVIDUAL_TABLE_NAME, null, values)
        } catch (e: IllegalStateException) {
            Log.w(javaClass.simpleName, "perhaps, service was restarted or un/reinstalled.", e)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return -1
    }

    @Synchronized @Throws(IOException::class, SQLException::class)
    fun insertPartyData(party: JSONObject?) {
        if (party == null) {
            throw NullPointerException("argument is null.")
        }

        val db = writableDatabase
        if (db.isReadOnly) {
            throw IOException("Cannot get writable access to DB.")
        }
        try {
            val values = ContentValues()
            values.put(BaseColumns._ID, party.getInt(BaseColumns._ID))
            values.put("time", party.getLong("time"))
            values.put("userName", party.getString("userName"))
            values.put("member1", party.getLong("member1"))
            values.put("member2", party.getLong("member2"))
            values.put("member3", party.getLong("member3"))
            values.put("member4", party.getLong("member4"))
            values.put("member5", party.getLong("member5"))
            values.put("member6", party.getLong("member6"))
            values.put("memo", party.getString("memo"))

            db.insert(PARTY_TABLE_NAME, null, values)
        } catch (e: IllegalStateException) {
            Log.w(javaClass.simpleName, "perhaps, service was restarted or un/reinstalled.", e)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

    @Synchronized @Throws(IOException::class, SQLException::class)
    fun insertMegaPBAPokemonData(megaPBAPokemon: JSONObject?) {
        if (megaPBAPokemon == null) {
            throw NullPointerException("argument is null.")
        }

        val t = megaPBAPokemon.toString()
        val db = writableDatabase
        if (db.isReadOnly) {
            throw IOException("Cannot get writable access to DB.")
        }
        try {
            val values = ContentValues()
            values.put("pokemonNo", megaPBAPokemon.getString("pokemonNo"))
            values.put("h", megaPBAPokemon.getInt("h"))
            values.put("a", megaPBAPokemon.getInt("a"))
            values.put("b", megaPBAPokemon.getInt("b"))
            values.put("c", megaPBAPokemon.getInt("c"))
            values.put("d", megaPBAPokemon.getInt("d"))
            values.put("s", megaPBAPokemon.getInt("s"))
            values.put("characteristic", megaPBAPokemon.getString("characteristic"))
            values.put("megaType", megaPBAPokemon.getString("megaType"))

            db.insert(MEGA_POKEMON_TABLE_NAME, null, values)
        } catch (e: IllegalStateException) {
            Log.w(javaClass.simpleName, "perhaps, service was restarted or un/reinstalled.", e)
        } catch (e: JSONException) {
            Log.w(javaClass.simpleName, t, e)
            e.printStackTrace()
        }

    }

    fun createPBAPokemon(cur: Cursor): PBAPokemon {
        return PBAPokemon(0, 0, Pokemon(), 0)

//                cur.getString(1), cur.getString(2), cur.getString(3), cur.getInt(4), cur.getInt(5), cur.getInt(6), cur.getInt(7), cur.getInt(8),
//                cur.getInt(9), cur.getString(10), cur.getString(11), cur.getString(12), Type.convertNoToTypeCode(cur.getInt(13)), Type.convertNoToTypeCode(cur.getInt(14)), cur.getFloat(15), cur.getInt(16))
    }

    override fun onUpgrade(arg0: SQLiteDatabase, arg1: Int, arg2: Int) {
    }

    companion object {
        protected val DB_FILE = "cache4pbuf.db"

        val POKEMON_MASTER_TABLE_NAME = "pokemon"
        val PARTY_TABLE_NAME = "party"
        val SKILL_MASTER_TABLE_NAME = "skill"
        val ITEM_MASTER_TABLE_NAME = "item"
        val POKEMON_INDIVIDUAL_TABLE_NAME = "pokemon_skill"
        val POKEMON_ITEM_TABLE_NAME = "pokemon_item"
        val MEGA_POKEMON_TABLE_NAME = "mega_pokemon"

        private val DATABASE_DEFINITIONS = arrayOf(POKEMON_MASTER_TABLE_NAME + "(" + BaseColumns._ID + " INTEGER PRIMARY KEY, " +
                "no TEXT, " +
                "jname TEXT, " +
                "ename TEXT, " +
                "h INTEGER, " +
                "a INTEGER, " +
                "b INTEGER, " +
                "c INTEGER, " +
                "d INTEGER, " +
                "s INTEGER, " +
                "ability1 TEXT, " +
                "ability2 TEXT, " +
                "abilityd TEXT, " +
                "type1 INTEGER, " +
                "type2 INTEGER, " +
                "weight REAL, " +
                "count INTEGER)", SKILL_MASTER_TABLE_NAME + "(" + BaseColumns._ID +
                " INTEGER PRIMARY KEY, " +
                "skillName TEXT, " +
                "type INTEGER, " +
                "power INTEGER, " +
                "accuracy INTEGER, " +
                "category INTEGER, " +
                "pp INTEGER)", ITEM_MASTER_TABLE_NAME + "(" + BaseColumns._ID + " INTEGER PRIMARY KEY, itemName TEXT)", PARTY_TABLE_NAME + "(" + BaseColumns._ID + " INTEGER PRIMARY KEY, " +
                "time INTEGER, " +
                "userName TEXT, " +
                "member1 INTEGER, " +
                "member2 INTEGER, " +
                "member3 INTEGER, " +
                "member4 INTEGER, " +
                "member5 INTEGER, " +
                "member6 INTEGER, " +
                "memo text)", POKEMON_INDIVIDUAL_TABLE_NAME + "(" + BaseColumns._ID + " INTEGER PRIMARY KEY, " +
                "time INTEGER, " +
                "item TEXT, " +
                "characteristic TEXT, " +
                "skillNo1 TEXT, " +
                "skillNo2 TEXT, " +
                "skillNo3 TEXT, " +
                "skillNo4 TEXT, " +
                "evh INTEGER, " +
                "eva INTEGER, " +
                "evb INTEGER, " +
                "evc INTEGER, " +
                "evd INTEGER, " +
                "evs INTEGER, " +
                "pokemonNo String)", MEGA_POKEMON_TABLE_NAME + "(" + BaseColumns._ID + "_mega INTEGER PRIMARY KEY, " +
                "pokemonNo INTEGER, " +
                "h INTEGER, " +
                "a INTEGER, " +
                "b INTEGER, " +
                "c INTEGER, " +
                "d INTEGER, " +
                "s INTEGER, " +
                "characteristic String, " +
                "megaType String)")
    }
}