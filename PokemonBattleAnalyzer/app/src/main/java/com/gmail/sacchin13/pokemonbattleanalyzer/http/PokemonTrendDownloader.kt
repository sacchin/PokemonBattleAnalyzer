package com.gmail.sacchin13.pokemonbattleanalyzer.http

import android.os.AsyncTask
import java.io.IOException
import org.json.JSONException

import android.util.Log
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl.RankingResponse
import com.squareup.moshi.Moshi
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

class PokemonTrendDownloader(
        val pokemonNo: String,
        val index: Int,
        val listener: EventListener): AsyncTask<Void, Void, String>(){

    interface EventListener{
        fun onFinish(result: RankingResponse, index: Int)
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)

        if(result.equals("error")){
            Log.e("onPostExecute", result)
        }else{
            val moshi = Moshi.Builder().build()
            val adapter = moshi.adapter(RankingResponse::class.java)

            val rankingResponse = adapter.fromJson(result)
            listener.onFinish(rankingResponse, index)
        }
    }

    override fun doInBackground(vararg params: Void?): String? {
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
            return response.body().string()

        } catch(e: IOException) {
            Log.e("doInBackground", "IOException")
        } catch(e: JSONException) {
            Log.e("doInBackground", "JSONException")
        } catch(e: Exception){
            Log.e("doInBackground", e.message)
        }
        return "error"
    }

    companion object {
        /**
         * PGLのURL
         */
        private val URL = "https://3ds.pokemon-gl.com/frontendApi/gbu/getSeasonPokemonDetail"
    }
}
