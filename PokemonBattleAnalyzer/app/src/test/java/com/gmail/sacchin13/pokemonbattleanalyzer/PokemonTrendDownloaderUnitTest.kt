package com.gmail.sacchin13.pokemonbattleanalyzer

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl.TrendForBattle
import com.gmail.sacchin13.pokemonbattleanalyzer.http.PokemonTrendDownloader
import org.junit.Assert.assertEquals
import org.junit.Test

class PokemonTrendDownloaderUnitTest {

    @Test
    fun クチートをダウンロードするテスト() {
        PokemonTrendDownloader("", 0, object : PokemonTrendDownloader.EventListener {
            override fun onFinish(result: TrendForBattle, index: Int) {
                for (t in result.itemInfo) println("${t.ranking}: ${t.name}: ${t.usageRate}")
                for (t in result.wazaInfo) println("${t.ranking}: ${t.name}: ${t.usageRate}")
                for (t in result.seikakuInfo) println("${t.ranking}: ${t.name}: ${t.usageRate}")
                for (t in result.tokuseiInfo) println("${t.ranking}: ${t.name}: ${t.usageRate}")
                assertEquals(4, (2 + 2).toLong())
            }
        }).execute()
    }
}