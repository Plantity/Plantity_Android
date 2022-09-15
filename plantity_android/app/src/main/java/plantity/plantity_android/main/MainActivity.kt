package plantity.plantity_android.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
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