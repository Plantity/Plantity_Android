package plantity.plantity_android.guideline

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_guide_line_detail.*
import plantity.plantity_android.R

class GuideLineDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide_line_detail)

        //intent
        val titlename = intent.getStringExtra("title")

        if(titlename == "실내식물 가지치기 하는 방법"){
            bigtitle.setText(titlename)
            subtitle1.setText("1. 죽은 잎과 가지, 꽃 제거")
            content1.setText(R.string.guide1_1)
            image1.setImageResource(R.drawable.kakao_login_page)
            subtitle2.setText("2. 지나치게 많이 자란 가지와 줄기 잘라내기")
            content2.setText(R.string.guide1_2)
            image2.setImageResource(R.drawable.kakao_login_page)
            subtitle3.setText("3. 실내식물 관리하기")
            content3.setText(R.string.guide1_3)
            image3.setImageResource(R.drawable.kakao_login_page)
        }
        else if(titlename == "과습된 식물 살리는 방법"){
            bigtitle.setText(titlename)
            subtitle1.setText("1. 과습 여부 파악하기")
            content1.setText(R.string.guide2_1)
            image1.setImageResource(R.drawable.kakao_login_page)
            subtitle2.setText("2. 과습 식물 살리기")
            content2.setText(R.string.guide2_2)
            image2.setImageResource(R.drawable.kakao_login_page)
            subtitle3.setText("3. 과습 후 관리하기")
            content3.setText(R.string.guide2_3)
            image3.setImageResource(R.drawable.kakao_login_page)
        }
        else if(titlename =="실내에서 선인장 기르는 방법"){
            bigtitle.setText(titlename)
            subtitle1.setText("1. 새로운 식물을 번식하기")
            content1.setText(R.string.guide3_1)
            image1.setImageResource(R.drawable.kakao_login_page)
            subtitle2.setText("2. 선인장 보살피기")
            content2.setText(R.string.guide3_2)
            image2.setImageResource(R.drawable.kakao_login_page)
            subtitle3.setText("3. 흔하게 발생하는 문제들을 해결하기")
            content3.setText(R.string.guide3_3)
            image3.setImageResource(R.drawable.kakao_login_page)
        }
        else if(titlename =="식물 건강하게 키우는 방법"){
            bigtitle.setText(titlename)
            subtitle1.setText("1. 올바른 환경 조성하기")
            content1.setText(R.string.guide4_1)
            image1.setImageResource(R.drawable.kakao_login_page)
            subtitle2.setText("2. 물과 양분 제공")
            content2.setText(R.string.guide4_2)
            image2.setImageResource(R.drawable.kakao_login_page)
            subtitle3.setText("3. 식물 키우는 간단한 요령")
            content3.setText(R.string.guide4_3)
            image3.setImageResource(R.drawable.kakao_login_page)
        }
        else if(titlename =="실내식물 관리하는 방법"){
            bigtitle.setText(titlename)
            subtitle1.setText("1. 식물에 물을 지속해서 주기")
            content1.setText(R.string.guide5_1)
            image1.setImageResource(R.drawable.kakao_login_page)
            subtitle2.setText("2. 실내식물 관리하기")
            content2.setText(R.string.guide5_2)
            image2.setImageResource(R.drawable.kakao_login_page)
            subtitle3.setText("3. 식물을 제대로 알기")
            content3.setText(R.string.guide5_3)
            image3.setImageResource(R.drawable.kakao_login_page)
        }
        else if(titlename =="다육이 잎꽂이 하는 방법"){
            bigtitle.setText(titlename)
            subtitle1.setText("1. 잎 떼어내 말리기")
            content1.setText(R.string.guide6_1)
            image1.setImageResource(R.drawable.kakao_login_page)
            subtitle2.setText("2. 새로운 뿌리 돋아나게 하기")
            content2.setText(R.string.guide6_2)
            image2.setImageResource(R.drawable.kakao_login_page)
            subtitle3.setText("3. 과습 후 관리하기")
            content3.setText(R.string.guide6_3)
            image3.setImageResource(R.drawable.kakao_login_page)
        }
        else if(titlename =="분갈이 하는 방법"){
            bigtitle.setText(titlename)
            subtitle1.setText("1. 과습 여부 파악하기")
            content1.setText(R.string.guide7_1)
            image1.setImageResource(R.drawable.kakao_login_page)
            subtitle2.setText("2. 과습 식물 살리기")
            content2.setText(R.string.guide7_2)
            image2.setImageResource(R.drawable.kakao_login_page)
            subtitle3.setText("3. 아기 다육이 옮겨 심고 키우기")
            content3.setText(R.string.guide7_3)
            image3.setImageResource(R.drawable.kakao_login_page)
        }

        back_button.setOnClickListener{
            finish()
        }

    }
}