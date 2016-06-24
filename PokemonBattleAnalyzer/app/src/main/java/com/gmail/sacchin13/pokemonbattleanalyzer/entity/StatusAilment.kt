package com.gmail.sacchin13.pokemonbattleanalyzer.entity

object StatusAilment {

    enum class StatusAilmentCode {
        BURN, FREEZE, PARALYSIS, POISON, BADPOISON, SLEEP, FAINTING , UNKNOWN
    }

    fun convertNameToTypeCode(statusName: String): StatusAilmentCode {
        return if ("やけど".equals(statusName)) StatusAilmentCode.BURN
        else if ("こおり".equals(statusName)) StatusAilmentCode.FREEZE
         else if ("まひ".equals(statusName)) StatusAilmentCode.PARALYSIS
         else if ("どく".equals(statusName)) StatusAilmentCode.POISON
         else if ("もうどく".equals(statusName)) StatusAilmentCode.BADPOISON
         else if ("ねむり".equals(statusName)) StatusAilmentCode.SLEEP
        else if ("ひんし".equals(statusName)) StatusAilmentCode.FAINTING
         else StatusAilmentCode.UNKNOWN
    }

    fun convertTypeCodeToName(status: StatusAilmentCode): String {
        when (status) {
            StatusAilmentCode.BURN -> return "やけど"
            StatusAilmentCode.FREEZE -> return "こおり"
            StatusAilmentCode.PARALYSIS -> return "まひ"
            StatusAilmentCode.POISON -> return "どく"
            StatusAilmentCode.BADPOISON -> return "もうどく"
            StatusAilmentCode.SLEEP -> return "ねむり"
            StatusAilmentCode.FAINTING -> return "ひんし"
            else -> return "UNKNOWN"
        }
    }

    fun convertTypeCodeToNo(status: StatusAilmentCode): Int {
        when (status) {
            StatusAilmentCode.BURN -> return 0
            StatusAilmentCode.FREEZE -> return 1
            StatusAilmentCode.PARALYSIS -> return 2
            StatusAilmentCode.POISON -> return 3
            StatusAilmentCode.BADPOISON -> return 4
            StatusAilmentCode.SLEEP -> return 5
            StatusAilmentCode.FAINTING -> return 6
            else -> return -1
        }
    }

    fun convertNoToTypeCode(type: Int): StatusAilmentCode {
        when (type) {
            0 -> return StatusAilmentCode.BURN
            1 -> return StatusAilmentCode.FREEZE
            2 -> return StatusAilmentCode.PARALYSIS
            3 -> return StatusAilmentCode.POISON
            4 -> return StatusAilmentCode.BADPOISON
            5 -> return StatusAilmentCode.SLEEP
            6 -> return StatusAilmentCode.FAINTING
            else -> return StatusAilmentCode.UNKNOWN
        }
    }

    fun values(): Array<StatusAilmentCode> {
        return StatusAilmentCode.values()
    }
}
