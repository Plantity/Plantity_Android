package plantity.plantity_android.search

import plantity.plantity_android.main.*
import plantity.plantity_android.plantlogs.MyPlantDetailService
import plantity.plantity_android.plantlogs.MyPlantLogService
import plantity.plantity_android.plantlogs.MyPlantService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// object명 Retrofit으로 변경해서 서버 연동 필요한 모든 곳에서 사용??
object RetrofitClient {
    private const val baseUrl = "http://plantity.shop/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())  // 데이터를 파싱하는 converter(JSON을 코틀린에서 바로 사용 가능한 데이터 형식으로 변환)
        .build()

    val service: SearchService = retrofit.create(SearchService::class.java)  // Retrofit 객체를 이용해 Interface 구현
    val addPlantService: AddPlantService = retrofit.create(AddPlantService::class.java)  // 내 식물 등록하기 인터페이스 구현
    val myPlantService: MyPlantService = retrofit.create(MyPlantService::class.java)  // 내 식물 리스트 조회하기 인터페이스 구현
    val myPlantDetailService: MyPlantDetailService = retrofit.create(MyPlantDetailService::class.java)  // 내 식물 상세 정보 조회 인터페이스 구현
    val myPlantLogService: MyPlantLogService = retrofit.create(MyPlantLogService::class.java)
    val waterAssService: WaterAssService = retrofit.create(WaterAssService::class.java)
    val lookAssService: LookAssService = retrofit.create(LookAssService::class.java)
    val sunAssService: SunAssService = retrofit.create(SunAssService::class.java)
    val repotAssService: RepotAssService = retrofit.create(RepotAssService::class.java)
}