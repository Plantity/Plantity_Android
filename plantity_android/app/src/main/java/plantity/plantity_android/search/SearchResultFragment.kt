package plantity.plantity_android.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.item_plant_list_recycler.*
import plantity.plantity_android.R
import plantity.plantity_android.databinding.FragmentSearchResultBinding
import plantity.plantity_android.databinding.ItemPlantListRecyclerBinding

class SearchResultFragment : Fragment() {
    lateinit var binding: FragmentSearchResultBinding
    lateinit var searchActivity: SearchActivity
    lateinit var searchAdapter: SearchAdapter

    var searchedList = mutableListOf<Content>()


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
        plantsList = bundle.get("allPlants") as MutableList<Content>

        searchAdapter = SearchAdapter(plantsList)  // 어댑터
        binding.searchListRecyclerView.adapter = searchAdapter
        binding.searchListRecyclerView.layoutManager = LinearLayoutManager(searchActivity)  // context로 this가 아님?

//        Toast.makeText(context, "in fragment, data is ${resultData.cntntsSj}", Toast.LENGTH_SHORT).show()

        searchActivity.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            // when query text is changed
            // return true if the action was handled by the listener
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            // when the user submits the query
            // return true if the action was handled by the listener
            override fun onQueryTextChange(newText: String?): Boolean {
                searchedList.clear()
                // 빈칸인 경우
                if(newText == ""){
                    searchAdapter.setPlantList(plantsList)
                }
                // 아닌 경우
                else{
                    for (a in 0 until plantsList.size) {
                        if(plantsList[a].cntntsSj.contains(newText as CharSequence)){
                            searchedList.add(plantsList[a])
                        }
                        searchAdapter.setPlantList(searchedList)
                    }
                }
                return true
            }
        })

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_search_result, container, false)
        return binding.root
    }
}