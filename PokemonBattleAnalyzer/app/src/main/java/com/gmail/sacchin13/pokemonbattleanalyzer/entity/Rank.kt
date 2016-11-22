package com.gmail.sacchin13.pokemonbattleanalyzer.entity

object Rank {
    //ABCDS -> Attack - Speed
    //P -> Hit Probability
    //V -> Avoid
    //T -> Critical
    enum class Code {
        A, A2, A12, mA, mA2, B, B2, B3, mB, mB2, C, C2, C3, mC, mC2, D, D2, mD, mD2, S, S2, mS, mS2, mP, V, V2, mV, mV2,
        T, AB, mAmB, AC, mAmC, mA2mC2, AD, AS, AS2, AP, mAmCmS, BC, BD, mBmD, mBmDmS, CD, CS, ABP, ABD,
        ABmS, CDS, C2D2S2, ABCDS, A2mBC2mDS2, RESET, P, BG, T2, A2B2C2D2S2, UNKNOWN
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
            Code.mP to arrayOf(0, 0, 0, 0, 0, -1, 0, 0),
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
            Code.RESET to arrayOf(0, 0, 0, 0, 0, 0, 0, 0),
            Code.P to arrayOf(0, 0, 0, 0, 0, 1, 0, 0),
            Code.BG to arrayOf(0, 1, 0, 0, 0, 0, 0, 0),
            Code.T to arrayOf(0, 1, 0, 0, 0, 0, 0, 1),
            Code.T2 to arrayOf(0, 1, 0, 0, 0, 0, 0, 2),
            Code.A2B2C2D2S2 to arrayOf(2, 2, 2, 2, 2, 0, 0, 0),
            Code.UNKNOWN to arrayOf(0, 0, 0, 0, 0, 0, 0, 0)
    )

    fun name(type: Code): String {
        when (type) {
            Code.A -> return "A↑"
            Code.A2 -> return "A2↑"
            Code.A12 -> return "A"
            Code.mA -> return "A↓"
            Code.mA2 -> return "A2↓"
            Code.B -> return "B↑"
            Code.B2 -> return "B2↑"
            Code.B3 -> return "B3↑"
            Code.mB -> return "B↓"
            Code.mB2 -> return "B2↓"
            Code.C -> return "C↑"
            Code.C2 -> return "C2↑"
            Code.C3 -> return "C3↑"
            Code.mC -> return "↓C"
            Code.mC2 -> return "C2↓"
            Code.D -> return "D↑"
            Code.D2 -> return "D2↑"
            Code.mD -> return "↓D"
            Code.mD2 -> return "D2↓"
            Code.S -> return "S↑"
            Code.S2 -> return "S2↑"
            Code.mS -> return "S↓"
            Code.mS2 -> return "S2↓"
            Code.mP -> return "P↓"
            Code.V -> return "V↑"
            Code.V2 -> return "V2↑"
            Code.mV -> return "V↓"
            Code.mV2 -> return "V2↓"
            Code.T -> return "T↑"
            Code.AB -> return "(AB)↑"
            Code.mAmB -> return "(AB)↓"
            Code.AC -> return "(AC)↑"
            Code.mAmC -> return "(AC)↓"
            Code.mA2mC2 -> return "(AC)2↓"
            Code.AD -> return "(AD)↑"
            Code.AS -> return "(AS)↑"
            Code.AS2 -> return "A↑S2↑"
            Code.AP -> return "(AP)↑"
            Code.mAmCmS -> return "(ACS)↓"
            Code.BC -> return "(BC)↑"
            Code.BD -> return "(BD)↑"
            Code.mBmD -> return "(BD)↓"
            Code.mBmDmS -> return "(BDS)↓"
            Code.CD -> return "(CD)↑"
            Code.CS -> return "(CS)↑"
            Code.ABP -> return "(ABP)↑"
            Code.ABD -> return "(ABD)↑"
            Code.ABmS -> return "(AB)↑S↓"
            Code.CDS -> return "(CDS)↑"
            Code.C2D2S2 -> return "(CDS)2↑"
            Code.ABCDS -> return "(ABCDS)↑"
            Code.A2mBC2mDS2 -> return "(ACS)2↑(BD)↓"
            Code.RESET -> return "RESET"
            Code.P -> return "P↑"
            Code.BG -> return "B↑"
            Code.T2 -> return "T2↑"
            Code.A2B2C2D2S2 -> return "(ABCDS)2↑"
            else -> return "UNKNOWN"
        }
    }

