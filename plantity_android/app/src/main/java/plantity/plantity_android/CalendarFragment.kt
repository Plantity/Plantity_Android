package plantity.plantity_android

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.model.InDateStyle
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import com.kizitonwose.calendarview.utils.yearMonth
import plantity.plantity_android.databinding.FragmentCalendarBinding
import plantity.plantity_android.databinding.ItemCalendarDayBinding
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

class CalendarFragment : Fragment() {
    private lateinit var binding: FragmentCalendarBinding
    private lateinit var plantLogActivity: PlantLogActivity

    private var selectedDate: LocalDate? = null
    private val today = LocalDate.now()

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
        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCalendarBinding.bind(view)

        val daysOfWeek = DayOfWeek.values()
        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(5)
        val endMonth = currentMonth.plusMonths(5)

        with(binding){
            selectedDate = today  // selectedDate는 today 날짜로 초기화
            Log.d("test", "init selectedDate to $today")
            calendarView.setup(startMonth, endMonth, DayOfWeek.SUNDAY)
            Log.d("test", "scrolling to $currentMonth")
            calendarView.scrollToDate(currentMonth.atDay(1))  // 애니메이션 없음
//            legendLayout.root.children.forEachIndexed { index, view ->
//                (view as TextView).apply {
//                    text = daysOfWeek[index].getDisplayName(TextStyle.SHORT, Locale.ENGLISH).uppercase(Locale.ENGLISH)
//                    setTextColor(Color.WHITE)
//                }
//            }
        }

        //  view container which acts as a view holder for each date cell
        //  The view passed in here is the inflated day view resource which you provided.
        class DayViewContainer(view: View) : ViewContainer(view) {
            // will be set when this container is bound. See the DayBinder.
            private val calendarView = binding.calendarView
            val textView = ItemCalendarDayBinding.bind(view).itemCalendarDayText

            lateinit var day: CalendarDay

            init{
                // 날짜 클릭 이벤트
                view.setOnClickListener{
                    Log.d("test", "selectedDate:"+selectedDate.toString())
                    // Check the day owner as we do not want to select in or out dates.
                    if(day.owner == DayOwner.THIS_MONTH){
                        val currentSelection = selectedDate
                        // 같은 날짜 클릭 시 선택 취소
                        if(currentSelection == day.date){
                            selectedDate = null
                            // Reload this date so the dayBinder is called
                            // and we can REMOVE the selection background.
                            calendarView.notifyDateChanged(currentSelection)
                        }
                        else{
                            // 새로 선택한 날짜이면 새로 선택한 날짜로 변경
                            selectedDate = day.date
                            // 새로 선택된 날짜 reload -> dayBinder 호출, selection background 추가하도록
                            calendarView.notifyDateChanged(day.date)
                            if(currentSelection != null){
                                // 이전에 선택된 날짜 reload -> selection background 제거
                                calendarView.notifyDateChanged(currentSelection)
                            }
                        }
                    }
                }
            }
        }

        class MonthViewContainer(view: View) : ViewContainer(view) {
            val textView = view.findViewById<TextView>(R.id.textCalendarHeader)
        }
        binding.calendarView.monthHeaderBinder = object: MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
//                container.textView.text = "${month.yearMonth.month.name.lowercase().capitalize()} ${month.year}"
            }
        }

        binding.calendarView.dayBinder = object : DayBinder<DayViewContainer> {
            // called only when a new container is needed
            override fun create(view: View) = DayViewContainer(view)

            // called every time we need to reuse a container
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.day = day  // calendar day for this container
//                Log.d("test", day.toString())
                val textView = container.textView
                textView.text = day.date.dayOfMonth.toString()

                if(day.owner == DayOwner.THIS_MONTH){
                    Log.d("test", "selectedDate: $selectedDate")
                    Log.d("test", "day.date: ${day.date}")
                    Log.d("test", "selectedDate==day.date: ${selectedDate == day.date}")
                    Log.d("test", "selectedDate == null: ${selectedDate == null}")
                    textView.setTextColor(Color.BLACK)
                    textView.visibility = View.VISIBLE  // ?

                    if(selectedDate == day.date){
                        Log.d("test", "selectedDate==day.date")
                        textView.setBackgroundResource(R.drawable.calendar_selected_day)
                    }
                    else if(today == day.date){
                        textView.setBackgroundResource(R.drawable.calendar_selected_day)
                    }
                    else if(selectedDate == null) {
                        Log.d("test", "else if set ${textView.text} bg to null")
                        textView.background = null
                    }
                    else{
                        Log.d("test", "set ${textView.text} bg to null")
                        textView.background = null
                    }
                }
                else {
                    textView.setTextColor(Color.GRAY)
                    textView.background = null
                }
            }
        }
    }
}