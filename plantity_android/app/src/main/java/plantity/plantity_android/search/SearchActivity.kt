package plantity.plantity_android.search

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import plantity.plantity_android.NavBarFragment
import plantity.plantity_android.R
import plantity.plantity_android.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    val binding by lazy { ActivitySearchBinding.inflate(layoutInflater) }
    val todaysPlantFragment by lazy{ TodaysPlantFragment() }
    val searchRepository = SearchRepository()

    var searchQuery:String? = null
    var page: Int = 0
    var allPlantsList = mutableListOf<Content>()
    var searchedList = mutableListOf<Content>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setInitFragment()
        setNavBarFragment("search")

        setPlantsList()

        // 사용자가 검색 대화상자 또는 위젯에서 검색을 실행하면 검색 가능 활동이 시작되고 이 활동에 ACTION_SEARCH 인텐트가 전송됨
        // 이 인텐트는 QUERY 문자열 extra에 검색어를 포함함 -> 활동이 시작될 때 이 인텐트를 확인하고 문자열을 추출
        // Verify the action and get the query(검색어 가져오기)
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                // doMySearch(query)
            }
        }

        with(binding){
            // 식물명으로만 이루어진 list 전달 -> cancel
            //val adapter = ArrayAdapter(this@SearchActivity, android.R.layout.simple_dropdown_item_1line, dummyData.map{it.cntntsSj})
            //searchInput.setAdapter(adapter)

            // 검색창 클릭됐을 때
            searchView.setOnSearchClickListener {
                searchView.setBackgroundResource(R.drawable.searchbar_frame_clicked)
                setResultFragment()
            }

            // 고쳐야 됨
            searchView.setOnFocusChangeListener { _, hasFocus ->
                if(hasFocus){
                    searchView.setBackgroundResource(R.drawable.searchbar_frame_clicked)
                    setResultFragment()
                }
                else{
                    searchView.setBackgroundResource(R.drawable.searchbar_frame)
                    setTodaysPlantFragment()
                    hideKeyboard()
                }
            }

//            searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
//                // when query text is changed
//                // return true if the action was handled by the listener
//                override fun onQueryTextSubmit(query: String?): Boolean {
//                    TODO("Not yet implemented")
//                }
//
//                // when the user submits the query
//                // return true if the action was handled by the listener
//                override fun onQueryTextChange(newText: String?): Boolean {
//                    searchedList.clear()
//                    if(newText == ""){
//                        adapter.
//                    }
//                }
//            })

            // 검색창 닫을 때
//            searchView.setOnCloseListener {
//                searchView.setBackgroundResource(R.drawable.searchbar_frame)
//                setTodaysPlantFragment()
//                hideKeyboard()
//                searchView.clearFocus()
//                true
//            }
            // AutoCompleteTextView에 나열된 항목을 클릭했을 경우 바로 적용 되도록
//            searchInput.setOnItemClickListener { parent, view, position, id ->
//                Toast.makeText(this@SearchActivity, "검색 처리됨 : ${searchInput.text}", Toast.LENGTH_SHORT).show()
//                // 목록에서 조회 -> 결과 보여주기
//                doSearch(searchInput.text.toString())
//                //searchRepository.getSearchPlants(page, this@SearchActivity)
//            }
            
            // 엔터키 눌렀을 때 검색 처리
//            searchInput.setOnKeyListener { _, action, keyEvent ->
//                if((keyEvent.action == KeyEvent.ACTION_DOWN) && (action == KeyEvent.KEYCODE_ENTER)) {
//                    Toast.makeText(this@SearchActivity, "검색 처리됨 : ${searchInput.text}", Toast.LENGTH_SHORT).show()
//                    // 목록에서 조회 -> 결과 보여주기
//                    //doSearch(searchInput.text.toString())
//                    searchQuery = searchInput.text.toString()
//                    Log.d("test", "searchQuery: $searchQuery")
//                    searchRepository.getSearchPlants(page, this@SearchActivity)
//                    true
//                }
//                else
//                    false
//            }
        }
    }

    fun setTodaysPlantFragment(){
        val bundle = Bundle()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.searchFragmentContainer, todaysPlantFragment)
        transaction.commit()
    }

    fun setInitFragment(){
        val bundle = Bundle()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.searchFragmentContainer, todaysPlantFragment)
        transaction.commit()

        val navBarFragment : NavBarFragment = NavBarFragment()
        val transaction2 = supportFragmentManager.beginTransaction()
        transaction2.add(R.id.nav_bar,navBarFragment)
        transaction2.commit()
    }

    // autocompletetextview 자동 완성 위해서 식물 이름으로만 이루어진 배열 만들기 -> (cancel)
    // 식물 정보로 이루어진 배열 만들기
    fun setPlantsList(){
        searchRepository.getSearchPlants(page, this)
    }

    // SearchRepository에서 get 끝나면 호출하는 함수
    fun loadComplete(result: SearchResult) {
        Log.d("test", "inside loadComplete, page: $page")
        allPlantsList.addAll(result.content)
        //Log.d("test", "plantsList[0]: ${plantsList[0].cntntsSj}")
        // 방금 불러온 페이지가 마지막이 아닌 경우
        if(!result.last){
            page++
            searchRepository.getSearchPlants(page, this)
        }
        // 방금 불러온 페이지가 마지막인 경우
        else{
            page = 0

        }
    }

    private fun setResultFragment(){
        val bundle = bundleOf("allPlants" to allPlantsList)

        val searchResultFragment = SearchResultFragment()

        val transaction = supportFragmentManager.beginTransaction()
        searchResultFragment.arguments = bundle
        transaction.replace(R.id.searchFragmentContainer, searchResultFragment)
        transaction.commit()
    }

    // 식물 목록에서 조회하고 결과 보여주는 함수를 호출하는 함수
    private fun doSearch(query: String?){
        //val resultData = dummyData.find{it.cntntsSj == query}
        //val resultData =
        //searchRepository.getSearchPlants(page, this)
        /*if(resultData == null)  // 찾은 데이터가 없을 때
            Toast.makeText(this@SearchActivity, "찾을 수 없음: $query", Toast.LENGTH_SHORT).show()
        else {  // 원하는 결과 찾았을 때
            Log.d("test", "${resultData?.plantId},${resultData?.plntzrNm}")
            // 키보드 내리기
            this@SearchActivity.hideKeyboard()
            // 검색 결과 보여주기
            setResultFragment(resultData)
        }*/

    }

    fun setNavBarFragment(title:String){
        val bundle = Bundle()
        bundle.putString("title", title)
        val navBarFragment : NavBarFragment = NavBarFragment()
        navBarFragment.arguments = bundle
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.nav_bar,navBarFragment)
        transaction.commit()
    }
}