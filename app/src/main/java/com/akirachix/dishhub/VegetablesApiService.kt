import com.akirachix.dishhub.Vegetables
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("api/categories/1/food-items/")
    fun getFoodItems(): Call<List<Vegetables>>
}
