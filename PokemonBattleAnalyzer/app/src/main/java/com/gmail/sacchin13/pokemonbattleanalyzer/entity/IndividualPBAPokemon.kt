package com.gmail.sacchin13.pokemonbattleanalyzer.entity

import com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl.RankingResponse
import java.util.ArrayList
import java.util.Collections
import java.util.HashMap

import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.RealmClass

@RealmClass
public open class IndividualPBAPokemon (
        public open var id: Long = -1,
        public open var status: Int = UNKNOWN,
        public open var item: String = "unknown",
        public open var characteristic: String = "unknown",
        public open var ability: String = "unknown",
        public open var skillNo1: Skill = Skill(),
        public open var skillNo2: Skill = Skill(),
        public open var skillNo3: Skill = Skill(),
        public open var skillNo4: Skill = Skill(),
        public open var hpEffortValue: Int = UNKNOWN,
        public open var hpRatio: Int = 100,
        public open var attackEffortValue: Int = UNKNOWN,
        public open var defenseEffortValue: Int = UNKNOWN,
        public open var specialAttackEffortValue: Int = UNKNOWN,
        public open var specialDefenseEffortValue: Int = UNKNOWN,
        public open var speedEffortValue: Int = UNKNOWN,
        public open var hpValue: Int = UNKNOWN,
        public open var attackValue: Int = UNKNOWN,
        public open var defenseValue: Int = UNKNOWN,
        public open var specialAttackValue: Int = UNKNOWN,
        public open var specialDefenseValue: Int = UNKNOWN,
        public open var speedValue: Int = UNKNOWN,
        public open var master : PokemonMasterData = PokemonMasterData()
        ): RealmObject() {

    companion object {
        const val UNKNOWN = -1

        fun create(id: Long, master : PokemonMasterData): IndividualPBAPokemon{
            return IndividualPBAPokemon(id, 0, "", "", "", Skill(), Skill(), Skill(), Skill(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, master)
        }
    }

    @Ignore
    var trend: RankingResponse? = null

    private fun valueSort(statistics: Map<String, Int>): List<Any> {
        val entries = ArrayList(statistics.entries)
        Collections.sort(entries) { o1, o2 -> o2.value.compareTo(o1.value)}
        return entries
    }

    fun calcHp(): Int {
        return master.getHPValue(31, hpEffortValue)
    }

    fun calcAttack(): Int {
        return master.getAttackValue(31, attackEffortValue)
    }

    fun calcDefense(): Int {
        return master.getDefenseValue(31, defenseEffortValue)
    }

    fun calcSpecialAttack(): Int {
        return master.getSpecialAttackValue(31, specialAttackEffortValue)
    }

    fun calcSpecialDefense(): Int {
        return master.getSpecialDefenseValue(31, specialDefenseEffortValue)
    }

    fun calcSpeed(): Int {
        return master.getSpeedValue(31, speedEffortValue)
    }

    fun getSpeedValue(characteristicNo: Int): Int {
        val rate = Characteristic.CHARACTERISTIC_TABLE[characteristicNo]
        if (4 < rate.size) {
            return (speedValue.times(rate[4] as Float)).toInt()
        } else {
            return speedValue
        }
    }





    fun calcDamage(attackSide: IndividualPBAPokemon, skill: Skill): Map<Float, Int> {
        val dSideCharacteristics = trend!!.rankingPokemonTrend.seikakuInfo
        val aSideCharacteristics = attackSide.characteristic

        val resultMap = HashMap<Float, Int>()
//        for (dc in dSideCharacteristics) {
//            Log.v("calcDamage", dc.name)
//            val dRevision = dc.revision
//
//                val aRevision = ac.revision
//                val rate = (dc.usageRate * ac.usageRate).toFloat()
//                var damage = 0
//                when (skill.category) {
//                    0 -> {
//                        damage = (22f * skill.power.toFloat() * attackSide.attackValue.toFloat() * aRevision[0] / deffenceValue * dRevision[1] / 50 + 2).toInt()
//                        resultMap.put(rate, damage)
//                    }
//                    1 -> {
//                        damage = (22f * skill.power.toFloat() * attackSide.specialAttackValue.toFloat() * aRevision[2] / specialDeffenceValue * dRevision[3] / 50 + 2).toInt()
//                        resultMap.put(rate, damage)
//                    }
//                    else -> {
//                    }
//                }//this is not attack
//        }
        return resultMap
    }

    val abilities: List<String>
        get() {
            val temp = ArrayList<String>()
            temp.add(master.ability1)
            if (master.ability2 != "-") {
                temp.add(master.ability2)
            }
            if (master.abilityd != "-") {
                temp.add(master.abilityd)
            }
            return temp
        }

    fun calcATypeScale(type: Type.Code): Map<String, Int> {
        val scaleMap = HashMap<String, Int>()
        var result: Int

        //タイプ相性に関係する特性がある場合、その値を格納する
        //ふしぎなまもりは特別
        for (ability in abilities) {
            if ("ふしぎなまもり".equals(ability)) {
                if (type === Type.Code.FIRE || type === Type.Code.GHOST || type === Type.Code.FLYING ||
                        type === Type.Code.ROCK || type === Type.Code.DARK) {
                    scaleMap.put(ability, 200)
                } else {
                    scaleMap.put(ability, 0)
                }
            } else {
                val scaleByAbility = Ability.calcTypeScale(ability, type)
                result = (scaleByAbility * Type.calculateAffinity(type, master) * 100f).toInt()
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

    fun calcAllTypeScale(): Map<Type.Code, Map<String, Int>> {
        val scaleMap = HashMap<Type.Code, Map<String, Int>>()
        for (type in Type.Code.values()) {
            val temp = calcATypeScale(type)
            scaleMap.put(type, temp)
        }
        return scaleMap
    }

    override fun toString(): String {
        return "id:$id, $master, item:$item, ability:$ability, characteristic:$characteristic, skill1:$skillNo1, skill2:$skillNo2, skill3:$skillNo3, skill4:$skillNo4, H:$hpEffortValue, A:$attackEffortValue, B:$defenseEffortValue, C:$specialAttackEffortValue, D:$specialDefenseEffortValue, S:$speedEffortValue"
    }

}
