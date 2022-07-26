package plantity.plantity_android.guideline

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_guide_line.*
import plantity.plantity_android.NavBarFragment
import plantity.plantity_android.R
import plantity.plantity_android.databinding.ActivityGuideLineBinding
import kotlin.math.abs


private const val NUM_PAGES = 5
class GuideLineActivity : AppCompatActivity() {
    //뷰 바인딩
    lateinit var guidelinelistAdapter : GuideLineListAdapter

    private var mBinding: ActivityGuideLineBinding? = null
    private val binding get() = mBinding!!
    private lateinit var cardviewPager : ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityGuideLineBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setNavBarFragment("guide")
        cardviewPager = mBinding!!.pager


        val cardViewAdapter = cardPagerAdapter(this)

        with(cardviewPager){
            adapter = cardViewAdapter

            // 카드뷰 양쪽으로 미리 보기
            offscreenPageLimit = 3
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

            val transform = CompositePageTransformer()
            transform.addTransformer(MarginPageTransformer(5))

            transform.addTransformer { page, position ->
                val r = 1 - abs(position)
                page.scaleY = 0.85f + r * 0.15f
            }
            setPageTransformer(transform)
        }

        cardviewPager.adapter = cardViewAdapter
        pager.setOnClickListener{
            val intent = Intent(this, GuideLineDetailActivity::class.java)
            intent.putExtra("title", "실내에서 선인장 기르는 방법")
            startActivity(intent)
        }
        initRecycler()
        guideline_rv.addItemDecoration(VerticalItemDecorator(20))
        guideline_rv.addItemDecoration(HorizontalItemDecorator(10))



    }
    private fun initRecycler() {

        val list = ArrayList<GuidelineData>()
        list.add(GuidelineData(title = "왕초보 식집사를 위한 기본 지식", subtitle = "식물 키우기가 처음이라면"))
        list.add(GuidelineData(title = "실내식물 가지치기 하는 방법", subtitle = ""))
        list.add(GuidelineData(title = "과습된 식물 살리는 방법", subtitle = "걱정이 지나쳐 물을 자주 주게 돼요."))
        list.add(GuidelineData(title = "실내에서 선인장 기르는 방법", subtitle = ""))
        list.add(GuidelineData(title = "식물 건강하게 키우는 방법", subtitle = ""))
        list.add(GuidelineData(title = "실내식물 관리하는 방법", subtitle = ""))
        list.add(GuidelineData(title = "다육이 잎꽂이 하는 방법", subtitle = ""))
        list.add(GuidelineData(title = "분갈이 하는 방법", subtitle = ""))

        guidelinelistAdapter = GuideLineListAdapter(list)
        mBinding!!.guidelineRv.adapter = guidelinelistAdapter

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
    private inner class cardPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment = GuidelineNewsFragment()


    }

}
class HorizontalItemDecorator(private val divHeight : Int) : RecyclerView.ItemDecoration() { // 리사이클러뷰 간격 조절

    @Override
    override fun getItemOffsets(outRect: Rect, view: View, parent : RecyclerView, state : RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.left = divHeight
        outRect.right = divHeight
    }
}
class VerticalItemDecorator(private val divHeight : Int) : RecyclerView.ItemDecoration() {

    @Override
    override fun getItemOffsets(outRect: Rect, view: View, parent : RecyclerView, state : RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = divHeight
        outRect.bottom = divHeight
    }
}