package com.gmail.sacchin13.pokemonbattleanalyzer

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Characteristic
import org.junit.Test as Test

import org.junit.Assert.assertEquals

class CharacteristicTest {
    @Test
    @Throws(Exception::class)
    fun testConvertCharacteristicNameToNo() {
        assertEquals(Characteristic.SAMISIGARI, Characteristic.convertCharacteristicNameToNo("さみしがり"));
        assertEquals(Characteristic.IJIPPARI, Characteristic.convertCharacteristicNameToNo("いじっぱり"));
        assertEquals(-1, Characteristic.convertCharacteristicNameToNo("かわいい"));
    }
}