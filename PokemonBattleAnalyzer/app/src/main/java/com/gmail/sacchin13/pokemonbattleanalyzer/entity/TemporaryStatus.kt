package com.gmail.sacchin13.pokemonbattleanalyzer.entity

import android.os.Parcel
import android.os.Parcelable
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Rank.Value

class TemporaryStatus(
        var tempItem: Int = 0,
        var tempStatus: Int = StatusAilment.no(StatusAilment.Code.UNKNOWN),
        var tempHpRatio: Int = 100,
        var tempHpValue: Int = 0,
        var tempAttack: Int = Rank.no(Value.DEFAULT),
        var tempDefense: Int = Rank.no(Value.DEFAULT),
        var tempSpecialAttack: Int = Rank.no(Value.DEFAULT),
        var tempSpecialDefense: Int = Rank.no(Value.DEFAULT),
        var tempSpeed: Int = Rank.no(Value.DEFAULT),
        var tempHitProbability: Int = Rank.no(Value.DEFAULT),
        var tempAvoidance: Int = Rank.no(Value.DEFAULT),
        var tempCritical: Int = Rank.no(Value.DEFAULT),
        var tempMigawari: Int = 0,
        var tempMega: Int = MegaPokemonMasterData.NOT_MEGA) : Parcelable {

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