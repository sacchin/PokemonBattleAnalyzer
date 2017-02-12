package com.gmail.sacchin13.pokemonbattleanalyzer.entity.realm

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.ui.IndividualPokemonForUI
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
        open var hp: Int = 0,
        open var attack: Int = 0,
        open var defense: Int = 0,
        open var specialAttack: Int = 0,
        open var specialDefense: Int = 0,
        open var speed: Int = 0,
        open var master: PokemonMasterData = PokemonMasterData()
) : RealmObject() {

    companion object {
        const val UNSPECIFIED = "unspecified"
        const val UNKNOWN = -1

        fun create(id: Long, master: PokemonMasterData): IndividualPokemon {
            return IndividualPokemon(id, 0, UNSPECIFIED, UNSPECIFIED, UNSPECIFIED,
                    Skill(), Skill(), Skill(), Skill(), 0, 0, 0, 0, 0, 0, master)
        }
    }

    fun uiObject(): IndividualPokemonForUI {
        return IndividualPokemonForUI(id, status, item, characteristic, ability,
                skillNo1.uiObject(), skillNo2.uiObject(), skillNo3.uiObject(), skillNo4.uiObject(),
                hp, attack, defense, specialAttack, specialDefense, speed, master.uiObject())
    }

    val abilities: List<String>
        get() {
            val temp = ArrayList<String>()
            if (master.ability1.isNullOrBlank().not() && master.ability1 != IndividualPokemonForUI.UNSPECIFIED && master.ability1 != "-") {
                temp.add(master.ability1)
            }
            if (master.ability2.isNullOrBlank().not() && master.ability2 != IndividualPokemonForUI.UNSPECIFIED && master.ability2 != "-") {
                temp.add(master.ability2)
            }
            if (master.abilityd.isNullOrBlank().not() && master.abilityd != IndividualPokemonForUI.UNSPECIFIED && master.abilityd != "-") {
                temp.add(master.abilityd)
            }
            return temp
        }
}
