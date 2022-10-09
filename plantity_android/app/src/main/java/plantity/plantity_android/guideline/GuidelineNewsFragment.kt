package plantity.plantity_android.guideline

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import plantity.plantity_android.R
import plantity.plantity_android.main.MainActivity

class GuidelineNewsFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_guideline_news, container, false)
        val card : CardView = view.findViewById(R.id.card)
        card.setOnClickListener{
            val intent = Intent(activity, GuideLineDetailActivity::class.java)
            intent.putExtra("title", "실내에서 선인장 기르는 방법")
            startActivity(intent)
        }
        return view
    }

}