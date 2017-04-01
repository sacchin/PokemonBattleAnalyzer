package com.gmail.sacchin13.pokemonbattleanalyzer.activity

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.gmail.sacchin13.pokemonbattleanalyzer.R
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.*
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl.RankingResponse
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl.TrendForBattle
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.realm.Party
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.realm.PokemonMasterData
import com.gmail.sacchin13.pokemonbattleanalyzer.http.PokemonTrendDownloader
import com.gmail.sacchin13.pokemonbattleanalyzer.util.Util
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.activity_kpt.*
import okhttp3.*
import java.io.IOException
import kotlin.properties.Delegates

class KpActivity : PGLActivity() {
    var util: Util by Delegates.notNull()

    var auth = "4/ULtF7zCqJxUSzLBcvqxa7zycf0D-ssTbJJ9Ts-1xAJo"
    var client = "634822348294-ievqaletpftfieshvacirovsheqt9vlj.apps.googleusercontent.com"
    var sk = "Bgejtv3iD03G46q9qUwOnKqE"

    var refresh = "1/frICe60dg4JMQfGMNdTEPLhei1jUECX4yayrjRm4P_w"

    var sheetId = "1TKcZOZd7H_C2E__xtEFh4kd5oxxqZ51slcS4iqKFM6E"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kpt)

        util = Util()

        databaseHelper.selectAllParty(this)
    }

    fun onSelect(list: List<Party>){
        val result = mutableMapOf<PokemonMasterData, Int>()
        for(temp in list) {
            result[temp.member1.master] = (result[temp.member1.master] ?: 0) + 1
            result[temp.member2.master] = (result[temp.member2.master] ?: 0) + 1
            result[temp.member3.master] = (result[temp.member3.master] ?: 0) + 1
            result[temp.member4.master] = (result[temp.member4.master] ?: 0) + 1
            result[temp.member5.master] = (result[temp.member5.master] ?: 0) + 1
            result[temp.member6.master] = (result[temp.member6.master] ?: 0) + 1
        }

        val sorted = result.entries.sortedByDescending { s -> s.value }
        sorted.forEach { run {
            val row = LinearLayout(this)
            row.orientation = LinearLayout.HORIZONTAL

            val image = ImageView(this)
            image.setImageBitmap(util.createImage(it.key, 120f, resources))
            row.addView(image)
            val text = TextView(this)
            text.text = it.value.toString()
            row.addView(text)

            view.addView(row)
        } }

        kp_fab.setOnClickListener { startAsync(list) }
    }

    private fun startAsync(list: List<Party>) {
        val sss = mutableListOf<SaveRequest.RowEntity>()
        for(t in list) sss.add(SaveRequest.RowEntity(
                                    listOf(SaveRequest.ValueEntity(SaveRequest.UserEnteredValueEntity(t.time.toString())),
                                            SaveRequest.ValueEntity(SaveRequest.UserEnteredValueEntity(t.member1.master.jname)),
                                            SaveRequest.ValueEntity(SaveRequest.UserEnteredValueEntity(t.member2.master.jname)),
                                            SaveRequest.ValueEntity(SaveRequest.UserEnteredValueEntity(t.member3.master.jname)),
                                            SaveRequest.ValueEntity(SaveRequest.UserEnteredValueEntity(t.member4.master.jname)),
                                            SaveRequest.ValueEntity(SaveRequest.UserEnteredValueEntity(t.member5.master.jname)),
                                            SaveRequest.ValueEntity(SaveRequest.UserEnteredValueEntity(t.member6.master.jname)))))

        val ss = SaveRequest.RequestEntity(
                SaveRequest.UpdateCellsEntity(
                        SaveRequest.StartEntity(0, 0, 0), sss))

        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter(SaveRequest::class.java)
        val body = adapter.toJson(SaveRequest(listOf(ss)))
        Log.e("token", body)

        val testTask = AppendKpTask(this, body)
        testTask.execute(0)
    }

    fun setTextView(count: String) {
        Log.e("token", count)
    }



    inner class AppendKpTask(val mainActivity: KpActivity, val body: String) : AsyncTask<Int, Int, String>() {
        override fun doInBackground(vararg params: Int?): String {
            val httpClient = OkHttpClient.Builder().build()
            val requestBody = sheet()

            var access_token = "ya29.Ci-mA-LY18m4-j6XY1C4bz4GmKQg3LT1MjK5y3W5Eh76RIv6m8dV3SQINtkJtFH2DQ"

            val request = Request.Builder()
                    .url("https://sheets.googleapis.com/v4/spreadsheets/$sheetId/values/A1:append")
                    .post(requestBody).build()
            try {
                val response = httpClient.newCall(request).execute()

                val str = response.body().string()
                val moshi = Moshi.Builder().build()
                val adapter = moshi.adapter(RefreshResponse::class.java)

                val refreshResponse = adapter.fromJson(str)

                response.close()
                return refreshResponse.token
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return "error"
        }

        override fun onPostExecute(result: String) {
            super.onPostExecute(result)
            mainActivity.setTextView(result)
        }

        fun sheet(): RequestBody{
            val ooo = MediaType.parse("application/json; charset=utf-8")
            return RequestBody.create(ooo, body)
        }
    }

    inner class GetTokenTask(private val mainActivity: KpActivity) : AsyncTask<Int, Int, String>() {
        override fun doInBackground(vararg params: Int?): String {
            val httpClient = OkHttpClient.Builder().build()
            val body = refreshToken()

            val request = Request.Builder()
                    .url("https://www.googleapis.com/oauth2/v4/token")
                    .post(body).build()
            try {
                val response = httpClient.newCall(request).execute()

                val str = response.body().string()
                val moshi = Moshi.Builder().build()
                val adapter = moshi.adapter(RefreshResponse::class.java)

                val refreshResponse = adapter.fromJson(str)

                response.close()
                return refreshResponse.token
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return "error"
        }

        override fun onPostExecute(result: String) {
            super.onPostExecute(result)
            mainActivity.setTextView(result)
        }

        fun refreshToken(): FormBody{
            return FormBody.Builder()
                    .add("client_id", client)
                    .add("client_secret", sk)
                    .add("refresh_token", refresh)
                    .add("grant_type", "refresh_token")
                    .build()
        }

        fun firstToken(): FormBody{
            return FormBody.Builder()
                .add("redirect_uri", "urn:ietf:wg:oauth:2.0:oob")
                .add("access_type", "offline")
                .add("code", auth)
                .add("client_secret", sk)
                .add("grant_type", "authorization_code")
                .add("client_id", client)
                .build()
        }
    }
}
