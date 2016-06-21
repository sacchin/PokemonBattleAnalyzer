package com.gmail.sacchin13.pokemonbattleanalyzer.interfaces

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.PBAPokemon

interface AddToListInterface {
    fun addPokemonToList(pokemon: PBAPokemon)
    fun removePokemonFromList(pokemon: PBAPokemon)
}
