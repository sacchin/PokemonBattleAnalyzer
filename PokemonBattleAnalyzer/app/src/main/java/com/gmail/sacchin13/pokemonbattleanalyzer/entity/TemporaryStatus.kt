package com.gmail.sacchin13.pokemonbattleanalyzer.entity

import android.os.Parcel
import android.os.Parcelable

class TemporaryStatus(
        var tempItem: Int = 0,
        var tempStatus: Int = StatusAilment.no(StatusAilment.Code.UNKNOWN),
        var tempHpRatio: Int = 100,
        var tempHpValue: Int = 0,
        var tempAttack: Int = 6,
        var tempDefense: Int = 6,
        var tempSpecialAttack: Int = 6,
        var tempSpecialDefense: Int = 6,
        var tempSpeed: Int = 6,
        var tempHitProbability: Int = 6,
        var tempAvoidance: Int = 6,
        var tempCritical: Int = 6,
        var tempMigawari: Int = 0,
        var tempMega: Int = MegaPokemonMasterData.NOT_MEGA) : Parcelable {

    fun setAttackRank(position: Int) {
        tempAttack = position
    }

    fun setDefenseRank(position: Int) {
        tempDefense = position
    }

    fun setSpecialAttackRank(position: Int) {
        tempSpecialAttack = position
    }

    fun setSpecialDefenseRank(position: Int) {
        tempSpecialDefense = position
    }

    fun setSpeedRank(position: Int) {
        tempSpeed = position
    }

    fun setHitProbabilityRank(position: Int) {
        tempHitProbability = position
    }

    fun setAvoidanceRank(position: Int) {
        tempAvoidance = position
    }

    fun setCriticalRank(position: Int) {
        tempCritical = position
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<TemporaryStatus> = object : Parcelable.Creator<TemporaryStatus> {
            override fun createFromParcel(source: Parcel): TemporaryStatus {
                return TemporaryStatus(source)
            }

            override fun newArray(size: Int): Array<TemporaryStatus?> {
                return arrayOfNulls(size)
            }
        }
    }

    constructor(`in`: Parcel)
    : this(`in`.readInt(), `in`.readInt(), `in`.readInt(), `in`.readInt(), `in`.readInt(), `in`.readInt(),
            `in`.readInt(), `in`.readInt(), `in`.readInt(), `in`.readInt(), `in`.readInt(), `in`.readInt(), `in`.readInt(), `in`.readInt()) {
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest!!.writeInt(tempItem)
        dest.writeInt(tempStatus)
        dest.writeInt(tempHpRatio)
        dest.writeInt(tempHpValue)
        dest.writeInt(tempAttack)
        dest.writeInt(tempDefense)
        dest.writeInt(tempSpecialAttack)
        dest.writeInt(tempSpecialDefense)
        dest.writeInt(tempSpeed)
        dest.writeInt(tempHitProbability)
        dest.writeInt(tempAvoidance)
        dest.writeInt(tempCritical)
        dest.writeInt(tempMigawari)
        dest.writeInt(tempMega)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "${tempStatus}, ${tempHpRatio}, ${tempHpValue}, ${tempAttack}, ${tempDefense}, ${tempSpecialAttack}, " +
                "${tempSpecialDefense}, ${tempSpeed}, ${tempMega}"
    }
}