package com.example.collectinvest.models

import android.os.Parcel
import android.os.Parcelable

data class ActualPriceModel (
    val Price_ID: Int,
    val Price: Double,
    val Collectible_ID: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readInt(),
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(Price_ID)
        parcel.writeDouble(Price)
        parcel.writeInt(Collectible_ID)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ActualPriceModel> {
        override fun createFromParcel(parcel: Parcel): ActualPriceModel {
            return ActualPriceModel(parcel)
        }

        override fun newArray(size: Int): Array<ActualPriceModel?> {
            return arrayOfNulls(size)
        }
    }
}
