package com.example.collectinvest.models

import android.os.Parcel
import android.os.Parcelable

data class PriceModel (
    val Price_ID: Int,
    val Date: String,
    val Price: Double,
    val Collectible_ID: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readInt(),
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(Price_ID)
        parcel.writeString(Date)
        parcel.writeDouble(Price)
        parcel.writeInt(Collectible_ID)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PriceModel> {
        override fun createFromParcel(parcel: Parcel): PriceModel {
            return PriceModel(parcel)
        }

        override fun newArray(size: Int): Array<PriceModel?> {
            return arrayOfNulls(size)
        }
    }
}
