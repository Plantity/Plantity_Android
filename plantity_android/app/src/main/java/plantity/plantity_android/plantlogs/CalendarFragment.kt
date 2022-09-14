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
import plantity.plantity_android.R
import plantity.plantity_android.databinding.FragmentCalendarBinding

class CalendarFragment : Fragment() {
    private lateinit var binding: FragmentCalendarBinding
    private lateinit var plantLogActivity: PlantLogActivity

    var selectedDate: CalendarDay = CalendarDay.today()

    private var dateText = ""
    private var dayText = ""

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
        var assignmentDays = mutableListOf<CalendarDay>()
        var logInflater: LayoutInflater
        var logDetailView: View

        binding = FragmentCalendarBinding.inflate(inflater, container, false)

        // 과제 완료한 날들 dummy로 추가
        assignmentDays.add(CalendarDay.from(2022, 9, 11))
        Log.d("test", "today is ${CalendarDay.today()}")
        assignmentDays.add(CalendarDay.from(2022, 9, 22))

        val logDecorator = CalendarLogDecorator(activity, assignmentDays as ArrayList<CalendarDay>)

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
                    // 로그 기록 없을 때를 위한 데코레이터 -> 수정 필요...
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
                }
            })

            // 식물 로그 과제 찍는 코드
            logInflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            logDetailView = logInflater.inflate(R.layout.item_plant_log_caring, null)
            logDetail.addView(logDetailView)

            logDetailView = logInflater.inflate(R.layout.item_plant_log_sunlight, null)
            logDetail.addView(logDetailView)

            logDetailView = logInflater.inflate(R.layout.item_plant_log_watering, null)
            logDetail.addView(logDetailView)

            logDetailView = logInflater.inflate(R.layout.item_plant_log_repotting, null)
            logDetail.addView(logDetailView)
        }

        return binding.root
    }
}