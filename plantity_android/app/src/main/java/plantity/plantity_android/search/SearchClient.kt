package plantity.plantity_android.search

import plantity.plantity_android.main.AddPlantService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// object명 Retrofit으로 변경해서 서버 연동 필요한 모든 곳에서 사용??
object SearchClient {
    private const val baseUrl = "http://plantity.shop/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())  // 데이터를 파싱하는 converter(JSON을 코틀린에서 바로 사용 가능한 데이터 형식으로 변환)
        .build()

    val service = retrofit.create(SearchService::class.java)  // Retrofit 객체를 이용해 Interface 구현
    val addPlantService = retrofit.create(AddPlantService::class.java)  // 내 식물 등록하기 인터페이스 구현
}