package com.gmail.sacchin13.pokemonbattleanalyzer

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.properties.Delegates

class PokemonForBattleTest {

    var kucheat: PokemonMasterData by Delegates.notNull()
    var bakuhun: PokemonMasterData by Delegates.notNull()
    var tekkanin: PokemonMasterData by Delegates.notNull()
    var tubotubo: PokemonMasterData by Delegates.notNull()

    @Before
    fun init() {
        kucheat = PokemonMasterData("303", "クチート", "Mawile", "-", 50, 85, 85, 55, 55, 50,
                "かいりきバサミ", "いかく", "ちからずく", Type.no(Type.Code.STEEL), Type.no(Type.Code.FAIRY), 23.5f)
        bakuhun = PokemonMasterData("157", "バクフーン", "Typhlosion", "-", 78, 84, 78, 109, 85, 100,
                "もうか", "-", "もらいび", 1, -1, 79.5f)
        tubotubo = PokemonMasterData("213", "ツボツボ", "Shuckle", "-", 20, 10, 230, 10, 230, 5,
                "がんじょう", "くいしんぼう", "あまのじゃく", 11, 12, 20.5f)
        tekkanin = PokemonMasterData("291", "テッカニン", "Ninjask", "-", 61, 90, 45, 50, 50, 160,
                "かそく", "-", "すりぬけ", 11, 9, 12.0f)
    }

    @Test
    fun 自分側の攻撃力計算のテスト() {
        val attackSide = PokemonForBattle.create(0, IndividualPokemon.create(1, kucheat))
        attackSide.side = PartyInBattle.MY_SIDE
        attackSide.individual.attackValue = 137

        val actual = attackSide.calcAttackValue()
        assertEquals(137.0, actual, 0.001)
    }

    @Test
    fun 相手側の攻撃力計算のテスト() {
        val attackSide = PokemonForBattle.create(0, IndividualPokemon.create(1, kucheat))
        attackSide.side = PartyInBattle.OPPONENT_SIDE
        attackSide.characteristic = "いじっぱり"

        val actual = attackSide.calcAttackValue()
        assertEquals(150.0, actual, 0.001)
    }


    @Test
    fun 自分側の防御力計算のテスト() {
        val attackSide = PokemonForBattle.create(0, IndividualPokemon.create(1, kucheat))
        attackSide.side = PartyInBattle.MY_SIDE
        attackSide.individual.defenseValue = 137

        val actual = attackSide.calcDefenseValue()
        assertEquals(137.0, actual, 0.001)
    }

    @Test
    fun 相手側の防御力計算のテスト() {
        val attackSide = PokemonForBattle.create(0, IndividualPokemon.create(1, kucheat))
        attackSide.side = PartyInBattle.OPPONENT_SIDE
        attackSide.characteristic = "さみしがり"

        val actual = attackSide.calcDefenseValue()
        assertEquals(123.0, actual, 0.001)
    }

    @Test
    fun 自分側の特殊攻撃力計算のテスト() {
        val attackSide = PokemonForBattle.create(0, IndividualPokemon.create(1, kucheat))
        attackSide.side = PartyInBattle.MY_SIDE
        attackSide.individual.specialAttackValue = 107

        val actual = attackSide.calcSpecialAttackValue()
        assertEquals(107.0, actual, 0.001)
    }


    @Test
    fun 相手側の特殊攻撃力計算のテスト() {
        val attackSide = PokemonForBattle.create(0, IndividualPokemon.create(1, kucheat))
        attackSide.side = PartyInBattle.OPPONENT_SIDE
        attackSide.characteristic = "ひかえめ"

        val actual = attackSide.calcSpecialAttackValue()
        assertEquals(117.0, actual, 0.001)
    }


    @Test
    fun 自分側の特殊防御力計算のテスト() {
        val attackSide = PokemonForBattle.create(0, IndividualPokemon.create(1, kucheat))
        attackSide.side = PartyInBattle.MY_SIDE
        attackSide.individual.specialDefenseValue = 107

        val actual = attackSide.calcSpecialDefenseValue()
        assertEquals(107.0, actual, 0.001)
    }

