package com.gmail.sacchin13.pokemonbattleanalyzer

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.*
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl.RankingPokemonSkill
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl.RankingResponse
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl.TrendForBattle
import com.squareup.moshi.Moshi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import java.io.File
import java.util.*
import kotlin.properties.Delegates

class BattleResultTest {

    var garura: PokemonForBattle by Delegates.notNull()
    var fireallow: PokemonForBattle by Delegates.notNull()

    @Before
    fun 初期化() {
        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter(RankingResponse::class.java)
        val database = mapOf<String, Skill>(
                "アイアンヘッド" to createSkill(58, "アイアンヘッド", 16, 80, 100, 0, 15, true, true, -1, 0.0, -1, 0.0, -1, 0.0),
                "いわなだれ" to createSkill(35, "いわなだれ", 12, 75, 90, 0, 10, false, true, -1, 0.0, -1, 0.0, -1, 0.0),
                "おにび" to createSkill(53, "おにび", 1, -1, 85, 2, 15, false, true, StatusAilment.no(StatusAilment.Code.BURN), 100.0, -1, 0.0, -1, 0.0),
                "おいうち" to createSkill(42, "おいうち", 15, 40, 100, 0, 20, true, true, -1, 0.0, -1, 0.0, -1, 0.0),
                "おんがえし" to createSkill(55, "おんがえし", 0, 102, 100, 0, 20, true, true, -1, 0.0, -1, 0.0, -1, 0.0),
                "がんせきふうじ" to createSkill(118, "がんせきふうじ", 12, 60, 95, 0, 15, false, true, -1, 0.0, -1, 0.0, Rank.no(Rank.Code.mS), 100.0),
                "グロウパンチ" to createSkill(177, "グロウパンチ", 6, 40, 100, 0, 20, true, true, -1, 0.0, Rank.no(Rank.Code.A), 100.0, -1, 0.0),
                "けたぐり" to createSkill(137, "けたぐり", 6, -1, 100, 0, 20, true, true, -1, 0.0, -1, 0.0, -1, 0.0),
                "げきりん" to createSkill(138, "げきりん", 14, 120, 100, 0, 10, true, true, -1, 0.0, -1, 0.0, -1, 0.0),
                "かげうち" to createSkill(97, "かげうち", 13, 40, 100, 0, 30, 1, true, true, -1, 0.0, -1, 0.0, -1, 0.0),
                "キングシールド" to createSkill(161, "キングシールド", 16, -1, -1, 2, 10, 4, false, false, -1, 0.0, -1, 0.0, Rank.no(Rank.Code.mA2), 100.0),
                "ステルスロック" to createSkill(260, "ステルスロック", 12, -1, -1, 2, 20, false, false, -1, 0.0, -1, 0.0, -1, 0.0),
                "ストーンエッジ" to createSkill(261, "ストーンエッジ", 12, 100, 80, 0, 5, false, true, -1, 0.0, -1, 0.0, -1, 0.0),
                "じしん" to createSkill(211, "じしん", 8, 100, 100, 0, 10, false, true, -1, 0.0, -1, 0.0, -1, 0.0),
                "せいなるつるぎ" to createSkill(230, "せいなるつるぎ", 6, 90, 100, 0, 20, true, true, -1, 0.0, -1, 0.0, -1, 0.0),
                "シャドーボール" to createSkill(250, "シャドーボール", 13, 80, 100, 1, 15, false, true, -1, 0.0, -1, 0.0, Rank.no(Rank.Code.mD), 20.0),
                "すてみタックル" to createSkill(222, "すてみタックル", 0, 120, 100, 0, 15, true, true, -1, 0.0, -1, 0.0, -1, 0.0),
                "サイコキネシス" to createSkill(238, "サイコキネシス", 10, 90, 100, 1, 10, false, true, -1, 0.0, -1, 0.0, Rank.no(Rank.Code.mD), 10.0),
                "スキルスワップ" to createSkill(258, "スキルスワップ", 10, -1, -1, 2, 10, false, true, -1, 0.0, -1, 0.0, -1, 0.0),
                "だいばくはつ" to createSkill(278, "だいばくはつ", 0, 250, 100, 0, 5, false, true, -1, 0.0, -1, 0.0, -1, 0.0),
                "ちょうはつ" to createSkill(287, "ちょうはつ", 15, -1, 100, 2, 20, false, true, -1, 0.0, -1, 0.0, -1, 0.0),
                "つるぎのまい" to createSkill(302, "つるぎのまい", 0, -1, -1, 2, 20, false, false, -1, 0.0, Rank.no(Rank.Code.A2), 100.0, -1, 0.0),
                "どくづき" to createSkill(328, "どくづき", 7, 80, 100, 0, 20, true, true, StatusAilment.no(StatusAilment.Code.POISON), 30.0, -1, 0.0, -1, 0.0),
                "どくどく" to createSkill(329, "どくどく", 7, -1, 90, 2, 10, false, true, StatusAilment.no(StatusAilment.Code.BADPOISON), 100.0, -1, 0.0, -1, 0.0),
                "でんじは" to createSkill(311, "でんじは", 3, -1, 100, 2, 20, false, true, StatusAilment.no(StatusAilment.Code.PARALYSIS), 100.0, -1, 0.0, -1, 0.0),
                "とんぼがえり" to createSkill(327, "とんぼがえり", 11, 70, 100, 0, 20, true, true, -1, 0.0, -1, 0.0, -1, 0.0),
                "ドラゴンクロー" to createSkill(365, "ドラゴンクロー", 14, 80, 100, 0, 15, true, true, -1, 0.0, -1, 0.0, -1, 0.0),
                "トリックルーム" to createSkill(362, "トリックルーム", 10, -1, -1, 2, 5, -7, false, false, -1, 0.0, -1, 0.0, -1, 0.0),
                "つきのひかり" to createSkill(289, "つきのひかり", 17, -1, -1, 2, 5, false, false, -1, 0.0, -1, 0.0, -1, 0.0),
                "ねごと" to createSkill(389, "ねごと", 0, -1, -1, 2, 10, false, false, -1, 0.0, -1, 0.0, -1, 0.0),
                "ねこだまし" to createSkill(387, "ねこだまし", 0, 40, 100, 0, 10, 3, true, true, -1, 0.0, -1, 0.0, -1, 0.0),
                "ばかぢから" to createSkill(423, "ばかぢから", 6, 120, 100, 0, 5, true, true, -1, 0.0, Rank.no(Rank.Code.mAmB), 100.0, -1, 0.0),
                "はたきおとす" to createSkill(412, "はたきおとす", 15, 65, 100, 0, 20, true, true, -1, 0.0, -1, 0.0, -1, 0.0),
                "はねやすめ" to createSkill(419, "はねやすめ", 9, -1, -1, 2, 10, false, false, -1, 0.0, -1, 0.0, -1, 0.0),
                "ひみつのちから" to createSkill(431, "ひみつのちから", 0, 70, 100, 0, 20, false, true, -1, 0.0, -1, 0.0, -1, 0.0),
                "ふいうち" to createSkill(432, "ふいうち", 15, 80, 100, 0, 5, 1, true, true, -1, 0.0, -1, 0.0, -1, 0.0),
                "フレアドライブ" to createSkill(493, "フレアドライブ", 1, 120, 100, 0, 15, true, true, StatusAilment.no(StatusAilment.Code.BURN), 10.0, -1, 0.0, -1, 0.0),
                "ブレイブバード" to createSkill(497, "ブレイブバード", 9, 120, 100, 0, 15, true, true, -1, 0.0, -1, 0.0, -1, 0.0),
                "ほのおのパンチ" to createSkill(454, "ほのおのパンチ", 1, 75, 100, 0, 15, true, true, StatusAilment.no(StatusAilment.Code.BURN), 10.0, -1, 0.0, -1, 0.0),
                "みかづきのまい" to createSkill(520, "みかづきのまい", 10, -1, -1, 2, 10, false, false, -1, 0.0, -1, 0.0, -1, 0.0),
                "まもる" to createSkill(517, "まもる", 0, -1, -1, 2, 10, 4, false, false, -1, 0.0, -1, 0.0, -1, 0.0),
                "ほのおのキバ" to createSkill(453, "ほのおのキバ", 1, 65, 95, 0, 15, true, true, StatusAilment.no(StatusAilment.Code.BURN), 10.0, -1, 0.0, -1, 0.0),
                "みがわり" to createSkill(521, "みがわり", 0, -1, -1, 2, 10, false, false, -1, 0.0, -1, 0.0, -1, 0.0),
                "めいそう" to createSkill(538, "めいそう", 10, -1, -1, 2, 20, false, false, -1, 0.0, Rank.no(Rank.Code.CD), 100.0, -1, 0.0),
                "れいとうパンチ" to createSkill(589, "れいとうパンチ", 5, 75, 100, 0, 15, true, true, StatusAilment.no(StatusAilment.Code.FREEZE), 10.0, -1, 0.0, -1, 0.0),
                "れいとうビーム" to createSkill(590, "れいとうビーム", 5, 90, 100, 1, 10, false, true, StatusAilment.no(StatusAilment.Code.FREEZE), 10.0, -1, 0.0, -1, 0.0),
                "ラスターカノン" to createSkill(594, "ラスターカノン", 16, 80, 100, 1, 10, false, true, -1, 0.0, -1, 0.0, Rank.no(Rank.Code.mD), 10.0),
                "ワイドガード" to createSkill(611, "ワイドガード", 12, -1, -1, 2, 10, 3, false, false, -1, 0.0, -1, 0.0, -1, 0.0),
                "おいかぜ" to createSkill(43, "おいかぜ", 9, -1, -1, 2, 30, false, false, -1, 0.0, -1, 0.0, -1, 0.0))

        var reader = File("testdata/1.txt").absoluteFile
        val sb1 = StringBuilder()
        for (temp in reader.readLines()) {
            sb1.append(temp)
        }
        val rankingResponse1 = adapter.fromJson(sb1.toString())
        rankingResponse1.rankingPokemonTrend.convertToFew()
        val skills1 = ArrayList<RankingPokemonSkill>()
        for (temp in rankingResponse1.rankingPokemonTrend.wazaInfo) {
            if (database.contains(temp.name)) skills1.add(RankingPokemonSkill.create(temp, database[temp.name] as Skill))
        }
        garura = PokemonForBattle.create(PartyInBattle.MY_SIDE, IndividualPBAPokemon(
                0, -1, "ガルーラナイト", "いじっぱり", "せいしんりょく",
                database["ねこだまし"] as Skill, database["いわなだれ"] as Skill, database["みがわり"] as Skill, database["アイアンヘッド"] as Skill,
                212, 150, 137, 40, 80, 150, PokemonMasterData("115", "ガルーラ", "Kangaskhan", "-", 105, 95, 80, 40, 80, 90, "はやおき", "きもったま", "せいしんりょく", 0, -1, 80.0f)))
        garura.skill = database["おんがえし"] as Skill
        garura.skill.power = 200
        garura.hpValue = 212
        garura.trend = TrendForBattle.create(rankingResponse1.rankingPokemonTrend)
        garura.trend.skillList = skills1

        reader = File("testdata/2.txt").absoluteFile
        val sb2 = StringBuilder()
        for (temp in reader.readLines()) {
            sb2.append(temp)
        }
        val rankingResponse2 = adapter.fromJson(sb2.toString())
        rankingResponse2.rankingPokemonTrend.convertToFew()
        val skills2 = ArrayList<RankingPokemonSkill>()
        for (temp in rankingResponse2.rankingPokemonTrend.wazaInfo) {
            if (database.contains(temp.name)) skills2.add(RankingPokemonSkill.create(temp, database[temp.name] as Skill))
        }
        fireallow = PokemonForBattle.create(PartyInBattle.OPPONENT_SIDE, IndividualPBAPokemon(
                0, -1, "ゴツゴツメット", "ずぶとい", "はやてのつばさ",
                database["はねやすめ"] as Skill, database["おにび"] as Skill, database["みがわり"] as Skill, database["ブレイブバード"] as Skill,
                0, 0, 0, 0, 0, 0, PokemonMasterData("663", "ファイアロー", "Talonflame", "-", 78, 81, 71, 74, 69, 126, "ほのおのからだ", "-", "はやてのつばさ", 1, 9, 24.5f)))
        fireallow.hpRatio = 100
        fireallow.skill = database["ブレイブバード"] as Skill
        fireallow.trend = TrendForBattle.create(rankingResponse2.rankingPokemonTrend)
        fireallow.trend.skillList = skills2


//        reader = File("testdata/3.txt").absoluteFile
//        val sb3 = StringBuilder()
//        for(temp in reader.readLines()){
//            sb3.append(temp)
//        }
//        val skills3 = ArrayList<Skill>()
//        val rankingResponse3 = adapter.fromJson(sb3.toString())
//        rankingResponse3.rankingPokemonTrend.convertToFew()
//        for (temp in rankingResponse3.rankingPokemonTrend.wazaInfo) {
//            if(database.contains(temp.name)) skills3.add(database[temp.name] as Skill)
//        }
//
//        reader = File("testdata/4.txt").absoluteFile
//        val sb4 = StringBuilder()
//        for(temp in reader.readLines()){
//            sb4.append(temp)
//        }
//        val skills4 = ArrayList<Skill>()
//        val rankingResponse4 = adapter.fromJson(sb4.toString())
//        rankingResponse4.rankingPokemonTrend.convertToFew()
//        for (temp in rankingResponse4.rankingPokemonTrend.wazaInfo) {
//            if(database.contains(temp.name)) skills4.add(database[temp.name] as Skill)
//        }
//
//        reader = File("testdata/5.txt").absoluteFile
//        val sb5 = StringBuilder()
//        for(temp in reader.readLines()){
//            sb5.append(temp)
//        }
//        val skills5 = ArrayList<Skill>()
//        val rankingResponse5 = adapter.fromJson(sb5.toString())
//        rankingResponse5.rankingPokemonTrend.convertToFew()
//        for (temp in rankingResponse5.rankingPokemonTrend.wazaInfo) {
//            if(database.contains(temp.name)) skills5.add(database[temp.name] as Skill)
//        }
//
//        reader = File("testdata/6.txt").absoluteFile
//        val sb6 = StringBuilder()
//        for(temp in reader.readLines()){
//            sb6.append(temp)
//        }
//        val skills6 = ArrayList<Skill>()
//        val rankingResponse6 = adapter.fromJson(sb6.toString())
//        rankingResponse6.rankingPokemonTrend.convertToFew()
//        for (temp in rankingResponse6.rankingPokemonTrend.wazaInfo) {
//            if(database.contains(temp.name)) skills6.add(database[temp.name] as Skill)
//        }

    }

