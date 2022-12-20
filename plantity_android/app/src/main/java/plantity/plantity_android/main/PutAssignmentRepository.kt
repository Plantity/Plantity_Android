package plantity.plantity_android.main

import android.util.Log
import plantity.plantity_android.search.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PutAssignmentRepository {
    fun putWaterAss(userId: Int, myPlantId: Int){
        val call = RetrofitClient.waterAssService.putWaterAssignment(userId, myPlantId)

        call.enqueue(object: Callback<PostAssignmentDTO>{
            override fun onResponse(
                call: Call<PostAssignmentDTO>,
                response: Response<PostAssignmentDTO>
            ) {
                Log.d("test", "물 주기 과제!! $response")
                if(response.body()!!.success) {
                    Log.d("test","======== 물주기 과제 ${response.body()!!.success} =========")
                    Log.d("test", "통신 성공 msg: ${response.body()!!.msg}")

                }
                else{
                    Log.d("test", "======= 물주기 과제 성공 but 문제 ========")
                    Log.d("test", "통신 성공 code: ${response.body()!!.msg}")
                }
            }

            override fun onFailure(call: Call<PostAssignmentDTO>, t: Throwable) {
                Log.d("test", "======== 물주기 과제 실패 =========")
                Log.d("test", "${t.message}")
            }
        })
    }

    fun putSunAss(userId: Int, myPlantId: Int){
        val call = RetrofitClient.sunAssService.putSunAssignment(userId, myPlantId)

        call.enqueue(object: Callback<PostAssignmentDTO>{
            override fun onResponse(
                call: Call<PostAssignmentDTO>,
                response: Response<PostAssignmentDTO>
            ) {
                if(response.body()!!.success) {
                    Log.d("test","======== 햇빛 과제 ${response.body()!!.success} =========")
                    Log.d("test", "통신 성공 msg: ${response.body()!!.msg}")

                }
                else{
                    Log.d("test", "======= 햇빛 과제 성공 but 문제 ========")
                    Log.d("test", "통신 성공 code: ${response.body()!!.msg}")
                }
            }

            override fun onFailure(call: Call<PostAssignmentDTO>, t: Throwable) {
                Log.d("test", "======== 햇빛 과제 실패 =========")
                Log.d("test", "${t.message}")
            }
        })
    }

    fun putLookAss(userId: Int, myPlantId: Int){
        val call = RetrofitClient.lookAssService.putLookAssignment(userId, myPlantId)

        call.enqueue(object: Callback<PostAssignmentDTO>{
            override fun onResponse(
                call: Call<PostAssignmentDTO>,
                response: Response<PostAssignmentDTO>
            ) {
                if(response.body()!!.success) {
                    Log.d("test","======== 돌보기 과제 ${response.body()!!.success} =========")
                    Log.d("test", "통신 성공 msg: ${response.body()!!.msg}")

                }
                else{
                    Log.d("test", "======= 돌보기 과제 성공 but 문제 ========")
                    Log.d("test", "통신 성공 code: ${response.body()!!.msg}")
                }
            }

            override fun onFailure(call: Call<PostAssignmentDTO>, t: Throwable) {
                Log.d("test", "======== 돌보기 과제 실패 =========")
                Log.d("test", "${t.message}")
            }
        })
    }

    fun putRepotAss(userId: Int, myPlantId: Int){
        val call = RetrofitClient.repotAssService.putRepotAssignment(userId, myPlantId)

        call.enqueue(object: Callback<PostAssignmentDTO>{
            override fun onResponse(
                call: Call<PostAssignmentDTO>,
                response: Response<PostAssignmentDTO>
            ) {
                if(response.body()!!.success) {
                    Log.d("test","======== 분갈이 과제 ${response.body()!!.success} =========")
                    Log.d("test", "통신 성공 msg: ${response.body()!!.msg}")

                }
                else{
                    Log.d("test", "======= 분갈이 과제 성공 but 문제 ========")
                    Log.d("test", "통신 성공 code: ${response.body()!!.msg}")
                }
            }

            override fun onFailure(call: Call<PostAssignmentDTO>, t: Throwable) {
                Log.d("test", "======== 분갈이 과제 실패 =========")
                Log.d("test", "${t.message}")
            }
        })
    }
}