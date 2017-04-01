package com.gmail.sacchin13.pokemonbattleanalyzer.entity.ui

import android.util.Log
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Ability
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Characteristic
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Type
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.realm.MegaPokemonMasterData
import java.util.*

open class IndividualPokemonForUI(
        open var id: Long = -1,
        open var status: Int = UNKNOWN,
        open var item: String = UNSPECIFIED,
        open var characteristic: String = UNSPECIFIED,
        open var ability: String = UNSPECIFIED,
        open var skillNo1: SkillForUI = SkillForUI(),
        open var skillNo2: SkillForUI = SkillForUI(),
        open var skillNo3: SkillForUI = SkillForUI(),
        open var skillNo4: SkillForUI = SkillForUI(),
        open var hp: Int = 0,
        open var attack: Int = 0,
        open var defense: Int = 0,
        open var specialAttack: Int = 0,
        open var specialDefense: Int = 0,
        open var speed: Int = 0,
        open var hpEv: Int = 0,
        open var attackEv: Int = 0,
        open var defenseEv: Int = 0,
        open var specialAttackEv: Int = 0,
        open var specialDefenseEv: Int = 0,
        open var speedEv: Int = 0,
        open var master: PokemonMasterDataForUI = PokemonMasterDataForUI()
) {

    companion object {
        const val UNSPECIFIED = "unspecified"
        const val UNKNOWN = -1

        fun create(id: Long, master: PokemonMasterDataForUI): IndividualPokemonForUI {
            return IndividualPokemonForUI(id, 0, UNSPECIFIED, UNSPECIFIED, UNSPECIFIED,
                    SkillForUI(), SkillForUI(), SkillForUI(), SkillForUI(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, master)
        }
    }

    fun calcHp(megaType: Int): Int = calcHp(31, megaType)
    fun calcHp(ev: Int, megaType: Int): Int {
        return when (megaType) {
            MegaPokemonMasterData.MEGA_X -> master.hpX(31, ev)
            MegaPokemonMasterData.MEGA_Y -> master.hpY(31, ev)
            else -> master.hp(31, ev)
        }
    }

    fun calcAttack(megaType: Int): Int = calcAttack(31, megaType)
    fun calcAttack(ev: Int, megaType: Int): Int {
        return when (megaType) {
            MegaPokemonMasterData.MEGA_X -> master.attackX(31, ev)
            MegaPokemonMasterData.MEGA_Y -> master.attackY(31, ev)
            else -> master.attack(31, ev)
        }
    }

    fun calcDefense(megaType: Int): Int = calcDefense(31, megaType)
    fun calcDefense(ev: Int, megaType: Int): Int {
        return when (megaType) {
            MegaPokemonMasterData.MEGA_X -> master.defenseX(31, ev)
            MegaPokemonMasterData.MEGA_Y -> master.defenseY(31, ev)
            else -> master.defense(31, ev)
        }
    }

    fun calcSpecialAttack(megaType: Int): Int = calcSpecialAttack(31, megaType)
    fun calcSpecialAttack(ev: Int, megaType: Int): Int {
        return when (megaType) {
            MegaPokemonMasterData.MEGA_X -> master.specialAttackX(31, ev)
            MegaPokemonMasterData.MEGA_Y -> master.specialAttackY(31, ev)
            else -> master.specialAttack(31, ev)
        }
    }

    fun calcSpecialDefense(megaType: Int): Int = calcSpecialDefense(31, megaType)
    fun calcSpecialDefense(ev: Int, megaType: Int): Int {
        return when (megaType) {
            MegaPokemonMasterData.MEGA_X -> master.specialDefenseX(31, ev)
            MegaPokemonMasterData.MEGA_Y -> master.specialDefenseY(31, ev)
            else -> master.specialDefense(31, ev)
        }
    }

    fun calcSpeed(megaType: Int): Int = calcSpeed(31, megaType)
    fun calcSpeed(ev: Int, megaType: Int): Int {
        return when (megaType) {
            MegaPokemonMasterData.MEGA_X -> master.speedX(31, ev)
            MegaPokemonMasterData.MEGA_Y -> master.speedY(31, ev)
            else -> master.speed(31, ev)
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

    fun types(megaType: Int): Pair<Int, Int> {
        return when (megaType) {
            MegaPokemonMasterData.MEGA_X -> Pair(master.megax!!.type1, master.megax!!.type2)
            MegaPokemonMasterData.MEGA_Y -> Pair(master.megay!!.type1, master.megay!!.type2)
            else -> Pair(master.type1, master.type2)
        }
    }

    fun iv(at: String): Int {
        val cor = Characteristic.correction(characteristic, at)

        Array<Int>(32, { 31 - it }).forEach {
            when (at) {
                "H" -> {
                    Log.v(at, "$hp == ${master.hpFormula(master.h, it, hpEv)}.times($cor).toInt()) return $it")
                    if (hp == master.hpFormula(master.h, it, hpEv).times(cor).toInt()) return it
                }
                "A" -> {
                    Log.v(at, "$attack == ${master.otherFormula(master.a, it, attackEv)}.times($cor).toInt()) return $it")
                    if (attack == master.otherFormula(master.a, it, attackEv).times(cor).toInt()) return it
                }
                "B" -> {
                    Log.v(at, "if ($defense == ${master.otherFormula(master.b, it, defenseEv)}.times($cor).toInt()) return $it")
                    if (defense == master.otherFormula(master.b, it, defenseEv).times(cor).toInt()) return it
                }
                "C" -> {
                    Log.v(at, "if ($specialAttack == ${master.otherFormula(master.c, it, specialAttackEv)}.times($cor).toInt()) return $it")
                    if (specialAttack == master.otherFormula(master.c, it, specialAttackEv).times(cor).toInt()) return it
                }
                "D" -> {
                    Log.v(at, "if ($specialDefense == ${master.otherFormula(master.d, it, specialDefenseEv)}.times($cor).toInt()) return $it")
                    if (specialDefense == master.otherFormula(master.d, it, specialDefenseEv).times(cor).toInt()) return it
                }
                "S" -> {
                    Log.v(at, "if ($speed == ${master.otherFormula(master.s, it, speedEv)}.times($cor).toInt()) return $it")
                    if (speed == master.otherFormula(master.s, it, speedEv).times(cor).toInt()) return it
                }
            }
        }
        return 0
    }
}
