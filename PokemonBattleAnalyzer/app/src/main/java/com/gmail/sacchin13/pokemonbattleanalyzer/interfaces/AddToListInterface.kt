package com.gmail.sacchin13.pokemonbattleanalyzer.interfaces

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.IndividualPokemon

interface AddToListInterface {
    fun addPokemonToList(pokemon: IndividualPokemon)
    fun removePokemonFromList(pokemon: IndividualPokemon)
}
