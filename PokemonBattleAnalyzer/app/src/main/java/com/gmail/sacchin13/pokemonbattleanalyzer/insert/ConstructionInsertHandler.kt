package com.gmail.sacchin13.pokemonbattleanalyzer.insert

import android.util.Log
import com.gmail.sacchin13.pokemonbattleanalyzer.DatabaseHelper
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Construction

class ConstructionInsertHandler(private val databaseHelper: DatabaseHelper?) {

    fun run() {
        Log.i("ConstructionInsert", "開始")

        val garugabugen = arrayOf("ガルーラ", "ガブリアス", "ゲンガー")
        val garugabugena = arrayOf("ガルーラ", "ガブリアス", "ゲンガー")
        databaseHelper!!.insertConstruction("ガルガブゲン", Construction.Type.STANDER, garugabugen, garugabugena,
                "その1その1その1その1その1その1その1その1その1その1その1その1その1その1その1その1その1" +
                        "その1その1その1その1その1その1その1その1その1その1その1その1その1その1その1その1その1")

        val garugabuaro = arrayOf("ガルーラ", "ガブリアス", "ファイアロー")
        val garugabuaroa = arrayOf("ガルーラ", "ガブリアス", "ファイアロー")
        databaseHelper.insertConstruction("ガルガブアロー", Construction.Type.STANDER, garugabuaro, garugabuaroa,
                "その2その2その2その2その2その2その2その2その2その2その2その2その2その2その2その2その2" +
                        "その2その2その2その2その2その2その2その2その2その2その2その2その2その2その2その2その2")

        val bandori = arrayOf("バンギラス", "ドリュウズ")
        val bandoria = arrayOf("バンギラス", "ドリュウズ")
        databaseHelper.insertConstruction("バンドリ", Construction.Type.WEATHER, bandori, bandoria,
                "その3その3その3その3その3その3その3その3その3その3その3その3その3その3その3その3その3" +
                        "その3その3その3その3その3その3その3その3その3その3その3その3その3その3その3その3その3")

        val sazanngarudo = arrayOf("サザンドラ", "ギルガルド")
        val sazanngarudoa = arrayOf("ミミロップ", "ガブリアス", "マンムー")
        databaseHelper.insertConstruction("サザンガルド", Construction.Type.STANDER, sazanngarudo, sazanngarudoa,
                "その3その3その3その3その3その3その3その3その3その3その3その3その3その3その3その3その3" +
                        "その3その3その3その3その3その3その3その3その3その3その3その3その3その3その3その3その3")

        val bangiguraibana = arrayOf("バンギラス", "グライオン", "フシギバナ")
        val bangiguraibanaa = arrayOf("バンギラス", "グライオン", "フシギバナ")
        databaseHelper.insertConstruction("ガルガブアロー", Construction.Type.LOOP, bangiguraibana, bangiguraibanaa,
                "その2その2その2その2その2その2その2その2その2その2その2その2その2その2その2その2その2" +
                        "その2その2その2その2その2その2その2その2その2その2その2その2その2その2その2その2その2")

        Log.i("ConstructionInsert", "ポケモンの構築データOK!")
    }
}
