package plantity.plantity_android.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_plant_list_recycler.*
import plantity.plantity_android.R
import plantity.plantity_android.databinding.ItemPlantListRecyclerBinding

class SearchAdapter(private var plantsList: MutableList<Content>): RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    // 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // 표시되는 view의 정보를 넘겨줘야 해서 3개의 인자 전달
        val binding = ItemPlantListRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // position 에 해당하는 데이터를 뷰홀더의 아이템 뷰에 표시
    // 한 화면에 보이는 목록 개수만큼 호출
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // 1. 사용할 데이터 꺼내기
        val plant = plantsList[position]
        // 2. 홀더에 데이터 전달
        holder.setPlant(plant)
        // 3. class Holder에서 받은 데이터를 출력함
    }

    // 전체 데이터 개수 리턴
    override fun getItemCount(): Int {
        return plantsList!!.size
    }

    fun setPlantList(list: MutableList<Content>){
        plantsList = list
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemPlantListRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
        lateinit var currentPlant: Content
        var isLiked: Boolean = false

        init {
            binding.heartIcon.setOnClickListener {
                // 좋아요 취소
                if (isLiked) {
                    binding.heartIcon.setImageResource(R.drawable.ic_heart)
                    isLiked = false
                }
                // 좋아요
                else {
                    binding.heartIcon.setImageResource(R.drawable.ic_heart_full)
                    isLiked = true
                }
            }
            // 클릭 했을 때 상세 보기로 넘어가는 클릭 이벤트 리스너 작성하기
            //Toast.makeText(binding.root.context, "선택된 아이템: ${currentPlant.cntntsSj}", Toast.LENGTH_SHORT).show()
        }

        fun setPlant(plant: Content) {
            currentPlant = plant
            with(binding){
                plantType.text = plant.cntntsSj
                plantDescription.text = plant.adviseInfo
                when (plant.managelevelCode){
                    "089001" -> difficultyRate.text = "난이도: ⭐"
                    "089002" -> difficultyRate.text = "난이도: ⭐⭐"
                    "089003" -> difficultyRate.text = "난이도: ⭐⭐⭐"
                    else -> difficultyRate.text = "난이도: "
                }


            }
        }
    }
}