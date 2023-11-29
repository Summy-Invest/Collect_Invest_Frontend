package com.example.collectinvest.models

import android.os.Parcel
import android.os.Parcelable

data class WalletModel (
    val Wallet_id: Int,
    var Money: Double,
    val Status: String,
    val User_id: Int
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readString() ?: "",
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(Wallet_id)
        parcel.writeDouble(Money)
        parcel.writeString(Status)
        parcel.writeInt(User_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WalletModel> {
        override fun createFromParcel(parcel: Parcel): WalletModel {
            return WalletModel(parcel)
        }

        override fun newArray(size: Int): Array<WalletModel?> {
            return arrayOfNulls(size)
        }
    }
}