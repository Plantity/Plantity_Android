package plantity.plantity_android.guideline

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import plantity.plantity_android.databinding.GuidelineCardCellBinding

class GuideLineListAdapter(val titleList : Array<String>, val desList : Array<String>) :
    RecyclerView.Adapter<GuideLineListAdapter.MyViewHolder>() {

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


}