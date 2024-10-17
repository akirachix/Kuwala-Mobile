
import android.os.Parcel
import android.os.Parcelable

data class RecipeDetailsDisplay(
    val name: String,
    val ingredients: String,
    val instructions: String?,
    val url: String,
    val image: String? // optional, in case you want to show an image later
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString(),
        parcel.readString() ?: "",
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(ingredients)
        parcel.writeString(instructions)
        parcel.writeString(url)
        parcel.writeString(image)
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


//import android.os.Parcelable
//import kotlinx.parcelize.Parcelize
//
//@Parcelize
//data class RecipeDetailsDisplay(
//    val name: String,
//    val ingredients: String,
//    val instructions: String,
//    val url: String?, // Make url nullable
//) : Parcelable























//data class RecipeDetailsDisplay(
//    val name: String,
//    val ingredients: String,  // Ensure this is a list of strings
//    val instructions: String,
//    val url: String,  // If you are still keeping the URL
//    val image: String? = null  // Image URL if needed
//)





//import android.os.Parcel
//import android.os.Parcelable
//
//data class RecipeDetailsDisplay(
//    val name: String,
//    val ingredients: String,
//    val instructions: String,
//    val url: String,
//    val image: String
//) : Parcelable {
//    constructor(parcel: Parcel) : this(
//        parcel.readString() ?: "",
//        parcel.readString() ?: "",
//        parcel.readString() ?: "",
//        parcel.readString() ?: "",
//        parcel.readString() ?: ""
//    )
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeString(name)
//        parcel.writeString(ingredients)
//        parcel.writeString(instructions)
//        parcel.writeString(url)
//        parcel.writeString(image)
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
