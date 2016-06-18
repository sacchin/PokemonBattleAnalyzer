package com.gmail.sacchin13.pokemonbattleanalyzer.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.gmail.sacchin13.pokemonbattleanalyzer.R;
import com.gmail.sacchin13.pokemonbattleanalyzer.Util;
import com.gmail.sacchin13.pokemonbattleanalyzer.activity.DetailActivity;
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.IndividualPBAPokemon;
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.PBAPokemon;
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Pokemon;
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl.RankingPokemonTrend;

import java.util.TreeMap;

public class DetailActivityFragment extends PGLFragment {

    private long id;
    private TableLayout skilltl = null;
    private TableLayout characteristictl = null;
    private TableLayout itemtl = null;
    private TableLayout abilitytl = null;
    private ImageView iv;
    private LinearLayout mainView = null;
    private CollapsingToolbarLayout toolbar = null;

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        iv = (ImageView)view.findViewById(R.id.image);
        skilltl = (TableLayout)view.findViewById(R.id.skilltable);
        characteristictl = (TableLayout)view.findViewById(R.id.characteristictable);
        itemtl = (TableLayout)view.findViewById(R.id.itemtable);
        abilitytl = (TableLayout)view.findViewById(R.id.abilitytable);

        mainView = (LinearLayout)view.findViewById(R.id.statusview);
        toolbar = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        toolbar.setCollapsedTitleTextColor(Color.WHITE);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DetailActivity da = (DetailActivity)getActivity();
        if(da.getId() != -1){
            this.id = da.getId();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        resetParty(true);
        if(party != null && party.getMember() != null){
            for(IndividualPBAPokemon p : party.getMember()){
                if(p.getId() == this.id){
                    iv.setImageResource(p.getMaster().getResourceId());
                    break;
                }
            }
        }
    }

    @Override
    public void finishAllDownload() {
        for(IndividualPBAPokemon p : party.getMember()){
            if(p.getId() == this.id){
                setTrend(p);
                setMainView(p);
                toolbar.setTitle(p.getMaster().getMasterRecord().getJname());
                break;
            }
        }
    }

    @Override
    public void setTrend() {}

    public void setTrend(IndividualPBAPokemon p) {
        skilltl.removeAllViews();
        characteristictl.removeAllViews();
        itemtl.removeAllViews();
        abilitytl.removeAllViews();

        RankingPokemonTrend trend = new RankingPokemonTrend(null, null, null, null);//p.getTrend();
        if(trend == null){
            String nullText[] = {"-", "-"};
            skilltl.addView(createTableRow(nullText, 0, Color.TRANSPARENT, Color.BLACK, 12));
        }else{
            TreeMap<String, String[]> skill = trend.createSkillMap();
            for(String key : skill.keySet()){
                String[] temp = skill.get(key).clone();
                temp[1] = temp[1] + "%";
                skilltl.addView(createTableRow(temp, 0, Color.TRANSPARENT, Color.BLACK, 12));
            }
        }

        if(trend == null){
            String nullText[] = {"-", "-"};
            characteristictl.addView(createTableRow(nullText, 0, Color.TRANSPARENT, Color.BLACK, 12));
        }else{
            TreeMap<String, String[]> characteristic = trend.createCharacteristicMap();
            for(String key : characteristic.keySet()){
                String[] temp = characteristic.get(key).clone();
                temp[1] = temp[1] + "%";
                characteristictl.addView(createTableRow(temp, 0, Color.TRANSPARENT, Color.BLACK, 12));
            }
        }

        if(trend == null){
            String nullText[] = {"-", "-"};
            itemtl.addView(createTableRow(nullText, 0, Color.TRANSPARENT, Color.BLACK, 12));
        }else{
            TreeMap<String, String[]> item = trend.createItemMap();
            for(String key : item.keySet()){
                String[] temp = item.get(key).clone();
                temp[1] = temp[1] + "%";
                itemtl.addView(createTableRow(temp, 0, Color.TRANSPARENT, Color.BLACK, 12));
            }
        }

        if(trend == null){
            String nullText[] = {"-", "-"};
            abilitytl.addView(createTableRow(nullText, 0, Color.TRANSPARENT, Color.BLACK, 12));
        }else{
            TreeMap<String, String[]> ability = trend.createAbilityMap();
            for(String key : ability.keySet()){
                String[] temp = ability.get(key).clone();
                temp[1] = temp[1] + "%";
                abilitytl.addView(createTableRow(temp, 0, Color.TRANSPARENT, Color.BLACK, 12));
            }
        }
    }

    public TableRow createTableRow(String[] texts, int layoutSpan, int bgColor, int txtColor, int txtSize){
        TableRow row = new TableRow(getActivity());
        TableRow.LayoutParams p = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        if(0 < layoutSpan){
            p.span = layoutSpan;
        }
        row.setLayoutParams(p);
        for(String temp : texts){
            if(layoutSpan <= 0){
                row.addView(createTextView(temp, bgColor, txtColor, txtSize));
            }else{
                row.addView(createTextView(temp, bgColor, txtColor, txtSize), p);
            }
        }
        return row;
    }

    public TextView createTextView(String text, int bgColor, int txtColor, int txtSize){
        TextView tv = new TextView(getActivity());
        tv.setText(text);
        tv.setBackgroundColor(bgColor);
        tv.setTextColor(txtColor);
        tv.setTextSize(txtSize);
        TableRow.LayoutParams p = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(p);
        return tv;
    }

    public void setMainView(IndividualPBAPokemon p) {
        mainView.removeAllViews();

        createPBAPokemonStatus(p.getMaster());
        if(p.getMaster().getMasterRecord().getMega() != null){
            for(Pokemon mega : p.getMaster().getMasterRecord().getMega()){
                //createPBAPokemonStatus(mega);
            }
        }
    }

    private void createPBAPokemonStatus(PBAPokemon po) {

        Pokemon p = po.getMasterRecord();

        Log.e("createPBAPokemonStatus", p.getJname());
        LinearLayout sss = new LinearLayout(getActivity());
        sss.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        sss.setOrientation(LinearLayout.HORIZONTAL);

        Bitmap temp = Util.Companion.createImage(po, 150f, getResources());
        ImageView imageView = new ImageView(getActivity());
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setImageBitmap(temp);
        sss.addView(imageView);

        TableLayout status = new TableLayout(getActivity());
        status.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        status.setStretchAllColumns(true);

        String headers[] = {"H", "A", "B", "C", "D", "S"};
        status.addView(createTableRow(headers, 0, Color.TRANSPARENT, Color.GRAY, 12));

        String statuses[] = {String.valueOf(p.getH()), String.valueOf(p.getA()), String.valueOf(p.getB()),
                String.valueOf(p.getC()), String.valueOf(p.getD()), String.valueOf(p.getS())};
        status.addView(createTableRow(statuses, 0, Color.TRANSPARENT, Color.BLACK, 18));

        String characteristics[] = {p.getAbility1(), p.getAbility2(), p.getAbilityd()};
        status.addView(createTableRow(characteristics, 2, Color.TRANSPARENT, Color.BLACK, 12));

        sss.addView(status);

        mainView.addView(sss);
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
