package plantity.plantity_android.main

import android.util.Log
import plantity.plantity_android.search.RetrofitClient
import plantity.plantity_android.search.SearchData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainSearchRepository {
    fun getMainSearchPlants(page: Int, mCallback: AddPlantActivity){
        Log.d("test", "inside MainSearchRepository")
        val call = RetrofitClient.service.loadPlants(page, 10)

        call.enqueue(object: Callback<SearchData> {  // enqueue: 비동기 방식으로 통신 요청
            override fun onResponse(  // 통신에 성공한 경우
                call: Call<SearchData>,
                response: Response<SearchData>
            ){
                if(response.body()!!.isSuccess) {  // 응답 잘 받은 경우
                    Log.d("test", "통신 성공 여부: ${response.body()!!.isSuccess}")
                    Log.d("test", "통신 성공 code: ${response.body()!!.code}")
                    Log.d("test", "통신 성공 msg: ${response.body()!!.message}")
                    Log.d("test", "통신 성공 body: ${response.body()!!}")
                    mCallback.loadMainSearchPlants(response.body()!!.result)
                }
                else{  // 통신은 성공했지만 응답에 문제가 있는 경우
                    Log.d("test", "통신 성공 but 문제, code: ${response.body()!!.code}")
                    Log.d("test", "통신 성공 but 응답 문제: ${response.body()!!.message}")
                }
            }

            override fun onFailure(call: Call<SearchData>, t: Throwable) {  // 통신에 실패한 경우
//                Toast.makeText(getApplicationContext(), "통신 실패", Toast.LENGTH_SHORT).show()
                Log.d("test", "서버 통신 실패, code: ${t.message}")
            }
        })
    }
}