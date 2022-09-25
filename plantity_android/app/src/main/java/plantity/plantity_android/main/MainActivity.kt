package plantity.plantity_android.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_main_card.*
import plantity.plantity_android.MainLogFragment
import plantity.plantity_android.NavBarFragment
import plantity.plantity_android.R
import plantity.plantity_android.databinding.ActivityMainBinding
import plantity.plantity_android.databinding.ItemMainCardBinding
import plantity.plantity_android.mypages.LikeFragment
import plantity.plantity_android.plantlogs.PlantLogActivity
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setNavBarFragment("main")

        // adapter 생성
        val cardViewAdapter = MainCardViewAdapter()
        // 화면의 viewPager와 연결
        binding.mainCardViewPager.adapter = cardViewAdapter

        Log.d("test", "cardview item count: ${cardViewAdapter.itemCount}")

//        var like = false
//        left.setOnClickListener{
//            if(like==true){
//                val imageUrl = "https://www.100ssd.co.kr/news/photo/202009/71614_51734_4048.jpg"
//                Glide.with(this).load(imageUrl).into(imageView2)
//                dday.setText("함께한지 14일째")
//                name.setText("새삼이")
//                like=false
//            }
//        }
//        right.setOnClickListener {
//            if(like==false){
//                val imageUrl =  "https://mule4.dingul.io/api/r?l=aHR0cHM6Ly90aHVtYm5haWw5LmNvdXBhbmdjZG4uY29tL3RodW1ibmFpbHMvcmVtb3RlLzQ5Mng0OTJleC9pbWFnZS92ZW5kb3JfaW52ZW50b3J5L2U1ZWMvNGI5YzQxODdjMjYyZGZiOGY2NzIyMmQzZDIzNWVhODU2YjA1NTViYWI2N2IwMTE4MDk5ZDlmMjI5OGFjLmpwZw"
//                Glide.with(this).load(imageUrl).into(imageView2)
//                dday.setText("함께한지 20일째")
//                name.setText("꺅둥이")
//                like=true
//            }
//        }
    }

    fun setNavBarFragment(title:String){
        val bundle = Bundle()
        bundle.putString("title", title)
        val navBarFragment : NavBarFragment = NavBarFragment()
        navBarFragment.arguments = bundle
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.nav_bar,navBarFragment)
        transaction.commit()

//        val mainlogFragment : MainLogFragment = MainLogFragment()
//        val transaction2 = supportFragmentManager.beginTransaction()
//        transaction2.add(R.id.mainlog,mainlogFragment)
//        transaction2.commit()
    }
}

// 더미 데이터로 식물 닉네임만 전달
class MainCardViewAdapter(var items: ArrayList<String> = arrayListOf("찌니꾸", "때따미")): RecyclerView.Adapter<MainCardViewAdapter.MainCardHolder>(){
    // 표시되는 뷰의 정보를 넘겨주기
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainCardHolder {
        // 표시되는 view의 정보를 넘겨줘야 해서 3개의 인자를 전달
        val binding = ItemMainCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainCardHolder(binding)
    }
    // 한 화면에 보이는 개수만큼 호출
    override fun onBindViewHolder(holder: MainCardHolder, position: Int) {
        // 사용할 데이터 꺼내기
        val item = items[position]
        // holder에 데이터 전달
        holder.setData(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class MainCardHolder(val binding: ItemMainCardBinding): RecyclerView.ViewHolder(binding.root){
        lateinit var currentItem:String

        init{
            binding.moveToLogBtn.setOnClickListener {

                val intent = Intent(binding.moveToLogBtn.context, PlantLogActivity::class.java)
                // 인텐트로 현재 식물이 무엇인지 정보 넘겨야 함 -> putExtra()
                startActivity(binding.moveToLogBtn.context, intent, null)
                // finish() 해야 하나?
            }
        }

        fun setData(data: String){
            currentItem = data
            binding.plantName.text = data
        }
    }
}