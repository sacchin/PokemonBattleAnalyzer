package com.gmail.sacchin13.pokemonbattleanalyzer

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Characteristic
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.PokemonMasterData
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Type
import org.junit.Test as Test

import org.junit.Assert.assertEquals

class PokemonTest {
    @Test
    @Throws(Exception::class)
    fun testConvertCharacteristicNameToNo() {
        assertEquals(Characteristic.SAMISIGARI, Characteristic.convertCharacteristicNameToNo("さみしがり"))
        assertEquals(Characteristic.IJIPPARI, Characteristic.convertCharacteristicNameToNo("いじっぱり"))
        assertEquals(-1, Characteristic.convertCharacteristicNameToNo("かわいい"))
    }


    @Test
    fun testGetHPValue() {
        val kucheat = PokemonMasterData("303", "クチート", "Mawile", 50, 85, 85, 55, 55, 50,
                "かいりきバサミ", "いかく", "ちからずく", Type.no(Type.Code.STEEL), Type.no(Type.Code.FAIRY), 23.5f)
        assertEquals(157, kucheat.getHPValue(31, 252))
        assertEquals(141, kucheat.getHPValue(0, 252))
        assertEquals(125, kucheat.getHPValue(31, 0))
        assertEquals(110, kucheat.getHPValue(0, 0))
    }

    @Test
    fun testGetAttackValue() {
        val kucheat = PokemonMasterData("303", "クチート", "Mawile", 50, 85, 85, 55, 55, 50,
                "かいりきバサミ", "いかく", "ちからずく", Type.no(Type.Code.STEEL), Type.no(Type.Code.FAIRY), 23.5f)
        assertEquals(137, kucheat.getAttackValue(31, 252))
        assertEquals(121, kucheat.getAttackValue(0, 252))
        assertEquals(105, kucheat.getAttackValue(31, 0))
        assertEquals(90, kucheat.getAttackValue(0, 0))
    }

    @Test
    fun testGetDefenseValue() {
        val kucheat = PokemonMasterData("303", "クチート", "Mawile", 50, 85, 85, 55, 55, 50,
                "かいりきバサミ", "いかく", "ちからずく", Type.no(Type.Code.STEEL), Type.no(Type.Code.FAIRY), 23.5f)
        assertEquals(137, kucheat.getDefenseValue(31, 252))
        assertEquals(121, kucheat.getDefenseValue(0, 252))
        assertEquals(105, kucheat.getDefenseValue(31, 0))
        assertEquals(90, kucheat.getDefenseValue(0, 0))
    }

    @Test
    fun testGetSpecialAttackValue() {
        val kucheat = PokemonMasterData("303", "クチート", "Mawile", 50, 85, 85, 55, 55, 50,
                "かいりきバサミ", "いかく", "ちからずく", Type.no(Type.Code.STEEL), Type.no(Type.Code.FAIRY), 23.5f)
        assertEquals(107, kucheat.getSpecialAttackValue(31, 252))
        assertEquals(91, kucheat.getSpecialAttackValue(0, 252))
        assertEquals(75, kucheat.getSpecialAttackValue(31, 0))
        assertEquals(60, kucheat.getSpecialAttackValue(0, 0))
    }

    @Test
    fun testGetSpecialDefenseValue() {
        val kucheat = PokemonMasterData("303", "クチート", "Mawile", 50, 85, 85, 55, 55, 50,
                "かいりきバサミ", "いかく", "ちからずく", Type.no(Type.Code.STEEL), Type.no(Type.Code.FAIRY), 23.5f)
        assertEquals(107, kucheat.getSpecialDefenseValue(31, 252))
        assertEquals(91, kucheat.getSpecialDefenseValue(0, 252))
        assertEquals(75, kucheat.getSpecialDefenseValue(31, 0))
        assertEquals(60, kucheat.getSpecialDefenseValue(0, 0))
    }

    @Test
    fun testGetSpeedValue() {
        val kucheat = PokemonMasterData("303", "クチート", "Mawile", 50, 85, 85, 55, 55, 50,
                "かいりきバサミ", "いかく", "ちからずく",Type.no(Type.Code.STEEL), Type.no(Type.Code.FAIRY), 23.5f)
        assertEquals(102, kucheat.getSpeedValue(31, 252))
        assertEquals(86, kucheat.getSpeedValue(0, 252))
        assertEquals(70, kucheat.getSpeedValue(31, 0))
        assertEquals(55, kucheat.getSpeedValue(0, 0))
    }
}