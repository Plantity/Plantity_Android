package plantity.plantity_android.plantlogs

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
/*듀여나ㅏ 사랑해~~*/
interface MyPlantService {
    @GET("myplant/{userId}")
    fun getMyPlants(@Path("userId") userId: Int): Call<MyPlants>
}