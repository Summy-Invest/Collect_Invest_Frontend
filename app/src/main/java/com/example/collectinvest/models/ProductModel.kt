package com.example.collectinvest.models

import android.os.Parcel
import android.os.Parcelable
import java.util.Currency
import io.ktor.http.Url
import java.io.Serializable

data class ProductModel(
    val name: String,
    val imgUrl: Url,
    val description: String,
    val price: Double,
    val currency: Currency
) : Parcelable, Serializable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        Url(parcel.readString() ?: ""),
        parcel.readString() ?: "",
        parcel.readDouble(),
        Currency.getInstance(parcel.readString() ?: "")
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(imgUrl.toString())
        parcel.writeString(description)
        parcel.writeDouble(price)
        parcel.writeString(currency.currencyCode)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProductModel> {
        override fun createFromParcel(parcel: Parcel): ProductModel {
            return ProductModel(parcel)
        }

        override fun newArray(size: Int): Array<ProductModel?> {
            return arrayOfNulls(size)
        }
    }
}
