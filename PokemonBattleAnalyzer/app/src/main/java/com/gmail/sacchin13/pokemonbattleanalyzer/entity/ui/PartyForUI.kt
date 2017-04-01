package com.gmail.sacchin13.pokemonbattleanalyzer.entity.ui

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.ui.IndividualPokemonForUI
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.ui.PokemonMasterDataForUI
import java.util.*

open class PartyForUI(
        open var time: Long = System.currentTimeMillis(),
        open var userName: String = "none",
        open var memo: String = "none",
        open var member1: IndividualPokemonForUI = IndividualPokemonForUI.create(-1, PokemonMasterDataForUI()),
        open var member2: IndividualPokemonForUI = IndividualPokemonForUI.create(-1, PokemonMasterDataForUI()),
        open var member3: IndividualPokemonForUI = IndividualPokemonForUI.create(-1, PokemonMasterDataForUI()),
        open var member4: IndividualPokemonForUI = IndividualPokemonForUI.create(-1, PokemonMasterDataForUI()),
        open var member5: IndividualPokemonForUI = IndividualPokemonForUI.create(-1, PokemonMasterDataForUI()),
        open var member6: IndividualPokemonForUI = IndividualPokemonForUI.create(-1, PokemonMasterDataForUI())
) {

    var member: MutableList<PokemonMasterDataForUI> = ArrayList<PokemonMasterDataForUI>()

    fun initMember() {
        addMember(member1.master)
        addMember(member2.master)
        addMember(member3.master)
        addMember(member4.master)
        addMember(member5.master)
        addMember(member6.master)
    }

    fun addMember(pokemon: PokemonMasterDataForUI): Int {
        if (this.member.size < 6) {
            this.member.add(pokemon)
            return this.member.size - 1
        }
        return -1;
    }

    fun removeMember(pokemon: PokemonMasterDataForUI): Int {
        return -1
    }

    fun clear() {
        member.clear()
    }
}
