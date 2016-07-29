package com.gmail.sacchin13.pokemonbattleanalyzer.entity

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl.RankingResponse
import kotlin.properties.Delegates

class PokemonForBattle(
        var side: Int = 0,
        var status: Int = UNKNOWN,
        var item: String = "unknown",
        var characteristic: String = "unknown",
        var ability: String = "unknown",
        var skill: Skill = Skill(),
        var hpEffortValue: Int = UNKNOWN,
        var hpRatio: Int = 100,
        var attackEffortValue: Int = UNKNOWN,
        var attackRank: Int = UNKNOWN,
        var defenseEffortValue: Int = UNKNOWN,
        var defenseRank: Int = UNKNOWN,
        var specialAttackEffortValue: Int = UNKNOWN,
        var specialAttackRank: Int = UNKNOWN,
        var specialDefenseEffortValue: Int = UNKNOWN,
        var specialDefenseRank: Int = UNKNOWN,
        var speedEffortValue: Int = UNKNOWN,
        var speedRank: Int = UNKNOWN,
        var criticalRank: Int = UNKNOWN,
        var individual: IndividualPBAPokemon = IndividualPBAPokemon()
) {

    var trend: RankingResponse by Delegates.notNull()

    companion object {
        const val UNKNOWN = -1

        fun create(side: Int, individual: IndividualPBAPokemon): PokemonForBattle {
            return PokemonForBattle(side, -1, "", "", "", Skill(), 0, 100, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, individual)
        }
    }

    fun determineSkillPower(defenseSide: PokemonForBattle): Int {
        if (skill.jname.equals("ダブルニードル") || skill.jname.equals("にどげり") || skill.jname.equals("ホネブーメラン") ||
                skill.jname.equals("ダブルチョップ") || skill.jname.equals("ギアソーサー") || skill.jname.equals("ダブルアタック")) {
            return skill.power * 2
        }

        if (skill.jname.equals("おうふくビンタ") || skill.jname.equals("れんぞくパンチ") || skill.jname.equals("みだれづき") ||
                skill.jname.equals("とげキャノン") || skill.jname.equals("たまなげ") || skill.jname.equals("みだれひっかき") ||
                skill.jname.equals("つっぱり") || skill.jname.equals("タネマシンガン") || skill.jname.equals("ロックブラスト") ||
                skill.jname.equals("ボーンラッシュ") || skill.jname.equals("つららばり") || skill.jname.equals("みずしゅりけん") ||
                skill.jname.equals("ミサイルばり") || skill.jname.equals("スイープビンタ")) {
            if (ability.equals("スキルリンク")) return skill.power * 5 else return skill.power * 3
        }

        if (skill.jname.equals("しおみず")) {
            if (defenseSide.hpRatio < 50) return skill.power * 2 else skill.power
        }
        if (skill.jname.equals("おしおき")) {
            var sum = 0
            sum += if (0 < defenseSide.attackRank) defenseSide.attackRank else 0
            sum += if (0 < defenseSide.defenseRank) defenseSide.defenseRank else 0
            sum += if (0 < defenseSide.specialAttackRank) defenseSide.specialAttackRank else 0
            sum += if (0 < defenseSide.specialDefenseRank) defenseSide.specialDefenseRank else 0
            sum += if (0 < defenseSide.speedRank) defenseSide.speedRank else 0
            sum += if (0 < defenseSide.criticalRank) defenseSide.criticalRank else 0

            when (sum) {
                0 -> return 60
                1 -> return 80
                2 -> return 100
                3 -> return 120
                4 -> return 140
                5 -> return 160
                6 -> return 180
            }
            if (7 < sum) return 200
        }
        if (skill.jname.equals("けたぐり") || skill.jname.equals("くさむすび")) {
            val tmp = defenseSide.individual.master.weight
            when {
                (tmp < 10.0) -> return 20
                (10.1 < tmp && tmp < 25.0) -> return 40
                (25.1 < tmp && tmp < 50.0) -> return 60
                (50.1 < tmp && tmp < 100.0) -> return 80
                (100.1 < tmp && tmp < 200.0) -> return 100
                (200.1 < tmp) -> return 120
            }
        }

        if (skill.jname.equals("ジャイロボール")) {
            val tmp = (25 * defenseSide.calcSpeedValue() / calcSpeedValue()).toInt() + 1
            if (150 < tmp) return 150 else return tmp
        }

        if (skill.jname.equals("しぼりとる") || skill.jname.equals("にぎりつぶす")) {
            val tmp = (120 * defenseSide.hpRatio / 100) + 1
            if (120 < tmp) return 120 else return tmp
        }

        if (skill.jname.equals("じたばた") || skill.jname.equals("きしかいせい")) {
            val tmp = hpRatio.div(100f).times(48f)
            when {
                (0f <= tmp && tmp < 1f) -> return 200
                (1f <= tmp && tmp < 4f) -> return 150
                (4f <= tmp && tmp < 9f) -> return 100
                (9f <= tmp && tmp < 16f) -> return 80
                (16f <= tmp && tmp < 31f) -> return 40
                (31 <= tmp && tmp <= 48f) -> return 20
            }
        }

        return skill.power
    }

    fun calcAttackValue(isCritical: Boolean): Int {
        var result = individual.master.getAttackValue(31, attackEffortValue)
        result = result.times(Characteristic.correction(characteristic, "A")).toInt()

        //ToDo: スロースタート
        if (ability.equals("ヨガパワー") || ability.equals("ちからもち")) {
            result = result.times(2)
        }
        if (item.equals("こだわりハチマキ")) {
            result = result.times(1.5).toInt()
        }
        if (item.equals("ふといホネ")) {
            result = result.times(2).toInt()
        }
        if (status != StatusAilment.no(StatusAilment.Code.UNKNOWN)) {
            if (ability.equals("こんじょう")) {
                result = result.times(1.5).toInt()
            } else if (status == StatusAilment.no(StatusAilment.Code.BURN)) {
                result = result.times(0.5).toInt()
            }
        }

        if (ability.equals("はりきり")) {
            result = result.times(1.5).toInt()
        }
        //ToDo: サンパワー、フラワーギフト、プラスマイナス対応

        when (attackRank) {
            -6 -> return if (isCritical) result.times(3).div(3).toInt() else result.times(3).div(9).toInt()
            -5 -> return if (isCritical) result.times(3).div(3).toInt() else result.times(3).div(8).toInt()
            -4 -> return if (isCritical) result.times(3).div(3).toInt() else result.times(3).div(7).toInt()
            -3 -> return if (isCritical) result.times(3).div(3).toInt() else result.times(3).div(6).toInt()
            -2 -> return if (isCritical) result.times(3).div(3).toInt() else result.times(3).div(5).toInt()
            -1 -> return if (isCritical) result.times(3).div(3).toInt() else result.times(3).div(4).toInt()
            1 -> return result.times(4).div(3).toInt()
            2 -> return result.times(5).div(3).toInt()
            3 -> return result.times(6).div(3).toInt()
            4 -> return result.times(7).div(3).toInt()
            5 -> return result.times(8).div(3).toInt()
            6 -> return result.times(9).div(3).toInt()
            else -> return result
        }
    }

    fun calcDefenseValue(isCritical: Boolean): Int {
        var result = individual.master.getDefenseValue(31, defenseEffortValue)
        result = result.times(Characteristic.correction(characteristic, "B")).toInt()

        if (ability.equals("ふしぎなうろこ") && status != 0) {
            result = result.times(1.5).toInt()
        }

        if (ability.equals("メタルパウダー")) {
            result = result.times(1.5).toInt()
        }

        when (defenseRank) {
            -6 -> return result.times(3).div(9).toInt()
            -5 -> return result.times(3).div(8).toInt()
            -4 -> return result.times(3).div(7).toInt()
            -3 -> return result.times(3).div(6).toInt()
            -2 -> return result.times(3).div(5).toInt()
            -1 -> return result.times(3).div(4).toInt()
            1 -> return if (isCritical) result.times(3).div(3).toInt() else result.times(4).div(3).toInt()
            2 -> return if (isCritical) result.times(3).div(3).toInt() else result.times(5).div(3).toInt()
            3 -> return if (isCritical) result.times(3).div(3).toInt() else result.times(6).div(3).toInt()
            4 -> return if (isCritical) result.times(3).div(3).toInt() else result.times(7).div(3).toInt()
            5 -> return if (isCritical) result.times(3).div(3).toInt() else result.times(8).div(3).toInt()
            6 -> return if (isCritical) result.times(3).div(3).toInt() else result.times(9).div(3).toInt()
            else -> return result
        }
    }

    fun calcSpecialAttackValue(isCritical: Boolean): Int {
        var result = individual.master.getSpecialAttackValue(31, specialAttackEffortValue)
        result = result.times(Characteristic.correction(characteristic, "C")).toInt()

        if (item.equals("こだわりメガネ")) {
            result = result.times(1.5).toInt()
        }
        if (item.equals("しんかいのキバ")) {
            result = result.times(2).toInt()
        }
        if (ability.equals("こころのしずく")) {
            result = result.times(1.5).toInt()
        }

        when (specialAttackRank) {
            -6 -> return if (isCritical) result else result.times(3).div(9).toInt()
            -5 -> return if (isCritical) result else result.times(3).div(8).toInt()
            -4 -> return if (isCritical) result else result.times(3).div(7).toInt()
            -3 -> return if (isCritical) result else result.times(3).div(6).toInt()
            -2 -> return if (isCritical) result else result.times(3).div(5).toInt()
            -1 -> return if (isCritical) result else result.times(3).div(4).toInt()
            1 -> return result.times(4).div(3).toInt()
            2 -> return result.times(5).div(3).toInt()
            3 -> return result.times(6).div(3).toInt()
            4 -> return result.times(7).div(3).toInt()
            5 -> return result.times(8).div(3).toInt()
            6 -> return result.times(9).div(3).toInt()
            else -> return result
        }
    }

    fun calcSpecialDefenseValue(isCritical: Boolean): Int {
        var result = individual.master.getSpecialDefenseValue(31, specialDefenseEffortValue)
        result = result.times(Characteristic.correction(characteristic, "D")).toInt()

        if (item.equals("しんかいのウロコ")) {
            result = result.times(2).toInt()
        }
        if (ability.equals("こころのしずく")) {
            result = result.times(1.5).toInt()
        }
        //ToDo: すなあらし対応

        when (specialDefenseRank) {
            -6 -> return result.times(3).div(9).toInt()
            -5 -> return result.times(3).div(8).toInt()
            -4 -> return result.times(3).div(7).toInt()
            -3 -> return result.times(3).div(6).toInt()
            -2 -> return result.times(3).div(5).toInt()
            -1 -> return result.times(3).div(4).toInt()
            1 -> return if (isCritical) result.times(3).div(3).toInt() else result.times(4).div(3).toInt()
            2 -> return if (isCritical) result.times(3).div(3).toInt() else result.times(5).div(3).toInt()
            3 -> return if (isCritical) result.times(3).div(3).toInt() else result.times(6).div(3).toInt()
            4 -> return if (isCritical) result.times(3).div(3).toInt() else result.times(7).div(3).toInt()
            5 -> return if (isCritical) result.times(3).div(3).toInt() else result.times(8).div(3).toInt()
            6 -> return if (isCritical) result.times(3).div(3).toInt() else result.times(9).div(3).toInt()
            else -> return result
        }
    }

    fun calcSpeedValue(): Int {
        var result = individual.master.getSpeedValue(31, speedEffortValue)
        result = result.times(Characteristic.correction(characteristic, "S")).toInt()

        if (item.equals("こだわりスカーフ")) {
            result = result.times(1.5).toInt()
        }
        if (ability.equals("メタルパウダー")) {
            result = result.times(1.5).toInt()
        }
        if (status != StatusAilment.no(StatusAilment.Code.UNKNOWN)) {
            if (ability.equals("ちどりあし")) {
                result = result.times(1.5).toInt()
            } else if (status == StatusAilment.no(StatusAilment.Code.PARALYSIS)) {
                result = result.times(0.25).toInt()
            }
        }

        when (speedRank) {
            -6 -> return result.times(3).div(9).toInt()
            -5 -> return result.times(3).div(8).toInt()
            -4 -> return result.times(3).div(7).toInt()
            -3 -> return result.times(3).div(6).toInt()
            -2 -> return result.times(3).div(5).toInt()
            -1 -> return result.times(3).div(4).toInt()
            1 -> return result.times(4).div(3).toInt()
            2 -> return result.times(5).div(3).toInt()
            3 -> return result.times(6).div(3).toInt()
            4 -> return result.times(7).div(3).toInt()
            5 -> return result.times(8).div(3).toInt()
            6 -> return result.times(9).div(3).toInt()
            else -> return result
        }
    }

    fun calcCriticalRate(): Float {
        if (skill.equals("やまあらし") || skill.equals("こおりのいぶき")) {
            return 1f
        }

        var rank = criticalRank
        if (item.equals("ピントレンズ") || item.equals("するどいツメ")) {
            rank += 1
        }
        if (item.equals("ラッキ-パンチ") || item.equals("ながねぎ")) {
            rank += 1
        }
        if (ability.equals("きょううん")) {
            rank += 1
        }
        if (skill.equals("からてチョップ") || skill.equals("きりさく") || skill.equals("クラブハンマー") || skill.equals("はっぱカッター") ||
                skill.equals("エアロブラスト") || skill.equals("クロスチョップ") || skill.equals("エアカッター") || skill.equals("ポイズンテール") ||
                skill.equals("ブレイズキック") || skill.equals("リーフブレード") || skill.equals("あくうせつだん") || skill.equals("こうげきしれい") ||
                skill.equals("クロスポイズン") || skill.equals("サイコカッター") || skill.equals("シャドークロー") || skill.equals("ストーンエッジ") ||
                skill.equals("つじぎり") || skill.equals("ドリルライナー") || skill.equals("ダークブラスト")) {
            rank += 1
        }

        val baseRate = arrayOf(1 / 16f, 1 / 8f, 1 / 2f)
        when {
            (0 <= rank && rank < 3) -> return baseRate[rank]
            (3 <= rank) -> return 1f
        }
        return baseRate[0]
    }

    fun calcTypeBonus(): Float {
        if (skill.type == individual.master.type1 || skill.type == individual.master.type2) {
            if (ability.equals("てきおうりょく")) return 2f else return 1.5f
        }
        return 1f
    }
}