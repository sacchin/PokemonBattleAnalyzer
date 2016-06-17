package com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;

public class RankingPokemonIn {

    private Timestamp updateTime;
    private int ranking;
    private String pokemonNo;

    public static RankingPokemonIn createRankingPokemon(JSONObject rankingPokemon){
        try {
            String time = rankingPokemon.getString("time");
            Timestamp updateTIme = Timestamp.valueOf(time);
            int ranking = rankingPokemon.getInt("ranking");
            String pokemonNo = rankingPokemon.getString("pokemon_no");

            RankingPokemonIn obj = new RankingPokemonIn(updateTIme, ranking, pokemonNo);
            return obj;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public RankingPokemonIn(Timestamp updateTime, int ranking, String pokemonNo){
        this.pokemonNo = pokemonNo;
        this.updateTime = updateTime;
        this.ranking = ranking;
    }

    public String getPokemonNo() {
        return pokemonNo;
    }

    public int getRanking() {

        return ranking;
    }

    public Timestamp getUpdateTime() {

        return updateTime;
    }
}
