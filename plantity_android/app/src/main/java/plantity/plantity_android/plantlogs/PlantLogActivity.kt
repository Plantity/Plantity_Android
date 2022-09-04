package plantity.plantity_android.plantlogs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import plantity.plantity_android.NavBarFragment
import plantity.plantity_android.R
import plantity.plantity_android.databinding.ActivityPlantLogBinding
import java.util.*
import plantity.plantity_android.databinding.ItemPlantCardBinding

class PlantLogActivity : AppCompatActivity() {
    val binding by lazy { ActivityPlantLogBinding.inflate(layoutInflater) }
    val calendarFragment by lazy { CalendarFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setFragment()
        setNavBarFragment("log")
        // adapter 생성
        val cardViewAdapter = CardViewAdapter()
        // 화면의 viewPager와 연결
        binding.cardViewPager.adapter = cardViewAdapter
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
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.add(R.id.calFragmentContainer, calendarFragment)
//        transaction.commit()

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

class CardViewAdapter(var items: ArrayList<String> = arrayListOf("몬스테라", "선인장")): RecyclerView.Adapter<CardViewAdapter.Holder>(){
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
        lateinit var currentItem:String

        fun setData(data:String){
            currentItem=data
            binding.plantName.text = data
        }
    }
}


