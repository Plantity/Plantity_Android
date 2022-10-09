package plantity.plantity_android.main

import android.Manifest
import android.R
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_add_plant.*
import plantity.plantity_android.databinding.ActivityAddPlantBinding
import plantity.plantity_android.databinding.ItemRecyclerDialogBinding


class AddPlantActivity : AppCompatActivity() {
    val binding by lazy { ActivityAddPlantBinding.inflate(layoutInflater) }

    private var nickName: String = ""
    private var adoptDate: String = ""  // 2022-10-8 형식

//    private val recyclerAdapter = DialogRecyclerAdapter(arrayListOf("몬스테라", "선인장"))

    private val permissionList = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    private val checkPermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
        result.forEach {
            if(!it.value) {
                Toast.makeText(applicationContext, "권한 동의 필요!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
    private val readImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        Glide.with(this)
            .load(uri)
            .into(binding.plantImage)
    }

    @RequiresApi(Build.VERSION_CODES.R)  // 없으면 datepicker 리스너 등록이 안됨
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        checkPermission.launch(permissionList)

        with(binding){
            // 뒤로가기 버튼, 빈 화면 터치를 통해 dialog가 사라지지 않도록
//            setCancelable(false)

            // background를 투명하게 만듦
            // (중요) Dialog는 내부적으로 뒤에 흰 사각형 배경이 존재하므로, 배경을 투명하게 만들지 않으면
            // corner radius의 적용이 보이지 않는다.
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            // 오늘 날짜를 바꾸지 않은 그대로 입력하면 바뀌지 않은 걸로 간주돼서 날짜 안찍힘..
            datePicker.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
                adoptDate = year.toString() + "-" + (monthOfYear+1).toString() + "-" + dayOfMonth
            }

            // OK Button 클릭에 대한 Callback 처리. 이 부분은 상황에 따라 자유롭게!
            addToMyPlantBtn.setOnClickListener {
                if (editTextNickName.text.isNullOrBlank()) {
                    Toast.makeText(this@AddPlantActivity, "애칭을 입력하세요!", Toast.LENGTH_SHORT).show()
                }
                else {
                    /* 서버에 POST하는 코드 작성*/
                    //okCallback(profileEt.text.toString())
                    nickName = editTextNickName.text.toString()
                    Toast.makeText(this@AddPlantActivity, "${adoptDate}에 입양한 $nickName 추가 완료!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }

            selectedPlantType.setOnClickListener {
                Log.d("test", "selectedPlantType clicked")
                val dialog = PlantTypeDialog(binding.root.context)
//                val lp = WindowManager.LayoutParams()
//                val windowMetrics =  windowManager.currentWindowMetrics
////
//                // Dialog layout 선언
//                lp.copyFrom(dialog.window!!.attributes)
////         width = size.x;
//                //val width: Int = display.getSize()
//                val width: Int = windowMetrics.bounds.width()
//                val height: Int = windowMetrics.bounds.height()
//                lp.width = width * 80 / 100 // 사용자 화면의 80%
//                //lp.height = WindowManager.LayoutParams.WRAP_CONTENT
//                lp.height = height * 70/100
//
//                dialog.window!!.attributes = lp // 지정한 너비, 높이 값 Dialog에 적용
                dialog.show()
                dialog.setOnOKClickedListener { content ->
                    selectedPlantType.text = content
                }
            }

            // 이미지 추가 버튼 -> 수정 필요
            imageAddBtn.setOnClickListener {
                readImage.launch("image/*")
                it.isEnabled = false
                it.visibility = View.INVISIBLE
            }

            // 창 닫기 버튼
            addViewClosebutton.setOnClickListener{
                finish()
            }
        }
    }

//    class DialogRecyclerAdapter(list: ArrayList<String>?) : RecyclerView.Adapter<DialogRecyclerAdapter.ViewHolder>() {
//        private var mData: ArrayList<String>? = null
//        lateinit var parentContext: Context
//
//        init {
//            mData = list // 입력받은 list를 저장
//        }
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//            parentContext = parent.context // parent로부터 content 받음
//            val binding = ItemRecyclerDialogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//            return ViewHolder(binding) // ViewHolder 반환
//        }
//
//        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//            val text = mData!![position] // 어떤 포지션의 텍스트인지 조회
//            holder.setPlantType(text) // 해당 포지션의 View item에 텍스트 입힘
//        }
//
//        override fun getItemCount(): Int {
//            return mData!!.size
//        }
//
//        inner class ViewHolder(val itemBinding : ItemRecyclerDialogBinding) : RecyclerView.ViewHolder(itemBinding.root) {
//            lateinit var currentPlant: String
//
//            init {
//                itemBinding.root.setOnClickListener {
//                    Toast.makeText(itemBinding.root.context, "선택된 아이템: $currentPlant", Toast.LENGTH_SHORT)
//                    (parentContext as Activity).selected_plant_type.text = currentPlant
//                }
//            }
//
//            fun setPlantType(type: String){
//                itemBinding.itemRecycler.text = type
//            }
//        }
//    }
}