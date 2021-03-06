package com.gmail.sacchin13.pokemonbattleanalyzer

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.*
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl.RankingPokemonSkill
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl.RankingResponse
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl.TrendForBattle
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.realm.IndividualPokemon
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.realm.PokemonMasterData
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.realm.Skill
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.ui.IndividualPokemonForUI
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.ui.PokemonMasterDataForUI
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.ui.SkillForUI
import com.gmail.sacchin13.pokemonbattleanalyzer.logic.BattleCalculator
import com.squareup.moshi.Moshi
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import java.io.File
import java.util.*
import kotlin.properties.Delegates

@RunWith(PowerMockRunner::class)
@PrepareForTest(PokemonForBattle::class)
class BattleCalculatorTest {

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
        garura = PokemonForBattle.create(PartyInBattle.MY_SIDE, IndividualPokemon(
                0, -1, "ガルーラナイト", "いじっぱり", "せいしんりょく",
                database["ねこだまし"] as Skill, database["いわなだれ"] as Skill, database["みがわり"] as Skill, database["アイアンヘッド"] as Skill,
                252, 252, 0, 0, 0, 4, //31, 31, 31, 31, 31, 31,
                PokemonMasterData("115", "ガルーラ", "Kangaskhan", "-", 105, 95, 80, 40, 80, 90, "はやおき", "きもったま", "せいしんりょく", 0, -1, 80.0f)))
        garura.skill = database["おんがえし"]?.uiObject() ?: SkillForUI()
        //garura.skill.power = 200
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
        fireallow = PokemonForBattle.create(PartyInBattle.OPPONENT_SIDE, IndividualPokemon(
                0, -1, "ゴツゴツメット", "ずぶとい", "はやてのつばさ",
                database["はねやすめ"] as Skill, database["おにび"] as Skill, database["みがわり"] as Skill, database["ブレイブバード"] as Skill,
                252, 0, 252, 0, 0, 4, //31, 31, 31, 31, 31, 31,
                PokemonMasterData("663", "ファイアロー", "Talonflame", "-", 78, 81, 71, 74, 69, 126, "ほのおのからだ", "-", "はやてのつばさ", 1, 9, 24.5f)))
        fireallow.hpRatio = 100
        fireallow.skill = database["ブレイブバード"]?.uiObject() ?: SkillForUI()
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
        return Skill.create(id, name, "", type, power, accuracy.div(100.0), category, pp, priority, contact, protectable, aliment, alimentRate.div(100.0), myRankUp, myRankUpRate.div(100.0), oppoRankUp, oppoRankUpRate.div(100.0))
    }

//    @Test
//    fun タイプ別強化アイテムと先攻後攻のテスト() {
//        val attackSide = PokemonForBattle.create(0, IndividualPokemon())
//        attackSide.item = "くろいメガネ"
//        attackSide.skill = Skill(197, "しっぺがえし", "", 15, 50, 1.0, 0, 10, 0, true, true)
//
//        val defenseSide = PokemonForBattle.create(0, IndividualPokemon())
//        val field = BattleField()
//
//        val result = BattleCalculator.companion.calcSkillPower(attackSide, defenseSide, field, false, false)
//        assertEquals(120, result)
//    }
//
//    @Test
//    fun タイプ半減特性と非ダメージによる威力上昇技のテスト() {
//        val attackSide = PokemonForBattle.create(0, IndividualPokemon())
//        attackSide.item = "ものしりメガネ"
//        attackSide.skill = Skill(575, "ゆきなだれ", "", 5, 60, 1.0, 0, 10, 0, true, true)
//
//        val defenseSide = PokemonForBattle.create(0, IndividualPokemon())
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
//        val attackSide = PokemonForBattle.create(0, IndividualPokemon())
//        attackSide.item = "ちからのハチマキ"
//        attackSide.ability = "テクニシャン"
//        attackSide.skill = Skill(553, "マッハパンチ", "", 6, 40, 1.0, 0, 30, 0, true, true)
//
//        val defenseSide = PokemonForBattle.create(0, IndividualPokemon())
//
//        val field = BattleField()
//
//        val result = BattleCalculator.companion.calcSkillPower(attackSide, defenseSide, field,  false, true)
//        assertEquals(66, result)
//    }
//
//    @Test
//    fun HP依存技と非ダメージ側の特性のテスト() {
//        val attackSide = PokemonForBattle.create(0, IndividualPokemon())
//        attackSide.ability = "もうか"
//        attackSide.hpRatio = 29
//        attackSide.skill = Skill(494, "ブラストバーン", "", 1, 150, 0.9, 1, 5, 0, false, true)
//
//        val defenseSide = PokemonForBattle.create(0, IndividualPokemon())
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
//        val attackSide = PokemonForBattle.create(0, IndividualPokemon())
//        attackSide.hpRatio = 100
//        attackSide.skill = Skill(440, "ふんか", "", 1, 150, 1.0, 1, 5, 0, false, true)
//
//        val defenseSide = PokemonForBattle.create(0, IndividualPokemon())
//
//        val field = BattleField()
//
//        val result = BattleCalculator.companion.calcSkillPower(attackSide, defenseSide, field, false, true)
//        assertEquals(150, result)
//    }


//    @Test
//    fun ダメージ計算の正常系テスト() {
//        val calc = BattleCalculator()
//        val result = calc.doSkill(fireallow, garura, BattleField(), 0.5, false, false)
//
//        assertEquals(0.0625, result[76]!!, 0.1)
//        assertEquals(0.003125, result[79]!!, 0.1)
//        assertEquals(0.0625, result[87]!!, 0.1)
//        assertEquals(0.03125, result[90]!!, 0.1)
//
//        assertEquals(0.09375, result[51]!!, 0.1)
//        assertEquals(0.09375, result[54]!!, 0.1)
//        assertEquals(0.09375, result[57]!!, 0.1)
//        assertEquals(0.003125, result[60]!!, 0.1)
//    }

//    @Test
//    fun 自分側の確定数を計算する正常系テスト() {
//        val calc = BattleCalculator()
//        val result = calc.myAttack(1.0, garura, fireallow, BattleField())
//        assertEquals(2.4765625, result.defeatTimes[1]!!, 0.1)
//        assertEquals(1.5234375, result.defeatTimes[2]!!, 0.1)
//        assertEquals(0.0, result.defeatTimes[3]!!, 0.1)
//        assertEquals(0.0, result.defeatTimes[4]!!, 0.1)
//        assertEquals(0.0, result.defeatTimes[5]!!, 0.1)
//    }

//    @Test
//    fun 自分側の確定数がすべて1となる場合の正常系テスト() {
//        //確一にするため威力を操作
//        garura.skill.power = 2000
//        val calc = BattleCalculator()
//        val result = calc.myAttack(1.0, garura, fireallow, BattleField())
//
//        assertEquals(1.0, result.defeatTimes[1]!!, 0.1)
//    }
//
//    @Test
//    fun 自分側の確定数がすべて5以上となる場合の正常系テスト() {
//        //確五以上にするため威力を操作
//        garura.skill.power = 1
//        val calc = BattleCalculator()
//        val result = calc.myAttack(1.0, garura, fireallow, BattleField())
//
//        assertEquals(3.0, result.defeatTimes[5]!!, 0.1)
//    }

//    @Test
//    fun 相手側の確定数を計算する正常系テスト() {
//        val calc = BattleCalculator()
//        val result = calc.oppoAttack(1.0, fireallow, garura, BattleField())
//
//        val skill1 = result.defeatedTimes["ブレイブバード"]
//        assertNotNull(skill1)
//        assertEquals(0.0, skill1!![1]!!, 0.1)
//        assertEquals(0.03515625, skill1[2]!!, 0.1)
//        assertEquals(0.67578125, skill1[3]!!, 0.1)
//        assertEquals(0.99609375, skill1[4]!!, 0.1)
//        assertEquals(0.29296875, skill1[5]!!, 0.1)
//
//        val skill2 = result.defeatedTimes["はねやすめ"]
//        assertNotNull(skill1)
//        assertEquals(0.0, skill2!![1]!!, 0.1)
//        assertEquals(0.0, skill2[2]!!, 0.1)
//        assertEquals(0.0, skill2[3]!!, 0.1)
//        assertEquals(0.0, skill2[4]!!, 0.1)
//        assertEquals(2.0, skill2[5]!!, 0.1)
//    }

