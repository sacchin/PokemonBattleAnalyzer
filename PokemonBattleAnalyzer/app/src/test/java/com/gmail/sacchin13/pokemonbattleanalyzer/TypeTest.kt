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


    @Test
    fun testy() {
        val damage = 10.0
        val criticalDamage = 10.0


        val randomize = arrayOf(0.85, 0.86, 0.87, 0.88, 0.89, 0.90, 0.91, 0.92, 0.93, 0.94, 0.95, 0.96, 0.97, 0.98, 0.99, 1.0)
        val newRandomDamage = randomize.map { num -> Math.floor(criticalDamage.times(num)) }.plus(randomize.map { num -> Math.floor(damage.times(num)) })


        val randomDamage = arrayOf(
                Math.floor(criticalDamage.times(0.85)),
                Math.floor(criticalDamage.times(0.86)),
                Math.floor(criticalDamage.times(0.87)),
                Math.floor(criticalDamage.times(0.88)),
                Math.floor(criticalDamage.times(0.89)),
                Math.floor(criticalDamage.times(0.90)),
                Math.floor(criticalDamage.times(0.91)),
                Math.floor(criticalDamage.times(0.92)),
                Math.floor(criticalDamage.times(0.93)),
                Math.floor(criticalDamage.times(0.94)),
                Math.floor(criticalDamage.times(0.95)),
                Math.floor(criticalDamage.times(0.96)),
                Math.floor(criticalDamage.times(0.97)),
                Math.floor(criticalDamage.times(0.98)),
                Math.floor(criticalDamage.times(0.99)),
                Math.floor(criticalDamage),
                Math.floor(damage.times(0.85)),
                Math.floor(damage.times(0.86)),
                Math.floor(damage.times(0.87)),
                Math.floor(damage.times(0.88)),
                Math.floor(damage.times(0.89)),
                Math.floor(damage.times(0.90)),
                Math.floor(damage.times(0.91)),
                Math.floor(damage.times(0.92)),
                Math.floor(damage.times(0.93)),
                Math.floor(damage.times(0.94)),
                Math.floor(damage.times(0.95)),
                Math.floor(damage.times(0.96)),
                Math.floor(damage.times(0.97)),
                Math.floor(damage.times(0.98)),
                Math.floor(damage.times(0.99)),
                Math.floor(damage))

        for(t in newRandomDamage) println(t)

        assertEquals(newRandomDamage[0], randomDamage[0], 0.000000001)
        assertEquals(newRandomDamage[2], randomDamage[2], 0.000000001)
        assertEquals(newRandomDamage[4], randomDamage[4], 0.000000001)
        assertEquals(newRandomDamage[6], randomDamage[6], 0.000000001)
        assertEquals(newRandomDamage[7], randomDamage[7], 0.000000001)
        assertEquals(newRandomDamage[9], randomDamage[9], 0.000000001)
        assertEquals(newRandomDamage[11], randomDamage[11], 0.000000001)
        assertEquals(newRandomDamage.size, randomDamage.size)
    }

}
