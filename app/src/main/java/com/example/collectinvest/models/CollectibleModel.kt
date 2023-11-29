package com.example.collectinvest.models

import android.os.Parcel
import android.os.Parcelable



data class CollectibleModel(
    val Collectible_ID: Int,
    val Name: String,
    val Description: String,
    val Photo: String,
    val Category_ID: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(Collectible_ID)
        parcel.writeString(Name)
        parcel.writeString(Description)
        parcel.writeString(Photo)
        parcel.writeInt(Category_ID)
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
