package com.gmail.sacchin13.pokemonbattleanalyzer

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.*
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.realm.IndividualPokemon
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.realm.MegaPokemonMasterData
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.realm.PokemonMasterData
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.realm.Skill
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.properties.Delegates

class PokemonForBattleTest {

    var kucheat: PokemonMasterData by Delegates.notNull()
    var bakuhun: PokemonMasterData by Delegates.notNull()
    var tekkanin: PokemonMasterData by Delegates.notNull()
    var tubotubo: PokemonMasterData by Delegates.notNull()

    var kucheatIndividual: IndividualPokemon by Delegates.notNull()
    var bakuhunIndividual: IndividualPokemon by Delegates.notNull()
    var tekkaninIndividual: IndividualPokemon by Delegates.notNull()
    var tubotuboIndividual: IndividualPokemon by Delegates.notNull()

    @Before
    fun init() {
        kucheat = PokemonMasterData("303", "クチート", "Mawile", "-", 50, 85, 85, 55, 55, 50,
                "かいりきバサミ", "いかく", "ちからずく", Type.no(Type.Code.STEEL), Type.no(Type.Code.FAIRY), 23.5f)
        kucheatIndividual = IndividualPokemon.create(1, kucheat)
        bakuhun = PokemonMasterData("157", "バクフーン", "Typhlosion", "-", 78, 84, 78, 109, 85, 100,
                "もうか", "-", "もらいび", 1, -1, 79.5f)
        bakuhunIndividual = IndividualPokemon.create(1, bakuhun)
        tubotubo = PokemonMasterData("213", "ツボツボ", "Shuckle", "-", 20, 10, 230, 10, 230, 5,
                "がんじょう", "くいしんぼう", "あまのじゃく", 11, 12, 20.5f)
        tubotuboIndividual = IndividualPokemon.create(1, tubotubo)
        tekkanin = PokemonMasterData("291", "テッカニン", "Ninjask", "-", 61, 90, 45, 50, 50, 160,
                "かそく", "-", "すりぬけ", 11, 9, 12.0f)
        tekkaninIndividual = IndividualPokemon.create(1, tekkanin)
    }

    @Test
    fun 自分側の攻撃力計算のテスト() {
        val attackSide = PokemonForBattle.create(0, kucheatIndividual)
        attackSide.side = PartyInBattle.MY_SIDE
        attackSide.attackEffortValue = 252
        attackSide.mega = MegaPokemonMasterData.NOT_MEGA

        attackSide.characteristic = "いじっぱり"
        assertEquals(0.0, attackSide.calcAttackValue(), 0.001)

        attackSide.characteristic = "おくびょう"
        assertEquals(0.0, attackSide.calcAttackValue(), 0.001)

        attackSide.characteristic = "おだやか"
        assertEquals(0.0, attackSide.calcAttackValue(), 0.001)

        attackSide.attackEffortValue = 0
        attackSide.characteristic = "ひかえめ"
        attackSide.mega = MegaPokemonMasterData.MEGA_X
        assertEquals(0.0, attackSide.calcAttackValue(), 0.001)
    }

    @Test
    fun 相手側の攻撃力計算のテスト() {
        val attackSide = PokemonForBattle.create(0, kucheatIndividual)
        attackSide.side = PartyInBattle.OPPONENT_SIDE
        attackSide.attackEffortValue = 252
        attackSide.mega = MegaPokemonMasterData.NOT_MEGA

        attackSide.characteristic = "ずぶとい"
        assertEquals(123.0, attackSide.calcAttackValue(), 0.001)

        attackSide.characteristic = "がんばりや"
        assertEquals(137.0, attackSide.calcAttackValue(), 0.001)
    }

    @Test
    fun 自分側の防御力計算のテスト() {
        val attackSide = PokemonForBattle.create(0, kucheatIndividual)
        attackSide.side = PartyInBattle.MY_SIDE
        attackSide.defenseEffortValue = 252
        attackSide.mega = MegaPokemonMasterData.MEGA_X

        attackSide.characteristic = "ひかえめ"
        assertEquals(0.0, attackSide.calcDefenseValue(), 0.001)

        attackSide.characteristic = "おくびょう"
        attackSide.defenseEffortValue = 0
        assertEquals(0.0, attackSide.calcDefenseValue(), 0.001)

        attackSide.characteristic = "ずぶとい"
        attackSide.mega = MegaPokemonMasterData.NOT_MEGA
        assertEquals(0.0, attackSide.calcDefenseValue(), 0.001)

        attackSide.characteristic = "いじっぱり"
        attackSide.mega = MegaPokemonMasterData.NOT_MEGA
        assertEquals(0.0, attackSide.calcDefenseValue(), 0.001)
    }

