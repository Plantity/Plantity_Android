package plantity.plantity_android.mypages

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_mypage.*
import plantity.plantity_android.NavBarFragment
import plantity.plantity_android.R
import plantity.plantity_android.main.AddPlantFragment
import plantity.plantity_android.settings.SettingActivity

class MypageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)

        setting_image.setOnClickListener{
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }
        setFragment()
        setNavBarFragment("mypage")
    }
    fun changeFragment(){
        val addplantFragment : AddPlantFragment = AddPlantFragment()
        val transaction5 = supportFragmentManager.beginTransaction()
        transaction5.replace(R.id.addplant, addplantFragment)
        transaction5.commit()
    }
    private fun setFragment(){
        val mypagecardFragment : MypageCardFragment = MypageCardFragment()
        val transaction1 = supportFragmentManager.beginTransaction()
        transaction1.add(R.id.user,mypagecardFragment)
        transaction1.commit()

        val myplantsFragment : MyplantsFragment = MyplantsFragment()
        val transaction2 = supportFragmentManager.beginTransaction()
        transaction2.add(R.id.myplants,myplantsFragment)
        transaction2.commit()

        val likeFragment : LikeFragment = LikeFragment()
        val transaction3 = supportFragmentManager.beginTransaction()
        transaction3.add(R.id.like,likeFragment)
        transaction3.commit()


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