    fun no(type: Code): Int {
        when (type) {
            Code.A -> return 0
            Code.A2 -> return 1
            Code.A12 -> return 2
            Code.mA -> return 3
            Code.mA2 -> return 4
            Code.B -> return 5
            Code.B2 -> return 6
            Code.B3 -> return 7
            Code.mB -> return 8
            Code.mB2 -> return 9
            Code.C -> return 10
            Code.C2 -> return 11
            Code.C3 -> return 12
            Code.mC -> return 13
            Code.mC2 -> return 14
            Code.D -> return 15
            Code.D2 -> return 16
            Code.mD -> return 17
            Code.mD2 -> return 18
            Code.S -> return 19
            Code.S2 -> return 20
            Code.mS -> return 21
            Code.mS2 -> return 22
            Code.mP -> return 23
            Code.V -> return 24
            Code.V2 -> return 25
            Code.mV -> return 26
            Code.mV2 -> return 27
            Code.T -> return 28
            Code.AB -> return 29
            Code.mAmB -> return 30
            Code.AC -> return 31
            Code.mAmC -> return 32
            Code.mA2mC2 -> return 33
            Code.AD -> return 34
            Code.AS -> return 35
            Code.AS2 -> return 36
            Code.AP -> return 37
            Code.mAmCmS -> return 38
            Code.BC -> return 39
            Code.BD -> return 40
            Code.mBmD -> return 41
            Code.mBmDmS -> return 42
            Code.CD -> return 43
            Code.CS -> return 44
            Code.ABP -> return 45
            Code.ABD -> return 46
            Code.ABmS -> return 47
            Code.CDS -> return 48
            Code.C2D2S2 -> return 49
            Code.ABCDS -> return 50
            Code.A2mBC2mDS2 -> return 51
            Code.RESET -> return 52
            Code.P -> return 53
            Code.BG -> return 54
            Code.T2 -> return 55
            Code.A2B2C2D2S2 -> return 56
            else -> return -1
        }
    }

    fun code(type: Int): Code {
        when (type) {
            0 -> return Code.A
            1 -> return Code.A2
            2 -> return Code.A12
            3 -> return Code.mA
            4 -> return Code.mA2
            5 -> return Code.B
            6 -> return Code.B2
            7 -> return Code.B3
            8 -> return Code.mB
            9 -> return Code.mB2
            10 -> return Code.C
            11 -> return Code.C2
            12 -> return Code.C3
            13 -> return Code.mC
            14 -> return Code.mC2
            15 -> return Code.D
            16 -> return Code.D2
            17 -> return Code.mD
            18 -> return Code.mD2
            19 -> return Code.S
            20 -> return Code.S2
            21 -> return Code.mS
            22 -> return Code.mS2
            23 -> return Code.mP
            24 -> return Code.V
            25 -> return Code.V2
            26 -> return Code.mV
            27 -> return Code.mV2
            28 -> return Code.T
            29 -> return Code.AB
            30 -> return Code.mAmB
            31 -> return Code.AC
            32 -> return Code.mAmC
            33 -> return Code.mA2mC2
            34 -> return Code.AD
            35 -> return Code.AS
            36 -> return Code.AS2
            37 -> return Code.AP
            38 -> return Code.mAmCmS
            39 -> return Code.BC
            40 -> return Code.BD
            41 -> return Code.mBmD
            42 -> return Code.mBmDmS
            43 -> return Code.CD
            44 -> return Code.CS
            45 -> return Code.ABP
            46 -> return Code.ABD
            47 -> return Code.ABmS
            48 -> return Code.CDS
            49 -> return Code.C2D2S2
            50 -> return Code.ABCDS
            51 -> return Code.A2mBC2mDS2
            52 -> return Code.RESET
            53 -> return Code.P
            54 -> return Code.BG
            55 -> return Code.T2
            56 -> return Code.A2B2C2D2S2
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
