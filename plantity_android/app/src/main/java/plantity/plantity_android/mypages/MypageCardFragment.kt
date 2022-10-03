package plantity.plantity_android.mypages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_like.*
import plantity.plantity_android.R

class MypageCardFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_mypage_card, container, false)
        val username_tv : TextView = view.findViewById(R.id.username_tv)
        val level : TextView = view.findViewById(R.id.level)
        username_tv.setText("고해주")
        level.setText("가드너")
        return view

    }

}
