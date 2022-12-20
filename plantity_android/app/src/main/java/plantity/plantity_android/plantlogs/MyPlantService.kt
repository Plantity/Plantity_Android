package plantity.plantity_android.plantlogs

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MyPlantService {
    @GET("myplant/{userId}")
    fun getMyPlants(@Path("userId") userId: Int): Call<MyPlants>
}

interface MyPlantLogService{
    @GET("myplant/plantLog")
    fun getMyPlantLog(userIdBody: RequestBody, plantIdBody: RequestBody, logDateBody: RequestBody): Call<MyPlantLog>
}