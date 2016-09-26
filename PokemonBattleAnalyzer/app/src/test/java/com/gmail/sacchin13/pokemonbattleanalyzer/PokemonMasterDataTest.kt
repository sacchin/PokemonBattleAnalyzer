package com.gmail.sacchin13.pokemonbattleanalyzer

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.MegaPokemonMasterData
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.PokemonMasterData
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Type
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.properties.Delegates

class PokemonMasterDataTest {

    var kucheat: PokemonMasterData by Delegates.notNull()
    var megaKucheat: MegaPokemonMasterData by Delegates.notNull()
    var hihidaruma: PokemonMasterData by Delegates.notNull()
    var girugarudo: PokemonMasterData by Delegates.notNull()

    @Before
    fun init() {
        megaKucheat = MegaPokemonMasterData.create("303", 50, 105, 125, 55, 95, 50,
                Type.no(Type.Code.STEEL), Type.no(Type.Code.FAIRY), 23.5f, "ちからずく", MegaPokemonMasterData.MEGA_X)
        kucheat = PokemonMasterData.create("303", "クチート", "Mawile", "-", 50, 85, 85, 55, 55, 50,
                "かいりきバサミ", "いかく", "ちからずく", Type.no(Type.Code.STEEL), Type.no(Type.Code.FAIRY), 23.5f, megaKucheat, megaKucheat)
        hihidaruma = PokemonMasterData.create("555", "ヒヒダルマ", "-", "-", 50, 85, 85, 55, 55, 50,
                "-", "-", "-", Type.no(Type.Code.STEEL), Type.no(Type.Code.FAIRY), 23.5f, megaKucheat)
        girugarudo = PokemonMasterData.create("681", "ギルガルド", "-", "-", 50, 85, 85, 55, 55, 50,
                "-", "-", "-", Type.no(Type.Code.STEEL), Type.no(Type.Code.FAIRY), 23.5f, megaKucheat)
    }

    @Test
    fun testGetHpValue() {
        assertEquals(157, kucheat.hp(31, 252))
        assertEquals(141, kucheat.hp(0, 252))
        assertEquals(125, kucheat.hp(31, 0))
        assertEquals(110, kucheat.hp(0, 0))
    }

    @Test
    fun testGetHpXValue() {
        assertEquals(157, kucheat.hpX(31, 252))
        assertEquals(141, kucheat.hpX(0, 252))
        assertEquals(125, kucheat.hpX(31, 0))
        assertEquals(110, kucheat.hpX(0, 0))
    }

    @Test
    fun testGetAttackValue() {
        assertEquals(137, kucheat.attack(31, 252))
        assertEquals(121, kucheat.attack(0, 252))
        assertEquals(105, kucheat.attack(31, 0))
        assertEquals(90, kucheat.attack(0, 0))
    }

    @Test
    fun testGetAttackXValue() {
        assertEquals(157, kucheat.attackX(31, 252))
        assertEquals(141, kucheat.attackX(0, 252))
        assertEquals(125, kucheat.attackX(31, 0))
        assertEquals(110, kucheat.attackX(0, 0))
    }

    @Test
    fun testGetDefenseValue() {
        assertEquals(137, kucheat.defense(31, 252))
        assertEquals(121, kucheat.defense(0, 252))
        assertEquals(105, kucheat.defense(31, 0))
        assertEquals(90, kucheat.defense(0, 0))
    }

    @Test
    fun testGetSpecialAttackValue() {
        assertEquals(107, kucheat.specialAttack(31, 252))
        assertEquals(91, kucheat.specialAttack(0, 252))
        assertEquals(75, kucheat.specialAttack(31, 0))
        assertEquals(60, kucheat.specialAttack(0, 0))
    }

    @Test
    fun testGetSpecialAttackXValue() {
        assertEquals(107, kucheat.specialAttackX(31, 252))
        assertEquals(91, kucheat.specialAttackX(0, 252))
        assertEquals(75, kucheat.specialAttackX(31, 0))
        assertEquals(60, kucheat.specialAttackX(0, 0))
    }

    @Test
    fun testGetSpecialDefenseValue() {
        assertEquals(107, kucheat.specialDefense(31, 252))
        assertEquals(91, kucheat.specialDefense(0, 252))
        assertEquals(75, kucheat.specialDefense(31, 0))
        assertEquals(60, kucheat.specialDefense(0, 0))
    }

    @Test
    fun testGetSpeedValue() {
        assertEquals(102, kucheat.speed(31, 252))
        assertEquals(86, kucheat.speed(0, 252))
        assertEquals(70, kucheat.speed(31, 0))
        assertEquals(55, kucheat.speed(0, 0))
    }

    @Test
    fun testGetSpeedXValue() {
        assertEquals(102, kucheat.speedX(31, 252))
        assertEquals(86, kucheat.speedX(0, 252))
        assertEquals(70, kucheat.speedX(31, 0))
        assertEquals(55, kucheat.speedX(0, 0))
    }

    @Test
    fun testGetSpeedValues() {
        val actual = kucheat.speedValues()
        assertEquals(49, actual[0])
        assertEquals(63, actual[1])
        assertEquals(70, actual[2])
        assertEquals(71, actual[3])
        assertEquals(77, actual[4])
        assertEquals(94, actual[5])
        assertEquals(102, actual[6])
        assertEquals(105, actual[7])
        assertEquals(115, actual[8])
        assertEquals(112, actual[9])
    }

    @Test
    fun testArraysForSpinner() {
        var actual = kucheat.battling()
        assertEquals("-", actual[0])
        assertEquals("X", actual[1])
        assertEquals("Y", actual[2])

        actual = hihidaruma.battling()
        assertEquals("-", actual[0])
        assertEquals("D", actual[1])

        actual = girugarudo.battling()
        assertEquals("-", actual[0])
        assertEquals("B", actual[1])
    }
}