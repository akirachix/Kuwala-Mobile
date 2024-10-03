//package com.akirachix.dishhub
//
//import android.os.Parcel
//import android.os.Parcelable
//
//
//data class RecipeDetailsDisplay(
//    val name: String,
//    val ingredients: String,  // Not ArrayList<String>?
//    val instructions: String
//) : Parcelable {
//    constructor(parcel: Parcel) : this(
//        parcel.readString().toString(),
//        TODO("ingredients"),
//        parcel.readString().toString()
//    ) {
//    }
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeString(name)
//        parcel.writeString(instructions)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<RecipeDetailsDisplay> {
//        override fun createFromParcel(parcel: Parcel): RecipeDetailsDisplay {
//            return RecipeDetailsDisplay(parcel)
//        }
//
//        override fun newArray(size: Int): Array<RecipeDetailsDisplay?> {
//            return arrayOfNulls(size)
//        }
//    }
//}




import android.os.Parcel
import android.os.Parcelable

data class RecipeDetailsDisplay(
    val name: String,
    val ingredients: String,
    val instructions: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "", // or provide a default value
        parcel.readString() ?: "", // or provide a default value
        parcel.readString() ?: ""  // or provide a default value
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(ingredients)
        parcel.writeString(instructions)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RecipeDetailsDisplay> {
        override fun createFromParcel(parcel: Parcel): RecipeDetailsDisplay {
            return RecipeDetailsDisplay(parcel)
        }

        override fun newArray(size: Int): Array<RecipeDetailsDisplay?> {
            return arrayOfNulls(size)
        }
    }
}