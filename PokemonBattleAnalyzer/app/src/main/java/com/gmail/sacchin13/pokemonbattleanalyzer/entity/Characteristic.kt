package com.gmail.sacchin13.pokemonbattleanalyzer.entity


class Characteristic {
    companion object Factory {
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
                arrayOf(1.1f,0.9f,1,1,1),
                arrayOf(1.1f,1,0.9f,1,1),
                arrayOf(1.1f,1,1,0.9f,1),
                arrayOf(1.1f,1,1,1,0.9f),
                arrayOf(0.9f,1.1f,1,1,1),
                arrayOf(1,1.1f,0.9f,1,1),
                arrayOf(1,1.1f,1,0.9f,1),
                arrayOf(1,1.1f,1,1,0.9f),
                arrayOf(0.9f,1,1.1f,1,1),
                arrayOf(1,0.9f,1.1f,1,1),
                arrayOf(1,1,1.1f,0.9f,1),
                arrayOf(1,1,1.1f,0.9f,1),
                arrayOf(0.9f,1,1,1.1f,1),
                arrayOf(1,0.9f,1,1.1f,1),
                arrayOf(1,1,0.9f,1.1f,1),
                arrayOf(1,1,1,1.1f,0.9f),
                arrayOf(0.9f,1,1,1,1.1f),
                arrayOf(1,0.9f,1,1,1.1f),
                arrayOf(1,1,0.9f,1,1.1f),
                arrayOf(1,1,1,0.9f,1.1f),
                arrayOf(1,1,1,1,1)
        )

        fun convertCharacteristicNameToNo(skillName: String): Int{
            return if("さみしがり".equals(skillName)) SAMISIGARI
            else if("いじっぱり".equals(skillName)) IJIPPARI
            else if("やんちゃ".equals(skillName)) YANCHA
            else if("ゆうかん".equals(skillName)) YUKAN
            else if("ずぶとい".equals(skillName))ZUBUTOI
            else if("わんぱく".equals(skillName)) WANPAKU
            else if("のうてんき".equals(skillName)) NOTENKI
            else if("のんき".equals(skillName))  NONKI
            else if("ひかえめ".equals(skillName)) HIKAEME
            else if("おっとり".equals(skillName)) OTTORI
            else if("うっかりや".equals(skillName)) UKKARIYA
            else if("れいせい".equals(skillName)) REISEI
            else if("おだやか".equals(skillName)) ODAYAKA
            else if("おとなしい".equals(skillName)) OTONASI
            else if("しんちょう".equals(skillName)) SINCHO
            else if("なまいき".equals(skillName)) NAMAIKI
            else if("おくびょう".equals(skillName)) OKUBYO
            else if("せっかち".equals(skillName)) SEKKATI
            else if("ようき".equals(skillName)) YOKI
            else if("むじゃき".equals(skillName)) MUJAKI
            else if("てれや".equals(skillName)) TEREYA
            else if("がんばりや".equals(skillName)) GANBAIYA
            else if("すなお".equals(skillName)) SUNAO
            else if("きまぐれ".equals(skillName)) KIMAGURE
            else if("まじめ".equals(skillName)) MAJIME
            else -1
        }
    }
}