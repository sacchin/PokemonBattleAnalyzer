package com.gmail.sacchin13.pokemonbattleanalyzer

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.*
import org.junit.Test as Test

import org.junit.Assert.assertEquals
import org.junit.Before
import kotlin.properties.Delegates

class PokemonForBattleTest {

    var kucheat: PokemonMasterData by Delegates.notNull()
    var bakuhun: PokemonMasterData by Delegates.notNull()
    var tekkanin: PokemonMasterData by Delegates.notNull()
    var tubotubo: PokemonMasterData by Delegates.notNull()

    @Before
    fun init(){
        kucheat = PokemonMasterData("303", "クチート", "Mawile", 50, 85, 85, 55, 55, 50,
                "かいりきバサミ", "いかく", "ちからずく", Type.no(Type.Code.STEEL), Type.no(Type.Code.FAIRY), 23.5f)
        bakuhun = PokemonMasterData("157", "バクフーン", "Typhlosion", 78, 84, 78, 109, 85, 100,
                "もうか", "-", "もらいび", 1, -1, 79.5f)
        tubotubo = PokemonMasterData("213", "ツボツボ", "Shuckle", 20, 10, 230, 10, 230, 5,
                "がんじょう", "くいしんぼう", "あまのじゃく", 11, 12, 20.5f)
        tekkanin = PokemonMasterData("291", "テッカニン", "Ninjask", 61, 90, 45, 50, 50, 160,
                "かそく", "-", "すりぬけ", 11, 9, 12.0f)
    }

    @Test
    fun 攻撃側の攻撃力計算のテスト() {
        val attackSide = PokemonForBattle.create(IndividualPBAPokemon.create(1, kucheat))
        attackSide.attackEffortValue = 252
        attackSide.ability = "ちからもち"
        attackSide.item = "こだわりハチマキ"
        attackSide.attackRank = 0

        //assertEquals(137, kucheat.getAttackValue(31, 252))

        val result = attackSide.calcAttackValue(false)
        assertEquals(411, result)
    }

    @Test
    fun 攻撃側の急所持の攻撃力計算のテスト() {
        val attackSide = PokemonForBattle.create(IndividualPBAPokemon.create(1, kucheat))
        attackSide.attackEffortValue = 252
        attackSide.attackRank = -3

        //assertEquals(137, kucheat.getAttackValue(31, 252))

        val result = attackSide.calcAttackValue(true)
        assertEquals(137, result)
    }

    @Test
    fun 攻撃側の状態異常時における攻撃力計算のテスト() {
        val attackSide = PokemonForBattle.create(IndividualPBAPokemon.create(1, kucheat))
        attackSide.attackEffortValue = 252
        attackSide.status = StatusAilment.no(StatusAilment.Code.BURN)
        attackSide.attackRank = 0

        val result = attackSide.calcAttackValue(false)
        assertEquals(68, result)
    }

    @Test
    fun 攻撃側の状態異常かつこんじょう時における攻撃力計算のテスト() {
        val attackSide = PokemonForBattle.create(IndividualPBAPokemon.create(1, kucheat))
        attackSide.attackEffortValue = 252
        attackSide.ability = "こんじょう"
        attackSide.status = StatusAilment.no(StatusAilment.Code.BURN)
        attackSide.attackRank = 0

        val result = attackSide.calcAttackValue(false)
        assertEquals(205, result)
    }


    @Test
    fun 攻撃側の特殊攻撃力計算のテスト() {
        val attackSide = PokemonForBattle.create(IndividualPBAPokemon.create(1, kucheat))
        attackSide.specialAttackEffortValue = 252
        attackSide.ability = "こころのしずく"
        attackSide.item = "こだわりメガネ"
        attackSide.specialAttackRank = -3

        //assertEquals(107, kucheat.getSpecialAttackValue(31, 252))

        val result = attackSide.calcSpecialAttackValue(false)
        assertEquals(120, result)
    }

    @Test
    fun 防御側の状態異常時の防御力計算のテスト() {
        val defenseSide = PokemonForBattle.create(IndividualPBAPokemon.create(1, kucheat))
        defenseSide.defenseEffortValue = 252
        defenseSide.ability = "ふしぎなうろこ"
        defenseSide.status = StatusAilment.no(StatusAilment.Code.POISON)

        //assertEquals(137, kucheat.getDefenseValue(31, 252))

        val result = defenseSide.calcDefenseValue(false)
        assertEquals(205, result)
    }

    @Test
    fun 防御側の特殊防御力計算のテスト() {
        val defenseSide = PokemonForBattle.create(IndividualPBAPokemon.create(1, kucheat))
        defenseSide.specialDefenseEffortValue = 252
        defenseSide.item = "しんかいのウロコ"
        defenseSide.status = StatusAilment.no(StatusAilment.Code.POISON)
        defenseSide.specialDefenseRank = 6

        //assertEquals(107, kucheat.getDefenseValue(31, 252))

        val result = defenseSide.calcSpecialDefenseValue(false)
        assertEquals(642, result)
    }

    @Test
    fun 防御側の急所時の特殊防御力計算のテスト() {
        val defenseSide = PokemonForBattle.create(IndividualPBAPokemon.create(1, kucheat))
        defenseSide.specialDefenseEffortValue = 252
        defenseSide.specialDefenseRank = 6

        //assertEquals(107, kucheat.getDefenseValue(31, 252))

        val result = defenseSide.calcSpecialDefenseValue(true)
        assertEquals(107, result)
    }

    @Test
    fun 攻撃側の急所率のテスト() {
        val attackSide = PokemonForBattle.create(IndividualPBAPokemon.create(1, kucheat))
        attackSide.criticalRank = 1
        attackSide.ability = "きょううん"

        val result = attackSide.calcCriticalRate()
        assertEquals(0.5f, result)
    }

