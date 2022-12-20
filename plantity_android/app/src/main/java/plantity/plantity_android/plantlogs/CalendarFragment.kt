package plantity.plantity_android.plantlogs

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import plantity.plantity_android.R
import plantity.plantity_android.databinding.FragmentCalendarBinding
import plantity.plantity_android.search.Content

class CalendarFragment : Fragment() {
    private lateinit var binding: FragmentCalendarBinding
    private lateinit var plantLogActivity: PlantLogActivity
    private lateinit var myPlantDatas: ArrayList<MyPlantData>

    lateinit var currentItem: MyPlantData
    //lateinit var currentItem: MyPlantLogDetail
    var assignmentDays = mutableListOf<CalendarDay>()
    var selectedDate: CalendarDay = CalendarDay.today()

    private var dateText = ""
    private var dayText = ""

    lateinit var logDecorator: CalendarLogDecorator

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(context is PlantLogActivity)
            plantLogActivity = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //var assignmentDays = mutableListOf<CalendarDay>()
//        var logInflater: LayoutInflater
//        var logDetailView: View

        binding = FragmentCalendarBinding.inflate(inflater, container, false)

        // PlantLogActivity로부터 번들 받기
        val bundle = Bundle(arguments)
        myPlantDatas = bundle.get("myPlantDatas") as ArrayList<MyPlantData>
        //logDates = myPlantDatas[0].logData.map
        //val date = LocalDate.parse(string, DateTimeFormatter.ISO_DATE)
        assignmentDays =
            myPlantDatas[0].logData.map{ CalendarDay.from(LocalDate.parse(it.date, DateTimeFormatter.ISO_DATE)) } as MutableList<CalendarDay>
        Log.d("test", "assignmentDays are ${assignmentDays.size}")

        // 과제 완료한 날들 dummy로 추가
//        assignmentDays.add(CalendarDay.from(2022, 9, 11))
//        Log.d("test", "today is ${CalendarDay.today()}")
//        assignmentDays.add(CalendarDay.from(2022, 9, 22))

        logDecorator = CalendarLogDecorator(activity, assignmentDays as ArrayList<CalendarDay>)

        // 오늘 날짜는 선택된 상태로
        val oneDayDecorator = OneDayDecorator(context)

        with(binding){
            // 오늘을 선택된 날짜로
            calendarView.selectedDate = CalendarDay.today()
            dateText = selectedDate.day.toString()  // 일
            when(selectedDate.date.dayOfWeek.value){  // 요일
                1 -> dayText = "Mon"
                2 -> dayText = "Tue"
                3 -> dayText = "Wed"
                4 -> dayText = "Thur"
                5 -> dayText = "Fri"
                6 -> dayText = "Sat"
                7 -> dayText = "Sun"
            }
            val text = dateText+"\n"+dayText
            selectedDay.text = text

            // 로그 기록과 날짜 클릭 데코레이터 등록
            calendarView.addDecorators(logDecorator, oneDayDecorator)

            // 선택된 날짜 바꼈을 때
            calendarView.setOnDateChangedListener(object: OnDateSelectedListener{
                override fun onDateSelected(
                    widget: MaterialCalendarView,
                    date: CalendarDay,
                    selected: Boolean
                ) {
//                    val index = MyPlantData()
                    // 로그 기록 없을 때를 위한 데코레이터 -> 수정 필요... -> 완료
                    //calendarView.removeDecorator(oneDayDecorator)
                    selectedDate = date
//                    Log.d("test", "parameter date is $date")
                    Log.d("test", "selectedDate is $selectedDate")
                    //calendarView.addDecorator(oneDayDecorator)

                    /* 데이터 가져오기 */

                    // 달력에 날짜 바꿔주기
                    dateText = selectedDate.day.toString()  // 일
                    //val day = selectedDate.date.dayOfWeek.value  // 요일
                    dayText = ""
                    when(selectedDate.date.dayOfWeek.value){  // 요일
                        1 -> dayText = "Mon"
                        2 -> dayText = "Tue"
                        3 -> dayText = "Wed"
                        4 -> dayText = "Thur"
                        5 -> dayText = "Fri"
                        6 -> dayText = "Sat"
                        7 -> dayText = "Sun"
                    }
                    Log.d("test", "day is ${selectedDate.date.dayOfWeek}")

                    Log.d("test", "date: $dateText , day: $dayText")
                    val text = dateText+"\n"+dayText
                    Log.d("test", "setting text to $text")
                    selectedDay.text = text

                    // 로그 디테일 내용 바꿔주기
                    var selectedLog = currentItem.logData.find{ CalendarDay.from(LocalDate.parse(it.date, DateTimeFormatter.ISO_DATE)) == selectedDate }

                    if (selectedLog != null) {
                        changeLogText(selectedLog)
                    }
                    else{
                        changeLogText(MyPlantLogData(selectedDate.toString(), false, false, false, false))
                    }
                }
            })
        }
        return binding.root
    }

    // position은 0부터 시작
    fun handleCardSwipe(pos: Int){
        currentItem = myPlantDatas[pos]
        var currentItemLog = currentItem.logData
        Log.d("test", "position is $pos")
        Log.d("test", "currentItem is ${currentItem.name}")
        Log.d("test", "currentLog size is ${currentItemLog.size}")

        // 바뀐 식물의 로그 데이터로 변경
        assignmentDays.clear()
        Log.d("test", "after clear(), ${assignmentDays.size}")
        assignmentDays =
            myPlantDatas[pos].logData.map{ CalendarDay.from(LocalDate.parse(it.date, DateTimeFormatter.ISO_DATE)) } as MutableList<CalendarDay>
        Log.d("test", "changed assignmentDays[0] is ${assignmentDays[0]}")

        // 기존에 있던 로그 데코레이터 삭제하고 새로 선택된 식물 정보로 새로 등록
        binding.calendarView.removeDecorator(logDecorator)
        logDecorator = CalendarLogDecorator(activity, assignmentDays as ArrayList<CalendarDay>)
        binding.calendarView.addDecorator(logDecorator)

        var selectedLog = currentItemLog.find{ CalendarDay.from(LocalDate.parse(it.date, DateTimeFormatter.ISO_DATE)) == binding.calendarView.selectedDate }

        if (selectedLog != null) {
            changeLogText(selectedLog)
        }
    }

    fun changeLogText(log : MyPlantLogData){
        with(binding){
            if(log.caring)
                caringText.text = "살펴보기 완료!"
            else
                caringText.text = "살펴보기 미완료"

            if(log.repotting)
                repottingText.text = "분갈이 완료!"
            else
                repottingText.text = "분갈이 미완료"

            if(log.sunlight)
                sunlightText.text = "광합성 완료!"
            else
                sunlightText.text = "광합성 미완료"

            if(log.watering)
                wateringText.text = "물 주기 완료!"
            else
                wateringText.text = "물 주기 미완료"
        }
    }
}