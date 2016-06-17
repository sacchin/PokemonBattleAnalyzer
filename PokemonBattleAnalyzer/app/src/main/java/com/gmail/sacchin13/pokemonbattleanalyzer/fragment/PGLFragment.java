package com.gmail.sacchin13.pokemonbattleanalyzer.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.sacchin13.pokemonbattleanalyzer.PartyDatabaseHelper;
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.IndividualPBAPokemon;
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Party;
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl.RankingPokemonTrend;
import com.gmail.sacchin13.pokemonbattleanalyzer.http.PokemonTrendDownloader;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class PGLFragment extends Fragment {
    protected PartyDatabaseHelper databaseHelper = null;
    protected Party party = null;

    protected ExecutorService executorService = Executors.newCachedThreadPool();

    public PGLFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        databaseHelper = new PartyDatabaseHelper(getActivity());
        return null;
    }

    protected void resetParty(boolean downloadTrend) {
        try {
            party = databaseHelper.selectOpponentParty();
            if(downloadTrend){
                downloadTrend();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void downloadTrend() {
        final Handler handler = new Handler();
        for(int i = 0 ; i < party.getMember().size() ; i++){
            IndividualPBAPokemon p = party.getMember().get(i);
            String pokemonNo = p.getNo();
            if(!pokemonNo.contains("-")){
                try {
                    pokemonNo = String.valueOf(Integer.parseInt(p.getNo())) + "-0";
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            executorService.execute(
                    new PokemonTrendDownloader(pokemonNo, this, i, handler));
        }
    }

    public IndividualPBAPokemon getIndividualPBAPokemon(int index){
        return party.getMember().get(index);
    }

    public void finishDownload(int index, RankingPokemonTrend trend){
        if(party == null || party.getMember() == null || party.getMember().size() < index){
            return;
        }
        party.getMember().get(index).setTrend(trend);
    }

    public abstract void finishAllDownload();
    public abstract void setTrend();

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
