package plantity.plantity_android

import android.util.Log
import plantity.plantity_android.search.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {
    fun getUser(userId: Int, callBack: (List<UserInfo>) -> (Unit)){
        val call = RetrofitClient.userService.getUser(userId)
        //val idList = ArrayList<Int>()
        //Thread{
        call.enqueue(object: Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {  // 통신 성공
                Log.d("test", "======= 내 식물 조회 ${response.body()!!.isSuccess} =======")
                if(response.body()!!.isSuccess) {  // 응답 잘 받은 경우
                    Log.d("test", "서버 code: ${response.body()!!.code}")
                    Log.d("test", "서버 msg: ${response.body()!!.message}")
                    Log.d("test", "서버 body: ${response.body()!!}")

//                    callBack(response.body()!!.result)
                }
                else{  // 통신은 성공했지만 응답에 문제가 있는 경우
                    Log.d("test", "Error!! 서버 code: ${response.body()!!.code}")
                    Log.d("test", "Error!! 서버 msg: ${response.body()!!.message}")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {  // 통신 실패
                Log.d("test", "======= 내 식물 조희 실패, code: ${t.message} =======")
            }
        })
        //}.start()
        //return idList
    }
}