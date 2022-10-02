package plantity.plantity_android.guideline

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.NonDisposableHandle.parent
import plantity.plantity_android.PlantDetailActivity
import plantity.plantity_android.R
import plantity.plantity_android.databinding.GuidelineCardCellBinding

class GuideLineListAdapter(private val data: ArrayList<GuidelineData>) : RecyclerView.Adapter<GuideLineListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.guideline_card_cell,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tv_title: TextView = itemView.findViewById(R.id.tv_title)
        private val tv_subtitle: TextView = itemView.findViewById(R.id.tv_subtitle)

        fun bind(item: GuidelineData) {
            tv_title.text = item.title
            tv_subtitle.text = item.subtitle

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, GuideLineDetailActivity::class.java)
                intent.putExtra("title", item.title)
                ContextCompat.startActivity(itemView.context, intent, null)
            }
        }
    }


}
data class GuidelineData(
    val title : String,
    val subtitle : String
)

