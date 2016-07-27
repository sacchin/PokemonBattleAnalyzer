package com.gmail.sacchin13.pokemonbattleanalyzer

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.*
import com.gmail.sacchin13.pokemonbattleanalyzer.logic.BattleCalculator
import org.junit.Test as Test

import org.junit.Assert.assertEquals

class BattleCalculatorTest {
    @Test
    fun タイプ別強化アイテムと先攻後攻のテスト() {
        val attackSide = PokemonForBattle.create(PokemonMasterData())
        attackSide.item = "くろいメガネ"
        attackSide.skill = Skill(197, "しっぺがえし", "", 15, 50, 100, 0, 10, 0, true, true)

        val defenseSide = PokemonForBattle.create(PokemonMasterData())
        val field = BattleField()

        val result = BattleCalculator.companion.calcSkillPower(attackSide, defenseSide, field, false, false)
        assertEquals(120, result)
    }

    @Test
    fun タイプ半減特性と非ダメージによる威力上昇技のテスト() {
        val attackSide = PokemonForBattle.create(PokemonMasterData())
        attackSide.item = "ものしりメガネ"
        attackSide.skill = Skill(575, "ゆきなだれ", "", 5, 60, 100, 0, 10, 0, true, true)

        val defenseSide = PokemonForBattle.create(PokemonMasterData())
        defenseSide.ability = "あついしぼう"

        val field = BattleField()

        val result = BattleCalculator.companion.calcSkillPower(attackSide, defenseSide, field,  false, true)
        assertEquals(60, result)
    }

    @Test
    fun 物理特殊依存アイテムとテクニシャンのテスト() {
        val attackSide = PokemonForBattle.create(PokemonMasterData())
        attackSide.item = "ちからのハチマキ"
        attackSide.ability = "テクニシャン"
        attackSide.skill = Skill(553, "マッハパンチ", "", 6, 40, 100, 0, 30, 0, true, true)

        val defenseSide = PokemonForBattle.create(PokemonMasterData())

        val field = BattleField()

        val result = BattleCalculator.companion.calcSkillPower(attackSide, defenseSide, field,  false, true)
        assertEquals(66, result)
    }

    @Test
    fun HP依存技と非ダメージ側の特性のテスト() {
        val attackSide = PokemonForBattle.create(PokemonMasterData())
        attackSide.ability = "もうか"
        attackSide.hpRatio = 29
        attackSide.skill = Skill(494, "ブラストバーン", "", 1, 150, 90, 1, 5, 0, false, true)

        val defenseSide = PokemonForBattle.create(PokemonMasterData())
        defenseSide.ability = "かんそうはだ"

        val field = BattleField()

        val result = BattleCalculator.companion.calcSkillPower(attackSide, defenseSide, field,  false, true)
        assertEquals(280, result)
    }

    @Test
    fun HP依存技ふんかの正常系テスト() {
        val attackSide = PokemonForBattle.create(PokemonMasterData())
        attackSide.hpRatio = 100
        attackSide.skill = Skill(440, "ふんか", "", 1, 150, 100, 1, 5, 0, false, true)

        val defenseSide = PokemonForBattle.create(PokemonMasterData())

        val field = BattleField()

        val result = BattleCalculator.companion.calcSkillPower(attackSide, defenseSide, field, false, true)
        assertEquals(150, result)
    }

}