    @Test
    fun 相手側の特殊防御力計算のテスト() {
        val attackSide = PokemonForBattle.create(0, IndividualPokemon.create(1, kucheat))
        attackSide.side = PartyInBattle.OPPONENT_SIDE
        attackSide.characteristic = "しんちょう"

        val actual = attackSide.calcDefenseValue()
        assertEquals(137.0, actual, 0.001)
    }

    @Test
    fun 自分側の素早さ計算のテスト() {
        val attackSide = PokemonForBattle.create(0, IndividualPokemon.create(1, kucheat))
        attackSide.item = "こだわりスカーフ"
        attackSide.side = PartyInBattle.MY_SIDE
        attackSide.individual.speedValue = 102

        val actual = attackSide.calcSpeedValue()
        assertEquals(153.0, actual, 0.001)
    }

    @Test
    fun 相手側の素早さ計算のテスト() {
        val attackSide = PokemonForBattle.create(0, IndividualPokemon.create(1, kucheat))
        attackSide.item = "こだわりスカーフ"
        attackSide.side = PartyInBattle.OPPONENT_SIDE
        attackSide.characteristic = "ようき"

        val actual = attackSide.calcSpeedValue()
        assertEquals(168.0, actual, 0.001)
    }

    @Test
    fun 攻撃側の攻撃力補正計算のテスト() {
        val attackSide = PokemonForBattle.create(0, IndividualPokemon.create(1, kucheat))

        val actual = attackSide.calcAttackValueCorrection(attackSide)
        assertEquals(4096.0, actual, 0.001)
    }

    @Test
    fun 攻撃側の急所持の攻撃力計算のテスト() {
        val attackSide = PokemonForBattle.create(0, IndividualPokemon.create(1, kucheat))
        attackSide.attackEffortValue = 252
        attackSide.attackRank = -3

        //assertEquals(137, kucheat.getAttackValue(31, 252))

        val result = attackSide.calcAttackValue()
        assertEquals(137.0, result, 1.0)
    }

//    @Test
//    fun 攻撃側の状態異常時における攻撃力計算のテスト() {
//        val attackSide = PokemonForBattle.create(0, IndividualPokemon.create(1, kucheat))
//        attackSide.attackEffortValue = 252
//        attackSide.status = StatusAilment.no(StatusAilment.Code.BURN)
//        attackSide.attackRank = 0
//
//        val result = attackSide.calcAttackValue()
//        assertEquals(68, result)
//    }

//    @Test
//    fun 攻撃側の状態異常かつこんじょう時における攻撃力計算のテスト() {
//        val attackSide = PokemonForBattle.create(0, IndividualPokemon.create(1, kucheat))
//        attackSide.attackEffortValue = 252
//        attackSide.ability = "こんじょう"
//        attackSide.status = StatusAilment.no(StatusAilment.Code.BURN)
//        attackSide.attackRank = 0
//
//        val result = attackSide.calcAttackValue()
//        assertEquals(205, result)
//    }

    @Test
    fun 攻撃側の特殊攻撃力補正計算のテスト() {
        val attackSide = PokemonForBattle.create(0, IndividualPokemon.create(1, kucheat))

        val actual = attackSide.calcSpecialAttackValueCorrection(attackSide)
        assertEquals(4096.0, actual, 0.001)
    }

    @Test
    fun 防御側の状態異常時の防御力計算のテスト() {
        val defenseSide = PokemonForBattle.create(0, IndividualPokemon.create(1, kucheat))
        defenseSide.defenseEffortValue = 252
        defenseSide.ability = "ふしぎなうろこ"
        defenseSide.status = StatusAilment.no(StatusAilment.Code.POISON)

        assertEquals(137, kucheat.specialDefense(31, 252))

        //val result = defenseSide.calcDefenseValue()
        //assertEquals(205, result)
    }

    @Test
    fun 防御側の防御力補正計算のテスト() {
        val attackSide = PokemonForBattle.create(0, IndividualPokemon.create(1, kucheat))

        val actual = attackSide.calcDefenseValueCorrection(attackSide)
        assertEquals(4096.0, actual, 0.001)

    }

    @Test
    fun 防御側の特殊防御力補正計算のテスト() {
        val attackSide = PokemonForBattle.create(0, IndividualPokemon.create(1, kucheat))

        val actual = attackSide.calcSpecialDefenseValueCorrection()
        assertEquals(4096.0, actual, 0.001)

    }

