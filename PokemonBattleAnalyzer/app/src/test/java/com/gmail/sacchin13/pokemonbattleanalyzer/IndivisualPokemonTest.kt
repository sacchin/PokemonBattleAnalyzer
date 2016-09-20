package com.gmail.sacchin13.pokemonbattleanalyzer

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.IndividualPokemon
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.MegaPokemonMasterData
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.PokemonMasterData
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Type
import org.junit.Assert.assertEquals
import org.junit.Test

class IndivisualPokemonTest {

    @Test
    fun 特性一覧取得の正常系のテスト() {
        val kucheat = IndividualPokemon.create(1,
                PokemonMasterData("303", "クチート", "Mawile", "-", 50, 85, 85, 55, 55, 50,
                        "かいりきバサミ", "", "-", Type.no(Type.Code.STEEL), Type.no(Type.Code.FAIRY), 23.5f))

        val actual = kucheat.abilities
        assertEquals(2, actual.size)
        assertEquals("かいりきバサミ", actual[0])
        assertEquals("", actual[1])
    }

    @Test
    fun タイプ相性取得の正常系のテスト() {
        val kucheat = IndividualPokemon.create(1,
                PokemonMasterData("303", "クチート", "Mawile", "-", 50, 85, 85, 55, 55, 50,
                        "かいりきバサミ", "いかく", "ちからずく", Type.no(Type.Code.STEEL), Type.no(Type.Code.FAIRY), 23.5f))

        kucheat.ability = "もらいび"
        var actual = kucheat.typeScale(Type.Code.FIRE)
        assertEquals(0.0, actual, 0.001)

        kucheat.ability = "ひらいしん"
        actual = kucheat.typeScale(Type.Code.ELECTRIC)
        assertEquals(0.0, actual, 0.001)

        kucheat.ability = "ふしぎなまもり"
        actual = kucheat.typeScale(Type.Code.WATER)
        assertEquals(0.0, actual, 0.001)
    }

    @Test
    fun 攻撃力計算の正常系のテスト() {
        val kucheat = IndividualPokemon.create(1,
                PokemonMasterData("303", "クチート", "Mawile", "-", 50, 85, 85, 55, 55, 50,
                        "かいりきバサミ", "いかく", "ちからずく", Type.no(Type.Code.STEEL), Type.no(Type.Code.FAIRY), 23.5f))

        val actual = kucheat.calcAttack(252, MegaPokemonMasterData.NOT_MEGA)
        assertEquals(137, actual)
    }

    @Test
    fun 防御力計算の正常系のテスト() {
        val kucheat = IndividualPokemon.create(1,
                PokemonMasterData("303", "クチート", "Mawile", "-", 50, 85, 85, 55, 55, 50,
                        "かいりきバサミ", "いかく", "ちからずく", Type.no(Type.Code.STEEL), Type.no(Type.Code.FAIRY), 23.5f))

        val actual = kucheat.calcDefense(252, MegaPokemonMasterData.NOT_MEGA)
        assertEquals(137, actual)
    }


    @Test
    fun 特殊攻撃力計算の正常系のテスト() {
        val kucheat = IndividualPokemon.create(1,
                PokemonMasterData("303", "クチート", "Mawile", "-", 50, 85, 85, 55, 55, 50,
                        "かいりきバサミ", "いかく", "ちからずく", Type.no(Type.Code.STEEL), Type.no(Type.Code.FAIRY), 23.5f))

        val actual = kucheat.calcSpecialAttack(252, MegaPokemonMasterData.NOT_MEGA)
        assertEquals(107, actual)
    }


    @Test
    fun 特殊防御力計算の正常系のテスト() {
        val kucheat = IndividualPokemon.create(1,
                PokemonMasterData("303", "クチート", "Mawile", "-", 50, 85, 85, 55, 55, 50,
                        "かいりきバサミ", "いかく", "ちからずく", Type.no(Type.Code.STEEL), Type.no(Type.Code.FAIRY), 23.5f))

        val actual = kucheat.calcSpecialDefense(252, MegaPokemonMasterData.NOT_MEGA)
        assertEquals(107, actual)
    }


    @Test
    fun 素早さ計算の正常系のテスト() {
        val kucheat = IndividualPokemon.create(1,
                PokemonMasterData("303", "クチート", "Mawile", "-", 50, 85, 85, 55, 55, 50,
                        "かいりきバサミ", "いかく", "ちからずく", Type.no(Type.Code.STEEL), Type.no(Type.Code.FAIRY), 23.5f))

        val actual = kucheat.calcSpeed(252, MegaPokemonMasterData.NOT_MEGA)
        assertEquals(102, actual)
    }

}