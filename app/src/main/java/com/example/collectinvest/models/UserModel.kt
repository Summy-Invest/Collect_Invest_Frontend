package com.example.collectinvest.models

import android.os.Parcel
import android.os.Parcelable

data class UserModel(
    val User_ID: Int,
    val Name: String,
    val Email: String,
    val Password: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(User_ID)
        parcel.writeString(Name)
        parcel.writeString(Email)
        parcel.writeString(Password)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserModel> {
        override fun createFromParcel(parcel: Parcel): UserModel {
            return UserModel(parcel)
        }

        override fun newArray(size: Int): Array<UserModel?> {
            return arrayOfNulls(size)
        }
    }
}