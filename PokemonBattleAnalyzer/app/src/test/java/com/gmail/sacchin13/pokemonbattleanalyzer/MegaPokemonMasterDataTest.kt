package com.gmail.sacchin13.pokemonbattleanalyzer

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.MegaPokemonMasterData
import org.junit.Assert.assertEquals
import org.junit.Test

class MegaPokemonMasterDataTest {

    @Test
    fun testCreate() {
        val kucheat = MegaPokemonMasterData.create("303", 50, 105, 125, 55, 95, 50, 23.5f, "ちからずく", MegaPokemonMasterData.MEGA_X)
        assertEquals(50, kucheat.h)
        assertEquals(105, kucheat.a)
        assertEquals(125, kucheat.b)
        assertEquals(55, kucheat.c)
        assertEquals(95, kucheat.d)
        assertEquals(50, kucheat.s)
    }
}