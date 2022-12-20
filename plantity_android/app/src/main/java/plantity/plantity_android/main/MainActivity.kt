package plantity.plantity_android.main

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.item_main_card.view.*
import plantity.plantity_android.*
import plantity.plantity_android.databinding.ActivityMainBinding
import plantity.plantity_android.databinding.ItemMainAddCardBinding
import plantity.plantity_android.databinding.ItemMainCardBinding
import plantity.plantity_android.plantlogs.MyPlantInfo
import plantity.plantity_android.search.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import plantity.plantity_android.plantlogs.MyPlantsRepository
import plantity.plantity_android.plantlogs.PlantLogActivity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    val userId: Int = 1  // 임시 유저 아이디
    val myPlantListRepository = MyPlantsRepository()
    var myPlantList = mutableListOf<MainPlantData>()  // 내 식물 아이디 컬렉션

    lateinit var mainCardViewAdapter: MainCardViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        myPlantListRepository.getMyPlantList(userId, ::setPlantList)
        setNavBarFragment("main")

    }

    fun setNavBarFragment(title:String){
        val bundle = Bundle()
        bundle.putString("title", title)
        val navBarFragment : NavBarFragment = NavBarFragment()
        navBarFragment.arguments = bundle
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.nav_bar,navBarFragment)
        transaction.commit()

//        val mainlogFragment : MainLogFragment = MainLogFragment()
//        val transaction2 = supportFragmentManager.beginTransaction()
//        transaction2.add(R.id.mainlog,mainlogFragment)
//        transaction2.commit()
    }

    private fun setPlantList(list: List<MyPlantInfo>){
        myPlantList.addAll(list.map{ MainPlantData(it, USER_PLANT) })
        myPlantList.add(MainPlantData(MyPlantInfo(-1, "식물추가", "식물추가", "식물추가", "사진"), ADD_PLANT))
        Log.d("test", "MAIN PAGE, original plant list size: ${list.size}")
        Log.d("test", "MAIN PAGE, myPlantList size: ${myPlantList.size}")
        setCardViewAdapter()

    }

    private fun setCardViewAdapter(){
        // adapter 생성
        mainCardViewAdapter = MainCardViewAdapter(myPlantList, userId)

        // 유저 정보 서버 통신
        val retrofit = Retrofit.Builder()
            .baseUrl("http://plantity.shop/")
            .addConverterFactory(GsonConverterFactory.create())  // 데이터를 파싱하는 converter(JSON을 코틀린에서 바로 사용 가능한 데이터 형식으로 변환)
            .build()


        val call = RetrofitClient.userService.getUser(userId)
        call.enqueue(object: Callback<User> {
            override fun onResponse(  // 통신에 성공한 경우
                call: Call<User>,
                response: Response<User>
            ) {
                if(response.body()!!.isSuccess){  // 응답 잘 받은 경우
                    Log.d("test", "통신 성공 여부: ${response.body()!!.isSuccess}")
                    Log.d("test", "통신 성공 code: ${response.body()!!.code}")
                    Log.d("test", "통신 성공 msg: ${response.body()!!.message}")
                    Log.d("test", "통신 성공 body: ${response.body()!!.result}")

                }
                else{
                    Log.d("test", "통신 성공 but 문제, code: ${response.body()!!.code}")
                    Log.d("test", "통신 성공 but 응답 문제: ${response.body()!!.message}")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(applicationContext, "통신 실패", Toast.LENGTH_SHORT).show()
                Log.d("test", "서버 통신 실패, code: ${t.message}")
            }
        })

        // 화면의 viewPager와 연결
        //binding.mainCardViewPager.adapter = cardViewAdapter
        with(binding.mainCardViewPager){
            adapter = mainCardViewAdapter
            offscreenPageLimit = 3
            getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER

            val transform = CompositePageTransformer()
            transform.addTransformer(MarginPageTransformer(40))

            transform.addTransformer { page, position ->
                val r = 1 - abs(position)
                page.scaleY = 0.85f + r * 0.15f
            }

            setPageTransformer(transform)
        }

        // 카드 뷰 인디케이터 추가
//        val dotsIndicator = binding.dotsIndicator
//        dotsIndicator.attachTo(binding.mainCardViewPager)
        TabLayoutMediator(binding.mainTabLayout, binding.mainCardViewPager){ tab, position ->
            // 필요한 구현?
        }.attach()

        Log.d("test", "MAIN PAGE, cardview item count: ${mainCardViewAdapter.itemCount}")
    }


}


