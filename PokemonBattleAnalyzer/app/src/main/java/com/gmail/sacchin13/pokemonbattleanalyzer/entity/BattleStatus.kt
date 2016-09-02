package com.gmail.sacchin13.pokemonbattleanalyzer.entity

object BattleStatus {

    enum class Code {
        WIN, DEFEAT, REVERSE, OWN_HEAD, DRAW,
        MS, NS, SQ, MQ, MQS,
        UNKNOWN,
        DEPEND_EV, IMPOSSIBLE, POSSIBLE
    }

    fun convertNameToTypeCode(statusName: String): Code {
        return if ("先勝".equals(statusName)) Code.WIN
        else if ("後負".equals(statusName)) Code.DEFEAT
         else if ("後勝".equals(statusName)) Code.REVERSE
         else if ("先負".equals(statusName)) Code.OWN_HEAD
         else if ("引分".equals(statusName)) Code.DRAW
        else if ("最遅".equals(statusName)) Code.MS
        else if ("無振".equals(statusName)) Code.NS
        else if ("準速".equals(statusName)) Code.SQ
        else if ("最速".equals(statusName)) Code.MQ
        else if ("最ス".equals(statusName)) Code.MQS
         else Code.UNKNOWN
    }

    fun name(status: Code): String {
        when (status) {
            Code.WIN -> return "先勝"
            Code.DEFEAT -> return "後負"
            Code.REVERSE -> return "後勝"
            Code.OWN_HEAD -> return "先負"
            Code.DRAW -> return "引分"
            Code.MS -> return "最遅"
            Code.NS -> return "無振"
            Code.SQ -> return "準速"
            Code.MQ -> return "最速"
            Code.MQS -> return "最ス"
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
            Code.MS -> return 5
            Code.NS -> return 6
            Code.SQ -> return 7
            Code.MQ -> return 8
            Code.MQS -> return 9
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
            5 -> return Code.MS
            6 -> return Code.NS
            7 -> return Code.SQ
            8 -> return Code.MQ
            9 -> return Code.MQS
            else -> return Code.UNKNOWN
        }
    }

    fun values(): Array<Code> {
        return Code.values()
    }
}
