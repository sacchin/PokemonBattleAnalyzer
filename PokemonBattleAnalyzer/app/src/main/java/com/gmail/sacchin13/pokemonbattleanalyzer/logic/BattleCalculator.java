package com.gmail.sacchin13.pokemonbattleanalyzer.logic;

import android.util.Log;

import com.gmail.sacchin13.pokemonbattleanalyzer.BattleUtil;
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.IndividualPBAPokemon;
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.PokemonCharacteristic;
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Skill;
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl.RankingPokemonTrend;
import java.util.List;
import java.util.Map;

public class BattleCalculator {
    public enum RiskDegree{
        SAFE,
        LGTM,
        NORMAL,
        WARN,
        FATAL
    }

    private static final float[] damageRevesion = {1.0f, 0.99f, 0.98f, 0.97f, 0.96f, 0.95f,
            0.94f, 0.93f, 0.92f, 0.91f, 0.90f, 0.89f, 0.88f, 0.87f, 0.86f, 85f};

    public static void calcDamageRate(IndividualPBAPokemon attackSide, Skill skill, IndividualPBAPokemon deffenceSide){
//        Map<Float, Integer> maxDamage = deffenceSide.calcDamage(attackSide, skill);
//        int kakuitiCount = 0;
//        for (float revision : damageRevesion){
//            int remain = deffenceSide.getHPValue() - (int)(maxDamage * revision);
//            if(remain <= 0){
//                kakuitiCount++;
//            }
//        }
        //((attackLevel * 2 / 5 + 2) * skill.getPower() * attackValue) / (deffenceValue / 50 + 2);

    }

    public static RiskDegree getAttackOrder(IndividualPBAPokemon mine, IndividualPBAPokemon opponent){
//        float secondRate = 0;
//        RankingPokemonTrend trend = opponent.getTrend();
//        if(trend != null){
//            List<PokemonCharacteristic> opponentCharacteristic = trend.getCharacteristicList();
//            for (PokemonCharacteristic pc : opponentCharacteristic) {
//                IndividualPBAPokemon[] order = BattleUtil.getAttackOrder(mine.getCharacteristic().toString(), mine, pc, opponent);
//                if(order[1] == mine){
//                    secondRate += pc.getUsageRate();
//                }
//                Log.v(mine.getJname() + "(S:" + mine.getS() + ")",
//                        mine.getCharacteristic().toString() + " vs " + opponent.getJname() +
//                        "(S:" + opponent.getS() + ":" + (int)pc.getUsageRate() + "%)" + ":" + pc.getName());
//            }
//            return getRiskDegree(secondRate);
//        }else{
//            Log.e("getAttackOrder", "Trend is null!");
//        }
        return RiskDegree.SAFE;
    }

    public static RiskDegree getRiskDegree(float rate){
        if(rate <= 0) {
            return RiskDegree.SAFE;
        }else if(0 < rate && rate <= 30){
            return RiskDegree.LGTM;
        }else if(30 < rate && rate <= 60){
            return RiskDegree.NORMAL;
        }else if(60 < rate && rate <= 99){
            return RiskDegree.WARN;
        }else{
            return RiskDegree.FATAL;
        }
    }
}
