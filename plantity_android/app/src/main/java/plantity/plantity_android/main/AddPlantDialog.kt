package plantity.plantity_android.main

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import plantity.plantity_android.databinding.FragmentAddPlantBinding

const val GET_GALLERY_IMAGE = 200

/* 추후에 정리하면서 파일 삭제 */
class AddPlantDialog(
    context: Context,
    //private val okCallback: (String) -> Unit,
) : Dialog(context) { // 뷰를 띄워야하므로 Dialog 클래스는 context를 인자로 받는다.

    private lateinit var binding: FragmentAddPlantBinding
    private var nickName: String = ""
    private var adoptDate: String = ""  // 2022-10-8 형식


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 만들어놓은 dialog_profile.xml 뷰를 띄운다.
        binding = FragmentAddPlantBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    @RequiresApi(Build.VERSION_CODES.O)  // 없으면 datepicker 리스너 등록이 안됨
    private fun initViews() = with(binding) {
        // 뒤로가기 버튼, 빈 화면 터치를 통해 dialog가 사라지지 않도록
        setCancelable(false)

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
                Toast.makeText(context, "애칭을 입력하세요!", Toast.LENGTH_SHORT).show()
            }
            else {
                /* 서버에 POST하는 코드 작성*/
                //okCallback(profileEt.text.toString())
                nickName = editTextNickName.text.toString()
                Toast.makeText(context, "${adoptDate}에 입양한 $nickName 추가 완료!", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }

        addViewClosebutton.setOnClickListener{
            dismiss()
        }

        imageAddBtn.setOnClickListener {
//            setOnClickListenerval intent = Intent(Intent.ACTION_PICK)
//            intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
//            startActivityForResult(context.applicationContext as Activity, intent, GET_GALLERY_IMAGE, null)
        }
    }

//    override fun onActivityResult(requestCode:Int, resultCode: Int, data: Intent){
//
//    }
}