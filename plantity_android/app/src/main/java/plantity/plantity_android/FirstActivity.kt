package plantity.plantity_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_first.*
import plantity.plantity_android.databinding.ActivityFirstBinding

class FirstActivity : AppCompatActivity() {
    val binding by lazy{ActivityFirstBinding.inflate(layoutInflater)}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
        Login_button.setOnClickListener{
        }
        test_skip_button.setOnClickListener{
        }
    }

}