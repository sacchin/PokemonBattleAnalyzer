package com.gmail.sacchin13.pokemonbattleanalyzer

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.MegaPokemonMasterData
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.PokemonMasterData
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Type
import org.junit.Assert.assertEquals
import org.junit.Test

class PokemonMasterDataTest {

    @Test
    fun testGetHpValue() {
        val megaKucheat = MegaPokemonMasterData.create("303", 50, 105, 125, 55, 95, 50, 23.5f, "ちからずく", MegaPokemonMasterData.MEGA_X)
        val kucheat = PokemonMasterData.create("303", "クチート", "Mawile", "-", 50, 85, 85, 55, 55, 50,
                "かいりきバサミ", "いかく", "ちからずく", Type.no(Type.Code.STEEL), Type.no(Type.Code.FAIRY), 23.5f, megaKucheat)
        assertEquals(157, kucheat.hp(31, 252))
        assertEquals(141, kucheat.hp(0, 252))
        assertEquals(125, kucheat.hp(31, 0))
        assertEquals(110, kucheat.hp(0, 0))
    }

    @Test
    fun testGetHpXValue() {
        val megaKucheat = MegaPokemonMasterData.create("303", 50, 105, 125, 55, 95, 50, 23.5f, "ちからずく", MegaPokemonMasterData.MEGA_X)
        val kucheat = PokemonMasterData.create("303", "クチート", "Mawile", "-", 50, 85, 85, 55, 55, 50,
                "かいりきバサミ", "いかく", "ちからずく", Type.no(Type.Code.STEEL), Type.no(Type.Code.FAIRY), 23.5f, megaKucheat)
        assertEquals(157, kucheat.hpX(31, 252))
        assertEquals(141, kucheat.hpX(0, 252))
        assertEquals(125, kucheat.hpX(31, 0))
        assertEquals(110, kucheat.hpX(0, 0))
    }

    @Test
    fun testGetAttackValue() {
        val megaKucheat = MegaPokemonMasterData.create("303", 50, 105, 125, 55, 95, 50, 23.5f, "ちからずく", MegaPokemonMasterData.MEGA_X)
        val kucheat = PokemonMasterData.create("303", "クチート", "Mawile", "-", 50, 85, 85, 55, 55, 50,
                "かいりきバサミ", "いかく", "ちからずく", Type.no(Type.Code.STEEL), Type.no(Type.Code.FAIRY), 23.5f, megaKucheat)
        assertEquals(137, kucheat.attack(31, 252))
        assertEquals(121, kucheat.attack(0, 252))
        assertEquals(105, kucheat.attack(31, 0))
        assertEquals(90, kucheat.attack(0, 0))
    }

    @Test
    fun testGetAttackXValue() {
        val megaKucheat = MegaPokemonMasterData.create("303", 50, 105, 125, 55, 95, 50, 23.5f, "ちからずく", MegaPokemonMasterData.MEGA_X)
        val kucheat = PokemonMasterData.create("303", "クチート", "Mawile", "-", 50, 85, 85, 55, 55, 50,
                "かいりきバサミ", "いかく", "ちからずく", Type.no(Type.Code.STEEL), Type.no(Type.Code.FAIRY), 23.5f, megaKucheat)
        assertEquals(157, kucheat.attackX(31, 252))
        assertEquals(141, kucheat.attackX(0, 252))
        assertEquals(125, kucheat.attackX(31, 0))
        assertEquals(110, kucheat.attackX(0, 0))
    }

    @Test
    fun testGetDefenseValue() {
        val megaKucheat = MegaPokemonMasterData.create("303", 50, 105, 125, 55, 95, 50, 23.5f, "ちからずく", MegaPokemonMasterData.MEGA_X)
        val kucheat = PokemonMasterData.create("303", "クチート", "Mawile", "-", 50, 85, 85, 55, 55, 50,
                "かいりきバサミ", "いかく", "ちからずく", Type.no(Type.Code.STEEL), Type.no(Type.Code.FAIRY), 23.5f, megaKucheat)
        assertEquals(137, kucheat.defense(31, 252))
        assertEquals(121, kucheat.defense(0, 252))
        assertEquals(105, kucheat.defense(31, 0))
        assertEquals(90, kucheat.defense(0, 0))
    }

    @Test
    fun testGetSpecialAttackValue() {
        val megaKucheat = MegaPokemonMasterData.create("303", 50, 105, 125, 55, 95, 50, 23.5f, "ちからずく", MegaPokemonMasterData.MEGA_X)
        val kucheat = PokemonMasterData.create("303", "クチート", "Mawile", "-", 50, 85, 85, 55, 55, 50,
                "かいりきバサミ", "いかく", "ちからずく", Type.no(Type.Code.STEEL), Type.no(Type.Code.FAIRY), 23.5f, megaKucheat)
        assertEquals(107, kucheat.specialAttack(31, 252))
        assertEquals(91, kucheat.specialAttack(0, 252))
        assertEquals(75, kucheat.specialAttack(31, 0))
        assertEquals(60, kucheat.specialAttack(0, 0))
    }

    @Test
    fun testGetSpecialDefenseValue() {
        val megaKucheat = MegaPokemonMasterData.create("303", 50, 105, 125, 55, 95, 50, 23.5f, "ちからずく", MegaPokemonMasterData.MEGA_X)
        val kucheat = PokemonMasterData.create("303", "クチート", "Mawile", "-", 50, 85, 85, 55, 55, 50,
                "かいりきバサミ", "いかく", "ちからずく", Type.no(Type.Code.STEEL), Type.no(Type.Code.FAIRY), 23.5f, megaKucheat)
        assertEquals(107, kucheat.specialDefense(31, 252))
        assertEquals(91, kucheat.specialDefense(0, 252))
        assertEquals(75, kucheat.specialDefense(31, 0))
        assertEquals(60, kucheat.specialDefense(0, 0))
    }

    @Test
    fun testGetSpeedValue() {
        val megaKucheat = MegaPokemonMasterData.create("303", 50, 105, 125, 55, 95, 50, 23.5f, "ちからずく", MegaPokemonMasterData.MEGA_X)
        val kucheat = PokemonMasterData.create("303", "クチート", "Mawile", "-", 50, 85, 85, 55, 55, 50,
                "かいりきバサミ", "いかく", "ちからずく", Type.no(Type.Code.STEEL), Type.no(Type.Code.FAIRY), 23.5f, megaKucheat)
        assertEquals(102, kucheat.speed(31, 252))
        assertEquals(86, kucheat.speed(0, 252))
        assertEquals(70, kucheat.speed(31, 0))
        assertEquals(55, kucheat.speed(0, 0))
    }

    @Test
    fun testGetSpeedValues() {
        val megaKucheat = MegaPokemonMasterData.create("303", 50, 105, 125, 55, 95, 50, 23.5f, "ちからずく", MegaPokemonMasterData.MEGA_X)
        val kucheat = PokemonMasterData.create("303", "クチート", "Mawile", "-", 50, 85, 85, 55, 55, 50,
                "かいりきバサミ", "いかく", "ちからずく", Type.no(Type.Code.STEEL), Type.no(Type.Code.FAIRY), 23.5f, megaKucheat)
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
}