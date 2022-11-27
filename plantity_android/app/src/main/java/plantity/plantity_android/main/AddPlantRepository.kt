package plantity.plantity_android.main

import android.util.Log
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import plantity.plantity_android.search.SearchClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddPlantRepository {
    // image: File, plantName: String, plantNickName: String, plantAdaptTime: String, cntntsNo: String,
    fun postPlantToServer(newPlant: AddPlantData, userId: Int) {
        // val jsonPlantName = JSONObject()
        // val requestBody = image.asRequestBody("image/jpeg".toMediaTypeOrNull())
        // val imageBody = RequestBody.create(MediaType.parse("image/*"), image)
        // image/jpeg 타입은 MIME 타입을 따르기 위함이다. 일반적인 String같은 경우 text/plain과 같이 쓴다.
        val imageBody = MultipartBody.Part.createFormData("image", newPlant.image.name+".png", RequestBody.create(MediaType.parse("image/*"), newPlant.image))
        val plantNameBody: RequestBody = RequestBody.create(MediaType.parse("application/json"), newPlant.plantName)
        val plantNickNameBody: RequestBody = RequestBody.create(MediaType.parse("application/json"), newPlant.plantNickName)
        val plantAdaptTimeBody: RequestBody = RequestBody.create(MediaType.parse("application/json"), newPlant.plantAdaptTime)

        // POST 요청
        val call = SearchClient.addPlantService.postNewPlant(userId, newPlant.cntntsNo, imageBody, plantNameBody, plantNickNameBody, plantAdaptTimeBody)

        call.enqueue(object: Callback<AddPlantResponse> {
            override fun onResponse(call: Call<AddPlantResponse>, response: Response<AddPlantResponse>) {
                if(response.body()!!.isSuccess) {
                    Log.d("test","======== 식물 등록 ${response.body()!!.isSuccess} =========")
                    Log.d("test", "통신 성공 code: ${response.body()!!.code}")
                    Log.d("test", "통신 성공 msg: ${response.body()!!.message}")
                }
                else{
                    Log.d("test", "======= 식물 등록 성공 but 문제 ========")
                    Log.d("test", "통신 성공 code: ${response.body()!!.code}")
                    Log.d("test", "통신 성공 msg: ${response.body()!!.message}")
                }
            }

            override fun onFailure(call: Call<AddPlantResponse>, t: Throwable) {
                Log.d("test", "======== 식물 등록 실패 =========")
                Log.d("test", "${t.message}")
            }
        })
    }
}