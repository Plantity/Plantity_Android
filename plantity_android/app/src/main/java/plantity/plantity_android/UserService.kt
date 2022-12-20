package plantity.plantity_android

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
    @GET("/users/{userId}")
    fun getUser(@Path("userId") userId:Int): Call<User>
}