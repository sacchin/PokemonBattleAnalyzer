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
    var field: MutableList<BattleField.Field> = mutableListOf()

    companion object {
        const val UNKNOWN = -1

        fun create(side: Int, individual: IndividualPBAPokemon): PokemonForBattle {
            return PokemonForBattle(side, -1, "", "", "", Skill(), 0, 100, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, individual)
        }
    }

    fun add(item: BattleField.Field){
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

        if(skill.jname.equals("ふんか") && skill.jname.equals("しおふき") && (hpRatio < 30)){
            val r = hpRatio.toFloat().div(100f).times(150).toInt()
            return if(r < 1) 1 else r
        }

        if(skill.jname.equals("アクロバット")){
            return skill.power * 2
        }

        if(skill.jname.equals("アシストパワー")){
            var sum = if (0 < attackRank) attackRank else 0
            sum += if (0 < defenseRank) defenseRank else 0
            sum += if (0 < specialAttackRank) specialAttackRank else 0
            sum += if (0 < specialDefenseRank) specialDefenseRank else 0
            sum += if (0 < speedRank) speedRank else 0
            sum += if (0 < criticalRank) criticalRank else 0
            return sum.times(20)
        }

        return skill.power
    }

    fun determineSkillPower(defenseSide: PokemonForBattle): Int {
        val skillPower = getSkillPower()
        //TODO れんぞくぎり,ころがる,エコーボイス,りんしょう,たつまき,かぜおこし,きりふだ,ちかい系,なげつける,ふくろだだき,ダメおし,しぜんのめぐみ,ウェザーボール、はきだす、おいうち対応

        if (skill.jname.equals("たたりめ")) {
            return if(defenseSide.status != StatusAilment.no(StatusAilment.Code.UNKNOWN)) skillPower * 2 else skillPower
        }
        if (skill.jname.equals("めざましビンタ")) {
            return if(defenseSide.status == StatusAilment.no(StatusAilment.Code.SLEEP)) skillPower * 2 else skillPower
        }
        if (skill.jname.equals("きつけ")) {
            return if(defenseSide.status == StatusAilment.no(StatusAilment.Code.PARALYSIS)) skillPower * 2 else skillPower
        }

        if (skill.jname.equals("おしおき")) {
            var sum = if (0 < defenseSide.attackRank) defenseSide.attackRank else 0
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
        if (skill.jname.equals("ヘビーボンバー")) {
            val tmp =  (defenseSide.individual.master.weight / individual.master.weight).times(120)
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
        if(Type.code(skill.type) == Type.Code.FIRE && ability.equals("もうか") && (hpRatio < 30)){
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }
        if(Type.code(skill.type) == Type.Code.WATER && ability.equals("げきりゅう") && (hpRatio < 30)){
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }
        if(Type.code(skill.type) == Type.Code.GRASS && ability.equals("しんりょく") && (hpRatio < 30)){
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }
        if(Type.code(skill.type) == Type.Code.BUG && ability.equals("むしのしらせ") && (hpRatio < 30)){
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }

        if (ability.equals("ヨガパワー") || ability.equals("ちからもち")) {
            initValue = Math.round(initValue.times(8192.0).div(4096.0)).toDouble()
        }

        if (item.equals("こだわりハチマキ")) {
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }

        if((Type.code(skill.type) == Type.Code.FIRE || Type.code(skill.type) == Type.Code.ICE) && defenseSide.ability.equals("あついしぼう")){
            initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
        }

        if(item.equals("でんきだま")) {
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

        if(Type.code(skill.type) == Type.Code.FIRE && ability.equals("もうか") && (hpRatio < 30)){
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }
        if(Type.code(skill.type) == Type.Code.WATER && ability.equals("げきりゅう") && (hpRatio < 30)){
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }
        if(Type.code(skill.type) == Type.Code.GRASS && ability.equals("しんりょく") && (hpRatio < 30)){
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }
        if(Type.code(skill.type) == Type.Code.BUG && ability.equals("むしのしらせ") && (hpRatio < 30)){
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }

        if (ability.equals("ヨガパワー") || ability.equals("ちからもち")) {
            initValue = Math.round(initValue.times(8192.0).div(4096.0)).toDouble()
        }

        if((Type.code(skill.type) == Type.Code.FIRE || Type.code(skill.type) == Type.Code.ICE) && defenseSide.ability.equals("あついしぼう")){
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
        val result = individual.master.getAttackValue(31, attackEffortValue)
        return result.times(Characteristic.correction(characteristic, "A")).toDouble()
    }

    fun getAttackRankCorrection(isCritical: Boolean): Double {
        when (attackRank) {
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
        val result = individual.master.getDefenseValue(31, defenseEffortValue)
        return result.times(Characteristic.correction(characteristic, "B")).toDouble()
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
        when (defenseRank) {
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
        val result = individual.master.getSpecialAttackValue(31, specialAttackEffortValue)
        return result.times(Characteristic.correction(characteristic, "C")).toDouble()
    }

    fun getSpecialAttackRankCorrection(isCritical: Boolean): Double {
        when (specialAttackRank) {
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
        val result = individual.master.getSpecialDefenseValue(31, specialDefenseEffortValue)
        return result.times(Characteristic.correction(characteristic, "D")).toDouble()
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
        when (specialDefenseRank) {
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

        return result
    }

    fun getSpeedRankCorrection(): Double {
        when (speedRank) {
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