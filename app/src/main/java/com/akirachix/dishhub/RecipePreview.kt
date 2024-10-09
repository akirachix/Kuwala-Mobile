package com.akirachix.dishhub.model

import android.os.Parcel
import android.os.Parcelable

data class RecipePreview(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(idMeal)
        parcel.writeString(strMeal)
        parcel.writeString(strMealThumb)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RecipePreview> {
        override fun createFromParcel(parcel: Parcel): RecipePreview {
            return RecipePreview(parcel)
        }

        override fun newArray(size: Int): Array<RecipePreview?> {
            return arrayOfNulls(size)
        }
    }
}