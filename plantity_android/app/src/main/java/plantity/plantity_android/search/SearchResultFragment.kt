package plantity.plantity_android.search

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import plantity.plantity_android.R
import plantity.plantity_android.databinding.FragmentSearchResultBinding
import plantity.plantity_android.databinding.FragmentTodaysPlantBinding

class SearchResultFragment : Fragment() {
    lateinit var binding: FragmentSearchResultBinding
    lateinit var searchActivity: SearchActivity
    lateinit var searchAdapter: SearchAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(context is SearchActivity)
            searchActivity = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchResultBinding.inflate(inflater, container, false)

        val plantsList: MutableList<Content>
        val bundle = Bundle(arguments)  //번들 받기
        plantsList = bundle.get("searchResult") as MutableList<Content>

        searchAdapter = SearchAdapter(plantsList)  // 어댑터
        binding.searchListRecyclerView.adapter = searchAdapter
        binding.searchListRecyclerView.layoutManager = LinearLayoutManager(searchActivity)  // context로 this가 아님?

//        Toast.makeText(context, "in fragment, data is ${resultData.cntntsSj}", Toast.LENGTH_SHORT).show()

        with(binding){

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