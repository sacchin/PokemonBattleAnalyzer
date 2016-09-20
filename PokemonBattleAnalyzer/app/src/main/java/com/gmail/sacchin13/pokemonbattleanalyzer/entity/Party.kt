package com.gmail.sacchin13.pokemonbattleanalyzer.entity

import io.realm.RealmObject
import io.realm.annotations.Ignore
import java.util.*

public open class Party (
        public open var time: Long = System.currentTimeMillis(),
        public open var userName: String = "none",
        public open var memo: String = "none",
        public open var member1: IndividualPokemon = IndividualPokemon.create(-1, PokemonMasterData()),
        public open var member2: IndividualPokemon = IndividualPokemon.create(-1, PokemonMasterData()),
        public open var member3: IndividualPokemon = IndividualPokemon.create(-1, PokemonMasterData()),
        public open var member4: IndividualPokemon = IndividualPokemon.create(-1, PokemonMasterData()),
        public open var member5: IndividualPokemon = IndividualPokemon.create(-1, PokemonMasterData()),
        public open var member6: IndividualPokemon = IndividualPokemon.create(-1, PokemonMasterData())
): RealmObject(){

    @Ignore
    var member: MutableList<PokemonMasterData> = ArrayList<PokemonMasterData>()

    fun initMember(){
        addMember(member1.master)
        addMember(member2.master)
        addMember(member3.master)
        addMember(member4.master)
        addMember(member5.master)
        addMember(member6.master)
    }

    fun addMember(pokemon: PokemonMasterData): Int {
        if(this.member.size < 6){
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
