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
        Log.d("test", binding.toString())
        assignmentDays.add(CalendarDay.today())
        Log.d("test", "today is ${CalendarDay.today()}")
        assignmentDays.add(CalendarDay.from(2022, 9, 22))

        var decorator = CalendarLogDecorator(activity, assignmentDays as ArrayList<CalendarDay>)
        var todayDecorator = TodayDecorator(context)


        with(binding){
            // 로그 기록이 있을 때를 위한 데코레이터
            calendarView.addDecorators(decorator)

            // 선택된 날짜 바꼈을 때
            calendarView.setOnDateChangedListener(object: OnDateSelectedListener{
                override fun onDateSelected(
                    widget: MaterialCalendarView,
                    date: CalendarDay,
                    selected: Boolean
                ) {
                    // 로그 기록 없을 때를 위한 데코레이터 -> 수정 필요...
                    calendarView.removeDecorator(todayDecorator)
                    selectedDate = date
//                    Log.d("test", "parameter date is $date")
                    Log.d("test", "selectedDate is $selectedDate")
                    calendarView.addDecorator(todayDecorator)

                    /* 데이터 가져오기 */

                    // 달력에 날짜 바꿔주기
                    val date = selectedDate.day.toString()  // 일
                    //val day = selectedDate.date.dayOfWeek.value  // 요일
                    var day = ""
                    when(selectedDate.date.dayOfWeek.value){
                        //0 -> day = "Sun"
                        1 -> day = "Mon"
                        2 -> day = "Tue"
                        3 -> day = "Wed"
                        4 -> day = "Thur"
                        5 -> day = "Fri"
                        6 -> day = "Sat"
                        7 -> day = "Sun"
                    }
                    Log.d("test", "day is ${selectedDate.date.dayOfWeek}")

                    Log.d("test", "date: $date , day: $day")
                    val text = date+"\n"+day
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
        }

        return binding.root
    }
}