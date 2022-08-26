package plantity.plantity_android

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import kotlinx.android.synthetic.main.fragment_nav_bar.*
import plantity.plantity_android.guideline.GuideLineActivity
import plantity.plantity_android.main.MainActivity
import plantity.plantity_android.mypages.MypageActivity
import plantity.plantity_android.plantlogs.PlantLogActivity
import plantity.plantity_android.search.SearchActivity

class NavBarFragment : Fragment() {
    private var title : String? = "title"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_nav_bar, container, false)
        val nav_guide : Button = view.findViewById(R.id.nav_guide)
        val nav_search : Button = view.findViewById(R.id.nav_search)
        val nav_main : Button = view.findViewById(R.id.nav_main)
        val nav_log : Button = view.findViewById(R.id.nav_log)
        val nav_mypage : Button = view.findViewById(R.id.nav_mypage)
        arguments?.let{
            title=it.getString("title")
        }
        if(title == "guide"){

            nav_guide.setEnabled(false)
            nav_guide.setBackgroundResource(R.drawable.guide_icon_color)
        }
        if(title == "search"){
            nav_search.setEnabled(false)
            nav_search.setBackgroundResource(R.drawable.search_icon_color)
        }
        if(title == "main"){
            nav_main.setEnabled(false)
            nav_main.setBackgroundResource(R.drawable.main_icon_color)
        }
        if(title == "log"){
            nav_log.setEnabled(false)
            nav_log.setBackgroundResource(R.drawable.log_icon_color)
        }
        if(title == "mypage"){
            nav_mypage.setEnabled(false)
            nav_mypage.setBackgroundResource(R.drawable.mypage_icon_color)
        }
        nav_guide.setOnClickListener{
            val intent = Intent(activity, GuideLineActivity::class.java)
            intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent)
        }
        nav_search.setOnClickListener{
            val intent = Intent(activity, SearchActivity::class.java)
            intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent)
        }

        nav_main.setOnClickListener{
            val intent = Intent(activity, MainActivity::class.java)
            intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent)
        }

        nav_log.setOnClickListener{
            val intent = Intent(activity, PlantLogActivity::class.java)
            intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent)
        }

        nav_mypage.setOnClickListener{
            val intent = Intent(activity, MypageActivity::class.java)
            intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent)
        }
        return view
    }
}