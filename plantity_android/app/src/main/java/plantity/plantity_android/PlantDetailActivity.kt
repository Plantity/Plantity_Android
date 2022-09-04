package plantity.plantity_android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_plant_detail.*

class PlantDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant_detail)

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
        plant_photo.setImageResource(R.drawable.ic_plant_log_image) //식물사진
        plant_name.setText("몬스테라") //식물한글이름
        difficulty.append("★") //난이도
        short_comment.setText("자라면서 잎에 구멍이 생기는 것으로 유명한 인테리어 식물") //짧은 설명
        water_comment.setText("물을 7일에 한 번씩 흙이 마르면 주세요.") //물주기
        sun_comment.setText("햇빛이 적당한 것을 좋아해요.") //햇빛
        name.append("Monstera deliciosa Liebm.") //학명
        eng_name.append("") //영문명
        country.append("") //원산지
        flower_time.append("") //개화시기
        flower_color.append("") //꽃색깔
        flower_lang.append("") //꽃말
    }
}