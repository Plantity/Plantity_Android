package plantity.plantity_android.search

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.Dimension
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
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

                // 취소 버튼 중복 생성 방지
                if(binding.cancelLayout.childCount == 0)
                    setCancelButton()
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

    fun setCancelButton(){
        val cancelText = TextView(applicationContext)
        cancelText.text = "취소"
        cancelText.setTextColor(Color.BLACK)
        //cancelText.textSize = resources.getDimension((R.dimen.dp_15))
        //cancelText.setTextSize(Dimension.DP, 15F)
        cancelText.setTextSize(Dimension.DP, resources.getDimension(R.dimen.dp_15))

        // 취소 버튼 클릭 되면
        cancelText.setOnClickListener{
            setTodaysPlantFragment()
            hideKeyboard()
            binding.searchView.clearFocus()
            binding.searchView.onActionViewCollapsed()
            binding.cancelLayout.removeView(cancelText)
            // 검색창 배경
            binding.searchView.setBackgroundResource(R.drawable.searchbar_frame)
            
            // 검색창 크기 원래대로 되돌리기
            var searchViewParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT, resources.getDimension((R.dimen.dp_42)).toInt()
            )

            searchViewParams.marginEnd = resources.getDimension((R.dimen.dp_20)).toInt()
            searchViewParams.marginStart = resources.getDimension((R.dimen.dp_20)).toInt()
            // searchViewParams.topMargin = resources.getDimension((R.dimen.dp_10)).toInt() -> 안 먹혀서 그냥 constraint layout에 padding 적용함..

            binding.searchView.layoutParams = searchViewParams
        }

        // 검색창 크기 줄이기
        var searchViewAfterParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT, resources.getDimension((R.dimen.dp_42)).toInt()
        )
        searchViewAfterParams.marginEnd = resources.getDimension((R.dimen.dp_60)).toInt()
        searchViewAfterParams.marginStart = resources.getDimension((R.dimen.dp_20)).toInt()
        searchViewAfterParams.topMargin = resources.getDimension((R.dimen.dp_10)).toInt()
        binding.searchView.layoutParams = searchViewAfterParams

        // 취소 버튼 layout
        var marginLeft = resources.getDimension((R.dimen.dp_10))
        var marginRight = resources.getDimension((R.dimen.dp_20))

        var param = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        param.marginStart = marginLeft.toInt()
        param.marginEnd = marginRight.toInt()

        cancelText.layoutParams = param

        // 취소 버튼 추가
        binding.cancelLayout.addView(cancelText)
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