package com.gmail.sacchin13.pokemonbattleanalyzer.entity

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Rank.Value
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl.Info
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl.TrendForBattle
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.realm.IndividualPokemon
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.realm.MegaPokemonMasterData
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.realm.Skill
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.realm.ZSkill
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.ui.IndividualPokemonForUI
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.ui.SkillForUI
import kotlin.properties.Delegates

class PokemonForBattle(
        var side: Int,
        var status: Int,
        var item: String,
        var itemUsed: Boolean,
        var characteristic: String,
        var ability: String,
        var skill: SkillForUI,
        var hpRatio: Int,
        var hpValue: Int,
        var attackRank: Int,
        var defenseRank: Int,
        var specialAttackRank: Int,
        var specialDefenseRank: Int,
        var speedRank: Int,
        var hitProbabilityRank: Int,
        var avoidanceRank: Int,
        var criticalRank: Int,
        var migawari: Boolean,
        var mega: Int,
        var individual: IndividualPokemon = IndividualPokemon()
) {
    var trend: TrendForBattle by Delegates.notNull()
    var individualForUI: IndividualPokemonForUI by Delegates.notNull()

    companion object {
        const val UNKNOWN = -1
        const val NOT_CHANGED = "notchanged"

        fun mine(pokemon: () -> IndividualPokemon): PokemonForBattle = PokemonForBattle.create(PartyInBattle.MY_SIDE, pokemon.invoke())
        fun opponent(pokemon: () -> IndividualPokemon): PokemonForBattle = PokemonForBattle.create(PartyInBattle.OPPONENT_SIDE, pokemon.invoke())

        fun create(side: Int, individual: IndividualPokemon): PokemonForBattle {
            return PokemonForBattle(side, UNKNOWN, individual.item, false, individual.characteristic, individual.ability, SkillForUI(), 100, individual.hp,
                    Rank.no(Value.DEFAULT), Rank.no(Value.DEFAULT), Rank.no(Value.DEFAULT), Rank.no(Value.DEFAULT), Rank.no(Value.DEFAULT),
                    Rank.no(Value.DEFAULT), Rank.no(Value.DEFAULT), Rank.no(Value.DEFAULT), false, MegaPokemonMasterData.NOT_MEGA, individual)
        }
    }

    fun ready() {
        individualForUI = individual.uiObject()
    }

    fun Int.over(): Int {
        return if (0 < this) this else 0
    }

    fun abilityTrend(): List<Info> = trend.tokuseiInfo.filterNotNull()
    fun characteristicTrend(): List<Info> = trend.seikakuInfo.filterNotNull()
    fun damageRelatedItem(): List<Info> {
        val list = trend.itemInfo.filterNotNull().filter { it -> it.name != null && attackRelated(it.name) }
        return if (list.isEmpty()) listOf(Info()) else list
    }

    fun itemTrend(): List<Info> = trend.itemInfo.filterNotNull()

    fun attackRelated(item: String): Boolean {
        return (ZSkill.zCrystal(item) || item == "いのちのたま" || item == "こだわりメガネ" ||
                item == "こだわりハチマキ" || item == "ものしりメガネ" ||
                item == "ちからのハチマキ" || item == "たつじんのおび" ||
                item == "ふといホネ" || item == "こころのしずく" ||
                item == "メトロノーム" || item == "あおぞらプレート" ||
                item == "いかずちプレート" || item == "がんせきプレート" ||
                item == "こうてつプレート" || item == "こぶしのプレート" ||
                item == "こわもてプレート" || item == "しずくプレート" ||
                item == "だいちのプレート" || item == "たまむしプレート" ||
                item == "つららのプレート" || item == "ひのたまプレート" ||
                item == "ふしぎのプレート" || item == "みどりのプレート" ||
                item == "もうどくプレート" || item == "もののけプレート" ||
                item == "りゅうのプレート" || item == "はっきんだま" ||
                item == "しらたま" || item == "こんごうだま" ||
                item == "しんかいのキバ" || item == "しんかいのウロコ" ||
                item == "ながねぎ" || item == "ラッキーパンチ" ||
                item == "スピードパウダー" || item == "メタルパウダー" ||
                item == "もくたん" || item == "しんぴのしずく" ||
                item == "じしゃく" || item == "きせきのタネ" ||
                item == "とけないこおり" || item == "くろおび" ||
                item == "どくバリ" || item == "やわらかいすな" ||
                item == "するどいくちばし" || item == "まがったスプーン" ||
                item == "ぎんのこな" || item == "かたいいし" ||
                item == "のろいのおふだ" || item == "りゅうのキバ" ||
                item == "くろいメガネ" || item == "メタルコート")
    }

    fun defenseRelated(item: String): Boolean {
        return (item == "いのちのたま" || item == "こだわりメガネ" ||
                item == "こだわりハチマキ" || item == "ものしりメガネ" ||
                item == "ちからのハチマキ" || item == "たつじんのおび" ||
                item == "ふといホネ" || item == "こころのしずく" ||
                item == "メトロノーム" || item == "あおぞらプレート" ||
                item == "いかずちプレート" || item == "がんせきプレート" ||
                item == "こうてつプレート" || item == "こぶしのプレート" ||
                item == "こわもてプレート" || item == "しずくプレート" ||
                item == "だいちのプレート" || item == "たまむしプレート" ||
                item == "つららのプレート" || item == "ひのたまプレート" ||
                item == "ふしぎのプレート" || item == "みどりのプレート" ||
                item == "もうどくプレート" || item == "もののけプレート" ||
                item == "りゅうのプレート" || item == "はっきんだま" ||
                item == "しらたま" || item == "こんごうだま" ||
                item == "しんかいのキバ" || item == "しんかいのウロコ" ||
                item == "ながねぎ" || item == "ラッキーパンチ" ||
                item == "スピードパウダー" || item == "メタルパウダー" ||
                item == "もくたん" || item == "しんぴのしずく" ||
                item == "じしゃく" || item == "きせきのタネ" ||
                item == "とけないこおり" || item == "くろおび" ||
                item == "どくバリ" || item == "やわらかいすな" ||
                item == "するどいくちばし" || item == "まがったスプーン" ||
                item == "ぎんのこな" || item == "かたいいし" ||
                item == "のろいのおふだ" || item == "りゅうのキバ" ||
                item == "くろいメガネ" || item == "メタルコート")
    }

    fun hpValue(): Int {
        when (side) {
            PartyInBattle.MY_SIDE -> return hpValue
            PartyInBattle.OPPONENT_SIDE -> return individualForUI.calcHp(individualForUI.hpEv, mega).times(hpRatio).div(100.0).toInt()
        }
        return 0
    }

    fun hpRatio(): Int {
        when (side) {
            PartyInBattle.MY_SIDE -> return hpValue.times(100.0).div(individualForUI.calcHp(individualForUI.hpEv, mega)).toInt()
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
            val tmp = hpRatio().div(100f).times(48f)
            when {
                (0f <= tmp && tmp < 1f) -> return 200
                (1f <= tmp && tmp < 4f) -> return 150
                (4f <= tmp && tmp < 9f) -> return 100
                (9f <= tmp && tmp < 16f) -> return 80
                (16f <= tmp && tmp < 31f) -> return 40
                (31 <= tmp && tmp <= 48f) -> return 20
            }
        }
        if (skill.jname == "ふんか" && skill.jname == "しおふき" && (hpRatio() < 30)) {
            val r = hpRatio().toFloat().div(100f).times(150).toInt()
            return if (r < 1) 1 else r
        }
        if (skill.jname == "アクロバット") {
            return skill.power * 2
        }
        if (skill.jname == "アシストパワー") {
            return (attackRank.over() + defenseRank.over() + specialAttackRank.over() + specialDefenseRank.over() +
                    speedRank.over() + hitProbabilityRank.over() + avoidanceRank.over() + criticalRank.over()).times(20)
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
            val sum = attackRank.over() + defenseRank.over() + specialAttackRank.over() + specialDefenseRank.over() +
                    speedRank.over() + hitProbabilityRank.over() + avoidanceRank.over() + criticalRank.over()

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
            val tmp = (defenseSide.individualForUI.master.weight / individualForUI.master.weight).times(120)
            when {
                (tmp <= 24) -> return 120
                (24 < tmp && tmp <= 30) -> return 100
                (30 < tmp && tmp <= 40) -> return 80
                (40 < tmp && tmp <= 60) -> return 60
                (60 < tmp) -> return 40
            }
        }
        if (skill.jname == "けたぐり" || skill.jname == "くさむすび") {
            val tmp = defenseSide.individualForUI.master.weight
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
            val tmp = (25 * defenseSide.calcSpeedValue(temp, false, false) / calcSpeedValue(temp, false, false)) + 1
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
        if (item() == "こだわりハチマキ") {
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }
        if (item() == "でんきだま") {
            initValue = Math.round(initValue.times(8192.0).div(4096.0)).toDouble()
        }
        if (item() == "ふといホネ") {
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
        if (item() == "しんかいのキバ") {
            initValue = Math.round(initValue.times(8192.0).div(4096.0)).toDouble()
        }
        if (item() == "こころのしずく") {
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }
        if (item() == "こだわりメガネ") {
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }
        return initValue
    }

    private fun relatedBoth(initValue: Double, defenseSide: PokemonForBattle): Double {
        var result = initValue
        if (Type.code(skill.type) == Type.Code.FIRE && ability() == "もうか" && (hpRatio() < 30)) {
            result = Math.round(result.times(6144.0).div(4096.0)).toDouble()
        }
        if (Type.code(skill.type) == Type.Code.WATER && ability() == "げきりゅう" && (hpRatio() < 30)) {
            result = Math.round(result.times(6144.0).div(4096.0)).toDouble()
        }
        if (Type.code(skill.type) == Type.Code.GRASS && ability() == "しんりょく" && (hpRatio() < 30)) {
            result = Math.round(result.times(6144.0).div(4096.0)).toDouble()
        }
        if (Type.code(skill.type) == Type.Code.BUG && ability() == "むしのしらせ" && (hpRatio() < 30)) {
            result = Math.round(result.times(6144.0).div(4096.0)).toDouble()
        }
        if ((Type.code(skill.type) == Type.Code.FIRE || Type.code(skill.type) == Type.Code.ICE) && defenseSide.ability() == "あついしぼう") {
            result = Math.round(result.times(2048.0).div(4096.0)).toDouble()
        }
        return result
    }

    fun calcAttackValue(): Double {
        val a = if (side == PartyInBattle.MY_SIDE) {
            individualForUI.attack
        } else {
            individualForUI.calcAttack(individualForUI.attackEv, mega)
        }
        return Math.floor(a.times(Characteristic.correction(characteristic, "A")))
    }

    fun getAttackRankCorrection(isCritical: Boolean): Double {
        return when (Rank.value(attackRank)) {
            Value.N6 -> if (isCritical) 3.toDouble().div(3) else 3.toDouble().div(9)
            Value.N5 -> if (isCritical) 3.toDouble().div(3) else 3.toDouble().div(8)
            Value.N4 -> if (isCritical) 3.toDouble().div(3) else 3.toDouble().div(7)
            Value.N3 -> if (isCritical) 3.toDouble().div(3) else 3.toDouble().div(6)
            Value.N2 -> if (isCritical) 3.toDouble().div(3) else 3.toDouble().div(5)
            Value.N1 -> if (isCritical) 3.toDouble().div(3) else 3.toDouble().div(4)
            Value.P1 -> 4.toDouble().div(3)
            Value.P2 -> 5.toDouble().div(3)
            Value.P3 -> 6.toDouble().div(3)
            Value.P4 -> 7.toDouble().div(3)
            Value.P5 -> 8.toDouble().div(3)
            Value.P6 -> 9.toDouble().div(3)
            else -> 1.toDouble()
        }
    }

    fun calcDefenseValue(): Double {
        val d = if (side == PartyInBattle.MY_SIDE) {
            individualForUI.defense
        } else {
            individualForUI.calcDefense(individualForUI.defenseEv, mega)
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
        if (item() == "しんかのきせき") {
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }
        if (item() == "メタルパウダー") {
            initValue = Math.round(initValue.times(8192.0).div(4096.0)).toDouble()
        }

        return initValue
    }

    fun getDefenseRankCorrection(isCritical: Boolean): Double {
        return when (Rank.value(defenseRank)) {
            Value.N6 -> 3.toDouble().div(9)
            Value.N5 -> 3.toDouble().div(8)
            Value.N4 -> 3.toDouble().div(7)
            Value.N3 -> 3.toDouble().div(6)
            Value.N2 -> 3.toDouble().div(5)
            Value.N1 -> 3.toDouble().div(4)
            Value.P1 -> if (isCritical) 3.toDouble().div(3) else 4.toDouble().div(3)
            Value.P2 -> if (isCritical) 3.toDouble().div(3) else 5.toDouble().div(3)
            Value.P3 -> if (isCritical) 3.toDouble().div(3) else 6.toDouble().div(3)
            Value.P4 -> if (isCritical) 3.toDouble().div(3) else 7.toDouble().div(3)
            Value.P5 -> if (isCritical) 3.toDouble().div(3) else 8.toDouble().div(3)
            Value.P6 -> if (isCritical) 3.toDouble().div(3) else 9.toDouble().div(3)
            else -> 1.toDouble()
        }
    }

    fun calcSpecialAttackValue(): Double {
        val sa = if (side == PartyInBattle.MY_SIDE) {
            individualForUI.specialAttack
        } else {
            individualForUI.calcSpecialAttack(individualForUI.specialAttackEv, mega)
        }
        return Math.floor(sa.times(Characteristic.correction(characteristic, "C")))
    }

    fun getSpecialAttackRankCorrection(isCritical: Boolean): Double {
        return when (Rank.value(specialAttackRank)) {
            Value.N6 -> if (isCritical) 3.toDouble().div(3) else 3.toDouble().div(9)
            Value.N5 -> if (isCritical) 3.toDouble().div(3) else 3.toDouble().div(8)
            Value.N4 -> if (isCritical) 3.toDouble().div(3) else 3.toDouble().div(7)
            Value.N3 -> if (isCritical) 3.toDouble().div(3) else 3.toDouble().div(6)
            Value.N2 -> if (isCritical) 3.toDouble().div(3) else 3.toDouble().div(5)
            Value.N1 -> if (isCritical) 3.toDouble().div(3) else 3.toDouble().div(4)
            Value.P1 -> 4.toDouble().div(3)
            Value.P2 -> 5.toDouble().div(3)
            Value.P3 -> 6.toDouble().div(3)
            Value.P4 -> 7.toDouble().div(3)
            Value.P5 -> 8.toDouble().div(3)
            Value.P6 -> 9.toDouble().div(3)
            else -> 1.toDouble()
        }
    }

    fun calcSpecialDefenseValue(): Double {
        val sd = if (side == PartyInBattle.MY_SIDE) {
            individualForUI.specialDefense
        } else {
            individualForUI.calcSpecialDefense(individualForUI.specialDefenseEv, mega)
        }
        return Math.floor(sd.times(Characteristic.correction(characteristic, "D")))
    }

    fun calcSpecialDefenseValueCorrection(field: BattleField): Double {
        var initValue = 4096.0

        if (ability() == "フラワーギフト" && field.weather == BattleField.Weather.Sunny) {
            initValue = initValue.times(6144).div(4096)
        }
        if (item() == "しんかのきせき") {
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }
        if (item() == "とつげきチョッキ") {
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }
        if (ability() == "こころのしずく") {
            initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
        }
        if (item() == "しんかいのウロコ") {
            initValue = Math.round(initValue.times(8192.0).div(4096.0)).toDouble()
        }
        return initValue
    }

    fun getSpecialDefenseRankCorrection(isCritical: Boolean): Double {
        return when (Rank.value(specialDefenseRank)) {
            Value.N6 -> 3.toDouble().div(9)
            Value.N5 -> 3.toDouble().div(8)
            Value.N4 -> 3.toDouble().div(7)
            Value.N3 -> 3.toDouble().div(6)
            Value.N2 -> 3.toDouble().div(5)
            Value.N1 -> 3.toDouble().div(4)
            Value.P1 -> if (isCritical) 3.toDouble().div(3) else 4.toDouble().div(3)
            Value.P2 -> if (isCritical) 3.toDouble().div(3) else 5.toDouble().div(3)
            Value.P3 -> if (isCritical) 3.toDouble().div(3) else 6.toDouble().div(3)
            Value.P4 -> if (isCritical) 3.toDouble().div(3) else 7.toDouble().div(3)
            Value.P5 -> if (isCritical) 3.toDouble().div(3) else 8.toDouble().div(3)
            Value.P6 -> if (isCritical) 3.toDouble().div(3) else 9.toDouble().div(3)
            else -> 1.toDouble()
        }
    }

    fun calcSpeedValue(allField: BattleField, tailWind: Boolean, onlyStatus: Boolean): Int {
        var result = when (side) {
            PartyInBattle.MY_SIDE -> individualForUI.speed
            else -> individualForUI.calcSpeed(252, mega)
        }

        result = Math.floor(result.times(Characteristic.correction(characteristic, "S"))).toInt()
        if (onlyStatus) return result

        result = Math.floor(result.times(getSpeedRankCorrection())).toInt()

        if (allField.weather == BattleField.Weather.Sunny && ability() == "ようりょくそ") {
            result = result.times(2)
        }
        if (allField.weather == BattleField.Weather.Rainy && ability() == "すいすい") {
            result = result.times(2)
        }
        if (allField.weather == BattleField.Weather.Sandstorm && ability() == "すなかき") {
            result = result.times(2)
        }

        if (item() == "こだわりスカーフ") {
            result = Math.round(result.times(1.5)).toInt()
        }
        if (item() == "スピードパウダー") {
            result = Math.round(result.times(1.5)).toInt()
        }

        if (status == StatusAilment.no(StatusAilment.Code.PARALYSIS) && ability() != "ちどりあし") {
            result = Math.floor(result.times(0.5)).toInt()
        }

        if (status == StatusAilment.no(StatusAilment.Code.PARALYSIS) && ability() == "ちどりあし") {
            result = Math.round(result.times(1.5)).toInt()
        }

        if (tailWind) result = result.times(2)

        return result
    }

    fun getSpeedRankCorrection(): Double {
        return when (Rank.value(speedRank)) {
            Value.N6 -> 3.toDouble().div(9)
            Value.N5 -> 3.toDouble().div(8)
            Value.N4 -> 3.toDouble().div(7)
            Value.N3 -> 3.toDouble().div(6)
            Value.N2 -> 3.toDouble().div(5)
            Value.N1 -> 3.toDouble().div(4)
            Value.P1 -> 4.toDouble().div(3)
            Value.P2 -> 5.toDouble().div(3)
            Value.P3 -> 6.toDouble().div(3)
            Value.P4 -> 7.toDouble().div(3)
            Value.P5 -> 8.toDouble().div(3)
            Value.P6 -> 9.toDouble().div(3)
            else -> 1.toDouble()
        }
    }

    fun calcCriticalRate(): Double {
        if (skill.jname == "やまあらし" || skill.jname == "こおりのいぶき") {
            return 1.0
        }
        var rank = criticalRank
        if (item() == "ピントレンズ" || item() == "するどいツメ") {
            rank += 1
        }
        if (item() == "ラッキーパンチ" || item() == "ながねぎ") {
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

    fun typeBonus(): Pair<Int, Double> {
        val skillType =
                if (ability() == "スカイスキン" && skill.type == Type.no(Type.Code.NORMAL)) {
                    Type.no(Type.Code.FLYING)
                } else if (ability() == "フェアリースキン" && skill.type == Type.no(Type.Code.NORMAL)) {
                    Type.no(Type.Code.FAIRY)
                } else if (ability() == "フリーズスキン" && skill.type == Type.no(Type.Code.NORMAL)) {
                    Type.no(Type.Code.ICE)
                } else if (ability() == "エレキスキン" && skill.type == Type.no(Type.Code.NORMAL)) {
                    Type.no(Type.Code.ELECTRIC)
                } else {
                    skill.type
                }

        if (skillType == type1() || skillType == type2()) {
            if (ability() == "てきおうりょく") return Pair(skillType, 2.0) else return Pair(skillType, 1.5)
        }
        return Pair(skillType, 1.0)
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
        if (damage == 0) return 0

        var hp = hpValue
        if (side == PartyInBattle.OPPONENT_SIDE) {
            hp = individualForUI.calcHp(individualForUI.hpEv, mega).times(hpRatio()).div(100.0).toInt()
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
        } else if (skill.jname == "アフロブレイク" || skill.jname == "じごくぐるま" ||
                skill.jname == "とっしん" || skill.jname == "ワイルドボルト") {
            damage.div(4.0).toInt()
        } else if (skill.jname == "ウッドハンマー" || skill.jname == "すてみタックル" ||
                skill.jname == "フレアドライブ" || skill.jname == "ブレイブバード" ||
                skill.jname == "ボルテッカー") {
            damage.div(3.0).toInt()
        } else if (skill.jname == "もろはのずつき") {
            damage.div(2.0).toInt()
        } else {
            0
        }

        val d2 = if (ability() == "マジックガード") {
            0
        } else if (ability() == "ちからずく" && skill.aliment == -2) {
            0
        } else if (item() == "いのちのたま") {
            if (side == PartyInBattle.MY_SIDE) individualForUI.calcHp(mega).div(10)
            else individualForUI.calcHp(252, mega).div(10)
        } else {
            0
        }

        if (side == PartyInBattle.MY_SIDE) {
            hpValue = hpValue - d - d2
        } else {
            val max = individualForUI.calcHp(252, mega)
            val now = hpValue()
            hpRatio = (now - d - d2).times(100.0).div(max).toInt()
        }
    }

    fun noEffect(skill: SkillForUI, attackSide: PokemonForBattle, field: BattleField): Boolean {

        val kimottama = attackSide.ability() == "きもったま"
        if (kimottama && (Type.code(skill.type) == Type.Code.NORMAL || Type.code(skill.type) == Type.Code.FIGHTING)) {
            return (type1() == Type.no(Type.Code.GHOST) || type2() == Type.no(Type.Code.GHOST)).not()
        }
        val katayaburi = attackSide.ability() == "かたやぶり"
        val result = individualForUI.typeScale(Type.code(skill.type), mega, katayaburi)
        if (result < 0.1) {
            return true
        }

        if (0 < attackSide.skill.priority) {
            if ((ability() == "じょおうのいげん" || ability() == "ビビッドボディ") && katayaburi.not()) {
                return true
            } else if (field.terrain == BattleField.Terrain.PsycoTerrain) {
                return true
            }
        }

        if ((skill.jname == "ねむりごな" || skill.jname == "しびれごな" || skill.jname == "どくのこな" || skill.jname == "キノコのほうし" ||
                skill.jname == "やどりぎのタネ" || skill.jname == "いかりのこな" || skill.jname == "ふんじん" || skill.jname == "わたほうし") &&
                (type1() == Type.no(Type.Code.GRASS) || type2() == Type.no(Type.Code.GRASS))) {
            return true
        }
        if (skill.jname == "でんじは" && (type1() == Type.no(Type.Code.ELECTRIC) || type2() == Type.no(Type.Code.ELECTRIC))) {
            return true
        }
        if (skill.jname == "おにび" && (type1() == Type.no(Type.Code.FIRE) || type2() == Type.no(Type.Code.FIRE))) {
            return true
        }
        if (skill.jname == "どくどく" && (type1() == Type.no(Type.Code.POISON) || type2() == Type.no(Type.Code.POISON))) {
            return true
        }

        if ("ぼうおん" == ability() && katayaburi.not()) return Skill.voiceSkill(skill.jname)
        if ("ぼうじん" == ability() && katayaburi.not()) return Skill.powderSkill(skill.jname)
        if ("ぼうだん" == ability() && katayaburi.not()) return Skill.bomSkill(skill.jname)
        if (("ビビッドボディ" == ability() || "じょおうのいげん" == ability()) && katayaburi.not()) {
            return 0 < skill.priority
        }
        return false
    }

    fun floating(): Boolean {
        return (ability() == "ふゆう" || individualForUI.types(mega).first == Type.no(Type.Code.FLYING) ||
                individualForUI.types(mega).second == Type.no(Type.Code.FLYING) ||
                item() == "ふうせん")
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
        val values = individualForUI.speedValues(mega)

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
            PartyInBattle.MY_SIDE -> if (ability == NOT_CHANGED) individualForUI.ability(mega) else ability
            else -> ability
        }
    }

    fun item(): String {
        return if (itemUsed) "" else when (side) {
            PartyInBattle.MY_SIDE -> if (item == NOT_CHANGED) individualForUI.item else item
            else -> item
        }
    }

    fun name(): String {
        return individualForUI.name(mega)
    }

    fun type1(): Int {
        return individualForUI.master.type1
    }

    fun type2(): Int {
        return individualForUI.master.type1
    }
}