package com.gmail.sacchin13.pokemonbattleanalyzer.insert

import android.util.Log
import com.gmail.sacchin13.pokemonbattleanalyzer.DatabaseHelper
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Type

class MegaPokemonInsertHandler(private val databaseHelper: DatabaseHelper?) {
    fun run() {
        databaseHelper!!.insertMegaPokemonDataX("003", 80, 100, 123, 122, 120, 80, "あついしぼう", 155.5f)
        databaseHelper.insertMegaPokemonDataX("006", 78, 130, 111, 130, 85, 100, Type.no(Type.Code.FIRE), Type.no(Type.Code.DRAGON), "かたいツメ", 110.5f)
        databaseHelper.insertMegaPokemonDataY("006", 78, 104, 78, 159, 115, 100, "ひでり", 100.5f)
        databaseHelper.insertMegaPokemonDataX("009", 79, 103, 120, 135, 115, 78, "メガランチャー", 101.1f)
        databaseHelper.insertMegaPokemonDataX("015", 65, 150, 40, 15, 80, 145, "てきおうりょく", 40.5f)
        databaseHelper.insertMegaPokemonDataX("018", 83, 80, 80, 135, 80, 121, "ノーガード", 50.5f)
        databaseHelper.insertMegaPokemonDataX("065", 55, 50, 65, 175, 95, 150, "トレース", 48.0f)
        databaseHelper.insertMegaPokemonDataX("080", 95, 75, 180, 130, 80, 30, "シェルアーマー", 120.0f)
        databaseHelper.insertMegaPokemonDataX("094", 60, 65, 80, 170, 95, 130, "かげふみ", 40.5f)
        databaseHelper.insertMegaPokemonDataX("115", 105, 125, 100, 60, 100, 100, "おやこあい", 100.0f)
        databaseHelper.insertMegaPokemonDataX("127", 65, 155, 120, 65, 90, 105, Type.no(Type.Code.BUG), Type.no(Type.Code.FLYING), "スカイスキン", 59.0f)
        databaseHelper.insertMegaPokemonDataX("130", 95, 155, 109, 70, 130, 81, Type.no(Type.Code.WATER), Type.no(Type.Code.DARK), "かたやぶり", 305.0f)
        databaseHelper.insertMegaPokemonDataX("142", 80, 135, 85, 70, 95, 150, "かたいツメ", 79.0f)
        databaseHelper.insertMegaPokemonDataX("150", 106, 190, 100, 154, 100, 130, Type.no(Type.Code.PSYCHIC), Type.no(Type.Code.FIGHTING), "ふくつのこころ", 127.0f)
        databaseHelper.insertMegaPokemonDataX("150", 106, 150, 70, 194, 120, 140, "ふみん", 33.0f)
        databaseHelper.insertMegaPokemonDataX("181", 90, 95, 105, 165, 110, 45, Type.no(Type.Code.ELECTRIC), Type.no(Type.Code.DRAGON), "かたやぶり", 61.5f)
        databaseHelper.insertMegaPokemonDataX("208", 75, 125, 230, 55, 95, 30, "すなのちから", 740.0f)
        databaseHelper.insertMegaPokemonDataX("212", 70, 150, 140, 65, 100, 75, "テクニシャン", 125.0f)
        databaseHelper.insertMegaPokemonDataX("214", 80, 185, 115, 40, 105, 75, "スキルリンク", 62.5f)
        databaseHelper.insertMegaPokemonDataX("229", 75, 90, 90, 140, 90, 115, "サンパワー", 49.5f)
        databaseHelper.insertMegaPokemonDataX("248", 100, 164, 150, 95, 120, 71, "すなおこし", 255.0f)
        databaseHelper.insertMegaPokemonDataX("254", 70, 110, 75, 145, 85, 145, Type.no(Type.Code.GRASS), Type.no(Type.Code.DRAGON), "ひらいしん", 55.2f)
        databaseHelper.insertMegaPokemonDataX("257", 80, 160, 80, 130, 80, 100, "かそく", 52.0f)
        databaseHelper.insertMegaPokemonDataX("260", 100, 150, 110, 95, 110, 70, "すいすい", 102.0f)
        databaseHelper.insertMegaPokemonDataX("282", 68, 85, 65, 165, 135, 100, "フェアリースキン", 48.4f)
        databaseHelper.insertMegaPokemonDataX("302", 50, 85, 125, 85, 115, 20, "マジックミラー", 161.0f)
        databaseHelper.insertMegaPokemonDataX("303", 50, 105, 125, 65, 95, 50, "ちからもち", 23.5f)
        databaseHelper.insertMegaPokemonDataX("306", 70, 140, 230, 60, 80, 50, Type.no(Type.Code.STEEL), Type.no(Type.Code.UNKNOWN), "フィルター", 395.0f)
        databaseHelper.insertMegaPokemonDataX("308", 60, 100, 85, 80, 85, 100, "ヨガパワー", 31.5f)
        databaseHelper.insertMegaPokemonDataX("310", 70, 75, 80, 135, 80, 135, "いかく", 44.0f)
        databaseHelper.insertMegaPokemonDataX("319", 70, 140, 70, 110, 65, 105, "がんじょうあご", 130.3f)
        databaseHelper.insertMegaPokemonDataX("323", 70, 120, 100, 145, 105, 20, "ちからずく", 320.5f)
        databaseHelper.insertMegaPokemonDataX("334", 75, 110, 110, 110, 105, 80, Type.no(Type.Code.DRAGON), Type.no(Type.Code.FAIRY), "フェアリースキン", 20.6f)
        databaseHelper.insertMegaPokemonDataX("354", 64, 165, 75, 93, 83, 75, "いたずらごころ", 13f)
        databaseHelper.insertMegaPokemonDataX("359", 65, 150, 60, 115, 60, 115, "マジックミラー", 49.0f)
        databaseHelper.insertMegaPokemonDataX("362", 80, 120, 80, 120, 80, 100, "フリーズスキン", 350.2f)
        databaseHelper.insertMegaPokemonDataX("373", 95, 145, 130, 120, 90, 120, "スカイスキン", 112.5f)
        databaseHelper.insertMegaPokemonDataX("376", 80, 145, 150, 105, 110, 110, "かたいツメ", 942.9f)
        databaseHelper.insertMegaPokemonDataX("380", 80, 100, 120, 140, 150, 110, "ふゆう", 52.0f)
        databaseHelper.insertMegaPokemonDataX("381", 80, 130, 100, 160, 120, 110, "ふゆう", 70f)
        databaseHelper.insertMegaPokemonDataX("384", 105, 180, 100, 180, 100, 115, "デルタストリーム", 392.0f)
        databaseHelper.insertMegaPokemonDataX("428", 65, 136, 94, 54, 96, 135, Type.no(Type.Code.NORMAL), Type.no(Type.Code.FIGHTING), "きもったま", 28.3f)
        databaseHelper.insertMegaPokemonDataX("445", 108, 170, 115, 120, 95, 92, "すなのちから", 95.0f)
        databaseHelper.insertMegaPokemonDataX("448", 70, 145, 88, 140, 70, 112, "てきおうりょく", 57.5f)
        databaseHelper.insertMegaPokemonDataX("460", 90, 132, 105, 132, 105, 30, "ゆきふらし", 185.0f)
        databaseHelper.insertMegaPokemonDataX("475", 68, 165, 95, 65, 115, 110, "せいしんりょく", 56.4f)
        databaseHelper.insertMegaPokemonDataX("531", 103, 60, 126, 80, 126, 50, Type.no(Type.Code.NORMAL), Type.no(Type.Code.FAIRY), "いやしのこころ", 32.0f)

        databaseHelper.insertMegaPokemonDataX("681", 60, 150, 50, 150, 50, 60, "バトルスイッチ", 53.0f)
        databaseHelper.insertMegaPokemonDataX("555", 105, 30, 105, 140, 105, 55, Type.no(Type.Code.FIRE), Type.no(Type.Code.PSYCHIC), "ダルマモード", 92.9f)

        Log.i("MegaPokemonInsertHandle", "メガシンカポケモンデータOK!")
    }
}

