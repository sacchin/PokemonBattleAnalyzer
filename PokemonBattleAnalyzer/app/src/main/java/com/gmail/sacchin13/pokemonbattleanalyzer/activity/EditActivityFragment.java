package com.gmail.sacchin13.pokemonbattleanalyzer.activity;

import android.graphics.Bitmap;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.gmail.sacchin13.pokemonbattleanalyzer.PartyDatabaseHelper;
import com.gmail.sacchin13.pokemonbattleanalyzer.R;
import com.gmail.sacchin13.pokemonbattleanalyzer.Util;
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.IndividualPBAPokemon;
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Party;
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.PokemonCharacteristic;
import com.gmail.sacchin13.pokemonbattleanalyzer.listener.OnClickSaveMyParty;

import java.io.IOException;

/**
 * A placeholder fragment containing a simple view.
 */
public class EditActivityFragment extends Fragment {
    private static LinearLayout scrollView;
    private View rootView;
    private FloatingActionButton fab;
    private Party mine = null;
    private String[] item;
    private String[] skill;
    protected PartyDatabaseHelper databaseHelper = null;

    public EditActivityFragment() {
    }

    public static EditActivityFragment newInstance() {
        EditActivityFragment fragment = new EditActivityFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        databaseHelper = new PartyDatabaseHelper(getActivity());

        rootView = inflater.inflate(R.layout.fragment_edit, container, false);
        scrollView = (LinearLayout)rootView.findViewById(R.id.scrollView);

        fab = (FloatingActionButton) rootView.findViewById(R.id.first_fab);
        fab.setOnClickListener(new OnClickSaveMyParty(this));

        try {
            item = databaseHelper.selectAllItem().toArray(new String[1]);
            skill = databaseHelper.selectAllSkill().toArray(new String[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        resetPartyList();
    }

    private void resetPartyList() {
        if(scrollView != null){
            scrollView.removeAllViews();
        }

        try {
            mine = databaseHelper.selectMyParty();
            if (mine != null) {
                for (int i = 0; i < mine.getMember().size(); i++) {
                    IndividualPBAPokemon p = mine.getMember().get(i);
                    addPokemonBlock(scrollView, p);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addPokemonBlock(LinearLayout all, IndividualPBAPokemon pokemon){
        LinearLayout bloack = new LinearLayout(getActivity());
        bloack.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        bloack.setOrientation(LinearLayout.HORIZONTAL);

        Bitmap image = Util.Companion.createImage(pokemon.getMaster(), 250f, getResources());
        ImageView iv = new ImageView(getActivity());
        iv.setImageBitmap(image);
        bloack.addView(iv);

        TableLayout tableLayout = new TableLayout(getActivity());
        tableLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tableLayout.addView(createSpinnerRow("もちもの", item, pokemon.getItem()));
        tableLayout.addView(createSpinnerRow("せいかく", PokemonCharacteristic.Companion.getCHARACTERISTIC(), pokemon.getCharacteristic()));
        tableLayout.addView(createSpinnerRow("技1", skill, pokemon.getSkillNo1()));
        tableLayout.addView(createSpinnerRow("技2", skill, pokemon.getSkillNo2()));
        tableLayout.addView(createSpinnerRow("技3", skill, pokemon.getSkillNo3()));
        tableLayout.addView(createSpinnerRow("技4", skill, pokemon.getSkillNo4()));
        tableLayout.addView(createInputRow("H", true, pokemon.getHpEffortValue()));
        tableLayout.addView(createInputRow("A", true, pokemon.getAttackEffortValue()));
        tableLayout.addView(createInputRow("B", true, pokemon.getDeffenceEffortValue()));
        tableLayout.addView(createInputRow("C", true, pokemon.getSpecialAttackEffortValue()));
        tableLayout.addView(createInputRow("D", true, pokemon.getSpecialDeffenceEffortValue()));
        tableLayout.addView(createInputRow("S", true, pokemon.getSpeedEffortValue()));
        bloack.addView(tableLayout);

        all.addView(bloack);
    }

    private TableRow createSpinnerRow(String title, String[] items, String defaultStr){
        TableRow row = new TableRow(getActivity());
        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        TextView tv = new TextView(getActivity());
        tv.setText(title);
        row.addView(tv);

        Spinner sp = new Spinner(getActivity());
        ArrayAdapter<String> a = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, items);
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        int position = a.getPosition(defaultStr);
        sp.setAdapter(a);
        sp.setSelection(position);
        row.addView(sp);

        return row;
    }

    private TableRow createInputRow(String title, boolean onlyNumber, int defaultValue){
        TableRow row = new TableRow(getActivity());
        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        TextView tv = new TextView(getActivity());
        tv.setText(title);
        row.addView(tv);

        EditText editText = new EditText(getActivity());
        editText.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        if(onlyNumber){
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        if(isEffortValue(defaultValue)){
            editText.setText(String.valueOf(defaultValue));
        }
        row.addView(editText);

        return row;
    }

    public void parseAlInput(){
        for (int i = 0; i < scrollView.getChildCount() ; i++) {
            try {
                parseInput(i, (TableLayout)((LinearLayout) scrollView.getChildAt(i)).getChildAt(1));
            }catch (ParseException e){
                Snackbar.make(fab, e.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    public void parseInput(int index, TableLayout input) throws ParseException{
        String item = (String)((Spinner)((TableRow)input.getChildAt(0)).getChildAt(1)).getSelectedItem();
        String characteristic = (String)((Spinner)((TableRow)input.getChildAt(1)).getChildAt(1)).getSelectedItem();
        String skill1 = (String)((Spinner)((TableRow)input.getChildAt(2)).getChildAt(1)).getSelectedItem();
        String skill2 = (String)((Spinner)((TableRow)input.getChildAt(3)).getChildAt(1)).getSelectedItem();
        String skill3 = (String)((Spinner)((TableRow)input.getChildAt(4)).getChildAt(1)).getSelectedItem();
        String skill4 = (String)((Spinner)((TableRow)input.getChildAt(5)).getChildAt(1)).getSelectedItem();

        int h = getEffortValue(input, 6);
        int a = getEffortValue(input, 7);
        int b = getEffortValue(input, 8);
        int c = getEffortValue(input, 9);
        int d = getEffortValue(input, 10);
        int s = getEffortValue(input, 11);

        try {
            long id = mine.getMember().get(index).getId();
            databaseHelper.updateIndividualPBAPokemonData(id, item, characteristic, skill1, skill2, skill3, skill4,
                    h, a, b, c, d, s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getEffortValue(TableLayout input, int position) throws ParseException{
        String ev = ((EditText)((TableRow)input.getChildAt(position)).getChildAt(1)).getText().toString();
        if(ev.isEmpty()){
            throw new ParseException("未入力が存在します");
        }
        try {
            int value = Integer.parseInt(ev);
            if(isEffortValue(value)){
                return value;
            }else{
                throw new ParseException("努力値として不正な値です");
            }
        }catch (NumberFormatException e){
            throw new ParseException("努力値として不正な値です");
        }
    }

    public static boolean isEffortValue(int value){
        return (0 <= value && value < 253);
    }

    private class ParseException extends Exception {
        public ParseException(String message){
            super(message);
        }
    }
}
