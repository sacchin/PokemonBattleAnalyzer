package com.gmail.sacchin13.pokemonbattleanalyzer.entity

import org.json.JSONException
import org.json.JSONObject

class PokemonCharacteristic(ranking: Int, usageRate: Double, name: String, sequenceNumber: Int) : Characteristic() {
    var ranking = 0
    var usageRate = 0.0
    var name = ""
    var sequenceNumber = 0

    init {
        this.ranking = ranking
        this.usageRate = usageRate
        this.name = name
        this.sequenceNumber = sequenceNumber
    }

    companion object {
        val CHARACTERISTIC = arrayOf("さみしがり", "いじっぱり", "やんちゃ", "ゆうかん", "ずぶとい", "わんぱく", "のうてんき", "のんき", "ひかえめ", "おっとり", "うっかりや", "れいせい", "おだやか", "おとなしい", "しんちょう", "なまいき", "おくびょう", "せっかち", "ようき", "むじゃき", "てれや", "がんばりや", "すなお", "きまぐれ", "まじめ")

        fun createCharacteristic(seikaku: JSONObject): PokemonCharacteristic? {
            try {
                val name = seikaku.getString("name")
                if (name == null || name == "null") {
                    return null
                }
                return PokemonCharacteristic(seikaku.getInt("ranking"), seikaku.getDouble("usageRate"), seikaku.getString("name"), seikaku.getInt("sequenceNumber"))
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            return null
        }
    }
}
