package com.gmail.sacchin13.pokemonbattleanalyzer.logic

import android.util.Log
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.*

class BattleCalculator {

    val notCompatibleWords = arrayOf(
            "れんぞくぎり", "ころがる", "エコーボイス", "りんしょう",
            "たつまき", "かぜおこし", "きりふだ", "ちかい系", "なげつける",
            "ふくろだだき", "ダメおし", "しぜんのめぐみ", "ウェザーボール",
            "はきだす", "おいうち")

    enum class RiskDegree {
        SAFE,
        LGTM,
        NORMAL,
        WARN,
        FATAL
    }

    companion object {
        fun getResult(mine: PokemonForBattle, opponent: PokemonForBattle, field: BattleField): BattleResult {
            val result = BattleResult()

            var sum = 0.0
            loop@ for (item in opponent.itemTrend()) {
                if (item.name.isNullOrEmpty() || item.usageRate < 0.01) continue else println("${item.name}")
                for (tokusei in opponent.abilityTrend()) {
                    if (tokusei.name.isNullOrEmpty() || tokusei.usageRate < 0.01) continue else println("- ${tokusei.name}")
                    for (seikaku in opponent.characteristicTrend()) {
                        if (seikaku.name.isNullOrEmpty() || seikaku.usageRate < 0.01) continue else println("-- ${seikaku.name}")
                        val rate = item.usageRate.times(tokusei.usageRate.times(seikaku.usageRate)).div(opponent.trend.skillList.size.toDouble())

                        opponent.item = item.name
                        opponent.ability = tokusei.name
                        opponent.characteristic = seikaku.name
                        result.add(opponent, rate.times(opponent.trend.skillList.size.toDouble()))
                        result.add(opponent.trend.skillList)

                        val skip = skipOrder(mine, opponent)
                        if(skip){
                            val(first, second) = attackOrder(mine, opponent)

                        }else{

                        }
                        sum += rate.times(opponent.trend.skillList.size.toDouble())




//                        for (waza in opponent.trend.skillList.filterNotNull()) {
//                            if (waza.usageRate < 0.01) continue
//
//                            opponent.skill = waza.skill
//
//                            println("--- 発生率${rate}: ${first.individual.master.jname}(${first.hp()}), ${first.skill.jname} -> ${second.individual.master.jname}(${second.hp()}), ${second.skill.jname}")
//                            val resultOfTurn = simulateTurn(rate, first, second, field)
//
//                            result.mayOccur[BattleStatus.Code.WIN] = result.mayOccur[BattleStatus.Code.WIN]!!.plus(resultOfTurn.mayOccur[BattleStatus.Code.WIN] as Double)
//                            result.mayOccur[BattleStatus.Code.DEFEAT] = result.mayOccur[BattleStatus.Code.DEFEAT]!!.plus(resultOfTurn.mayOccur[BattleStatus.Code.DEFEAT] as Double)
//                            result.mayOccur[BattleStatus.Code.REVERSE] = result.mayOccur[BattleStatus.Code.REVERSE]!!.plus(resultOfTurn.mayOccur[BattleStatus.Code.REVERSE] as Double)
//                            result.mayOccur[BattleStatus.Code.OWN_HEAD] = result.mayOccur[BattleStatus.Code.OWN_HEAD]!!.plus(resultOfTurn.mayOccur[BattleStatus.Code.OWN_HEAD] as Double)
//                            result.mayOccur[BattleStatus.Code.DRAW] = result.mayOccur[BattleStatus.Code.DRAW]!!.plus(resultOfTurn.mayOccur[BattleStatus.Code.DRAW] as Double)
//                        }
//                        if (0.9 < sum) break@loop
                    }
                }
            }

            result.coverRate = sum
            Log.v("getResult", "${result.coverRate}")
            Log.v("getResult", "${result.mayOccur[BattleStatus.Code.WIN]}")
            Log.v("getResult", "${result.mayOccur[BattleStatus.Code.DEFEAT]}")
            Log.v("getResult", "${result.mayOccur[BattleStatus.Code.REVERSE]}")
            Log.v("getResult", "${result.mayOccur[BattleStatus.Code.OWN_HEAD]}")
            Log.v("getResult", "${result.mayOccur[BattleStatus.Code.DRAW]}")

            return result
        }

        fun simulateTurn(baseRate: Double, baseFirst: PokemonForBattle, baseSecond: PokemonForBattle, field: BattleField): BattleResult {
            val result = BattleResult()

//            val firstAttackSuccessRate = baseRate.times(baseFirst.skillAccuracy())
//            if (firstAttackSuccessRate < 0.0000000001) return result

            baseFirst.attackEffortValue = 0
            baseFirst.specialAttackEffortValue = 0
            baseSecond.defenseEffortValue = 0
            baseSecond.specialDefenseEffortValue = 0
            val d0to0 = doSkill(baseFirst, baseSecond, field, baseFirst.calcCriticalRate().toDouble(), true, false)

            baseFirst.attackEffortValue = 252
            baseFirst.specialAttackEffortValue = 252
            baseSecond.defenseEffortValue = 0
            baseSecond.specialDefenseEffortValue = 0
            val d252to0 = doSkill(baseFirst, baseSecond, field, baseFirst.calcCriticalRate().toDouble(), true, false)

            baseFirst.attackEffortValue = 0
            baseFirst.specialAttackEffortValue = 0
            baseSecond.defenseEffortValue = 252
            baseSecond.specialDefenseEffortValue = 252
            val d0to252 = doSkill(baseFirst, baseSecond, field, baseFirst.calcCriticalRate().toDouble(), true, false)

            baseFirst.attackEffortValue = 252
            baseFirst.specialAttackEffortValue = 252
            baseSecond.defenseEffortValue = 252
            baseSecond.specialDefenseEffortValue = 252
            val d252to252 = doSkill(baseFirst, baseSecond, field, baseFirst.calcCriticalRate().toDouble(), true, false)

//            println("---- 成功率${firstAttackSuccessRate}: 先手ダメ${damages1}")

            baseSecond.hpEffortValue = 252
            for (d in d252to252) result.updateDefeatTimes( baseSecond.defeatTimes(d.key), baseRate.times(d.value))
            for (d in d0to252) result.updateDefeatTimes( baseSecond.defeatTimes(d.key), baseRate.times(d.value))
            for (d in d252to0) result.updateDefeatTimes( baseSecond.defeatTimes(d.key), baseRate.times(d.value))
            for (d in d0to0) result.updateDefeatTimes( baseSecond.defeatTimes(d.key), baseRate.times(d.value))

//
//
//
//
//
//            else {
//                    if (copiedSecond.ability.equals("")) {
//                    }
//
//                    val secondAttackSuccessRate = firstAttackSuccessRate.times(d1.value).times(copiedSecond.skillAccuracy())
//                    if (secondAttackSuccessRate < 0.0000000001) continue
//                    result.updateDraw(firstAttackSuccessRate.times(d1.value).minus(secondAttackSuccessRate))
//
//                    val affects = copiedFirst.skillAffects()
//                    println("----- ダメ計後 ${copiedFirst.individual.master.jname}(${copiedFirst.hp()}) -> ${copiedSecond.individual.master.jname}(${copiedSecond.hp()})")
//                    for (case in affects) {
//                        if (case.value.minus(0.0) < 0.001) {
//                            //println("------: no affects")
//                            continue
//                        }
//                        val rate = case.value.times(secondAttackSuccessRate)
//                        copiedSecond.status = case.key[0]
//                        copiedFirst.updateRank(case.key[1])
//                        copiedSecond.updateRank(case.key[2])
//
//                        val damages2 = doSkill(copiedSecond, copiedFirst, field, copiedSecond.calcCriticalRate().toDouble(), false, false)
//                        println("------ ${rate}:${StatusAilment.name(copiedSecond.status)}-${case.key[1]}-${case.key[2]}-${damages2}")
//                        for (d2 in damages2) {
//                            if (copiedFirst.dying(d2.key)) {
//                                result.updateReverseOwnHeadDefeat(copiedFirst, copiedSecond, rate.times(d2.value))
//                            } else {
//                                result.updateDraw(rate.times(d2.value))
//                            }
//                        }
//                    }
//                    copiedSecond.firstCheck = false
//                }
//            }
//
//
//            val firstAttackFailRate = baseRate.minus(firstAttackSuccessRate)
//            val copiedFirst = baseFirst.clone()
//            copiedFirst.recoil()
//
//            val copiedSecond = baseSecond.clone()
//            if (copiedFirst.dying()) {
////                result.updateWinDefeat(copiedFirst, copiedSecond, firstAttackSuccessRate.times(d1.value))
//            } else {
//                if (copiedSecond.ability.equals("")) {
//
//                }
//
//                val secondAttackSuccessRate = firstAttackFailRate.times(copiedSecond.skillAccuracy())
//                if (secondAttackSuccessRate < 0.0000000001) return result
//                result.updateDraw(firstAttackFailRate.minus(secondAttackSuccessRate))
//
//                val rate = secondAttackSuccessRate
//
//                val damages2 = doSkill(copiedSecond, copiedFirst, field, copiedSecond.calcCriticalRate().toDouble(), false, false)
//                println("---- 失敗率${secondAttackSuccessRate}: ${copiedFirst.individual.master.jname}(${copiedFirst.hp()}) -> ${copiedSecond.individual.master.jname}(${copiedSecond.hp()})")
//                for (d2 in damages2) {
//                    val remain2 = copiedFirst.individual.calcHp().times(copiedFirst.hpRatio).div(100).minus(d2.key)
//                    if (remain2 < 1) {
//                        result.updateReverseOwnHeadDefeat(copiedFirst, copiedSecond, rate.times(d2.value))
//                    } else {
//                        result.updateDraw(rate.times(d2.value))
//                    }
//                }
//            }

            return result
        }

        fun skipOrder(mine: PokemonForBattle, opponent: PokemonForBattle): Boolean{
            return when {
                mine.skill.priority < opponent.skill.priority -> true
                mine.skill.priority > opponent.skill.priority -> true
                else -> false
            }
        }

        fun attackOrder(mine: PokemonForBattle, opponent: PokemonForBattle): Pair<PokemonForBattle, PokemonForBattle>{
            return when {
                mine.skill.priority < opponent.skill.priority -> Pair(opponent, mine)
                mine.skill.priority > opponent.skill.priority -> Pair(mine, opponent)
                else -> Pair(opponent, mine)
            }
        }

        fun doSkill(attackSide: PokemonForBattle, defenseSide: PokemonForBattle, field: BattleField,
                    criticalRate: Double, first: Boolean, damaged: Boolean): MutableMap<Int, Double> {
            if (attackSide.skill.category == 2) {

                return mutableMapOf(0 to 1.0)
            }
            if (defenseSide.noEffect(attackSide.skill)) {
                return mutableMapOf(0 to 1.0)
            }

            var attackValue = 0.0
            var attackValueCorrection = 0.0
            var defenseValue = 0.0
            var defenseValueCorrection = 0.0
            var attackRankCorrectionA = 0.0
            var defenseRankCorrectionA = 0.0
            var attackRankCorrectionB = 0.0
            var defenseRankCorrectionB = 0.0
            when (attackSide.skill.category) {
                0 -> {
                    attackValue = attackSide.calcAttackValue()
                    attackValueCorrection = attackSide.calcAttackValueCorrection(defenseSide)
                    attackRankCorrectionA = attackSide.getAttackRankCorrection(true)
                    attackRankCorrectionB = attackSide.getAttackRankCorrection(false)
                    defenseValue = defenseSide.calcDefenseValue()
                    defenseValueCorrection = defenseSide.calcDefenseValueCorrection(attackSide)
                    defenseRankCorrectionA = defenseSide.getDefenseRankCorrection(true)
                    defenseRankCorrectionB = defenseSide.getDefenseRankCorrection(false)
                }
                1 -> {
                    attackValue = attackSide.calcSpecialAttackValue()
                    attackValueCorrection = attackSide.calcSpecialAttackValueCorrection(defenseSide)
                    attackRankCorrectionA = attackSide.getSpecialAttackRankCorrection(true)
                    attackRankCorrectionB = attackSide.getSpecialAttackRankCorrection(false)
                    defenseValue = defenseSide.calcSpecialDefenseValue()
                    defenseValueCorrection = defenseSide.calcSpecialDefenseValueCorrection()
                    defenseRankCorrectionA = defenseSide.getSpecialDefenseRankCorrection(true)
                    defenseRankCorrectionB = defenseSide.getSpecialDefenseRankCorrection(false)
                }
            }

            val skillPower = calcSkillPower(attackSide, defenseSide, field, first, damaged)

            attackValue = calcAttackValue(attackValue, attackValueCorrection, attackRankCorrectionA, attackSide)
            defenseValue = calcDefenseValue(defenseValue, defenseValueCorrection, defenseRankCorrectionA, attackSide, defenseSide)

            var criticalDamage = Math.floor(Math.floor(22.times(skillPower).times(attackValue).div(defenseValue)).toDouble().div(50.0).plus(2.0))
            //TODO * 3072 / 4096 五捨五超入 複数ダメージ補正
            //TODO * 2048 / 4096 五捨五超入 おやこあい2回目
            //TODO * 6144 / 4096 五捨五超入 天候強化
            //TODO * 2048 / 4096 五捨五超入 天候弱化
            criticalDamage = Math.floor(criticalDamage.times(1.5))


            attackValue = calcAttackValue(attackValue, attackValueCorrection, attackRankCorrectionB, attackSide)
            defenseValue = calcDefenseValue(defenseValue, defenseValueCorrection, defenseRankCorrectionB, attackSide, defenseSide)

            val damage = Math.floor(Math.floor(22.times(skillPower).times(attackValue).div(defenseValue)).toDouble().div(50.0).plus(2.0))
            //TODO * 3072 / 4096 五捨五超入 複数ダメージ補正
            //TODO * 2048 / 4096 五捨五超入 おやこあい2回目
            //TODO * 6144 / 4096 五捨五超入 天候強化
            //TODO * 2048 / 4096 五捨五超入 天候弱化

            val randomDamage = arrayOf(
                    Math.floor(criticalDamage.times(0.85)),
                    Math.floor(criticalDamage.times(0.86)),
                    Math.floor(criticalDamage.times(0.87)),
                    Math.floor(criticalDamage.times(0.88)),
                    Math.floor(criticalDamage.times(0.89)),
                    Math.floor(criticalDamage.times(0.90)),
                    Math.floor(criticalDamage.times(0.91)),
                    Math.floor(criticalDamage.times(0.92)),
                    Math.floor(criticalDamage.times(0.93)),
                    Math.floor(criticalDamage.times(0.94)),
                    Math.floor(criticalDamage.times(0.95)),
                    Math.floor(criticalDamage.times(0.96)),
                    Math.floor(criticalDamage.times(0.97)),
                    Math.floor(criticalDamage.times(0.98)),
                    Math.floor(criticalDamage.times(0.99)),
                    Math.floor(criticalDamage),
                    Math.floor(damage.times(0.85)),
                    Math.floor(damage.times(0.86)),
                    Math.floor(damage.times(0.87)),
                    Math.floor(damage.times(0.88)),
                    Math.floor(damage.times(0.89)),
                    Math.floor(damage.times(0.90)),
                    Math.floor(damage.times(0.91)),
                    Math.floor(damage.times(0.92)),
                    Math.floor(damage.times(0.93)),
                    Math.floor(damage.times(0.94)),
                    Math.floor(damage.times(0.95)),
                    Math.floor(damage.times(0.96)),
                    Math.floor(damage.times(0.97)),
                    Math.floor(damage.times(0.98)),
                    Math.floor(damage.times(0.99)),
                    Math.floor(damage))

            val damageCorrectionA = calcDamageCorrection(attackSide, defenseSide, field, true)
            val damageCorrectionB = calcDamageCorrection(attackSide, defenseSide, field, false)
            for (i in 0..(randomDamage.size - 1)) {
                randomDamage[i] = Math.ceil(randomDamage[i].times(attackSide.typeBonus()).minus(0.5))
                randomDamage[i] = Math.floor(randomDamage[i].times(Type.calculateAffinity(Type.code(attackSide.skill.type), defenseSide.individual.master)))
                if (attackSide.skill.category == 0 && attackSide.status == StatusAilment.no(StatusAilment.Code.BURN)) {
                    randomDamage[i] = Math.floor(randomDamage[i].times(0.5))
                }
                if (i < 16) {
                    randomDamage[i] = Math.ceil(randomDamage[i].times(damageCorrectionA).div(4096).minus(0.5))
                } else {
                    randomDamage[i] = Math.ceil(randomDamage[i].times(damageCorrectionB).div(4096).minus(0.5))
                }
                if (randomDamage[i] < 1) {
                    randomDamage[i] = 1.0
                }
            }

            val r = criticalRate.div(16.0)
            val notr = 1.minus(criticalRate).div(16.0)

            val result = mutableMapOf<Int, Double>()
            for (i in 0..(randomDamage.size - 1)) {
                val k = randomDamage[i].toInt()
                if (result.containsKey(k)) {
                    result[k] = if (i < 16) result[k]!!.plus(r) else result[k]!!.plus(notr)
                } else {
                    result[k] = if (i < 16) r else notr
                }
            }
            return result
        }

        fun calcAttackValue(base: Double, attackValueCorrection: Double, attackRankCorrection: Double, attackSide: PokemonForBattle): Double {
            var result = Math.floor(base.times(attackRankCorrection))
            if (attackSide.skill.category == 0 && attackSide.ability.equals("はりきり")) {
                result = Math.ceil(result.times(1.5).minus(0.5)) //五捨五超入
            }
            result = Math.ceil(result.times(attackValueCorrection).div(4096.0).minus(0.5))//五捨五超入
            return if (result < 1) 1.0 else result
        }

        fun calcDefenseValue(base: Double, defenseValueCorrection: Double, defenseRankCorrection: Double, attackSide: PokemonForBattle, defenseSide: PokemonForBattle): Double {
            var result = Math.floor(base.times(defenseRankCorrection))
            if (attackSide.skill.category == 1 && //ToDo: すなあらし対応
                    (defenseSide.individual.master.type1 == Type.no(Type.Code.ROCK) || defenseSide.individual.master.type2 == Type.no(Type.Code.ROCK))) {
                result = Math.ceil(result.times(1.5).minus(0.5)) //五捨五超入
            }
            return Math.ceil(result.times(defenseValueCorrection).div(4096.0).minus(0.5))//五捨五超入
        }

        fun calcSkillPowerCorrection(attackSide: PokemonForBattle, defenseSide: PokemonForBattle, field: BattleField, first: Boolean): Double {
            var initValue = 4096.0


            //TODO とうそうしん弱化,オーラブレイク initValue.times(3042).div(4096).toInt()

            if ((attackSide.skill.jname.equals("れんぞくパンチ") || attackSide.skill.jname.equals("メガトンパンチ") || attackSide.skill.jname.equals("ほのおのパンチ") || attackSide.skill.jname.equals("れいとうパンチ") || attackSide.skill.jname.equals("かみなりパンチ") || attackSide.skill.jname.equals("ピヨピヨパンチ") ||
                    attackSide.skill.jname.equals("マッハパンチ") || attackSide.skill.jname.equals("ばくれつパンチ") || attackSide.skill.jname.equals("きあいパンチ") || attackSide.skill.jname.equals("コメットパンチ") || attackSide.skill.jname.equals("シャドーパンチ") || attackSide.skill.jname.equals("スカイアッパー") ||
                    attackSide.skill.jname.equals("アームハンマー") || attackSide.skill.jname.equals("バレットパンチ") || attackSide.skill.jname.equals("ドレインパンチ")) && attackSide.ability.equals("てつのこぶし")) {
                initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
            }
            if ((attackSide.skill.jname.equals("すてみタックル") || attackSide.skill.jname.equals("ウッドハンマー") || attackSide.skill.jname.equals("ブレイブバード") || attackSide.skill.jname.equals("とっしん") || attackSide.skill.jname.equals("じごくぐるま") || attackSide.skill.jname.equals("ボルテッカー") ||
                    attackSide.skill.jname.equals("フレアドライブ") || attackSide.skill.jname.equals("もろはのずつき") || attackSide.skill.jname.equals("とびげり") || attackSide.skill.jname.equals("とびひざげり")) && attackSide.ability.equals("すてみ")) {
                initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
            }

            //TODO とうそうしん強化 initValue.times(5120).div(4096).toInt()

            if (attackSide.sheerForce()) {
                initValue = Math.round(initValue.times(5325.0).div(4096.0)).toDouble()
            }

            if (attackSide.ability.equals("すなのちから") && field.weather == BattleField.Weather.Sandstorm &&
                    (attackSide.skill.type == Type.no(Type.Code.ROCK) || attackSide.skill.type == Type.no(Type.Code.STEEL) || attackSide.skill.type == Type.no(Type.Code.GROUND))) {
                initValue = Math.round(initValue.times(5325.0).div(4096.0)).toDouble()
                attackSide.skill.type = Type.no(Type.Code.FLYING)
            }
            if (attackSide.ability.equals("スカイスキン") && attackSide.skill.type == Type.no(Type.Code.NORMAL)) {
                initValue = Math.round(initValue.times(5325.0).div(4096.0)).toDouble()
                attackSide.skill.type = Type.no(Type.Code.FLYING)
            }
            if (attackSide.ability.equals("フェアリースキン") && attackSide.skill.type == Type.no(Type.Code.NORMAL)) {
                initValue = Math.round(initValue.times(5325.0).div(4096.0)).toDouble()
                attackSide.skill.type = Type.no(Type.Code.FAIRY)
            }
            if (attackSide.ability.equals("フリーズスキン") && attackSide.skill.type == Type.no(Type.Code.NORMAL)) {
                initValue = Math.round(initValue.times(5325.0).div(4096.0)).toDouble()
                attackSide.skill.type = Type.no(Type.Code.ICE)
            }

            if (attackSide.ability.equals("かたいツメ") && attackSide.skill.contact) {
                initValue = Math.round(initValue.times(5325.0).div(4096.0)).toDouble()
            }

            if (attackSide.ability.equals("アナライズ") && !first) {
                initValue = Math.round(initValue.times(5325.0).div(4096.0)).toDouble()
            }

            //TODO 5448 / 4096 四捨五入(※2体以上いても補正は1回のみ) フェアリーオーラ,ダークオーラ

            if (attackSide.ability.equals("ねつぼうそう") && attackSide.status == StatusAilment.no(StatusAilment.Code.BURN)) {
                initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
            }
            if (attackSide.ability.equals("どくぼうそう") && (attackSide.status == StatusAilment.no(StatusAilment.Code.POISON) || attackSide.status == StatusAilment.no(StatusAilment.Code.BADPOISON))) {
                initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
            }
            if ((attackSide.skill.jname.equals("かみつく") || attackSide.skill.jname.equals("かみくだく") || attackSide.skill.jname.equals("かみなりのキバ") || attackSide.skill.jname.equals("ほのおのキバ") ||
                    attackSide.skill.jname.equals("こおりのキバ") || attackSide.skill.jname.equals("どくどくのキバ") || attackSide.skill.jname.equals("ひっさつまえば")) && attackSide.ability.equals("がんじょうあご")) {
                initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
            }

            if ((attackSide.skill.jname.equals("はどうだん") || attackSide.skill.jname.equals("みずのはどう") || attackSide.skill.jname.equals("りゅうのはどう") ||
                    attackSide.skill.jname.equals("あくのはどう") || attackSide.skill.jname.equals("こんげんのはどう")) && attackSide.ability.equals("メガランチャー")) {
                initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
            }


            if ((attackSide.skill.power <= 60) && attackSide.ability.equals("テクニシャン")) {
                initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
            }

            if (Type.code(attackSide.skill.type) == Type.Code.FIRE && defenseSide.ability.equals("たいねつ")) {
                initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
            }

            if (Type.code(attackSide.skill.type) == Type.Code.FIRE && defenseSide.ability.equals("かんそうはだ")) {
                initValue = Math.round(initValue.times(5120.0).div(4096.0)).toDouble()
            }


            if (attackSide.skill.category == 0 && attackSide.item.equals("ちからのハチマキ")) {
                initValue = Math.round(initValue.times(4505.0).div(4096.0)).toDouble()
            }
            if (attackSide.skill.category == 1 && attackSide.item.equals("ものしりメガネ")) {
                initValue = Math.round(initValue.times(4505.0).div(4096.0)).toDouble()
            }

            //ToDo: こうんごうだま、しらたま、はっきんだま対応
            if (Type.code(attackSide.skill.type) == Type.Code.NORMAL && attackSide.item.equals("シルクのスカーフ")) {
                initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
            }
            if (Type.code(attackSide.skill.type) == Type.Code.FLYING && (attackSide.item.equals("あおぞらプレート") || attackSide.item.equals("するどいくちばし"))) {
                initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
            }
            if (Type.code(attackSide.skill.type) == Type.Code.ELECTRIC && (attackSide.item.equals("いかずちプレート") || attackSide.item.equals("じしゃく"))) {
                initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
            }
            if (Type.code(attackSide.skill.type) == Type.Code.ROCK && (attackSide.item.equals("がんせきプレート") || attackSide.item.equals("かたいいし") || attackSide.item.equals("がんせきおこう"))) {
                initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
            }
            if (Type.code(attackSide.skill.type) == Type.Code.STEEL && (attackSide.item.equals("こうてつプレート") || attackSide.item.equals("メタルコート"))) {
                initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
            }
            if (Type.code(attackSide.skill.type) == Type.Code.FIGHTING && (attackSide.item.equals("こぶしのプレート") || attackSide.item.equals("くろおび"))) {
                initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
            }
            if (Type.code(attackSide.skill.type) == Type.Code.DARK && (attackSide.item.equals("こわもてプレート") || attackSide.item.equals("くろいメガネ"))) {
                initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
            }
            if (Type.code(attackSide.skill.type) == Type.Code.WATER && (attackSide.item.equals("しずくプレート") || attackSide.item.equals("しんぴのしずく") || attackSide.item.equals("うしおのおこう") || attackSide.item.equals("さざなみのおこう"))) {
                initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
            }
            if (Type.code(attackSide.skill.type) == Type.Code.GROUND && (attackSide.item.equals("だいちのプレート") || attackSide.item.equals("やわらかいすな"))) {
                initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
            }
            if (Type.code(attackSide.skill.type) == Type.Code.BUG && (attackSide.item.equals("たまむしプレート") || attackSide.item.equals("ぎんのこな"))) {
                initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
            }
            if (Type.code(attackSide.skill.type) == Type.Code.ICE && (attackSide.item.equals("つららのプレート") || attackSide.item.equals("とけないこおり"))) {
                initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
            }
            if (Type.code(attackSide.skill.type) == Type.Code.FIRE && (attackSide.item.equals("ひのたまプレート") || attackSide.item.equals("もくたん"))) {
                initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
            }
            if (Type.code(attackSide.skill.type) == Type.Code.PSYCHIC && (attackSide.item.equals("ふしぎのプレート") || attackSide.item.equals("まがったスプーン") || attackSide.item.equals("あやしいおこう"))) {
                initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
            }
            if (Type.code(attackSide.skill.type) == Type.Code.GRASS && (attackSide.item.equals("みどりのプレート") || attackSide.item.equals("きせきのタネ") || attackSide.item.equals("おはなのおこう"))) {
                initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
            }
            if (Type.code(attackSide.skill.type) == Type.Code.POISON && (attackSide.item.equals("もうどくプレート") || attackSide.item.equals("どくバリ"))) {
                initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
            }
            if (Type.code(attackSide.skill.type) == Type.Code.GHOST && (attackSide.item.equals("もののけプレート") || attackSide.item.equals("のろいのおふだ"))) {
                initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
            }
            if (Type.code(attackSide.skill.type) == Type.Code.DRAGON && (attackSide.item.equals("りゅうのプレート") || attackSide.item.equals("りゅうのキバ"))) {
                initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
            }


            //5325 / 4096 四捨五入 ジュエル

            if (attackSide.skill.jname.equals("ソーラービーム") && field.weather == BattleField.Weather.Rainy) {
                initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
            }

            //6144 / 4096 四捨五入 さきどり はたきおとす

            //8192 / 4096 四捨五入クロスサンダー,クロスフレイム,じゅうでん,かたきうち

            if (attackSide.skill.jname.equals("からげんき") && attackSide.status != StatusAilment.no(StatusAilment.Code.UNKNOWN)) {
                initValue = Math.round(initValue.times(8192.0).div(4096.0)).toDouble()
            }
            if (attackSide.skill.jname.equals("ベノムショック") && (defenseSide.status != StatusAilment.no(StatusAilment.Code.POISON) || defenseSide.status != StatusAilment.no(StatusAilment.Code.BADPOISON))) {
                initValue = Math.round(initValue.times(8192.0).div(4096.0)).toDouble()
            }
            if (attackSide.skill.jname.equals("しおみず") && defenseSide.hpRatio < 50) {
                initValue = Math.round(initValue.times(8192.0).div(4096.0)).toDouble()
            }

            //6144 / 4096 四捨五入 てだすけ1
            //9216 / 4096 四捨五入 てだすけ2

            if (attackSide.skill.type == Type.no(Type.Code.ELECTRIC) && field.field.contains(BattleField.Field.MudSport)) {
                initValue = Math.round(initValue.times(1352.0).div(4096.0)).toDouble()
            }
            if (attackSide.skill.type == Type.no(Type.Code.FIRE) && field.field.contains(BattleField.Field.WaterSport)) {
                initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
            }

            //2048 / 4096 四捨五入 グラスフィールド弱化,ミストフィールド
            //6144 / 4096 四捨五入 グラスフィールド強化,エレキフィールド

            return initValue
        }

        fun calcSkillPower(attackSide: PokemonForBattle, defenseSide: PokemonForBattle, field: BattleField, first: Boolean, damaged: Boolean): Int {
            //first と damagedを利用して技の威力を計算するもののみ、attackSide.determineSkillPower(defenseSide)の外側で計算
            var skillPower = attackSide.determineSkillPower(defenseSide)
            if (attackSide.skill.jname.equals("しっぺがえし") && !first) {
                skillPower = skillPower.times(2)
            }
            if (attackSide.skill.jname.equals("ゆきなだれ") || attackSide.skill.jname.equals("リベンジ") && damaged) {
                skillPower = skillPower.times(2)
            }

            val skillCorrection = calcSkillPowerCorrection(attackSide, defenseSide, field, first)
            //五捨五超入
            val tmp = Math.ceil(skillPower.toDouble().times(skillCorrection).div(4096.0).minus(0.5)).toInt()
            return if (tmp < 1) 1 else tmp
        }

        fun calcDamageCorrection(attackSide: PokemonForBattle, defenseSide: PokemonForBattle, field: BattleField, isCritical: Boolean): Double {
            var initValue = 4096.0

            if (attackSide.skill.category == 0 && defenseSide.field.contains(BattleField.Field.Reflect)) {
                initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
            }
            if (attackSide.skill.category == 1 && defenseSide.field.contains(BattleField.Field.LightScreen)) {
                initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
            }

            if (defenseSide.hpRatio == 100 && defenseSide.ability.equals("マルチスケイル")) {
                initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
            }

            val batsugun = Type.calculateAffinity(Type.code(attackSide.skill.type), defenseSide.individual.master)
            if (batsugun < 1 && attackSide.ability.equals("いろめがね")) {
                initValue = Math.round(initValue.times(8192.0).div(4096.0)).toDouble()
            }

            if (isCritical && attackSide.ability.equals("スナイパー")) {
                initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
            }

            //Todo: フレンドガード1 initValue = Math.round(initValue.times(3072.0).div(4096.0)).toDouble()
            //Todo: フレンドガード2 initValue = Math.round(initValue.times(2304.0).div(4096.0)).toDouble()

            if (batsugun < 1 && (defenseSide.ability.equals("ハードロック") || defenseSide.ability.equals("フィルター"))) {
                initValue = Math.round(initValue.times(3072.0).div(4096.0)).toDouble()
            }

            //ToDo: メトロノーム initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble() 4096 から 819 ずつ上がる。6回目以降：8196

            if (batsugun < 1 && attackSide.item.equals("たつじんのおび")) {
                initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
            }

            if (attackSide.item.equals("いのちのたま")) {
                initValue = Math.round(initValue.times(5324.0).div(4096.0)).toDouble()
            }

            if (1 < batsugun) {
                when (attackSide.skill.type) {
                    Type.no(Type.Code.FIRE) -> if (defenseSide.item.equals("オッカのみ")) initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                    Type.no(Type.Code.WATER) -> if (defenseSide.item.equals("イトケのみ")) initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                    Type.no(Type.Code.ELECTRIC) -> if (defenseSide.item.equals("ソクノのみ")) initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                    Type.no(Type.Code.GRASS) -> if (defenseSide.item.equals("リンドのみ")) initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                    Type.no(Type.Code.ICE) -> if (defenseSide.item.equals("ヤチェのみ")) initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                    Type.no(Type.Code.FIGHTING) -> if (defenseSide.item.equals("ヨプのみ")) initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                    Type.no(Type.Code.POISON) -> if (defenseSide.item.equals("ビアーのみ")) initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                    Type.no(Type.Code.GROUND) -> if (defenseSide.item.equals("シュカのみ")) initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                    Type.no(Type.Code.FLYING) -> if (defenseSide.item.equals("バコウのみ")) initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                    Type.no(Type.Code.PSYCHIC) -> if (defenseSide.item.equals("ウタンのみ")) initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                    Type.no(Type.Code.BUG) -> if (defenseSide.item.equals("タンガのみ")) initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                    Type.no(Type.Code.ROCK) -> if (defenseSide.item.equals("ヨロギのみ")) initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                    Type.no(Type.Code.GHOST) -> if (defenseSide.item.equals("カシブのみ")) initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                    Type.no(Type.Code.DRAGON) -> if (defenseSide.item.equals("ハバンのみ")) initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                    Type.no(Type.Code.DARK) -> if (defenseSide.item.equals("ナモのみ")) initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                    Type.no(Type.Code.STEEL) -> if (defenseSide.item.equals("リリバのみ")) initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                    Type.no(Type.Code.FAIRY) -> if (defenseSide.item.equals("ロゼルのみ")) initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                }
            }
            if (attackSide.skill.type == Type.no(Type.Code.NORMAL) && defenseSide.item.equals("ホズのみ")) {
                initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
            }

//            　×　8192（0x2000）　÷　4096（0x1000）　→　四捨五入
//            　　ふみつけ（ちいさくなる）
//            　　じしん（あなをほる）
//            　　なみのり（ダイビング）
//            　　ハードローラー（ちいさくなる）
//            　　ドラゴンダイブ（ちいさくなる）
//            　　ゴーストダイブ（ちいさくなる）
//            　　シャドーダイブ（ちいさくなる）
//            　　フライングプレス（ちいさくなる）
//            　　のしかかり（ちいさくなる）：

            return initValue
        }

        fun simulateTurn(baseFirst: PokemonForBattle, baseSecond: PokemonForBattle, field: BattleField, h: Int, bd: Int): BattleResult {
            val result = BattleResult()
            baseSecond.defenseEffortValue = bd
            baseSecond.specialDefenseEffortValue = bd
            val damage = doSkill(baseFirst, baseSecond, field, true, false)
            baseSecond.hpEffortValue = h
            for (d in damage) result.updateDefeatTimes(baseSecond.defeatTimes(d.key), d.value)
            return result
        }

        fun doSkill(attackSide: PokemonForBattle, defenseSide: PokemonForBattle, field: BattleField,
                    first: Boolean, damaged: Boolean): Map<Int, Double> {
            var attackValue = 0.0
            var attackValueCorrection = 0.0
            var defenseValue = 0.0
            var defenseValueCorrection = 0.0
            var attackRankCorrectionB = 0.0
            var defenseRankCorrectionB = 0.0
            when (attackSide.skill.category) {
                0 -> {
                    attackValue = attackSide.calcAttackValue()
                    attackValueCorrection = attackSide.calcAttackValueCorrection(defenseSide)
                    attackRankCorrectionB = attackSide.getAttackRankCorrection(false)
                    defenseValue = defenseSide.calcDefenseValue()
                    defenseValueCorrection = defenseSide.calcDefenseValueCorrection(attackSide)
                    defenseRankCorrectionB = defenseSide.getDefenseRankCorrection(false)
                }
                1 -> {
                    attackValue = attackSide.calcSpecialAttackValue()
                    attackValueCorrection = attackSide.calcSpecialAttackValueCorrection(defenseSide)
                    attackRankCorrectionB = attackSide.getSpecialAttackRankCorrection(false)
                    defenseValue = defenseSide.calcSpecialDefenseValue()
                    defenseValueCorrection = defenseSide.calcSpecialDefenseValueCorrection()
                    defenseRankCorrectionB = defenseSide.getSpecialDefenseRankCorrection(false)
                }
            }

            val skillPower = calcSkillPower(attackSide, defenseSide, field, first, damaged)

            //Log.v(attackSide.individual.master.jname + "${attackSide.side}", "${attackValue},${attackValueCorrection},${attackRankCorrectionB}")
            //Log.v(defenseSide.individual.master.jname + "${defenseSide.side}", "${defenseValue},${defenseValueCorrection},${defenseRankCorrectionB}")

            attackValue = calcAttackValue(attackValue, attackValueCorrection, attackRankCorrectionB, attackSide)
            defenseValue = calcDefenseValue(defenseValue, defenseValueCorrection, defenseRankCorrectionB, attackSide, defenseSide)

            val damage = Math.floor(Math.floor(22.times(skillPower).times(attackValue).div(defenseValue)).toDouble().div(50.0).plus(2.0))
            //TODO * 3072 / 4096 五捨五超入 複数ダメージ補正
            //TODO * 2048 / 4096 五捨五超入 おやこあい2回目
            //TODO * 6144 / 4096 五捨五超入 天候強化
            //TODO * 2048 / 4096 五捨五超入 天候弱化

            val randomDamage = arrayOf(
                    Math.floor(damage.times(0.85)),
                    Math.floor(damage.times(0.86)),
                    Math.floor(damage.times(0.87)),
                    Math.floor(damage.times(0.88)),
                    Math.floor(damage.times(0.89)),
                    Math.floor(damage.times(0.90)),
                    Math.floor(damage.times(0.91)),
                    Math.floor(damage.times(0.92)),
                    Math.floor(damage.times(0.93)),
                    Math.floor(damage.times(0.94)),
                    Math.floor(damage.times(0.95)),
                    Math.floor(damage.times(0.96)),
                    Math.floor(damage.times(0.97)),
                    Math.floor(damage.times(0.98)),
                    Math.floor(damage.times(0.99)),
                    Math.floor(damage))

            val damageCorrectionB = calcDamageCorrection(attackSide, defenseSide, field, false)
            for (i in 0..(randomDamage.size - 1)) {
                randomDamage[i] = Math.ceil(randomDamage[i].times(attackSide.typeBonus()).minus(0.5))
                randomDamage[i] = Math.floor(randomDamage[i].times(Type.calculateAffinity(Type.code(attackSide.skill.type), defenseSide.individual.master)))
                if (attackSide.skill.category == 0 && attackSide.status == StatusAilment.no(StatusAilment.Code.BURN)) {
                    randomDamage[i] = Math.floor(randomDamage[i].times(0.5))
                }
                randomDamage[i] = Math.ceil(randomDamage[i].times(damageCorrectionB).div(4096).minus(0.5))
                if (randomDamage[i] < 1) {
                    randomDamage[i] = 1.0
                }
            }

            val result = mutableMapOf<Int, Double>()
            for ((i, value)in randomDamage.withIndex()) {
                val k = value.toInt()
                if (result.containsKey(k)) {
                    result[k] = result[k]!!.plus(1)
                } else {
                    result[k] = 1.0
                }
            }
            return result
        }
    }
}
