package com.gmail.sacchin13.pokemonbattleanalyzer.entity

object Type {

    enum class Code {
        NORMAL, FIRE, WATER, ELECTRIC, GRASS, ICE, FIGHTING, POISON, GROUND, FLYING, PSYCHIC, BUG, ROCK, GHOST, DRAGON, DARK, STEEL, FAIRY, UNKNOWN
    }

    var AFFINITY_TABLE = arrayOf(
            floatArrayOf(1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 0.5f, 0f, 1f, 1f, 0.5f, 1f),
            floatArrayOf(1f, 0.5f, 0.5f, 1f, 2f, 2f, 1f, 1f, 1f, 1f, 1f, 2f, 0.5f, 1f, 0.5f, 1f, 2f, 1f),
            floatArrayOf(1f, 2f, 0.5f, 1f, 0.5f, 1f, 1f, 1f, 2f, 1f, 1f, 1f, 2f, 1f, 0.5f, 1f, 1f, 1f),
            floatArrayOf(1f, 1f, 2f, 0.5f, 0.5f, 1f, 1f, 1f, 0f, 2f, 1f, 1f, 1f, 1f, 0.5f, 1f, 1f, 1f),
            floatArrayOf(1f, 0.5f, 2f, 1f, 0.5f, 1f, 1f, 0.5f, 2f, 0.5f, 1f, 0.5f, 2f, 1f, 0.5f, 1f, 0.5f, 1f),
            floatArrayOf(1f, 0.5f, 0.5f, 1f, 2f, 0.5f, 1f, 1f, 2f, 2f, 1f, 1f, 1f, 1f, 2f, 1f, 0.5f, 1f),
            floatArrayOf(2f, 1f, 1f, 1f, 1f, 2f, 1f, 0.5f, 1f, 0.5f, 0.5f, 0.5f, 2f, 0f, 1f, 2f, 2f, 0.5f),
            floatArrayOf(1f, 1f, 1f, 1f, 2f, 1f, 1f, 0.5f, 0.5f, 1f, 1f, 1f, 0.5f, 0.5f, 1f, 1f, 0f, 2f),
            floatArrayOf(1f, 2f, 1f, 2f, 0.5f, 1f, 1f, 2f, 1f, 0f, 1f, 0.5f, 2f, 1f, 1f, 1f, 2f, 1f),
            floatArrayOf(1f, 1f, 1f, 0.5f, 2f, 1f, 2f, 1f, 1f, 1f, 1f, 2f, 0.5f, 1f, 1f, 1f, 0.5f, 1f),
            floatArrayOf(1f, 1f, 1f, 1f, 1f, 1f, 2f, 2f, 1f, 1f, 0.5f, 1f, 1f, 1f, 1f, 0f, 0.5f, 1f),
            floatArrayOf(1f, 0.5f, 1f, 1f, 2f, 1f, 0.5f, 0.5f, 1f, 0.5f, 2f, 1f, 1f, 0.5f, 1f, 2f, 0.5f, 0.5f),
            floatArrayOf(1f, 2f, 1f, 1f, 1f, 2f, 0.5f, 1f, 0.5f, 2f, 1f, 2f, 1f, 1f, 1f, 1f, 0.5f, 1f),
            floatArrayOf(0f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 2f, 1f, 1f, 2f, 1f, 0.5f, 1f, 1f),
            floatArrayOf(1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 11f, 1f, 1f, 1f, 1f, 1f, 2f, 1f, 0.5f, 0f),
            floatArrayOf(1f, 1f, 1f, 1f, 1f, 1f, 0.5f, 1f, 1f, 1f, 2f, 1f, 1f, 2f, 1f, 0.5f, 1f, 0.5f),
            floatArrayOf(1f, 0.5f, 0.5f, 0.5f, 1f, 2f, 1f, 1f, 1f, 1f, 1f, 1f, 2f, 1f, 1f, 1f, 0.5f, 2f),
            floatArrayOf(1f, 0.5f, 1f, 1f, 1f, 1f, 2f, 0.5f, 1f, 1f, 1f, 1f, 1f, 1f, 2f, 2f, 0.5f, 1f))

    fun code(typeName: String): Code {
        return if ("ノーマル".equals(typeName)) Code.NORMAL
        else if ("ほのお".equals(typeName)) Code.FIRE
        else if ("みず".equals(typeName)) Code.WATER
        else if ("でんき".equals(typeName)) Code.ELECTRIC
        else if ("くさ".equals(typeName)) Code.GRASS
        else if ("こおり".equals(typeName)) Code.ICE
        else if ("かくとう".equals(typeName)) Code.FIGHTING
        else if ("どく".equals(typeName)) Code.POISON
        else if ("じめん".equals(typeName)) Code.GROUND
        else if ("ひこう".equals(typeName)) Code.FLYING
        else if ("エスパー".equals(typeName)) Code.PSYCHIC
        else if ("むし".equals(typeName)) Code.BUG
        else if ("いわ".equals(typeName)) Code.ROCK
        else if ("ゴースト".equals(typeName)) Code.GHOST
        else if ("ドラゴン".equals(typeName)) Code.DRAGON
        else if ("あく".equals(typeName)) Code.DARK
        else if ("はがね".equals(typeName)) Code.STEEL
        else if ("フェアリー".equals(typeName)) Code.FAIRY
        else Code.UNKNOWN
    }

