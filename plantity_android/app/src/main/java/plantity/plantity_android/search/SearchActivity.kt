package plantity.plantity_android.search

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import plantity.plantity_android.NavBarFragment
import plantity.plantity_android.R
import plantity.plantity_android.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    val binding by lazy { ActivitySearchBinding.inflate(layoutInflater) }
    val todaysPlantFragment by lazy{ TodaysPlantFragment() }
    // dummy data
    val datas = arrayOf(
        "android",
        "and",
        "apple",
        "몬스테라",
        "선인장"
    )
    val data1 = Content(
    1,
    "10",
    "몬스테라",
    "Monstera deliciosa Liebm",
    "Monstera",
    "식물 설명",
    "멕시코",
    "광도",
    "9~10월",
    "흰색",
    "물 주기",
    "관리 수준")
    val data2 = Content(
    2,
    "11",
    "선인장",
    "Monstera deliciosa Liebm",
    "Cactus",
    "식물 설명",
    "멕시코2",
    "광도2",
    "7~8월",
    "노란색",
    "물 주기2",
    "관리 수준2")

    val dummyData = mutableListOf(data1, data2)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setFragment()
        setNavBarFragment("search")

        // 사용자가 검색 대화상자 또는 위젯에서 검색을 실행하면 검색 가능 활동이 시작되고 이 활동에 ACTION_SEARCH 인텐트가 전송됨
        // 이 인텐트는 QUERY 문자열 extra에 검색어를 포함함 -> 활동이 시작될 때 이 인텐트를 확인하고 문자열을 추출
        // Verify the action and get the query(검색어 가져오기)
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                // doMySearch(query)
            }
        }

        with(binding){
            /* autocompletetextview 사용했을 때*/
            // 식물명으로만 이루어진 list 전달
            val adapter = ArrayAdapter(this@SearchActivity, android.R.layout.simple_dropdown_item_1line, dummyData.map{it.cntntsSj})
            searchInput.setAdapter(adapter)

            searchInput.setOnFocusChangeListener { _, focused ->
                if(focused)
                    searchView.setImageResource(R.drawable.searchbar_frame_clicked)
                else
                    searchView.setImageResource(R.drawable.searchbar_frame)
            }
            // AutoCompleteTextView에 나열된 항목을 클릭했을 경우 바로 적용 되도록
            searchInput.setOnItemClickListener { parent, view, position, id ->
                Toast.makeText(this@SearchActivity, "검색 처리됨 : ${searchInput.text}", Toast.LENGTH_SHORT).show()
                // 목록에서 조회 -> 결과 보여주기
                doSearch(searchInput.text.toString())
                //searchInput.setText("")
            }
            
            // 엔터키 눌렀을 때 검색 처리
            searchInput.setOnKeyListener { _, action, keyEvent ->
                if((keyEvent.action == KeyEvent.ACTION_DOWN) && (action == KeyEvent.KEYCODE_ENTER)) {
                    Toast.makeText(this@SearchActivity, "검색 처리됨 : ${searchInput.text}", Toast.LENGTH_SHORT).show()
                    // 목록에서 조회 -> 결과 보여주기
                    doSearch(searchInput.text.toString())
                    true
                }
                else
                    false
            }
        }
    }

    private fun setFragment(){
        val bundle = Bundle()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.searchFragmentContainer, todaysPlantFragment)
        transaction.commit()

        val navBarFragment : NavBarFragment = NavBarFragment()
        val transaction2 = supportFragmentManager.beginTransaction()
        transaction2.add(R.id.nav_bar,navBarFragment)
        transaction2.commit()
    }

    private fun setResultFragment(resultData: Content?){
//        val bundle = Bundle()
//        bundle.putString("plantName", resultData)
        val bundle = bundleOf("searchResult" to resultData)

        val searchResultFragment = SearchResultFragment()

        val transaction = supportFragmentManager.beginTransaction()
        searchResultFragment.arguments = bundle
        transaction.replace(R.id.searchFragmentContainer, searchResultFragment)
        transaction.commit()
    }

    // 식물 목록에서 조회하고 결과 보여주는 함수를 호출하는 함수
    private fun doSearch(query: String?){
        val resultData = dummyData.find{it.cntntsSj == query}
        if(resultData == null)  // 찾은 데이터가 없을 때
            Toast.makeText(this@SearchActivity, "찾을 수 없음: $query", Toast.LENGTH_SHORT).show()
        else {  // 원하는 결과 찾았을 때
            Log.d("test", "${resultData?.plantId},${resultData?.plntzrNm}")
            // 키보드 내리기
            this@SearchActivity.hideKeyboard()
            // 검색 결과 보여주기
            setResultFragment(resultData)
        }
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