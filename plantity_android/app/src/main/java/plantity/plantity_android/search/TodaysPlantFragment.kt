package plantity.plantity_android.search

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import plantity.plantity_android.R
import plantity.plantity_android.databinding.FragmentTodaysPlantBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class TodaysPlantFragment : Fragment() {
    lateinit var binding: FragmentTodaysPlantBinding
    lateinit var searchActivity: SearchActivity

    var isLiked: Boolean = false

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(context is SearchActivity)
            searchActivity = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodaysPlantBinding.inflate(inflater, container, false)

        binding.heartIcon.setOnClickListener {
            // 좋아요 취소
            if(isLiked){
                binding.heartIcon.setImageResource(R.drawable.ic_heart)
                isLiked = false
            }
            // 좋아요
            else {
                binding.heartIcon.setImageResource(R.drawable.ic_heart_full)
                isLiked = true
            }
        }
        return binding.root
    }
}