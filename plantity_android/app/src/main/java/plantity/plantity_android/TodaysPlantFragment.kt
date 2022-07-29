package plantity.plantity_android

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import plantity.plantity_android.databinding.FragmentTodaysPlantBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class TodaysPlantFragment : Fragment() {
    lateinit var binding: FragmentTodaysPlantBinding
    lateinit var searchActivity: SearchActivity

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
        return binding.root
    }
}