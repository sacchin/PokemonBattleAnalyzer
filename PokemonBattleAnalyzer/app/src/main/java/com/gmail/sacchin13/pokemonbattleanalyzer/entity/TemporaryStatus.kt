package com.gmail.sacchin13.pokemonbattleanalyzer.entity

import android.os.Parcel
import android.os.Parcelable

class TemporaryStatus (
    var tempStatus: Int = StatusAilment.no(StatusAilment.Code.UNKNOWN),
    var tempHpRatio: Int = 100,
    var tempHpValue: Int = 0,
    var tempAttack: Int = 6,
    var tempDefense: Int = 6,
    var tempSpecialAttack: Int = 6,
    var tempSpecialDefense: Int = 6,
    var tempSpeed: Int = 6,
    var tempMega: Boolean = false): Parcelable
{
    val rank = arrayOf(-6, -5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5, 6)

    fun setAttackRank(position: Int) {
        tempAttack = rank[position]
    }

    fun setDefenseRank(position: Int) {
        tempDefense = rank[position]
    }

    fun setSpecialAttackRank(position: Int) {
        tempSpecialAttack = rank[position]
    }

    fun setSpecialDefenseRank(position: Int) {
        tempSpecialDefense = rank[position]
    }

    fun setSpeedRank(position: Int) {
        tempSpeed = rank[position]
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
    : this(`in`.readInt(), `in`.readInt(), `in`.readInt(), `in`.readInt(), `in`.readInt(), `in`.readInt(), `in`.readInt(), `in`.readInt(), `in`.readInt() == 0) { }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest!!.writeInt(tempStatus)
        dest.writeInt(tempHpRatio)
        dest.writeInt(tempHpValue)
        dest.writeInt(tempAttack)
        dest.writeInt(tempDefense)
        dest.writeInt(tempSpecialAttack)
        dest.writeInt(tempSpecialDefense)
        dest.writeInt(tempSpeed)
        dest.writeInt(if(tempMega) 0 else 1)
    }

    override fun describeContents(): Int {
        return 0
    }

    override  fun toString(): String{
        return "${tempStatus}, ${tempHpRatio}, ${tempHpValue}, ${tempAttack}, ${tempDefense}, ${tempSpecialAttack}, ${tempSpecialDefense}, ${tempSpeed}, ${tempMega}"
    }
}