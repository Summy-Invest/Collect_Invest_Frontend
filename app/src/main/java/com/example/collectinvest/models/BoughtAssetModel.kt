package com.example.collectinvest.models

import android.os.Parcel
import android.os.Parcelable
import java.sql.Date

data class BoughtAssetModel (
    val Order_Id: Int,
    val Order_date: String,
    var Count: Int,
    val Collectible_ID: Long,
    val User_ID: Int,
    val Transaction_id: Int
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readLong(),
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(Order_Id)
        parcel.writeString(Order_date)
        parcel.writeInt(Count)
        parcel.writeLong(Collectible_ID)
        parcel.writeInt(User_ID)
        parcel.writeInt(Transaction_id)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BoughtAssetModel> {
        override fun createFromParcel(parcel: Parcel): BoughtAssetModel {
            return BoughtAssetModel(parcel)
        }

        override fun newArray(size: Int): Array<BoughtAssetModel?> {
            return arrayOfNulls(size)
        }
    }
}