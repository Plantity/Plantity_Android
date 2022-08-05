package plantity.plantity_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        edit_info_tv.setOnClickListener{
            val intent = Intent(this, Setting2Activity::class.java)
            intent.putExtra("key","edit_info")
            startActivity(intent)
        }
        notice_tv.setOnClickListener{
            val intent = Intent(this, Setting2Activity::class.java)
            intent.putExtra("key","notice")
            startActivity(intent)
        }
        send_tv.setOnClickListener{
            val intent = Intent(this, Setting2Activity::class.java)
            intent.putExtra("key","send")
            startActivity(intent)
        }
        version_tv.setOnClickListener{
            val intent = Intent(this, Setting2Activity::class.java)
            intent.putExtra("key","version")
            startActivity(intent)
        }
        report_tv.setOnClickListener{
            val intent = Intent(this, Setting2Activity::class.java)
            intent.putExtra("key","report")
            startActivity(intent)
        }
        logout_tv.setOnClickListener{
            val intent = Intent(this, Setting2Activity::class.java)
            intent.putExtra("key","logout")
            startActivity(intent)
        }
        delete_tv.setOnClickListener{
            val intent = Intent(this, Setting2Activity::class.java)
            intent.putExtra("key","delete")
            startActivity(intent)
        }
    }
}