package com.gmail.sacchin13.pokemonbattleanalyzer.http;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

import com.gmail.sacchin13.pokemonbattleanalyzer.fragment.PGLFragment;
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl.RankingPokemonTrend;

import android.os.Handler;
import android.util.Log;

/**
 * ToolActivityが開始した時に、ポケモンのトレンドをダウンロードするクラス
 */
public class PokemonTrendDownloader implements Runnable {

    /**
     * PGLのURL
     */
    private final static String URL = "http://3ds.pokemon-gl.com/frontendApi/gbu/getSeasonPokemonDetail";

    private String pokemonNo = "";
    private int index = 0;
    private Handler handler = null;
    private PGLFragment fragment = null;

    /**
     * @param pokemonNo トレンドをダウンロードするポケモンのNo
     * @param fragment ダウンロードを行ったfragment
     * @param index パーティーの何番目のポケモンか？
     * @param handler 利用するhandler
     */
    public PokemonTrendDownloader(String pokemonNo, PGLFragment fragment, int index, Handler handler){
        this.pokemonNo = pokemonNo;
        this.index = index;
        this.handler = handler;
        this.fragment = fragment;
    }

    public String doPostToPGL(String pokemonNo) {
        StringBuilder result = new StringBuilder();
//        DefaultHttpClient client = new DefaultHttpClient();
//        List<NameValuePair> params = createParameter(pokemonNo);
//
//        try {
//            HttpPost method = new HttpPost(URL);
//            method.setHeader("Referer", "http://3ds.pokemon-gl.com/battle/");
//            method.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
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

//    private List<NameValuePair> createParameter(String pockemonNo) {
//        List<NameValuePair> params = new ArrayList<>();
//        params.add(new BasicNameValuePair("languageId", "1"));
//        params.add(new BasicNameValuePair("seasonId", "107"));
//        params.add(new BasicNameValuePair("battleType", "1"));
//        params.add(new BasicNameValuePair("timezone", "JST"));
//        params.add(new BasicNameValuePair("pokemonId", pockemonNo));
//        params.add(new BasicNameValuePair("displayNumberWaza", "10"));
//        params.add(new BasicNameValuePair("displayNumberTokusei", "3"));
//        params.add(new BasicNameValuePair("displayNumberSeikaku", "10"));
//        params.add(new BasicNameValuePair("displayNumberItem", "10"));
//        params.add(new BasicNameValuePair("displayNumberLevel", "10"));
//        params.add(new BasicNameValuePair("displayNumberPokemonIn", "10"));
//        params.add(new BasicNameValuePair("timeStamp", String.valueOf(System.currentTimeMillis())));
//        return params;
//    }

    public String doPostToMyServer(String pokemonNo) {
        return "";
    }

    @Override
    public void run() {
        String jsonStr = "";
        try {
            jsonStr = doPostToMyServer(pokemonNo);
            if(jsonStr != null){
                jsonStr = doPostToPGL(pokemonNo);
                if(jsonStr != null){
                    JSONObject j = new JSONObject(jsonStr);
                    JSONObject rankingPokemonTrend = j.getJSONObject("rankingPokemonTrend");
                    RankingPokemonTrend trend = RankingPokemonTrend.createRankingPokemonTrend(rankingPokemonTrend);
                    fragment.finishDownload(index, trend);
                }
            }
        } catch (JSONException e) {
            Log.e("PGLGetter[" + index + "]", "JSONException : " + jsonStr);
        }
        if(index == 0){
            handler.post(new Runnable() {
                public void run() {
                    fragment.setTrend();
                }
            });
        }else if(index == 5){
            handler.post(new Runnable() {
                public void run() {
                    fragment.finishAllDownload();
                }
            });
        }
    }
}