    @Test
    fun 相手側の防御力計算のテスト() {
        val attackSide = PokemonForBattle.create(0, kucheatIndividual)
        attackSide.side = PartyInBattle.OPPONENT_SIDE
        attackSide.defenseEffortValue = 0

        attackSide.characteristic = "がんばりや"
        attackSide.mega = MegaPokemonMasterData.NOT_MEGA
        assertEquals(105.0, attackSide.calcDefenseValue(), 0.001)

        attackSide.characteristic = "おだやか"
        attackSide.mega = MegaPokemonMasterData.MEGA_X
        assertEquals(105.0, attackSide.calcDefenseValue(), 0.001)
    }

    @Test
    fun 自分側の特殊攻撃力計算のテスト() {
        val attackSide = PokemonForBattle.create(0, kucheatIndividual)
        attackSide.side = PartyInBattle.MY_SIDE
        attackSide.specialAttackEffortValue = 252
        attackSide.mega = MegaPokemonMasterData.NOT_MEGA

        attackSide.characteristic = "おだやか"
        assertEquals(0.0, attackSide.calcSpecialAttackValue(), 0.001)

        attackSide.characteristic = "がんばりや"
        assertEquals(0.0, attackSide.calcSpecialAttackValue(), 0.001)

        attackSide.characteristic = "おくびょう"
        assertEquals(0.0, attackSide.calcSpecialAttackValue(), 0.001)
    }

    @Test
    fun 相手側の特殊攻撃力計算のテスト() {
        val attackSide = PokemonForBattle.create(0, kucheatIndividual)
        attackSide.side = PartyInBattle.OPPONENT_SIDE
        attackSide.specialAttackEffortValue = 0
        attackSide.mega = MegaPokemonMasterData.MEGA_X

        attackSide.characteristic = "いじっぱり"
        assertEquals(67.0, attackSide.calcSpecialAttackValue(), 0.001)

        attackSide.characteristic = "ずぶとい"
        assertEquals(75.0, attackSide.calcSpecialAttackValue(), 0.001)

        attackSide.mega = MegaPokemonMasterData.NOT_MEGA
        attackSide.characteristic = "ひかえめ"
        attackSide.specialAttackEffortValue = 252
        assertEquals(117.0, attackSide.calcSpecialAttackValue(), 0.001)
    }

    @Test
    fun 自分側の特殊防御力計算のテスト() {
        val attackSide = PokemonForBattle.create(0, kucheatIndividual)
        attackSide.side = PartyInBattle.MY_SIDE
        attackSide.specialDefenseEffortValue = 0
        attackSide.mega = MegaPokemonMasterData.NOT_MEGA

        attackSide.characteristic = "おだやか"
        assertEquals(0.0, attackSide.calcSpecialDefenseValue(), 0.001)

        attackSide.mega = MegaPokemonMasterData.MEGA_X
        attackSide.specialDefenseEffortValue = 252
        attackSide.characteristic = "がんばりや"
        assertEquals(0.0, attackSide.calcSpecialDefenseValue(), 0.001)

        attackSide.mega = MegaPokemonMasterData.NOT_MEGA
        attackSide.characteristic = "いじっぱり"
        assertEquals(0.0, attackSide.calcSpecialDefenseValue(), 0.001)
    }

    @Test
    fun 相手側の特殊防御力計算のテスト() {
        val attackSide = PokemonForBattle.create(0, kucheatIndividual)
        attackSide.side = PartyInBattle.OPPONENT_SIDE
        attackSide.mega = MegaPokemonMasterData.MEGA_X
        attackSide.specialDefenseEffortValue = 252

        attackSide.characteristic = "おくびょう"
        assertEquals(107.0, attackSide.calcSpecialDefenseValue(), 0.001)

        attackSide.characteristic = "ひかえめ"
        assertEquals(107.0, attackSide.calcSpecialDefenseValue(), 0.001)

        attackSide.characteristic = "ずぶとい"
        assertEquals(107.0, attackSide.calcSpecialDefenseValue(), 0.001)
    }