// 더미 데이터로 식물 닉네임만 전달
class MainCardViewAdapter(val items: List<MainPlantData>, val userId: Int): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val assignmentRepository = PutAssignmentRepository()

    // 표시되는 뷰의 정보를 넘겨주기
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // 표시되는 view의 정보를 넘겨줘야 해서 3개의 인자를 전달
        return when (viewType) {
            USER_PLANT -> {
//                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_main_card, parent, false)
//                MainCardHolder(view)
                MainCardHolder(
                    ItemMainCardBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
//                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_main_add_card, parent, false)
//                MainCardHolder(view)
                AddCardHolder(
                    ItemMainAddCardBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
//        val binding = ItemMainCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return MainCardHolder(binding)
    }
    // 한 화면에 보이는 개수만큼 호출
//    override fun onBindViewHolder(holder: MainCardHolder, position: Int) {
//        // 사용할 데이터 꺼내기
//        val item = dummy[position]
//        // holder에 데이터 전달
//        holder.setData(item)
//    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (items[position].cardType) {
            USER_PLANT -> {
                (holder as MainCardHolder).bind(items[position])
                //holder.setIsRecyclable(false)
            }
            ADD_PLANT -> {
                (holder as AddCardHolder).bind(items[position])
                //(holder as AddCardHolder).setIsRecyclable(false)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].cardType
    }

    inner class MainCardHolder(val binding: ItemMainCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        //private lateinit var nickName: TextView

//        init{
//            nickName = mainView.findViewById(R.id.nickName)
//            itemView.moveToLogBtn.setOnClickListener {
//                val intent = Intent(itemView.moveToLogBtn.context, PlantLogActivity::class.java)
//                // 인텐트로 현재 식물이 무엇인지 정보 넘겨야 함 -> putExtra()
//                startActivity(itemView.moveToLogBtn.context, intent, null)
//                // finish() 해야 하나?
//            }
//        }

        // data: 식물 이름(더미)
        fun bind(item: MainPlantData) {
            binding.apply {
                nickName.text = item.plantInfo.plantNickName
                Glide.with(binding.root.context)
                    .load(item.plantInfo.filePath)
                    .into(binding.mainPlantImg)
                // 디데이 계산하기
                val today = Calendar.getInstance()
                val sf = SimpleDateFormat("yyyy-MM-dd")  // 단순히 날짜만 확인하기위해 시간을 00:00:00으로 셋팅함.
                val date = sf.parse(item.plantInfo.plantAdaptTime) //string to Date
                // 날짜 셋팅
                val calcDate = (today.time.time - date.time) / (60 * 60 * 24 * 1000)
                dday.text = "함께한지 ${calcDate + 1}일 째"

                moveToLogBtn.setOnClickListener {
                    val intent = Intent(itemView.moveToLogBtn.context, PlantLogActivity::class.java)
                    // 인텐트로 현재 식물이 무엇인지 정보 넘겨야 함 -> putExtra()
                    startActivity(itemView.moveToLogBtn.context, intent, null)
                    // finish() 해야 하나?
                }

                waterBtn.setOnClickListener {
                    // 다이얼로그를 생성하기 위해 Builder 클래스 생성자를 이용해 줍니다.
                    val dialog = AlertDialog.Builder(this.root.context)
                    dialog.setTitle("물 주기 과제 수행")
                        .setMessage("물 주기 과제를 완료하시겠어요?")
                        .setPositiveButton("확인"
                        ) { _, _ ->
                            assignmentRepository.putWaterAss(userId, item.plantInfo.myPlantId)
                        }
                        .setNegativeButton("취소"
                        ) { _, _ ->
                            Toast.makeText(this.root.context, "물 주기 과제를 취소합니다.", Toast.LENGTH_SHORT)
                                .show()
                        }
                    // 다이얼로그를 띄워주기
                    dialog.show()
                }

                repotBtn.setOnClickListener {
                    val dialog = AlertDialog.Builder(this.root.context)
                    dialog.setTitle("분갈이 과제 수행")
                        .setMessage("분갈이 과제를 완료하시겠어요?")
                        .setPositiveButton("확인"
                        ) { _, _ ->
                            assignmentRepository.putRepotAss(userId, item.plantInfo.myPlantId)
                        }
                        .setNegativeButton("취소"
                        ) { _, _ ->
                            Toast.makeText(this.root.context, "분갈이 과제를 취소합니다.", Toast.LENGTH_SHORT)
                                .show()
                        }
                    // 다이얼로그를 띄워주기
                    dialog.show()
                }

                careBtn.setOnClickListener {
                    val dialog = AlertDialog.Builder(this.root.context)
                    dialog.setTitle("돌보기 과제 수행")
                        .setMessage("돌보기 과제를 완료하시겠어요?")
                        .setPositiveButton("확인"
                        ) { _, _ ->
                            assignmentRepository.putLookAss(userId, item.plantInfo.myPlantId)
                        }
                        .setNegativeButton("취소"
                        ) { _, _ ->
                            Toast.makeText(this.root.context, "돌보기 과제를 취소합니다.", Toast.LENGTH_SHORT)
                                .show()
                        }
                    // 다이얼로그를 띄워주기
                    dialog.show()
                }

                sunBtn.setOnClickListener {
                    val dialog = AlertDialog.Builder(this.root.context)
                    dialog.setTitle("광합성 과제 수행")
                        .setMessage("광합성 과제를 완료하시겠어요?")
                        .setPositiveButton("확인"
                        ) { _, _ ->
                            assignmentRepository.putSunAss(userId, item.plantInfo.myPlantId)
                        }
                        .setNegativeButton("취소"
                        ) { _, _ ->
                            Toast.makeText(this.root.context, "광합성 과제를 취소합니다.", Toast.LENGTH_SHORT)
                                .show()
                        }
                    // 다이얼로그를 띄워주기
                    dialog.show()
                }
            }
        }
    }

        inner class AddCardHolder(val binding: ItemMainAddCardBinding) :
            RecyclerView.ViewHolder(binding.root) {
//       init{
//           itemView.addPlantBtn.setOnClickListener {
//                Toast.makeText(context, "식물 추가 버튼이 클릭되었습니다.", Toast.LENGTH_SHORT).show()
//            }
//        }

            //        // data: 식물 이름(더미)
            fun bind(item: MainPlantData) {
                binding.apply {
                    addPlantBtn.setOnClickListener {
                        Log.d("test", "식물 추가 버튼 클릭됨!")
//                            AddPlantDialog(binding.root.context) {
//                                viewModel.setName(it)
//                            }.show()
                        //AddPlantDialog(binding.root.context).show()
                        val intent = Intent(binding.root.context, AddPlantActivity::class.java)
                        startActivity(binding.root.context, intent, null)
                    }
                }
            }
        }
}