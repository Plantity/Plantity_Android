package plantity.plantity_android.plantlogs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import plantity.plantity_android.NavBarFragment
import plantity.plantity_android.R
import plantity.plantity_android.databinding.ActivityPlantLogBinding
import plantity.plantity_android.databinding.ItemPlantCardBinding
import plantity.plantity_android.search.SearchResultFragment
import java.util.*

class PlantLogActivity : AppCompatActivity() {
    val binding by lazy { ActivityPlantLogBinding.inflate(layoutInflater) }
    val calendarFragment by lazy { CalendarFragment() }

    // 식물 2개 만들어서 확인하기
    private var plant1Log = arrayListOf(
        MyPlantLogData("2022-09-07",true, true, true, true),
        MyPlantLogData("2022-09-10", false, false, true, true),
        MyPlantLogData("2022-09-14", true, false, true, false)
    )
    private var plant1 = MyPlantData("꺅둥이", "몬스테라", "2022-08-14", plant1Log)

    private var plant2Log = arrayListOf(
        MyPlantLogData("2022-09-09",false, false, true, false),
        MyPlantLogData("2022-09-11", true, false, true, true),
        MyPlantLogData("2022-09-15", true, false, false, false)
    )
    private var plant2 = MyPlantData("새삼이", "다육선인장", "2022-09-01", plant2Log)
    private var dummy = arrayListOf(
        plant1,
        plant2
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        /* 데이터 먼저 가져오기!! */

        setFragment()  // -> 처음엔 bundle로 보내놓고 calendar fragment 내부에 변수로 저장해두기
        setNavBarFragment("log")
        // adapter 생성
        val cardViewAdapter = CardViewAdapter(dummy)
        // 화면의 viewPager와 연결
        binding.cardViewPager.adapter = cardViewAdapter

        binding.cardViewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            // 처음 실행했을 때도 실행됨..
            override fun onPageSelected(position: Int){
                // position은 0부터 시작
                Log.d("test", "position changed to $position")
                calendarFragment.handleCardSwipe(position)
            }
        })
        TabLayoutMediator(binding.tabLayout, binding.cardViewPager){ tab, position ->
            // 필요한 구현?
        }.attach()

//        binding.cardViewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
//            override fun onPageSelected(position: Int) {
//                super.onPageSelected(position)
//                binding.cardViewIndicator.selectDot(position)
//            }
//        })
        //init indicator
        Log.d("test", "cardview item count: ${cardViewAdapter.itemCount}")
//        binding.cardViewIndicator.createDotPanel(cardViewAdapter.itemCount, R.drawable.shape_circle_gray, R.drawable.shape_circle_green, 0)
        // layout manager 설정?
    }

    private fun setFragment(){
        val bundle = bundleOf("myPlantDatas" to dummy)
        val transaction = supportFragmentManager.beginTransaction()
        calendarFragment.arguments = bundle
        transaction.add(R.id.calFragmentContainer, calendarFragment)
        transaction.commit()

        val navBarFragment : NavBarFragment = NavBarFragment()
        val transaction2 = supportFragmentManager.beginTransaction()
        transaction2.add(R.id.nav_bar,navBarFragment)
        transaction2.commit()
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

class CardViewAdapter(var items: ArrayList<MyPlantData>): RecyclerView.Adapter<CardViewAdapter.Holder>(){
    // 표시되는 뷰의 정보를 넘겨주기
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        // 표시되는 view의 정보를 넘겨줘야 해서 3개의 인자를 전달
        val binding = ItemPlantCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    // 한 화면에 보이는 개수만큼 호출
    override fun onBindViewHolder(holder: Holder, position: Int) {
        // 사용할 데이터 꺼내기
        val item = items[position]
        // holder에 데이터 전달
        holder.setData(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class Holder(val binding: ItemPlantCardBinding): RecyclerView.ViewHolder(binding.root){
        lateinit var currentItem: MyPlantData

        fun setData(data: MyPlantData){
            currentItem = data
            binding.plantName.text = data.name
            binding.plantType.text = data.plantType
            binding.sinceText.text = "Since " + data.since
        }
    }
}

