package com.gmail.sacchin13.pokemonbattleanalyzer.entity.realm

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Type
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.ui.SkillForUI
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class ZSkill(
        open var no: Int = -1,
        open var skillNumber: Int = -1,
        open var jname: String = "unknown",
        open var type: Int = -1,
        open var power: Int = -1,
        open var category: Int = -1,
        open var rank: Int = -1
) : RealmObject() {

    companion object {
        fun zCrystal(name: String): Boolean {
            return name.contains("ノーマル") || name.contains("ホノオ") ||
                    name.contains("デンキ") || name.contains("ミズ") ||
                    name.contains("クサ") || name.contains("コオリ") ||
                    name.contains("ジメン") || name.contains("ドク") ||
                    name.contains("ゴースト") || name.contains("エスパー") ||
                    name.contains("アク") || name.contains("ハガネ") ||
                    name.contains("イワ") || name.contains("フェアリー") ||
                    name.contains("ドラゴン") || name.contains("ヒコウ") ||
                    name.contains("ムシ") || name.contains("ガオガエン") ||
                    name.contains("ジュナイパー") || name.contains("アシレーヌ") ||
                    name.contains("ミュウ") || name.contains("カビゴン") ||
                    name.contains("イーブイ") || name.contains("アロライ") ||
                    name.contains("サトシピカチュウ") || name.contains("ピカチュウ") ||
                    name.contains("マーシャドー")
        }

        fun satisfyZSkill(item: String, skill: SkillForUI): Boolean {
            val hasZ = zCrystal(item)
            val zType = Type.no(Type.zNo(item))

            if (hasZ.not()) return false

            //own z skill
            if (hasZ && zType == -1) {
                if (item.contains("アロライ") && skill.jname.contains("まんボルト")) return true
                if (item.contains("ガオガエン") && skill.jname.contains("ラリアット")) return true
                if (item.contains("アシレーヌ") && skill.jname.contains("うたかた")) return true
                if (item.contains("ジュナイパー") && skill.jname.contains("かげぬい")) return true
                if (item.contains("ミュウ") && skill.jname.contains("サイコキネシス")) return true
                if (item.contains("ピカチュウ") && skill.jname.contains("ボルテッカー")) return true
                if (item.contains("カプ") && skill.jname.contains("しぜんのいかり")) return true
                if (item.contains("カビゴン") && skill.jname.contains("ギガインパクト")) return true
            }

            if (hasZ && skill.type == zType) return true

            return false
        }
    }


    fun convert(): Skill {
        val skillName = when (category) {
            2 -> "Z".plus(jname)
            else -> jname + "($power)"
        }

        return Skill(-1, skillName, skillName, type, power, 100.0, category, 1, 0, false, false, -1, 0.0, -1, 0.0, -1, 0.0)
    }
}