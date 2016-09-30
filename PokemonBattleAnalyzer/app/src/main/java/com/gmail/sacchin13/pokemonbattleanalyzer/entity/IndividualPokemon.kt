package com.gmail.sacchin13.pokemonbattleanalyzer.entity

import io.realm.RealmObject
import io.realm.annotations.RealmClass
import java.util.*

@RealmClass
public open class IndividualPokemon(
        public open var id: Long = -1,
        public open var status: Int = UNKNOWN,
        public open var item: String = "unknown",
        public open var characteristic: String = "unknown",
        public open var ability: String = "unknown",
        public open var skillNo1: Skill = Skill(),
        public open var skillNo2: Skill = Skill(),
        public open var skillNo3: Skill = Skill(),
        public open var skillNo4: Skill = Skill(),
        public open var hpEv: Int = UNKNOWN,
        public open var attackEv: Int = UNKNOWN,
        public open var defenseEv: Int = UNKNOWN,
        public open var specialAttackEv: Int = UNKNOWN,
        public open var specialDefenseEv: Int = UNKNOWN,
        public open var speedEv: Int = UNKNOWN,
        public open var hpIv: Int = UNKNOWN,
        public open var attackIv: Int = UNKNOWN,
        public open var defenseIv: Int = UNKNOWN,
        public open var specialAttackIv: Int = UNKNOWN,
        public open var specialDefenseIv: Int = UNKNOWN,
        public open var speedIv: Int = UNKNOWN,
        public open var master: PokemonMasterData = PokemonMasterData()
) : RealmObject() {

    companion object {
        const val UNKNOWN = -1

        fun create(id: Long, master: PokemonMasterData): IndividualPokemon {
            return IndividualPokemon(id, 0, "", "", "", Skill(), Skill(), Skill(), Skill(), 0, 0, 0, 0, 0, 0, 31, 31, 31, 31, 31, 31, master)
        }
    }

    fun calcHp(megaType: Int): Int = calcHp(hpEv, megaType)
    fun calcAttack(megaType: Int): Int = calcAttack(attackEv, megaType)
    fun calcDefense(megaType: Int): Int = calcDefense(defenseEv, megaType)
    fun calcSpecialAttack(megaType: Int): Int = calcSpecialAttack(specialAttackEv, megaType)
    fun calcSpecialDefense(megaType: Int): Int = calcSpecialDefense(specialDefenseEv, megaType)
    fun calcSpeed(megaType: Int): Int = calcSpeed(speedEv, megaType)

    fun calcHp(ev: Int, megaType: Int): Int {
        return when (megaType) {
            MegaPokemonMasterData.MEGA_X -> master.hpX(hpIv, ev)
            MegaPokemonMasterData.MEGA_Y -> master.hpY(hpIv, ev)
            else -> master.hp(hpIv, ev)
        }
    }

    fun calcAttack(ev: Int, megaType: Int): Int {
        return when (megaType) {
            MegaPokemonMasterData.MEGA_X -> master.attackX(attackIv, ev)
            MegaPokemonMasterData.MEGA_Y -> master.attackY(attackIv, ev)
            else -> master.attack(attackIv, ev)
        }
    }

    fun calcDefense(ev: Int, megaType: Int): Int {
        return when (megaType) {
            MegaPokemonMasterData.MEGA_X -> master.defenseX(defenseIv, ev)
            MegaPokemonMasterData.MEGA_Y -> master.defenseY(defenseIv, ev)
            else -> master.defense(defenseIv, ev)
        }
    }

    fun calcSpecialAttack(ev: Int, megaType: Int): Int {
        return when (megaType) {
            MegaPokemonMasterData.MEGA_X -> master.specialAttackX(specialAttackIv, ev)
            MegaPokemonMasterData.MEGA_Y -> master.specialAttackY(specialAttackIv, ev)
            else -> master.specialAttack(specialAttackIv, ev)
        }
    }

    fun calcSpecialDefense(ev: Int, megaType: Int): Int {
        return when (megaType) {
            MegaPokemonMasterData.MEGA_X -> master.specialDefenseX(specialDefenseIv, ev)
            MegaPokemonMasterData.MEGA_Y -> master.specialDefenseY(specialDefenseIv, ev)
            else -> master.specialDefense(specialDefenseIv, ev)
        }
    }

    fun calcSpeed(ev: Int, megaType: Int): Int {
        return when (megaType) {
            MegaPokemonMasterData.MEGA_X -> master.speedX(speedIv, ev)
            MegaPokemonMasterData.MEGA_Y -> master.speedY(speedIv, ev)
            else -> master.speed(speedIv, ev)
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

    fun typeScale(type: Type.Code, megaType: Int, katayaburi: Boolean): Double {
        var type1 = 0
        var type2 = 0
        when(megaType){
            MegaPokemonMasterData.MEGA_X -> {
                if(master.megax == null || (master.megax!!.type1 == -1 && master.megax!!.type2 == -1)){
                    type1 = master.type1
                    type2 = master.type2
                }else{
                    type1 = master.megax!!.type1
                    type2 = master.megax!!.type2
                }
            }
            MegaPokemonMasterData.MEGA_Y -> {
                if(master.megay == null || (master.megay!!.type1 == -1 && master.megay!!.type2 == -1)){
                    type1 = master.type1
                    type2 = master.type2
                }else{
                    type1 = master.megay!!.type1
                    type2 = master.megay!!.type2
                }
            }
            else -> {
                type1 = master.type1
                type2 = master.type2
            }
        }

        val scale = Type.calculateAffinity(type, Type.code(type1), Type.code(type2))
        if (scale < 0.1) {
            return 0.0
        }

        if(katayaburi.not()) {
            val scaleByAbility = Ability.calcTypeScale(ability(megaType), type)
            if (scaleByAbility < 0.1) {
                return 0.0
            }
        }

        return scale
    }

    fun name(megaType: Int): String {
        return when (megaType) {
            MegaPokemonMasterData.MEGA_X -> "メガ${master.jname}"
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