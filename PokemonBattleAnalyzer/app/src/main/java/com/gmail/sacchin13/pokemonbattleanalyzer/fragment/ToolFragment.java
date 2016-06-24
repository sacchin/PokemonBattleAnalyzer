package com.gmail.sacchin13.pokemonbattleanalyzer.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.gmail.sacchin13.pokemonbattleanalyzer.R;
import com.gmail.sacchin13.pokemonbattleanalyzer.activity.DetailActivity;
import com.gmail.sacchin13.pokemonbattleanalyzer.logic.BattleCalculator;
import com.gmail.sacchin13.pokemonbattleanalyzer.Util;
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.IndividualPBAPokemon;
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Party;
import com.gmail.sacchin13.pokemonbattleanalyzer.listener.OnClickChoicedPokemon;
import com.gmail.sacchin13.pokemonbattleanalyzer.listener.OnClickIndividualPokemon;

import java.io.IOException;

public class ToolFragment extends PGLFragment {
    private int index = 0;

    private LinearLayout partyLayout = null;
    private LinearLayout choicedLayout = null;
    private TableLayout status = null;
    private ImageView selected = null;

    private Party choiced = null;

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public static ToolFragment newInstance(int sectionNumber) {
        ToolFragment fragment = new ToolFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    public ToolFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_tool, container, false);
        status = (TableLayout) rootView.findViewById(R.id.status);
        partyLayout = (LinearLayout)rootView.findViewById(R.id.party);
        choicedLayout = (LinearLayout)rootView.findViewById(R.id.choiced);
        selected = (ImageView)rootView.findViewById(R.id.selected_image);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        resetParty(true);
        resetMyParty();
        createPartyList();
    }

    protected void resetMyParty() {
        try {
            choiced = databaseHelper.selectMyChoicedParty();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createPartyList() {
        if(partyLayout == null){
            Log.e("createPartyList", "null!");
            return;
        }else{
            partyLayout.removeAllViews();
        }

//        if(party != null){
//            for (int i = 0; i < party.getMember().size(); i++) {
//                IndividualPBAPokemon p = party.getMember().get(i);
//                FrameLayout frame = createFrameLayout(p.getMaster(), 210f);
//                frame.setOnClickListener(new OnClickIndividualPokemon(this, i, (ImageView)frame.getChildAt(0)));
//                partyLayout.addView(frame);
//            }
//        }

        if(choicedLayout == null){
            Log.e("choicedLayout", "null!");
            return;
        }else{
            choicedLayout.removeAllViews();
        }

//        if(choiced != null){
//            for (int i = 0; i < choiced.getMember().size(); i++) {
//                IndividualPBAPokemon p = choiced.getMember().get(i);
//                FrameLayout frame = createFrameLayout(p.getMaster(), 280f);
//                frame.setOnClickListener(new OnClickChoicedPokemon(this, i));
//                choicedLayout.addView(frame);
//            }
//        }
    }

    public FrameLayout createFrameLayout(IndividualPBAPokemon p, float size){
        FrameLayout fl = new FrameLayout(getActivity());

        Bitmap temp = Util.Companion.createImage(p.getMaster(), size, getResources());
        ImageView localView = new ImageView(getActivity());
        localView.setImageBitmap(temp);
        localView.setTransitionName("image");
        fl.addView(localView);

        return fl;
    }

    public IndividualPBAPokemon getIndividualPBAPokemon(int index){
        return null;//party.getMember().get(index);
    }

    @Override
    public void finishAllDownload() {

    }

    public void setIndex(int index){
        this.index = index;
    }

    @Override
    public void setTrend(){

    }

    public void setAlert(int index){
//        IndividualPBAPokemon tapped = choiced.getMember().get(index);
//        if(party != null){
//            for (int i = 0; i < party.getMember().size(); i++) {
//                IndividualPBAPokemon p = party.getMember().get(i);
//                FrameLayout frame = (FrameLayout)partyLayout.getChildAt(i);
//                if(1 < frame.getChildCount()){
//                    frame.removeViewAt(frame.getChildCount() - 1);
//                }
//                Bitmap over = null;
//                ImageView overView = new ImageView(getActivity());
//                switch (BattleCalculator.getAttackOrder(tapped, p)){
//                    case SAFE:
//                        //set no frame
//                        break;
//                    case LGTM:
//                        over = Util.Companion.createImage(R.drawable.safe, 210f, getResources());
//                        break;
//                    case FATAL:
//                        over = Util.Companion.createImage(R.drawable.caution, 210f, getResources());
//                        break;
//                    default:
//                        over = Util.Companion.createImage(R.drawable.alert, 210f, getResources());
//                        break;
//                }
//                if(over != null){
//                    overView.setImageBitmap(over);
//                }
//                frame.addView(overView);
//            }
//        }
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

    public void setMainView(int index){
//        IndividualPBAPokemon tapped = choiced.getMember().get(index);
//        selected.setImageBitmap(Util.Companion.createImage(tapped.getMaster(), 210f, getResources()));
//
//        String headers[] = {"", "H", "A", "B", "C", "D", "S"};
//        status.addView(createTableRow(headers, 0, Color.TRANSPARENT, Color.GRAY, 12));
//
//        String base[] = {"種族", String.valueOf(tapped.getMaster().getMasterRecord().getH()), String.valueOf(tapped.getMaster().getMasterRecord().getA()), String.valueOf(tapped.getMaster().getMasterRecord().getB()),
//                String.valueOf(tapped.getMaster().getMasterRecord().getC()), String.valueOf(tapped.getMaster().getMasterRecord().getD()), String.valueOf(tapped.getMaster().getMasterRecord().getS())};
//        status.addView(createTableRow(base, 0, Color.TRANSPARENT, Color.BLACK, 18));
//
//        String effort[] = {"努力", String.valueOf(tapped.getHpEffortValue()), String.valueOf(tapped.getAttackEffortValue()),
//                String.valueOf(tapped.getDeffenceEffortValue()), String.valueOf(tapped.getSpecialAttackEffortValue()),
//                String.valueOf(tapped.getSpecialDeffenceEffortValue()), String.valueOf(tapped.getSpeedEffortValue())};
//        status.addView(createTableRow(effort, 0, Color.TRANSPARENT, Color.BLACK, 18));
//
//        String actual[] = {"実数", String.valueOf(tapped.getHpEffortValue()), String.valueOf(tapped.getAttackValue()), String.valueOf(tapped.getDeffenceValue()),
//                String.valueOf(tapped.getSpecialAttackValue()), String.valueOf(tapped.getSpecialDeffenceValue()), String.valueOf(tapped.getSpeedValue())};
//        status.addView(createTableRow(actual, 0, Color.TRANSPARENT, Color.BLACK, 18));


//        if(party != null){
//            for (int i = 0; i < party.getMember().size(); i++) {
//                IndividualPBAPokemon p = party.getMember().get(i);
//                FrameLayout frame = (FrameLayout)partyLayout.getChildAt(i);
//                if(1 < frame.getChildCount()){
//                    frame.removeViewAt(frame.getChildCount() - 1);
//                }
//                Bitmap over = null;
//                ImageView overView = new ImageView(getActivity());
//                switch (BattleCalculator.getAttackOrder(tapped, p)){
//                    case SAFE:
//                        //set no frame
//                        break;
//                    case LGTM:
//                        over = Util.createImage(R.drawable.safe, 210f, getResources());
//                        break;
//                    case FATAL:
//                        over = Util.createImage(R.drawable.caution, 210f, getResources());
//                        break;
//                    default:
//                        over = Util.createImage(R.drawable.alert, 210f, getResources());
//                        break;
//                }
//                if(over != null){
//                    overView.setImageBitmap(over);
//                }
//                frame.addView(overView);
//            }
//        }
    }

    public void startDetailActivity(IndividualPBAPokemon pokemon, ImageView from){
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("id", pokemon.getId());
        startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(getActivity(), from, "image").toBundle());
    }

    @Override
    public void onStop() {
        super.onStop();
        if(status != null){
            status.removeAllViews();
            status = null;
        }
    }
}
