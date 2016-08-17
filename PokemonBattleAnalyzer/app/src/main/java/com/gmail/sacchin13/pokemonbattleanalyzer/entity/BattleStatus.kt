package com.gmail.sacchin13.pokemonbattleanalyzer.entity

object BattleStatus {

    enum class Code {
        WIN, DEFEAT, REVERSE, OWN_HEAD, DRAW, UNKNOWN
    }

    fun convertNameToTypeCode(statusName: String): Code {
        return if ("先勝".equals(statusName)) Code.WIN
        else if ("後負".equals(statusName)) Code.DEFEAT
         else if ("後勝".equals(statusName)) Code.REVERSE
         else if ("先負".equals(statusName)) Code.OWN_HEAD
         else if ("引分".equals(statusName)) Code.DRAW
         else Code.UNKNOWN
    }

    fun name(status: Code): String {
        when (status) {
            Code.WIN -> return "先勝"
            Code.DEFEAT -> return "後負"
            Code.REVERSE -> return "後勝"
            Code.OWN_HEAD -> return "先負"
            Code.DRAW -> return "引分"
            else -> return "UNKNOWN"
        }
    }

    fun no(status: Code): Int {
        when (status) {
            Code.WIN -> return 0
            Code.DEFEAT -> return 1
            Code.REVERSE -> return 2
            Code.OWN_HEAD -> return 3
            Code.DRAW -> return 4
            else -> return -1
        }
    }

    fun code(type: Int): Code {
        when (type) {
            0 -> return Code.WIN
            1 -> return Code.DEFEAT
            2 -> return Code.REVERSE
            3 -> return Code.OWN_HEAD
            4 -> return Code.DRAW
            else -> return Code.UNKNOWN
        }
    }

    fun values(): Array<Code> {
        return Code.values()
    }
}
