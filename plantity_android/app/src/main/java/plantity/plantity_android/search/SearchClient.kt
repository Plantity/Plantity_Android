package plantity.plantity_android.search

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SearchClient {
    private const val baseUrl = "http://plantity.shop/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())  // 데이터를 파싱하는 converter(JSON을 코틀린에서 바로 사용 가능한 데이터 형식으로 변환)
        .build()

    val service = retrofit.create(SearchService::class.java)!!  // Retrofit 객체를 이용해 Interface 구현
}