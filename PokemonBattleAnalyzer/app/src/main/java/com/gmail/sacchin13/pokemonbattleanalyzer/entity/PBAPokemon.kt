package com.gmail.sacchin13.pokemonbattleanalyzer.entity

import java.util.*

open class PBAPokemon(
        var resourceId:Int = 0,
        var rowId:Int = 0,
        val masterRecord :Pokemon,
        val ranking: Int) {

    val abilities: List<String>
        get() {
            val temp = ArrayList<String>()
            temp.add(masterRecord.ability1)
            if (masterRecord.ability2 != "-") {
                temp.add(masterRecord.ability2)
            }
            if (masterRecord.abilityd != "-") {
                temp.add(masterRecord.abilityd)
            }
            return temp
        }

    fun calcATypeScale(type: Type.TypeCode): Map<String, Int> {
        val scaleMap = HashMap<String, Int>()
        var result: Int

        //タイプ相性に関係する特性がある場合、その値を格納する
        //ふしぎなまもりは特別
        for (ability in abilities) {
            if ("ふしぎなまもり".equals(ability)) {
                if (type === Type.TypeCode.FIRE || type === Type.TypeCode.GHOST || type === Type.TypeCode.FLYING ||
                        type === Type.TypeCode.ROCK || type === Type.TypeCode.DARK) {
                    scaleMap.put(ability, 200)
                } else {
                    scaleMap.put(ability, 0)
                }
            } else {
                val scaleByAbility = Ability.calcTypeScale(ability, type)
                result = (scaleByAbility * Type.calcurateAffinity(type, masterRecord) * 100f).toInt()
                scaleMap.put(ability, result)
            }
        }

        //すべての特性で倍率が同じ場合、１つにまとめる
//        val judgeSameScale = scaleMap.entries.iterator().next() as Int
//        var isSame = true
//        for (scale in scaleMap.entries) {
//            if (judgeSameScale != scale) {
//                isSame = false
//            }
//        }
//        if (isSame) {
//            scaleMap.clear()
//            scaleMap.put("both", judgeSameScale)
//        }
        return scaleMap
    }

    fun calcAllTypeScale(): Map<Type.TypeCode, Map<String, Int>> {
        val scaleMap = HashMap<Type.TypeCode, Map<String, Int>>()
        for (type in Type.TypeCode.values()) {
            val temp = calcATypeScale(type)
            scaleMap.put(type, temp)
        }
        return scaleMap
    }
}
