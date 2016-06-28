package com.gmail.sacchin13.pokemonbattleanalyzer.entity

import java.util.Collections
import java.util.Comparator

/** point算出アルゴリズム
 * 1. 補完得点を計算する。補完得点は、技を受けた時の倍率が低い方を採用する
 * 例1：ほのお技に対して、ポケモンAが4倍弱点、ポケモンBが等倍の場合
 * → 4 > 1 = 1
 * 例2：ほのお技に対して、ポケモンAが2倍弱点、ポケモンBが1/4倍の場合
 * → 2 > 0.25 = 0.25
 * 例3：みず技に対して、ポケモンAが「よびみず」の場合0倍、その他の特性の場合1倍、ポケモンBが1/4倍の場合
 * → 0 or 1 < 0.25 = 0
 * 2. すべてのタイプに対して、補完得点を計算し、それを合計する。
 * 3. 合計が低いほど補完として優秀であり、高いほど補完として優秀でない。
 */
class AffinityRank(point: Int, val pokemon: IndividualPBAPokemon) {
    var point = 0
    var deviation = 0

    init {
        this.point = point
    }

    private class AffinityRankComparator : Comparator<AffinityRank> {
        override fun compare(lhs: AffinityRank, rhs: AffinityRank): Int {
            return lhs.point - rhs.point
        }
    }

    val type1Name: String
        get() = Type.name(Type.code(pokemon.master.type1))

    val type2Name: String
        get() = Type.name(Type.code(pokemon.master.type2))

    override fun toString(): String {
        return point.toString() + " - " + type1Name + "," + type2Name
    }

    companion object {

        fun sort(list: List<AffinityRank>) {
            Collections.sort(list, AffinityRankComparator())
        }

        fun calcDeviation(list: List<AffinityRank>?) {
            if (list == null) {
                return
            }

            var average = 0
            for (temp in list) {
                average += temp.point
            }
            average /= list.size

            var standardDeviation = 0
            for (temp in list) {
                standardDeviation += (temp.point - average) * (temp.point - average)
            }
            standardDeviation /= list.size
            standardDeviation = Math.sqrt(standardDeviation.toDouble()).toInt()

            for (temp in list) {
                //pointが低いほど優秀なため、下記計算式を変えています。
                val value = (average - temp.point) / standardDeviation.toDouble()
                temp.deviation = (value * 10.0 + 50.0).toInt()
            }
        }
    }
}
