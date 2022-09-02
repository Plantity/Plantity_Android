package plantity.plantity_android.search

import retrofit2.Call  // ?? okhttp도 존재함
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("")
    fun loadPlants(@Query("page") page: Int): Call<SearchData>
}