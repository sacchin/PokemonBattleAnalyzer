package com.gmail.sacchin13.pokemonbattleanalyzer

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Characteristic
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.IndividualPBAPokemon
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.PokemonCharacteristic

object BattleUtil {
    fun getAttackOrder(myCharacteristicName: String, mine: IndividualPBAPokemon,
                       opPc: PokemonCharacteristic, opponent: IndividualPBAPokemon): Array<IndividualPBAPokemon?> {
        val order = arrayOfNulls<IndividualPBAPokemon>(2)

        val mysp = mine.getSpeedValue(Characteristic.convertCharacteristicNameToNo(myCharacteristicName))
        val opposp = opponent.getSpeedValue(Characteristic.convertCharacteristicNameToNo(opPc.name))
        //        Log.v("getAttackOrder", "mine:" + mysp + ", oppo:" + opposp);
        if (opposp < mysp) {
            order[0] = mine
            order[1] = opponent
        } else {
            order[0] = opponent
            order[1] = mine
        }
        return order
    }
}
