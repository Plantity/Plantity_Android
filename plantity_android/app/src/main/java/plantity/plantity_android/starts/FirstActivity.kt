package plantity.plantity_android.starts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_first.*
import plantity.plantity_android.PlantDetailActivity
import plantity.plantity_android.mypages.MypageActivity
import plantity.plantity_android.R
import plantity.plantity_android.databinding.ActivityFirstBinding
import plantity.plantity_android.main.MainActivity

class FirstActivity : AppCompatActivity() {
    val binding by lazy{ActivityFirstBinding.inflate(layoutInflater)}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
        test_button.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        login_button.setOnClickListener{
            val intent = Intent(this, PlantDetailActivity::class.java)
            startActivity(intent)
        }
    }

}