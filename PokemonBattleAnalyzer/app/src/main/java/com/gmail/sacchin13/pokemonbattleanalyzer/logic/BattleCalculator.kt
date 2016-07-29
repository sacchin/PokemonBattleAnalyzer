package com.gmail.sacchin13.pokemonbattleanalyzer.logic

import android.util.Log

import com.gmail.sacchin13.pokemonbattleanalyzer.BattleUtil
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.*
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl.RankingPokemonTrend

object BattleCalculator {
    enum class RiskDegree {
        SAFE,
        LGTM,
        NORMAL,
        WARN,
        FATAL
    }

    fun calcDamageRate(attackSide: IndividualPBAPokemon, skill: Skill, defenseSide: IndividualPBAPokemon) {
//        val maxDamage = defenseSide.calcDamage(attackSide, skill)
//        var kakuitiCount = 0
//        for (revision in damageRevesion) {
//            val remain = defenseSide.getHPValue() - (maxDamage * revision).toInt()
//            if (remain <= 0) {
//                kakuitiCount++
//            }
//        }
//        (attackLevel * 2 / 5 + 2) * skill.power * attackValue / (deffenceValue / 50 + 2)

    }

    fun getRiskDegree(rate: Float): RiskDegree {
        if (rate <= 0) {
            return RiskDegree.SAFE
        } else if (0 < rate && rate <= 30) {
            return RiskDegree.LGTM
        } else if (30 < rate && rate <= 60) {
            return RiskDegree.NORMAL
        } else if (60 < rate && rate <= 99) {
            return RiskDegree.WARN
        } else {
            return RiskDegree.FATAL
        }
    }

    object companion{
        fun getResult(skill: Skill, mine: PokemonForBattle, opponent: PokemonForBattle, field: BattleField): BattleResult{
            val result = BattleResult()
            for(item in opponent.trend.rankingPokemonTrend.itemInfo){
                for(tokusei in opponent.trend.rankingPokemonTrend.tokuseiInfo){
                    for(seikaku in opponent.trend.rankingPokemonTrend.seikakuInfo){
                        for(waza in opponent.trend.rankingPokemonTrend.wazaInfo){
                            val rate = item.usageRate.times(tokusei.usageRate.times(seikaku.usageRate.times(waza.usageRate)))
                            opponent.item = item.name
                            opponent.ability = tokusei.name
                            opponent.characteristic = seikaku.name

                            val order = getAttackOrder(mine, opponent)
                            var damages = calcDamage(order[0], order[1], field, false, false, false)
                            var count = 0
                            for(d in damages){
                                val remain = order[1].individual.calcHp().times(100).div(order[1].hpRatio) - d
                                if(remain < 1) count++
                            }
                            when(order[1].side){
                                0 -> result.mayOccur[BattleStatus.Code.DEFEAT] = result.mayOccur[BattleStatus.Code.DEFEAT]!!.plus(rate.times(count).div(damages.size))
                                1 -> result.mayOccur[BattleStatus.Code.WIN] = result.mayOccur[BattleStatus.Code.WIN]!!.plus(rate.times(count).div(damages.size))
                            }

                            var damages = calcDamage(order[0], order[1], field, false, false, false)
                        }
                    }
                }
            }








            return BattleResult()
        }

        fun getAttackOrder(mine: PokemonForBattle, opponent: PokemonForBattle): Array<PokemonForBattle> {
            when {
                mine.skill.priority < opponent.skill.priority -> return arrayOf(opponent, mine)
                mine.skill.priority > opponent.skill.priority -> return arrayOf(mine, opponent)
                else -> if (opponent.calcSpeedValue() < mine.calcSpeedValue()) return arrayOf(mine, opponent) else return arrayOf(opponent, mine)
            }
        }

        fun calcFirstSection(attackSide: PokemonForBattle, defenseSide: PokemonForBattle, field: BattleField, isCritical: Boolean, first: Boolean,  damaged: Boolean): Int {
            var damage = 0
            val skillPower = calcSkillPower(attackSide, defenseSide, field, first, damaged)
            var attackValue = 0
            var defenseValue = 0
            when (attackSide.skill.category) {
                0 -> {
                    attackValue = attackSide.calcAttackValue(isCritical)
                    defenseValue = defenseSide.calcDefenseValue(isCritical)
                }
                1 -> {
                    attackValue = attackSide.calcSpecialAttackValue(isCritical)
                    defenseValue = defenseSide.calcSpecialDefenseValue(isCritical)
                }
            }

            damage = (22f * skillPower * attackValue.toFloat() / defenseValue / 50).toInt()
            //Todo: if(isCritical) リフレクター
            //ToDo: 天候補正
            //ToDo: もらいび

            damage += 2

            if(isCritical){
                if(attackSide.ability.equals("スナイパー")) damage *= 3 else damage *= 2
            }

            //ToDo: さきどり
            //ToDo: メトロノーム
            if(attackSide.item.equals("いのちのたま")) damage = damage.times(1.3).toInt()

            return damage
        }

