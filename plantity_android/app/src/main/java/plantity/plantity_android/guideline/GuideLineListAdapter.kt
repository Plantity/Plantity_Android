package plantity.plantity_android.guideline

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import plantity.plantity_android.databinding.GuidelineCardCellBinding

class GuideLineListAdapter(val gdlist: List<GuideData>) : RecyclerView.Adapter<GuideLineListAdapter.GuideViewHolder>(){
    inner class GuideViewHolder(val binding: GuidelineCardCellBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : GuideViewHolder
       = GuideViewHolder(GuidelineCardCellBinding.inflate(LayoutInflater.from(parent.context),parent,false))


    override fun onBindViewHolder(holder: GuideViewHolder, position: Int) {
        Log.d("리사이클러뷰 call","onBindView Holder:$position")
        val binding = (holder as GuideViewHolder).binding
        //뷰에 데이터 출력
        binding.gdCardTitle.text = gdlist[position].toString()
        binding.cardview.setOnClickListener{
            Log.d("cardClick", "item root click:$position")
        }
    }

    override fun getItemCount(): Int = gdlist.size

}
  /* RecyclerView.Adapter<GuideLineListAdapter.MyViewHolder>() {

        class MyViewHolder(val binding: GuidelineCardCellBinding) :RecyclerView.ViewHolder(binding.root){
            fun bind(title: String, des: String) {
                binding.gdCardTitle.text=title
                binding.gdCardContext.text=des

                binding.cardview.setOnClickListener {
                    val intent:Intent = Intent(it.context, GuideLineDetailActivity::class.java)
                    intent.putExtra("currentTitle",title)
                    intent.putExtra("currentDes",des)
                    it.context.startActivity(intent)
                }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = GuidelineCardCellBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(titleList[position],desList[position])
    }

    override fun getItemCount(): Int {
        return titleList.size
    }


}*/
