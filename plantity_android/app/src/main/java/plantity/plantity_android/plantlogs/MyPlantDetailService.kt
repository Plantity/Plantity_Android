package plantity.plantity_android.plantlogs

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MyPlantDetailService {
    @GET("/myplant/{userId}/{myPlantId}")
    fun getMyPlantDetail(@Path("userId") userId: Int, @Path("myPlantId") myPlantId: Int): Call<MyPlantDetailResponse>
}