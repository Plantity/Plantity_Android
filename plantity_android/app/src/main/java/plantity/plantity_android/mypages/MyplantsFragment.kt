package plantity.plantity_android.mypages

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_like.*
import plantity.plantity_android.PlantDetailActivity
import plantity.plantity_android.R
import plantity.plantity_android.plantlogs.PlantLogActivity


class MyplantsFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_myplants, container, false)
        val fir : ImageView = view.findViewById(R.id.fir)
        fir.setBackgroundResource(R.drawable.ic_plant_log_image)
        fir.setOnClickListener{
            val intent = Intent(activity, PlantLogActivity::class.java)
            startActivity(intent)
        }

        val imageUrl2 = "https://cdn.pixabay.com/photo/2017/07/27/11/14/flower-2544986_960_720.jpg"
        val sec : ImageView = view.findViewById(R.id.sec)
        Glide.with(this).load(imageUrl2).into(sec)
        sec.setOnClickListener{
            val intent = Intent(activity, PlantLogActivity::class.java)
            startActivity(intent)
        }
        val thi : ImageView = view.findViewById(R.id.thi)
        thi.setEnabled(false)
        return view

    }

}