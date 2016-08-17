package com.gmail.sacchin13.pokemonbattleanalyzer

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.PokemonMasterData
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Type
import org.junit.Assert.assertEquals
import org.junit.Test

class TypeTest {
    @Test
    fun testConvertNameToTypeCode() {
        val typeCode1 = Type.code("ほのお")
        assertEquals(Type.Code.FIRE, typeCode1)

        val typeCode2 = Type.code("くさ")
        assertEquals(Type.Code.GRASS, typeCode2)

        val typeCode3 = Type.code("かぜ")
        assertEquals(Type.Code.UNKNOWN, typeCode3);
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
        assertEquals(Type.Code.UNKNOWN, Type.code(100));
    }

    @Test
    fun testCalculateAffinity() {
        val kucheat = PokemonMasterData("303", "クチート", "Mawile", 50, 85, 85, 55, 55, 50,
                "かいりきバサミ", "いかく", "ちからずく", Type.no(Type.Code.STEEL), Type.no(Type.Code.FAIRY), 23.5f)
        var result = Type.calculateAffinity(Type.Code.FIRE, kucheat)
        assertEquals(2.0, result, 0.1)
        result = Type.calculateAffinity(Type.Code.DRAGON, kucheat)
        assertEquals(0.0, result, 0.1)
        result = Type.calculateAffinity(Type.Code.FIGHTING, kucheat)
        assertEquals(1.0, result, 0.1)
        result = Type.calculateAffinity(Type.Code.UNKNOWN, kucheat)
        assertEquals(-1.0, result, 0.1)

        val torimian = PokemonMasterData("676", "トリミアン", "", 50, 85, 85, 55, 55, 50,
                "ファーコート", "", "", Type.no(Type.Code.NORMAL), Type.no(Type.Code.UNKNOWN), 23.5f)

        result = Type.calculateAffinity(Type.Code.FIGHTING, torimian)
        assertEquals(2.0, result, 0.1)
        result = Type.calculateAffinity(Type.Code.DRAGON, torimian)
        assertEquals(1.0, result, 0.1)
        result = Type.calculateAffinity(Type.Code.UNKNOWN, torimian)
        assertEquals(-1.0, result, 0.1)
    }
}
