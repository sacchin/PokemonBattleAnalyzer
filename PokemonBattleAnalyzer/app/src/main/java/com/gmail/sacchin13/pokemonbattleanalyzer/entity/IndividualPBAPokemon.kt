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

        fun create(id: Long, master: PokemonMasterData): IndividualPBAPokemon {
            return IndividualPBAPokemon(id, 0, "", "", "", Skill(), Skill(), Skill(), Skill(), 0, 0, 0, 0, 0, 0, master)
        }
    }

    fun calcHp() = master.hp(31, 252)
    fun calcAttack() = master.attack(31, 252)
    fun calcDefense() = master.defense(31, 252)
    fun calcSpecialAttack() = master.specialAttack(31, 252)
    fun calcSpecialDefense() = master.specialDefense(31, 252)
    fun calcSpeed() = master.speed(31, 252)

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
}
