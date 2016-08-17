package com.gmail.sacchin13.pokemonbattleanalyzer.interfaces

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.IndividualPBAPokemon

interface AddToListInterface {
    fun addPokemonToList(pokemon: IndividualPBAPokemon)
    fun removePokemonFromList(pokemon: IndividualPBAPokemon)
}