    @Test
    fun 攻撃側の最大に詰め込んだ急所率のテスト() {
        val attackSide = PokemonForBattle.create(IndividualPBAPokemon.create(1, kucheat))
        attackSide.criticalRank = 2
        attackSide.item = "ラッキーパンチ"
        attackSide.ability = "きょううん"

        val result = attackSide.calcCriticalRate()
        assertEquals(1f, result)
    }

    @Test
    fun 素早さ計算のテスト() {
        val defenseSide = PokemonForBattle.create(IndividualPBAPokemon.create(1, kucheat))
        defenseSide.speedEffortValue = 252
        defenseSide.speedRank = 1

        //assertEquals(107, kucheat.getDefenseValue(31, 252))

        val result = defenseSide.calcSpeedValue()
        assertEquals(136, result)
    }

    @Test
    fun ジャイロボールの威力のテスト() {
        var attackSide = PokemonForBattle.create(IndividualPBAPokemon.create(1, kucheat))
        attackSide.skill.jname = "ジャイロボール"
        var defenseSide = PokemonForBattle.create(IndividualPBAPokemon.create(1, bakuhun))

        var result = attackSide.determineSkillPower(defenseSide)
        assertEquals(43, result)


        attackSide = PokemonForBattle.create(IndividualPBAPokemon.create(1, tubotubo))
        attackSide.skill.jname = "ジャイロボール"
        defenseSide = PokemonForBattle.create(IndividualPBAPokemon.create(1, tekkanin))
        result = attackSide.determineSkillPower(defenseSide)
        assertEquals(150, result)
    }

    @Test
    fun 固定2回攻撃技の威力のテスト() {
        val attackSide = PokemonForBattle.create(IndividualPBAPokemon.create(1, kucheat))
        attackSide.skill.jname = "ダブルアタック"
        attackSide.skill.power = 35
        val defenseSide = PokemonForBattle.create(IndividualPBAPokemon.create(1, bakuhun))

        val result = attackSide.determineSkillPower(defenseSide)
        assertEquals(70, result)
    }

    @Test
    fun 不定5回攻撃技の威力のテスト() {
        val attackSide = PokemonForBattle.create(IndividualPBAPokemon.create(1, kucheat))
        attackSide.skill.jname = "スイープビンタ"
        attackSide.skill.power = 25
        attackSide.ability = "スキルリンク"
        val defenseSide = PokemonForBattle.create(IndividualPBAPokemon.create(1, bakuhun))

        val result = attackSide.determineSkillPower(defenseSide)
        assertEquals(125, result)
    }

    @Test
    fun 重さ依存技の威力のテスト() {
        val attackSide = PokemonForBattle.create(IndividualPBAPokemon.create(1, kucheat))
        attackSide.skill.jname = "けたぐり"
        val defenseSide = PokemonForBattle.create(IndividualPBAPokemon.create(1, bakuhun))

        val result = attackSide.determineSkillPower(defenseSide)
        assertEquals(80, result)
    }

    @Test
    fun 攻撃側HP依存技の威力のテスト() {
        val attackSide = PokemonForBattle.create(IndividualPBAPokemon.create(1, kucheat))
        attackSide.skill.jname = "きしかいせい"
        attackSide.hpRatio = 100
        val defenseSide = PokemonForBattle.create(IndividualPBAPokemon.create(1, bakuhun))

        var result = attackSide.determineSkillPower(defenseSide)
        assertEquals(20, result)

        attackSide.hpRatio = 50
        result = attackSide.determineSkillPower(defenseSide)
        assertEquals(40, result)

        attackSide.hpRatio = 1
        result = attackSide.determineSkillPower(defenseSide)
        assertEquals(200, result)
    }

    @Test
    fun 防御側HP依存技の威力のテスト1() {
        val attackSide = PokemonForBattle.create(IndividualPBAPokemon.create(1, kucheat))
        attackSide.skill.jname = "しおみず"
        attackSide.skill.power = 65
        val defenseSide = PokemonForBattle.create(IndividualPBAPokemon.create(1, bakuhun))
        defenseSide.hpRatio = 49

        val result = attackSide.determineSkillPower(defenseSide)
        assertEquals(130, result)
    }

    @Test
    fun 防御側HP依存技の威力のテスト2() {
        val attackSide = PokemonForBattle.create(IndividualPBAPokemon.create(1, kucheat))
        attackSide.skill.jname = "しぼりとる"
        val defenseSide = PokemonForBattle.create(IndividualPBAPokemon.create(1, bakuhun))
        defenseSide.hpRatio = 100

        var result = attackSide.determineSkillPower(defenseSide)
        assertEquals(120, result)

        defenseSide.hpRatio = 50
        result = attackSide.determineSkillPower(defenseSide)
        assertEquals(61, result)


        defenseSide.hpRatio = 5
        result = attackSide.determineSkillPower(defenseSide)
        assertEquals(7, result)
    }


    @Test
    fun 防御側ランク依存技の威力のテスト() {
        val attackSide = PokemonForBattle.create(IndividualPBAPokemon.create(1, kucheat))
        attackSide.skill.jname = "おしおき"
        val defenseSide = PokemonForBattle.create(IndividualPBAPokemon.create(1, bakuhun))
        defenseSide.attackRank = 1
        defenseSide.defenseRank = 1
        defenseSide.speedRank = -1

        var result = attackSide.determineSkillPower(defenseSide)
        assertEquals(100, result)

        defenseSide.specialDefenseRank = 2
        defenseSide.specialAttackRank = 2
        defenseSide.criticalRank = 2

        result = attackSide.determineSkillPower(defenseSide)
        assertEquals(200, result)
    }


}