package plantity.plantity_android.search

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import plantity.plantity_android.R
import plantity.plantity_android.databinding.FragmentSearchResultBinding
import plantity.plantity_android.databinding.FragmentTodaysPlantBinding

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchResultFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchResultFragment : Fragment() {
    lateinit var binding: FragmentSearchResultBinding
    lateinit var searchActivity: SearchActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(context is SearchActivity)
            searchActivity = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchResultBinding.inflate(inflater, container, false)

        val resultData: Content
        val bundle = Bundle(arguments)  //번들 받기
        resultData = bundle.get("searchResult") as Content

        //var result = arguments?.getBundle("searchResult")
        Toast.makeText(context, "in fragment, data is ${resultData.cntntsSj}", Toast.LENGTH_SHORT).show()

        with(binding){
            plantType.text = resultData.cntntsSj
            plantDescription.text = resultData.adviseInfo
//            heartIcon.setOnClickListener {
//                // 좋아요 취소
//                if(isLiked){
//                    binding.heartIcon.setImageResource(R.drawable.ic_heart)
//                    isLiked = false
//                }
//                // 좋아요
//                else {
//                    binding.heartIcon.setImageResource(R.drawable.ic_heart_full)
//                    isLiked = true
//                }
//
        }

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_search_result, container, false)
        return binding.root
    }


}