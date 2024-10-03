import android.os.Parcelable
import kotlinx.android.parcel.Parcelize




@Parcelize
data class Recipes(
    val title: String,
    val ingredients: String,
    val instructions: String
) : Parcelable

