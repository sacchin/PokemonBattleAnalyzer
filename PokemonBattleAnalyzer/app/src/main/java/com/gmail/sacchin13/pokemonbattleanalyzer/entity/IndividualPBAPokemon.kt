package com.gmail.sacchin13.pokemonbattleanalyzer.entity

import io.realm.RealmObject
import io.realm.annotations.RealmClass
import java.util.*

@RealmClass
public open class IndividualPBAPokemon(
        public open var id: Long = -1,
        public open var status: Int = UNKNOWN,
        public open var item: String = "unknown",
        public open var characteristic: String = "unknown",
        public open var ability: String = "unknown",
        public open var skillNo1: Skill = Skill(),
        public open var skillNo2: Skill = Skill(),
        public open var skillNo3: Skill = Skill(),
        public open var skillNo4: Skill = Skill(),
        public open var hpValue: Int = UNKNOWN,
        public open var attackValue: Int = UNKNOWN,
        public open var defenseValue: Int = UNKNOWN,
        public open var specialAttackValue: Int = UNKNOWN,
        public open var specialDefenseValue: Int = UNKNOWN,
        public open var speedValue: Int = UNKNOWN,
        public open var master: PokemonMasterData = PokemonMasterData()
) : RealmObject() {

    companion object {
        const val UNKNOWN = -1
        const val NOT_MEGA = 0
        const val MEGA_X = 1
        const val MEGA_Y = 2

        fun create(id: Long, master: PokemonMasterData): IndividualPBAPokemon {
            return IndividualPBAPokemon(id, 0, "", "", "", Skill(), Skill(), Skill(), Skill(), 0, 0, 0, 0, 0, 0, master)
        }
    }

    fun calcHp(ev: Int, megaType: Int): Int {
        return when (megaType) {
            MEGA_X -> master.hpX(31, ev)
            MEGA_Y -> master.hpY(31, ev)
            else -> master.hp(31, ev)
        }
    }

    fun calcAttack(ev: Int, megaType: Int): Int {
        return when (megaType) {
            MEGA_X -> master.attackX(31, ev)
            MEGA_Y -> master.attackY(31, ev)
            else -> master.attack(31, ev)
        }
    }

    fun calcDefense(ev: Int, megaType: Int): Int {
        return when (megaType) {
            MEGA_X -> master.defenseX(31, ev)
            MEGA_Y -> master.defenseY(31, ev)
            else -> master.defense(31, ev)
        }
    }

    fun calcSpecialAttack(ev: Int, megaType: Int): Int {
        return when (megaType) {
            MEGA_X -> master.specialAttackX(31, ev)
            MEGA_Y -> master.specialAttackY(31, ev)
            else -> master.specialAttack(31, ev)
        }
    }

    fun calcSpecialDefense(ev: Int, megaType: Int): Int {
        return when (megaType) {
            MEGA_X -> master.specialDefenseX(31, ev)
            MEGA_Y -> master.specialDefenseY(31, ev)
            else -> master.specialDefense(31, ev)
        }
    }

    fun calcSpeed(ev: Int, megaType: Int): Int {
        return when (megaType) {
            MEGA_X -> master.speedX(31, ev)
            MEGA_Y -> master.speedY(31, ev)
            else -> master.speed(31, ev)
        }
    }

    val abilities: List<String>
        get() {
            val temp = ArrayList<String>()
            temp.add(master.ability1)
            if (master.ability2 != "-") {
                temp.add(master.ability2)
            }
            if (master.abilityd != "-") {
                temp.add(master.abilityd)
            }
            return temp
        }

    fun typeScale(type: Type.Code): Double {
        val scale = Type.calculateAffinity(type, master)
        if (scale < 1) {
            return 0.0
        }

        val scaleByAbility = Ability.calcTypeScale(ability, type)
        if (scaleByAbility < 1) {
            return 0.0
        }

        return scale
    }

    fun name(megaType: Int): String {
        return when (megaType) {
            NOT_MEGA -> master.jname
            MEGA_X -> "メガ${master.jname}"
            MEGA_Y -> "メガ${master.jname}Y"
            else -> "unknown"
        }
    }
}
