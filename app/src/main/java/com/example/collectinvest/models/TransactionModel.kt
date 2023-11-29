package com.example.collectinvest.models

import android.os.Parcel
import android.os.Parcelable

data class TransactionModel(
    val Transaction_id: Int,
    val Amount: Int,
    val Status: String,
    val Wallet_id: Int
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(Transaction_id)
        parcel.writeInt(Amount)
        parcel.writeString(Status)
        parcel.writeInt(Wallet_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TransactionModel> {
        override fun createFromParcel(parcel: Parcel): TransactionModel {
            return TransactionModel(parcel)
        }

        override fun newArray(size: Int): Array<TransactionModel?> {
            return arrayOfNulls(size)
        }
    }
}