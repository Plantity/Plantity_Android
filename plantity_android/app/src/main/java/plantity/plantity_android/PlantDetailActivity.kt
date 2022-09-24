package plantity.plantity_android

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_plant_detail.*
import plantity.plantity_android.search.Content
import plantity.plantity_android.search.SearchData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PlantDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant_detail)

        // intent
        val plantCntntsNo = intent.getStringExtra("selectedPlant")
        Log.d("test","value : "+ plantCntntsNo)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://plantity.shop/")
            .addConverterFactory(GsonConverterFactory.create())  // 데이터를 파싱하는 converter(JSON을 코틀린에서 바로 사용 가능한 데이터 형식으로 변환)
            .build()

        val service = retrofit.create(PlantDetailService::class.java)!!  // Retrofit 객체를 이용해 Interface 구현

        service.getPlantDetail(plantCntntsNo!!).enqueue(object: Callback<PlantData>{
            override fun onResponse(  // 통신에 성공한 경우
                call: Call<PlantData>,
                response: Response<PlantData>
            ){
                if(response.body()!!.isSuccess){  // 응답 잘 받은 경우
//                    Log.d("test", "통신 성공 여부: ${response.body()!!.isSuccess}")
//                    Log.d("test", "통신 성공 code: ${response.body()!!.code}")
//                    Log.d("test", "통신 성공 msg: ${response.body()!!.message}")
//                    Log.d("test", "통신 성공 body: ${response.body()!!.result}")
                    with(response.body()!!.result){
                        plant_photo.setImageResource(R.drawable.ic_plant_log_image) // 식물 사진
                        plant_name.text = cntntsSj // 식물 한글 이름

                        when (managelevelCode){  // 난이도
                            "089001" -> difficulty.append("⭐")
                            "089002" -> difficulty.append("⭐⭐")
                            "089003" -> difficulty.append("⭐⭐⭐")
                        }

                        short_comment.text = adviseInfo // 짧은 설명
                        water_comment.text = watercycleSprngCodeNm // 물 주기 -> 멘트 변경 필요!!!!!
                        sun_comment.text = lighttdemanddoCodeNm  // 햇빛 -> 멘트 변경 필요!!!!
                        name.append(plntbneNm) // 학명
                        eng_name.append(plntzrNm) // 영문명
                        country.append(orgplceInfo) // 원산지
                        flower_time.append(ignSeasonCodeNm) // 개화시기
                        flower_color.append(flclrCodeNm) // 꽃색깔
                        flower_lang.append("") // 꽃말
                    }
                }
                else{  // 통신은 성공했지만 응답에 문제가 있는 경우
                    Log.d("test", "통신 성공 but 문제, code: ${response.body()!!.code}")
                    Log.d("test", "통신 성공 but 응답 문제: ${response.body()!!.message}")
                }
            }

            override fun onFailure(call: Call<PlantData>, t: Throwable) {  // 통신에 실패한 경우
    //                Toast.makeText(getApplicationContext(), "통신 실패", Toast.LENGTH_SHORT).show()
                Log.d("test", "서버 통신 실패, code: ${t.message}")
            }
        })

        back_button.setOnClickListener{
            this.finish()
        }

        var like = false //찜한 목록에 있는지 불러오기
        heart.setOnClickListener{
            if(like==false){
                heart.setBackgroundResource(R.drawable.ic_heart_full)
                like = true
            }
            else{
                heart.setBackgroundResource(R.drawable.ic_heart)
                like = false
            }

        }

        plant_add.setOnClickListener {

        }

        //화면 구성

    }
}