    fun name(type: Code): String {
        when (type) {
            Type.Code.NORMAL -> return "ノーマル"
            Type.Code.FIRE -> return "ほのお"
            Type.Code.WATER -> return "みず"
            Type.Code.ELECTRIC -> return "でんき"
            Type.Code.GRASS -> return "くさ"
            Type.Code.ICE -> return "こおり"
            Type.Code.FIGHTING -> return "かくとう"
            Type.Code.POISON -> return "どく"
            Type.Code.GROUND -> return "じめん"
            Type.Code.FLYING -> return "ひこう"
            Type.Code.PSYCHIC -> return "エスパー"
            Type.Code.BUG -> return "むし"
            Type.Code.ROCK -> return "いわ"
            Type.Code.GHOST -> return "ゴースト"
            Type.Code.DRAGON -> return "ドラゴン"
            Type.Code.DARK -> return "あく"
            Type.Code.STEEL -> return "はがね"
            Type.Code.FAIRY -> return "フェアリー"
            else -> return "UNKNOWN"
        }
    }

    fun zNo(zCrystal: String): Code {
        return if (zCrystal.contains("ノーマル")) Code.NORMAL
        else if (zCrystal.contains("ホノオ")) Code.FIRE
        else if (zCrystal.contains("ミズ")) Code.WATER
        else if (zCrystal.contains("デンキ")) Code.ELECTRIC
        else if (zCrystal.contains("クサ")) Code.GRASS
        else if (zCrystal.contains("コオリ")) Code.ICE
        else if (zCrystal.contains("カクトウ")) Code.FIGHTING
        else if (zCrystal.contains("ドク")) Code.POISON
        else if (zCrystal.contains("ジメン")) Code.GROUND
        else if (zCrystal.contains("ヒコウ")) Code.FLYING
        else if (zCrystal.contains("エスパー")) Code.PSYCHIC
        else if (zCrystal.contains("ムシ")) Code.BUG
        else if (zCrystal.contains("イワ")) Code.ROCK
        else if (zCrystal.contains("ゴースト")) Code.GHOST
        else if (zCrystal.contains("ドラゴン")) Code.DRAGON
        else if (zCrystal.contains("アク")) Code.DARK
        else if (zCrystal.contains("ハガネ")) Code.STEEL
        else if (zCrystal.contains("フェアリー")) Code.FAIRY
        else Code.UNKNOWN
    }

    fun no(type: Code): Int {
        when (type) {
            Type.Code.NORMAL -> return 0
            Type.Code.FIRE -> return 1
            Type.Code.WATER -> return 2
            Type.Code.ELECTRIC -> return 3
            Type.Code.GRASS -> return 4
            Type.Code.ICE -> return 5
            Type.Code.FIGHTING -> return 6
            Type.Code.POISON -> return 7
            Type.Code.GROUND -> return 8
            Type.Code.FLYING -> return 9
            Type.Code.PSYCHIC -> return 10
            Type.Code.BUG -> return 11
            Type.Code.ROCK -> return 12
            Type.Code.GHOST -> return 13
            Type.Code.DRAGON -> return 14
            Type.Code.DARK -> return 15
            Type.Code.STEEL -> return 16
            Type.Code.FAIRY -> return 17
            else -> return -1
        }
    }

    fun code(type: Int): Code {
        when (type) {
            0 -> return Code.NORMAL
            1 -> return Code.FIRE
            2 -> return Code.WATER
            3 -> return Code.ELECTRIC
            4 -> return Code.GRASS
            5 -> return Code.ICE
            6 -> return Code.FIGHTING
            7 -> return Code.POISON
            8 -> return Code.GROUND
            9 -> return Code.FLYING
            10 -> return Code.PSYCHIC
            11 -> return Code.BUG
            12 -> return Code.ROCK
            13 -> return Code.GHOST
            14 -> return Code.DRAGON
            15 -> return Code.DARK
            16 -> return Code.STEEL
            17 -> return Code.FAIRY
            else -> return Code.UNKNOWN
        }
    }

    fun values(): Array<Code> {
        return Code.values()
    }

    fun calculateAffinity(attackType: Code, defenseType1: Code, defenseType2: Code): Double {
        if (attackType == Type.Code.UNKNOWN || (defenseType1 == Code.UNKNOWN && defenseType2 == Code.UNKNOWN)) return -1.0

        val attackNo = no(attackType)
        val type1 = Type.no(defenseType1)
        val type2 = Type.no(defenseType2)

        if (defenseType1 != Code.UNKNOWN && defenseType2 == Code.UNKNOWN) {
            return AFFINITY_TABLE[attackNo][type1].toDouble()
        } else if (defenseType1 == Code.UNKNOWN && defenseType2 != Code.UNKNOWN) {
            return AFFINITY_TABLE[attackNo][type2].toDouble()
        } else {
            return AFFINITY_TABLE[attackNo][type1].times(AFFINITY_TABLE[attackNo][type2]).toDouble()
        }
    }
}
