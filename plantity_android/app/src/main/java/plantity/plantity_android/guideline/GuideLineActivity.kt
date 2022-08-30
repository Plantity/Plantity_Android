package plantity.plantity_android.guideline

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import plantity.plantity_android.R
import plantity.plantity_android.databinding.ActivityGuideLineBinding

class GuideLineActivity : AppCompatActivity() {
    //뷰 바인딩
    private var mBinding: ActivityGuideLineBinding? = null
    private val binding get() = mBinding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityGuideLineBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
    }
}