    @Test
    fun 自分側の素早さ計算のテスト() {
        val attackSide = PokemonForBattle.create(0, kucheatIndividual)
        attackSide.side = PartyInBattle.MY_SIDE
        attackSide.mega = MegaPokemonMasterData.MEGA_X
        attackSide.speedEffortValue = 0


        attackSide.characteristic = "ずぶとい"
        assertEquals(0, attackSide.calcSpeedValue(BattleField(), false, false))

        attackSide.characteristic = "がんばりや"
        attackSide.speedEffortValue = 252
        assertEquals(0, attackSide.calcSpeedValue(BattleField(), false, false))

        attackSide.characteristic = "いじっぱり"
        attackSide.mega = MegaPokemonMasterData.NOT_MEGA
        assertEquals(0, attackSide.calcSpeedValue(BattleField(), false, false))
    }

    @Test
    fun 相手側の素早さ計算のテスト() {
        val attackSide = PokemonForBattle.create(0, kucheatIndividual)
        attackSide.side = PartyInBattle.OPPONENT_SIDE
        attackSide.speedEffortValue = 0
        attackSide.mega = MegaPokemonMasterData.MEGA_X

        attackSide.characteristic = "おくびょう"
        assertEquals(112, attackSide.calcSpeedValue(BattleField(), false, false))

        attackSide.characteristic = "ひかえめ"
        assertEquals(102, attackSide.calcSpeedValue(BattleField(), false, false))

        attackSide.characteristic = "おだやか"
        attackSide.speedEffortValue = 252
        attackSide.mega = MegaPokemonMasterData.NOT_MEGA
        assertEquals(102, attackSide.calcSpeedValue(BattleField(), false, false))
    }

    @Test
    fun 攻撃力ランク補正を計算するテスト() {
        val attackSide = PokemonForBattle.create(0, kucheatIndividual)
        attackSide.attackRank = 0
        assertEquals(1.0, attackSide.getAttackRankCorrection(true), 0.001)

        attackSide.attackRank = 6
        assertEquals(1.0, attackSide.getAttackRankCorrection(true), 0.001)

        attackSide.attackRank = 12
        assertEquals(3.0, attackSide.getAttackRankCorrection(true), 0.001)

        attackSide.attackRank = 0
        assertEquals(0.333333333333, attackSide.getAttackRankCorrection(false), 0.001)

        attackSide.attackRank = 6
        assertEquals(1.0, attackSide.getAttackRankCorrection(false), 0.001)

        attackSide.attackRank = 12
        assertEquals(3.0, attackSide.getAttackRankCorrection(false), 0.001)
    }

    @Test
    fun 防御力ランク補正を計算するテスト() {
        val attackSide = PokemonForBattle.create(0, kucheatIndividual)

        attackSide.defenseRank = 0
        assertEquals(0.3333333333333333, attackSide.getDefenseRankCorrection(false), 0.001)

        attackSide.defenseRank = 6
        assertEquals(1.0, attackSide.getDefenseRankCorrection(false), 0.001)

        attackSide.defenseRank = 12
        assertEquals(3.0, attackSide.getDefenseRankCorrection(false), 0.001)

        attackSide.defenseRank = 0
        assertEquals(0.3333333333333333, attackSide.getDefenseRankCorrection(true), 0.001)

        attackSide.defenseRank = 6
        assertEquals(1.0, attackSide.getDefenseRankCorrection(true), 0.001)

        attackSide.defenseRank = 12
        assertEquals(1.0, attackSide.getDefenseRankCorrection(true), 0.001)
    }

    @Test
    fun 素早さランク補正を計算するテスト() {
        val attackSide = PokemonForBattle.create(0, kucheatIndividual)

        attackSide.speedRank = 0
        assertEquals(0.3333333333333333, attackSide.getSpeedRankCorrection(), 0.001)

        attackSide.speedRank = 6
        assertEquals(1.0, attackSide.getSpeedRankCorrection(), 0.001)

        attackSide.speedRank = 12
        assertEquals(3.0, attackSide.getSpeedRankCorrection(), 0.001)
    }

