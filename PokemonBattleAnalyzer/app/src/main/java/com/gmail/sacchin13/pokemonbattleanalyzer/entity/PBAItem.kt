package com.gmail.sacchin13.pokemonbattleanalyzer.entity

import org.json.JSONException
import org.json.JSONObject

public open class PBAItem(
        var ranking: Int = 0,
        var usageRate: Float = 0f,
        var name: ItemMasterData,
        var sequenceNumber: Int = 1){

    companion object {
        fun createItem(item: JSONObject): PBAItem? {
            try {
                val name = item.getString("name")
                if (name == null || name == "null") {
                    return null
                }
                return PBAItem(item.getInt("ranking"), item.getDouble("usageRate").toFloat(),
                        ItemMasterData(item.getString("name")), item.getInt("sequenceNumber"))
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            return null
        }
    }

}
