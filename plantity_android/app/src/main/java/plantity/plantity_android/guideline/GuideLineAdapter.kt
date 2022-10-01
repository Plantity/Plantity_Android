package plantity.plantity_android.guideline

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import plantity.plantity_android.R
import plantity.plantity_android.databinding.GuidelineCardCellBinding
import plantity.plantity_android.search.Content
import plantity.plantity_android.search.SearchAdapter

class GuideLineAdapter(private var guideLineDataList: MutableList<GuideLineData>): RecyclerView.Adapter<GuideLineAdapter.ViewHolder>() {

    // 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // 표시되는 view의 정보를 넘겨줘야 해서 3개의 인자 전달
        val binding =
            GuidelineCardCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // position 에 해당하는 데이터를 뷰홀더의 아이템 뷰에 표시
    // 한 화면에 보이는 목록 개수만큼 호출
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // 1. 사용할 데이터 꺼내기
        val guideLine = guideLineDataList[position]
        // 2. 홀더에 데이터 전달
        holder.setGuideLine(guideLine)
        // 3. class Holder에서 받은 데이터를 출력함
    }

    // 전체 데이터 개수 리턴
    override fun getItemCount(): Int {
        return guideLineDataList!!.size
    }

//    fun setPlantList(list: MutableList<Content>) {
//        plantsList = list
//        notifyDataSetChanged()
//    }

    class ViewHolder(val binding: GuidelineCardCellBinding) :
        RecyclerView.ViewHolder(binding.root) {
        lateinit var currentData: GuideLineData

        init {
            // 클릭 했을 때 상세 보기로 넘어가는 클릭 이벤트 리스너 작성하기
            //Toast.makeText(binding.root.context, "선택된 아이템: ${currentPlant.cntntsSj}", Toast.LENGTH_SHORT).show()
        }

        fun setGuideLine(info: GuideLineData) {
            with(binding) {
                gdCardTitle.text = info.title
                gdCardContext.text = info.context
            }
        }
    }
}