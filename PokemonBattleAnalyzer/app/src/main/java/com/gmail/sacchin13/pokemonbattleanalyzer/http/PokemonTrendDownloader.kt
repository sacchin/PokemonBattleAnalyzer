package com.gmail.sacchin13.pokemonbattleanalyzer.http

import android.os.AsyncTask
import android.util.Log
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl.RankingResponse
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl.TrendForBattle
import com.squareup.moshi.Moshi
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

class PokemonTrendDownloader(
        val pokemonNo: String,
        val index: Int,
        val listener: EventListener) : AsyncTask<Void, Void, TrendForBattle>() {

    interface EventListener {
        fun onFinish(result: TrendForBattle, index: Int)
    }

    override fun onPostExecute(result: TrendForBattle) {
        listener.onFinish(result, index)
    }

    override fun doInBackground(vararg params: Void?): TrendForBattle {
        var str = ""
        val ms = System.currentTimeMillis()
        try {
            val httpClient = OkHttpClient.Builder()
                    .addInterceptor {
                        it.proceed(it.request()
                                .newBuilder()
                                .addHeader("Referer", "https://3ds.pokemon-gl.com/battle/oras/")
                                .build())
                    }.build()
            val requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8"),
                    "languageId=1&seasonId=117&battleType=1&timezone=JST&pokemonId=$pokemonNo&displayNumberWaza=10&displayNumberTokusei=3" +
                            "&displayNumberSeikaku=10&displayNumberItem=10&displayNumberLevel=10&displayNumberPokemonIn=10&displayNumberPokemonDown=10" +
                            "&displayNumberPokemonDownWaza=10&timeStamp=$ms"
            )

            val request = Request.Builder().url(URL).post(requestBody).build()

            val response = httpClient.newCall(request).execute()
            str = response.body().string()

            if (str == "error") {
                Log.v("PokemonTrendDownloader", "stop $str")
            } else {
                val moshi = Moshi.Builder().build()
                val adapter = moshi.adapter(RankingResponse::class.java)

                val rankingResponse = adapter.fromJson(str)
                rankingResponse.rankingPokemonTrend.convertToFew()

                Log.v("doInBackground $pokemonNo", str)
                return TrendForBattle.create(rankingResponse.rankingPokemonTrend)
            }
        } catch(e: com.squareup.moshi.JsonDataException) {
            Log.e("moshi with $pokemonNo", str)
        } catch(e: Exception) {
            Log.e("doInBackground", e.toString())
        }
        return TrendForBattle(listOf(), listOf(), listOf(), listOf())
    }

    companion object {
        /**
         * PGLのURL
         */
        private val URL = "https://3ds.pokemon-gl.com/frontendApi/gbu/getSeasonPokemonDetail"
    }
}
