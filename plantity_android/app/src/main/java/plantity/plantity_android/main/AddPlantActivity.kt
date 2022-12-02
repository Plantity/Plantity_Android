package plantity.plantity_android.main

import android.Manifest
import android.content.ContentResolver
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import plantity.plantity_android.R
import plantity.plantity_android.databinding.ActivityAddPlantBinding
import plantity.plantity_android.search.SearchResult
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*
import kotlin.collections.ArrayList

class AddPlantActivity : AppCompatActivity() {
    private val GET_GALLERY_IMAGE = 200

    val binding by lazy { ActivityAddPlantBinding.inflate(layoutInflater) }
    val mainSearchRepository = MainSearchRepository()
    val addPlantRepository = AddPlantRepository()
    var allPlantsTypeList = mutableListOf<Pair<String, String>>()  // <cntntsNo, cntntsSj>
    var page: Int = 0
    val calendar = GregorianCalendar(Locale.KOREA)

    lateinit var imageFile: File
    private var plantNo: String = ""
    private var plantType: String = ""
    private var nickName: String = ""
    // adoptDate: 2022-10-8 형식, 오늘 날짜로 초기화
    private var adoptDate: String =
        calendar.get(Calendar.YEAR).toString()+"-"+(calendar.get(Calendar.MONTH)+1).toString()+"-"+calendar.get(Calendar.DAY_OF_MONTH).toString()

    // 식물 이미지를 위한 권한 동의
    private val permissionList = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    private val checkPermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
        result.forEach {
            if(!it.value) {
                Toast.makeText(applicationContext, "권한 동의 필요!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    // 사용자 갤러리에서 이미지 가져오고 이미지뷰에 띄우기
    private val readImage = registerForActivityResult(ActivityResultContracts.GetContent()){ uri ->
        // 이미지의 실제 경로 가져오기
        imageFile = getRealPathFromURI(uri!!)?.let { File(it) }!!
        Glide.with(this)
            .load(uri)
            .into(binding.plantImage)
    }

    // uri로부터 실제 경로 가져오기
    fun getRealPathFromURI(uri: Uri): String?{
        val contentResolver: ContentResolver = baseContext.contentResolver ?: return null // context.getContentResolver()

        // 파일 경로를 만듬
        val filePath: String = (baseContext.applicationInfo.dataDir + File.separator + System.currentTimeMillis())
        val file = File(filePath)
        try {
            // 매개변수로 받은 uri 를 통해  이미지에 필요한 데이터를 불러 들인다.
            val inputStream = contentResolver.openInputStream(uri) ?: return null
            // 이미지 데이터를 다시 내보내면서 file 객체에  만들었던 경로를 이용한다.
            val outputStream: OutputStream = FileOutputStream(file)
            val buf = ByteArray(1024)
            var len: Int
            while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
            outputStream.close()
            inputStream.close()
        } catch (ignore: IOException) {
            return null
        }
        return file.absolutePath
    }

    @RequiresApi(Build.VERSION_CODES.R)  // 없으면 datepicker 리스너 등록이 안됨
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setPlantsList()

        checkPermission.launch(permissionList)

        with(binding){
            // 뒤로가기 버튼, 빈 화면 터치를 통해 dialog가 사라지지 않도록
//            setCancelable(false)

            // background를 투명하게 만듦
            // (중요) Dialog는 내부적으로 뒤에 흰 사각형 배경이 존재하므로, 배경을 투명하게 만들지 않으면
            // corner radius의 적용이 보이지 않는다.
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            datePicker.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
                adoptDate = year.toString() + "-" + (monthOfYear+1).toString() + "-" + dayOfMonth
            }

            // 내 식물로 추가하기 Button 클릭에 대한 Callback 처리
            addToMyPlantBtn.setOnClickListener {
                Log.d("test", "등록하기 버튼 clicked")
                // 모든 정보가 입력되지 않은 경우
                if (editTextNickName.text.isNullOrBlank()
                    || selectedPlantType.text.isNullOrBlank()
                    || plantImage.drawable == ResourcesCompat.getDrawable(resources, R.drawable.add_button, null)
                    || adoptDate.isBlank()
                ) {
                    Toast.makeText(this@AddPlantActivity, "모든 정보를 입력해주세요!", Toast.LENGTH_SHORT).show()
                }
                else {
                    nickName = editTextNickName.text.toString()

                    /* 서버에 POST */
                    /////////////// userId 수정 필요!!! /////////////
                    //addPlantRepository.postPlantToServer(imageFile, plantType, nickName, adoptDate, plantNo, 220)
                    addPlantRepository.postPlantToServer(AddPlantData(imageFile, plantType, nickName, adoptDate, plantNo), 200)
                    Toast.makeText(this@AddPlantActivity, "${adoptDate}에 입양한 $nickName 추가 완료!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }

            selectedPlantType.setOnClickListener {
                Log.d("test", "selectedPlantType clicked")
                val dialog = PlantTypeDialog(binding.root.context,
                    allPlantsTypeList.map { it.second } as ArrayList<String>
                )
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
                    plantType = content
                    plantNo = allPlantsTypeList.find{ it.second == content }?.first.toString()
                    Log.d("test", "selected plantNo: $plantNo")
                }
            }

            // 이미지 추가
            plantImage.setOnClickListener {
                readImage.launch("image/*")
                Log.d("test", "plant image clicked")
//                val intent = Intent(Intent.ACTION_PICK)
//                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
//                startActivityForResult(intent, GET_GALLERY_IMAGE)
            }

            // 창 닫기 버튼
            addViewClosebutton.setOnClickListener{
                finish()
            }
        }
    }

    // 식물 종류로 이루어진 배열 만들기
    fun setPlantsList(){
        mainSearchRepository.getMainSearchPlants(page, this)
    }

    // MainSearchRepository에서 get 끝나면 호출하는 함수
    fun loadMainSearchPlants(result: SearchResult){
        Log.d("test", "inside loadComplete, page: $page")
        for(plant in result.content){
            allPlantsTypeList.add(Pair(plant.cntntsNo, plant.cntntsSj))
        }
        // 방금 불러온 페이지가 마지막이 아닌 경우
        if(!result.last){
            page++
            mainSearchRepository.getMainSearchPlants(page, this)
        }
        // 방금 불러온 페이지가 마지막인 경우
        else{
            page = 0
        }
    }
}