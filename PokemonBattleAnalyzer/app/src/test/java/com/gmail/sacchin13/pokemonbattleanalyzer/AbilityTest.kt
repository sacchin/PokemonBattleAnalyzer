package com.gmail.sacchin13.pokemonbattleanalyzer

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Ability
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Type
import org.junit.Assert.assertEquals
import org.junit.Test

class AbilityTest {
    @Test
    fun ほのおタイプを無効にする特性のテスト() {
        assertEquals(0.0, Ability.calcTypeScale("もらいび", Type.Code.FIRE), 0.001)
        assertEquals(1.0, Ability.calcTypeScale("もらいび", Type.Code.ROCK), 0.001)
    }

    @Test
    fun でんきタイプを無効にする特性のテスト() {
        assertEquals(0.0, Ability.calcTypeScale("ひらいしん", Type.Code.ELECTRIC), 0.001)
        assertEquals(1.0, Ability.calcTypeScale("ひらいしん", Type.Code.ROCK), 0.001)
    }

    @Test
    fun みずタイプを無効にする特性のテスト() {
        assertEquals(0.0, Ability.calcTypeScale("よびみず", Type.Code.WATER), 0.001)
        assertEquals(1.0, Ability.calcTypeScale("よびみず", Type.Code.ROCK), 0.001)
    }

    @Test
    fun くさタイプを無効にする特性のテスト() {
        assertEquals(0.0, Ability.calcTypeScale("そうしょく", Type.Code.GRASS), 0.001)
        assertEquals(1.0, Ability.calcTypeScale("そうしょく", Type.Code.ROCK), 0.001)
    }

    @Test
    fun ふしぎなまもりのテスト() {
        assertEquals(0.0, Ability.calcTypeScale("ふしぎなまもり", Type.Code.WATER), 0.001)
        assertEquals(0.0, Ability.calcTypeScale("ふしぎなまもり", Type.Code.PSYCHIC), 0.001)
        assertEquals(2.0, Ability.calcTypeScale("ふしぎなまもり", Type.Code.ROCK), 0.001)
    }

    @Test
    fun 異常系の場合のテスト() {
        assertEquals(1.0, Ability.calcTypeScale("もらいび", Type.Code.UNKNOWN), 0.001)
        assertEquals(1.0, Ability.calcTypeScale("", Type.Code.FIRE), 0.001)
        assertEquals(1.0, Ability.calcTypeScale("", Type.Code.UNKNOWN), 0.001)
    }
}
