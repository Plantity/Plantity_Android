package plantity.plantity_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import plantity.plantity_android.databinding.ActivityMainBinding
import plantity.plantity_android.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    val binding by lazy { ActivitySearchBinding.inflate(layoutInflater) }
    val todaysPlantFragment by lazy{ TodaysPlantFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setFragment()
    }

    private fun setFragment(){
        val bundle = Bundle()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragmentContainerView, todaysPlantFragment)
        Log.d("test", "after add")
        transaction.commit()
    }
}