package com.gmail.sacchin13.pokemonbattleanalyzer.entity

import io.realm.RealmObject
import io.realm.annotations.RealmClass
import java.util.*

@RealmClass
open class Skill(
        open var no: Int = -1,
        open var jname: String = "unknown",
        open var ename: String = "unknown",
        open var type: Int = -1,
        open var power: Int = -1,
        open var accuracy: Double = -1.0,
        open var category: Int = -1,
        open var pp: Int = -1,
        open var priority: Int = 0,
        open var contact: Boolean = false,
        open var protectable: Boolean = false,
        open var aliment: Int = -1,
        open var alimentRate: Double = 0.0,
        open var myRankUp: Int = -1,
        open var myRankUpRate: Double = 0.0,
        open var oppoRankUp: Int = -1,
        open var oppoRankUpRate: Double = 0.0

) : RealmObject() {

    companion object {
        fun create(no: Int, jname: String, ename: String, type: Int, power: Int, accuracy: Double,
                   category: Int, pp: Int, priority: Int, contact: Boolean, protectable: Boolean,
                   aliment: Int, alimentRate: Double, myRankUp: Int, myRankUpRate: Double,
                   oppoRankUp: Int, oppoRankUpRate: Double): Skill {
            return Skill(no, jname, ename, type, power, accuracy, category, pp, priority, contact,
                    protectable, aliment, alimentRate, myRankUp, myRankUpRate, oppoRankUp, oppoRankUpRate)
        }

        fun skillNameList(list: MutableList<Skill>): MutableList<String> {
            val result = ArrayList<String>()
            for (temp in list) {
                result.add(temp.jname)
            }
            return result
        }

        fun voiceSkill(name: String): Boolean {
            return if (name == "いにしえのうた" || name == "いびき" || name == "いやなおと" || name == "うたう" ||
                    name == "エコーボイス" || name == "おしゃべり" || name == "おたけび" || name == "きんぞくおん" ||
                    name == "くさぶえ" || name == "さわぐ" || name == "すてゼリフ" || name == "ダークパニック" ||
                    name == "チャームボイス" || name == "ちょうおんぱ" || name == "ないしょばなし" || name == "なきごえ" ||
                    name == "ハイパーボイス" || name == "バークアウト" || name == "ばくおんぱ" || name == "ほえる" ||
                    name == "ほろびのうた" || name == "むしのさざめき" || name == "りんしょう") true else false
        }

        fun powderSkill(name: String): Boolean {
            return if (name == "ねむりごな" || name == "しびれごな" || name == "どくのこな" ||
                    name == "キノコのほうし" || name == "わたほうし" || name == "いかりのこな" ||
                    name == "ふんじん") true else false
        }

        fun bomSkill(name: String): Boolean {
            return if (name == "アイスボール" || name == "アシッドボム" || name == "ウェザーボール" || name == "エナジーボール" ||
                    name == "エレキボール" || name == "オクタンほう" || name == "かえんだん" || name == "がんせきほう" ||
                    name == "きあいだま" || name == "ジャイロボール" || name == "シャドーボール" || name == "タネマシンガン" ||
                    name == "タネばくだん" || name == "タマゴばくだん" || name == "たまなげ" || name == "でんじほう" ||
                    name == "どろばくだん" || name == "はどうだん" || name == "ヘドロばくだん" || name == "マグネットボム" ||
                    name == "ミストボール") true else false
        }

        fun migawariSkill(name: String): Boolean {
            if (voiceSkill(name)) return true
            return if (name == "メロメロ" || name == "ちょうはつ" || name == "いちゃもん" || name == "アンコール" ||
                    name == "かなしばり" || name == "みやぶる" || name == "ミラクルアイ" || name == "のろい" ||
                    name == "ふきとばし" || name == "スキルスワップ" || name == "じこあんじ" || name == "なりきり" ||
                    name == "テクスチャー2" || name == "ミラータイプ" || name == "パワーズワップ" || name == "ガードスワップ" ||
                    name == "ハートスワップ" || name == "くろいきり" || name == "うらみ" || name == "おんねん" ||
                    name == "さきどり" || name == "よこどり" || name == "おさきにどうぞ" || name == "てだすけ") true else false
        }
    }

    override fun toString(): String {
        return "${no},${jname},${ename},${type},${power},${accuracy},${category},${pp},${priority},${contact},${protectable}"
    }
}

