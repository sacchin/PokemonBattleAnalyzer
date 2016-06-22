package com.gmail.sacchin13.pokemonbattleanalyzer

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl.RankingResponse
import com.gmail.sacchin13.pokemonbattleanalyzer.http.PokemonTrendDownloader
import org.junit.Test

import org.junit.Assert.*

class PokemonTrendDownloaderUnitTest {

    inner class TestListener: PokemonTrendDownloader.EventListener{
        override fun onFinish(result: RankingResponse){
            assertTrue(false)
        }
    }

    @Test
    @Throws(Exception::class)
    fun addition_isCorrect() {
//        PokemonTrendDownloader("", 0, TestListener()).doInBackground()
    }

    @Test
    @Throws(Exception::class)
    fun test_isCorrect() {
        assertEquals(4, (2 + 2).toLong())
    }
}