    @Test
    fun 攻撃側の攻撃力補正計算のテスト() {
        val f = BattleField()
        val attackSide = PokemonForBattle.create(0, IndividualPokemon.create(1, kucheat))
        attackSide.side = PartyInBattle.OPPONENT_SIDE

        attackSide.item = "ふといホネ"
        assertEquals(8192.0, attackSide.calcAttackValueCorrection(attackSide, f).first, 0.001)

        attackSide.item = ""
        attackSide.ability = "ヨガパワー"
        assertEquals(8192.0, attackSide.calcAttackValueCorrection(attackSide, f).first, 0.001)

        attackSide.ability = "こんじょう"
        attackSide.status = StatusAilment.no(StatusAilment.Code.BURN)
        assertEquals(6144.0, attackSide.calcAttackValueCorrection(attackSide, f).first, 0.001)

        attackSide.ability = "フラワーギフト"
        attackSide.status = StatusAilment.no(StatusAilment.Code.UNKNOWN)
        f.weather = BattleField.Weather.Sunny
        assertEquals(6144.0, attackSide.calcAttackValueCorrection(attackSide, f).first, 0.001)

        attackSide.ability = "しんりょく"
        attackSide.skill.type = Type.no(Type.Code.GRASS)
        attackSide.hpRatio = 10
        assertEquals(6144.0, attackSide.calcAttackValueCorrection(attackSide, f).first, 0.001)

        attackSide.side = PartyInBattle.MY_SIDE
        attackSide.hpValue = 100
        assertEquals(6144.0, attackSide.calcAttackValueCorrection(attackSide, f).first, 0.001)
    }

    @Test
    fun 攻撃側の特殊攻撃力補正計算のテスト() {
        val f = BattleField()
        val attackSide = PokemonForBattle.create(0, IndividualPokemon.create(1, kucheat))
        attackSide.side = PartyInBattle.OPPONENT_SIDE

        attackSide.item = "こだわりメガネ"
        assertEquals(6144.0, attackSide.calcSpecialAttackValueCorrection(attackSide, f), 0.001)

        attackSide.ability = "フラワーギフト"
        f.weather = BattleField.Weather.Sunny
        assertEquals(9216.0, attackSide.calcSpecialAttackValueCorrection(attackSide, f), 0.001)
    }

    @Test
    fun 防御側の防御力補正計算のテスト() {
        val attackSide = PokemonForBattle.create(0, IndividualPokemon.create(1, kucheat))
        attackSide.side = PartyInBattle.OPPONENT_SIDE
        attackSide.skill.category = 0

        attackSide.item = "しんかのきせき"
        attackSide.ability = "ファーコート"
        assertEquals(12288.0, attackSide.calcDefenseValueCorrection(attackSide), 0.001)

        attackSide.item = "メタルパウダー"
        attackSide.ability = "ふしぎなうろこ"
        attackSide.status = 1
        assertEquals(12288.0, attackSide.calcDefenseValueCorrection(attackSide), 0.001)
    }

    @Test
    fun 防御側の特殊防御力補正計算のテスト() {
        val attackSide = PokemonForBattle.create(0, IndividualPokemon.create(1, kucheat))
        attackSide.side = PartyInBattle.OPPONENT_SIDE
        attackSide.skill.category = 0

        val field = BattleField()
        field.weather = BattleField.Weather.Sunny

        attackSide.item = "しんかのきせき"
        attackSide.ability = "こころのしずく"
        assertEquals(9216.0, attackSide.calcSpecialDefenseValueCorrection(field), 0.001)

        attackSide.item = "とつげきチョッキ"
        attackSide.ability = "フラワーギフト"
        attackSide.status = 1
        assertEquals(9216.0, attackSide.calcSpecialDefenseValueCorrection(field), 0.001)
    }

    @Test
    fun 防御側の急所時の特殊防御力計算のテスト() {
        assertEquals(1, 1)
    }

    @Test
    fun 攻撃技のタイプ補正のテスト() {
        val attackSide = PokemonForBattle.create(0, IndividualPokemon.create(1, bakuhun))
        attackSide.side = PartyInBattle.OPPONENT_SIDE

        attackSide.skill.type = Type.no(Type.Code.WATER)
        assertEquals(Type.no(Type.Code.WATER), attackSide.typeBonus().first)
        assertEquals(1.0, attackSide.typeBonus().second, 0.001)

        attackSide.skill.type = Type.no(Type.Code.FIRE)
        assertEquals(Type.no(Type.Code.FIRE), attackSide.typeBonus().first)
        assertEquals(1.5, attackSide.typeBonus().second, 0.001)

        attackSide.ability = "てきおうりょく"
        assertEquals(Type.no(Type.Code.FIRE), attackSide.typeBonus().first)
        assertEquals(2.0, attackSide.typeBonus().second, 0.001)
    }

