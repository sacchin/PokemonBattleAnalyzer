package com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.realm.Skill
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.ui.SkillForUI
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.realm.ZSkill

data class RankingPokemonSkill(
        var ranking: Int = 0,
        var sequenceNumber: Int = 0,
        var skill: SkillForUI = SkillForUI(),
        var typeId: Int = 0,
        var usageRate: Float = 0.0f
) {
    companion object {
        fun create(wazaInfo: WazaInfo, skill: Skill): RankingPokemonSkill {
            return RankingPokemonSkill(wazaInfo.ranking, wazaInfo.sequenceNumber, skill.uiObject(), wazaInfo.typeId, wazaInfo.usageRate)
        }

        fun create(wazaInfo: WazaInfo, skill: ZSkill): RankingPokemonSkill {
            return RankingPokemonSkill(wazaInfo.ranking, wazaInfo.sequenceNumber, skill.convert().uiObject(), wazaInfo.typeId, wazaInfo.usageRate)
        }
    }
}

