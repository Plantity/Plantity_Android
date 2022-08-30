package plantity.plantity_android.search

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import plantity.plantity_android.NavBarFragment
import plantity.plantity_android.R
import plantity.plantity_android.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    val binding by lazy { ActivitySearchBinding.inflate(layoutInflater) }
    val todaysPlantFragment by lazy{ TodaysPlantFragment() }
    // 동적 데이터
    val datas = arrayOf(
        "android",
        "and",
        "apple",
        "몬스테라",
        "선인장"
    )

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
            val adapter = ArrayAdapter(this@SearchActivity, android.R.layout.simple_dropdown_item_1line, datas)
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
                searchInput.setText("")
            }

//            searchInput.setOnEditorActionListener { textView, action, keyEvent ->
//                var handled = false
//                if(keyEvent != null){
//                    Toast.makeText(this@SearchActivity, "검색 처리됨 : $searchInput.text", Toast.LENGTH_SHORT).show()
//                    searchInput.setText("")
//                    handled = true
//                }
//                handled
//            }

            searchInput.setOnKeyListener { _, action, keyEvent ->
                if((keyEvent.action == KeyEvent.ACTION_DOWN) && (action == KeyEvent.KEYCODE_ENTER)) {
                    Toast.makeText(this@SearchActivity, "검색 처리됨 : ${searchInput.text}", Toast.LENGTH_SHORT).show()
                    searchInput.setText("")
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
        transaction.add(R.id.fragmentContainerView, todaysPlantFragment)
//        Log.d("test", "after add")
        transaction.commit()

        val navBarFragment : NavBarFragment = NavBarFragment()
        val transaction2 = supportFragmentManager.beginTransaction()
        transaction2.add(R.id.nav_bar,navBarFragment)
        transaction2.commit()
    }

    private fun doSearch(query: String?){

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