package com.gmail.sacchin13.pokemonbattleanalyzer.entity

import io.realm.RealmObject
import io.realm.annotations.Ignore
import java.sql.Timestamp
import java.util.*

public open class Party (
        public open var time: Long = System.currentTimeMillis(),
        public open var userName: String = "none",
        public open var memo: String = "none",
        public open var member1: String? = null,
        public open var member2: String? = null,
        public open var member3: String? = null,
        public open var member4: String? = null,
        public open var member5: String? = null,
        public open var member6: String? = null
): RealmObject(){

    @Ignore
    var member: MutableList<PBAPokemon?> = ArrayList<PBAPokemon?>()

    fun addMember(pokemon: IndividualPBAPokemon): Int {
        if(this.member.size < 6){
            this.member.add(pokemon.master)
            return this.member.size - 1
        }
        return -1;
    }

    fun removeMember(PBAPokemon: PBAPokemon?): Int {
        return -1
    }

    fun clear() {
        member.clear()
    }
}