    @Test
    fun 防御側の急所時の特殊防御力計算のテスト() {
        val defenseSide = PokemonForBattle.create(0, IndividualPokemon.create(1, kucheat))
        defenseSide.specialDefenseEffortValue = 252
        defenseSide.specialDefenseRank = 6

        //assertEquals(107, kucheat.getDefenseValue(31, 252))

        val result = defenseSide.calcSpecialDefenseValue()
        assertEquals(107.0, result, 1.0)
    }

    @Test
    fun 攻撃側の急所率のテスト() {
        val attackSide = PokemonForBattle.create(0, IndividualPokemon.create(1, kucheat))
        attackSide.criticalRank = 1
        attackSide.ability = "きょううん"

        val result = attackSide.calcCriticalRate()
        assertEquals(0.5, result, 0.001)
    }

    @Test
    fun 攻撃側の最大に詰め込んだ急所率のテスト() {
        val attackSide = PokemonForBattle.create(0, IndividualPokemon.create(1, kucheat))
        attackSide.criticalRank = 2
        attackSide.item = "ラッキーパンチ"
        attackSide.ability = "きょううん"

        val result = attackSide.calcCriticalRate()
        assertEquals(1.0, result, 0.001)
    }

    @Test
    fun 素早さ計算のテスト() {
//        val defenseSide = PokemonForBattle.create(0, IndividualPokemon.create(1, kucheat))
//        defenseSide.speedEffortValue = 252
//        defenseSide.speedRank = 1
//
//        assertEquals(107, kucheat.getDefenseValue(31, 252))
//
//        val result = defenseSide.calcSpeedValue()
//        assertEquals(136, result)
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
        attackSide.ability = "スキルリンク"
        val defenseSide = PokemonForBattle.create(0, IndividualPokemon.create(1, bakuhun))

        val result = attackSide.determineSkillPower(defenseSide)
        assertEquals(125, result)
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

//    @Test
//    fun 防御側HP依存技の威力のテスト1() {
//        val attackSide = PokemonForBattle.create(0, IndividualPokemon.create(1, kucheat))
//        attackSide.skill.jname = "しおみず"
//        attackSide.skill.power = 65
//        val defenseSide = PokemonForBattle.create(0, IndividualPokemon.create(1, bakuhun))
//        defenseSide.hpRatio = 49
//
//        val result = attackSide.determineSkillPower(defenseSide)
//        assertEquals(130, result)
//    }

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

    @Test
    fun 攻撃技を無効にするかどうかのテスト() {
        val skill = Skill()
        val defenseSide = PokemonForBattle.create(0, IndividualPokemon.create(1, bakuhun))


        skill.type = Type.no(Type.Code.ELECTRIC)
        defenseSide.ability = "ちくでん"
        assertEquals(true, defenseSide.doesntAffect(skill))

        skill.type = Type.no(Type.Code.WATER)
        defenseSide.ability = "ふしぎなまもり"
        assertEquals(true, defenseSide.doesntAffect(skill))

        skill.jname = "タネマシンガン"
        defenseSide.ability = "ぼうだん"
        assertEquals(true, defenseSide.doesntAffect(skill))

        skill.jname = "どくのこな"
        defenseSide.ability = "ぼうじん"
        assertEquals(true, defenseSide.doesntAffect(skill))

        skill.jname = "ハイドロポンプ"
        defenseSide.ability = "ぼうじん"
        assertEquals(false, defenseSide.doesntAffect(skill))
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
        attackSide.field.add(BattleField.Field.Tailwind)

        val actual = attackSide.speedValues(BattleField())
        assertEquals(24, actual[0])
        assertEquals(34, actual[4])
        assertEquals(44, actual[10])
        assertEquals(84, actual[23])
    }

    @Test
    fun 砂嵐時の素早さ計算のテスト() {
        val attackSide = PokemonForBattle.create(0, IndividualPokemon.create(1, kucheat))
        attackSide.ability = "すなかき"

        val field = BattleField()
        field.weather = BattleField.Weather.Sandstorm
        val actual = attackSide.speedValues(field)
        assertEquals(98, actual[0])
        assertEquals(140, actual[4])
        assertEquals(180, actual[10])
        assertEquals(336, actual[23])
    }
}