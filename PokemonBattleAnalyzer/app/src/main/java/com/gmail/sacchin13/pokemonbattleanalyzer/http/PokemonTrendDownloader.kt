package com.gmail.sacchin13.pokemonbattleanalyzer.http

import android.os.AsyncTask
import android.util.Log
import com.gmail.sacchin13.pokemonbattleanalyzer.DatabaseHelper
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Skill
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl.RankingResponse
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl.TrendForBattle
import com.squareup.moshi.Moshi
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONException
import java.io.IOException
import java.util.*

class PokemonTrendDownloader(
        val pokemonNo: String,
        val index: Int,
        val listener: EventListener,
        val databaseHelper: DatabaseHelper) : AsyncTask<Void, Void, TrendForBattle>() {

    interface EventListener {
        fun onFinish(result: TrendForBattle, index: Int)
    }

    override fun onPostExecute(result: TrendForBattle) {
        listener.onFinish(result, index)
    }

    override fun doInBackground(vararg params: Void?): TrendForBattle {
        try {
            val httpClient = OkHttpClient.Builder()
                    .addInterceptor {
                        it.proceed(it.request()
                                .newBuilder()
                                .addHeader("Referer", "https://3ds.pokemon-gl.com/battle/oras/")
                                .build())
                    }.build()
            val requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8"),
                    "languageId=1&seasonId=116&battleType=1&timezone=JST&pokemonId=$pokemonNo&displayNumberWaza=10&displayNumberTokusei=3&displayNumberSeikaku=10&displayNumberItem=10&displayNumberLevel=10&displayNumberPokemonIn=10&displayNumberPokemonDown=10&displayNumberPokemonDownWaza=10&timeStamp=1466495098653"
            )

            val request = Request.Builder().url(URL).post(requestBody).build()

            val response = httpClient.newCall(request).execute()
            val str = response.body().string()

            if (str.equals("error")) {
                Log.e("doInBackground", str)
            } else {
                val moshi = Moshi.Builder().build()
                val adapter = moshi.adapter(RankingResponse::class.java)

                val rankingResponse = adapter.fromJson(str)

                val skills = ArrayList<Skill>()
                for (temp in rankingResponse.rankingPokemonTrend.wazaInfo) {
                    skills.add(databaseHelper.selectSkillByName(temp.name))
                }

                return TrendForBattle.create(rankingResponse.rankingPokemonTrend, skills)
            }
        } catch(e: IOException) {
            Log.e("doInBackground", "IOException")
        } catch(e: JSONException) {
            Log.e("doInBackground", "JSONException")
        } catch(e: Exception) {
            Log.e("doInBackground", e.message)
        }
        return TrendForBattle(listOf(), listOf(), listOf())
    }

    companion object {
        /**
         * PGLのURL
         */
        private val URL = "https://3ds.pokemon-gl.com/frontendApi/gbu/getSeasonPokemonDetail"
    }
}
