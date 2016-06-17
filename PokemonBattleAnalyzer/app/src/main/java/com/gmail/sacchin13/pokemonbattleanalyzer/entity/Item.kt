package com.gmail.sacchin13.pokemonbattleanalyzer.entity

import org.json.JSONException
import org.json.JSONObject

class Item(ranking: Int, usageRate: Double, name: String, sequenceNumber: Int) {
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

        fun createItem(item: JSONObject): Item? {
            try {
                val name = item.getString("name")
                if (name == null || name == "null") {
                    return null
                }
                return Item(item.getInt("ranking"), item.getDouble("usageRate"), item.getString("name"), item.getInt("sequenceNumber"))
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            return null
        }
    }

}