        fun calcDamage(attackSide: PokemonForBattle, defenseSide: PokemonForBattle, field: BattleField, isCritical: Boolean, first: Boolean, damaged: Boolean): Array<Int> {
            val damage = calcFirstSection(attackSide, defenseSide, field, isCritical, first, damaged)
            val randomDamage = arrayOf(
                    damage.times(0.85).toInt(),
                    damage.times(0.86).toInt(),
                    damage.times(0.87).toInt(),
                    damage.times(0.88).toInt(),
                    damage.times(0.89).toInt(),
                    damage.times(0.90).toInt(),
                    damage.times(0.91).toInt(),
                    damage.times(0.92).toInt(),
                    damage.times(0.93).toInt(),
                    damage.times(0.94).toInt(),
                    damage.times(0.95).toInt(),
                    damage.times(0.96).toInt(),
                    damage.times(0.97).toInt(),
                    damage.times(0.98).toInt(),
                    damage.times(0.99).toInt(),
                    damage)

            for(i in 0..randomDamage.size){
                randomDamage[i] = calcSecondSection(randomDamage[i], attackSide, defenseSide)
            }

            return randomDamage
        }

//            resultMap.put(rate, damage)
//            damage = (22f * skill.power.toFloat() * attackSide.specialAttackValue.toFloat() * aRevision[2] / specialDeffenceValue * dRevision[3] / 50 + 2).toInt()
//            resultMap.put(rate, damage)

        fun calcSecondSection(firstSectionDamage: Int, attackSide: PokemonForBattle, defenseSide: PokemonForBattle): Int{
            var damage = firstSectionDamage.times(attackSide.calcTypeBonus()).toInt()

            val batsugun = Type.calculateAffinity(Type.code(attackSide.skill.type), defenseSide.individual.master)
            damage = damage.times(batsugun).toInt()
            if(1 < batsugun){
                when(attackSide.skill.type){
                    Type.no(Type.Code.FIRE) -> if(defenseSide.item.equals("オッカのみ")) damage = damage.times(0.5).toInt()
                    Type.no(Type.Code.WATER) -> if(defenseSide.item.equals("イトケのみ")) damage = damage.times(0.5).toInt()
                    Type.no(Type.Code.ELECTRIC) -> if(defenseSide.item.equals("ソクノのみ")) damage = damage.times(0.5).toInt()
                    Type.no(Type.Code.GRASS) -> if(defenseSide.item.equals("リンドのみ")) damage = damage.times(0.5).toInt()
                    Type.no(Type.Code.ICE) -> if(defenseSide.item.equals("ヤチェのみ")) damage = damage.times(0.5).toInt()
                    Type.no(Type.Code.FIGHTING) -> if(defenseSide.item.equals("ヨプのみ")) damage = damage.times(0.5).toInt()
                    Type.no(Type.Code.POISON) -> if(defenseSide.item.equals("ビアーのみ")) damage = damage.times(0.5).toInt()
                    Type.no(Type.Code.GROUND) -> if(defenseSide.item.equals("シュカのみ")) damage = damage.times(0.5).toInt()
                    Type.no(Type.Code.FLYING) -> if(defenseSide.item.equals("バコウのみ")) damage = damage.times(0.5).toInt()
                    Type.no(Type.Code.PSYCHIC) -> if(defenseSide.item.equals("ウタンのみ")) damage = damage.times(0.5).toInt()
                    Type.no(Type.Code.BUG) -> if(defenseSide.item.equals("タンガのみ")) damage = damage.times(0.5).toInt()
                    Type.no(Type.Code.ROCK) -> if(defenseSide.item.equals("ヨロギのみ")) damage = damage.times(0.5).toInt()
                    Type.no(Type.Code.GHOST) -> if(defenseSide.item.equals("カシブのみ")) damage = damage.times(0.5).toInt()
                    Type.no(Type.Code.DRAGON) -> if(defenseSide.item.equals("ハバンのみ")) damage = damage.times(0.5).toInt()
                    Type.no(Type.Code.DARK) -> if(defenseSide.item.equals("ナモのみ")) damage = damage.times(0.5).toInt()
                    Type.no(Type.Code.STEEL) -> if(defenseSide.item.equals("リリバのみ")) damage = damage.times(0.5).toInt()
                    Type.no(Type.Code.FAIRY) -> if(defenseSide.item.equals("ロゼルのみ")) damage = damage.times(0.5).toInt()
                }
                if(attackSide.item.equals("たつじんのおび")) damage = damage.times(1.2).toInt()
                if(defenseSide.ability.equals("ハードロック")) damage = damage.times(0.75).toInt()
            }
            if(batsugun < 1 && attackSide.ability.equals("いろめがね")) damage = damage.times(2).toInt()
            if(attackSide.skill.type == Type.no(Type.Code.NORMAL) && defenseSide.item.equals("ホズのみ")) damage = damage.times(0.5).toInt()

            return damage
        }

