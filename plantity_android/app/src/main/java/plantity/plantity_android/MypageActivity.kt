package plantity.plantity_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_first.*
import kotlinx.android.synthetic.main.activity_mypage.*

class MypageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)

        setting_image.setOnClickListener{
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }
    }
}