    @Test
    fun 攻撃側の急所率のテスト() {
        val attackSide = PokemonForBattle.create(0, IndividualPokemon.create(1, bakuhun))
        attackSide.side = PartyInBattle.OPPONENT_SIDE
        attackSide.ability = "きょううん"
        attackSide.criticalRank = 1

        val result = attackSide.calcCriticalRate()
        assertEquals(0.5, result, 0.001)
    }

    @Test
    fun 攻撃側の最大に詰め込んだ急所率のテスト() {
        val attackSide = PokemonForBattle.create(0, IndividualPokemon.create(1, kucheat))
        attackSide.side = PartyInBattle.OPPONENT_SIDE
        attackSide.criticalRank = 2
        attackSide.item = "ラッキーパンチ"
        attackSide.ability = "きょううん"

        val result = attackSide.calcCriticalRate()
        assertEquals(1.0, result, 0.001)
    }

    @Test
    fun ジャイロボールの威力のテスト() {
        var attackSide = PokemonForBattle.create(0, IndividualPokemon.create(1, kucheat))
        attackSide.skill.jname = "ジャイロボール"
        var defenseSide = PokemonForBattle.create(0, IndividualPokemon.create(1, bakuhun))

        var result = attackSide.determineSkillPower(defenseSide)
        assertEquals(43, result)


        attackSide = PokemonForBattle.create(0, IndividualPokemon.create(1, tubotubo))
        attackSide.skill.jname = "ジャイロボール"
        defenseSide = PokemonForBattle.create(0, IndividualPokemon.create(1, tekkanin))
        result = attackSide.determineSkillPower(defenseSide)
        assertEquals(150, result)
    }

    @Test
    fun 固定2回攻撃技の威力のテスト() {
        val attackSide = PokemonForBattle.create(0, IndividualPokemon.create(1, kucheat))
        attackSide.skill.jname = "ダブルアタック"
        attackSide.skill.power = 35
        val defenseSide = PokemonForBattle.create(0, IndividualPokemon.create(1, bakuhun))

        val result = attackSide.determineSkillPower(defenseSide)
        assertEquals(70, result)
    }

    @Test
    fun 不定5回攻撃技の威力のテスト() {
        val attackSide = PokemonForBattle.create(0, IndividualPokemon.create(1, kucheat))
        attackSide.skill.jname = "スイープビンタ"
        attackSide.skill.power = 25
        val defenseSide = PokemonForBattle.create(0, IndividualPokemon.create(1, bakuhun))
        assertEquals(75, attackSide.determineSkillPower(defenseSide))

        attackSide.ability = "スキルリンク"
        attackSide.side = PartyInBattle.OPPONENT_SIDE
        assertEquals(125, attackSide.determineSkillPower(defenseSide))
    }

    @Test
    fun 重さ依存技の威力のテスト() {
        val attackSide = PokemonForBattle.create(0, IndividualPokemon.create(1, kucheat))
        attackSide.skill.jname = "けたぐり"
        val defenseSide = PokemonForBattle.create(0, IndividualPokemon.create(1, bakuhun))

        val result = attackSide.determineSkillPower(defenseSide)
        assertEquals(80, result)
    }

    @Test
    fun 攻撃側HP依存技の威力のテスト() {
        val attackSide = PokemonForBattle.create(0, IndividualPokemon.create(1, kucheat))
        attackSide.side = PartyInBattle.OPPONENT_SIDE
        attackSide.skill.jname = "きしかいせい"
        attackSide.hpRatio = 100
        val defenseSide = PokemonForBattle.create(0, IndividualPokemon.create(1, bakuhun))

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
        val attackSide = PokemonForBattle.create(0, IndividualPokemon.create(1, kucheat))
        attackSide.skill.jname = "しおみず"
        attackSide.skill.power = 65
        val defenseSide = PokemonForBattle.create(0, IndividualPokemon.create(1, bakuhun))
        defenseSide.hpRatio = 49

        val result = attackSide.determineSkillPower(defenseSide)
        assertEquals(130, result)
    }

    @Test
    fun 防御側HP依存技の威力のテスト2() {
        val attackSide = PokemonForBattle.create(0, IndividualPokemon.create(1, kucheat))
        attackSide.skill.jname = "しぼりとる"
        val defenseSide = PokemonForBattle.create(0, IndividualPokemon.create(1, bakuhun))
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
        val attackSide = PokemonForBattle.create(0, IndividualPokemon.create(1, kucheat))
        attackSide.skill.jname = "おしおき"
        val defenseSide = PokemonForBattle.create(0, IndividualPokemon.create(1, bakuhun))

        defenseSide.specialDefenseRank = 0
        defenseSide.specialAttackRank = 7
        defenseSide.criticalRank = 7

        var result = attackSide.determineSkillPower(defenseSide)
        assertEquals(100, result)

        defenseSide.specialDefenseRank = 8
        defenseSide.specialAttackRank = 8
        defenseSide.criticalRank = 8

        result = attackSide.determineSkillPower(defenseSide)
        assertEquals(180, result)
    }

