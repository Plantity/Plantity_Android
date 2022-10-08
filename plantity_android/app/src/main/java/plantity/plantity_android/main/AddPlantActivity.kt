package plantity.plantity_android.main

import android.Manifest
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import plantity.plantity_android.databinding.ActivityAddPlantBinding

class AddPlantActivity : AppCompatActivity() {
    val binding by lazy { ActivityAddPlantBinding.inflate(layoutInflater) }

    private var nickName: String = ""
    private var adoptDate: String = ""  // 2022-10-8 형식

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

    @RequiresApi(Build.VERSION_CODES.O)  // 없으면 datepicker 리스너 등록이 안됨
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Log.d("test", "inside addplantactivity")
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

            imageAddBtn.setOnClickListener {
                readImage.launch("image/*")
                it.isEnabled = false
                it.visibility = View.INVISIBLE
            }

            addViewClosebutton.setOnClickListener{
                finish()
            }
        }
    }
}