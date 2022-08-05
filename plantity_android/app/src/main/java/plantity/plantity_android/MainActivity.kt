package plantity.plantity_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import plantity.plantity_android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
        binding.logBtn.setOnClickListener {
            val intent = Intent(this, PlantLogActivity::class.java)
            startActivity(intent)
        }
    }
}