package com.gmail.sacchin13.pokemonbattleanalyzer.entity

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.realm.PokemonMasterData
import io.realm.RealmList
import io.realm.RealmObject

public open class Construction(
        public open var name: String = "",
        public open var warning: String = "",
        public open var type: Int = -1,
        public open var list: RealmList<PokemonMasterData> = RealmList(),
        public open var advantage: RealmList<PokemonMasterData> = RealmList()

) : RealmObject() {

    companion object {

        fun no(type: Type): Int {
            return when (type) {
                Type.STANDER -> 0
                Type.ROLE -> 1
                Type.LOOP -> 2
                Type.MEETING -> 3
                Type.STACK_LOOP -> 4
                Type.WEATHER -> 5
                Type.TRICK -> 6
                Type.BATON -> 7
                Type.WALL -> 8
                Type.TOXIC -> 9
                Type.CAT -> 10
                else -> -1
            }
        }

        fun type(no: Int): Type {
            return when (no) {
                0 -> Type.STANDER
                1 -> Type.ROLE
                2 -> Type.LOOP
                3 -> Type.MEETING
                4 -> Type.STACK_LOOP
                5 -> Type.WEATHER
                6 -> Type.TRICK
                7 -> Type.BATON
                8 -> Type.WALL
                9 -> Type.TOXIC
                10 -> Type.CAT
                else -> Construction.Type.UNKNOWN
            }
        }
    }

    enum class Type {
        STANDER, ROLE, LOOP, MEETING, STACK_LOOP, WEATHER, TRICK, BATON, WALL, TOXIC, CAT, UNKNOWN
    }
}