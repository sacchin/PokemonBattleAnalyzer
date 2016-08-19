package com.gmail.sacchin13.pokemonbattleanalyzer.entity

object Rank {
    //ABCDS -> Attack - Speed
    //P -> Hit Probability
    //V -> Avoid
    //T -> Critical
   enum class Code {
        A, A2, A12, mA, mA2, B, B2, B3, mB, mB2, C, C2, C3, mC, mC2, D, D2, mD, mD2, S, S2, mS, mS2, mP, V, V2, mV, mV2,
        T, AB, mAmB, AC, mAmC, mA2mC2, AD, AS, AS2, AP, mAmCmS, BC, BD, mBmD, mBmDmS, CD, CS, ABP, ABD,
        ABmS, CDS, C2D2S2, ABCDS, A2mBC2mDS2, UNKNOWN
    }

    var RANK_TABLE = mapOf<Code, Array<Int>>(
                Code.A to arrayOf(1, 0, 0, 0, 0, 0, 0, 0),
                Code.A2 to arrayOf(2, 0, 0, 0, 0, 0, 0, 0),
                Code.A12 to arrayOf(12, 0, 0, 0, 0, 0, 0, 0),
                Code.mA to arrayOf(-1, 0, 0, 0, 0, 0, 0, 0),
                Code.mA2 to arrayOf(-2, 0, 0, 0, 0, 0, 0, 0),
                Code.B to arrayOf(0, 1, 0, 0, 0, 0, 0, 0),
                Code.B2 to arrayOf(0, 2, 0, 0, 0, 0, 0, 0),
                Code.mB to arrayOf(0, -1, 0, 0, 0, 0, 0, 0),
                Code.mB2 to arrayOf(0, -2, 0, 0, 0, 0, 0, 0),
                Code.B3 to arrayOf(0, 3, 0, 0, 0, 0, 0, 0),
                Code.C to arrayOf(0, 0, 1, 0, 0, 0, 0, 0),
                Code.C2 to arrayOf(0, 0, 2, 0, 0, 0, 0, 0),
                Code.C3 to arrayOf(0, 0, 3, 0, 0, 0, 0, 0),
                Code.mC to arrayOf(0, 0, -1, 0, 0, 0, 0, 0),
                Code.mC2 to arrayOf(0, 0, -2, 0, 0, 0, 0, 0),
                Code.D to arrayOf(0, 0, 0, 1, 0, 0, 0, 0),
                Code.D2 to arrayOf(0, 0, 0, 2, 0, 0, 0, 0),
                Code.mD to arrayOf(0, 0, 0, -1, 0, 0, 0, 0),
                Code.mD2 to arrayOf(0, 0, 0, -2, 0, 0, 0, 0),
                Code.S to arrayOf(0, 0, 0, 0, 1, 0, 0, 0),
                Code.S2 to arrayOf(0, 0, 0, 0, 2, 0, 0, 0),
                Code.mS2 to arrayOf(0, 0, 0, 0, -2, 0, 0, 0),
                Code.mP to arrayOf(0, 0, 0, 0, 0, 0, -1, 0),
                Code.V to arrayOf(0, 0, 0, 0, 0, 0, 1, 0),
                Code.V2 to arrayOf(0, 0, 0, 0, 0, 0, 2, 0),
                Code.mV to arrayOf(0, 0, 0, 0, 0, 0, -1, 0),
                Code.mV2 to arrayOf(0, 0, 0, 0, 0, 0, -2, 0),
                Code.mS to arrayOf(0, 0, 0, 0, -1, 0, 0, 0),
                Code.AB to arrayOf(1, 1, 0, 0, 0, 0, 0, 0),
                Code.AC to arrayOf(1, 0, 1, 0, 0, 0, 0, 0),
                Code.AS to arrayOf(1, 0, 0, 0, 1, 0, 0, 0),
                Code.AS2 to arrayOf(1, 0, 0, 0, 2, 0, 0, 0),
                Code.AP to arrayOf(1, 0, 0, 0, 0, 1, 0, 0),
                Code.BD to arrayOf(0, 1, 0, 1, 0, 0, 0, 0),
                Code.ABP to arrayOf(1, 1, 0, 0, 0, 1, 0, 0),
                Code.CDS to arrayOf(0, 0, 1, 1, 1, 0, 0, 0),
                Code.C2D2S2 to arrayOf(0, 0, 2, 2, 2, 0, 0, 0),
                Code.ABmS to arrayOf(1, 1, 0, 0, -1, 0, 0, 0),
                Code.ABCDS to arrayOf(1, 1, 1, 1, 1, 0, 0, 0),
                Code.mAmB to arrayOf(-1, -1, 0, 0, 0, 0, 0, 0),
                Code.mAmC to arrayOf(-1, 0, -1, 0, 0, 0, 0, 0),
                Code.mA2mC2 to arrayOf(-2, 0, -2, 0, 0, 0, 0, 0),
                Code.mBmD to arrayOf(0, -1, 0, -1, 0, 0, 0, 0),
                Code.mAmCmS to arrayOf(-1, 0, -1, 0, -1, 0, 0, 0),
                Code.mBmDmS to arrayOf(0, -1, 0, -1, -1, 0, 0, 0),
                Code.A2mBC2mDS2 to arrayOf(2, -1, 2, -1, 2, 0, 0, 0),
                Code.UNKNOWN to arrayOf(0, 0, 0, 0, 0, 0, 0, 0)
        )

//    var AFFINITY_TABLE = mapOf(
//            Code.A -> intArrayOf(1, 1))
//
//
//    fun code(typeName: String): Code {
//        return if ("ノーマル".equals(typeName)) Code.NORMAL
//        else if ("ほのお".equals(typeName)) Code.FIRE
//        else if ("みず".equals(typeName)) Code.WATER
//        else if ("でんき".equals(typeName)) Code.ELECTRIC
//        else if ("くさ".equals(typeName)) Code.GRASS
//        else if ("こおり".equals(typeName)) Code.ICE
//        else if ("かくとう".equals(typeName)) Code.FIGHTING
//        else if ("どく".equals(typeName)) Code.POISON
//        else if ("じめん".equals(typeName)) Code.GROUND
//        else if ("ひこう".equals(typeName)) Code.FLYING
//        else if ("エスパー".equals(typeName)) Code.PSYCHIC
//        else if ("むし".equals(typeName)) Code.BUG
//        else if ("いわ".equals(typeName)) Code.ROCK
//        else if ("ゴースト".equals(typeName)) Code.GHOST
//        else if ("ドラゴン".equals(typeName)) Code.DRAGON
//        else if ("あく".equals(typeName)) Code.DARK
//        else if ("はがね".equals(typeName)) Code.STEEL
//        else if ("フェアリー".equals(typeName)) Code.FAIRY
//        else Code.UNKNOWN
//    }
//
//    fun name(type: Code): String {
//        when (type) {
//            Type.Code.NORMAL -> return "ノーマル"
//            Type.Code.FIRE -> return "ほのお"
//            Type.Code.WATER -> return "みず"
//            Type.Code.ELECTRIC -> return "でんき"
//            Type.Code.GRASS -> return "くさ"
//            Type.Code.ICE -> return "こおり"
//            Type.Code.FIGHTING -> return "かくとう"
//            Type.Code.POISON -> return "どく"
//            Type.Code.GROUND -> return "じめん"
//            Type.Code.FLYING -> return "ひこう"
//            Type.Code.PSYCHIC -> return "エスパー"
//            Type.Code.BUG -> return "むし"
//            Type.Code.ROCK -> return "いわ"
//            Type.Code.GHOST -> return "ゴースト"
//            Type.Code.DRAGON -> return "ドラゴン"
//            Type.Code.DARK -> return "あく"
//            Type.Code.STEEL -> return "はがね"
//            Type.Code.FAIRY -> return "フェアリー"
//            else -> return "UNKNOWN"
//        }
//    }
//
    fun no(type: Code): Int {
        when (type) {
            Code.A -> return 0
            Code.B -> return 1
            Code.C -> return 2
            Code.D -> return 3
            Code.S -> return 4
            else -> return -1
        }
    }

    fun code(type: Int): Code {
        when (type) {
            0 -> return Code.A
            1 -> return Code.B
            2 -> return Code.C
            3 -> return Code.D
            4 -> return Code.S
            else -> return Code.UNKNOWN
        }
    }
//
//    fun values(): Array<Code> {
//        return Code.values()
//    }
//
//    fun calculateAffinity(attackType: Code, p: PokemonMasterData): Double {
//        if (attackType.equals(Type.Code.UNKNOWN)) return -1.0
//
//        val attackNo = no(attackType)
//        val type1 = Type.code(p.type1)
//        val type2 = Type.code(p.type2)
//
//        if (type1 != Code.UNKNOWN && type2 == Code.UNKNOWN) {
//            return AFFINITY_TABLE[attackNo][p.type1].toDouble()
//        } else if (type1 == Code.UNKNOWN && type2 != Code.UNKNOWN) {
//            return AFFINITY_TABLE[attackNo][p.type2].toDouble()
//        } else {
//            return AFFINITY_TABLE[attackNo][p.type1].times(AFFINITY_TABLE[attackNo][p.type2]).toDouble()
//        }
//    }
}
