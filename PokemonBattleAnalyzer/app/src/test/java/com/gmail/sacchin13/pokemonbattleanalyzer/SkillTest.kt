package com.gmail.sacchin13.pokemonbattleanalyzer

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Ability
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Type
import org.junit.Assert.assertEquals
import org.junit.Test

class SkillTest {
    @Test
    fun ほのおタイプを無効にする特性のテスト() {
        assertEquals(0.0, Ability.calcTypeScale("もらいび", Type.Code.FIRE), 0.001)
        assertEquals(1.0, Ability.calcTypeScale("もらいび", Type.Code.ROCK), 0.001)
    }
}
