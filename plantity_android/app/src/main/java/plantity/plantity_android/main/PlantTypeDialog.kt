package plantity.plantity_android.main

import android.R
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.view.Display
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_add_plant.*
import plantity.plantity_android.databinding.ItemRecyclerDialogBinding
import plantity.plantity_android.databinding.RecyclerDialogLayoutBinding


//AddPlantDialog(context).show()
class PlantTypeDialog(context: Context): Dialog(context) {
    protected lateinit var binding: RecyclerDialogLayoutBinding
    private lateinit var recyclerAdapter : DialogRecyclerAdapter
    private lateinit var recyclerView : RecyclerView

    private lateinit var listener : MyDialogOKClickedListener

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 만들어놓은 xml 뷰를 띄운다.
        binding = RecyclerDialogLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()

        recyclerAdapter = DialogRecyclerAdapter(arrayListOf("몬스테라", "선인장"))

        //this.setCanceledOnTouchOutside(true)  // 외부 터치 시 다이얼로그 종료

        binding.dialogRecyclerView.adapter = recyclerAdapter
        binding.dialogRecyclerView.layoutManager = LinearLayoutManager(this.context)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun initViews() {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display: Display? = context.display

        val lp = WindowManager.LayoutParams()
        val windowMetrics =  windowManager.currentWindowMetrics

        lp.copyFrom(this.window!!.attributes)

        val width: Int = windowMetrics.bounds.width()
        val height: Int = windowMetrics.bounds.height()
        lp.width = width * 80 / 100 // 사용자 화면의 80%
        //lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.height = height * 60/100
//
//        dialog.setContentView(dialogView) // Dialog에 선언했던 layout 적용
//
        this.setCanceledOnTouchOutside(true) // 외부 touch 시 Dialog 종료
        this.window!!.attributes = lp // 지정한 너비, 높이 값 Dialog에 적용

    }

    inner class DialogRecyclerAdapter(list: ArrayList<String>?) : RecyclerView.Adapter<DialogRecyclerAdapter.ViewHolder>() {
        private var mData: ArrayList<String>? = null
        lateinit var parentContext: Context

        init {
            mData = list // 입력받은 list를 저장
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            parentContext = parent.context // parent로부터 content 받음
            val binding = ItemRecyclerDialogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding) // ViewHolder 반환
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val text = mData!![position] // 어떤 포지션의 텍스트인지 조회
            holder.setPlantType(text) // 해당 포지션의 View item에 텍스트 입힘
        }

        override fun getItemCount(): Int {
            return mData!!.size
        }

        inner class ViewHolder(val itemBinding : ItemRecyclerDialogBinding) : RecyclerView.ViewHolder(itemBinding.root) {
            lateinit var currentPlant: String

            init {
                itemBinding.root.setOnClickListener {
                    Toast.makeText(itemBinding.root.context, "선택된 아이템: $currentPlant", Toast.LENGTH_SHORT).show()
                    // 여기서 currentPlant를 addplantactivity로 보내는 방법??
                    listener.onOKClicked(currentPlant)
                    dismiss()
                }
            }

            fun setPlantType(type: String){
                currentPlant = type
                itemBinding.itemRecycler.text = type
            }
        }
    }

    fun setOnOKClickedListener(listener: (String) -> Unit) {
        this.listener = object: MyDialogOKClickedListener {
            override fun onOKClicked(content: String) {
                listener(content)
            }
        }
    }

    interface MyDialogOKClickedListener {
        fun onOKClicked(content : String)
    }
}