package com.gmail.sacchin13.pokemonbattleanalyzer.entity

object StatusAilment {

    enum class Code {
        BURN, FREEZE, PARALYSIS, POISON, BADPOISON, SLEEP, FAINTING , UNKNOWN
    }

    fun convertNameToTypeCode(statusName: String): Code {
        return if ("やけど".equals(statusName)) Code.BURN
        else if ("こおり".equals(statusName)) Code.FREEZE
         else if ("まひ".equals(statusName)) Code.PARALYSIS
         else if ("どく".equals(statusName)) Code.POISON
         else if ("もうどく".equals(statusName)) Code.BADPOISON
         else if ("ねむり".equals(statusName)) Code.SLEEP
        else if ("ひんし".equals(statusName)) Code.FAINTING
         else Code.UNKNOWN
    }

    fun name(status: Code): String {
        when (status) {
            Code.BURN -> return "やけど"
            Code.FREEZE -> return "こおり"
            Code.PARALYSIS -> return "まひ"
            Code.POISON -> return "どく"
            Code.BADPOISON -> return "もうどく"
            Code.SLEEP -> return "ねむり"
            Code.FAINTING -> return "ひんし"
            else -> return "UNKNOWN"
        }
    }

    fun name(code: Int): String {
        when (code) {
            0 -> return "やけど"
            1 -> return "こおり"
            2 -> return "まひ"
            3 -> return "どく"
            4 -> return "もうどく"
            5 -> return "ねむり"
            6 -> return "ひんし"
            else -> return "UNKNOWN"
        }
    }

    fun no(status: Code): Int {
        when (status) {
            Code.BURN -> return 0
            Code.FREEZE -> return 1
            Code.PARALYSIS -> return 2
            Code.POISON -> return 3
            Code.BADPOISON -> return 4
            Code.SLEEP -> return 5
            Code.FAINTING -> return 6
            else -> return -1
        }
    }

    fun code(type: Int): Code {
        when (type) {
            0 -> return Code.BURN
            1 -> return Code.FREEZE
            2 -> return Code.PARALYSIS
            3 -> return Code.POISON
            4 -> return Code.BADPOISON
            5 -> return Code.SLEEP
            6 -> return Code.FAINTING
            else -> return Code.UNKNOWN
        }
    }

    fun values(): Array<Code> {
        return Code.values()
    }
}
