package plantity.plantity_android.main

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface AddPlantService {
    @Multipart
    @POST("myplant/save/{userId}")
//    @Headers("accept: application/json",
//        "content-type: application/json")
    fun postNewPlant(
        @Path("userId") userId: Int,
        @Query ("cntntsNo") cntntsNo: String,
        @Part image: MultipartBody.Part?,
        @Part ("plantName") plantName: RequestBody,
        @Part ("plantNickName") plantNickName: RequestBody,
        @Part ("plantAdaptTime")plantAdaptTime: RequestBody
    ): Call<AddPlantResponse>
}