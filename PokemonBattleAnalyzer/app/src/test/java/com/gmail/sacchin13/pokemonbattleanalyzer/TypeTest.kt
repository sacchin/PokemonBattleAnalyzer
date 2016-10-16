package com.gmail.sacchin13.pokemonbattleanalyzer

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Type
import org.junit.Assert.assertEquals
import org.junit.Test

class TypeTest {

    @Test
    fun testConvertNameToTypeCode() {
        assertEquals(Type.Code.FIRE, Type.code("ほのお"))
        assertEquals(Type.Code.GRASS, Type.code("くさ"))
        assertEquals(Type.Code.UNKNOWN, Type.code("かぜ"))
        assertEquals(Type.Code.UNKNOWN, Type.code(""))
    }

    @Test
    fun testConvertTypeCodeToName() {
        assertEquals("でんき", Type.name(Type.Code.ELECTRIC))
        assertEquals("ドラゴン", Type.name(Type.Code.DRAGON))
        assertEquals("UNKNOWN", Type.name(Type.Code.UNKNOWN))
    }

    @Test
    fun testConvertTypeCodeToNo() {
        assertEquals(6, Type.no(Type.Code.FIGHTING))
        assertEquals(14, Type.no(Type.Code.DRAGON))
        assertEquals(-1, Type.no(Type.Code.UNKNOWN))
    }

    @Test
    fun testConvertNoToTypeCode() {
        assertEquals(Type.Code.BUG, Type.code(11))
        assertEquals(Type.Code.DARK, Type.code(15))
        assertEquals(Type.Code.UNKNOWN, Type.code(100))
        assertEquals(Type.Code.UNKNOWN, Type.code(-1))
    }

    @Test
    fun testCalculateAffinity() {
        assertEquals(2.0, Type.calculateAffinity(Type.Code.FIRE, Type.Code.STEEL, Type.Code.FAIRY), 0.1)
        assertEquals(0.0, Type.calculateAffinity(Type.Code.DRAGON, Type.Code.STEEL, Type.Code.FAIRY), 0.1)
        assertEquals(1.0, Type.calculateAffinity(Type.Code.FIGHTING, Type.Code.STEEL, Type.Code.FAIRY), 0.1)
        assertEquals(-1.0, Type.calculateAffinity(Type.Code.UNKNOWN, Type.Code.STEEL, Type.Code.FAIRY), 0.1)

        assertEquals(2.0, Type.calculateAffinity(Type.Code.FIGHTING, Type.Code.NORMAL, Type.Code.UNKNOWN), 0.1)
        assertEquals(1.0, Type.calculateAffinity(Type.Code.DRAGON, Type.Code.NORMAL, Type.Code.UNKNOWN), 0.1)
        assertEquals(-1.0, Type.calculateAffinity(Type.Code.UNKNOWN, Type.Code.NORMAL, Type.Code.UNKNOWN), 0.1)

        assertEquals(-1.0, Type.calculateAffinity(Type.Code.BUG, Type.Code.UNKNOWN, Type.Code.UNKNOWN), 0.1)
        assertEquals(-1.0, Type.calculateAffinity(Type.Code.UNKNOWN, Type.Code.UNKNOWN, Type.Code.UNKNOWN), 0.1)
    }
}
