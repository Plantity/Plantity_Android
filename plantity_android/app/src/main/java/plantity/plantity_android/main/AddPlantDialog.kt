package plantity.plantity_android.main

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import plantity.plantity_android.databinding.FragmentAddPlantBinding

class AddPlantDialog(
    context: Context,
    //private val okCallback: (String) -> Unit,
) : Dialog(context) { // 뷰를 띄워야하므로 Dialog 클래스는 context를 인자로 받는다.

    private lateinit var binding: FragmentAddPlantBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 만들어놓은 dialog_profile.xml 뷰를 띄운다.
        binding = FragmentAddPlantBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() = with(binding) {
        // 뒤로가기 버튼, 빈 화면 터치를 통해 dialog가 사라지지 않도록
        setCancelable(false)

        // background를 투명하게 만듦
        // (중요) Dialog는 내부적으로 뒤에 흰 사각형 배경이 존재하므로, 배경을 투명하게 만들지 않으면
        // corner radius의 적용이 보이지 않는다.
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // OK Button 클릭에 대한 Callback 처리. 이 부분은 상황에 따라 자유롭게!
        addviewAddButton.setOnClickListener {
            if (editTextNickName.text.isNullOrBlank()) {
                Toast.makeText(context, "애칭을 입력하세요!", Toast.LENGTH_SHORT).show()
            }
            else if(editTextDate2.text.isNullOrBlank()){
                Toast.makeText(context, "입양날짜를 입력하세요!", Toast.LENGTH_SHORT).show()
            }
            else {
                /* 서버에 POST하는 코드 작성*/
                //okCallback(profileEt.text.toString())
                Toast.makeText(context, "식물을 성공적으로 추가하였습니달라", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }

        addViewClosebutton.setOnClickListener{
            dismiss()
        }
    }
}