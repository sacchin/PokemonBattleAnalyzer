package com.gmail.sacchin13.pokemonbattleanalyzer.http;


import android.util.Log;

import com.gmail.sacchin13.pokemonbattleanalyzer.PartyDatabaseHelper;
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * MainActivityが開始した時に、全ポケモンの順位をダウンロードするクラス
 */
public class PokemonRankingDownloader implements Runnable {

    /**
     * PGLのURL
     */
    private final static String URL = "http://54.68.40.140:8080/getRankingJson";

    PartyDatabaseHelper databaseHelper;

    public PokemonRankingDownloader(PartyDatabaseHelper databaseHelper){
        this.databaseHelper = databaseHelper;
    }

    public String doPostToMyServer() {
        StringBuilder result = new StringBuilder();
//        DefaultHttpClient client = new DefaultHttpClient();
//
//        try {
//            HttpGet method = new HttpGet(URL);
//
//            HttpResponse response = client.execute(method);
//
//            switch (response.getStatusLine().getStatusCode()) {
//                case HttpStatus.SC_OK:
//                    result.append(EntityUtils.toString(response.getEntity(), "UTF-8"));
//                    break;
//                case HttpStatus.SC_NOT_FOUND:
//                    Log.e("doPostToPGL", "HttpStatus.SC_NOT_FOUND");
//                    break;
//                default:
//                    break;
//            }
//        } catch (ClientProtocolException e) {
//            Log.e("doPostToPGL", e.getMessage());
//        } catch (IOException e) {
//            Log.e("doPostToPGL", e.getMessage());
//        } catch (Exception e) {
//            Log.e("doPostToPGL", e.getMessage());
//        }

        return result.toString();
    }

    @Override
    public void run() {
//        String jsonStr = "";
//        try {
//            jsonStr = doPostToMyServer();
//            JSONArray temp = new JSONArray(jsonStr);
//            List<RankingPokemonIn> rankingList = JSONParser.Companion.createPokemonRankingList(temp);
//            databaseHelper.updatePBAPokemonRanking(rankingList);
//            Log.e("PokemonRankingDownloade", "finish");
//        } catch (JSONException e) {
//            Log.e("PokemonRankingDownloade", "JSONException : " + jsonStr);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}

