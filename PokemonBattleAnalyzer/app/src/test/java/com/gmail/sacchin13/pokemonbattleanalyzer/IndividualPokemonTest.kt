package com.gmail.sacchin13.pokemonbattleanalyzer

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.IndividualPokemon
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.MegaPokemonMasterData
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.PokemonMasterData
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Type
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.properties.Delegates

class IndividualPokemonTest {
    var kucheat: IndividualPokemon by Delegates.notNull()
    var megaKucheat: MegaPokemonMasterData by Delegates.notNull()

    @Before
    fun init() {
        megaKucheat = MegaPokemonMasterData.create("303", 50, 105, 125, 55, 95, 50,
                Type.no(Type.Code.STEEL), Type.no(Type.Code.FAIRY), 23.5f, "ちからずく", MegaPokemonMasterData.MEGA_X)
        kucheat = IndividualPokemon.create(1,
                PokemonMasterData("303", "クチート", "Mawile", "-", 50, 85, 85, 55, 55, 50,
                        "かいりきバサミ", "", "-", Type.no(Type.Code.STEEL), Type.no(Type.Code.FAIRY), 23.5f, megaKucheat))
    }

    @Test
    fun 特性一覧取得の正常系のテスト() {
        val actual = kucheat.abilities
        assertEquals(2, actual.size)
        assertEquals("かいりきバサミ", actual[0])
        assertEquals("", actual[1])
    }

    @Test
    fun タイプ相性取得の正常系のテスト() {
        val megaX = MegaPokemonMasterData.create("-", 50, 105, 125, 55, 95, 50,
                Type.no(Type.Code.FIRE), Type.no(Type.Code.FLYING), 23.5f, "-", MegaPokemonMasterData.MEGA_X)
        val megaY = MegaPokemonMasterData.create("-", 50, 105, 125, 55, 95, 50,
                Type.no(Type.Code.GROUND), Type.no(Type.Code.FAIRY), 23.5f, "-", MegaPokemonMasterData.MEGA_X)
        val pokemon = IndividualPokemon.create(1,
                PokemonMasterData("-", "-", "-", "-", 50, 85, 85, 55, 55, 50,
                        "-", "-", "-", Type.no(Type.Code.STEEL), Type.no(Type.Code.FAIRY), 23.5f, megaX, megaY))

        assertEquals(2.0, pokemon.typeScale(Type.Code.FIRE, MegaPokemonMasterData.NOT_MEGA, false), 0.001)
        assertEquals(0.5, pokemon.typeScale(Type.Code.FIRE, MegaPokemonMasterData.MEGA_X, false), 0.001)
        assertEquals(1.0, pokemon.typeScale(Type.Code.FIRE, MegaPokemonMasterData.MEGA_Y, false), 0.001)

        pokemon.ability = "もらいび"
        assertEquals(0.0, pokemon.typeScale(Type.Code.FIRE, MegaPokemonMasterData.NOT_MEGA, false), 0.001)
        assertEquals(2.0, pokemon.typeScale(Type.Code.FIRE, MegaPokemonMasterData.NOT_MEGA, true), 0.001)
        assertEquals(0.5, pokemon.typeScale(Type.Code.FIRE, MegaPokemonMasterData.MEGA_X, false), 0.001)
        assertEquals(1.0, pokemon.typeScale(Type.Code.FIRE, MegaPokemonMasterData.MEGA_Y, false), 0.001)

        pokemon.master.megax!!.ability = "ひらいしん"
        assertEquals(1.0, pokemon.typeScale(Type.Code.ELECTRIC, MegaPokemonMasterData.NOT_MEGA, false), 0.001)
        assertEquals(0.0, pokemon.typeScale(Type.Code.ELECTRIC, MegaPokemonMasterData.MEGA_X, false), 0.001)
        assertEquals(2.0, pokemon.typeScale(Type.Code.ELECTRIC, MegaPokemonMasterData.MEGA_X, true), 0.001)
        assertEquals(0.0, pokemon.typeScale(Type.Code.ELECTRIC, MegaPokemonMasterData.MEGA_Y, false), 0.001)

        pokemon.master.megay!!.ability = "ふしぎなまもり"
        assertEquals(1.0, pokemon.typeScale(Type.Code.WATER, MegaPokemonMasterData.NOT_MEGA, false), 0.001)
        assertEquals(2.0, pokemon.typeScale(Type.Code.WATER, MegaPokemonMasterData.MEGA_X, false), 0.001)
        assertEquals(0.0, pokemon.typeScale(Type.Code.WATER, MegaPokemonMasterData.MEGA_Y, false), 0.001)
        assertEquals(2.0, pokemon.typeScale(Type.Code.WATER, MegaPokemonMasterData.MEGA_Y, true), 0.001)
    }

    @Test
    fun HP計算の正常系のテスト() {
        assertEquals(157, kucheat.calcHp(252, MegaPokemonMasterData.NOT_MEGA))
        assertEquals(157, kucheat.calcHp(252, MegaPokemonMasterData.MEGA_X))
    }

    @Test
    fun 攻撃力計算の正常系のテスト() {
        assertEquals(137, kucheat.calcAttack(252, MegaPokemonMasterData.NOT_MEGA))
    }

    @Test
    fun 防御力計算の正常系のテスト() {
        assertEquals(137, kucheat.calcDefense(252, MegaPokemonMasterData.NOT_MEGA))
    }

    @Test
    fun 特殊攻撃力計算の正常系のテスト() {
        assertEquals(107, kucheat.calcSpecialAttack(252, MegaPokemonMasterData.NOT_MEGA))
        assertEquals(107, kucheat.calcSpecialAttack(252, MegaPokemonMasterData.MEGA_X))
    }

    @Test
    fun 特殊防御力計算の正常系のテスト() {
        assertEquals(107, kucheat.calcSpecialDefense(252, MegaPokemonMasterData.NOT_MEGA))
    }

    @Test
    fun 素早さ計算の正常系のテスト() {
        assertEquals(102, kucheat.calcSpeed(252, MegaPokemonMasterData.NOT_MEGA))
        assertEquals(102, kucheat.calcSpeed(252, MegaPokemonMasterData.MEGA_X))
    }

    @Test
    fun メガシンカXした場合の素早さ表計算の正常系のテスト() {
        val actual = kucheat.speedValues(MegaPokemonMasterData.MEGA_X)
        assertEquals(49, actual[0])
        assertEquals(63, actual[1])
        assertEquals(70, actual[2])
        assertEquals(71, actual[3])
        assertEquals(77, actual[4])
        assertEquals(78, actual[5])
        assertEquals(102, actual[6])
        assertEquals(112, actual[7])
    }

    @Test
    fun メガシンカYした場合の素早さ表計算の正常系のテスト() {
        val actual = kucheat.speedValues(MegaPokemonMasterData.MEGA_Y)
        assertEquals(49, actual[0])
        assertEquals(63, actual[1])
        assertEquals(70, actual[2])
        assertEquals(71, actual[3])
        assertEquals(77, actual[4])
        assertEquals(78, actual[5])
        assertEquals(102, actual[6])
        assertEquals(112, actual[7])
    }

    @Test
    fun メガシンカしていない場合の素早さ表計算の正常系のテスト() {
        val actual = kucheat.speedValues(MegaPokemonMasterData.NOT_MEGA)
        assertEquals(49, actual[0])
        assertEquals(63, actual[1])
        assertEquals(70, actual[2])
        assertEquals(71, actual[3])
        assertEquals(77, actual[4])
        assertEquals(78, actual[5])
        assertEquals(102, actual[6])
        assertEquals(112, actual[7])
    }

}