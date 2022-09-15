package plantity.plantity_android.guideline

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import plantity.plantity_android.NavBarFragment
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
        val listdata = mutableListOf<GuideData>()

        binding.gdRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.gdRecyclerView.adapter=GuideLineListAdapter(listdata)
        binding.gdRecyclerView.addItemDecoration(DividerItemDecoration(this,LinearLayoutManager.VERTICAL))
        setNavBarFragment("guide")
        
    }

    fun setNavBarFragment(title:String){
        val bundle = Bundle()
        bundle.putString("title", title)
        val navBarFragment : NavBarFragment = NavBarFragment()
        navBarFragment.arguments = bundle
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.nav_bar,navBarFragment)
        transaction.commit()
    }
}