//    @Test
//    fun 戦闘シミュレーションの正常系テスト() {
//        fireallow.item = ""
//        fireallow.characteristic = ""
//        fireallow.ability = ""
//        fireallow.skill = createSkill(43, "おいかぜ", 9, -1, -1, 2, 30, false, false, -1, 0.0, -1, 0.0, -1, 0.0)
//
//
//        val result = BattleCalculator.simulateTurn(1.0, garura, fireallow, BattleField())
//        for (temp in result.defeatTimes) {
//            println("${temp.key} = ${temp.value}")
//        }
//    }

    @Test
    fun 検証スレテストケースその1() {
        val field = BattleField()

        val attackSide = PowerMockito.mock(PokemonForBattle::class.java)
        Mockito.`when`(attackSide.calcAttackValue()).thenReturn(166.0)
        Mockito.`when`(attackSide.typeBonus()).thenReturn(Pair(0, 1.0))
        Mockito.`when`(attackSide.getAttackRankCorrection(Mockito.anyBoolean())).thenReturn(1.0)
        Mockito.`when`(attackSide.side).thenReturn(PartyInBattle.MY_SIDE)
        Mockito.`when`(attackSide.status).thenReturn(-1)
        Mockito.`when`(attackSide.skill).thenReturn(Skill(104, "かみくだく", "かみくだく", 15, 80, 100.0, 0, 15, 0, true, true, -1, 0.0, -1, 0.0, Rank.no(Rank.Code.mB), 20.0).uiObject())
        Mockito.`when`(attackSide.individual).thenReturn(IndividualPokemonForUI(
                0, -1, "unknown", "unknown", "unknown", SkillForUI(), SkillForUI(), SkillForUI(), SkillForUI(),
                252, 252, 0, 0, 0, 4, //31, 31, 31, 31, 31, 31,
                PokemonMasterDataForUI("445", "ガブリアス", "Garchomp", "-", 108, 130, 95, 80, 85, 102, "すながくれ", "-", "さめはだ", 14, 8, 95.0f)))

        val defenseSide = PowerMockito.mock(PokemonForBattle::class.java)
        Mockito.`when`(defenseSide.calcDefenseValue()).thenReturn(104.0)
        Mockito.`when`(defenseSide.calcDefenseValueCorrection(attackSide)).thenReturn(4096.0)
        Mockito.`when`(defenseSide.getDefenseRankCorrection(Mockito.anyBoolean())).thenReturn(1.0)
        Mockito.`when`(defenseSide.noEffect(attackSide.skill, attackSide, field)).thenReturn(false)
        Mockito.`when`(defenseSide.side).thenReturn(PartyInBattle.OPPONENT_SIDE)
        Mockito.`when`(defenseSide.individual).thenReturn(IndividualPokemonForUI(
                0, -1, "unknown", "unknown", "unknown", SkillForUI(), SkillForUI(), SkillForUI(), SkillForUI(),
                252, 252, 0, 0, 0, 4, //31, 31, 31, 31, 31, 31,
                PokemonMasterDataForUI("083", "カモネギ", "Farfetch'D", "-", 52, 65, 55, 58, 62, 60, "するどいめ", "せいしんりょく", "まけんき", 0, 9, 15.0f)))

        Mockito.`when`(attackSide.determineSkillPower(defenseSide)).thenReturn(80)
        Mockito.`when`(attackSide.calcAttackValueCorrection(defenseSide, field)).thenReturn(Pair(4096.0, false))

        val calc = BattleCalculator(61, attackSide, defenseSide, field, object : BattleCalculator.EventListener {
            override fun onFinish(result: BattleResult) {}
        })
        val damages = calc.doSkill(attackSide, defenseSide, field, 0.0, true, true)
        assertTrue(damages.containsKey(57))
        assertTrue(damages.containsKey(58))
        assertTrue(damages.containsKey(59))
        assertTrue(damages.containsKey(60))
        assertTrue(damages.containsKey(61))
        assertTrue(damages.containsKey(62))
        assertTrue(damages.containsKey(63))
        assertTrue(damages.containsKey(64))
        assertTrue(damages.containsKey(65))
        assertTrue(damages.containsKey(66))
        assertTrue(damages.containsKey(68))
        assertTrue(damages.containsKey(89)) //critical
    }

    @Test
    fun 検証スレテストケースその2() {
        val field = BattleField()

        val attackSide = PowerMockito.mock(PokemonForBattle::class.java)
        Mockito.`when`(attackSide.calcSpecialAttackValue()).thenReturn(82.0)
        Mockito.`when`(attackSide.typeBonus()).thenReturn(Pair(0, 1.0))
        Mockito.`when`(attackSide.getSpecialAttackRankCorrection(Mockito.anyBoolean())).thenReturn(1.0)
        Mockito.`when`(attackSide.side).thenReturn(PartyInBattle.MY_SIDE)
        Mockito.`when`(attackSide.status).thenReturn(-1)
        Mockito.`when`(attackSide.skill).thenReturn(Skill(263, "スピードスター", "", 0, 60, -1.0, 1, 20, 0, false, true, -1, 0.0, -1, 0.0, -1, 0.0).uiObject())
        Mockito.`when`(attackSide.individual).thenReturn(IndividualPokemonForUI(
                0, -1, "unknown", "unknown", "unknown", SkillForUI(), SkillForUI(), SkillForUI(), SkillForUI(),
                252, 252, 0, 0, 0, 4, //31, 31, 31, 31, 31, 31,
                PokemonMasterDataForUI("700", "ニンフィア", "Sylveon", "-", 95, 65, 65, 110, 130, 60, "メロメロボディ", "-", "フェアリースキン", 17, -1, 23.5f)))

        val defenseSide = PowerMockito.mock(PokemonForBattle::class.java)
        Mockito.`when`(defenseSide.calcSpecialDefenseValue()).thenReturn(103.0)
        Mockito.`when`(defenseSide.calcSpecialDefenseValueCorrection(field)).thenReturn(4096.0)
        Mockito.`when`(defenseSide.getSpecialDefenseRankCorrection(Mockito.anyBoolean())).thenReturn(1.0)
        Mockito.`when`(defenseSide.noEffect(attackSide.skill, attackSide, field)).thenReturn(false)
        Mockito.`when`(defenseSide.side).thenReturn(PartyInBattle.OPPONENT_SIDE)
        Mockito.`when`(defenseSide.individual).thenReturn(IndividualPokemonForUI(
                0, -1, "unknown", "unknown", "unknown", SkillForUI(), SkillForUI(), SkillForUI(), SkillForUI(),
                252, 252, 0, 0, 0, 4, //31, 31, 31, 31, 31, 31,
                PokemonMasterDataForUI("196", "エーフィ", "Espeon", "-", 65, 65, 60, 130, 95, 110, "シンクロ", "-", "マジックミラー", 10, -1, 26.5f)))

        Mockito.`when`(attackSide.determineSkillPower(defenseSide)).thenReturn(60)
        Mockito.`when`(attackSide.calcSpecialAttackValueCorrection(defenseSide, field)).thenReturn(4096.0)

        val calc = BattleCalculator(31, attackSide, defenseSide, field, object : BattleCalculator.EventListener {
            override fun onFinish(result: BattleResult) {}
        })
        val damages = calc.doSkill(attackSide, defenseSide, field, 0.0, true, true)
        assertTrue(damages.containsKey(12))
        assertTrue(damages.containsKey(13))
        assertTrue(damages.containsKey(14))
    }

    @Test
    fun 検証スレテストケースその3() {
        val field = BattleField()

        val attackSide = PowerMockito.mock(PokemonForBattle::class.java)
        Mockito.`when`(attackSide.calcSpecialAttackValue()).thenReturn(82.0)
        Mockito.`when`(attackSide.typeBonus()).thenReturn(Pair(0, 1.5))
        Mockito.`when`(attackSide.ability()).thenReturn("フェアリースキン")
        Mockito.`when`(attackSide.getSpecialAttackRankCorrection(Mockito.anyBoolean())).thenReturn(1.0)
        Mockito.`when`(attackSide.side).thenReturn(PartyInBattle.MY_SIDE)
        Mockito.`when`(attackSide.status).thenReturn(-1)
        Mockito.`when`(attackSide.skill).thenReturn(Skill(263, "スピードスター", "", 0, 60, -1.0, 1, 20, 0, false, true, -1, 0.0, -1, 0.0, -1, 0.0).uiObject())
        Mockito.`when`(attackSide.individual).thenReturn(IndividualPokemonForUI(
                0, -1, "unknown", "unknown", "unknown", SkillForUI(), SkillForUI(), SkillForUI(), SkillForUI(),
                252, 252, 0, 0, 0, 4, //31, 31, 31, 31, 31, 31,
                PokemonMasterDataForUI("700", "ニンフィア", "Sylveon", "-", 95, 65, 65, 110, 130, 60, "メロメロボディ", "-", "フェアリースキン", 17, -1, 23.5f)))

        val defenseSide = PowerMockito.mock(PokemonForBattle::class.java)
        Mockito.`when`(defenseSide.calcSpecialDefenseValue()).thenReturn(103.0)
        Mockito.`when`(defenseSide.calcSpecialDefenseValueCorrection(field)).thenReturn(4096.0)
        Mockito.`when`(defenseSide.getSpecialDefenseRankCorrection(Mockito.anyBoolean())).thenReturn(1.0)
        Mockito.`when`(defenseSide.noEffect(attackSide.skill, attackSide, field)).thenReturn(false)
        Mockito.`when`(defenseSide.side).thenReturn(PartyInBattle.OPPONENT_SIDE)
        Mockito.`when`(defenseSide.individual).thenReturn(IndividualPokemonForUI(
                0, -1, "unknown", "unknown", "unknown", SkillForUI(), SkillForUI(), SkillForUI(), SkillForUI(),
                252, 252, 0, 0, 0, 4, //31, 31, 31, 31, 31, 31,
                PokemonMasterDataForUI("196", "エーフィ", "Espeon", "-", 65, 65, 60, 130, 95, 110, "シンクロ", "-", "マジックミラー", 10, -1, 26.5f)))

        Mockito.`when`(attackSide.determineSkillPower(defenseSide)).thenReturn(60)
        Mockito.`when`(attackSide.calcSpecialAttackValueCorrection(defenseSide, field)).thenReturn(4096.0)

        val calc = BattleCalculator(31, attackSide, defenseSide, field, object : BattleCalculator.EventListener {
            override fun onFinish(result: BattleResult) {}
        })
        val damages = calc.doSkill(attackSide, defenseSide, field, 0.0, true, true)
        assertTrue(damages.containsKey(24))
        assertTrue(damages.containsKey(27))
    }

    @Test
    fun 検証スレテストケースその4() {
        val field = BattleField()

        val attackSide = PowerMockito.mock(PokemonForBattle::class.java)
        Mockito.`when`(attackSide.calcAttackValue()).thenReturn(90.0)
        Mockito.`when`(attackSide.typeBonus()).thenReturn(Pair(0, 1.5))
        Mockito.`when`(attackSide.getAttackRankCorrection(Mockito.anyBoolean())).thenReturn(1.0)
        Mockito.`when`(attackSide.side).thenReturn(PartyInBattle.MY_SIDE)
        Mockito.`when`(attackSide.status).thenReturn(-1)
        Mockito.`when`(attackSide.skill).thenReturn(Skill(91, "かいりき", "-", 0, 80, 100.0, 0, 15, 0, true, true, -1, 0.0, -1, 0.0, -1, 0.0).uiObject())
        Mockito.`when`(attackSide.individual).thenReturn(IndividualPokemonForUI(
                0, -1, "unknown", "unknown", "unknown", SkillForUI(), SkillForUI(), SkillForUI(), SkillForUI(),
                252, 252, 0, 0, 0, 4, //31, 31, 31, 31, 31, 31,
                PokemonMasterDataForUI("660", "ホルード", "Diggersby", "-", 85, 56, 77, 50, 77, 78, "ものひろい", "ほおぶくろ", "ちからもち", 0, 8, 42.4f)))

        val defenseSide = PowerMockito.mock(PokemonForBattle::class.java)
        Mockito.`when`(defenseSide.calcDefenseValue()).thenReturn(27.0)
        Mockito.`when`(defenseSide.calcDefenseValueCorrection(attackSide)).thenReturn(4096.0)
        Mockito.`when`(defenseSide.getDefenseRankCorrection(Mockito.anyBoolean())).thenReturn(1.0)
        Mockito.`when`(defenseSide.noEffect(attackSide.skill, attackSide, field)).thenReturn(false)
        Mockito.`when`(defenseSide.side).thenReturn(PartyInBattle.OPPONENT_SIDE)
        Mockito.`when`(defenseSide.individual).thenReturn(IndividualPokemonForUI(
                0, -1, "unknown", "unknown", "unknown", SkillForUI(), SkillForUI(), SkillForUI(), SkillForUI(),
                252, 252, 0, 0, 0, 4, //31, 31, 31, 31, 31, 31,
                PokemonMasterDataForUI("113", "ラッキー", "-", "-", 250, 5, 5, 35, 105, 50, "しぜんかいふく", "てんのめぐみ", "いやしのこころ", 0, -1, 34.6f)))

        Mockito.`when`(attackSide.determineSkillPower(defenseSide)).thenReturn(80)
        Mockito.`when`(attackSide.calcAttackValueCorrection(defenseSide, field)).thenReturn(Pair(4096.0, false))

        val calc = BattleCalculator(50, attackSide, defenseSide, field, object : BattleCalculator.EventListener {
            override fun onFinish(result: BattleResult) {}
        })
        val damages = calc.doSkill(attackSide, defenseSide, field, 0.0, true, true)
        for (d in damages) println(d)
        assertTrue(damages.containsKey(151))
        assertTrue(damages.containsKey(153))
        assertTrue(damages.containsKey(154))
        assertTrue(damages.containsKey(157))
        assertTrue(damages.containsKey(160))
        assertTrue(damages.containsKey(162))
        assertTrue(damages.containsKey(163))
        assertTrue(damages.containsKey(171))
        assertTrue(damages.containsKey(172))
        assertTrue(damages.containsKey(174))
        assertTrue(damages.containsKey(178))
    }

    @Test
    fun 検証スレテストケースその5() {
        val field = BattleField()

        val attackSide = PowerMockito.mock(PokemonForBattle::class.java)
        Mockito.`when`(attackSide.calcAttackValue()).thenReturn(90.0)
        Mockito.`when`(attackSide.typeBonus()).thenReturn(Pair(0, 1.5))
        Mockito.`when`(attackSide.item).thenReturn("いのちのたま")
        Mockito.`when`(attackSide.getAttackRankCorrection(Mockito.anyBoolean())).thenReturn(1.0)
        Mockito.`when`(attackSide.side).thenReturn(PartyInBattle.MY_SIDE)
        Mockito.`when`(attackSide.status).thenReturn(-1)
        Mockito.`when`(attackSide.skill).thenReturn(Skill(91, "かいりき", "-", 0, 80, 100.0, 0, 15, 0, true, true, -1, 0.0, -1, 0.0, -1, 0.0).uiObject())
        Mockito.`when`(attackSide.individual).thenReturn(IndividualPokemonForUI(
                0, -1, "unknown", "unknown", "unknown", SkillForUI(), SkillForUI(), SkillForUI(), SkillForUI(),
                252, 252, 0, 0, 0, 4, //31, 31, 31, 31, 31, 31,
                PokemonMasterDataForUI("660", "ホルード", "Diggersby", "-", 85, 56, 77, 50, 77, 78, "ものひろい", "ほおぶくろ", "ちからもち", 0, 8, 42.4f)))

        val defenseSide = PowerMockito.mock(PokemonForBattle::class.java)
        Mockito.`when`(defenseSide.calcDefenseValue()).thenReturn(27.0)
        Mockito.`when`(defenseSide.calcDefenseValueCorrection(attackSide)).thenReturn(4096.0)
        Mockito.`when`(defenseSide.getDefenseRankCorrection(Mockito.anyBoolean())).thenReturn(1.0)
        Mockito.`when`(defenseSide.noEffect(attackSide.skill, attackSide, field)).thenReturn(false)
        Mockito.`when`(defenseSide.side).thenReturn(PartyInBattle.OPPONENT_SIDE)
        Mockito.`when`(defenseSide.individual).thenReturn(IndividualPokemonForUI(
                0, -1, "unknown", "unknown", "unknown", SkillForUI(), SkillForUI(), SkillForUI(), SkillForUI(),
                252, 252, 0, 0, 0, 4, //31, 31, 31, 31, 31, 31,
                PokemonMasterDataForUI("113", "ラッキー", "-", "-", 250, 5, 5, 35, 105, 50, "しぜんかいふく", "てんのめぐみ", "いやしのこころ", 0, -1, 34.6f)))

        Mockito.`when`(attackSide.determineSkillPower(defenseSide)).thenReturn(80)
        Mockito.`when`(attackSide.calcAttackValueCorrection(defenseSide, field)).thenReturn(Pair(4096.0, false))

        val calc = BattleCalculator(50, attackSide, defenseSide, field, object : BattleCalculator.EventListener {
            override fun onFinish(result: BattleResult) {}
        })
        val damages = calc.doSkill(attackSide, defenseSide, field, 0.0, true, true)
        assertTrue(damages.containsKey(196))
        assertTrue(damages.containsKey(199))
        assertTrue(damages.containsKey(200))
        assertTrue(damages.containsKey(203))
        assertTrue(damages.containsKey(204))
        assertTrue(damages.containsKey(208))
        assertTrue(damages.containsKey(212))
        assertTrue(damages.containsKey(214))
        assertTrue(damages.containsKey(222))
        assertTrue(damages.containsKey(224))
        assertTrue(damages.containsKey(226))
        assertTrue(damages.containsKey(231))
    }

    @Test
    fun 検証スレテストケースその6() {
        val field = BattleField()
        field.terrain = BattleField.Terrain.MistyTerrain

        val attackSide = PowerMockito.mock(PokemonForBattle::class.java)
        Mockito.`when`(attackSide.calcSpecialAttackValue()).thenReturn(154.0)
        Mockito.`when`(attackSide.typeBonus()).thenReturn(Pair(0, 1.5))
        Mockito.`when`(attackSide.getSpecialAttackRankCorrection(Mockito.anyBoolean())).thenReturn(1.0)
        Mockito.`when`(attackSide.side).thenReturn(PartyInBattle.MY_SIDE)
        Mockito.`when`(attackSide.status).thenReturn(-1)
        Mockito.`when`(attackSide.skill).thenReturn(Skill(586, "りゅうのはどう", "-", 14, 85, 100.0, 1, 10, 0, false, true, -1, 0.0, -1, 0.0, -1, 0.0).uiObject())
        Mockito.`when`(attackSide.individual).thenReturn(IndividualPokemonForUI(
                0, -1, "unknown", "unknown", "unknown", SkillForUI(), SkillForUI(), SkillForUI(), SkillForUI(),
                252, 252, 0, 0, 0, 4, //31, 31, 31, 31, 31, 31,
                PokemonMasterDataForUI("718", "ジガルデ", "-", "-", 108, 100, 121, 81, 95, 95, "オーラブレイク", "-", "-", 8, 14, 305.0f)))

        val defenseSide = PowerMockito.mock(PokemonForBattle::class.java)
        Mockito.`when`(defenseSide.calcSpecialDefenseValue()).thenReturn(170.0)
        Mockito.`when`(defenseSide.calcSpecialDefenseValueCorrection(field)).thenReturn(4096.0)
        Mockito.`when`(defenseSide.getSpecialDefenseRankCorrection(Mockito.anyBoolean())).thenReturn(1.0)
        Mockito.`when`(defenseSide.noEffect(attackSide.skill, attackSide, field)).thenReturn(false)
        Mockito.`when`(defenseSide.side).thenReturn(PartyInBattle.OPPONENT_SIDE)
        Mockito.`when`(defenseSide.individual).thenReturn(IndividualPokemonForUI(
                0, -1, "unknown", "unknown", "unknown", SkillForUI(), SkillForUI(), SkillForUI(), SkillForUI(),
                252, 252, 0, 0, 0, 4, //31, 31, 31, 31, 31, 31,
                PokemonMasterDataForUI("009", "カメックス", "Blastoise", "-", 79, 83, 100, 85, 105, 78, "げきりゅう", "-", "あめうけざら", 2, -1, 85.5f)))

        Mockito.`when`(attackSide.determineSkillPower(defenseSide)).thenReturn(85)
        Mockito.`when`(attackSide.calcSpecialAttackValueCorrection(defenseSide, field)).thenReturn(4096.0)

        val calc = BattleCalculator(70, attackSide, defenseSide, field, object : BattleCalculator.EventListener {
            override fun onFinish(result: BattleResult) {}
        })
        val damages = calc.doSkill(attackSide, defenseSide, field, 0.0, true, true)
        println(damages)
        assertTrue(damages.containsKey(30))
        assertTrue(damages.containsKey(31))
        assertTrue(damages.containsKey(33))
        assertTrue(damages.containsKey(34))
        assertTrue(damages.containsKey(36))
        assertTrue(damages.containsKey(49))//critical
        assertTrue(damages.containsKey(51))//critical
    }

    @Test
    fun 検証スレテストケースその7() {
        val field = BattleField()

        val attackSide = PowerMockito.mock(PokemonForBattle::class.java)
        Mockito.`when`(attackSide.calcAttackValue()).thenReturn(150.0)
        Mockito.`when`(attackSide.typeBonus()).thenReturn(Pair(0, 1.5))
        Mockito.`when`(attackSide.getAttackRankCorrection(Mockito.anyBoolean())).thenReturn(1.0)
        Mockito.`when`(attackSide.side).thenReturn(PartyInBattle.MY_SIDE)
        Mockito.`when`(attackSide.status).thenReturn(-1)
        Mockito.`when`(attackSide.skill).thenReturn(Skill(216, "じゃれつく", "-", 17, 90, 90.0, 0, 10, 0, true, true, -1, 0.0, -1, 0.0, Rank.no(Rank.Code.mA), 10.0).uiObject())
        Mockito.`when`(attackSide.individual).thenReturn(IndividualPokemonForUI(
                0, -1, "unknown", "unknown", "unknown", SkillForUI(), SkillForUI(), SkillForUI(), SkillForUI(),
                252, 252, 0, 0, 0, 4, //31, 31, 31, 31, 31, 31,
                PokemonMasterDataForUI("303", "クチート", "Mawile", "-", 50, 85, 85, 55, 55, 50, "かいりきバサミ", "いかく", "ちからずく", 16, 17, 11.0f)))

        val defenseSide = PowerMockito.mock(PokemonForBattle::class.java)
        Mockito.`when`(defenseSide.calcDefenseValue()).thenReturn(137.0)
        Mockito.`when`(defenseSide.calcDefenseValueCorrection(attackSide)).thenReturn(8192.0)//ファーコート
        Mockito.`when`(defenseSide.getDefenseRankCorrection(Mockito.anyBoolean())).thenReturn(1.0)
        Mockito.`when`(defenseSide.noEffect(attackSide.skill, attackSide, field)).thenReturn(false)
        Mockito.`when`(defenseSide.side).thenReturn(PartyInBattle.OPPONENT_SIDE)
        Mockito.`when`(defenseSide.individual).thenReturn(IndividualPokemonForUI(
                0, -1, "unknown", "unknown", "unknown", SkillForUI(), SkillForUI(), SkillForUI(), SkillForUI(),
                252, 252, 0, 0, 0, 4, //31, 31, 31, 31, 31, 31,
                PokemonMasterDataForUI("703", "メレシー", "Carbink", "", 50, 50, 150, 50, 150, 50, "クリアボディ", "-", "がんじょう", 12, 17, 5.7f)))

        Mockito.`when`(attackSide.determineSkillPower(defenseSide)).thenReturn(90)
        Mockito.`when`(attackSide.calcAttackValueCorrection(defenseSide, field)).thenReturn(Pair(4096.0, false))

        val calc = BattleCalculator(50, attackSide, defenseSide, field, object : BattleCalculator.EventListener {
            override fun onFinish(result: BattleResult) {}
        })
        val damages = calc.doSkill(attackSide, defenseSide, field, 0.0, true, true)
        assertTrue(damages.containsKey(28))
        assertTrue(damages.containsKey(30))
        assertTrue(damages.containsKey(31))
        assertTrue(damages.containsKey(33))
        assertTrue(damages.containsKey(34))
    }

    @Test
    fun 検証スレテストケースその8() {
        val field = BattleField()
        field.terrain = BattleField.Terrain.GrassyTerrain

        val attackSide = PowerMockito.mock(PokemonForBattle::class.java)
        Mockito.`when`(attackSide.calcSpecialAttackValue()).thenReturn(81.0)
        Mockito.`when`(attackSide.typeBonus()).thenReturn(Pair(0, 1.0))
        Mockito.`when`(attackSide.getSpecialAttackRankCorrection(Mockito.anyBoolean())).thenReturn(1.0)
        Mockito.`when`(attackSide.side).thenReturn(PartyInBattle.MY_SIDE)
        Mockito.`when`(attackSide.status).thenReturn(-1)
        Mockito.`when`(attackSide.skill).thenReturn(Skill(549, "マジカルリーフ", "-", 4, 60, -1.0, 1, 20, 0, false, true, -1, 0.0, -1, 0.0, -1, 0.0).uiObject())
        Mockito.`when`(attackSide.individual).thenReturn(IndividualPokemonForUI(
                0, -1, "unknown", "unknown", "unknown", SkillForUI(), SkillForUI(), SkillForUI(), SkillForUI(),
                252, 252, 0, 0, 0, 4, //31, 31, 31, 31, 31, 31,
                PokemonMasterDataForUI("671", "フラージェス", "Flogres", "-", 78, 65, 68, 112, 154, 75, "フラワーベール", "-", "きょうせい", 17, -1, 10.0f)))

        val defenseSide = PowerMockito.mock(PokemonForBattle::class.java)
        Mockito.`when`(defenseSide.calcSpecialDefenseValue()).thenReturn(68.0)
        Mockito.`when`(defenseSide.calcSpecialDefenseValueCorrection(field)).thenReturn(4096.0)
        Mockito.`when`(defenseSide.getSpecialDefenseRankCorrection(Mockito.anyBoolean())).thenReturn(1.0)
        Mockito.`when`(defenseSide.noEffect(attackSide.skill, attackSide, field)).thenReturn(false)
        Mockito.`when`(defenseSide.side).thenReturn(PartyInBattle.OPPONENT_SIDE)
        Mockito.`when`(defenseSide.individual).thenReturn(IndividualPokemonForUI(
                0, -1, "unknown", "unknown", "unknown", SkillForUI(), SkillForUI(), SkillForUI(), SkillForUI(),
                252, 252, 0, 0, 0, 4, //31, 31, 31, 31, 31, 31,
                PokemonMasterDataForUI("678", "ニャオニクス", "Meowstic", "-", 74, 48, 76, 83, 81, 104, "するどいめ", "すりぬけ", "♂：いたずらごころ/♀：かちき", 10, -1, 8.5f)))

        Mockito.`when`(attackSide.determineSkillPower(defenseSide)).thenReturn(60)
        Mockito.`when`(attackSide.calcSpecialAttackValueCorrection(defenseSide, field)).thenReturn(4096.0)

        val calc = BattleCalculator(30, attackSide, defenseSide, field, object : BattleCalculator.EventListener {
            override fun onFinish(result: BattleResult) {}
        })
        val damages = calc.doSkill(attackSide, defenseSide, field, 0.0, true, true)
        assertTrue(damages.containsKey(27))
        assertTrue(damages.containsKey(28))
        assertTrue(damages.containsKey(29))
        assertTrue(damages.containsKey(30))
        assertTrue(damages.containsKey(31))
        assertTrue(damages.containsKey(32))
    }

    @Test
    fun 検証スレテストケースその9() {
        val field = BattleField()

        val attackSide = PowerMockito.mock(PokemonForBattle::class.java)
        Mockito.`when`(attackSide.calcAttackValue()).thenReturn(103.0)
        Mockito.`when`(attackSide.typeBonus()).thenReturn(Pair(0, 1.0))
        Mockito.`when`(attackSide.getAttackRankCorrection(Mockito.anyBoolean())).thenReturn(1.0)
        Mockito.`when`(attackSide.side).thenReturn(PartyInBattle.MY_SIDE)
        Mockito.`when`(attackSide.status).thenReturn(-1)
        Mockito.`when`(attackSide.skill).thenReturn(Skill(278, "だいばくはつ", "-", 0, 250, 100.0, 0, 5, 0, false, true, -1, 0.0, -1, 0.0, -1, 0.0).uiObject())
        Mockito.`when`(attackSide.individual).thenReturn(IndividualPokemonForUI(
                0, -1, "unknown", "unknown", "unknown", SkillForUI(), SkillForUI(), SkillForUI(), SkillForUI(),
                252, 252, 0, 0, 0, 4, //31, 31, 31, 31, 31, 31,
                PokemonMasterDataForUI("095", "イワーク", "-", "-", 35, 45, 160, 30, 45, 70, "いしあたま", "がんじょう", "くだけるよろい", 12, 8, 210.0f)))

        val defenseSide = PowerMockito.mock(PokemonForBattle::class.java)
        Mockito.`when`(defenseSide.calcDefenseValue()).thenReturn(115.0)
        Mockito.`when`(defenseSide.calcDefenseValueCorrection(attackSide)).thenReturn(4096.0)
        Mockito.`when`(defenseSide.getDefenseRankCorrection(Mockito.anyBoolean())).thenReturn(1.0)
        Mockito.`when`(defenseSide.noEffect(attackSide.skill, attackSide, field)).thenReturn(false)
        Mockito.`when`(defenseSide.side).thenReturn(PartyInBattle.OPPONENT_SIDE)
        Mockito.`when`(defenseSide.individual).thenReturn(IndividualPokemonForUI(
                0, -1, "unknown", "unknown", "unknown", SkillForUI(), SkillForUI(), SkillForUI(), SkillForUI(),
                252, 252, 0, 0, 0, 4, //31, 31, 31, 31, 31, 31,
                PokemonMasterDataForUI("652", "ブリガロン", "Chesnaught", "-", 88, 107, 122, 74, 75, 64, "しんりょく", "-", "ぼうだん", 4, 6, 90.0f)))

        val calc = BattleCalculator(50, attackSide, defenseSide, field, object : BattleCalculator.EventListener {
            override fun onFinish(result: BattleResult) {}
        })
        Mockito.`when`(attackSide.determineSkillPower(defenseSide)).thenReturn(250)
        Mockito.`when`(attackSide.calcAttackValueCorrection(defenseSide, field)).thenReturn(Pair(4096.0, false))

        val damages = calc.doSkill(attackSide, defenseSide, field, 0.0, true, true)
        assertTrue(damages.containsKey(98))
    }

    @Test
    fun 検証スレテストケースその10() {
        val field = BattleField()

        val attackSide = PowerMockito.mock(PokemonForBattle::class.java)
        Mockito.`when`(attackSide.calcAttackValue()).thenReturn(99.0)
        Mockito.`when`(attackSide.ability()).thenReturn("かたいツメ")
        Mockito.`when`(attackSide.typeBonus()).thenReturn(Pair(0, 1.0))
        Mockito.`when`(attackSide.getAttackRankCorrection(Mockito.anyBoolean())).thenReturn(1.0)
        Mockito.`when`(attackSide.side).thenReturn(PartyInBattle.MY_SIDE)
        Mockito.`when`(attackSide.status).thenReturn(-1)
        Mockito.`when`(attackSide.skill).thenReturn(Skill(126, "きりさく", "-", 0, 70, 100.0, 0, 20, 0, true, true, -1, 0.0, -1, 0.0, -1, 0.0).uiObject())
        Mockito.`when`(attackSide.individual).thenReturn(IndividualPokemonForUI(
                0, -1, "unknown", "unknown", "unknown", SkillForUI(), SkillForUI(), SkillForUI(), SkillForUI(),
                252, 252, 0, 0, 0, 4, //31, 31, 31, 31, 31, 31,
                PokemonMasterDataForUI("689", "ガメノデス", "Barbaracle", "-", 72, 105, 115, 54, 86, 68, "かたいツメ", "スナイパー", "わるいてぐせ", 12, 2, 96.0f)))

        val defenseSide = PowerMockito.mock(PokemonForBattle::class.java)
        Mockito.`when`(defenseSide.calcDefenseValue()).thenReturn(141.0)
        Mockito.`when`(defenseSide.calcDefenseValueCorrection(attackSide)).thenReturn(4096.0)
        Mockito.`when`(defenseSide.getDefenseRankCorrection(Mockito.anyBoolean())).thenReturn(1.0)
        Mockito.`when`(defenseSide.noEffect(attackSide.skill, attackSide, field)).thenReturn(false)
        Mockito.`when`(defenseSide.side).thenReturn(PartyInBattle.OPPONENT_SIDE)
        Mockito.`when`(defenseSide.individual).thenReturn(IndividualPokemonForUI(
                0, -1, "unknown", "unknown", "unknown", SkillForUI(), SkillForUI(), SkillForUI(), SkillForUI(),
                252, 252, 0, 0, 0, 4, //31, 31, 31, 31, 31, 31,
                PokemonMasterDataForUI("716", "ゼルネアス", "-", "-", 126, 131, 95, 131, 98, 99, "フェアリーオーラ", "-", "-", 17, -1, 215.0f)))

        Mockito.`when`(attackSide.determineSkillPower(defenseSide)).thenReturn(70)
        Mockito.`when`(attackSide.calcAttackValueCorrection(defenseSide, field)).thenReturn(Pair(4096.0, false))

        val calc = BattleCalculator(45, attackSide, defenseSide, field, object : BattleCalculator.EventListener {
            override fun onFinish(result: BattleResult) {}
        })
        val damages = calc.doSkill(attackSide, defenseSide, field, 0.0, true, true)
        assertTrue(damages.containsKey(22))
        assertTrue(damages.containsKey(23))
        assertTrue(damages.containsKey(24))
        assertTrue(damages.containsKey(25))
    }

    @Test
    fun 検証スレテストケースその11() {
        val field = BattleField()

        val attackSide = PowerMockito.mock(PokemonForBattle::class.java)
        Mockito.`when`(attackSide.calcSpecialAttackValue()).thenReturn(152.0)
        Mockito.`when`(attackSide.ability()).thenReturn("フェアリースキン")
        Mockito.`when`(attackSide.typeBonus()).thenReturn(Pair(0, 1.5))
        Mockito.`when`(attackSide.getSpecialAttackRankCorrection(Mockito.anyBoolean())).thenReturn(1.0)
        Mockito.`when`(attackSide.side).thenReturn(PartyInBattle.MY_SIDE)
        Mockito.`when`(attackSide.status).thenReturn(-1)
        Mockito.`when`(attackSide.skill).thenReturn(Skill(407, "はかいこうせん", "-", 0, 150, 90.0, 1, 5, 0, false, true, -1, 0.0, -1, 0.0, -1, 0.0).uiObject())
        Mockito.`when`(attackSide.individual).thenReturn(IndividualPokemonForUI(
                0, -1, "unknown", "unknown", "unknown", SkillForUI(), SkillForUI(), SkillForUI(), SkillForUI(),
                252, 252, 0, 0, 0, 4, //31, 31, 31, 31, 31, 31,
                PokemonMasterDataForUI("282", "サーナイト", "Gardevoir", "-", 68, 65, 65, 125, 115, 80, "トレース", "シンクロ", "テレパシー", 10, 17, 48.4f)))

        val defenseSide = PowerMockito.mock(PokemonForBattle::class.java)
        Mockito.`when`(defenseSide.calcSpecialDefenseValue()).thenReturn(227.0)
        Mockito.`when`(defenseSide.calcSpecialDefenseValueCorrection(field)).thenReturn(4096.0)
        Mockito.`when`(defenseSide.getSpecialDefenseRankCorrection(Mockito.anyBoolean())).thenReturn(1.0)
        Mockito.`when`(defenseSide.noEffect(attackSide.skill, attackSide, field)).thenReturn(false)
        Mockito.`when`(defenseSide.side).thenReturn(PartyInBattle.OPPONENT_SIDE)
        Mockito.`when`(defenseSide.individual).thenReturn(IndividualPokemonForUI(
                0, -1, "unknown", "unknown", "unknown", SkillForUI(), SkillForUI(), SkillForUI(), SkillForUI(),
                252, 252, 0, 0, 0, 4, //31, 31, 31, 31, 31, 31,
                PokemonMasterDataForUI("643", "レシラム", "Reshiram", "-", 100, 120, 100, 150, 120, 90, "ターボブレイズ", "-", "-", 14, 1, 330.0f)))

        Mockito.`when`(attackSide.determineSkillPower(defenseSide)).thenReturn(150)
        Mockito.`when`(attackSide.calcSpecialAttackValueCorrection(defenseSide, field)).thenReturn(4096.0)

        val calc = BattleCalculator(37, attackSide, defenseSide, field, object : BattleCalculator.EventListener {
            override fun onFinish(result: BattleResult) {}
        })
        val damages = calc.doSkill(attackSide, defenseSide, field, 0.0, true, true)
        assertTrue(damages.containsKey(54))
        assertTrue(damages.containsKey(57))
        assertTrue(damages.containsKey(58))
        assertTrue(damages.containsKey(60))
        assertTrue(damages.containsKey(63))
        assertTrue(damages.containsKey(90))//
    }

    @Test
    fun 検証スレテストケースその12() {
        val field = BattleField()

        val attackSide = PowerMockito.mock(PokemonForBattle::class.java)
        Mockito.`when`(attackSide.calcSpecialAttackValue()).thenReturn(104.0)
        Mockito.`when`(attackSide.typeBonus()).thenReturn(Pair(0, 1.5))
        Mockito.`when`(attackSide.getSpecialAttackRankCorrection(Mockito.anyBoolean())).thenReturn(1.0)
        Mockito.`when`(attackSide.side).thenReturn(PartyInBattle.MY_SIDE)
        Mockito.`when`(attackSide.status).thenReturn(-1)
        Mockito.`when`(attackSide.skill).thenReturn(Skill(390, "ねっとう", "-", 2, 80, 100.0, 1, 15, 0, false, true, StatusAilment.no(StatusAilment.Code.BURN), 30.0, -1, 0.0, -1, 0.0).uiObject())
        Mockito.`when`(attackSide.individual).thenReturn(IndividualPokemonForUI(
                0, -1, "unknown", "unknown", "unknown", SkillForUI(), SkillForUI(), SkillForUI(), SkillForUI(),
                252, 252, 0, 0, 0, 4, //31, 31, 31, 31, 31, 31,
                PokemonMasterDataForUI("593", "ブルンゲル", "Jellicent", "-", 100, 60, 70, 85, 105, 60, "ちょすい", "のろわれボディ", "しめりけ", 2, 13, 135.0f)))

        val defenseSide = PowerMockito.mock(PokemonForBattle::class.java)
        Mockito.`when`(defenseSide.calcSpecialDefenseValue()).thenReturn(227.0)
        Mockito.`when`(defenseSide.calcSpecialDefenseValueCorrection(field)).thenReturn(4096.0)
        Mockito.`when`(defenseSide.getSpecialDefenseRankCorrection(Mockito.anyBoolean())).thenReturn(1.0)
        Mockito.`when`(defenseSide.noEffect(attackSide.skill, attackSide, field)).thenReturn(false)
        Mockito.`when`(defenseSide.side).thenReturn(PartyInBattle.OPPONENT_SIDE)
        Mockito.`when`(defenseSide.individual).thenReturn(IndividualPokemonForUI(
                0, -1, "unknown", "unknown", "unknown", SkillForUI(), SkillForUI(), SkillForUI(), SkillForUI(),
                252, 252, 0, 0, 0, 4, //31, 31, 31, 31, 31, 31,
                PokemonMasterDataForUI("643", "レシラム", "Reshiram", "-", 100, 120, 100, 150, 120, 90, "ターボブレイズ", "-", "-", 14, 1, 330.0f)))

        Mockito.`when`(attackSide.determineSkillPower(defenseSide)).thenReturn(80)
        Mockito.`when`(attackSide.calcSpecialAttackValueCorrection(defenseSide, field)).thenReturn(4096.0)

        val calc = BattleCalculator(50, attackSide, defenseSide, field, object : BattleCalculator.EventListener {
            override fun onFinish(result: BattleResult) {}
        })
        val damages = calc.doSkill(attackSide, defenseSide, field, 0.0, true, true)
        assertTrue(damages.containsKey(22))
        assertTrue(damages.containsKey(24))
        assertTrue(damages.containsKey(25))
        assertTrue(damages.containsKey(27))
    }

    @Test
    fun 検証スレテストケースその13() {
        val field = BattleField()

        val attackSide = PowerMockito.mock(PokemonForBattle::class.java)
        Mockito.`when`(attackSide.calcAttackValue()).thenReturn(120.0)
        Mockito.`when`(attackSide.ability()).thenReturn("がんじょうあご")
        Mockito.`when`(attackSide.item).thenReturn("ひのたまプレート")
        Mockito.`when`(attackSide.typeBonus()).thenReturn(Pair(0, 1.5))
        Mockito.`when`(attackSide.getAttackRankCorrection(Mockito.anyBoolean())).thenReturn(1.0)
        Mockito.`when`(attackSide.side).thenReturn(PartyInBattle.MY_SIDE)
        Mockito.`when`(attackSide.status).thenReturn(-1)
        Mockito.`when`(attackSide.skill).thenReturn(Skill(453, "ほのおのキバ", "-", 1, 65, 95.0, 0, 15, 0, true, true, StatusAilment.no(StatusAilment.Code.BURN), 10.0, -1, 0.0, -1, 0.0).uiObject())
        Mockito.`when`(attackSide.individual).thenReturn(IndividualPokemonForUI(
                0, -1, "unknown", "unknown", "unknown", SkillForUI(), SkillForUI(), SkillForUI(), SkillForUI(),
                252, 252, 0, 0, 0, 4, //31, 31, 31, 31, 31, 31,
                PokemonMasterDataForUI("059", "ウインディ", "Arcanine", "-", 90, 110, 80, 100, 80, 95, "いかく", "もらいび", "せいぎのこころ", 1, -1, 155.0f)))

        val defenseSide = PowerMockito.mock(PokemonForBattle::class.java)
        Mockito.`when`(defenseSide.calcDefenseValue()).thenReturn(62.0)
        Mockito.`when`(defenseSide.ability()).thenReturn("たいねつ")
        Mockito.`when`(defenseSide.calcDefenseValueCorrection(attackSide)).thenReturn(4096.0)
        Mockito.`when`(defenseSide.getDefenseRankCorrection(Mockito.anyBoolean())).thenReturn(1.0)
        Mockito.`when`(defenseSide.noEffect(attackSide.skill, attackSide, field)).thenReturn(false)
        Mockito.`when`(defenseSide.side).thenReturn(PartyInBattle.OPPONENT_SIDE)
        Mockito.`when`(defenseSide.individual).thenReturn(IndividualPokemonForUI(
                0, -1, "unknown", "unknown", "unknown", SkillForUI(), SkillForUI(), SkillForUI(), SkillForUI(),
                252, 252, 0, 0, 0, 4, //31, 31, 31, 31, 31, 31,
                PokemonMasterDataForUI("113", "ラッキー", "-", "-", 250, 5, 5, 35, 105, 50, "しぜんかいふく", "てんのめぐみ", "いやしのこころ", 0, -1, 34.6f)))

        Mockito.`when`(attackSide.determineSkillPower(defenseSide)).thenReturn(65)
        Mockito.`when`(attackSide.calcAttackValueCorrection(defenseSide, field)).thenReturn(Pair(4096.0, false))

        val calc = BattleCalculator(52, attackSide, defenseSide, field, object : BattleCalculator.EventListener {
            override fun onFinish(result: BattleResult) {}
        })
        val damages = calc.doSkill(attackSide, defenseSide, field, 0.0, true, true)
        assertTrue(damages.containsKey(64))
        assertTrue(damages.containsKey(66))
        assertTrue(damages.containsKey(67))
        assertTrue(damages.containsKey(70))
        assertTrue(damages.containsKey(72))
        assertTrue(damages.containsKey(73))
        assertTrue(damages.containsKey(75))
        assertTrue(damages.containsKey(76))
        assertTrue(damages.containsKey(114))//
    }

    @Test
    fun 検証スレテストケースその14() {
        val field = BattleField()

        val attackSide = PowerMockito.mock(PokemonForBattle::class.java)
        Mockito.`when`(attackSide.calcAttackValue()).thenReturn(120.0)
        Mockito.`when`(attackSide.ability()).thenReturn("かたいツメ")
        Mockito.`when`(attackSide.item).thenReturn("ひのたまプレート")
        Mockito.`when`(attackSide.typeBonus()).thenReturn(Pair(0, 1.5))
        Mockito.`when`(attackSide.getAttackRankCorrection(Mockito.anyBoolean())).thenReturn(1.0)
        Mockito.`when`(attackSide.side).thenReturn(PartyInBattle.MY_SIDE)
        Mockito.`when`(attackSide.status).thenReturn(-1)
        Mockito.`when`(attackSide.skill).thenReturn(Skill(402, "ニトロチャージ", "-", 1, 50, 100.0, 0, 20, 0, true, true, -1, 0.0, Rank.no(Rank.Code.S), 100.0, -1, 0.0).uiObject())
        Mockito.`when`(attackSide.individual).thenReturn(IndividualPokemonForUI(
                0, -1, "unknown", "unknown", "unknown", SkillForUI(), SkillForUI(), SkillForUI(), SkillForUI(),
                252, 252, 0, 0, 0, 4, //31, 31, 31, 31, 31, 31,
                PokemonMasterDataForUI("059", "ウインディ", "Arcanine", "-", 90, 110, 80, 100, 80, 95, "いかく", "もらいび", "せいぎのこころ", 1, -1, 155.0f)))

        val defenseSide = PowerMockito.mock(PokemonForBattle::class.java)
        Mockito.`when`(defenseSide.calcDefenseValue()).thenReturn(62.0)
        Mockito.`when`(defenseSide.ability()).thenReturn("かんそうはだ")
        Mockito.`when`(defenseSide.calcDefenseValueCorrection(attackSide)).thenReturn(4096.0)
        Mockito.`when`(defenseSide.getDefenseRankCorrection(Mockito.anyBoolean())).thenReturn(1.0)
        Mockito.`when`(defenseSide.noEffect(attackSide.skill, attackSide, field)).thenReturn(false)
        Mockito.`when`(defenseSide.side).thenReturn(PartyInBattle.OPPONENT_SIDE)
        Mockito.`when`(defenseSide.individual).thenReturn(IndividualPokemonForUI(
                0, -1, "unknown", "unknown", "unknown", SkillForUI(), SkillForUI(), SkillForUI(), SkillForUI(),
                252, 252, 0, 0, 0, 4, //31, 31, 31, 31, 31, 31,
                PokemonMasterDataForUI("113", "ラッキー", "-", "-", 250, 5, 5, 35, 105, 50, "しぜんかいふく", "てんのめぐみ", "いやしのこころ", 0, -1, 34.6f)))

        Mockito.`when`(attackSide.determineSkillPower(defenseSide)).thenReturn(60)
        Mockito.`when`(attackSide.calcAttackValueCorrection(defenseSide, field)).thenReturn(Pair(4096.0, false))

        val calc = BattleCalculator(52, attackSide, defenseSide, field, object : BattleCalculator.EventListener {
            override fun onFinish(result: BattleResult) {}
        })
        val damages = calc.doSkill(attackSide, defenseSide, field, 0.0, true, true)
        assertTrue(damages.containsKey(109))
        assertTrue(damages.containsKey(106))
        assertTrue(damages.containsKey(114))
        assertTrue(damages.containsKey(118))
        assertTrue(damages.containsKey(123))
        assertTrue(damages.containsKey(124))
        assertTrue(damages.containsKey(126))
        assertTrue(damages.containsKey(165))//
    }

    @Test
    fun 検証スレテストケースその15() {
        val field = BattleField()

        val attackSide = PowerMockito.mock(PokemonForBattle::class.java)
        Mockito.`when`(attackSide.calcAttackValue()).thenReturn(73.0)
        Mockito.`when`(attackSide.ability()).thenReturn("はりきり")
        Mockito.`when`(attackSide.item).thenReturn("こだわりハチマキ")
        Mockito.`when`(attackSide.typeBonus()).thenReturn(Pair(0, 1.0))
        Mockito.`when`(attackSide.getAttackRankCorrection(Mockito.anyBoolean())).thenReturn(1.0)
        Mockito.`when`(attackSide.side).thenReturn(PartyInBattle.MY_SIDE)
        Mockito.`when`(attackSide.status).thenReturn(-1)
        Mockito.`when`(attackSide.skill).thenReturn(Skill(474, "パワーウィップ", "-", 4, 120, 85.0, 0, 10, 0, true, true, -1, 0.0, -1, 0.0, -1, 0.0).uiObject())
        Mockito.`when`(attackSide.individual).thenReturn(IndividualPokemonForUI(
                0, -1, "unknown", "unknown", "unknown", SkillForUI(), SkillForUI(), SkillForUI(), SkillForUI(),
                252, 252, 0, 0, 0, 4, //31, 31, 31, 31, 31, 31,
                PokemonMasterDataForUI("108", "ベロリンガ", "-", "-", 0, 0, 0, 0, 0, 0, "-", "-", "-", 0, -1, 65.5f)))

        val defenseSide = PowerMockito.mock(PokemonForBattle::class.java)
        Mockito.`when`(defenseSide.calcDefenseValue()).thenReturn(62.0)
        Mockito.`when`(defenseSide.calcDefenseValueCorrection(attackSide)).thenReturn(4096.0)
        Mockito.`when`(defenseSide.getDefenseRankCorrection(Mockito.anyBoolean())).thenReturn(1.0)
        Mockito.`when`(defenseSide.noEffect(attackSide.skill, attackSide, field)).thenReturn(false)
        Mockito.`when`(defenseSide.side).thenReturn(PartyInBattle.OPPONENT_SIDE)
        Mockito.`when`(defenseSide.individual).thenReturn(IndividualPokemonForUI(
                0, -1, "unknown", "unknown", "unknown", SkillForUI(), SkillForUI(), SkillForUI(), SkillForUI(),
                252, 252, 0, 0, 0, 4, //31, 31, 31, 31, 31, 31,
                PokemonMasterDataForUI("113", "ラッキー", "-", "-", 250, 5, 5, 35, 105, 50, "しぜんかいふく", "てんのめぐみ", "いやしのこころ", 0, -1, 34.6f)))

        Mockito.`when`(attackSide.determineSkillPower(defenseSide)).thenReturn(120)
        Mockito.`when`(attackSide.calcAttackValueCorrection(defenseSide, field)).thenReturn(Pair(6144.0, false))

        val calc = BattleCalculator(58, attackSide, defenseSide, field, object : BattleCalculator.EventListener {
            override fun onFinish(result: BattleResult) {}
        })
        val damages = calc.doSkill(attackSide, defenseSide, field, 0.0, true, true)
        assertTrue(damages.containsKey(136))
        assertTrue(damages.containsKey(139))
        assertTrue(damages.containsKey(143))
        assertTrue(damages.containsKey(144))
        assertTrue(damages.containsKey(152))
        assertTrue(damages.containsKey(159))
    }

    @Test
    fun 検証スレテストケースその16() {
        val field = BattleField()

        val attackSide = PowerMockito.mock(PokemonForBattle::class.java)
        Mockito.`when`(attackSide.calcAttackValue()).thenReturn(73.0)
        Mockito.`when`(attackSide.ability()).thenReturn("こんじょう")
        Mockito.`when`(attackSide.item).thenReturn("こだわりハチマキ")
        Mockito.`when`(attackSide.typeBonus()).thenReturn(Pair(0, 1.0))
        Mockito.`when`(attackSide.getAttackRankCorrection(Mockito.anyBoolean())).thenReturn(1.0)
        Mockito.`when`(attackSide.side).thenReturn(PartyInBattle.MY_SIDE)
        Mockito.`when`(attackSide.status).thenReturn(StatusAilment.no(StatusAilment.Code.PARALYSIS))
        Mockito.`when`(attackSide.skill).thenReturn(Skill(474, "パワーウィップ", "-", 4, 120, 85.0, 0, 10, 0, true, true, -1, 0.0, -1, 0.0, -1, 0.0).uiObject())
        Mockito.`when`(attackSide.individual).thenReturn(IndividualPokemonForUI(
                0, -1, "unknown", "unknown", "unknown", SkillForUI(), SkillForUI(), SkillForUI(), SkillForUI(),
                252, 252, 0, 0, 0, 4, //31, 31, 31, 31, 31, 31,
                PokemonMasterDataForUI("108", "ベロリンガ", "-", "-", 0, 0, 0, 0, 0, 0, "-", "-", "-", 0, -1, 65.5f)))

        val defenseSide = PowerMockito.mock(PokemonForBattle::class.java)
        Mockito.`when`(defenseSide.calcDefenseValue()).thenReturn(62.0)
        Mockito.`when`(defenseSide.calcDefenseValueCorrection(attackSide)).thenReturn(4096.0)
        Mockito.`when`(defenseSide.getDefenseRankCorrection(Mockito.anyBoolean())).thenReturn(1.0)
        Mockito.`when`(defenseSide.noEffect(attackSide.skill, attackSide, BattleField())).thenReturn(false)
        Mockito.`when`(defenseSide.side).thenReturn(PartyInBattle.OPPONENT_SIDE)
        Mockito.`when`(defenseSide.individual).thenReturn(IndividualPokemonForUI(
                0, -1, "unknown", "unknown", "unknown", SkillForUI(), SkillForUI(), SkillForUI(), SkillForUI(),
                252, 252, 0, 0, 0, 4, //31, 31, 31, 31, 31, 31,
                PokemonMasterDataForUI("113", "ラッキー", "-", "-", 250, 5, 5, 35, 105, 50, "しぜんかいふく", "てんのめぐみ", "いやしのこころ", 0, -1, 34.6f)))

        Mockito.`when`(attackSide.determineSkillPower(defenseSide)).thenReturn(120)
        Mockito.`when`(attackSide.calcAttackValueCorrection(defenseSide, field)).thenReturn(Pair(Math.round(4096.0.times(6144.0).div(4096.0).times(6144.0).div(4096.0)).toDouble(), false))

        val calc = BattleCalculator(58, attackSide, defenseSide, field, object : BattleCalculator.EventListener {
            override fun onFinish(result: BattleResult) {}
        })
        val damages = calc.doSkill(attackSide, defenseSide, field, 0.0, true, true)
        assertTrue(damages.containsKey(140))
        assertTrue(damages.containsKey(147))
        assertTrue(damages.containsKey(153))
        assertTrue(damages.containsKey(155))
        assertTrue(damages.containsKey(156))
        assertTrue(damages.containsKey(204))//
        assertTrue(damages.containsKey(230))//
    }

    @Test
    fun 検証スレテストケースその17() {
        val field = BattleField()

        val attackSide = PowerMockito.mock(PokemonForBattle::class.java)
        Mockito.`when`(attackSide.calcAttackValue()).thenReturn(90.0)
        Mockito.`when`(attackSide.typeBonus()).thenReturn(Pair(0, 1.5))
        Mockito.`when`(attackSide.getAttackRankCorrection(Mockito.anyBoolean())).thenReturn(1.0)
        Mockito.`when`(attackSide.side).thenReturn(PartyInBattle.MY_SIDE)
        Mockito.`when`(attackSide.status).thenReturn(StatusAilment.no(StatusAilment.Code.UNKNOWN))
        Mockito.`when`(attackSide.skill).thenReturn(Skill(255, "ジャイロボール", "-", 16, -1, 100.0, 0, 5, 0, true, true, -1, 0.0, -1, 0.0, -1, 0.0).uiObject())
        Mockito.`when`(attackSide.individual).thenReturn(IndividualPokemonForUI(
                0, -1, "unknown", "unknown", "unknown", SkillForUI(), SkillForUI(), SkillForUI(), SkillForUI(),
                252, 252, 0, 0, 0, 4, //31, 31, 31, 31, 31, 31,
                PokemonMasterDataForUI("413-2", "ミノマダム", "-", "ゴミのミノ", 60, 69, 95, 69, 95, 36, "きけんよち", "ぼうじん", "-", 11, 16, 6.5f)))

        val defenseSide = PowerMockito.mock(PokemonForBattle::class.java)
        Mockito.`when`(defenseSide.calcDefenseValue()).thenReturn(71.0)
        Mockito.`when`(defenseSide.calcDefenseValueCorrection(attackSide)).thenReturn(4096.0)
        Mockito.`when`(defenseSide.getDefenseRankCorrection(Mockito.anyBoolean())).thenReturn(1.0)
        Mockito.`when`(defenseSide.noEffect(attackSide.skill, attackSide, field)).thenReturn(false)
        Mockito.`when`(defenseSide.side).thenReturn(PartyInBattle.OPPONENT_SIDE)
        Mockito.`when`(defenseSide.individual).thenReturn(IndividualPokemonForUI(
                0, -1, "unknown", "unknown", "unknown", SkillForUI(), SkillForUI(), SkillForUI(), SkillForUI(),
                252, 252, 0, 0, 0, 4, //31, 31, 31, 31, 31, 31,
                PokemonMasterDataForUI("132", "メタモン", "Ditto", "-", 48, 48, 48, 48, 48, 48, "じゅうなん", "-", "かわりもの", 0, -1, 4.0f)))

        Mockito.`when`(attackSide.determineSkillPower(defenseSide)).thenReturn(51)
        Mockito.`when`(attackSide.calcAttackValueCorrection(defenseSide, field)).thenReturn(Pair(4096.0, false))

        val calc = BattleCalculator(50, attackSide, defenseSide, field, object : BattleCalculator.EventListener {
            override fun onFinish(result: BattleResult) {}
        })
        val damages = calc.doSkill(attackSide, defenseSide, field, 0.0, true, true)
        for (d in damages) println(d)
        assertTrue(damages.containsKey(45))
    }


    //以下第7世代
    @Test
    fun 検証スレテストケースその18() {
        val field = BattleField()

        val attackSide = PowerMockito.mock(PokemonForBattle::class.java)
        Mockito.`when`(attackSide.calcAttackValue()).thenReturn(49.0)
        Mockito.`when`(attackSide.typeBonus()).thenReturn(Pair(0, 1.0))
        Mockito.`when`(attackSide.getAttackRankCorrection(Mockito.anyBoolean())).thenReturn(1.0)
        Mockito.`when`(attackSide.side).thenReturn(PartyInBattle.MY_SIDE)
        Mockito.`when`(attackSide.status).thenReturn(StatusAilment.no(StatusAilment.Code.UNKNOWN))
        Mockito.`when`(attackSide.skill).thenReturn(Skill(197, "しっぺがえし", "-", 15, 50, 100.0, 0, 10, 0, true, true, -1, 0.0, -1, 0.0, -1, 0.0).uiObject())
        Mockito.`when`(attackSide.individual).thenReturn(IndividualPokemonForUI(
                0, -1, "unknown", "unknown", "unknown", SkillForUI(), SkillForUI(), SkillForUI(), SkillForUI(),
                252, 252, 0, 0, 0, 4, //31, 31, 31, 31, 31, 31,
                PokemonMasterDataForUI("094", "ゲンガー", "Gengar", "-", 60, 65, 60, 130, 75, 110, "のろわれボディ", "のろわれボディ", "のろわれボディ", 13, 7, 40.5f)))

        val defenseSide = PowerMockito.mock(PokemonForBattle::class.java)
        Mockito.`when`(defenseSide.calcDefenseValue()).thenReturn(75.0)
        Mockito.`when`(defenseSide.calcDefenseValueCorrection(attackSide)).thenReturn(6144.0)
        Mockito.`when`(defenseSide.getDefenseRankCorrection(Mockito.anyBoolean())).thenReturn(1.0)
        Mockito.`when`(defenseSide.noEffect(attackSide.skill, attackSide, field)).thenReturn(false)
        Mockito.`when`(defenseSide.side).thenReturn(PartyInBattle.OPPONENT_SIDE)
        Mockito.`when`(defenseSide.individual).thenReturn(IndividualPokemonForUI(
                0, -1, "unknown", "unknown", "unknown", SkillForUI(), SkillForUI(), SkillForUI(), SkillForUI(),
                252, 252, 0, 0, 0, 4, //31, 31, 31, 31, 31, 31,
                PokemonMasterDataForUI("042", "ゴルバット", "-", "-", 75, 80, 70, 65, 75, 90, "-", "-", "-", 7, 9, 55.0f)))

        Mockito.`when`(attackSide.determineSkillPower(defenseSide)).thenReturn(100)
        Mockito.`when`(attackSide.calcAttackValueCorrection(defenseSide, field)).thenReturn(Pair(4096.0, false))

        val calc = BattleCalculator(32, attackSide, defenseSide, field, object : BattleCalculator.EventListener {
            override fun onFinish(result: BattleResult) {}
        })
        val damages = calc.doSkill(attackSide, defenseSide, field, 0.0, true, true)
        for (d in damages) println(d)
        assertTrue(damages.containsKey(12))
        assertTrue(damages.containsKey(13))
    }

    @Test
    fun 検証スレテストケースその19() {
        val field = BattleField()

        val attackSide = PowerMockito.mock(PokemonForBattle::class.java)
        Mockito.`when`(attackSide.calcAttackValue()).thenReturn(49.0)
        Mockito.`when`(attackSide.typeBonus()).thenReturn(Pair(0, 1.5))
        Mockito.`when`(attackSide.getAttackRankCorrection(Mockito.anyBoolean())).thenReturn(1.0)
        Mockito.`when`(attackSide.side).thenReturn(PartyInBattle.MY_SIDE)
        Mockito.`when`(attackSide.status).thenReturn(StatusAilment.no(StatusAilment.Code.UNKNOWN))
        Mockito.`when`(attackSide.skill).thenReturn(Skill(249, "シャドーパンチ", "-", 13, 60, -1.0, 0, 20, 0, true, true, -1, 0.0, -1, 0.0, -1, 0.0).uiObject())
        Mockito.`when`(attackSide.individual).thenReturn(IndividualPokemonForUI(
                0, -1, "unknown", "unknown", "unknown", SkillForUI(), SkillForUI(), SkillForUI(), SkillForUI(),
                252, 252, 0, 0, 0, 4, //31, 31, 31, 31, 31, 31,
                PokemonMasterDataForUI("094", "ゲンガー", "Gengar", "-", 60, 65, 60, 130, 75, 110, "のろわれボディ", "のろわれボディ", "のろわれボディ", 13, 7, 40.5f)))

        val defenseSide = PowerMockito.mock(PokemonForBattle::class.java)
        Mockito.`when`(defenseSide.calcDefenseValue()).thenReturn(75.0)
        Mockito.`when`(defenseSide.calcDefenseValueCorrection(attackSide)).thenReturn(6144.0)
        Mockito.`when`(defenseSide.getDefenseRankCorrection(Mockito.anyBoolean())).thenReturn(1.0)
        Mockito.`when`(defenseSide.noEffect(attackSide.skill, attackSide, field)).thenReturn(false)
        Mockito.`when`(defenseSide.side).thenReturn(PartyInBattle.OPPONENT_SIDE)
        Mockito.`when`(defenseSide.individual).thenReturn(IndividualPokemonForUI(
                0, -1, "unknown", "unknown", "unknown", SkillForUI(), SkillForUI(), SkillForUI(), SkillForUI(),
                252, 252, 0, 0, 0, 4, //31, 31, 31, 31, 31, 31,
                PokemonMasterDataForUI("042", "ゴルバット", "-", "-", 75, 80, 70, 65, 75, 90, "-", "-", "-", 7, 9, 55.0f)))

        Mockito.`when`(attackSide.determineSkillPower(defenseSide)).thenReturn(60)
        Mockito.`when`(attackSide.calcAttackValueCorrection(defenseSide, field)).thenReturn(Pair(4096.0, false))

        val calc = BattleCalculator(32, attackSide, defenseSide, field, object : BattleCalculator.EventListener {
            override fun onFinish(result: BattleResult) {}
        })
        val damages = calc.doSkill(attackSide, defenseSide, field, 0.0, true, true)
        for (d in damages) println(d)
        assertTrue(damages.containsKey(10))
        assertTrue(damages.containsKey(12))
    }

}