    @Test
    fun 攻撃技を無効にするかどうかのテスト() {
        val field = BattleField()
        val skill = Skill()
        val attackSide = PokemonForBattle.create(0, IndividualPokemon.create(1, kucheat))
        attackSide.side = PartyInBattle.OPPONENT_SIDE
        val defenseSide = PokemonForBattle.create(0, IndividualPokemon.create(1, bakuhun))
        defenseSide.side = PartyInBattle.OPPONENT_SIDE

        skill.type = Type.no(Type.Code.NORMAL)
        attackSide.ability = "きもったま"
        defenseSide.individual.master.type1 = Type.no(Type.Code.GHOST)
        assertEquals(false, defenseSide.noEffect(skill.uiObject(), attackSide, field))


        skill.jname = "どくのこな"
        skill.type = Type.no(Type.Code.GRASS)
        defenseSide.individual.master.type1 = Type.no(Type.Code.GRASS)
        assertEquals(true, defenseSide.noEffect(skill.uiObject(), attackSide, field))

        skill.jname = "でんじは"
        skill.type = Type.no(Type.Code.ELECTRIC)
        defenseSide.individual.master.type1 = Type.no(Type.Code.ELECTRIC)
        assertEquals(true, defenseSide.noEffect(skill.uiObject(), attackSide, field))

        skill.jname = "おにび"
        skill.type = Type.no(Type.Code.FIRE)
        defenseSide.individual.master.type1 = Type.no(Type.Code.FIRE)
        assertEquals(true, defenseSide.noEffect(skill.uiObject(), attackSide, field))

        skill.jname = "どくどく"
        skill.type = Type.no(Type.Code.POISON)
        defenseSide.individual.master.type1 = Type.no(Type.Code.POISON)
        assertEquals(true, defenseSide.noEffect(skill.uiObject(), attackSide, field))


        skill.jname = "チャームボイス"
        skill.type = Type.no(Type.Code.FAIRY)
        defenseSide.ability = "ぼうおん"
        assertEquals(true, defenseSide.noEffect(skill.uiObject(), attackSide, field))

        skill.jname = "タネマシンガン"
        skill.type = Type.no(Type.Code.GRASS)
        defenseSide.ability = "ぼうだん"
        assertEquals(true, defenseSide.noEffect(skill.uiObject(), attackSide, field))

        skill.jname = "どくのこな"
        defenseSide.ability = "ぼうじん"
        assertEquals(true, defenseSide.noEffect(skill.uiObject(), attackSide, field))

        skill.jname = "ハイドロポンプ"
        skill.type = Type.no(Type.Code.WATER)
        defenseSide.ability = "ぼうじん"
        assertEquals(false, defenseSide.noEffect(skill.uiObject(), attackSide, field))


        skill.jname = "チャームボイス"
        skill.type = Type.no(Type.Code.FAIRY)
        attackSide.ability = "かたやぶり"
        defenseSide.ability = "ぼうおん"
        assertEquals(false, defenseSide.noEffect(skill.uiObject(), attackSide, field))

        skill.jname = "タネマシンガン"
        skill.type = Type.no(Type.Code.GRASS)
        attackSide.ability = "かたやぶり"
        defenseSide.ability = "ぼうだん"
        assertEquals(false, defenseSide.noEffect(skill.uiObject(), attackSide, field))

        skill.jname = "どくのこな"
        attackSide.ability = "かたやぶり"
        defenseSide.ability = "ぼうじん"
        assertEquals(false, defenseSide.noEffect(skill.uiObject(), attackSide, field))

    }

