package plantity.plantity_android.plantlogs

import android.util.Log
import okhttp3.MediaType
import okhttp3.RequestBody
import plantity.plantity_android.search.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPlantsRepository {
    fun getMyPlantList(userId: Int, callBack: (List<MyPlantInfo>) -> (Unit)){
        val call = RetrofitClient.myPlantService.getMyPlants(userId)
        //val idList = ArrayList<Int>()
        //Thread{
        call.enqueue(object: Callback<MyPlants> {
            override fun onResponse(call: Call<MyPlants>, response: Response<MyPlants>) {  // 통신 성공
                Log.d("test", "======= 내 식물 조회 ${response.body()!!.isSuccess} =======")
                if(response.body()!!.isSuccess) {  // 응답 잘 받은 경우
                    Log.d("test", "서버 code: ${response.body()!!.code}")
                    Log.d("test", "서버 msg: ${response.body()!!.message}")
                    Log.d("test", "서버 body: ${response.body()!!}")

                    callBack(response.body()!!.result)
                }
                else{  // 통신은 성공했지만 응답에 문제가 있는 경우
                    Log.d("test", "Error!! 서버 code: ${response.body()!!.code}")
                    Log.d("test", "Error!! 서버 msg: ${response.body()!!.message}")
                }
            }

            override fun onFailure(call: Call<MyPlants>, t: Throwable) {  // 통신 실패
                Log.d("test", "======= 내 식물 조희 실패, code: ${t.message} =======")
            }
        })
        //}.start()
        //return idList
    }

    fun getMyPlantLog(userId: Long, plantId: Long, logDate: String){
        val userIdBody: RequestBody = RequestBody.create(MediaType.parse("application/long"), userId.toString())
        val plantIdBody: RequestBody = RequestBody.create(MediaType.parse("application/json"), plantId.toString())
        val logDateBody: RequestBody = RequestBody.create(MediaType.parse("application/json"), logDate)

        val call = RetrofitClient.myPlantLogService.getMyPlantLog(userIdBody, plantIdBody, logDateBody)

        call.enqueue(object: Callback<MyPlantLog>{
            override fun onResponse(call: Call<MyPlantLog>, response: Response<MyPlantLog>) {  // 통신 성공
                Log.d("test", "======= 내 식물 로그 조회 ${response.body()!!.success} =======")
                if(response.body()!!.success) {  // 응답 잘 받은 경우
                    Log.d("test", "서버 msg: ${response.body()!!.msg}")
                    Log.d("test", "서버 body: ${response.body()!!}")
                }
                else{
                    Log.d("test", "ERROR!! 서버 msg: ${response.body()!!.msg}")
                }
            }

            override fun onFailure(call: Call<MyPlantLog>, t: Throwable) {  // 통신 실패
                Log.d("test", "======= 내 식물 로그 조회 실패, code: ${t.message} =======")
            }
        })
    }
}