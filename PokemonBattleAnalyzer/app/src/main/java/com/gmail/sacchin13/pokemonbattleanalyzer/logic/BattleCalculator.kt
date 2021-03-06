package com.gmail.sacchin13.pokemonbattleanalyzer.logic

import android.os.AsyncTask
import android.util.Log
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.*
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.realm.ZSkill
import com.gmail.sacchin13.pokemonbattleanalyzer.util.Util

class BattleCalculator(
        val level: Int = 50,
        val mine: PokemonForBattle,
        val opponent: PokemonForBattle,
        val field: BattleField,
        val listener: EventListener
) : AsyncTask<Void, Void, BattleResult>() {

    val notCompatibleWords = arrayOf(
            "れんぞくぎり", "ころがる", "エコーボイス", "りんしょう",
            "たつまき", "かぜおこし", "きりふだ", "ちかい系", "なげつける",
            "ふくろだだき", "ダメおし", "しぜんのめぐみ", "ウェザーボール",
            "はきだす", "おいうち", "ふみつけ", "じしん", "なみのり",
            "ハードローラー", "ドラゴンダイブ", "ゴーストダイブ", "シャドーダイブ",
            "フライングプレス", "のしかかり")

    interface EventListener {
        fun onFinish(result: BattleResult)
    }

    override fun onPostExecute(result: BattleResult) {
        listener.onFinish(result)
    }

    override fun doInBackground(vararg params: Void?): BattleResult {
        return calc()
    }

    fun calc(): BattleResult {
        val result = BattleResult()

        result.coverRate = 0.0
        loop@ for (item in opponent.damageRelatedItem()) {
            if (item.name.isNullOrEmpty() || item.usageRate < 0.01) continue
            for (tokusei in opponent.abilityTrend()) {
                if (tokusei.name.isNullOrEmpty() || tokusei.usageRate < 0.01) continue
                for (seikaku in opponent.characteristicTrend()) {
                    if (seikaku.name.isNullOrEmpty() || seikaku.usageRate < 0.01) continue
                    val rate = item.usageRate.times(tokusei.usageRate.times(seikaku.usageRate)).toDouble()
                    result.coverRate = result.coverRate.plus(rate)

                    opponent.item = item.name
                    opponent.ability = tokusei.name
                    opponent.characteristic = seikaku.name

                    result.add(oppoAttack(rate, opponent, mine, field))

                    if (0.9 < result.coverRate) break@loop
                }
            }
        }

        return result
    }

    fun givenDamage(mine: PokemonForBattle, opponent: PokemonForBattle, field: BattleField): BattleResult {
        val result = BattleResult()

        result.coverRate = 0.0
        loop@ for (item in opponent.itemTrend()) {
            if (item.name.isNullOrEmpty() || item.usageRate < 0.01) continue
            for (tokusei in opponent.abilityTrend()) {
                if (tokusei.name.isNullOrEmpty() || tokusei.usageRate < 0.01) continue
                for (seikaku in opponent.characteristicTrend()) {
                    if (seikaku.name.isNullOrEmpty() || seikaku.usageRate < 0.01) continue
                    val rate = item.usageRate.times(tokusei.usageRate.times(seikaku.usageRate)).toDouble()
                    result.coverRate = result.coverRate.plus(rate)
                    opponent.item = item.name
                    opponent.ability = tokusei.name
                    opponent.characteristic = seikaku.name
                    result.add(myAttack(rate, mine, opponent, field))

                    //if (0.95 < result.coverRate) break@loop
                }
            }
        }
        result.calcBreakMigawari(opponent.migawari, mine.skill.jname)
        return result
    }

    fun myAttack(baseRate: Double, mine: PokemonForBattle, opponent: PokemonForBattle, field: BattleField): BattleResult {
        val result = BattleResult()
        if (mine.skill.category == 2) return result else result.didAttack = true
        Log.e("myAttack", "${mine.skill.jname}(${mine.skill.category}):" + mine.name() + "(${mine.ability()}) vs " + opponent.name() + "(${opponent.ability()})")

        opponent.individualForUI.defenseEv = 252
        opponent.individualForUI.specialDefenseEv = 252
        val d252 = doSkill(mine, opponent, field, mine.calcCriticalRate(), true, false)

        opponent.individualForUI.hpEv = 252
        //H=252, B(or D)=252の場合の確定数
        for ((key, value) in d252) result.updateDefeatTimes(opponent.defeatTimes(key), baseRate.times(value))

        if (result.blow(baseRate)) {
            println("H and B(or D)=252: blow!!")
            return result
        }

        opponent.individualForUI.defenseEv = 0
        opponent.individualForUI.specialDefenseEv = 0
        val d0 = doSkill(mine, opponent, field, mine.calcCriticalRate(), true, false)
        //H=252, B(or D)が0の場合の確定数
        for ((key, value) in d0) result.updateDefeatTimes(opponent.defeatTimes(key), baseRate.times(value))

        opponent.individualForUI.hpEv = 0
        //H=0, B(or D)が0の場合の確定数
        for ((key, value) in d0) result.updateDefeatTimes(opponent.defeatTimes(key), baseRate.times(value))

        if (result.little()) {
            println("H and B(or D)=0: can not blow!!")
            return result
        }

        //H=0, B(or D)が252の場合の確定数
        for ((key, value) in d252) result.updateDefeatTimes(opponent.defeatTimes(key), baseRate.times(value))

        return result
    }


    fun oppoAttack(baseRate: Double, opponent: PokemonForBattle, mine: PokemonForBattle, field: BattleField): BattleResult {
        val result = BattleResult()
        Log.v("oppoAttack", "${opponent.individualForUI.master.jname}(${opponent.attackRank},${opponent.defenseRank}," +
                "${opponent.specialAttackRank},${opponent.specialDefenseRank},${opponent.speedRank})")
        Log.v("oppoAttack", "${mine.individualForUI.master.jname}(${mine.attackRank},${mine.defenseRank}," +
                "${mine.specialAttackRank},${mine.specialDefenseRank},${mine.speedRank})")


        for (skill in opponent.trend.skillList) {
            opponent.skill = skill.skill

            if (ZSkill.zCrystal(opponent.item)) {
                if (ZSkill.satisfyZSkill(opponent.item, opponent.skill))
                //Log.v("oppoAttack", "has ${opponent.item}, calc ${skill.skill.jname}(${skill.skill.power})")
                else
                    continue
            }
            Log.v("oppoAttack", "${skill.skill.jname}(${opponent.item}, ${opponent.ability}, ${opponent.characteristic})")

            opponent.individualForUI.attackEv = 0
            opponent.individualForUI.specialAttackEv = 0
            val d0 = doSkill(opponent, mine, field, mine.calcCriticalRate(), true, false)
            for ((damage, rate) in d0) result.updateDefeatedTimes(skill.skill.jname, damage, mine.defeatTimes(damage), baseRate.times(rate))

            if (result.blow(baseRate)) {
                println("blow!!")
                return result
            }

            opponent.individualForUI.attackEv = 252
            opponent.individualForUI.specialAttackEv = 252
            val d252 = doSkill(opponent, mine, field, mine.calcCriticalRate(), true, false)
            for ((damage, rate) in d252) result.updateDefeatedTimes(skill.skill.jname, damage, mine.defeatTimes(damage), baseRate.times(rate))
        }

        return result
    }

    fun doSkill(attackSide: PokemonForBattle, defenseSide: PokemonForBattle, field: BattleField,
                criticalRate: Double, first: Boolean, damaged: Boolean): MutableMap<Int, Double> {
        if (defenseSide.noEffect(attackSide.skill, attackSide, field)) {
            //Log.v("doSkill", "${attackSide.skill.jname} to ${defenseSide.individualForUI.master.jname} is no effect !")
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
        var ignore = false
        when (attackSide.skill.category) {
            0 -> {
                attackValue = attackSide.calcAttackValue()
                val temp = attackSide.calcAttackValueCorrection(defenseSide, field)
                attackValueCorrection = temp.first
                ignore = temp.second
                attackRankCorrectionA = attackSide.getAttackRankCorrection(true)
                attackRankCorrectionB = attackSide.getAttackRankCorrection(false)
                defenseValue = defenseSide.calcDefenseValue()
                defenseValueCorrection = defenseSide.calcDefenseValueCorrection(attackSide)
                defenseRankCorrectionA = defenseSide.getDefenseRankCorrection(true)
                defenseRankCorrectionB = defenseSide.getDefenseRankCorrection(false)
            }
            1 -> {
                attackValue = attackSide.calcSpecialAttackValue()
                attackValueCorrection = attackSide.calcSpecialAttackValueCorrection(defenseSide, field)
                attackRankCorrectionA = attackSide.getSpecialAttackRankCorrection(true)
                attackRankCorrectionB = attackSide.getSpecialAttackRankCorrection(false)
                defenseValue = defenseSide.calcSpecialDefenseValue()
                defenseValueCorrection = defenseSide.calcSpecialDefenseValueCorrection(field)
                defenseRankCorrectionA = defenseSide.getSpecialDefenseRankCorrection(true)
                defenseRankCorrectionB = defenseSide.getSpecialDefenseRankCorrection(false)
            }
            2 -> {
                Log.v("doSkill", "${attackSide.skill.jname} is support skill !")
                return mutableMapOf(0 to 1.0)
            }
        }

        val skillPower = calcSkillPower(attackSide, defenseSide, field, first, damaged)

        val ac = calcAttackValue(attackValue, attackValueCorrection, attackRankCorrectionA, attackSide)
        val dc = calcDefenseValue(defenseValue, defenseValueCorrection, defenseRankCorrectionA, attackSide, defenseSide, field)

        var criticalDamage = Math.floor(Math.floor(levelValue().times(skillPower).times(ac).div(dc)).toDouble().div(50.0).plus(2.0))
        // 3072 / 4096 五捨五超入 複数ダメージ補正

        var parentalCriticalDamage = Util.round5(criticalDamage.times(2048).div(4096))

        val fieldCorrection = fieldCorrection(attackSide, field)
        criticalDamage = Util.round5(criticalDamage.times(fieldCorrection))
        criticalDamage = Math.floor(criticalDamage.times(1.5))

        val an = calcAttackValue(attackValue, attackValueCorrection, attackRankCorrectionB, attackSide)
        val dn = calcDefenseValue(defenseValue, defenseValueCorrection, defenseRankCorrectionB, attackSide, defenseSide, field)
        var damage = Math.floor(Math.floor(levelValue().times(skillPower).times(an).div(dn)).toDouble().div(50.0).plus(2.0))
        // 3072 / 4096 五捨五超入 複数ダメージ補正
        var parentalDamage = Util.round5(damage.times(2048).div(4096))
        damage = Util.round5(damage.times(fieldCorrection))


        val randomize = arrayOf(0.85, 0.86, 0.87, 0.88, 0.89, 0.90, 0.91, 0.92, 0.93, 0.94, 0.95, 0.96, 0.97, 0.98, 0.99, 1.0)
        val randomDamage = randomize.map { num -> Math.floor(criticalDamage.times(num)) }.plus(randomize.map { num -> Math.floor(damage.times(num)) }).toTypedArray()

        val damageCorrectionA = calcDamageCorrection(attackSide, defenseSide, field, true)
        val damageCorrectionB = calcDamageCorrection(attackSide, defenseSide, field, false)
        println("${attackSide.individualForUI.master.jname}(${attackSide.skill.jname}), $attackValue, $attackValueCorrection, $defenseValue, $defenseValueCorrection, $attackRankCorrectionA, " +
                "$defenseRankCorrectionA, $attackRankCorrectionB, $defenseRankCorrectionB, $damageCorrectionA, $damageCorrectionB, $criticalDamage, $damage, $criticalRate, $fieldCorrection, ${levelValue()} * $skillPower * $an / $dn")

        for ((i, value) in randomDamage.withIndex()) {
            var v = value
            val bonus = attackSide.typeBonus()
            v = Util.round5(v.times(bonus.second))
            v = Math.floor(v.times(Type.calculateAffinity(Type.code(bonus.first),
                    Type.code(defenseSide.type1()), Type.code(defenseSide.type2()))))
            if (ignore.not() && attackSide.skill.category == 0 && attackSide.status == StatusAilment.no(StatusAilment.Code.BURN)) {
                v = Math.floor(v.times(0.5))
            }
            v = Util.round5(v.times(if (i < 16) damageCorrectionA else damageCorrectionB).div(4096))
            randomDamage[i] = if (v < 1) 1.0 else v
        }

        var parentalRandomDamage = arrayOf<Double>()
        if (attackSide.ability() == "おやこあい") {
            parentalCriticalDamage = Util.round5(parentalCriticalDamage.times(fieldCorrection))
            parentalCriticalDamage = Math.floor(parentalCriticalDamage.times(1.5))
            parentalDamage = Util.round5(parentalDamage.times(fieldCorrection))
            parentalRandomDamage = randomize.map { num -> Math.floor(parentalCriticalDamage.times(num)) }.plus(randomize.map { num -> Math.floor(parentalDamage.times(num)) }).toTypedArray()
            for ((i, value) in parentalRandomDamage.withIndex()) {
                var v = value
                val bonus = attackSide.typeBonus()
                v = Util.round5(v.times(bonus.second))
                v = Math.floor(v.times(Type.calculateAffinity(Type.code(bonus.first),
                        Type.code(defenseSide.type1()), Type.code(defenseSide.type2()))))
                if (ignore.not() && attackSide.skill.category == 0 && attackSide.status == StatusAilment.no(StatusAilment.Code.BURN)) {
                    v = Math.floor(v.times(0.5))
                }
                v = Util.round5(v.times(if (i < 16) damageCorrectionA else damageCorrectionB).div(4096))
                parentalRandomDamage[i] = if (v < 1) 1.0 else v
            }
        }

        val r = criticalRate.div(16.0)
        val notr = 1.minus(criticalRate).div(16.0)

        val result = mutableMapOf<Int, Double>()
        for ((i, d) in randomDamage.withIndex()) {
            val damageKey = d.toInt()
            if (attackSide.ability() == "おやこあい") {
                for ((j, pd) in parentalRandomDamage.withIndex()) {
                    val k = damageKey + pd.toInt()

                    val rate = if (i < 16 && j < 16) {
                        r.times(r)
                    } else if (16 <= i && 16 <= j) {
                        notr.times(notr)
                    } else {
                        r.times(notr)
                    }

                    result[k] = if (result.containsKey(k)) result[k]!!.plus(rate) else rate
                }
            } else {
                val rate = if (i < 16) r else notr
                result[damageKey] = if (result.containsKey(damageKey)) result[damageKey]!!.plus(rate) else rate
            }
        }

        return result
    }

    fun fieldCorrection(attackSide: PokemonForBattle, field: BattleField): Double {
        var initValue = 1.0

        if (attackSide.skill.type == Type.no(Type.Code.FIRE) && field.weather == BattleField.Weather.Sunny) {
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }
        if (attackSide.skill.type == Type.no(Type.Code.WATER) && field.weather == BattleField.Weather.Sunny) {
            initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
        }

        if (attackSide.skill.type == Type.no(Type.Code.WATER) && field.weather == BattleField.Weather.Rainy) {
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }
        if (attackSide.skill.type == Type.no(Type.Code.FIRE) && field.weather == BattleField.Weather.Rainy) {
            initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
        }

        if (attackSide.skill.jname == "ソーラービーム" && (field.weather == BattleField.Weather.Rainy ||
                field.weather == BattleField.Weather.Hailstone || field.weather == BattleField.Weather.Sandstorm)) {
            initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
        }

        return initValue
    }

    fun calcAttackValue(base: Double, attackValueCorrection: Double, attackRankCorrection: Double, attackSide: PokemonForBattle): Double {
        var result = Math.floor(base.times(attackRankCorrection))
        if (attackSide.skill.category == 0 && attackSide.ability() == "はりきり") {
            result = Util.round5(result.times(1.5))
        }
        result = Util.round5(result.times(attackValueCorrection).div(4096.0))
        return if (result < 1) 1.0 else result
    }

    fun calcDefenseValue(base: Double, defenseValueCorrection: Double, defenseRankCorrection: Double, attackSide: PokemonForBattle, defenseSide: PokemonForBattle, field: BattleField): Double {
        var result = Math.floor(base.times(defenseRankCorrection))
        if (attackSide.skill.category == 1 && field.weather == BattleField.Weather.Sandstorm &&
                (defenseSide.type1() == Type.no(Type.Code.ROCK) || defenseSide.type2() == Type.no(Type.Code.ROCK))) {
            result = Util.round5(result.times(1.5))
        }

        return Util.round5(result.times(defenseValueCorrection).div(4096.0))
    }

    fun calcSkillPowerCorrection(attackSide: PokemonForBattle, defenseSide: PokemonForBattle, field: BattleField, first: Boolean): Double {
        var initValue = 4096.0

        // とうそうしん弱化,オーラブレイク initValue.times(3042).div(4096).toInt()
        if ((attackSide.skill.jname == "れんぞくパンチ" || attackSide.skill.jname == "メガトンパンチ" || attackSide.skill.jname == "ほのおのパンチ" || attackSide.skill.jname == "れいとうパンチ" || attackSide.skill.jname == "かみなりパンチ" || attackSide.skill.jname == "ピヨピヨパンチ" ||
                attackSide.skill.jname == "マッハパンチ" || attackSide.skill.jname == "ばくれつパンチ" || attackSide.skill.jname == "きあいパンチ" || attackSide.skill.jname == "コメットパンチ" || attackSide.skill.jname == "シャドーパンチ" || attackSide.skill.jname == "スカイアッパー" ||
                attackSide.skill.jname == "アームハンマー" || attackSide.skill.jname == "バレットパンチ" || attackSide.skill.jname == "ドレインパンチ") && attackSide.ability() == "てつのこぶし") {
            initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
        }
        if ((attackSide.skill.jname == "すてみタックル" || attackSide.skill.jname == "ウッドハンマー" || attackSide.skill.jname == "ブレイブバード" || attackSide.skill.jname == "とっしん" || attackSide.skill.jname == "じごくぐるま" || attackSide.skill.jname == "ボルテッカー" ||
                attackSide.skill.jname == "フレアドライブ" || attackSide.skill.jname == "もろはのずつき" || attackSide.skill.jname == "とびげり" || attackSide.skill.jname == "とびひざげり") && attackSide.ability() == "すてみ") {
            initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
        }
        // とうそうしん強化 initValue.times(5120).div(4096).toInt()
        if (attackSide.sheerForce()) {
            initValue = Math.round(initValue.times(5325.0).div(4096.0)).toDouble()
        }
        if (attackSide.ability() == "すなのちから" && field.weather == BattleField.Weather.Sandstorm &&
                (attackSide.skill.type == Type.no(Type.Code.ROCK) || attackSide.skill.type == Type.no(Type.Code.STEEL) || attackSide.skill.type == Type.no(Type.Code.GROUND))) {
            initValue = Math.round(initValue.times(5325.0).div(4096.0)).toDouble()
        }
        if (attackSide.ability() == "スカイスキン" && attackSide.skill.type == Type.no(Type.Code.NORMAL)) {
            initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
        }
        if (attackSide.ability() == "フェアリースキン" && attackSide.skill.type == Type.no(Type.Code.NORMAL)) {
            initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
        }
        if (attackSide.ability() == "フリーズスキン" && attackSide.skill.type == Type.no(Type.Code.NORMAL)) {
            initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
        }
        if (attackSide.ability() == "エレキスキン" && attackSide.skill.type == Type.no(Type.Code.NORMAL)) {
            initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
        }
        if (attackSide.ability() == "かたいツメ" && attackSide.skill.contact) {
            initValue = Math.round(initValue.times(5325.0).div(4096.0)).toDouble()
        }
        if (attackSide.ability() == "アナライズ" && !first) {
            initValue = Math.round(initValue.times(5325.0).div(4096.0)).toDouble()
        }
        //5448 / 4096 四捨五入(※2体以上いても補正は1回のみ) フェアリーオーラ,ダークオーラ
        if (attackSide.ability() == "ねつぼうそう" && attackSide.status == StatusAilment.no(StatusAilment.Code.BURN)) {
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }
        if (attackSide.ability() == "どくぼうそう" && (attackSide.status == StatusAilment.no(StatusAilment.Code.POISON) || attackSide.status == StatusAilment.no(StatusAilment.Code.BADPOISON))) {
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }
        if ((attackSide.skill.jname == "かみつく" || attackSide.skill.jname == "かみくだく" || attackSide.skill.jname == "かみなりのキバ" || attackSide.skill.jname == "ほのおのキバ" ||
                attackSide.skill.jname == "こおりのキバ" || attackSide.skill.jname == "どくどくのキバ" || attackSide.skill.jname == "ひっさつまえば") && attackSide.ability() == "がんじょうあご") {
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }
        if ((attackSide.skill.jname == "はどうだん" || attackSide.skill.jname == "みずのはどう" || attackSide.skill.jname == "りゅうのはどう" ||
                attackSide.skill.jname == "あくのはどう" || attackSide.skill.jname == "こんげんのはどう") && attackSide.ability() == "メガランチャー") {
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }
        if ((attackSide.skill.power <= 60) && attackSide.ability() == "テクニシャン") {
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }
        if (Type.code(attackSide.skill.type) == Type.Code.FIRE && defenseSide.ability() == "たいねつ") {
            initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
        }
        if (Type.code(attackSide.skill.type) == Type.Code.FIRE && defenseSide.ability() == "かんそうはだ") {
            initValue = Math.round(initValue.times(5120.0).div(4096.0)).toDouble()
        }
        if (attackSide.skill.category == 0 && attackSide.item() == "ちからのハチマキ") {
            initValue = Math.round(initValue.times(4505.0).div(4096.0)).toDouble()
        }
        if (attackSide.skill.category == 1 && attackSide.item() == "ものしりメガネ") {
            initValue = Math.round(initValue.times(4505.0).div(4096.0)).toDouble()
        }
        //こうんごうだま、しらたま、はっきんだま対応
        if (Type.code(attackSide.skill.type) == Type.Code.NORMAL && attackSide.item() == "シルクのスカーフ") {
            initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
        }
        if (Type.code(attackSide.skill.type) == Type.Code.FLYING && (attackSide.item() == "あおぞらプレート" || attackSide.item() == "するどいくちばし")) {
            initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
        }
        if (Type.code(attackSide.skill.type) == Type.Code.ELECTRIC && (attackSide.item() == "いかずちプレート" || attackSide.item() == "じしゃく")) {
            initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
        }
        if (Type.code(attackSide.skill.type) == Type.Code.ROCK && (attackSide.item() == "がんせきプレート" || attackSide.item() == "かたいいし" || attackSide.item() == "がんせきおこう")) {
            initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
        }
        if (Type.code(attackSide.skill.type) == Type.Code.STEEL && (attackSide.item() == "こうてつプレート" || attackSide.item() == "メタルコート")) {
            initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
        }
        if (Type.code(attackSide.skill.type) == Type.Code.FIGHTING && (attackSide.item() == "こぶしのプレート" || attackSide.item() == "くろおび")) {
            initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
        }
        if (Type.code(attackSide.skill.type) == Type.Code.DARK && (attackSide.item() == "こわもてプレート" || attackSide.item() == "くろいメガネ")) {
            initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
        }
        if (Type.code(attackSide.skill.type) == Type.Code.WATER && (attackSide.item() == "しずくプレート" || attackSide.item() == "しんぴのしずく" || attackSide.item() == "うしおのおこう" || attackSide.item() == "さざなみのおこう")) {
            initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
        }
        if (Type.code(attackSide.skill.type) == Type.Code.GROUND && (attackSide.item() == "だいちのプレート" || attackSide.item() == "やわらかいすな")) {
            initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
        }
        if (Type.code(attackSide.skill.type) == Type.Code.BUG && (attackSide.item() == "たまむしプレート" || attackSide.item() == "ぎんのこな")) {
            initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
        }
        if (Type.code(attackSide.skill.type) == Type.Code.ICE && (attackSide.item() == "つららのプレート" || attackSide.item() == "とけないこおり")) {
            initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
        }
        if (Type.code(attackSide.skill.type) == Type.Code.FIRE && (attackSide.item() == "ひのたまプレート" || attackSide.item() == "もくたん")) {
            initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
        }
        if (Type.code(attackSide.skill.type) == Type.Code.PSYCHIC && (attackSide.item() == "ふしぎのプレート" || attackSide.item() == "まがったスプーン" || attackSide.item() == "あやしいおこう")) {
            initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
        }
        if (Type.code(attackSide.skill.type) == Type.Code.GRASS && (attackSide.item() == "みどりのプレート" || attackSide.item() == "きせきのタネ" || attackSide.item() == "おはなのおこう")) {
            initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
        }
        if (Type.code(attackSide.skill.type) == Type.Code.POISON && (attackSide.item() == "もうどくプレート" || attackSide.item() == "どくバリ")) {
            initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
        }
        if (Type.code(attackSide.skill.type) == Type.Code.GHOST && (attackSide.item() == "もののけプレート" || attackSide.item() == "のろいのおふだ")) {
            initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
        }
        if (Type.code(attackSide.skill.type) == Type.Code.DRAGON && (attackSide.item() == "りゅうのプレート" || attackSide.item() == "りゅうのキバ")) {
            initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
        }
        //5325 / 4096 四捨五入 ジュエル
        if (attackSide.skill.jname == "ソーラービーム" && field.weather == BattleField.Weather.Rainy) {
            initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
        }
        //TODO 6144 / 4096 四捨五入 さきどり はたきおとす
        //8192 / 4096 四捨五入クロスサンダー,クロスフレイム,じゅうでん,かたきうち
        if (attackSide.skill.jname == "からげんき" && attackSide.status != StatusAilment.no(StatusAilment.Code.UNKNOWN)) {
            initValue = Math.round(initValue.times(8192.0).div(4096.0)).toDouble()
        }
        if (attackSide.skill.jname == "ベノムショック" && (defenseSide.status != StatusAilment.no(StatusAilment.Code.POISON) || defenseSide.status != StatusAilment.no(StatusAilment.Code.BADPOISON))) {
            initValue = Math.round(initValue.times(8192.0).div(4096.0)).toDouble()
        }
        if (attackSide.skill.jname == "しおみず" && defenseSide.hpRatio < 50) {
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

        if ((attackSide.skill.jname == "じならし" || attackSide.skill.jname == "じしん" || attackSide.skill.jname == "マグニチュード") &&
                (field.terrain == BattleField.Terrain.GrassyTerrain && defenseSide.floating().not())) {
            initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
        }
        if (attackSide.skill.type == Type.no(Type.Code.GRASS) && field.terrain == BattleField.Terrain.GrassyTerrain &&
                attackSide.floating().not()) {
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }
        if (attackSide.skill.type == Type.no(Type.Code.ELECTRIC) && field.terrain == BattleField.Terrain.ElectricTerrain &&
                attackSide.floating().not()) {
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }
        if (attackSide.skill.type == Type.no(Type.Code.DRAGON) && field.terrain == BattleField.Terrain.MistyTerrain &&
                defenseSide.floating().not()) {
            initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
        }
        if (attackSide.skill.type == Type.no(Type.Code.PSYCHIC) && field.terrain == BattleField.Terrain.PsycoTerrain &&
                attackSide.floating().not()) {
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }

        return initValue
    }

    fun calcSkillPower(attackSide: PokemonForBattle, defenseSide: PokemonForBattle, field: BattleField, first: Boolean, damaged: Boolean): Int {
        //first と damagedを利用して技の威力を計算するもののみ、attackSide.determineSkillPower(defenseSide)の外側で計算
        var skillPower = attackSide.determineSkillPower(defenseSide)
        if ((attackSide.skill.jname == "しっぺがえし" || attackSide.ability() == "アナライズ") && !first) {
            skillPower = skillPower.times(2)
        }
        if (attackSide.skill.jname == "ゆきなだれ" || attackSide.skill.jname == "リベンジ" && damaged) {
            skillPower = skillPower.times(2)
        }

        val skillCorrection = calcSkillPowerCorrection(attackSide, defenseSide, field, first)
        val tmp = Util.round5(skillPower.toDouble().times(skillCorrection).div(4096.0)).toInt()
        return if (tmp < 1) 1 else tmp
    }

    fun calcDamageCorrection(attackSide: PokemonForBattle, defenseSide: PokemonForBattle, field: BattleField, isCritical: Boolean): Double {
        var initValue = 4096.0

        if (attackSide.skill.category == 0 && field.defenseSide.contains(BattleField.Field.Reflect)) {
            initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
        }
        if (attackSide.skill.category == 1 && field.defenseSide.contains(BattleField.Field.LightScreen)) {
            initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
        }
        if (defenseSide.hpRatio == 100 && defenseSide.ability() == "マルチスケイル") {
            initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
        }
        val batsugun = Type.calculateAffinity(Type.code(attackSide.skill.type), Type.code(defenseSide.type1()), Type.code(defenseSide.type2()))
        if (batsugun < 1 && attackSide.ability() == "いろめがね") {
            initValue = Math.round(initValue.times(8192.0).div(4096.0)).toDouble()
        }
        if (isCritical && attackSide.ability() == "スナイパー") {
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }
        //フレンドガード1 initValue = Math.round(initValue.times(3072.0).div(4096.0)).toDouble()
        //フレンドガード2 initValue = Math.round(initValue.times(2304.0).div(4096.0)).toDouble()
        if (batsugun < 1 && (defenseSide.ability() == "ハードロック" || defenseSide.ability() == "フィルター")) {
            initValue = Math.round(initValue.times(3072.0).div(4096.0)).toDouble()
        }
        //ToDo: メトロノーム initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble() 4096 から 819 ずつ上がる。6回目以降：8196
        if (batsugun < 1 && attackSide.item() == "たつじんのおび") {
            initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
        }
        if (attackSide.item() == "いのちのたま") {
            initValue = Math.round(initValue.times(5324.0).div(4096.0)).toDouble()
        }
        if (1 < batsugun) {
            when (attackSide.skill.type) {
                Type.no(Type.Code.FIRE) -> if (defenseSide.item() == "オッカのみ") initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                Type.no(Type.Code.WATER) -> if (defenseSide.item() == "イトケのみ") initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                Type.no(Type.Code.ELECTRIC) -> if (defenseSide.item() == "ソクノのみ") initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                Type.no(Type.Code.GRASS) -> if (defenseSide.item() == "リンドのみ") initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                Type.no(Type.Code.ICE) -> if (defenseSide.item() == "ヤチェのみ") initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                Type.no(Type.Code.FIGHTING) -> if (defenseSide.item() == "ヨプのみ") initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                Type.no(Type.Code.POISON) -> if (defenseSide.item() == "ビアーのみ") initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                Type.no(Type.Code.GROUND) -> if (defenseSide.item() == "シュカのみ") initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                Type.no(Type.Code.FLYING) -> if (defenseSide.item() == "バコウのみ") initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                Type.no(Type.Code.PSYCHIC) -> if (defenseSide.item() == "ウタンのみ") initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                Type.no(Type.Code.BUG) -> if (defenseSide.item() == "タンガのみ") initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                Type.no(Type.Code.ROCK) -> if (defenseSide.item() == "ヨロギのみ") initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                Type.no(Type.Code.GHOST) -> if (defenseSide.item() == "カシブのみ") initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                Type.no(Type.Code.DRAGON) -> if (defenseSide.item() == "ハバンのみ") initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                Type.no(Type.Code.DARK) -> if (defenseSide.item() == "ナモのみ") initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                Type.no(Type.Code.STEEL) -> if (defenseSide.item() == "リリバのみ") initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                Type.no(Type.Code.FAIRY) -> if (defenseSide.item() == "ロゼルのみ") initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
            }
        }
        if (attackSide.skill.type == Type.no(Type.Code.NORMAL) && defenseSide.item() == "ホズのみ") {
            initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
        }

        return initValue
    }

    fun doSkill(attackSide: PokemonForBattle, defenseSide: PokemonForBattle, field: BattleField,
                first: Boolean, damaged: Boolean): Map<Int, Double> {
        var attackValue = 0.0
        var attackValueCorrection = 0.0
        var defenseValue = 0.0
        var defenseValueCorrection = 0.0
        var attackRankCorrectionB = 0.0
        var defenseRankCorrectionB = 0.0
        var ignore = false
        when (attackSide.skill.category) {
            0 -> {
                attackValue = attackSide.calcAttackValue()
                val temp = attackSide.calcAttackValueCorrection(defenseSide, field)
                attackValueCorrection = temp.first
                ignore = temp.second
                attackRankCorrectionB = attackSide.getAttackRankCorrection(false)
                defenseValue = defenseSide.calcDefenseValue()
                defenseValueCorrection = defenseSide.calcDefenseValueCorrection(attackSide)
                defenseRankCorrectionB = defenseSide.getDefenseRankCorrection(false)
            }
            1 -> {
                attackValue = attackSide.calcSpecialAttackValue()
                attackValueCorrection = attackSide.calcSpecialAttackValueCorrection(defenseSide, field)
                attackRankCorrectionB = attackSide.getSpecialAttackRankCorrection(false)
                defenseValue = defenseSide.calcSpecialDefenseValue()
                defenseValueCorrection = defenseSide.calcSpecialDefenseValueCorrection(field)
                defenseRankCorrectionB = defenseSide.getSpecialDefenseRankCorrection(false)
            }
        }

        val skillPower = calcSkillPower(attackSide, defenseSide, field, first, damaged)

        //Log.v(attackSide.individual.master.jname + "${attackSide.side}", "${attackValue},${attackValueCorrection},${attackRankCorrectionB}")
        //Log.v(defenseSide.individual.master.jname + "${defenseSide.side}", "${defenseValue},${defenseValueCorrection},${defenseRankCorrectionB}")

        attackValue = calcAttackValue(attackValue, attackValueCorrection, attackRankCorrectionB, attackSide)
        defenseValue = calcDefenseValue(defenseValue, defenseValueCorrection, defenseRankCorrectionB, attackSide, defenseSide, field)

        var damage = Math.floor(Math.floor(levelValue().times(skillPower).times(attackValue).div(defenseValue)).toDouble().div(50.0).plus(2.0))
        //TODO * 3072 / 4096 五捨五超入 複数ダメージ補正
        //TODO * 2048 / 4096 五捨五超入 おやこあい2回目
        val fieldCorrection = fieldCorrection(attackSide, field)
        damage = Util.round5(damage.times(fieldCorrection))

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
            val bonus = attackSide.typeBonus()
            randomDamage[i] = Util.round5(randomDamage[i].times(bonus.second))
            randomDamage[i] = Math.floor(randomDamage[i].times(Type.calculateAffinity(Type.code(bonus.first),
                    Type.code(defenseSide.type1()), Type.code(defenseSide.type2()))))
            if (ignore.not() && attackSide.skill.category == 0 && attackSide.status == StatusAilment.no(StatusAilment.Code.BURN)) {
                randomDamage[i] = Math.floor(randomDamage[i].times(0.5))
            }
            randomDamage[i] = Util.round5(randomDamage[i].times(damageCorrectionB).div(4096))
            if (randomDamage[i] < 1) {
                randomDamage[i] = 1.0
            }
        }

        val result = mutableMapOf<Int, Double>()
        randomDamage.map { it -> result[it.toInt()] = result[it.toInt()]?.plus(1) ?: 1.0 }
        return result
    }

    fun levelValue(): Int {
        return Math.floor(2.times(level).div(5.0).plus(2)).toInt()
    }
}
