package com.gmail.sacchin13.pokemonbattleanalyzer.entity

object Type {

    enum class TypeCode {
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

    fun convertNameToTypeCode(typeName: String): TypeCode? {
        return if ("ノーマル".equals(typeName)) TypeCode.NORMAL
        else if ("ほのお".equals(typeName)) TypeCode.FIRE
         else if ("みず".equals(typeName)) TypeCode.WATER
         else if ("でんき".equals(typeName)) TypeCode.ELECTRIC
         else if ("くさ".equals(typeName)) TypeCode.GRASS
         else if ("こおり".equals(typeName)) TypeCode.ICE
         else if ("かくとう".equals(typeName)) TypeCode.FIGHTING
         else if ("どく".equals(typeName)) TypeCode.POISON
         else if ("じめん".equals(typeName)) TypeCode.GROUND
         else if ("ひこう".equals(typeName)) TypeCode.FLYING
         else if ("エスパー".equals(typeName)) TypeCode.PSYCHIC
         else if ("むし".equals(typeName)) TypeCode.BUG
         else if ("いわ".equals(typeName)) TypeCode.ROCK
         else if ("ゴースト".equals(typeName)) TypeCode.GHOST
         else if ("ドラゴン".equals(typeName)) TypeCode.DRAGON
         else if ("あく".equals(typeName)) TypeCode.DARK
         else if ("はがね".equals(typeName)) TypeCode.STEEL
         else if ("フェアリー".equals(typeName)) TypeCode.FAIRY
         else null
    }

    fun convertTypeCodeToName(type: TypeCode?): String {
        if (type == null) {
            return "エラー"
        }
        when (type) {
            Type.TypeCode.NORMAL -> return "ノーマル"
            Type.TypeCode.FIRE -> return "ほのお"
            Type.TypeCode.WATER -> return "みず"
            Type.TypeCode.ELECTRIC -> return "でんき"
            Type.TypeCode.GRASS -> return "くさ"
            Type.TypeCode.ICE -> return "こおり"
            Type.TypeCode.FIGHTING -> return "かくとう"
            Type.TypeCode.POISON -> return "どく"
            Type.TypeCode.GROUND -> return "じめん"
            Type.TypeCode.FLYING -> return "ひこう"
            Type.TypeCode.PSYCHIC -> return "エスパー"
            Type.TypeCode.BUG -> return "むし"
            Type.TypeCode.ROCK -> return "いわ"
            Type.TypeCode.GHOST -> return "ゴースト"
            Type.TypeCode.DRAGON -> return "ドラゴン"
            Type.TypeCode.DARK -> return "あく"
            Type.TypeCode.STEEL -> return "はがね"
            Type.TypeCode.FAIRY -> return "フェアリー"
            else -> return "UNKNOWN"
        }
    }

    fun convertTypeCodeToNo(type: TypeCode?): Int {
        if (type == null) {
            return -1
        }
        when (type) {
            Type.TypeCode.NORMAL -> return 0
            Type.TypeCode.FIRE -> return 1
            Type.TypeCode.WATER -> return 2
            Type.TypeCode.ELECTRIC -> return 3
            Type.TypeCode.GRASS -> return 4
            Type.TypeCode.ICE -> return 5
            Type.TypeCode.FIGHTING -> return 6
            Type.TypeCode.POISON -> return 7
            Type.TypeCode.GROUND -> return 8
            Type.TypeCode.FLYING -> return 9
            Type.TypeCode.PSYCHIC -> return 10
            Type.TypeCode.BUG -> return 11
            Type.TypeCode.ROCK -> return 12
            Type.TypeCode.GHOST -> return 13
            Type.TypeCode.DRAGON -> return 14
            Type.TypeCode.DARK -> return 15
            Type.TypeCode.STEEL -> return 16
            Type.TypeCode.FAIRY -> return 17
            else -> return -1
        }
    }

    fun convertNoToTypeCode(type: Int): TypeCode {
        when (type) {
            0 -> return TypeCode.NORMAL
            1 -> return TypeCode.FIRE
            2 -> return TypeCode.WATER
            3 -> return TypeCode.ELECTRIC
            4 -> return TypeCode.GRASS
            5 -> return TypeCode.ICE
            6 -> return TypeCode.FIGHTING
            7 -> return TypeCode.POISON
            8 -> return TypeCode.GROUND
            9 -> return TypeCode.FLYING
            10 -> return TypeCode.PSYCHIC
            11 -> return TypeCode.BUG
            12 -> return TypeCode.ROCK
            13 -> return TypeCode.GHOST
            14 -> return TypeCode.DRAGON
            15 -> return TypeCode.DARK
            16 -> return TypeCode.STEEL
            17 -> return TypeCode.FAIRY
            else -> return TypeCode.UNKNOWN
        }
    }

    fun values(): Array<TypeCode> {
        return TypeCode.values()
    }

    fun calcurateAffinity(attackType: TypeCode?, p: PokemonMasterData): Float {
        if (attackType == null) {
            return -1f
        }
        val attackNo = convertTypeCodeToNo(attackType)

        if (p.type1 == null && p.type2 == null) {
            return -1f
        } else if (p.type1 != null && p.type2 == null) {
            val type1No = p.type1
            return AFFINITY_TABLE[attackNo][type1No]
        } else if (p.type1 == null && p.type2 != null) {
            val type2No = p.type2
            return AFFINITY_TABLE[attackNo][type2No]
        } else {
            val type1No = p.type1
            val type2No = p.type2
            return AFFINITY_TABLE[attackNo][type1No] * AFFINITY_TABLE[attackNo][type2No]
        }
    }
}
