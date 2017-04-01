package com.gmail.sacchin13.pokemonbattleanalyzer.entity.realm

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.ui.PartyForUI
import io.realm.RealmObject
import io.realm.annotations.Ignore
import java.util.*

open class Party(
        open var time: Long = System.currentTimeMillis(),
        open var userName: String = "none",
        open var memo: String = "none",
        open var member1: IndividualPokemon = IndividualPokemon.create(-1, PokemonMasterData()),
        open var member2: IndividualPokemon = IndividualPokemon.create(-1, PokemonMasterData()),
        open var member3: IndividualPokemon = IndividualPokemon.create(-1, PokemonMasterData()),
        open var member4: IndividualPokemon = IndividualPokemon.create(-1, PokemonMasterData()),
        open var member5: IndividualPokemon = IndividualPokemon.create(-1, PokemonMasterData()),
        open var member6: IndividualPokemon = IndividualPokemon.create(-1, PokemonMasterData())
) : RealmObject() {

    @Ignore
    var member: MutableList<PokemonMasterData> = ArrayList<PokemonMasterData>()

    fun uiObject(): PartyForUI {
        return PartyForUI(time, userName, memo,
                member1.uiObject(), member2.uiObject(), member3.uiObject(),
                member4.uiObject(), member5.uiObject(), member6.uiObject())
    }

    fun initMember() {
        addMember(member1.master)
        addMember(member2.master)
        addMember(member3.master)
        addMember(member4.master)
        addMember(member5.master)
        addMember(member6.master)
    }

    fun addMember(pokemon: PokemonMasterData): Int {
        if (this.member.size < 6) {
            this.member.add(pokemon)
            return this.member.size - 1
        }
        return -1;
    }

    fun removeMember(pokemon: PokemonMasterData): Int {
        return -1
    }

    fun clear() {
        member.clear()
    }
}
