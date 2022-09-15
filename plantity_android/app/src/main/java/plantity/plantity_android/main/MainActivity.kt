package plantity.plantity_android.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import plantity.plantity_android.MainLogFragment
import plantity.plantity_android.NavBarFragment
import plantity.plantity_android.R
import plantity.plantity_android.databinding.ActivityMainBinding
import plantity.plantity_android.databinding.ItemPlantCardBinding
import plantity.plantity_android.mypages.LikeFragment
import plantity.plantity_android.plantlogs.CardViewAdapter
import plantity.plantity_android.search.SearchActivity
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setNavBarFragment("main")
        var like = false
        left.setOnClickListener{
            if(like==true){
                val imageUrl = "https://www.100ssd.co.kr/news/photo/202009/71614_51734_4048.jpg"
                Glide.with(this).load(imageUrl).into(imageView2)
                dday.setText("함께한지 14일째")
                name.setText("새삼이")
                like=false
            }
        }
        right.setOnClickListener {
            if(like==false){
                val imageUrl =  "https://mule4.dingul.io/api/r?l=aHR0cHM6Ly90aHVtYm5haWw5LmNvdXBhbmdjZG4uY29tL3RodW1ibmFpbHMvcmVtb3RlLzQ5Mng0OTJleC9pbWFnZS92ZW5kb3JfaW52ZW50b3J5L2U1ZWMvNGI5YzQxODdjMjYyZGZiOGY2NzIyMmQzZDIzNWVhODU2YjA1NTViYWI2N2IwMTE4MDk5ZDlmMjI5OGFjLmpwZw"
                Glide.with(this).load(imageUrl).into(imageView2)
                dday.setText("함께한지 20일째")
                name.setText("꺅둥이")
                like=true
            }
        }
    }

    fun setNavBarFragment(title:String){
        val bundle = Bundle()
        bundle.putString("title", title)
        val navBarFragment : NavBarFragment = NavBarFragment()
        navBarFragment.arguments = bundle
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.nav_bar,navBarFragment)
        transaction.commit()

        val mainlogFragment : MainLogFragment = MainLogFragment()
        val transaction2 = supportFragmentManager.beginTransaction()
        transaction2.add(R.id.mainlog,mainlogFragment)
        transaction2.commit()
    }


}