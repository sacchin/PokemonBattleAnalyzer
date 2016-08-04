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

        fun calcDamage(attackSide: PokemonForBattle, defenseSide: PokemonForBattle, field: BattleField, isCritical: Boolean, first: Boolean, damaged: Boolean): Array<Int> {
            var attackValue = 0.0
            var attackValueCorrection = 0.0
            var defenseValue = 0.0
            var defenseValueCorrection = 0.0
            var attackRankCorrection = 0.0
            var defenseRankCorrection = 0.0
            when (attackSide.skill.category) {
                0 -> {
                    attackValue = attackSide.calcAttackValue()
                    attackValueCorrection = attackSide.calcAttackValueCorrection(defenseSide)
                    attackRankCorrection = attackSide.getAttackRankCorrection(isCritical)
                    defenseValue = defenseSide.calcDefenseValue()
                    defenseValueCorrection = defenseSide.calcDefenseValueCorrection(attackSide)
                    defenseRankCorrection = defenseSide.getDefenseRankCorrection(isCritical)
                }
                1 -> {
                    attackValue = attackSide.calcSpecialAttackValue()
                    attackValueCorrection = attackSide.calcSpecialAttackValueCorrection(defenseSide)
                    attackRankCorrection = attackSide.getSpecialAttackRankCorrection(isCritical)
                    defenseValue = defenseSide.calcSpecialDefenseValue()
                    defenseValueCorrection = defenseSide.calcSpecialDefenseValueCorrection()
                    defenseRankCorrection = defenseSide.getDefenseRankCorrection(isCritical)
                }
            }

            val skillPower = calcSkillPower(attackSide, defenseSide, field, first, damaged)


            attackValue = Math.floor(attackValue.times(attackRankCorrection))
            if (attackSide.skill.category == 0 && attackSide.ability.equals("はりきり")) {
                attackValue = Math.ceil(attackValue.times(1.5).plus(0.5)) //五捨五超入
            }
            attackValue = Math.ceil(attackValue.times(attackValueCorrection).div(4096.0).plus(0.5))
            attackValue = if(attackValue < 1) 1.0 else attackValue

            defenseValue = Math.floor(defenseValue.times(defenseRankCorrection))
            if (attackSide.skill.category == 1 && //ToDo: すなあらし対応
                    (defenseSide.individual.master.type1 == Type.no(Type.Code.ROCK) || defenseSide.individual.master.type2 == Type.no(Type.Code.ROCK))) {
                defenseValue = Math.ceil(defenseValue.times(1.5).plus(0.5)) //五捨五超入
            }
            defenseValue = Math.ceil(defenseValue.times(defenseValueCorrection).div(4096.0).plus(0.5))


            val damage = 8
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
//            for(i in 0..randomDamage.size){
//                randomDamage[i] = calcSecondSection(randomDamage[i], attackSide, defenseSide)
//            }

            return randomDamage
        }

        fun calcSkillPowerCorrection(attackSide: PokemonForBattle, defenseSide: PokemonForBattle, field: BattleField, first: Boolean): Double{
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
            //first と damagedを利用して技の威力を計算するもののみ、attackSide.determineSkillPower(defenseSide)の外側で計算
            var skillPower = attackSide.determineSkillPower(defenseSide)
            if(attackSide.skill.jname.equals("しっぺがえし") && !first){
                skillPower = skillPower.times(2)
            }
            if(attackSide.skill.jname.equals("ゆきなだれ") || attackSide.skill.jname.equals("リベンジ") && damaged){
                skillPower = skillPower.times(2)
            }

            val skillCorrection = calcSkillPowerCorrection(attackSide, defenseSide, field, first)
            //五捨五超入
            return Math.ceil(skillPower.toDouble().times(skillCorrection).div(4096.0).plus(0.5)).toInt()
        }

        fun calcDamageCorrection(attackSide: PokemonForBattle, defenseSide: PokemonForBattle, field: BattleField, isCritical: Boolean): Double{
            var initValue = 4096.0

            if(attackSide.skill.category == 0 && defenseSide.field.contains(BattleField.Field.Reflect)){
                initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
            }
            if(attackSide.skill.category == 1 && defenseSide.field.contains(BattleField.Field.LightScreen)){
                initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
            }

            if(defenseSide.hpRatio == 100 && defenseSide.ability.equals("マルチスケイル")){
                initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
            }

            val batsugun = Type.calculateAffinity(Type.code(attackSide.skill.type), defenseSide.individual.master)
            if(batsugun < 1 && attackSide.ability.equals("いろめがね")) {
                initValue = Math.round(initValue.times(8192.0).div(4096.0)).toDouble()
            }

            if(isCritical && attackSide.ability.equals("スナイパー")) {
                initValue = Math.round(initValue.times(6144.0).div(4096.0)).toDouble()
            }

            //Todo: フレンドガード1 initValue = Math.round(initValue.times(3072.0).div(4096.0)).toDouble()
            //Todo: フレンドガード2 initValue = Math.round(initValue.times(2304.0).div(4096.0)).toDouble()

            if(batsugun < 1 && (defenseSide.ability.equals("ハードロック") || defenseSide.ability.equals("フィルター"))){
                initValue = Math.round(initValue.times(3072.0).div(4096.0)).toDouble()
            }

            //ToDo: メトロノーム initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble() 4096 から 819 ずつ上がる。6回目以降：8196

            if(batsugun < 1 && attackSide.item.equals("たつじんのおび")) {
                initValue = Math.round(initValue.times(4915.0).div(4096.0)).toDouble()
            }

            if(attackSide.item.equals("いのちのたま")) {
                initValue = Math.round(initValue.times(5324.0).div(4096.0)).toDouble()
            }

            if(1 < batsugun){
                when(attackSide.skill.type){
                    Type.no(Type.Code.FIRE) -> if(defenseSide.item.equals("オッカのみ")) initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                    Type.no(Type.Code.WATER) -> if(defenseSide.item.equals("イトケのみ")) initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                    Type.no(Type.Code.ELECTRIC) -> if(defenseSide.item.equals("ソクノのみ")) initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                    Type.no(Type.Code.GRASS) -> if(defenseSide.item.equals("リンドのみ")) initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                    Type.no(Type.Code.ICE) -> if(defenseSide.item.equals("ヤチェのみ")) initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                    Type.no(Type.Code.FIGHTING) -> if(defenseSide.item.equals("ヨプのみ")) initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                    Type.no(Type.Code.POISON) -> if(defenseSide.item.equals("ビアーのみ")) initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                    Type.no(Type.Code.GROUND) -> if(defenseSide.item.equals("シュカのみ")) initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                    Type.no(Type.Code.FLYING) -> if(defenseSide.item.equals("バコウのみ")) initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                    Type.no(Type.Code.PSYCHIC) -> if(defenseSide.item.equals("ウタンのみ")) initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                    Type.no(Type.Code.BUG) -> if(defenseSide.item.equals("タンガのみ")) initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                    Type.no(Type.Code.ROCK) -> if(defenseSide.item.equals("ヨロギのみ")) initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                    Type.no(Type.Code.GHOST) -> if(defenseSide.item.equals("カシブのみ")) initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                    Type.no(Type.Code.DRAGON) -> if(defenseSide.item.equals("ハバンのみ")) initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                    Type.no(Type.Code.DARK) -> if(defenseSide.item.equals("ナモのみ")) initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                    Type.no(Type.Code.STEEL) -> if(defenseSide.item.equals("リリバのみ")) initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                    Type.no(Type.Code.FAIRY) -> if(defenseSide.item.equals("ロゼルのみ")) initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
                }
            }
            if(attackSide.skill.type == Type.no(Type.Code.NORMAL) && defenseSide.item.equals("ホズのみ")) {
                initValue = Math.round(initValue.times(2048.0).div(4096.0)).toDouble()
            }

//            　×　8192（0x2000）　÷　4096（0x1000）　→　四捨五入
//            　　ふみつけ（ちいさくなる）
//            　　じしん（あなをほる）
//            　　なみのり（ダイビング）
//            　　ハードローラー（ちいさくなる）
//            　　ドラゴンダイブ（ちいさくなる）
//            　　ゴーストダイブ（ちいさくなる）
//            　　シャドーダイブ（ちいさくなる）
//            　　フライングプレス（ちいさくなる）
//            　　のしかかり（ちいさくなる）：

            return initValue
        }
    }

}
