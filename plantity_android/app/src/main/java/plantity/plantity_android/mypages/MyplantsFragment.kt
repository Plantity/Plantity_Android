package plantity.plantity_android.mypages

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_like.*
import plantity.plantity_android.PlantDetailActivity
import plantity.plantity_android.R
import plantity.plantity_android.main.AddPlantActivity
import plantity.plantity_android.main.AddPlantDialog
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
        val addbutton :ImageButton = view.findViewById(R.id.addbutton)
        addbutton.setOnClickListener {
            val intent = Intent(activity, AddPlantActivity::class.java)
            startActivity(intent)
        }
        val fir : ImageView = view.findViewById(R.id.fir)
        val imageUrl1 = "https://www.100ssd.co.kr/news/photo/202009/71614_51734_4048.jpg"
        Glide.with(this).load(imageUrl1).into(fir)
        fir.setOnClickListener{
            val intent = Intent(activity, PlantLogActivity::class.java)
            startActivity(intent)
        }

        val imageUrl2 =  "https://mule4.dingul.io/api/r?l=aHR0cHM6Ly90aHVtYm5haWw5LmNvdXBhbmdjZG4uY29tL3RodW1ibmFpbHMvcmVtb3RlLzQ5Mng0OTJleC9pbWFnZS92ZW5kb3JfaW52ZW50b3J5L2U1ZWMvNGI5YzQxODdjMjYyZGZiOGY2NzIyMmQzZDIzNWVhODU2YjA1NTViYWI2N2IwMTE4MDk5ZDlmMjI5OGFjLmpwZw"
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