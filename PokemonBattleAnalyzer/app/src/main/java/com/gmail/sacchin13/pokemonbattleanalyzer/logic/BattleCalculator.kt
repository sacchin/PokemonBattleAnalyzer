package com.gmail.sacchin13.pokemonbattleanalyzer.logic

import android.util.Log

import com.gmail.sacchin13.pokemonbattleanalyzer.BattleUtil
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.*
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl.RankingPokemonTrend

object BattleCalculator {

    val notCompatibleWords = arrayOf(
            "れんぞくぎり","ころがる","エコーボイス","りんしょう",
            "たつまき","かぜおこし","きりふだ","ちかい系","なげつける",
            "ふくろだだき","ダメおし","しぜんのめぐみ","ウェザーボール",
            "はきだす","おいうち")

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

                            if(count == damages.size){

                            }

                            damages = calcDamage(order[1], order[0], field, false, false, false)
                            count = 0
                            for(d in damages){
                                val remain = order[0].individual.calcHp().times(100).div(order[0].hpRatio) - d
                                if(remain < 1) count++
                            }
                            when(order[1].side){
                                0 -> result.mayOccur[BattleStatus.Code.OWN_HEAD] = result.mayOccur[BattleStatus.Code.OWN_HEAD]!!.plus(rate.times(count).div(damages.size))
                                1 -> result.mayOccur[BattleStatus.Code.REVERSE] = result.mayOccur[BattleStatus.Code.REVERSE]!!.plus(rate.times(count).div(damages.size))
                            }
                        }
                    }
                }
            }

            return result
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

        fun calcSkillPowerCorrection(attackSide: PokemonForBattle, defenseSide: PokemonForBattle, field: BattleField, first: Boolean, damaged: Boolean): Double{
            var initValue = 4096.0


            //TODO とうそうしん弱化,オーラブレイク initValue.times(3042).div(4096).toInt()

            if((attackSide.skill.jname.equals("れんぞくパンチ") || attackSide.skill.jname.equals("メガトンパンチ") || attackSide.skill.jname.equals("ほのおのパンチ") || attackSide.skill.jname.equals("れいとうパンチ") || attackSide.skill.jname.equals("かみなりパンチ") || attackSide.skill.jname.equals("ピヨピヨパンチ") ||
                    attackSide.skill.jname.equals("マッハパンチ") || attackSide.skill.jname.equals("ばくれつパンチ") || attackSide.skill.jname.equals("きあいパンチ") || attackSide.skill.jname.equals("コメットパンチ") || attackSide.skill.jname.equals("シャドーパンチ") || attackSide.skill.jname.equals("スカイアッパー") ||
                    attackSide.skill.jname.equals("アームハンマー") || attackSide.skill.jname.equals("バレットパンチ") || attackSide.skill.jname.equals("ドレインパンチ"))  && attackSide.ability.equals("てつのこぶし")){
                initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
            }
            if((attackSide.skill.jname.equals("すてみタックル") || attackSide.skill.jname.equals("ウッドハンマー") || attackSide.skill.jname.equals("ブレイブバード") || attackSide.skill.jname.equals("とっしん") || attackSide.skill.jname.equals("じごくぐるま") || attackSide.skill.jname.equals("ボルテッカー") ||
                    attackSide.skill.jname.equals("フレアドライブ") || attackSide.skill.jname.equals("もろはのずつき") || attackSide.skill.jname.equals("とびげり") || attackSide.skill.jname.equals("とびひざげり")) && attackSide.ability.equals("すてみ")){
                initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
            }

            //TODO とうそうしん強化 initValue.times(5120).div(4096).toInt()
            //5325 / 4096 四捨五入 ちからずく

            if(attackSide.ability.equals("すなのちから") && field.weather == BattleField.Weather.Sandstorm &&
                    (attackSide.skill.type == Type.no(Type.Code.ROCK) || attackSide.skill.type == Type.no(Type.Code.STEEL) || attackSide.skill.type == Type.no(Type.Code.GROUND))){
                initValue = Math.round(initValue.times(5325.0).div(4096.0)).toDouble()
                attackSide.skill.type = Type.no(Type.Code.FLYING)
            }
            if(attackSide.ability.equals("スカイスキン") && attackSide.skill.type == Type.no(Type.Code.NORMAL)){
                initValue = Math.round(initValue.times(5325.0).div(4096.0)).toDouble()
                attackSide.skill.type = Type.no(Type.Code.FLYING)
            }
            if(attackSide.ability.equals("フェアリースキン") && attackSide.skill.type == Type.no(Type.Code.NORMAL)){
                initValue = Math.round(initValue.times(5325.0).div(4096.0)).toDouble()
                attackSide.skill.type = Type.no(Type.Code.FAIRY)
            }
            if(attackSide.ability.equals("フリーズスキン") && attackSide.skill.type == Type.no(Type.Code.NORMAL)){
                initValue = Math.round(initValue.times(5325.0).div(4096.0)).toDouble()
                attackSide.skill.type = Type.no(Type.Code.ICE)
            }

            if(attackSide.ability.equals("かたいツメ") && attackSide.skill.contact){
                initValue = Math.round(initValue.times(5325.0).div(4096.0)).toDouble()
            }

            if(attackSide.ability.equals("アナライズ") && !first){
                initValue = Math.round(initValue.times(5325.0).div(4096.0)).toDouble()
            }

            //TODO 5448 / 4096 四捨五入(※2体以上いても補正は1回のみ) フェアリーオーラ,ダークオーラ

            if(attackSide.ability.equals("ねつぼうそう") && attackSide.status == StatusAilment.no(StatusAilment.Code.BURN)){
                initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
            }
            if(attackSide.ability.equals("どくぼうそう") && (attackSide.status == StatusAilment.no(StatusAilment.Code.POISON) || attackSide.status == StatusAilment.no(StatusAilment.Code.BADPOISON))){
                initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
            }
            if((attackSide.skill.jname.equals("かみつく") || attackSide.skill.jname.equals("かみくだく") || attackSide.skill.jname.equals("かみなりのキバ") || attackSide.skill.jname.equals("ほのおのキバ")||
                    attackSide.skill.jname.equals("こおりのキバ") || attackSide.skill.jname.equals("どくどくのキバ") || attackSide.skill.jname.equals("ひっさつまえば")) && attackSide.ability.equals("がんじょうあご")){
                initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
            }

            if((attackSide.skill.jname.equals("はどうだん") || attackSide.skill.jname.equals("みずのはどう") || attackSide.skill.jname.equals("りゅうのはどう") ||
                    attackSide.skill.jname.equals("あくのはどう") || attackSide.skill.jname.equals("こんげんのはどう")) && attackSide.ability.equals("メガランチャー")){
                initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
            }


            if((attackSide.skill.power <= 60) && attackSide.ability.equals("テクニシャン")){
                initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
            }

            if(Type.code(attackSide.skill.type) == Type.Code.FIRE && defenseSide.ability.equals("たいねつ")){
                initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
            }

            if(Type.code(attackSide.skill.type) == Type.Code.FIRE && defenseSide.ability.equals("かんそうはだ")){
                initValue = Math.round(initValue.times(5120.0).div(4096.0)).toDouble()
            }


            if(attackSide.skill.category == 0 && attackSide.item.equals("ちからのハチマキ")){
                initValue = Math.round(initValue.times(4505.0).div(4096.0)).toDouble()
            }
            if(attackSide.skill.category == 1 && attackSide.item.equals("ものしりメガネ")){
                initValue = Math.round(initValue.times(4505.0).div(4096.0)).toDouble()
            }

            //ToDo: こうんごうだま、しらたま、はっきんだま対応
            if(Type.code(attackSide.skill.type) == Type.Code.NORMAL && attackSide.item.equals("シルクのスカーフ")){
                initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
            }
            if(Type.code(attackSide.skill.type) == Type.Code.FLYING && (attackSide.item.equals("あおぞらプレート") || attackSide.item.equals("するどいくちばし"))){
                initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
            }
            if(Type.code(attackSide.skill.type) == Type.Code.ELECTRIC && (attackSide.item.equals("いかずちプレート") || attackSide.item.equals("じしゃく"))){
                initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
            }
            if(Type.code(attackSide.skill.type) == Type.Code.ROCK && (attackSide.item.equals("がんせきプレート") || attackSide.item.equals("かたいいし") || attackSide.item.equals("がんせきおこう"))){
                initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
            }
            if(Type.code(attackSide.skill.type) == Type.Code.STEEL && (attackSide.item.equals("こうてつプレート") || attackSide.item.equals("メタルコート"))){
                initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
            }
            if(Type.code(attackSide.skill.type) == Type.Code.FIGHTING && (attackSide.item.equals("こぶしのプレート") || attackSide.item.equals("くろおび"))){
                initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
            }
            if(Type.code(attackSide.skill.type) == Type.Code.DARK && (attackSide.item.equals("こわもてプレート") || attackSide.item.equals("くろいメガネ"))){
                initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
            }
            if(Type.code(attackSide.skill.type) == Type.Code.WATER && (attackSide.item.equals("しずくプレート") || attackSide.item.equals("しんぴのしずく") || attackSide.item.equals("うしおのおこう") || attackSide.item.equals("さざなみのおこう"))){
                initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
            }
            if(Type.code(attackSide.skill.type) == Type.Code.GROUND && (attackSide.item.equals("だいちのプレート") || attackSide.item.equals("やわらかいすな"))){
                initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
            }
            if(Type.code(attackSide.skill.type) == Type.Code.BUG && (attackSide.item.equals("たまむしプレート") || attackSide.item.equals("ぎんのこな"))){
                initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
            }
            if(Type.code(attackSide.skill.type) == Type.Code.ICE && (attackSide.item.equals("つららのプレート") || attackSide.item.equals("とけないこおり"))){
                initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
            }
            if(Type.code(attackSide.skill.type) == Type.Code.FIRE && (attackSide.item.equals("ひのたまプレート") || attackSide.item.equals("もくたん"))){
                initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
            }
            if(Type.code(attackSide.skill.type) == Type.Code.PSYCHIC && (attackSide.item.equals("ふしぎのプレート") || attackSide.item.equals("まがったスプーン") || attackSide.item.equals("あやしいおこう"))){
                initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
            }
            if(Type.code(attackSide.skill.type) == Type.Code.GRASS && (attackSide.item.equals("みどりのプレート") || attackSide.item.equals("きせきのタネ") || attackSide.item.equals("おはなのおこう"))){
                initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
            }
            if(Type.code(attackSide.skill.type) == Type.Code.POISON && (attackSide.item.equals("もうどくプレート") || attackSide.item.equals("どくバリ"))){
                initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
            }
            if(Type.code(attackSide.skill.type) == Type.Code.GHOST && (attackSide.item.equals("もののけプレート") || attackSide.item.equals("のろいのおふだ"))){
                initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
            }
            if(Type.code(attackSide.skill.type) == Type.Code.DRAGON && (attackSide.item.equals("りゅうのプレート") || attackSide.item.equals("りゅうのキバ"))) {
                initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
            }


            //5325 / 4096 四捨五入 ジュエル

            if(attackSide.skill.jname.equals("ソーラービーム") && field.weather == BattleField.Weather.Rainy){
                initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
            }

            //6144 / 4096 四捨五入 さきどり はたきおとす

            //8192 / 4096 四捨五入クロスサンダー,クロスフレイム,じゅうでん,かたきうち

            if (attackSide.skill.jname.equals("からげんき") &&  attackSide.status != StatusAilment.no(StatusAilment.Code.UNKNOWN)) {
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

            if(attackSide.skill.type == Type.no(Type.Code.ELECTRIC) && field.field.contains(BattleField.Field.MudSport)){
                initValue = Math.round(initValue.times(1352.0).div(4096.0)).toDouble()
            }
            if(attackSide.skill.type == Type.no(Type.Code.FIRE) && field.field.contains(BattleField.Field.WaterSport)){
                initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
            }

            //2048 / 4096 四捨五入 グラスフィールド弱化,ミストフィールド
            //6144 / 4096 四捨五入 グラスフィールド強化,エレキフィールド

            return initValue
        }



        fun calcSkillPower(attackSide: PokemonForBattle, defenseSide: PokemonForBattle, field: BattleField, first: Boolean, damaged: Boolean): Int{
            var result = attackSide.determineSkillPower(defenseSide)

            if(attackSide.skill.jname.equals("しっぺがえし") && !first){
                result = result.times(2.0f).toInt()
            }

            if(attackSide.skill.jname.equals("ゆきなだれ") || attackSide.skill.jname.equals("リベンジ") && damaged){
                result = result.times(2.0f).toInt()
            }

            return result
        }
    }



}
