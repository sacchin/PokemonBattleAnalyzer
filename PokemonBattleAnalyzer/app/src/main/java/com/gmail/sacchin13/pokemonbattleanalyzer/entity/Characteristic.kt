package com.gmail.sacchin13.pokemonbattleanalyzer.entity

import android.util.Log

open class Characteristic {
    companion object Code {
        const val SAMISIGARI = 0
        const val IJIPPARI = 1
        const val YANCHA = 2
        const val YUKAN = 3
        const val ZUBUTOI = 4
        const val WANPAKU = 5
        const val NOTENKI = 6
        const val NONKI = 7
        const val HIKAEME = 8
        const val OTTORI = 9
        const val UKKARIYA = 10
        const val REISEI = 11
        const val ODAYAKA = 12
        const val OTONASI = 13
        const val SINCHO = 14
        const val NAMAIKI = 15
        const val OKUBYO = 16
        const val SEKKATI = 17
        const val YOKI = 18
        const val MUJAKI = 19
        const val TEREYA = 20
        const val GANBAIYA = 21
        const val SUNAO = 22
        const val KIMAGURE = 23
        const val MAJIME = 24

        val CHARACTERISTIC_TABLE = arrayOf(
                arrayOf(1.1, 0.9, 1.0, 1.0, 1.0),
                arrayOf(1.1, 1.0, 0.9, 1.0, 1.0),
                arrayOf(1.1, 1.0, 1.0, 0.9, 1.0),
                arrayOf(1.1, 1.0, 1.0, 1.0, 0.9),
                arrayOf(0.9, 1.1, 1.0, 1.0, 1.0),
                arrayOf(1.0, 1.1, 0.9, 1.0, 1.0),
                arrayOf(1.0, 1.1, 1.0, 0.9, 1.0),
                arrayOf(1.0, 1.1, 1.0, 1.0, 0.9),
                arrayOf(0.9, 1.0, 1.1, 1.0, 1.0),
                arrayOf(1.0, 0.9, 1.1, 1.0, 1.0),
                arrayOf(1.0, 1.0, 1.1, 0.9, 1.0),
                arrayOf(1.0, 1.0, 1.1, 0.9, 1.0),
                arrayOf(0.9, 1.0, 1.0, 1.1, 1.0),
                arrayOf(1.0, 0.9, 1.0, 1.1, 1.0),
                arrayOf(1.0, 1.0, 0.9, 1.1, 1.0),
                arrayOf(1.0, 1.0, 1.0, 1.1, 0.9),
                arrayOf(0.9, 1.0, 1.0, 1.0, 1.1),
                arrayOf(1.0, 0.9, 1.0, 1.0, 1.1),
                arrayOf(1.0, 1.0, 0.9, 1.0, 1.1),
                arrayOf(1.0, 1.0, 1.0, 0.9, 1.1),
                arrayOf(1.0, 1.0, 1.0, 1.0, 1.0),
                arrayOf(1.0, 1.0, 1.0, 1.0, 1.0),
                arrayOf(1.0, 1.0, 1.0, 1.0, 1.0),
                arrayOf(1.0, 1.0, 1.0, 1.0, 1.0),
                arrayOf(1.0, 1.0, 1.0, 1.0, 1.0)
        )

        fun no(name: String): Int {
            return if ("さみしがり".equals(name)) SAMISIGARI
            else if ("いじっぱり".equals(name)) IJIPPARI
            else if ("やんちゃ".equals(name)) YANCHA
            else if ("ゆうかん".equals(name)) YUKAN
            else if ("ずぶとい".equals(name)) ZUBUTOI
            else if ("わんぱく".equals(name)) WANPAKU
            else if ("のうてんき".equals(name)) NOTENKI
            else if ("のんき".equals(name)) NONKI
            else if ("ひかえめ".equals(name)) HIKAEME
            else if ("おっとり".equals(name)) OTTORI
            else if ("うっかりや".equals(name)) UKKARIYA
            else if ("れいせい".equals(name)) REISEI
            else if ("おだやか".equals(name)) ODAYAKA
            else if ("おとなしい".equals(name)) OTONASI
            else if ("しんちょう".equals(name)) SINCHO
            else if ("なまいき".equals(name)) NAMAIKI
            else if ("おくびょう".equals(name)) OKUBYO
            else if ("せっかち".equals(name)) SEKKATI
            else if ("ようき".equals(name)) YOKI
            else if ("むじゃき".equals(name)) MUJAKI
            else if ("てれや".equals(name)) TEREYA
            else if ("がんばりや".equals(name)) GANBAIYA
            else if ("すなお".equals(name)) SUNAO
            else if ("きまぐれ".equals(name)) KIMAGURE
            else if ("まじめ".equals(name)) MAJIME
            else -1
        }

        fun correction(code: String, at: String): Double {
            val index = no(code)
            if (index < 0){
                Log.e("Characteristic", code + " is not correct")
                return 1.0
            }

            val tmp = CHARACTERISTIC_TABLE[index]
            when (at) {
                "A" -> return tmp[0]
                "B" -> return tmp[1]
                "C" -> return tmp[2]
                "D" -> return tmp[3]
                "S" -> return tmp[4]
            }
            return 1.0
        }

    }
}