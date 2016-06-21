package com.gmail.sacchin13.pokemonbattleanalyzer.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gmail.sacchin13.pokemonbattleanalyzer.R;
import com.gmail.sacchin13.pokemonbattleanalyzer.logic.EstimateOpponentElection;
import com.gmail.sacchin13.pokemonbattleanalyzer.activity.SelectActivity;
import com.gmail.sacchin13.pokemonbattleanalyzer.insert.PartyInsertHandler;
import com.gmail.sacchin13.pokemonbattleanalyzer.interfaces.AddToListInterface;
import com.gmail.sacchin13.pokemonbattleanalyzer.PartyDatabaseHelper;
import com.gmail.sacchin13.pokemonbattleanalyzer.Util;
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.IndividualPBAPokemon;
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.PBAPokemon;
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Party;
import com.gmail.sacchin13.pokemonbattleanalyzer.listener.OnClickFromList;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SelectFragment extends PGLFragment implements AddToListInterface {
    private PartyDatabaseHelper databaseHelper = null;

    private Party mine = null;

    private LinearLayout selected = null;
    private LinearLayout estimate = null;

    private ImageView myParty[] = null;
    private ImageView opponentParty[] = null;

    private Party choicedPokemon = null;

    protected ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public static SelectFragment newInstance(int sectionNumber) {
        SelectFragment fragment = new SelectFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public SelectFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_select, container, false);
//        selected = (LinearLayout) rootView.findViewById(R.id.selected);
//        estimate = (LinearLayout) rootView.findViewById(R.id.opponent);
//
//        initPartyList(rootView);
//
//        databaseHelper = new PartyDatabaseHelper(getActivity());
//
//        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.first_fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(choicedPokemon == null || choicedPokemon.getMember().size() != 3){
//                    Snackbar.make(estimate, "3体選択して下さい。", Snackbar.LENGTH_SHORT).show();
//                }else{
//                    choicedPokemon.setTime(new Timestamp(System.currentTimeMillis()));
//                    executorService.execute(new PartyInsertHandler(databaseHelper, choicedPokemon, false));
//                    ((SelectActivity)getActivity()).startToolActivity();
//                }
//            }
//        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        resetParty(false);
        createPartyList();
    }

    private void initPartyList(View rootView) {
        myParty = new ImageView[6];
//        myParty[0] = (ImageView) rootView.findViewById(R.id.my_party1);
//        myParty[1] = (ImageView) rootView.findViewById(R.id.my_party2);
//        myParty[2] = (ImageView) rootView.findViewById(R.id.my_party3);
//        myParty[3] = (ImageView) rootView.findViewById(R.id.my_party4);
//        myParty[4] = (ImageView) rootView.findViewById(R.id.my_party5);
//        myParty[5] = (ImageView) rootView.findViewById(R.id.my_party6);
//        opponentParty = new ImageView[6];
//        opponentParty[0] = (ImageView) rootView.findViewById(R.id.opponent_party1);
//        opponentParty[1] = (ImageView) rootView.findViewById(R.id.opponent_party2);
//        opponentParty[2] = (ImageView) rootView.findViewById(R.id.opponent_party3);
//        opponentParty[3] = (ImageView) rootView.findViewById(R.id.opponent_party4);
//        opponentParty[4] = (ImageView) rootView.findViewById(R.id.opponent_party5);
//        opponentParty[5] = (ImageView) rootView.findViewById(R.id.opponent_party6);
    }

    private void createPartyList() {
//        try {
//            if (party != null) {
//                for (int i = 0; i < party.getMember().size(); i++) {
//                    IndividualPBAPokemon p = party.getMember().get(i);
//                    Bitmap image = Util.Companion.createImage(p.getMaster(), 250f, getResources());
//                    opponentParty[i].setImageBitmap(image);
//                }
//            }
//
//            mine = databaseHelper.selectMyParty();
//            if (mine != null) {
//                for (int i = 0; i < mine.getMember().size(); i++) {
//                    IndividualPBAPokemon p = mine.getMember().get(i);
//                    Bitmap image = Util.Companion.createImage(p.getMaster(), 250f, getResources());
//                    myParty[i].setImageBitmap(image);
//                    myParty[i].setOnClickListener(new OnClickFromList(this, p.getMaster()));
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private void determineOpponent(){
        estimate.removeAllViews();
        IndividualPBAPokemon[][] estimated = EstimateOpponentElection.createAllPattern(mine, party);
//        for(IndividualPBAPokemon[] p : estimated){
//            addPokemonToOpponentParty(p);
//        }
//        addPokemonToOpponentParty(estimated[0]);
    }

    @Override
    public void addPokemonToList(PBAPokemon pokemon) {
//        IndividualPBAPokemon ip = new IndividualPBAPokemon(pokemon);
//        if (choicedPokemon == null) {
//            choicedPokemon = new Party(null, null, null, null, null, null, null, "", "");
//            choicedPokemon.setUserName("choiced");
//        } else if (choicedPokemon.getMember().size() == 3) {
//            Snackbar.make(estimate, "すでに3体選択しています。", Snackbar.LENGTH_SHORT).show();
//            return;
//        }
//        choicedPokemon.getMember().add(ip);
//
//        Bitmap temp = Util.Companion.createImage(pokemon, 120f, getResources());
//        ImageView localView = new ImageView(getActivity());
//        localView.setImageBitmap(temp);
//        selected.addView(localView);
    }

    public void addPokemonToOpponentParty(final PBAPokemon[] pokemons) {
        LinearLayout l = new LinearLayout(getActivity());
        l.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        l.setOrientation(LinearLayout.HORIZONTAL);
//        l.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, pokemons[2].getJname(), Snackbar.LENGTH_SHORT).show();
//            }
//        });

        for(PBAPokemon p : pokemons){
            IndividualPBAPokemon ip = new IndividualPBAPokemon(p);//, 0, new Timestamp(System.currentTimeMillis()), "", "", "", "", "", "", "");

            Bitmap temp = Util.Companion.createImage(p, 120f, getResources());
            ImageView localView = new ImageView(getActivity());
            localView.setImageBitmap(temp);

            l.addView(localView);
        }
        estimate.addView(l);
    }

    @Override
    public void removePokemonFromList(PBAPokemon pokemon) {

    }

    @Override
    public void setTrend(){
    }
    @Override
    public void finishAllDownload() {
        determineOpponent();
    }

}
