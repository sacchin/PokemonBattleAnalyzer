package com.gmail.sacchin13.pokemonbattleanalyzer.listener

import com.gmail.sacchin13.pokemonbattleanalyzer.interfaces.AddToListInterface
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.PBAPokemon

import android.view.View
import android.view.View.OnClickListener
import kotlin.properties.Delegates

class OnClickFromList(someFragment: AddToListInterface, pokemon: PBAPokemon) : OnClickListener {
    private var pokemon: PBAPokemon by Delegates.notNull()
    private var someFragment: AddToListInterface by Delegates.notNull()

    init {
        this.pokemon = pokemon
        this.someFragment = someFragment
    }

    override fun onClick(v: View) {
        someFragment!!.addPokemonToList(pokemon)
    }
}
