package plantity.plantity_android

import android.util.Log
import plantity.plantity_android.search.SearchActivity
import plantity.plantity_android.search.RetrofitClient
import plantity.plantity_android.search.SearchData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/* 파일 삭제 예정 */
/*
class PlantDetailRepository {
    fun getSearchPlants(page: Int, mCallback: SearchActivity){
        Log.d("test", "inside SearchRepository")
        val call = SearchClient.service.loadPlants(page, 10)

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
                    mCallback.loadComplete(response.body()!!.result)
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
}*/