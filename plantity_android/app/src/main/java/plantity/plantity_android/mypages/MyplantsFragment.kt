package plantity.plantity_android.mypages

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_myplants.*
import kotlinx.android.synthetic.main.mypage_myplant_cell.view.*
import plantity.plantity_android.R
import plantity.plantity_android.databinding.FragmentMyplantsBinding
import plantity.plantity_android.databinding.MypageMyplantCellBinding
import plantity.plantity_android.main.AddPlantActivity
import plantity.plantity_android.plantlogs.*
import plantity.plantity_android.search.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyplantsFragment : Fragment() {
    val binding by lazy{FragmentMyplantsBinding.inflate(layoutInflater)}
    val userId: Int = 222  // 임시 유저 아이디
    val myPlantListRepository = MyPlantsRepository()
    var myPlantList = mutableListOf<MyPlantInfo>()  // 내 식물 아이디 컬렉션

    var myPlantDetailList = mutableListOf<MyPlantDetail>()

    private lateinit var cardViewAdapter: CardViewAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* 데이터 먼저 가져오기!! */
        // getMyPlantList(userId) { getPlantDetail() }
        myPlantListRepository.getMyPlantList(userId, ::setPlantList)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_myplants, container, false)
        val addbutton: ImageButton = view.findViewById(R.id.addbutton)
        addbutton.setOnClickListener {
            val intent = Intent(activity, AddPlantActivity::class.java)
            startActivity(intent)
        }

        return view

    }

    private fun setPlantList(list: List<MyPlantInfo>) {
        myPlantList.addAll(list)
        Log.d("test", "myPlantList size: ${myPlantList.size}")


        cardViewAdapter = CardViewAdapter(myPlantList as ArrayList<MyPlantInfo>)
        binding.myplantsRv.adapter = cardViewAdapter
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.myplantsRv.layoutManager = linearLayoutManager
//        setCardViewAdapter()

    }
    private fun getMyPlantDetail(userId: Int, plantId: Int){
        val call = RetrofitClient.myPlantDetailService.getMyPlantDetail(userId, plantId)
        call.enqueue(object: Callback<MyPlantDetailResponse> {
            override fun onResponse(
                call: Call<MyPlantDetailResponse>,
                response: Response<MyPlantDetailResponse>
            ) {
                //Log.d("test", "서버 body: ${response.body()!!}")
                Log.d("test", "======= 내 식물 상세 조희 ${response.body()!!.isSuccess} =======")
                if(response.body()!!.isSuccess){
                    Log.d("test", "서버 code: ${response.body()!!.code}")
                    Log.d("test", "서버 msg: ${response.body()!!.message}")
                    Log.d("test", "서버 body: ${response.body()!!}")
                    myPlantDetailList.add(response.body()!!.result)  // 해당 아이디의 식물 정보 컬렉션에 추가
                }
                else{
                    Log.d("test", "Error!! 서버 code: ${response.body()!!.code}")
                    Log.d("test", "Error!! 서버 msg: ${response.body()!!.message}")
                }
            }

            override fun onFailure(call: Call<MyPlantDetailResponse>, t: Throwable) {
                Log.d("test", "======= 내 식물 상세 조희 실패, code: ${t.message} =======")
            }
        })
    }
//    private fun setCardViewAdapter() {
//        //cardViewAdapter = CardViewAdapter(myPlantsList as ArrayList<MyPlantInfo>)
//        cardViewAdapter =CardViewAdapter(myPlantList as ArrayList<MyPlantInfo>)
//        Log.d("test", "setting CardViewAdapter, ${myPlantList.size}")
//    }

}
class CardViewAdapter(val items: ArrayList<MyPlantInfo>): RecyclerView.Adapter<CardViewAdapter.Holder>(){

    // 표시되는 뷰의 정보를 넘겨주기
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        // 표시되는 view의 정보를 넘겨줘야 해서 3개의 인자를 전달
        val binding = MypageMyplantCellBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return Holder(binding)
    }

    // 한 화면에 보이는 개수만큼 호출
    override fun onBindViewHolder(holder: Holder, position: Int) {
        // 사용할 데이터 꺼내기
        val item = items[position]
        // holder에 데이터 전달
        holder.setData(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class Holder(val binding: MypageMyplantCellBinding): RecyclerView.ViewHolder(binding.root){
        lateinit var currentItem: MyPlantInfo

        fun setData(data: MyPlantInfo){
            currentItem = data

            Glide.with(binding.root.context)
                .load(data.filePath)
                .into(binding.img)

        }
    }
}