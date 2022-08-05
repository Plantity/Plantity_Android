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
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import plantity.plantity_android.databinding.FragmentCalendarBinding
import plantity.plantity_android.databinding.FragmentTodaysPlantBinding
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

    private val selectedDates = mutableSetOf<LocalDate>()
    private val today = LocalDate.now()
    private val monthTitleFormatter = DateTimeFormatter.ofPattern("MMMM")

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
        Log.d("test", currentMonth.toString())
        val startMonth = currentMonth.minusMonths(10)
        val endMonth = currentMonth.plusMonths(10)

        with(binding){
            calendarView.setup(startMonth, endMonth, daysOfWeek.first())
            calendarView.scrollToMonth(currentMonth)  // 애니메이션 없음
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
            private var selectedDate: LocalDate? = null
            val textView = ItemCalendarDayBinding.bind(view).itemCalendarDayText

            lateinit var day: CalendarDay

            init{
                view.setOnClickListener{
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
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.day = day
                val textView = container.textView
                textView.text = day.date.dayOfMonth.toString()
                if(day.owner == DayOwner.THIS_MONTH){
                    when{
                        selectedDates.contains(day.date) -> {
                            textView.setBackgroundResource(R.drawable.calendar_selected_day)
                        }
                        today == day.date -> {
                            textView.setBackgroundResource(R.drawable.calendar_selected_day)
                        }
                        else -> {
                            textView.background = null
                        }
                    }
                }
                else {
                    textView.background = null
                }
            }
        }
    }
}