package com.gmail.sacchin13.pokemonbattleanalyzer.entity

import io.realm.RealmObject
import io.realm.annotations.RealmClass
import java.util.*

@RealmClass
open class IndividualPokemon(
        open var id: Long = -1,
        open var status: Int = UNKNOWN,
        open var item: String = UNSPECIFIED,
        open var characteristic: String = UNSPECIFIED,
        open var ability: String = UNSPECIFIED,
        open var skillNo1: Skill = Skill(),
        open var skillNo2: Skill = Skill(),
        open var skillNo3: Skill = Skill(),
        open var skillNo4: Skill = Skill(),
        open var hpEv: Int = UNKNOWN,
        open var attackEv: Int = UNKNOWN,
        open var defenseEv: Int = UNKNOWN,
        open var specialAttackEv: Int = UNKNOWN,
        open var specialDefenseEv: Int = UNKNOWN,
        open var speedEv: Int = UNKNOWN,
        open var hpIv: Int = UNKNOWN,
        open var attackIv: Int = UNKNOWN,
        open var defenseIv: Int = UNKNOWN,
        open var specialAttackIv: Int = UNKNOWN,
        open var specialDefenseIv: Int = UNKNOWN,
        open var speedIv: Int = UNKNOWN,
        open var master: PokemonMasterData = PokemonMasterData()
) : RealmObject() {

    companion object {
        const val UNSPECIFIED = "unspecified"
        const val UNKNOWN = -1

        fun create(id: Long, master: PokemonMasterData): IndividualPokemon {
            return IndividualPokemon(id, 0, UNSPECIFIED, UNSPECIFIED, UNSPECIFIED, Skill(), Skill(), Skill(), Skill(), 0, 0, 0, 0, 0, 0, 31, 31, 31, 31, 31, 31, master)
        }
    }

    fun calcHp(megaType: Int): Int = calcHp(hpEv, megaType)
    fun calcHp(ev: Int, megaType: Int): Int {
        return when (megaType) {
            MegaPokemonMasterData.MEGA_X -> master.hpX(hpIv, ev)
            MegaPokemonMasterData.MEGA_Y -> master.hpY(hpIv, ev)
            else -> master.hp(hpIv, ev)
        }
    }

    fun calcAttack(megaType: Int): Int = calcAttack(attackEv, megaType)
    fun calcAttack(ev: Int, megaType: Int): Int {
        return when (megaType) {
            MegaPokemonMasterData.MEGA_X -> master.attackX(attackIv, ev)
            MegaPokemonMasterData.MEGA_Y -> master.attackY(attackIv, ev)
            else -> master.attack(attackIv, ev)
        }
    }

    fun calcDefense(megaType: Int): Int = calcDefense(defenseEv, megaType)
    fun calcDefense(ev: Int, megaType: Int): Int {
        return when (megaType) {
            MegaPokemonMasterData.MEGA_X -> master.defenseX(defenseIv, ev)
            MegaPokemonMasterData.MEGA_Y -> master.defenseY(defenseIv, ev)
            else -> master.defense(defenseIv, ev)
        }
    }

    fun calcSpecialAttack(megaType: Int): Int = calcSpecialAttack(specialAttackEv, megaType)
    fun calcSpecialAttack(ev: Int, megaType: Int): Int {
        return when (megaType) {
            MegaPokemonMasterData.MEGA_X -> master.specialAttackX(specialAttackIv, ev)
            MegaPokemonMasterData.MEGA_Y -> master.specialAttackY(specialAttackIv, ev)
            else -> master.specialAttack(specialAttackIv, ev)
        }
    }

    fun calcSpecialDefense(megaType: Int): Int = calcSpecialDefense(specialDefenseEv, megaType)
    fun calcSpecialDefense(ev: Int, megaType: Int): Int {
        return when (megaType) {
            MegaPokemonMasterData.MEGA_X -> master.specialDefenseX(specialDefenseIv, ev)
            MegaPokemonMasterData.MEGA_Y -> master.specialDefenseY(specialDefenseIv, ev)
            else -> master.specialDefense(specialDefenseIv, ev)
        }
    }

    fun calcSpeed(megaType: Int): Int = calcSpeed(speedEv, megaType)
    fun calcSpeed(ev: Int, megaType: Int): Int {
        return when (megaType) {
            MegaPokemonMasterData.MEGA_X -> master.speedX(speedIv, ev)
            MegaPokemonMasterData.MEGA_Y -> master.speedY(speedIv, ev)
            else -> master.speed(speedIv, ev)
        }
    }

    fun speedValues(megaType: Int): Array<Int> {
        return when (megaType) {
            MegaPokemonMasterData.MEGA_X -> master.speedValuesX()
            MegaPokemonMasterData.MEGA_Y -> master.speedValuesY()
            else -> master.speedValues()
        }
    }

    val abilities: List<String>
        get() {
            val temp = ArrayList<String>()
            if (master.ability1.isNullOrBlank().not() && master.ability1 != UNSPECIFIED && master.ability1 != "-") {
                temp.add(master.ability1)
            }
            if (master.ability2.isNullOrBlank().not() && master.ability2 != UNSPECIFIED && master.ability2 != "-") {
                temp.add(master.ability2)
            }
            if (master.abilityd.isNullOrBlank().not() && master.abilityd != UNSPECIFIED && master.abilityd != "-") {
                temp.add(master.abilityd)
            }
            return temp
        }

    fun typeScale(type: Type.Code, megaType: Int, katayaburi: Boolean): Double {
        val (type1, type2) =
                if ((megaType == MegaPokemonMasterData.MEGA_X &&
                        master.megax != null &&
                        master.megax!!.type1 != -1 && master.megax!!.type2 != -1)) {
                    Pair(master.megax!!.type1, master.megax!!.type2)
                } else if (megaType == MegaPokemonMasterData.MEGA_Y &&
                        master.megay != null &&
                        master.megay!!.type1 != -1 && master.megay!!.type2 != -1) {
                    Pair(master.megay!!.type1, master.megay!!.type2)
                } else {
                    Pair(master.type1, master.type2)
                }

        val scale = Type.calculateAffinity(type, Type.code(type1), Type.code(type2))
        if (scale < 0.1) {
            return 0.0
        }

        if (katayaburi.not()) {
            val scaleByAbility = Ability.calcTypeScale(ability(megaType), type)
            if (scaleByAbility < 0.1) {
                return 0.0
            }
        }

        return scale
    }

    fun name(megaType: Int): String {
        return when (megaType) {
            MegaPokemonMasterData.MEGA_X -> "メガ${master.jname}X"
            MegaPokemonMasterData.MEGA_Y -> "メガ${master.jname}Y"
            else -> master.jname
        }
    }

    fun ability(megaType: Int): String {
        return when (megaType) {
            MegaPokemonMasterData.MEGA_X -> master.megax!!.ability
            MegaPokemonMasterData.MEGA_Y -> master.megay!!.ability
            else -> ability
        }
    }
}
