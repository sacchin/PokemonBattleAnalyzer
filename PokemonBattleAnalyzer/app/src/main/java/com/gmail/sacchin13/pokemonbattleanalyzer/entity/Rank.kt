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

    enum class Value {
        P1, P2, P3, P4, P5, P6, N1, N2, N3, N4, N5, N6, DEFAULT, UNKNOWN
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
        return when (type) {
            Code.A -> "A↑"
            Code.A2 -> "A2↑"
            Code.A12 -> "A"
            Code.mA -> "A↓"
            Code.mA2 -> "A2↓"
            Code.B -> "B↑"
            Code.B2 -> "B2↑"
            Code.B3 -> "B3↑"
            Code.mB -> "B↓"
            Code.mB2 -> "B2↓"
            Code.C -> "C↑"
            Code.C2 -> "C2↑"
            Code.C3 -> "C3↑"
            Code.mC -> "↓C"
            Code.mC2 -> "C2↓"
            Code.D -> "D↑"
            Code.D2 -> "D2↑"
            Code.mD -> "↓D"
            Code.mD2 -> "D2↓"
            Code.S -> "S↑"
            Code.S2 -> "S2↑"
            Code.mS -> "S↓"
            Code.mS2 -> "S2↓"
            Code.mP -> "P↓"
            Code.V -> "V↑"
            Code.V2 -> "V2↑"
            Code.mV -> "V↓"
            Code.mV2 -> "V2↓"
            Code.T -> "T↑"
            Code.AB -> "(AB)↑"
            Code.mAmB -> "(AB)↓"
            Code.AC -> "(AC)↑"
            Code.mAmC -> "(AC)↓"
            Code.mA2mC2 -> "(AC)2↓"
            Code.AD -> "(AD)↑"
            Code.AS -> "(AS)↑"
            Code.AS2 -> "A↑S2↑"
            Code.AP -> "(AP)↑"
            Code.mAmCmS -> "(ACS)↓"
            Code.BC -> "(BC)↑"
            Code.BD -> "(BD)↑"
            Code.mBmD -> "(BD)↓"
            Code.mBmDmS -> "(BDS)↓"
            Code.CD -> "(CD)↑"
            Code.CS -> "(CS)↑"
            Code.ABP -> "(ABP)↑"
            Code.ABD -> "(ABD)↑"
            Code.ABmS -> "(AB)↑S↓"
            Code.CDS -> "(CDS)↑"
            Code.C2D2S2 -> "(CDS)2↑"
            Code.ABCDS -> "(ABCDS)↑"
            Code.A2mBC2mDS2 -> "(ACS)2↑(BD)↓"
            Code.RESET -> "RESET"
            Code.P -> "P↑"
            Code.BG -> "B↑"
            Code.T2 -> "T2↑"
            Code.A2B2C2D2S2 -> "(ABCDS)2↑"
            else -> "UNKNOWN"
        }
    }

    fun no(type: Code): Int {
        return when (type) {
            Code.A -> 0
            Code.A2 -> 1
            Code.A12 -> 2
            Code.mA -> 3
            Code.mA2 -> 4
            Code.B -> 5
            Code.B2 -> 6
            Code.B3 -> 7
            Code.mB -> 8
            Code.mB2 -> 9
            Code.C -> 10
            Code.C2 -> 11
            Code.C3 -> 12
            Code.mC -> 13
            Code.mC2 -> 14
            Code.D -> 15
            Code.D2 -> 16
            Code.mD -> 17
            Code.mD2 -> 18
            Code.S -> 19
            Code.S2 -> 20
            Code.mS -> 21
            Code.mS2 -> 22
            Code.mP -> 23
            Code.V -> 24
            Code.V2 -> 25
            Code.mV -> 26
            Code.mV2 -> 27
            Code.T -> 28
            Code.AB -> 29
            Code.mAmB -> 30
            Code.AC -> 31
            Code.mAmC -> 32
            Code.mA2mC2 -> 33
            Code.AD -> 34
            Code.AS -> 35
            Code.AS2 -> 36
            Code.AP -> 37
            Code.mAmCmS -> 38
            Code.BC -> 39
            Code.BD -> 40
            Code.mBmD -> 41
            Code.mBmDmS -> 42
            Code.CD -> 43
            Code.CS -> 44
            Code.ABP -> 45
            Code.ABD -> 46
            Code.ABmS -> 47
            Code.CDS -> 48
            Code.C2D2S2 -> 49
            Code.ABCDS -> 50
            Code.A2mBC2mDS2 -> 51
            Code.RESET -> 52
            Code.P -> 53
            Code.BG -> 54
            Code.T2 -> 55
            Code.A2B2C2D2S2 -> 56
            else -> -1
        }
    }

    fun code(type: Int): Code {
        return when (type) {
            0 -> Code.A
            1 -> Code.A2
            2 -> Code.A12
            3 -> Code.mA
            4 -> Code.mA2
            5 -> Code.B
            6 -> Code.B2
            7 -> Code.B3
            8 -> Code.mB
            9 -> Code.mB2
            10 -> Code.C
            11 -> Code.C2
            12 -> Code.C3
            13 -> Code.mC
            14 -> Code.mC2
            15 -> Code.D
            16 -> Code.D2
            17 -> Code.mD
            18 -> Code.mD2
            19 -> Code.S
            20 -> Code.S2
            21 -> Code.mS
            22 -> Code.mS2
            23 -> Code.mP
            24 -> Code.V
            25 -> Code.V2
            26 -> Code.mV
            27 -> Code.mV2
            28 -> Code.T
            29 -> Code.AB
            30 -> Code.mAmB
            31 -> Code.AC
            32 -> Code.mAmC
            33 -> Code.mA2mC2
            34 -> Code.AD
            35 -> Code.AS
            36 -> Code.AS2
            37 -> Code.AP
            38 -> Code.mAmCmS
            39 -> Code.BC
            40 -> Code.BD
            41 -> Code.mBmD
            42 -> Code.mBmDmS
            43 -> Code.CD
            44 -> Code.CS
            45 -> Code.ABP
            46 -> Code.ABD
            47 -> Code.ABmS
            48 -> Code.CDS
            49 -> Code.C2D2S2
            50 -> Code.ABCDS
            51 -> Code.A2mBC2mDS2
            52 -> Code.RESET
            53 -> Code.P
            54 -> Code.BG
            55 -> Code.T2
            56 -> Code.A2B2C2D2S2
            else -> Code.UNKNOWN
        }
    }

    fun no(value: Value): Int {
        return when (value) {
            Value.P1 -> 1
            Value.P2 -> 2
            Value.P3 -> 3
            Value.P4 -> 4
            Value.P5 -> 5
            Value.P6 -> 6
            Value.N1 -> -1
            Value.N2 -> -2
            Value.N3 -> -3
            Value.N4 -> -4
            Value.N5 -> -5
            Value.N6 -> -6
            else -> 0
        }
    }

    fun value(no: Int): Value {
        return when (no) {
            1 -> Value.P1
            2 -> Value.P2
            3 -> Value.P3
            4 -> Value.P4
            5 -> Value.P5
            6 -> Value.P6
            -1 -> Value.N1
            -2 -> Value.N2
            -3 -> Value.N3
            -4 -> Value.N4
            -5 -> Value.N5
            -6 -> Value.N6
            else -> Value.UNKNOWN
        }
    }

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
