package com.example.collectinvest.models

import android.os.Parcel
import android.os.Parcelable

data class ProductAppearModel (
    val Name: String,
    val Photo: String,
    val Description: String,
    val Category: String,
    val Price: Double
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readDouble()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Name)
        parcel.writeString(Photo)
        parcel.writeString(Description)
        parcel.writeString(Category)
        parcel.writeDouble(Price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProductAppearModel> {
        override fun createFromParcel(parcel: Parcel): ProductAppearModel {
            return ProductAppearModel(parcel)
        }

        override fun newArray(size: Int): Array<ProductAppearModel?> {
            return arrayOfNulls(size)
        }
    }
}