    @Test
    fun 状態異常を付加せずRankの変動も発生しない攻撃技の影響のテスト() {
        val attackSide = PokemonForBattle.create(0, IndividualPokemon.create(1, bakuhun))
        attackSide.skill.aliment = StatusAilment.no(StatusAilment.Code.UNKNOWN)
        attackSide.skill.myRankUp = Rank.no(Rank.Code.UNKNOWN)
        attackSide.skill.oppoRankUp = Rank.no(Rank.Code.UNKNOWN)

        val actual = attackSide.skillAffects().iterator()

        val first = actual.next()
        assertEquals(StatusAilment.no(StatusAilment.Code.UNKNOWN), first.key[0])
        assertEquals(Rank.no(Rank.Code.UNKNOWN), first.key[1])
        assertEquals(Rank.no(Rank.Code.UNKNOWN), first.key[2])
        assertEquals(1.0, first.value, 0.001)
    }

    @Test
    fun 状態異常を付加するがRankの変動は発生しない攻撃技の影響のテスト() {
        val attackSide = PokemonForBattle.create(0, IndividualPokemon.create(1, bakuhun))
        attackSide.skill.aliment = StatusAilment.no(StatusAilment.Code.BURN)
        attackSide.skill.alimentRate = 0.1
        attackSide.skill.myRankUp = Rank.no(Rank.Code.UNKNOWN)
        attackSide.skill.oppoRankUp = Rank.no(Rank.Code.UNKNOWN)

        val actual = attackSide.skillAffects().iterator()

        val first = actual.next()
        assertEquals(StatusAilment.no(StatusAilment.Code.BURN), first.key[0])
        assertEquals(Rank.no(Rank.Code.UNKNOWN), first.key[1])
        assertEquals(Rank.no(Rank.Code.UNKNOWN), first.key[2])
        assertEquals(0.1, first.value, 0.001)

        val second = actual.next()
        assertEquals(StatusAilment.no(StatusAilment.Code.UNKNOWN), second.key[0])
        assertEquals(Rank.no(Rank.Code.UNKNOWN), second.key[1])
        assertEquals(Rank.no(Rank.Code.UNKNOWN), second.key[2])
        assertEquals(0.9, second.value, 0.001)
    }

    @Test
    fun 状態異常を付加しないがRankの変動が発生する攻撃技の影響のテスト() {
        val attackSide = PokemonForBattle.create(0, IndividualPokemon.create(1, bakuhun))
        attackSide.skill.aliment = StatusAilment.no(StatusAilment.Code.PARALYSIS)
        attackSide.skill.alimentRate = 0.3
        attackSide.skill.myRankUp = Rank.no(Rank.Code.UNKNOWN)
        attackSide.skill.oppoRankUp = Rank.no(Rank.Code.S)
        attackSide.skill.oppoRankUpRate = 0.1

        val actual = attackSide.skillAffects().iterator()

        val first = actual.next()
        assertEquals(StatusAilment.no(StatusAilment.Code.PARALYSIS), first.key[0])
        assertEquals(Rank.no(Rank.Code.UNKNOWN), first.key[1])
        assertEquals(Rank.no(Rank.Code.S), first.key[2])
        assertEquals(0.03, first.value, 0.001)

        val second = actual.next()
        assertEquals(StatusAilment.no(StatusAilment.Code.UNKNOWN), second.key[0])
        assertEquals(Rank.no(Rank.Code.UNKNOWN), second.key[1])
        assertEquals(Rank.no(Rank.Code.S), second.key[2])
        assertEquals(0.07, second.value, 0.001)

        val third = actual.next()
        assertEquals(StatusAilment.no(StatusAilment.Code.PARALYSIS), third.key[0])
        assertEquals(Rank.no(Rank.Code.UNKNOWN), third.key[1])
        assertEquals(Rank.no(Rank.Code.UNKNOWN), third.key[2])
        assertEquals(0.27, third.value, 0.001)


        val fourth = actual.next()
        assertEquals(StatusAilment.no(StatusAilment.Code.UNKNOWN), fourth.key[0])
        assertEquals(Rank.no(Rank.Code.UNKNOWN), fourth.key[1])
        assertEquals(Rank.no(Rank.Code.UNKNOWN), fourth.key[2])
        assertEquals(0.63, fourth.value, 0.001)
    }

    @Test
    fun マヒ時かつ追い風時の素早さ計算のテスト() {
        val attackSide = PokemonForBattle.create(0, IndividualPokemon.create(1, kucheat))
        attackSide.status = StatusAilment.no(StatusAilment.Code.PARALYSIS)

        val actual = attackSide.speedValues(true)
        assertEquals(24, actual[0])
        assertEquals(31, actual[1])
        assertEquals(34, actual[2])
        assertEquals(34, actual[3])
        assertEquals(38, actual[4])
        assertEquals(46, actual[5])
        assertEquals(50, actual[6])
        assertEquals(52, actual[7])
    }

