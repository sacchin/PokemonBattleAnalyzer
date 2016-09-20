package com.gmail.sacchin13.pokemonbattleanalyzer.entity

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl.Info
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl.TrendForBattle
import kotlin.properties.Delegates

class PokemonForBattle(
        var side: Int,
        var status: Int,
        var item: String,
        var characteristic: String,
        var ability: String,
        var skill: Skill,
        var hpEffortValue: Int,
        var hpRatio: Int,
        var hpValue: Int,
        var attackEffortValue: Int,
        var attackRank: Int,
        var defenseEffortValue: Int,
        var defenseRank: Int,
        var specialAttackEffortValue: Int,
        var specialAttackRank: Int,
        var specialDefenseEffortValue: Int,
        var specialDefenseRank: Int,
        var speedEffortValue: Int,
        var speedRank: Int,
        var hitProbabilityRank: Int,
        var avoidanceRank: Int,
        var criticalRank: Int,
        var mega: Int,
        var individual: IndividualPBAPokemon = IndividualPBAPokemon()
) {

    val rank = arrayOf(-6, -5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5, 6)
    var trend: TrendForBattle by Delegates.notNull()
    var field: MutableList<BattleField.Field> = mutableListOf()
    var firstCheck = true

    companion object {
        const val UNKNOWN = -1

        fun create(side: Int, individual: IndividualPBAPokemon): PokemonForBattle {
            return PokemonForBattle(side, -1, "unknown", "unknown", "unknown", Skill(), 0, 100, 0,
                    0, 6, 0, 6, 0, 6, 0, 6, 0, 6,
                    0, 0, 0, IndividualPBAPokemon.NOT_MEGA, individual)
        }
    }

    fun abilityTrend(): List<Info> {
        if (ability.equals("unknown")) return trend.tokuseiInfo.filterNotNull()
        return listOf(Info(0, 1.0f, ability, 0))
    }

    fun characteristicTrend(): List<Info> {
        if (characteristic.equals("unknown")) return trend.seikakuInfo.filterNotNull()
        return listOf(Info(0, 1.0f, characteristic, 0))
    }

    fun itemTrend(): List<Info> {
        if (item.equals("unknown")) return trend.itemInfo.filterNotNull()
        return listOf(Info(0, 1.0f, item, 0))
    }

    fun hp(): Int {
        when (side) {
            0 -> return hpValue
            1 -> return individual.calcHp(hpEffortValue, mega).times(hpRatio).div(100.0).toInt()
        }
        return 0
    }

    fun add(item: BattleField.Field) {
        field.add(item)
    }

    fun getSkillPower(): Int {
        if (skill.jname.equals("マグニチュード") || skill.jname.equals("トリプルキック")) {
            return 71
        }

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

        if (skill.jname.equals("ふんか") && skill.jname.equals("しおふき") && (hpRatio < 30)) {
            val r = hpRatio.toFloat().div(100f).times(150).toInt()
            return if (r < 1) 1 else r
        }

        if (skill.jname.equals("アクロバット")) {
            return skill.power * 2
        }

        if (skill.jname.equals("アシストパワー")) {
            var sum = if (0 < rank[attackRank]) rank[attackRank] else 0
            sum += if (0 < rank[defenseRank]) rank[defenseRank] else 0
            sum += if (0 < rank[specialAttackRank]) rank[specialAttackRank] else 0
            sum += if (0 < rank[specialDefenseRank]) rank[specialDefenseRank] else 0
            sum += if (0 < rank[speedRank]) rank[speedRank] else 0
            sum += if (0 < criticalRank) criticalRank else 0
            return sum.times(20)
        }

        return skill.power
    }

    fun determineSkillPower(defenseSide: PokemonForBattle): Int {
        val skillPower = getSkillPower()
        //TODO れんぞくぎり,ころがる,エコーボイス,りんしょう,たつまき,かぜおこし,きりふだ,ちかい系,なげつける,ふくろだだき,ダメおし,しぜんのめぐみ,ウェザーボール、はきだす、おいうち対応

        if (skill.jname.equals("たたりめ")) {
            return if (defenseSide.status != StatusAilment.no(StatusAilment.Code.UNKNOWN)) skillPower * 2 else skillPower
        }
        if (skill.jname.equals("めざましビンタ")) {
            return if (defenseSide.status == StatusAilment.no(StatusAilment.Code.SLEEP)) skillPower * 2 else skillPower
        }
        if (skill.jname.equals("きつけ")) {
            return if (defenseSide.status == StatusAilment.no(StatusAilment.Code.PARALYSIS)) skillPower * 2 else skillPower
        }

        if (skill.jname.equals("おしおき")) {
            var sum = if (0 < rank[defenseSide.attackRank]) rank[defenseSide.attackRank] else 0
            sum += if (0 < rank[defenseSide.defenseRank]) rank[defenseSide.defenseRank] else 0
            sum += if (0 < rank[defenseSide.specialAttackRank]) rank[defenseSide.specialAttackRank] else 0
            sum += if (0 < rank[defenseSide.specialDefenseRank]) rank[defenseSide.specialDefenseRank] else 0
            sum += if (0 < rank[defenseSide.speedRank]) rank[defenseSide.speedRank] else 0
            sum += if (0 < rank[defenseSide.criticalRank]) rank[defenseSide.criticalRank] else 0

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
        if (skill.jname.equals("ヘビーボンバー")) {
            val tmp = (defenseSide.individual.master.weight / individual.master.weight).times(120)
            when {
                (tmp <= 24) -> return 120
                (24 < tmp && tmp <= 30) -> return 100
                (30 < tmp && tmp <= 40) -> return 80
                (40 < tmp && tmp <= 60) -> return 60
                (60 < tmp) -> return 40
            }
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
            return if (150 < tmp) 150 else tmp
        }

        if (skill.jname.equals("エレキボール")) {
            val tmp = calcSpeedValue().toDouble() / defenseSide.calcSpeedValue().toDouble()
            when {
                (tmp < 1) -> return 40
                (1 <= tmp && tmp < 2) -> return 60
                (2 <= tmp && tmp < 3) -> return 80
                (3 <= tmp && tmp < 4) -> return 120
                (4 <= tmp) -> return 150
            }
        }

        if (skill.jname.equals("しぼりとる") || skill.jname.equals("にぎりつぶす")) {
            val tmp = (120 * defenseSide.hpRatio / 100) + 1
            return if (120 < tmp) 120 else tmp
        }


        return skillPower
    }

    fun calcAttackValueCorrection(defenseSide: PokemonForBattle): Double {
        var initValue = 4096.0

        //TODO よわき, スロースタート: initValue = initValue.times(2048).div(4096).toInt()
        //TODO プラス,マイナス,フラワーギフト,もらいび: initValue = initValue.times(6144).div(4096).toInt()

        if (status != StatusAilment.no(StatusAilment.Code.UNKNOWN)) {
            if (ability.equals("こんじょう")) {
                initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
            } else if (status == StatusAilment.no(StatusAilment.Code.BURN)) {
                initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
            }
        }
        if (Type.code(skill.type) == Type.Code.FIRE && ability.equals("もうか") && (hpRatio < 30)) {
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }
        if (Type.code(skill.type) == Type.Code.WATER && ability.equals("げきりゅう") && (hpRatio < 30)) {
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }
        if (Type.code(skill.type) == Type.Code.GRASS && ability.equals("しんりょく") && (hpRatio < 30)) {
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }
        if (Type.code(skill.type) == Type.Code.BUG && ability.equals("むしのしらせ") && (hpRatio < 30)) {
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }

        if (ability.equals("ヨガパワー") || ability.equals("ちからもち")) {
            initValue = Math.round(initValue.times(8192.0).div(4096.0)).toDouble()
        }

        if (item.equals("こだわりハチマキ")) {
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }

        if ((Type.code(skill.type) == Type.Code.FIRE || Type.code(skill.type) == Type.Code.ICE) && defenseSide.ability.equals("あついしぼう")) {
            initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
        }

        if (item.equals("でんきだま")) {
            initValue = Math.round(initValue.times(8192.0).div(4096.0)).toDouble()
        }

        if (item.equals("ふといホネ")) {
            initValue = Math.round(initValue.times(8192.0).div(4096.0)).toDouble()
        }

        return initValue
    }

    fun calcSpecialAttackValueCorrection(defenseSide: PokemonForBattle): Double {
        var initValue = 4096.0

        //TODO よわき, スロースタート: initValue = initValue.times(2048).div(4096).toInt()
        //TODO もらいび,サンパワー,プラス,マイナス,フラワーギフト: initValue = initValue.times(6144).div(4096).toInt()

        if (Type.code(skill.type) == Type.Code.FIRE && ability.equals("もうか") && (hpRatio < 30)) {
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }
        if (Type.code(skill.type) == Type.Code.WATER && ability.equals("げきりゅう") && (hpRatio < 30)) {
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }
        if (Type.code(skill.type) == Type.Code.GRASS && ability.equals("しんりょく") && (hpRatio < 30)) {
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }
        if (Type.code(skill.type) == Type.Code.BUG && ability.equals("むしのしらせ") && (hpRatio < 30)) {
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }

        if (ability.equals("ヨガパワー") || ability.equals("ちからもち")) {
            initValue = Math.round(initValue.times(8192.0).div(4096.0)).toDouble()
        }

        if ((Type.code(skill.type) == Type.Code.FIRE || Type.code(skill.type) == Type.Code.ICE) && defenseSide.ability.equals("あついしぼう")) {
            initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
        }

        if (item.equals("しんかいのキバ")) {
            initValue = Math.round(initValue.times(8192.0).div(4096.0)).toDouble()
        }
        if (ability.equals("こころのしずく")) {
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }
        if (item.equals("こだわりメガネ")) {
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }

        return initValue
    }

    fun calcAttackValue(): Double {
        if (side == PartyInBattle.MY_SIDE) {
            return individual.attackValue.toDouble()
        }
        return Math.floor(individual.calcAttack(attackEffortValue, mega).times(Characteristic.correction(characteristic, "A")))
    }

    fun getAttackRankCorrection(isCritical: Boolean): Double {
        when (rank[attackRank]) {
            -6 -> return if (isCritical) 3.toDouble().div(3) else 3.toDouble().div(9)
            -5 -> return if (isCritical) 3.toDouble().div(3) else 3.toDouble().div(8)
            -4 -> return if (isCritical) 3.toDouble().div(3) else 3.toDouble().div(7)
            -3 -> return if (isCritical) 3.toDouble().div(3) else 3.toDouble().div(6)
            -2 -> return if (isCritical) 3.toDouble().div(3) else 3.toDouble().div(5)
            -1 -> return if (isCritical) 3.toDouble().div(3) else 3.toDouble().div(4)
            1 -> return 4.toDouble().div(3)
            2 -> return 5.toDouble().div(3)
            3 -> return 6.toDouble().div(3)
            4 -> return 7.toDouble().div(3)
            5 -> return 8.toDouble().div(3)
            6 -> return 9.toDouble().div(3)
            else -> return 1.toDouble()
        }
    }

    fun calcDefenseValue(): Double {
        if (side == PartyInBattle.MY_SIDE) {
            return individual.defenseValue.toDouble()
        }
        return Math.floor(individual.calcDefense(defenseEffortValue, mega).times(Characteristic.correction(characteristic, "B")))
    }

    fun calcDefenseValueCorrection(attackSide: PokemonForBattle): Double {
        var initValue = 4096.0

        //×6144 / 4096 四捨五入 くさのけがわ
        if (ability.equals("ふしぎなうろこ") && status != 0) {
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }
        if (ability.equals("ファーコート") && attackSide.skill.category == 0) {
            initValue = Math.round(initValue.times(8192.0).div(4096.0)).toDouble()
        }
        if (item.equals("しんかのきせき")) {
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }
        if (ability.equals("メタルパウダー")) {
            initValue = Math.round(initValue.times(8192.0).div(4096.0)).toDouble()
        }

        return initValue
    }

    fun getDefenseRankCorrection(isCritical: Boolean): Double {
        when (rank[defenseRank]) {
            -6 -> return 3.toDouble().div(9)
            -5 -> return 3.toDouble().div(8)
            -4 -> return 3.toDouble().div(7)
            -3 -> return 3.toDouble().div(6)
            -2 -> return 3.toDouble().div(5)
            -1 -> return 3.toDouble().div(4)
            1 -> return if (isCritical) 3.toDouble().div(3) else 4.toDouble().div(3)
            2 -> return if (isCritical) 3.toDouble().div(3) else 5.toDouble().div(3)
            3 -> return if (isCritical) 3.toDouble().div(3) else 6.toDouble().div(3)
            4 -> return if (isCritical) 3.toDouble().div(3) else 7.toDouble().div(3)
            5 -> return if (isCritical) 3.toDouble().div(3) else 8.toDouble().div(3)
            6 -> return if (isCritical) 3.toDouble().div(3) else 9.toDouble().div(3)
            else -> return 1.toDouble()
        }
    }

    fun calcSpecialAttackValue(): Double {
        if (side == PartyInBattle.MY_SIDE) {
            return individual.specialAttackValue.toDouble()
        }
        return Math.floor(individual.calcSpecialAttack(specialAttackEffortValue, mega).times(Characteristic.correction(characteristic, "C")))
    }

    fun getSpecialAttackRankCorrection(isCritical: Boolean): Double {
        when (rank[specialAttackRank]) {
            -6 -> return if (isCritical) 3.toDouble().div(3) else 3.toDouble().div(9)
            -5 -> return if (isCritical) 3.toDouble().div(3) else 3.toDouble().div(8)
            -4 -> return if (isCritical) 3.toDouble().div(3) else 3.toDouble().div(7)
            -3 -> return if (isCritical) 3.toDouble().div(3) else 3.toDouble().div(6)
            -2 -> return if (isCritical) 3.toDouble().div(3) else 3.toDouble().div(5)
            -1 -> return if (isCritical) 3.toDouble().div(3) else 3.toDouble().div(4)
            1 -> return 4.toDouble().div(3)
            2 -> return 5.toDouble().div(3)
            3 -> return 6.toDouble().div(3)
            4 -> return 7.toDouble().div(3)
            5 -> return 8.toDouble().div(3)
            6 -> return 9.toDouble().div(3)
            else -> return 1.toDouble()
        }
    }

    fun calcSpecialDefenseValue(): Double {
        if (side == PartyInBattle.MY_SIDE) {
            return individual.specialDefenseValue.toDouble()
        }
        return Math.floor(individual.calcSpecialDefense(specialDefenseEffortValue, mega).times(Characteristic.correction(characteristic, "D")))
    }

    fun calcSpecialDefenseValueCorrection(): Double {
        var initValue = 4096.0

        //×6144 / 4096 四捨五入 フラワーギフト
        if (item.equals("しんかのきせき")) {
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }
        if (item.equals("とつげきチョッキ")) {
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }
        if (ability.equals("こころのしずく")) {
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }

        if (item.equals("しんかいのウロコ")) {
            initValue = Math.round(initValue.times(8192.0).div(4096.0)).toDouble()
        }
        return initValue
    }

    fun getSpecialDefenseRankCorrection(isCritical: Boolean): Double {
        when (rank[specialDefenseRank]) {
            -6 -> return 3.toDouble().div(9)
            -5 -> return 3.toDouble().div(8)
            -4 -> return 3.toDouble().div(7)
            -3 -> return 3.toDouble().div(6)
            -2 -> return 3.toDouble().div(5)
            -1 -> return 3.toDouble().div(4)
            1 -> return if (isCritical) 3.toDouble().div(3) else 4.toDouble().div(3)
            2 -> return if (isCritical) 3.toDouble().div(3) else 5.toDouble().div(3)
            3 -> return if (isCritical) 3.toDouble().div(3) else 6.toDouble().div(3)
            4 -> return if (isCritical) 3.toDouble().div(3) else 7.toDouble().div(3)
            5 -> return if (isCritical) 3.toDouble().div(3) else 8.toDouble().div(3)
            6 -> return if (isCritical) 3.toDouble().div(3) else 9.toDouble().div(3)
            else -> return 1.toDouble()
        }
    }

    fun calcSpeedValue(): Double {
        var result = if (side == PartyInBattle.MY_SIDE) individual.speedValue.toDouble()
        else individual.calcSpeed(252, mega).toDouble()

        result = result.times(Characteristic.correction(characteristic, "S"))

        if (item.equals("こだわりスカーフ")) {
            result = result.times(1.5)
        }
        if (ability.equals("メタルパウダー")) {
            result = result.times(1.5)
        }
        if (status != StatusAilment.no(StatusAilment.Code.UNKNOWN)) {
            if (ability.equals("ちどりあし")) {
                result = result.times(1.5)
            } else if (status == StatusAilment.no(StatusAilment.Code.PARALYSIS)) {
                result = result.times(0.25)
            }
        }

        return Math.floor(result)
    }

    fun getSpeedRankCorrection(): Double {
        when (rank[speedRank]) {
            -6 -> return 3.toDouble().div(9)
            -5 -> return 3.toDouble().div(8)
            -4 -> return 3.toDouble().div(7)
            -3 -> return 3.toDouble().div(6)
            -2 -> return 3.toDouble().div(5)
            -1 -> return 3.toDouble().div(4)
            1 -> return 4.toDouble().div(3)
            2 -> return 5.toDouble().div(3)
            3 -> return 6.toDouble().div(3)
            4 -> return 7.toDouble().div(3)
            5 -> return 8.toDouble().div(3)
            6 -> return 9.toDouble().div(3)
            else -> return 1.toDouble()
        }
    }

    fun calcCriticalRate(): Double {
        if (skill.equals("やまあらし") || skill.equals("こおりのいぶき")) {
            return 1.0
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
            (0 <= rank && rank < 3) -> return baseRate[rank].toDouble()
            (3 <= rank) -> return 1.0
            else -> return baseRate[0].toDouble()
        }
    }

    fun typeBonus(): Double {
        if (skill.type == individual.master.type1 || skill.type == individual.master.type2) {
            if (ability.equals("てきおうりょく")) return 2.0 else return 1.5
        }
        return 1.0
    }

    fun doesntAffect(skill: Skill): Boolean {
        val skillType = Type.code(skill.type)

        if ("ふしぎなまもり".equals(ability)) {
            return if (skillType.equals(Type.Code.FIRE) ||
                    skillType.equals(Type.Code.GHOST) ||
                    skillType.equals(Type.Code.FLYING) ||
                    skillType.equals(Type.Code.ROCK) ||
                    skillType.equals(Type.Code.DARK)) false else true
        } else if ("もらいび".equals(ability)) {
            return if (skillType.equals(Type.Code.FIRE)) true else false
        } else if ("よびみず".equals(ability)) {
            return if (skillType.equals(Type.Code.WATER)) true else false
        } else if ("ちょすい".equals(ability)) {
            return if (skillType.equals(Type.Code.WATER)) true else false
        } else if ("そうしょく".equals(ability)) {
            return if (skillType.equals(Type.Code.GRASS)) true else false
        } else if ("ふゆう".equals(ability)) {
            return if (skillType.equals(Type.Code.GROUND)) true else false
        } else if ("ちくでん".equals(ability)) {
            return if (skillType.equals(Type.Code.ELECTRIC)) true else false
        } else if ("でんきエンジン".equals(ability)) {
            return if (skillType.equals(Type.Code.ELECTRIC)) true else false
        } else if ("かんそうはだ".equals(ability)) {
            return if (skillType.equals(Type.Code.WATER)) true else false
        } else if ("ぼうおん".equals(ability)) {
            return if (skill.jname.equals("いにしえのうた") ||
                    skill.jname.equals("いびき") ||
                    skill.jname.equals("いやなおと") ||
                    skill.jname.equals("うたう") ||
                    skill.jname.equals("エコーボイス") ||
                    skill.jname.equals("おしゃべり") ||
                    skill.jname.equals("おたけび") ||
                    skill.jname.equals("きんぞくおん") ||
                    skill.jname.equals("くさぶえ") ||
                    skill.jname.equals("さわぐ") ||
                    skill.jname.equals("すてゼリフ") ||
                    skill.jname.equals("ダークパニック") ||
                    skill.jname.equals("チャームボイス") ||
                    skill.jname.equals("ちょうおんぱ") ||
                    skill.jname.equals("ないしょばなし") ||
                    skill.jname.equals("なきごえ") ||
                    skill.jname.equals("ハイパーボイス") ||
                    skill.jname.equals("バークアウト") ||
                    skill.jname.equals("ばくおんぱ") ||
                    skill.jname.equals("ほえる") ||
                    skill.jname.equals("ほろびのうた") ||
                    skill.jname.equals("むしのさざめき") ||
                    skill.jname.equals("りんしょう")) true else false
        } else if ("ぼうじん".equals(ability)) {
            return if (skill.jname.equals("ねむりごな") ||
                    skill.jname.equals("しびれごな") ||
                    skill.jname.equals("どくのこな") ||
                    skill.jname.equals("キノコのほうし") ||
                    skill.jname.equals("わたほうし") ||
                    skill.jname.equals("いかりのこな") ||
                    skill.jname.equals("ふんじん")) true else false
        } else if ("ぼうだん".equals(ability)) {
            return if (skill.jname.equals("アイスボール") ||
                    skill.jname.equals("アシッドボム") ||
                    skill.jname.equals("ウェザーボール") ||
                    skill.jname.equals("エナジーボール") ||
                    skill.jname.equals("エレキボール") ||
                    skill.jname.equals("オクタンほう") ||
                    skill.jname.equals("かえんだん") ||
                    skill.jname.equals("がんせきほう") ||
                    skill.jname.equals("きあいだま") ||
                    skill.jname.equals("ジャイロボール") ||
                    skill.jname.equals("シャドーボール") ||
                    skill.jname.equals("タネマシンガン") ||
                    skill.jname.equals("タネばくだん") ||
                    skill.jname.equals("タマゴばくだん") ||
                    skill.jname.equals("たまなげ") ||
                    skill.jname.equals("でんじほう") ||
                    skill.jname.equals("どろばくだん") ||
                    skill.jname.equals("はどうだん") ||
                    skill.jname.equals("ヘドロばくだん") ||
                    skill.jname.equals("マグネットボム") ||
                    skill.jname.equals("ミストボール")) true else false
        } else {
            return true
        }
    }

    fun skillAffects(): MutableMap<Array<Int>, Double> {
        val result = mutableMapOf<Array<Int>, Double>()
        if (skill.aliment.equals(StatusAilment.no(StatusAilment.Code.UNKNOWN))) {
            if (skill.myRankUp.equals(Rank.no(Rank.Code.UNKNOWN))) {
                if (skill.oppoRankUp.equals(Rank.no(Rank.Code.UNKNOWN))) {
                    result[arrayOf(StatusAilment.no(StatusAilment.Code.UNKNOWN), Rank.no(Rank.Code.UNKNOWN), Rank.no(Rank.Code.UNKNOWN))] = 1.0
                } else {
                    result[arrayOf(StatusAilment.no(StatusAilment.Code.UNKNOWN), Rank.no(Rank.Code.UNKNOWN), skill.oppoRankUp)] = skill.oppoRankUpRate
                    result[arrayOf(StatusAilment.no(StatusAilment.Code.UNKNOWN), Rank.no(Rank.Code.UNKNOWN), Rank.no(Rank.Code.UNKNOWN))] = 1.0.minus(skill.oppoRankUpRate)
                }
            } else {
                if (skill.oppoRankUp.equals(Rank.no(Rank.Code.UNKNOWN))) {
                    result[arrayOf(StatusAilment.no(StatusAilment.Code.UNKNOWN), skill.myRankUp, Rank.no(Rank.Code.UNKNOWN))] = skill.myRankUpRate
                    result[arrayOf(StatusAilment.no(StatusAilment.Code.UNKNOWN), Rank.no(Rank.Code.UNKNOWN), Rank.no(Rank.Code.UNKNOWN))] = 1.0.minus(skill.myRankUpRate)
                } else {
                    result[arrayOf(StatusAilment.no(StatusAilment.Code.UNKNOWN), skill.myRankUp, skill.oppoRankUp)] = skill.myRankUpRate.times(skill.oppoRankUpRate)
                    result[arrayOf(StatusAilment.no(StatusAilment.Code.UNKNOWN), Rank.no(Rank.Code.UNKNOWN), skill.oppoRankUp)] = 1.0.minus(skill.myRankUpRate).times(skill.oppoRankUpRate)
                    result[arrayOf(StatusAilment.no(StatusAilment.Code.UNKNOWN), skill.myRankUp, Rank.no(Rank.Code.UNKNOWN))] = skill.myRankUpRate.times(1.0.minus(skill.oppoRankUpRate))
                    result[arrayOf(StatusAilment.no(StatusAilment.Code.UNKNOWN), Rank.no(Rank.Code.UNKNOWN), Rank.no(Rank.Code.UNKNOWN))] = 1.0.minus(skill.myRankUpRate).times(1.0.minus(skill.oppoRankUpRate))
                }
            }
        } else {
            if (skill.myRankUp.equals(Rank.no(Rank.Code.UNKNOWN))) {
                if (skill.oppoRankUp.equals(Rank.no(Rank.Code.UNKNOWN))) {
                    result[arrayOf(skill.aliment, Rank.no(Rank.Code.UNKNOWN), Rank.no(Rank.Code.UNKNOWN))] = skill.alimentRate
                    result[arrayOf(StatusAilment.no(StatusAilment.Code.UNKNOWN), Rank.no(Rank.Code.UNKNOWN), Rank.no(Rank.Code.UNKNOWN))] = 1.0.minus(skill.alimentRate)
                } else {
                    result[arrayOf(skill.aliment, Rank.no(Rank.Code.UNKNOWN), skill.oppoRankUp)] = skill.alimentRate.times(skill.oppoRankUpRate)
                    result[arrayOf(StatusAilment.no(StatusAilment.Code.UNKNOWN), Rank.no(Rank.Code.UNKNOWN), skill.oppoRankUp)] = 1.0.minus(skill.alimentRate).times(skill.oppoRankUpRate)
                    result[arrayOf(skill.aliment, Rank.no(Rank.Code.UNKNOWN), Rank.no(Rank.Code.UNKNOWN))] = skill.alimentRate.times(1.0.minus(skill.oppoRankUpRate))
                    result[arrayOf(StatusAilment.no(StatusAilment.Code.UNKNOWN), Rank.no(Rank.Code.UNKNOWN), Rank.no(Rank.Code.UNKNOWN))] = 1.0.minus(skill.alimentRate).times(1.0.minus(skill.oppoRankUpRate))
                }
            } else {
                if (skill.oppoRankUp.equals(Rank.no(Rank.Code.UNKNOWN))) {
                    result[arrayOf(skill.aliment, skill.myRankUp, Rank.no(Rank.Code.UNKNOWN))] = skill.alimentRate.times(skill.myRankUpRate)
                    result[arrayOf(StatusAilment.no(StatusAilment.Code.UNKNOWN), skill.myRankUp, Rank.no(Rank.Code.UNKNOWN))] = 1.0.minus(skill.alimentRate).times(skill.myRankUpRate)
                    result[arrayOf(skill.aliment, Rank.no(Rank.Code.UNKNOWN), Rank.no(Rank.Code.UNKNOWN))] = skill.alimentRate.times(1.0.minus(skill.myRankUpRate))
                    result[arrayOf(StatusAilment.no(StatusAilment.Code.UNKNOWN), Rank.no(Rank.Code.UNKNOWN), Rank.no(Rank.Code.UNKNOWN))] = 1.0.minus(skill.alimentRate).times(1.0.minus(skill.myRankUpRate))
                } else {
                    result[arrayOf(skill.aliment, skill.myRankUp, skill.oppoRankUp)] = skill.alimentRate.times(skill.myRankUpRate.times(skill.oppoRankUpRate))
                    result[arrayOf(skill.aliment, Rank.no(Rank.Code.UNKNOWN), skill.oppoRankUp)] = skill.alimentRate.times(1.0.minus(skill.myRankUpRate).times(skill.oppoRankUpRate))
                    result[arrayOf(StatusAilment.no(StatusAilment.Code.UNKNOWN), skill.myRankUp, skill.oppoRankUp)] = 1.0.minus(skill.alimentRate).times(skill.myRankUpRate.times(skill.oppoRankUpRate))
                    result[arrayOf(StatusAilment.no(StatusAilment.Code.UNKNOWN), Rank.no(Rank.Code.UNKNOWN), skill.oppoRankUp)] = 1.0.minus(skill.alimentRate).times(1.0.minus(skill.myRankUpRate).times(skill.oppoRankUpRate))
                    result[arrayOf(skill.aliment, skill.myRankUp, Rank.no(Rank.Code.UNKNOWN))] = skill.alimentRate.times(skill.myRankUpRate.times(1.0.minus(skill.oppoRankUpRate)))
                    result[arrayOf(skill.aliment, Rank.no(Rank.Code.UNKNOWN), Rank.no(Rank.Code.UNKNOWN))] = skill.alimentRate.times(1.0.minus(skill.myRankUpRate).times(1.0.minus(skill.oppoRankUpRate)))
                    result[arrayOf(StatusAilment.no(StatusAilment.Code.UNKNOWN), skill.myRankUp, Rank.no(Rank.Code.UNKNOWN))] = 1.0.minus(skill.alimentRate).times(skill.myRankUpRate.times(1.0.minus(skill.oppoRankUpRate)))
                    result[arrayOf(StatusAilment.no(StatusAilment.Code.UNKNOWN), Rank.no(Rank.Code.UNKNOWN), Rank.no(Rank.Code.UNKNOWN))] = 1.0.minus(skill.alimentRate).times(1.0.minus(skill.myRankUpRate).times(1.0.minus(skill.oppoRankUpRate)))
                }
            }
        }
        return result
    }

    fun updateHP(damage: Int) {
        when (side) {
            PartyInBattle.MY_SIDE -> {
                hpValue -= damage
            }
            PartyInBattle.OPPONENT_SIDE -> {
                val remain = individual.calcHp(252, mega).times(hpRatio).div(100.0).minus(damage)
                hpRatio = if (remain < 1) 0 else remain.times(100.0).div(individual.calcHp(252, mega)).toInt()
            }
        }
    }

    fun dying(): Boolean {
        when (side) {
            PartyInBattle.MY_SIDE -> {
                return if (hpValue < 1) true else false
            }
            PartyInBattle.OPPONENT_SIDE -> {
                return if (hpRatio < 1) true else false
            }
            else -> return false
        }
    }

    fun defeatTimes(damage: Int): Int {
        var hp = hpValue
        if (side == PartyInBattle.OPPONENT_SIDE) {
            hp = individual.calcHp(hpEffortValue, mega).times(hpRatio).div(100.0).toInt()
        }
        //println("${hp} - ${damage}")
        return if (hp < damage) {
            1
        } else if (hp < damage.times(2)) {
            2
        } else if (hp < damage.times(3)) {
            3
        } else if (hp < damage.times(4)) {
            4
        } else {
            5
        }
    }

    fun updateRank(affects: Int) {
        val value = Rank.RANK_TABLE[Rank.code(affects)]
        attackRank += value!![0]
        defenseRank += value[1]
        specialAttackRank += value[2]
        specialDefenseRank += value[3]
        speedRank += value[4]
        hitProbabilityRank += value[5]
        avoidanceRank += value[6]
        criticalRank += value[7]
    }

    fun recoil(damage: Int) {
        val d = if (ability.equals("いしあたま") || ability.equals("マジックガード")) {
            0
        } else {
            if (skill.jname.equals("アフロブレイク") || skill.jname.equals("じごくぐるま") || skill.jname.equals("とっしん") || skill.jname.equals("ワイルドボルト")) {
                damage.div(4.0).toInt()
            } else if (skill.jname.equals("ウッドハンマー") || skill.jname.equals("すてみタックル") ||
                    skill.jname.equals("フレアドライブ") || skill.jname.equals("ブレイブバード") || skill.jname.equals("ボルテッカー")) {
                damage.div(3.0).toInt()
            } else if (skill.jname.equals("もろはのずつき")) {
                damage.div(2.0).toInt()
            } else {
                0
            }
        }

        val d2 = if (ability.equals("マジックガード")) {
            0
        } else if (ability.equals("ちからずく") && skill.aliment == -2) {
            0
        } else if (item.equals("いのちのたま")) {
            if (side == PartyInBattle.MY_SIDE) individual.hpValue.div(10) else individual.calcHp(252, mega).div(10)
        } else {
            0
        }

        if (side == PartyInBattle.MY_SIDE) {
            individual.hpValue = individual.hpValue - d - d2
        } else {
            val max = individual.calcHp(252, mega)
            val now = hp()
            hpRatio = (now - d - d2).times(100.0).div(max).toInt()
        }
    }

    fun recoil() {
        if (ability.equals("ひざげり") || ability.equals("とびひざげり")) {
            return
        }
    }

    fun clone(): PokemonForBattle {
        return PokemonForBattle(side, status, item, characteristic, ability, skill, hpEffortValue, hpRatio, hpValue, attackEffortValue, attackRank,
                defenseEffortValue, defenseRank, specialAttackEffortValue, specialAttackRank, specialDefenseEffortValue, specialDefenseRank,
                speedEffortValue, speedRank, hitProbabilityRank, avoidanceRank, criticalRank, mega, individual)
    }

    fun skillAccuracy(): Double {
        return if (skill.accuracy < 0) return 1.0 else skill.accuracy
    }

    fun noEffect(skill: Skill): Boolean {
        val result = individual.typeScale(Type.code(skill.type))
        return if (result < 1) true else false
    }

    fun summarizable(): Boolean {
        if (ability.equals("よわき") || ability.equals("むしのしらせ") || ability.equals("げきりゅう") ||
                ability.equals("もうか") || ability.equals("しんりょく") || item.equals("オボンのみ")) {
            return false
        }
        return true
    }

    fun sheerForce(): Boolean {
        if (ability.equals("ちからずく")) {
            if (skill.aliment == -1 && skill.myRankUp == -1 && skill.oppoRankUp == -1) {
                return false

            } else {
                //ちからずくで無効にしたことを表現するために-2を代入している
                //fun recoil(damage: Int)にて利用
                skill.aliment = -2
                skill.myRankUp = -2
                skill.oppoRankUp = -2
                return true
            }
        } else {
            return false
        }
    }

    fun useBerry(): Boolean {
        return false
    }

    fun statusMove(): Boolean {
        return false
    }

    fun speedValues(allField: BattleField): Array<Int> {
        val values = individual.master.speedValues()

        for (i in values.indices) {
            if (status == StatusAilment.no(StatusAilment.Code.PARALYSIS)) {
                values[i] = values[i].toDouble().div(4.0).toInt()
            }
            values[i] = values[i].times(getSpeedRankCorrection()).toInt()

            if (allField.weather == BattleField.Weather.Rainy && ability.equals("すいすい")) {
                values[i] = values[i].times(2.0).toInt()
            }
            if (allField.weather == BattleField.Weather.Sunny && ability.equals("ようりょくそ")) {
                values[i] = values[i].times(2.0).toInt()
            }
            if (allField.weather == BattleField.Weather.Sandstorm && ability.equals("すなかき")) {
                values[i] = values[i].times(2.0).toInt()
            }
            if (side == PartyInBattle.MY_SIDE && field.contains(BattleField.Field.Tailwind)) {
                values[i] = values[i].times(2.0).toInt()
            }
        }
        return values
    }


    fun name(): String {
        return individual.name(mega)
    }
}