    fun createSkill(id: Int, name: String, type: Int, power: Int, accuracy: Int, category: Int, pp: Int, contact: Boolean, protectable: Boolean,
                    aliment: Int, alimentRate: Double, myRankUp: Int, myRankUpRate: Double, oppoRankUp: Int, oppoRankUpRate: Double): Skill {
        return createSkill(id, name, type, power, accuracy, category, pp, 0, contact, protectable, aliment, alimentRate, myRankUp, myRankUpRate, oppoRankUp, oppoRankUpRate)
    }

    fun createSkill(id: Int, name: String, type: Int, power: Int, accuracy: Int, category: Int, pp: Int, priority: Int,
                    contact: Boolean, protectable: Boolean, aliment: Int, alimentRate: Double, myRankUp: Int, myRankUpRate: Double, oppoRankUp: Int, oppoRankUpRate: Double): Skill {
        return Skill(id, name, "", type, power, accuracy.div(100.0), category, pp, priority, contact, protectable, aliment, alimentRate.div(100.0), myRankUp, myRankUpRate.div(100.0), oppoRankUp, oppoRankUpRate.div(100.0))
    }

//    @Test
//    fun タイプ別強化アイテムと先攻後攻のテスト() {
//        val attackSide = PokemonForBattle.create(0, IndividualPBAPokemon())
//        attackSide.item = "くろいメガネ"
//        attackSide.skill = Skill(197, "しっぺがえし", "", 15, 50, 1.0, 0, 10, 0, true, true)
//
//        val defenseSide = PokemonForBattle.create(0, IndividualPBAPokemon())
//        val field = BattleField()
//
//        val result = BattleCalculator.companion.calcSkillPower(attackSide, defenseSide, field, false, false)
//        assertEquals(120, result)
//    }
//
//    @Test
//    fun タイプ半減特性と非ダメージによる威力上昇技のテスト() {
//        val attackSide = PokemonForBattle.create(0, IndividualPBAPokemon())
//        attackSide.item = "ものしりメガネ"
//        attackSide.skill = Skill(575, "ゆきなだれ", "", 5, 60, 1.0, 0, 10, 0, true, true)
//
//        val defenseSide = PokemonForBattle.create(0, IndividualPBAPokemon())
//        defenseSide.ability = "あついしぼう"
//
//        val field = BattleField()
//
//        val result = BattleCalculator.companion.calcSkillPower(attackSide, defenseSide, field,  false, true)
//        assertEquals(60, result)
//    }
//
//    @Test
//    fun 物理特殊依存アイテムとテクニシャンのテスト() {
//        val attackSide = PokemonForBattle.create(0, IndividualPBAPokemon())
//        attackSide.item = "ちからのハチマキ"
//        attackSide.ability = "テクニシャン"
//        attackSide.skill = Skill(553, "マッハパンチ", "", 6, 40, 1.0, 0, 30, 0, true, true)
//
//        val defenseSide = PokemonForBattle.create(0, IndividualPBAPokemon())
//
//        val field = BattleField()
//
//        val result = BattleCalculator.companion.calcSkillPower(attackSide, defenseSide, field,  false, true)
//        assertEquals(66, result)
//    }
//
//    @Test
//    fun HP依存技と非ダメージ側の特性のテスト() {
//        val attackSide = PokemonForBattle.create(0, IndividualPBAPokemon())
//        attackSide.ability = "もうか"
//        attackSide.hpRatio = 29
//        attackSide.skill = Skill(494, "ブラストバーン", "", 1, 150, 0.9, 1, 5, 0, false, true)
//
//        val defenseSide = PokemonForBattle.create(0, IndividualPBAPokemon())
//        defenseSide.ability = "かんそうはだ"
//
//        val field = BattleField()
//
//        val result = BattleCalculator.companion.calcSkillPower(attackSide, defenseSide, field,  false, true)
//        assertEquals(280, result)
//    }
//
//    @Test
//    fun HP依存技ふんかの正常系テスト() {
//        val attackSide = PokemonForBattle.create(0, IndividualPBAPokemon())
//        attackSide.hpRatio = 100
//        attackSide.skill = Skill(440, "ふんか", "", 1, 150, 1.0, 1, 5, 0, false, true)
//
//        val defenseSide = PokemonForBattle.create(0, IndividualPBAPokemon())
//
//        val field = BattleField()
//
//        val result = BattleCalculator.companion.calcSkillPower(attackSide, defenseSide, field, false, true)
//        assertEquals(150, result)
//    }

