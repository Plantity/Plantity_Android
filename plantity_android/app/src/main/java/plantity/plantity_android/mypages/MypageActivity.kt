package plantity.plantity_android.mypages

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_mypage.*
import plantity.plantity_android.R
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
}