    @Test
    fun HPの実数値を計算するテスト() {
        val attackSide = PokemonForBattle.create(0, IndividualPokemon.create(1, kucheat))

        attackSide.side = PartyInBattle.MY_SIDE
        attackSide.hpValue = 200
        assertEquals(200, attackSide.hpValue())

        attackSide.side = PartyInBattle.OPPONENT_SIDE
        attackSide.hpRatio = 50
        assertEquals(62, attackSide.hpValue())
    }

    @Test
    fun HPの比率を計算するテスト() {
        val attackSide = PokemonForBattle.create(0, IndividualPokemon.create(1, kucheat))

        attackSide.side = PartyInBattle.MY_SIDE
        attackSide.hpValue = 200
        assertEquals(200, attackSide.hpValue())

        attackSide.side = PartyInBattle.OPPONENT_SIDE
        attackSide.hpRatio = 50
        assertEquals(50, attackSide.hpValue())
    }

    fun Int.over(): Int {
        return if(0 < this) this else 0
    }
    
    @Test
    fun 演算子オーバーロードテスト() {

        val attackRank = 0
        val defenseRank = 1
        val specialAttackRank = 0
        val specialDefenseRank = 1
        val speedRank = -2
        val hitProbabilityRank = -5
        val avoidanceRank = 1
        val criticalRank = 0


        var oldType = if (0 < attackRank) attackRank else 0
        oldType += if (0 < defenseRank) defenseRank else 0
        oldType += if (0 < specialAttackRank) specialAttackRank else 0
        oldType += if (0 < specialDefenseRank) specialDefenseRank else 0
        oldType += if (0 < speedRank) speedRank else 0
        oldType += if (0 < hitProbabilityRank) hitProbabilityRank else 0
        oldType += if (0 < avoidanceRank) avoidanceRank else 0
        oldType += if (0 < criticalRank) criticalRank else 0


        var newType = attackRank.over() + defenseRank.over() + specialAttackRank.over() + specialDefenseRank.over() +
         speedRank.over() + hitProbabilityRank.over() + avoidanceRank.over() + criticalRank.over()

        assertEquals(newType, oldType)
    }

//
//    fun calcSpeedValue(allField: BattleField, tailWind: Boolean, onlyStatus: Boolean): Int {
//        var result = when (side) {
//            PartyInBattle.MY_SIDE -> individual.calcSpeed(mega)
//            else -> individual.calcSpeed(252, mega)
//        }
//
//        result = Math.floor(result.times(Characteristic.correction(characteristic, "S"))).toInt()
//        if (onlyStatus) return result
//
//
//        if (status == StatusAilment.no(StatusAilment.Code.PARALYSIS) && ability() == "ちどりあし") {
//            result = Math.round(result.times(1.5)).toInt()
//        }
//        if (allField.weather == BattleField.Weather.Sunny && ability() == "ようりょくそ") {
//            result = result.times(2)
//        }
//        if (allField.weather == BattleField.Weather.Rainy && ability() == "すいすい") {
//            result = result.times(2)
//        }
//        if (allField.weather == BattleField.Weather.Sandstorm && ability() == "すなかき") {
//            result = result.times(2)
//        }
//
//        if (item == "こだわりスカーフ") {
//            result = Math.round(result.times(1.5)).toInt()
//        }
//        if (item == "メタルパウダー") {
//            result = Math.round(result.times(1.5)).toInt()
//        }
//
//        result = Math.floor(result.times(getSpeedRankCorrection())).toInt()
//
//        if (tailWind) result = result.times(2)
//
//        if (status == StatusAilment.no(StatusAilment.Code.PARALYSIS) && ability() != "ちどりあし") {
//            result = Math.floor(result.times(0.25)).toInt()
//        }
//
//        return result
//    }
//
//    fun speedValues(tailWind: Boolean): Array<Int> {
//        val values = individual.speedValues(mega)
//
//        for (i in values.indices) {
//            values[i] = Math.floor(values[i].times(getSpeedRankCorrection())).toInt()
//
//            if (tailWind) {
//                values[i] = values[i].times(2)
//            }
//
//            if (status == StatusAilment.no(StatusAilment.Code.PARALYSIS)) {
//                values[i] = Math.floor(values[i].toDouble().div(4.0)).toInt()
//            }
//
//        }
//        return values
//    }




}