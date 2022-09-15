package plantity.plantity_android.mypages

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import kotlinx.android.synthetic.main.fragment_like.*
import plantity.plantity_android.PlantDetailActivity
import plantity.plantity_android.R

class LikeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_like, container, false)
        val fir : ImageButton = view.findViewById(R.id.fir)

        fir.setOnClickListener {
            val intent = Intent(activity, PlantDetailActivity::class.java)
            startActivity(intent)
        }
        return view
    }
}