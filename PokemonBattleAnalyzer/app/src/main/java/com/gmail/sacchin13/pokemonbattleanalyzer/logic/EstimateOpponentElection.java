package com.gmail.sacchin13.pokemonbattleanalyzer.logic;

import android.util.Log;

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.IndividualPBAPokemon;
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Party;
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.PokemonCharacteristic;

import java.util.List;

public class EstimateOpponentElection {

    private static int[][] kumiawase = {
            {1,2,3},
            {1,2,4},
            {1,2,5},
            {1,2,6},
            {1,3,4},
            {1,3,5},
            {1,3,6},
            {1,4,5},
            {1,4,6},
            {1,5,6},
            {2,3,4},
            {2,3,5},
            {2,3,6},
            {2,4,5},
            {2,4,6},
            {2,5,6},
            {3,4,5},
            {3,4,6},
            {3,5,6},
            {4,5,6}
    };

    public static IndividualPBAPokemon[][] createAllPattern(Party mine, Party opponent){
        IndividualPBAPokemon[][] estimated = new IndividualPBAPokemon[20][3];
        for(int i = 0 ; i < 20 ; i++){
            for(int j = 0 ; j < 3 ; j++){
                estimated[i][j] = opponent.getMember().get(kumiawase[i][j] - 1);
            }
        }
        return estimated;
    }

    public void calc(IndividualPBAPokemon mine, IndividualPBAPokemon opponent){
//        if(opponent.getTrend() != null){
//            List<PokemonCharacteristic> c = opponent.getTrend().getCharacteristicList();
//
//            for(PokemonCharacteristic h : c){
//                Log.v("createAllPattern", h.getName() + ":" + h.getUsageRate() );
//            }
//        }
    }
}
