package com.example.collectinvest.models

import android.os.Parcel
import android.os.Parcelable



data class CollectibleModel(
    val id: Long,
    val name: String,
    val description: String,
    val category: String,
    val photoUrl: String,
    val currentPrice: Double,
    var availableShares: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(category)
        parcel.writeString(photoUrl)
        parcel.writeDouble(currentPrice)
        parcel.writeInt(availableShares)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CollectibleModel> {
        override fun createFromParcel(parcel: Parcel): CollectibleModel {
            return CollectibleModel(parcel)
        }

        override fun newArray(size: Int): Array<CollectibleModel?> {
            return arrayOfNulls(size)
        }
    }
}
