package plantity.plantity_android.guideline

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import plantity.plantity_android.NavBarFragment
import plantity.plantity_android.R
import plantity.plantity_android.databinding.ActivityGuideLineBinding
import plantity.plantity_android.search.SearchAdapter

class GuideLineActivity : AppCompatActivity() {
    //뷰 바인딩
    private var mBinding: ActivityGuideLineBinding? = null
    private val binding get() = mBinding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityGuideLineBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //val listdata = mutableListOf<GuideData>() -> 원래 코드에 있던 것
        val guideLineDataList = mutableListOf<GuideLineData>()
        guideLineDataList.add(GuideLineData("가이드라인 제목", "내용입니달라........................"))
        guideLineDataList.add(GuideLineData("가이드라인 제목2", "내용2입니달라......................"))
        guideLineDataList.add(GuideLineData("가이드라인 제목3", "내용3입니달라..........................."))

        binding.gdRecyclerView.layoutManager = LinearLayoutManager(this)
        //binding.gdRecyclerView.adapter=GuideLineListAdapter(listdata)
        binding.gdRecyclerView.addItemDecoration(DividerItemDecoration(this,LinearLayoutManager.VERTICAL))
        setNavBarFragment("guide")

        val guideLineAdatper = GuideLineAdapter(guideLineDataList)  // 어댑터
        binding.gdRecyclerView.adapter = guideLineAdatper
//        binding.searchListRecyclerView.layoutManager = LinearLayoutManager(guide)  // context로 this가 아님?
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