package com.gmail.sacchin13.pokemonbattleanalyzer

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Ability
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.PokemonMasterData
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Type
import org.junit.Assert.assertEquals
import org.junit.Test

class AbilityTest {
    @Test
    fun ほのおタイプを無効にする特性のテスト() {
        var actual = Ability.calcTypeScale("もらいび", Type.Code.FIRE)
        assertEquals(0.0, actual, 0.001)

        actual = Ability.calcTypeScale("もらいび", Type.Code.ROCK)
        assertEquals(1.0, actual, 0.001)
    }

    @Test
    fun でんきタイプを無効にする特性のテスト() {
        var actual = Ability.calcTypeScale("ひらいしん", Type.Code.ELECTRIC)
        assertEquals(0.0, actual, 0.001)

        actual = Ability.calcTypeScale("ひらいしん", Type.Code.ROCK)
        assertEquals(1.0, actual, 0.001)
    }

    @Test
    fun みずタイプを無効にする特性のテスト() {
        var actual = Ability.calcTypeScale("よびみず", Type.Code.WATER)
        assertEquals(0.0, actual, 0.001)

        actual = Ability.calcTypeScale("よびみず", Type.Code.ROCK)
        assertEquals(1.0, actual, 0.001)
    }

    @Test
    fun くさタイプを無効にする特性のテスト() {
        var actual = Ability.calcTypeScale("そうしょく", Type.Code.GRASS)
        assertEquals(0.0, actual, 0.001)

        actual = Ability.calcTypeScale("そうしょく", Type.Code.ROCK)
        assertEquals(1.0, actual, 0.001)
    }

    @Test
    fun ふしぎなまもりのテスト() {
        var actual = Ability.calcTypeScale("ふしぎなまもり", Type.Code.WATER)
        assertEquals(0.0, actual, 0.001)

        actual = Ability.calcTypeScale("ふしぎなまもり", Type.Code.PSYCHIC)
        assertEquals(0.0, actual, 0.001)

        actual = Ability.calcTypeScale("ふしぎなまもり", Type.Code.ROCK)
        assertEquals(2.0, actual, 0.001)
    }
}
