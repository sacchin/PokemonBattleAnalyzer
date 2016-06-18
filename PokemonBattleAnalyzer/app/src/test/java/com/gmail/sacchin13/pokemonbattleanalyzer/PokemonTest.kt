package com.gmail.sacchin13.pokemonbattleanalyzer

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Characteristic
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Pokemon
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


//    @Test
//    fun testGetHPValue() {
//        val kucheat = Pokemon("303", "クチート", "Mawile", 50, 85, 85, 55, 55, 50,
//                "かいりきバサミ", "いかく", "ちからずく", Type.TypeCode.STEEL, Type.TypeCode.FAIRY, 23.5f)
//        assertEquals(157, kucheat.getHPValue(31, 252))
//        assertEquals(141, kucheat.getHPValue(0, 252))
//        assertEquals(125, kucheat.getHPValue(31, 0))
//        assertEquals(110, kucheat.getHPValue(0, 0))
//    }
//
//    @Test
//    fun testGetAttackValue() {
//        val kucheat = Pokemon("303", "クチート", "Mawile", 50, 85, 85, 55, 55, 50,
//                "かいりきバサミ", "いかく", "ちからずく", Type.TypeCode.STEEL, Type.TypeCode.FAIRY, 23.5f)
//        assertEquals(137, kucheat.getAttackValue(31, 252))
//        assertEquals(121, kucheat.getAttackValue(0, 252))
//        assertEquals(105, kucheat.getAttackValue(31, 0))
//        assertEquals(90, kucheat.getAttackValue(0, 0))
//    }
//
//    @Test
//    fun testGetDeffenceValue() {
//        val kucheat = Pokemon("303", "クチート", "Mawile", 50, 85, 85, 55, 55, 50,
//                "かいりきバサミ", "いかく", "ちからずく", Type.TypeCode.STEEL, Type.TypeCode.FAIRY, 23.5f)
//        assertEquals(137, kucheat.getDeffenceValue(31, 252))
//        assertEquals(121, kucheat.getDeffenceValue(0, 252))
//        assertEquals(105, kucheat.getDeffenceValue(31, 0))
//        assertEquals(90, kucheat.getDeffenceValue(0, 0))
//    }
//
//    @Test
//    fun testGetSecialAttackValue() {
//        val kucheat = Pokemon("303", "クチート", "Mawile", 50, 85, 85, 55, 55, 50,
//                "かいりきバサミ", "いかく", "ちからずく", Type.TypeCode.STEEL, Type.TypeCode.FAIRY, 23.5f)
//        assertEquals(107, kucheat.getSpecialAttackValue(31, 252))
//        assertEquals(91, kucheat.getSpecialAttackValue(0, 252))
//        assertEquals(75, kucheat.getSpecialAttackValue(31, 0))
//        assertEquals(60, kucheat.getSpecialAttackValue(0, 0))
//    }
//
//    @Test
//    fun testGetSpecialDeffenceValue() {
//        val kucheat = Pokemon("303", "クチート", "Mawile", 50, 85, 85, 55, 55, 50,
//                "かいりきバサミ", "いかく", "ちからずく", Type.TypeCode.STEEL, Type.TypeCode.FAIRY, 23.5f)
//        assertEquals(107, kucheat.getSpecialDeffenceValue(31, 252))
//        assertEquals(91, kucheat.getSpecialDeffenceValue(0, 252))
//        assertEquals(75, kucheat.getSpecialDeffenceValue(31, 0))
//        assertEquals(60, kucheat.getSpecialDeffenceValue(0, 0))
//    }
//
//    @Test
//    fun testGetSpeedValue() {
//        val kucheat = Pokemon("303", "クチート", "Mawile", 50, 85, 85, 55, 55, 50,
//                "かいりきバサミ", "いかく", "ちからずく",Type.TypeCode.STEEL, Type.TypeCode.FAIRY, 23.5f)
//        assertEquals(107, kucheat.getSpecialDeffenceValue(31, 252))
//        assertEquals(91, kucheat.getSpecialDeffenceValue(0, 252))
//        assertEquals(75, kucheat.getSpecialDeffenceValue(31, 0))
//        assertEquals(60, kucheat.getSpecialDeffenceValue(0, 0))
//    }
}