package plantity.plantity_android

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PlantDetailService {
    @GET("plant/{cntntsNo}")
    fun getPlantDetail(@Path("cntntsNo") cntntsNo: String): Call<PlantData>
}