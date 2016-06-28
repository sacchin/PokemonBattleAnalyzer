package com.gmail.sacchin13.pokemonbattleanalyzer.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.gmail.sacchin13.pokemonbattleanalyzer.PartyDatabaseHelper;
import com.gmail.sacchin13.pokemonbattleanalyzer.R;
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.IndividualPBAPokemon;
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Type;

import java.util.HashMap;
import java.util.Map;

public class AffinityFragment extends Fragment {
    private LinearLayout selectPokemon = null;
    private Map<String, Integer> affinityCode = null;

    private LinearLayout[] mainPokemonAffinity = null;
    private LinearLayout[] targetPokemonAffinity = null;

    private TableLayout typeList = null;
    private IndividualPBAPokemon p = null;

    /**
     *
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";


    public static AffinityFragment newInstance(String pokemonNo) {
        AffinityFragment fragment = new AffinityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SECTION_NUMBER, pokemonNo);
        fragment.setArguments(args);
        return fragment;
    }
    public AffinityFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_affinity, container, false);
        initView(rootView);

        affinityCode = new HashMap<>();
        affinityCode.put("400", 0);
        affinityCode.put("200", 1);
        affinityCode.put("50", 2);
        affinityCode.put("25", 3);
        affinityCode.put("0", 4);

        PartyDatabaseHelper databaseHelper = new PartyDatabaseHelper(getActivity());

        String pokemonNo = getArguments().getString(ARG_SECTION_NUMBER);
//        try {
//            this.p = databaseHelper.selectPBAPokemon(pokemonNo);
//            Map<Type.TypeCode, Map<String, Integer>> selectedPokemonAffinity = p.calcAllTypeScale();
//
//            setAffinity(selectedPokemonAffinity, mainPokemonAffinity);
//
//            List<PokemonMasterData> list = databaseHelper.selectAllPokemonMasterData();
//            List<AffinityRank> ranks = new ArrayList<>();
//            for(PBAPokemon pokemon : list){
//                Map<Type.TypeCode, Map<String, Integer>> targetPokemonAffinity = pokemon.calcAllTypeScale();
//
//                int sum = 0;
//                for(Type.TypeCode type : Type.TypeCode.values()){
//                    int minimumScale = Integer.MAX_VALUE;
//                    Map<String, Integer> selectedScale = selectedPokemonAffinity.get(type);
//                    for(String ability : selectedScale.keySet()){
//                        if(selectedScale.get(ability) < minimumScale){
//                            minimumScale = selectedScale.get(ability);
//                        }
//                    }
//                    Map<String, Integer> targetScale = targetPokemonAffinity.get(type);
//                    for(String ability : targetScale.keySet()){
//                        if(targetScale.get(ability) < minimumScale){
//                            minimumScale = targetScale.get(ability);
//                        }
//                    }
//                    sum += minimumScale;
//                }
//                ranks.add(new AffinityRank(sum, pokemon));
//            }
//
//            AffinityRank.Companion.sort(ranks);
//            AffinityRank.Companion.calcDeviation(ranks);
//
//            for (AffinityRank r : ranks) {
//                TableRow lastBlock = (TableRow)typeList.getChildAt(typeList.getChildCount() - 1);
//                TextView deviationValue = (TextView)lastBlock.getChildAt(0);
//                if(deviationValue != null){
//                    try {
//                        int deviation = Integer.parseInt((String)deviationValue.getText());
//                        if(r.getDeviation() == deviation){
//                            LinearLayout pokemons = (LinearLayout)lastBlock.getChildAt(1);
//                            //既存のリストに追加する
//                            LinearLayout pokemonBlock = (LinearLayout)pokemons.getChildAt(pokemons.getChildCount() - 1);
//                            //一行10匹まで
//                            if(pokemonBlock.getChildCount() < 10){
//                                pokemonBlock.addView(createImageView(r.getPokemon()));
//                            }else{
//                                pokemonBlock = createPokemonBlock();
//                                pokemonBlock.addView(createImageView(r.getPokemon()));
//
//                                pokemons.addView(pokemonBlock);
//                            }
//                        }else{
//                            //新規のリストに追加する
//                            lastBlock = createTableBlock();
//                            deviationValue = new TextView(getActivity());
//                            deviationValue.setText(String.valueOf(r.getDeviation()));
//                            lastBlock.addView(deviationValue);
//
//                            LinearLayout pokemonBlock = createPokemonBlock();
//                            pokemonBlock.addView(createImageView(r.getPokemon()));
//
//                            LinearLayout pokemons = createPokemons();
//                            pokemons.addView(pokemonBlock);
//                            lastBlock.addView(pokemons);
//                            typeList.addView(lastBlock);
//                        }
//                    }catch (NumberFormatException e){
//                        //新規のリストに追加する
//                        lastBlock = createTableBlock();
//                        deviationValue = new TextView(getActivity());
//                        deviationValue.setText(String.valueOf(r.getDeviation()));
//                        lastBlock.addView(deviationValue);
//
//                        LinearLayout pokemonBlock = createPokemonBlock();
//                        pokemonBlock.addView(createImageView(r.getPokemon()));
//
//                        LinearLayout pokemons = createPokemons();
//                        pokemons.addView(pokemonBlock);
//                        lastBlock.addView(pokemons);
//                        typeList.addView(lastBlock);
//                    }
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return rootView;
    }

//    public ImageView createImageView(PBAPokemon pokemon){
//        Bitmap image = Util.Companion.createImage(pokemon, 100f, getResources());
//        ImageView iv = new ImageView(getActivity());
//        iv.setImageBitmap(image);
//        iv.setOnClickListener(new OnClickFromAffinityList(this, pokemon));
//        return iv;
//    }

    public void initView(View rootView){
        selectPokemon = (LinearLayout)rootView.findViewById(R.id.select_pokemon);
        typeList = (TableLayout)rootView.findViewById(R.id.type_list);

        mainPokemonAffinity = new LinearLayout[5];
        mainPokemonAffinity[0] = (LinearLayout)rootView.findViewById(R.id.main_type_of_4);
        mainPokemonAffinity[1] = (LinearLayout)rootView.findViewById(R.id.main_type_of_2);
        mainPokemonAffinity[2] = (LinearLayout)rootView.findViewById(R.id.main_type_of_h);
        mainPokemonAffinity[3] = (LinearLayout)rootView.findViewById(R.id.main_type_of_q);
        mainPokemonAffinity[4] = (LinearLayout)rootView.findViewById(R.id.main_type_of_z);

        targetPokemonAffinity = new LinearLayout[5];
        targetPokemonAffinity[0] = (LinearLayout)rootView.findViewById(R.id.target_type_of_4);
        targetPokemonAffinity[1] = (LinearLayout)rootView.findViewById(R.id.target_type_of_2);
        targetPokemonAffinity[2] = (LinearLayout)rootView.findViewById(R.id.target_type_of_h);
        targetPokemonAffinity[3] = (LinearLayout)rootView.findViewById(R.id.target_type_of_q);
        targetPokemonAffinity[4] = (LinearLayout)rootView.findViewById(R.id.target_type_of_z);

        Button back = (Button) rootView.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

//    public void setTypeView(PBAPokemon pokemon){
//        for(LinearLayout temp : targetPokemonAffinity){
//            temp.removeAllViews();
//        }
//
//        Map<Type.TypeCode, Map<String, Integer>> resultMap = pokemon.calcAllTypeScale();
//        setAffinity(resultMap, targetPokemonAffinity);
//    }

    public void setAffinity(Map<Type.Code, Map<String, Integer>> affinityMap, LinearLayout[] targetLayout){
        for(Type.Code type : affinityMap.keySet()){
            Map<String, Integer> ttt = affinityMap.get(type);
            for(String key : ttt.keySet()){
                Integer p = affinityCode.get(String.valueOf(ttt.get(key)));
                if(p != null) {
                    TextView temp = new TextView(getActivity());
                    if(key.equals("both")){
                        temp.setText(Type.INSTANCE.name(type));
                    }else{
                        String message = Type.INSTANCE.name(type) + " (" + key + ")";
                        temp.setText(message);
                    }
                    targetLayout[p].addView(temp);
                }
            }
        }
    }

    public LinearLayout createPokemonBlock(){
        LinearLayout pokemonBlock = new LinearLayout(getActivity());
        pokemonBlock.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        pokemonBlock.setGravity(Gravity.CENTER);
        pokemonBlock.setOrientation(LinearLayout.HORIZONTAL);
        return  pokemonBlock;
    }

    public LinearLayout createPokemons(){
        LinearLayout pokemons = new LinearLayout(getActivity());
        pokemons.setOrientation(LinearLayout.VERTICAL);
        return  pokemons;
    }

    public TableRow createTableBlock(){
        TableRow lastBlock = new TableRow(getActivity());
        lastBlock.setOrientation(LinearLayout.HORIZONTAL);
        lastBlock.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        return lastBlock;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(selectPokemon != null){
//            Bitmap image = Util.Companion.createImage(p, 150f, getResources());
//            ImageView imageView = new ImageView(getActivity());
//            imageView.setImageBitmap(image);
//            selectPokemon.addView(imageView);
        }
    }
}
