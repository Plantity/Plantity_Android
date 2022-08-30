package plantity.plantity_android.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import plantity.plantity_android.R

class Setting2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting2)
        var click = intent.getStringExtra("key")

        if (click != null) {
            setFragment(click)
        }
    }

    private fun setFragment(click:String){
        if (click == "edit_info_tv") {
            val editinfoFragment : EditInfoFragment = EditInfoFragment()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.framelayout,editinfoFragment)
            transaction.commit()
        }
        if (click == "notice") {
            val noticeFragment : NoticeFragment = NoticeFragment()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.framelayout,noticeFragment)
            transaction.commit()
        }
        if (click == "send") {
            val sendFragment : SendFragment = SendFragment()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.framelayout,sendFragment)
            transaction.commit()
        }
        if (click == "version") {
            val versionFragment : VersionFragment = VersionFragment()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.framelayout,versionFragment)
            transaction.commit()
        }
        if (click == "report") {
            val reportFragment : ReportFragment = ReportFragment()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.framelayout,reportFragment)
            transaction.commit()
        }
        if (click == "logout") {
            val logoutFragment : LogoutFragment = LogoutFragment()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.framelayout,logoutFragment)
            transaction.commit()
        }
        if (click == "delete") {
            val deleteFragment : DeleteFragment = DeleteFragment()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.framelayout,deleteFragment)
            transaction.commit()
        }
    }
}