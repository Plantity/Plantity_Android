package plantity.plantity_android

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_plant_detail.*
import plantity.plantity_android.databinding.ActivityPlantDetailBinding
import plantity.plantity_android.databinding.FragmentAddPlantBinding
import plantity.plantity_android.main.AddPlantDialog
import plantity.plantity_android.search.Content
import plantity.plantity_android.search.SearchData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PlantDetailActivity : AppCompatActivity() {
    val binding by lazy { ActivityPlantDetailBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

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
                        binding.plantPhoto.setImageResource(R.drawable.ic_plant_log_image) // 식물 사진
                        binding.plantName.text = cntntsSj // 식물 한글 이름

                        when (managelevelCode){  // 난이도
                            "089001" -> binding.difficulty.append("⭐")
                            "089002" -> binding.difficulty.append("⭐⭐")
                            "089003" -> binding.difficulty.append("⭐⭐⭐")
                        }

                        binding.shortComment.text = adviseInfo // 짧은 설명

                        if(watercycleSprngCodeNm.contains("축축")){  // 물 주기
                            binding.waterComment.text = "흙을 항상 축축하게 유지해주세요"
                        }
                        else if(watercycleSprngCodeNm.contains("촉촉")){
                            binding.waterComment.text = "물을 3일에 한 번씩, 흙이 마르면 주세요."
                        }
                        else if(watercycleSprngCodeNm.contains("표면")){
                            binding.waterComment.text = "물을 7일에 한 번씩, 표면이 마르면 주세요."
                        }
                        else if(watercycleSprngCodeNm.contains("대부분")){
                            binding.waterComment.text = "흙이 바싹 말랐을 떄 조금씩 주세요."
                        }

                        if(lighttdemanddoCodeNm.contains("낮은")){  // 광도
                            binding.sunComment.text = "햇빛이 적은 것을 좋아해요."
                        }
                        else if(lighttdemanddoCodeNm.contains("높은")){
                            binding.sunComment.text = "햇빛을 매우 좋아해요."
                        }
                        else {
                            binding.sunComment.text = "햇빛이 적당한 것을 좋아해요."
                        }
                        binding.name.append(plntbneNm) // 학명
                        binding.engName.append(plntzrNm) // 영문명
                        binding.country.append(orgplceInfo) // 원산지
                        binding.flowerTime.append(ignSeasonCodeNm) // 개화시기
                        binding.flowerColor.append(flclrCodeNm) // 꽃색깔
                    }
                }
                else{  // 통신은 성공했지만 응답에 문제가 있는 경우
                    Log.d("test", "통신 성공 but 문제, code: ${response.body()!!.code}")
                    Log.d("test", "통신 성공 but 응답 문제: ${response.body()!!.message}")
                }
            }

            override fun onFailure(call: Call<PlantData>, t: Throwable) {  // 통신에 실패한 경우
                Toast.makeText(applicationContext, "통신 실패", Toast.LENGTH_SHORT).show()
                Log.d("test", "서버 통신 실패, code: ${t.message}")
            }
        })

        with(binding){
            var like = false //찜한 목록에 있는지 불러오기
            heart.setOnClickListener{
                if(!like){
                    heart.setBackgroundResource(R.drawable.ic_heart_full)
                    like = true
                }
                else{
                    heart.setBackgroundResource(R.drawable.ic_heart)
                    like = false
                }

            }

            // 식물 추가 버튼
            plantAdd.setOnClickListener {
                AddPlantDialog(root.context).show()
            }
        }

        binding.backButton.setOnClickListener{
            this.finish()
        }
    }
}