    @Test
    fun 優先度技探索の正常系テスト() {
        val result = BattleResult()
        result.prioritySkill(fireallow.trend)

        var actual = result.prioritySkills["ブレイブバード:はやてのつばさ(1)"]
        assertNotNull(actual)
        assertEquals(0.9818019270896912, actual!!, 0.1)

        actual = result.prioritySkills["はねやすめ:はやてのつばさ(1)"]
        assertNotNull(actual)
        assertEquals(0.9818019270896912, actual!!, 0.1)

        actual = result.prioritySkills["おいかぜ:はやてのつばさ(1)"]
        assertNotNull(actual)
        assertEquals(0.9818019270896912, actual!!, 0.1)

        actual = result.prioritySkills["まもる(4)"]
        assertNotNull(actual)
        assertEquals(1.0, actual!!, 0.1)
    }

    @Test
    fun 素早さ集計の枠組みを作る正常系テスト() {
        val result = BattleResult()

        fireallow.characteristic = "ようき"
        result.orderRate(fireallow, 0.2)

        fireallow.characteristic = "いじっぱり"
        result.orderRate(fireallow, 0.5)

        fireallow.characteristic = "ゆうかん"
        result.orderRate(fireallow, 0.3)

        assertEquals(0.15, result.speedOccur[0]!!, 0.1)
        assertEquals(0.15, result.speedOccur[1]!!, 0.1)
        assertEquals(0.16666666666666666, result.speedOccur[2]!!, 0.1)
        assertEquals(0.16666666666666666, result.speedOccur[3]!!, 0.1)
        assertEquals(0.1, result.speedOccur[4]!!, 0.1)
        assertEquals(0.0, result.speedOccur[5]!!, 0.1)
        assertEquals(0.16666666666666666, result.speedOccur[6]!!, 0.1)
        assertEquals(0.0, result.speedOccur[7]!!, 0.1)
        assertEquals(0.0, result.speedOccur[8]!!, 0.1)
        assertEquals(0.1, result.speedOccur[9]!!, 0.1)
        assertEquals(0.0, result.speedOccur[10]!!, 0.1)
        assertEquals(0.0, result.speedOccur[11]!!, 0.1)
//        117
//        131
//        146
//        147
//        160
//        197
//        178
//        219
//        240
//        195
//        267
//        293
    }

    @Test
    fun 素早さ集計の正常系テスト() {
        val result = BattleResult()

        fireallow.characteristic = "ようき"
        result.orderRate(fireallow, 0.2)

        fireallow.characteristic = "いじっぱり"
        result.orderRate(fireallow, 0.5)

        fireallow.characteristic = "ゆうかん"
        result.orderRate(fireallow, 0.3)

        val(label, rate) = result.orderResult(garura, fireallow)
        assertEquals("4振無補正", label)
        assertEquals(0.6333333333333333, rate, 0.1)
    }

}