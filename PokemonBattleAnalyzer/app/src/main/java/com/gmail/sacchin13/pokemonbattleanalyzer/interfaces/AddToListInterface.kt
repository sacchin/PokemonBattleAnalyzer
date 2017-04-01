package com.gmail.sacchin13.pokemonbattleanalyzer.interfaces

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.realm.IndividualPokemon

interface AddToListInterface {
    fun addPokemonToList(pokemon: IndividualPokemon)
    fun removePokemonFromList(pokemon: IndividualPokemon)
}