        fun calcSkillPower(attackSide: PokemonForBattle, defenseSide: PokemonForBattle, field: BattleField, first: Boolean, damaged: Boolean): Int{
            var result = attackSide.determineSkillPower(defenseSide)
            var cause = ""
            var apply = true

            if(attackSide.skill.category == 0 && attackSide.item.equals("ちからのハチマキ")){
                result = result.times(1.1f).toInt()
            }
            if(attackSide.skill.category == 1 && attackSide.item.equals("ものしりメガネ")){
                result = result.times(1.1f).toInt()
            }

            if(Type.code(attackSide.skill.type) == Type.Code.NORMAL && attackSide.item.equals("シルクのスカーフ")){
                result = result.times(1.2f).toInt()
            }
            if(Type.code(attackSide.skill.type) == Type.Code.FLYING && (attackSide.item.equals("あおぞらプレート") || attackSide.item.equals("するどいくちばし"))){
                result = result.times(1.2f).toInt()
            }
            if(Type.code(attackSide.skill.type) == Type.Code.ELECTRIC && (attackSide.item.equals("いかずちプレート") || attackSide.item.equals("じしゃく"))){
                result = result.times(1.2f).toInt()
            }
            if(Type.code(attackSide.skill.type) == Type.Code.ROCK && (attackSide.item.equals("がんせきプレート") || attackSide.item.equals("かたいいし") || attackSide.item.equals("がんせきおこう"))){
                result = result.times(1.2f).toInt()
            }
            if(Type.code(attackSide.skill.type) == Type.Code.STEEL && (attackSide.item.equals("こうてつプレート") || attackSide.item.equals("メタルコート"))){
                result = result.times(1.2f).toInt()
            }
            if(Type.code(attackSide.skill.type) == Type.Code.FIGHTING && (attackSide.item.equals("こぶしのプレート") || attackSide.item.equals("くろおび"))){
                result = result.times(1.2f).toInt()
            }
            if(Type.code(attackSide.skill.type) == Type.Code.DARK && (attackSide.item.equals("こわもてプレート") || attackSide.item.equals("くろいメガネ"))){
                result = result.times(1.2f).toInt()
            }
            if(Type.code(attackSide.skill.type) == Type.Code.WATER && (attackSide.item.equals("しずくプレート") || attackSide.item.equals("しんぴのしずく") || attackSide.item.equals("うしおのおこう") || attackSide.item.equals("さざなみのおこう"))){
                result = result.times(1.2f).toInt()
            }
            if(Type.code(attackSide.skill.type) == Type.Code.GROUND && (attackSide.item.equals("だいちのプレート") || attackSide.item.equals("やわらかいすな"))){
                result = result.times(1.2f).toInt()
            }
            if(Type.code(attackSide.skill.type) == Type.Code.BUG && (attackSide.item.equals("たまむしプレート") || attackSide.item.equals("ぎんのこな"))){
                result = result.times(1.2f).toInt()
            }
            if(Type.code(attackSide.skill.type) == Type.Code.ICE && (attackSide.item.equals("つららのプレート") || attackSide.item.equals("とけないこおり"))){
                result = result.times(1.2f).toInt()
            }
            if(Type.code(attackSide.skill.type) == Type.Code.FIRE && (attackSide.item.equals("ひのたまプレート") || attackSide.item.equals("もくたん"))){
                result = result.times(1.2f).toInt()
            }
            if(Type.code(attackSide.skill.type) == Type.Code.PSYCHIC && (attackSide.item.equals("ふしぎのプレート") || attackSide.item.equals("まがったスプーン") || attackSide.item.equals("あやしいおこう"))){
                result = result.times(1.2f).toInt()
            }
            if(Type.code(attackSide.skill.type) == Type.Code.GRASS && (attackSide.item.equals("みどりのプレート") || attackSide.item.equals("きせきのタネ") || attackSide.item.equals("おはなのおこう"))){
                result = result.times(1.2f).toInt()
            }
            if(Type.code(attackSide.skill.type) == Type.Code.POISON && (attackSide.item.equals("もうどくプレート") || attackSide.item.equals("どくバリ"))){
                result = result.times(1.2f).toInt()
            }
            if(Type.code(attackSide.skill.type) == Type.Code.GHOST && (attackSide.item.equals("もののけプレート") || attackSide.item.equals("のろいのおふだ"))){
                result = result.times(1.2f).toInt()
            }
            if(Type.code(attackSide.skill.type) == Type.Code.DRAGON && (attackSide.item.equals("りゅうのプレート") || attackSide.item.equals("りゅうのキバ"))) {
                result = result.times(1.2f).toInt()
            }
            //ToDo: こうんごうだま、しらたま対応
            if(attackSide.item.equals("でんきだま")) {
                result = result.times(2.0f).toInt()
            }
            if(Type.code(attackSide.skill.type) == Type.Code.FIRE && defenseSide.ability.equals("かんそうはだ")){
                result = result.times(1.25f).toInt()
            }
            if(Type.code(attackSide.skill.type) == Type.Code.FIRE && defenseSide.ability.equals("たいねつ")){
                result = result.times(0.5f).toInt()
            }
            if((Type.code(attackSide.skill.type) == Type.Code.FIRE || Type.code(attackSide.skill.type) == Type.Code.ICE) && defenseSide.ability.equals("あついしぼう")){
                result = result.times(0.5f).toInt()
            }
            if((attackSide.skill.jname.equals("すてみタックル") || attackSide.skill.jname.equals("ウッドハンマー") || attackSide.skill.jname.equals("ブレイブバード") || attackSide.skill.jname.equals("とっしん") || attackSide.skill.jname.equals("じごくぐるま") || attackSide.skill.jname.equals("ボルテッカー") ||
                    attackSide.skill.jname.equals("フレアドライブ") || attackSide.skill.jname.equals("もろはのずつき") || attackSide.skill.jname.equals("とびげり") || attackSide.skill.jname.equals("とびひざげり")) && attackSide.ability.equals("すてみ")){
                result = result.times(1.2f).toInt()
            }
            if((attackSide.skill.jname.equals("れんぞくパンチ") || attackSide.skill.jname.equals("メガトンパンチ") || attackSide.skill.jname.equals("ほのおのパンチ") || attackSide.skill.jname.equals("れいとうパンチ") || attackSide.skill.jname.equals("かみなりパンチ") || attackSide.skill.jname.equals("ピヨピヨパンチ") ||
                    attackSide.skill.jname.equals("マッハパンチ") || attackSide.skill.jname.equals("ばくれつパンチ") || attackSide.skill.jname.equals("きあいパンチ") || attackSide.skill.jname.equals("コメットパンチ") || attackSide.skill.jname.equals("シャドーパンチ") || attackSide.skill.jname.equals("スカイアッパー") ||
                    attackSide.skill.jname.equals("アームハンマー") || attackSide.skill.jname.equals("バレットパンチ") || attackSide.skill.jname.equals("ドレインパンチ"))  && attackSide.ability.equals("てつのこぶし")){
                result = result.times(1.2f).toInt()
            }

            if(attackSide.skill.jname.equals("しっぺがえし") && !first){
                result = result.times(2.0f).toInt()
                apply = false
            }

            if(attackSide.skill.jname.equals("ゆきなだれ") || attackSide.skill.jname.equals("リベンジ") && damaged){
                result = result.times(2.0f).toInt()
                apply = false
            }

            if((attackSide.skill.power <= 60) && attackSide.ability.equals("テクニシャン")){
                result = result.times(1.5f).toInt()
            }

            //ToDo: とうそうしん対応
            if(Type.code(attackSide.skill.type) == Type.Code.FIRE && attackSide.ability.equals("もうか") && (attackSide.hpRatio < 30)){
                result = result.times(1.5f).toInt()
            }
            if(Type.code(attackSide.skill.type) == Type.Code.WATER && attackSide.ability.equals("げきりゅう") && (attackSide.hpRatio < 30)){
                result = result.times(1.5f).toInt()
            }
            if(Type.code(attackSide.skill.type) == Type.Code.GRASS && attackSide.ability.equals("しんりょく") && (attackSide.hpRatio < 30)){
                result = result.times(1.5f).toInt()
            }
            if(Type.code(attackSide.skill.type) == Type.Code.BUG && attackSide.ability.equals("むしのしらせ") && (attackSide.hpRatio < 30)){
                result = result.times(1.5f).toInt()
            }
            //ToDo: たつまき・かぜおこし対応
            //ToDo: みずあそび・どろあそび対応
            if(attackSide.skill.jname.equals("ふんか") && attackSide.skill.jname.equals("しおふき") && (attackSide.hpRatio < 30)){
                val r = attackSide.hpRatio.toFloat().div(100f).times(150).toInt()
                if(r < 1) result = 1
                else result = r
            }
            //ToDo: じゅうでん対応

            return result
        }
    }



}
