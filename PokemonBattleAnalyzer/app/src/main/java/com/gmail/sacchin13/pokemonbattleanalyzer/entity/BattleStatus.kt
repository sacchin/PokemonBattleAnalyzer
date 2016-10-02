package com.gmail.sacchin13.pokemonbattleanalyzer.entity

object BattleStatus {

    enum class Order {
        IV0EV0CHM, IV31EV0CHM, IV31EV0CH, IV31EV4CH,
        IV31EV0CHP, IV31EV4CHP, IV31EV252CH, IV31EV252CHP, UNKNOWN
    }

    enum class Code {
        WIN, DEFEAT, REVERSE, OWN_HEAD, DRAW,
        MS, NS, SQ, MQ, MQS,
        UNKNOWN,
        DEPEND_EV, IMPOSSIBLE, POSSIBLE
    }

    fun name(code: Order): String {
        when (code) {
            Order.IV0EV0CHM -> return "最遅"
            Order.IV31EV0CHM -> return "無振負補正"
            Order.IV31EV0CH -> return "無振無補正"
            Order.IV31EV4CH -> return "4振無補正"
            Order.IV31EV0CHP -> return "無振正補正"
            Order.IV31EV4CHP -> return "4振負補正"
            Order.IV31EV252CH -> return "準速"
            Order.IV31EV252CHP -> return "最速"
            else -> return "UNKNOWN"
        }
    }

    fun name(code: Int): String {
        when (code) {
            0 -> return "最遅"
            1 -> return "無振負補正"
            2 -> return "無振無補正"
            3 -> return "4振無補正"
            4 -> return "無振正補正"
            5 -> return "4振正補正"
            6 -> return "準速"
            7 -> return "最速"
            else -> return "UNKNOWN"
        }
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

    fun no(code: Order): Int {
        when (code) {
            Order.IV0EV0CHM -> return 0
            Order.IV31EV0CHM -> return 1
            Order.IV31EV0CH -> return 2
            Order.IV31EV4CH -> return 3
            Order.IV31EV0CHP -> return 4
            Order.IV31EV4CHP -> return 5
            Order.IV31EV252CH -> return 6
            Order.IV31EV252CHP -> return 7
            else -> return -1
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
