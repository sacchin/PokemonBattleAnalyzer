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
        var individual: IndividualPokemon = IndividualPokemon()
) {

    val rank = arrayOf(-6, -5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5, 6)
    var trend: TrendForBattle by Delegates.notNull()

    companion object {
        const val UNKNOWN = -1

        fun create(side: Int, individual: IndividualPokemon): PokemonForBattle {
            return PokemonForBattle(side, UNKNOWN, "unknown", "unknown", "unknown", Skill(), 0, 100, 0,
                    0, 6, 0, 6, 0, 6, 0, 6, 0, 6,
                    0, 0, 0, MegaPokemonMasterData.NOT_MEGA, individual)
        }
    }

    fun abilityTrend(): List<Info> {
        if (ability() == "unknown") return trend.tokuseiInfo.filterNotNull()
        return listOf(Info(0, 1.0f, ability(), 0))
    }

    fun characteristicTrend(): List<Info> {
        if (characteristic == "unknown") return trend.seikakuInfo.filterNotNull()
        return listOf(Info(0, 1.0f, characteristic, 0))
    }

    fun itemTrend(): List<Info> {
        if (item == "unknown") return trend.itemInfo.filterNotNull()
        return listOf(Info(0, 1.0f, item, 0))
    }

    fun hpValue(): Int {
        when (side) {
            PartyInBattle.MY_SIDE -> return hpValue
            PartyInBattle.OPPONENT_SIDE -> return individual.calcHp(hpEffortValue, mega).times(hpRatio).div(100.0).toInt()
        }
        return 0
    }

    fun hpRatio(): Int {
        when (side) {
            PartyInBattle.MY_SIDE -> return hpValue.times(100.0).div(individual.calcHp(hpEffortValue, mega)).toInt()
            PartyInBattle.OPPONENT_SIDE -> return hpRatio
        }
        return 0
    }

    fun getSkillPower(): Int {
        if (skill.jname == "マグニチュード" || skill.jname == "トリプルキック") {
            return 71
        }
        if (skill.jname == "ダブルニードル" || skill.jname == "にどげり" || skill.jname == "ホネブーメラン" ||
                skill.jname == "ダブルチョップ" || skill.jname == "ギアソーサー" || skill.jname == "ダブルアタック") {
            return skill.power * 2
        }
        if (skill.jname == "おうふくビンタ" || skill.jname == "れんぞくパンチ" || skill.jname == "みだれづき" ||
                skill.jname == "とげキャノン" || skill.jname == "たまなげ" || skill.jname == "みだれひっかき" ||
                skill.jname == "つっぱり" || skill.jname == "タネマシンガン" || skill.jname == "ロックブラスト" ||
                skill.jname == "ボーンラッシュ" || skill.jname == "つららばり" || skill.jname == "みずしゅりけん" ||
                skill.jname == "ミサイルばり" || skill.jname == "スイープビンタ") {
            if (ability() == "スキルリンク") return skill.power * 5 else return skill.power * 3
        }
        if (skill.jname == "じたばた" || skill.jname == "きしかいせい") {
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
        if (skill.jname == "ふんか" && skill.jname == "しおふき" && (hpRatio < 30)) {
            val r = hpRatio.toFloat().div(100f).times(150).toInt()
            return if (r < 1) 1 else r
        }
        if (skill.jname == "アクロバット") {
            return skill.power * 2
        }
        if (skill.jname == "アシストパワー") {
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

        if (skill.jname == "たたりめ") {
            return if (defenseSide.status != StatusAilment.no(StatusAilment.Code.UNKNOWN)) skillPower * 2 else skillPower
        }
        if (skill.jname == "めざましビンタ") {
            return if (defenseSide.status == StatusAilment.no(StatusAilment.Code.SLEEP)) skillPower * 2 else skillPower
        }
        if (skill.jname == "きつけ") {
            return if (defenseSide.status == StatusAilment.no(StatusAilment.Code.PARALYSIS)) skillPower * 2 else skillPower
        }
        if (skill.jname == "おしおき") {
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
        if (skill.jname == "ヘビーボンバー") {
            val tmp = (defenseSide.individual.master.weight / individual.master.weight).times(120)
            when {
                (tmp <= 24) -> return 120
                (24 < tmp && tmp <= 30) -> return 100
                (30 < tmp && tmp <= 40) -> return 80
                (40 < tmp && tmp <= 60) -> return 60
                (60 < tmp) -> return 40
            }
        }
        if (skill.jname == "けたぐり" || skill.jname == "くさむすび") {
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
        val temp = BattleField()
        if (skill.jname == "ジャイロボール") {
            val tmp = (25 * defenseSide.calcSpeedValue(temp, false, false) / calcSpeedValue(temp, false, false)).toInt() + 1
            return if (150 < tmp) 150 else tmp
        }
        if (skill.jname == "エレキボール") {
            val tmp = calcSpeedValue(temp, false, false).toDouble() / defenseSide.calcSpeedValue(temp, false, false).toDouble()
            when {
                (tmp < 1) -> return 40
                (1 <= tmp && tmp < 2) -> return 60
                (2 <= tmp && tmp < 3) -> return 80
                (3 <= tmp && tmp < 4) -> return 120
                (4 <= tmp) -> return 150
            }
        }
        if (skill.jname == "しぼりとる" || skill.jname == "にぎりつぶす") {
            val tmp = (120 * defenseSide.hpRatio / 100) + 1
            return if (120 < tmp) 120 else tmp
        }
        return skillPower
    }

    fun calcAttackValueCorrection(defenseSide: PokemonForBattle, field: BattleField): Pair<Double, Boolean> {
        var initValue = 4096.0
        var ignore = false

        //TODO よわき, スロースタート: initValue = initValue.times(2048).div(4096).toInt()
        //TODO プラス,マイナス,もらいび: initValue = initValue.times(6144).div(4096).toInt()

        if (ability() == "フラワーギフト" && field.weather == BattleField.Weather.Sunny) {
            initValue = initValue.times(6144).div(4096)
        }
        initValue = relatedBoth(initValue, defenseSide)
        if (status != StatusAilment.no(StatusAilment.Code.UNKNOWN) && ability() == "こんじょう") {
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
            ignore = true
        }
        if (ability() == "ヨガパワー" || ability() == "ちからもち") {
            initValue = Math.round(initValue.times(8192.0).div(4096.0)).toDouble()
        }
        if (item == "こだわりハチマキ") {
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }
        if (item == "でんきだま") {
            initValue = Math.round(initValue.times(8192.0).div(4096.0)).toDouble()
        }
        if (item == "ふといホネ") {
            initValue = Math.round(initValue.times(8192.0).div(4096.0)).toDouble()
        }
        return Pair(initValue, ignore)
    }

    fun calcSpecialAttackValueCorrection(defenseSide: PokemonForBattle, field: BattleField): Double {
        var initValue = 4096.0

        //TODO よわき, スロースタート: initValue = initValue.times(2048).div(4096).toInt()
        //TODO もらいび,プラス,マイナス: initValue = initValue.times(6144).div(4096).toInt()

        if (ability() == "サンパワー" && field.weather == BattleField.Weather.Sunny) {
            initValue = initValue.times(6144).div(4096)
        }
        if (ability() == "フラワーギフト" && field.weather == BattleField.Weather.Sunny) {
            initValue = initValue.times(6144).div(4096)
        }
        initValue = relatedBoth(initValue, defenseSide)
        if (item == "しんかいのキバ") {
            initValue = Math.round(initValue.times(8192.0).div(4096.0)).toDouble()
        }
        if (item == "こころのしずく") {
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }
        if (item == "こだわりメガネ") {
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }
        return initValue
    }

    private fun relatedBoth(initValue: Double, defenseSide: PokemonForBattle): Double {
        var result = initValue
        if (Type.code(skill.type) == Type.Code.FIRE && ability() == "もうか" && (hpRatio < 30)) {
            result = Math.round(result.times(6144.0).div(4096.0)).toDouble()
        }
        if (Type.code(skill.type) == Type.Code.WATER && ability() == "げきりゅう" && (hpRatio < 30)) {
            result = Math.round(result.times(6144.0).div(4096.0)).toDouble()
        }
        if (Type.code(skill.type) == Type.Code.GRASS && ability() == "しんりょく" && (hpRatio < 30)) {
            result = Math.round(result.times(6144.0).div(4096.0)).toDouble()
        }
        if (Type.code(skill.type) == Type.Code.BUG && ability() == "むしのしらせ" && (hpRatio < 30)) {
            result = Math.round(result.times(6144.0).div(4096.0)).toDouble()
        }
        if ((Type.code(skill.type) == Type.Code.FIRE || Type.code(skill.type) == Type.Code.ICE) && defenseSide.ability() == "あついしぼう") {
            result = Math.round(result.times(2048.0).div(4096.0)).toDouble()
        }
        return result
    }

    fun calcAttackValue(): Double {
        val a = if (side == PartyInBattle.MY_SIDE) {
            individual.calcAttack(mega)
        } else {
            individual.calcAttack(attackEffortValue, mega)
        }
        return Math.floor(a.times(Characteristic.correction(characteristic, "A")))
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
        val d = if (side == PartyInBattle.MY_SIDE) {
            individual.calcDefense(mega)
        } else {
            individual.calcDefense(defenseEffortValue, mega)
        }
        return Math.floor(d.times(Characteristic.correction(characteristic, "B")))
    }

    fun calcDefenseValueCorrection(attackSide: PokemonForBattle): Double {
        var initValue = 4096.0

        //×6144 / 4096 四捨五入 くさのけがわ
        if (ability() == "ふしぎなうろこ" && status != 0) {
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }
        if (ability() == "ファーコート" && attackSide.skill.category == 0) {
            initValue = Math.round(initValue.times(8192.0).div(4096.0)).toDouble()
        }
        if (item == "しんかのきせき") {
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }
        if (item == "メタルパウダー") {
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
        val sa = if (side == PartyInBattle.MY_SIDE) {
            individual.calcSpecialAttack(mega)
        } else {
            individual.calcSpecialAttack(specialAttackEffortValue, mega)
        }
        return Math.floor(sa.times(Characteristic.correction(characteristic, "C")))
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
        val sd = if (side == PartyInBattle.MY_SIDE) {
            individual.calcSpecialDefense(mega)
        } else {
            individual.calcSpecialDefense(specialDefenseEffortValue, mega)
        }
        return Math.floor(sd.times(Characteristic.correction(characteristic, "D")))
    }

    fun calcSpecialDefenseValueCorrection(field: BattleField): Double {
        var initValue = 4096.0

        if (ability() == "フラワーギフト" && field.weather == BattleField.Weather.Sunny) {
            initValue = initValue.times(6144).div(4096)
        }
        if (item == "しんかのきせき") {
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }
        if (item == "とつげきチョッキ") {
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }
        if (ability() == "こころのしずく") {
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }
        if (item == "しんかいのウロコ") {
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

    fun calcSpeedValue(allField: BattleField, tailWind: Boolean, onlyStatus: Boolean): Int {
        var result = when (side) {
            PartyInBattle.MY_SIDE -> individual.calcSpeed(mega)
            else -> individual.calcSpeed(252, mega)
        }

        result = Math.floor(result.times(Characteristic.correction(characteristic, "S"))).toInt()
        if (onlyStatus) return result


        if (status == StatusAilment.no(StatusAilment.Code.PARALYSIS) && ability() == "ちどりあし") {
            result = Math.round(result.times(1.5)).toInt()
        }
        if (allField.weather == BattleField.Weather.Sunny && ability() == "ようりょくそ") {
            result = result.times(2)
        }
        if (allField.weather == BattleField.Weather.Rainy && ability() == "すいすい") {
            result = result.times(2)
        }
        if (allField.weather == BattleField.Weather.Sandstorm && ability() == "すなかき") {
            result = result.times(2)
        }

        if (item == "こだわりスカーフ") {
            result = Math.round(result.times(1.5)).toInt()
        }
        if (item == "メタルパウダー") {
            result = Math.round(result.times(1.5)).toInt()
        }

        result = Math.floor(result.times(getSpeedRankCorrection())).toInt()

        if (tailWind) result = result.times(2)

        if (status == StatusAilment.no(StatusAilment.Code.PARALYSIS) && ability() != "ちどりあし") {
            result = Math.floor(result.times(0.25)).toInt()
        }

        return result
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
        if (skill.jname == "やまあらし" || skill.jname == "こおりのいぶき") {
            return 1.0
        }
        var rank = criticalRank
        if (item == "ピントレンズ" || item == "するどいツメ") {
            rank += 1
        }
        if (item == "ラッキーパンチ" || item == "ながねぎ") {
            rank += 1
        }
        if (ability() == "きょううん") {
            rank += 1
        }
        if (skill.jname == "からてチョップ" || skill.jname == "きりさく" || skill.jname == "クラブハンマー" || skill.jname == "はっぱカッター" ||
                skill.jname == "エアロブラスト" || skill.jname == "クロスチョップ" || skill.jname == "エアカッター" || skill.jname == "ポイズンテール" ||
                skill.jname == "ブレイズキック" || skill.jname == "リーフブレード" || skill.jname == "あくうせつだん" || skill.jname == "こうげきしれい" ||
                skill.jname == "クロスポイズン" || skill.jname == "サイコカッター" || skill.jname == "シャドークロー" || skill.jname == "ストーンエッジ" ||
                skill.jname == "つじぎり" || skill.jname == "ドリルライナー") {
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
            if (ability() == "てきおうりょく") return 2.0 else return 1.5
        }
        return 1.0
    }

    fun skillAffects(): MutableMap<Array<Int>, Double> {
        val result = mutableMapOf<Array<Int>, Double>()
        if (skill.aliment == StatusAilment.no(StatusAilment.Code.UNKNOWN)) {
            if (skill.myRankUp == Rank.no(Rank.Code.UNKNOWN)) {
                if (skill.oppoRankUp == Rank.no(Rank.Code.UNKNOWN)) {
                    result[arrayOf(StatusAilment.no(StatusAilment.Code.UNKNOWN), Rank.no(Rank.Code.UNKNOWN), Rank.no(Rank.Code.UNKNOWN))] = 1.0
                } else {
                    result[arrayOf(StatusAilment.no(StatusAilment.Code.UNKNOWN), Rank.no(Rank.Code.UNKNOWN), skill.oppoRankUp)] = skill.oppoRankUpRate
                    result[arrayOf(StatusAilment.no(StatusAilment.Code.UNKNOWN), Rank.no(Rank.Code.UNKNOWN), Rank.no(Rank.Code.UNKNOWN))] = 1.0.minus(skill.oppoRankUpRate)
                }
            } else {
                if (skill.oppoRankUp == Rank.no(Rank.Code.UNKNOWN)) {
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
            if (skill.myRankUp == Rank.no(Rank.Code.UNKNOWN)) {
                if (skill.oppoRankUp == Rank.no(Rank.Code.UNKNOWN)) {
                    result[arrayOf(skill.aliment, Rank.no(Rank.Code.UNKNOWN), Rank.no(Rank.Code.UNKNOWN))] = skill.alimentRate
                    result[arrayOf(StatusAilment.no(StatusAilment.Code.UNKNOWN), Rank.no(Rank.Code.UNKNOWN), Rank.no(Rank.Code.UNKNOWN))] = 1.0.minus(skill.alimentRate)
                } else {
                    result[arrayOf(skill.aliment, Rank.no(Rank.Code.UNKNOWN), skill.oppoRankUp)] = skill.alimentRate.times(skill.oppoRankUpRate)
                    result[arrayOf(StatusAilment.no(StatusAilment.Code.UNKNOWN), Rank.no(Rank.Code.UNKNOWN), skill.oppoRankUp)] = 1.0.minus(skill.alimentRate).times(skill.oppoRankUpRate)
                    result[arrayOf(skill.aliment, Rank.no(Rank.Code.UNKNOWN), Rank.no(Rank.Code.UNKNOWN))] = skill.alimentRate.times(1.0.minus(skill.oppoRankUpRate))
                    result[arrayOf(StatusAilment.no(StatusAilment.Code.UNKNOWN), Rank.no(Rank.Code.UNKNOWN), Rank.no(Rank.Code.UNKNOWN))] = 1.0.minus(skill.alimentRate).times(1.0.minus(skill.oppoRankUpRate))
                }
            } else {
                if (skill.oppoRankUp == Rank.no(Rank.Code.UNKNOWN)) {
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

    fun defeatTimes(damage: Int): Int {
        var hp = hpValue
        if (side == PartyInBattle.OPPONENT_SIDE) {
            hp = individual.calcHp(hpEffortValue, mega).times(hpRatio).div(100.0).toInt()
        }
        //println("${hp} - ${damage}"
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

    fun recoil(damage: Int) {
        val d = if (ability() == "いしあたま" || ability() == "マジックガード") {
            0
        } else {
            if (skill.jname == "アフロブレイク" || skill.jname == "じごくぐるま" || skill.jname == "とっしん" || skill.jname == "ワイルドボルト") {
                damage.div(4.0).toInt()
            } else if (skill.jname == "ウッドハンマー" || skill.jname == "すてみタックル" ||
                    skill.jname == "フレアドライブ" || skill.jname == "ブレイブバード" || skill.jname == "ボルテッカー") {
                damage.div(3.0).toInt()
            } else if (skill.jname == "もろはのずつき") {
                damage.div(2.0).toInt()
            } else {
                0
            }
        }

        val d2 = if (ability() == "マジックガード") {
            0
        } else if (ability() == "ちからずく" && skill.aliment == -2) {
            0
        } else if (item == "いのちのたま") {
            if (side == PartyInBattle.MY_SIDE) individual.calcHp(mega).div(10) else individual.calcHp(252, mega).div(10)
        } else {
            0
        }

        if (side == PartyInBattle.MY_SIDE) {
            hpValue = hpValue - d - d2
        } else {
            val max = individual.calcHp(252, mega)
            val now = hpValue()
            hpRatio = (now - d - d2).times(100.0).div(max).toInt()
        }
    }

    fun clone(): PokemonForBattle {
        return PokemonForBattle(side, status, item, characteristic, ability, skill, hpEffortValue, hpRatio, hpValue, attackEffortValue, attackRank,
                defenseEffortValue, defenseRank, specialAttackEffortValue, specialAttackRank, specialDefenseEffortValue, specialDefenseRank,
                speedEffortValue, speedRank, hitProbabilityRank, avoidanceRank, criticalRank, mega, individual)
    }

    fun noEffect(skill: Skill, attackSide: PokemonForBattle): Boolean {

        val kimottama = attackSide.ability() == "きもったま"
        if (kimottama && (Type.code(skill.type) == Type.Code.NORMAL || Type.code(skill.type) == Type.Code.FIGHTING)) {
            return (individual.master.type1 == Type.no(Type.Code.GHOST) || individual.master.type2 == Type.no(Type.Code.GHOST)).not()
        }
        val katayaburi = attackSide.ability() == "かたやぶり"
        val result = individual.typeScale(Type.code(skill.type), mega, katayaburi)
        if (result < 0.1) {
            return true
        }

        if ((skill.jname == "ねむりごな" || skill.jname == "しびれごな" || skill.jname == "どくのこな" || skill.jname == "キノコのほうし" ||
                skill.jname == "やどりぎのタネ" || skill.jname == "いかりのこな" || skill.jname == "ふんじん" || skill.jname == "わたほうし") &&
                (individual.master.type1 == Type.no(Type.Code.GRASS) || individual.master.type2 == Type.no(Type.Code.GRASS))) {
            return true
        }
        if (skill.jname == "でんじは" &&
                (individual.master.type1 == Type.no(Type.Code.ELECTRIC) || individual.master.type2 == Type.no(Type.Code.ELECTRIC))) {
            return true
        }
        if (skill.jname == "おにび" &&
                (individual.master.type1 == Type.no(Type.Code.FIRE) || individual.master.type2 == Type.no(Type.Code.FIRE))) {
            return true
        }
        if (skill.jname == "どくどく" &&
                (individual.master.type1 == Type.no(Type.Code.POISON) || individual.master.type2 == Type.no(Type.Code.POISON))) {
            return true
        }

        if ("ぼうおん" == ability() && katayaburi.not()) {
            return if (skill.jname == "いにしえのうた" || skill.jname == "いびき" || skill.jname == "いやなおと" || skill.jname == "うたう" ||
                    skill.jname == "エコーボイス" || skill.jname == "おしゃべり" || skill.jname == "おたけび" || skill.jname == "きんぞくおん" ||
                    skill.jname == "くさぶえ" || skill.jname == "さわぐ" || skill.jname == "すてゼリフ" || skill.jname == "ダークパニック" ||
                    skill.jname == "チャームボイス" || skill.jname == "ちょうおんぱ" || skill.jname == "ないしょばなし" || skill.jname == "なきごえ" ||
                    skill.jname == "ハイパーボイス" || skill.jname == "バークアウト" || skill.jname == "ばくおんぱ" || skill.jname == "ほえる" ||
                    skill.jname == "ほろびのうた" || skill.jname == "むしのさざめき" || skill.jname == "りんしょう") true else false
        }
        if ("ぼうじん" == ability() && katayaburi.not()) {
            return if (skill.jname == "ねむりごな" || skill.jname == "しびれごな" || skill.jname == "どくのこな" ||
                    skill.jname == "キノコのほうし" || skill.jname == "わたほうし" || skill.jname == "いかりのこな" || skill.jname == "ふんじん") true else false
        }
        if ("ぼうだん" == ability() && katayaburi.not()) {
            return if (skill.jname == "アイスボール" || skill.jname == "アシッドボム" || skill.jname == "ウェザーボール" || skill.jname == "エナジーボール" ||
                    skill.jname == "エレキボール" || skill.jname == "オクタンほう" || skill.jname == "かえんだん" || skill.jname == "がんせきほう" ||
                    skill.jname == "きあいだま" || skill.jname == "ジャイロボール" || skill.jname == "シャドーボール" || skill.jname == "タネマシンガン" ||
                    skill.jname == "タネばくだん" || skill.jname == "タマゴばくだん" || skill.jname == "たまなげ" || skill.jname == "でんじほう" ||
                    skill.jname == "どろばくだん" || skill.jname == "はどうだん" || skill.jname == "ヘドロばくだん" || skill.jname == "マグネットボム" ||
                    skill.jname == "ミストボール") true else false
        }
        return false
    }

    fun sheerForce(): Boolean {
        if (ability() == "ちからずく") {
            if (skill.aliment == -1 && skill.myRankUp == -1 && skill.oppoRankUp == -1) {
                return false

            } else {
                //ちからずくで無効にしたことを表現するために-2を代入している
                //fun recoil(damage: Int)にて利用
                //skill.aliment = -2
                //skill.myRankUp = -2
                //skill.oppoRankUp = -2
                return true
            }
        } else {
            return false
        }
    }

    fun speedValues(tailWind: Boolean): Array<Int> {
        val values = individual.speedValues(mega)

        for (i in values.indices) {
            values[i] = Math.floor(values[i].times(getSpeedRankCorrection())).toInt()

            if (tailWind) {
                values[i] = values[i].times(2)
            }

            if (status == StatusAilment.no(StatusAilment.Code.PARALYSIS)) {
                values[i] = Math.floor(values[i].toDouble().div(4.0)).toInt()
            }

        }
        return values
    }

    fun ability(): String {
        return when (side) {
            PartyInBattle.MY_SIDE -> individual.ability(mega)
            else -> ability
        }
    }

    fun name(): String {
        return individual.name(mega)
    }
}