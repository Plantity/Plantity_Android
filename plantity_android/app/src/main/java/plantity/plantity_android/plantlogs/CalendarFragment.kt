package plantity.plantity_android.plantlogs

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import kotlinx.android.synthetic.main.fragment_calendar.*
import plantity.plantity_android.R
import plantity.plantity_android.databinding.FragmentCalendarBinding
import plantity.plantity_android.databinding.ItemCalendarDayBinding
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*

class CalendarFragment : Fragment() {
    private lateinit var binding: FragmentCalendarBinding
    private lateinit var plantLogActivity: PlantLogActivity

    private var selectedDate: LocalDate? = null
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

        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(5)
        val endMonth = currentMonth.plusMonths(5)
        var inflater: LayoutInflater
        var logDetailView: View

        with(binding){
            //selectedDate = today  // selectedDate는 today 날짜로 초기화 -> today는 계속 선택layout 적용된 상태가 됨.
            calendarView.setup(startMonth, endMonth, DayOfWeek.SUNDAY)
            calendarView.scrollToDate(currentMonth.atDay(today.dayOfMonth))  // no smooth animation
//            legendLayout.root.children.forEachIndexed { index, view ->
//                (view as TextView).apply {
//                    text = daysOfWeek[index].getDisplayName(TextStyle.SHORT, Locale.ENGLISH).uppercase(Locale.ENGLISH)
//                    setTextColor(Color.WHITE)
//                }
//            }

            inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            logDetailView = inflater.inflate(R.layout.item_plant_log_caring, null)
            logDetail.addView(logDetailView)

            logDetailView = inflater.inflate(R.layout.item_plant_log_sunlight, null)
            logDetail.addView(logDetailView)
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
                    Log.d("test", "date clicked, selectedDate:"+selectedDate.toString())
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
                        // 새로 선택한 날짜이면 새로 선택한 날짜로 변경
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

//        class MonthViewContainer(view: View) : ViewContainer(view) {
//            val textView = view.findViewById<TextView>(R.id.textCalendarHeader)
//        }
//        binding.calendarView.monthHeaderBinder = object: MonthHeaderFooterBinder<MonthViewContainer> {
//            override fun create(view: View) = MonthViewContainer(view)
//            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
////                container.textView.text = "${month.yearMonth.month.name.lowercase().capitalize()} ${month.year}"
//            }
//        }

        binding.calendarView.dayBinder = object : DayBinder<DayViewContainer> {
            // called only when a new container is needed
            override fun create(view: View) = DayViewContainer(view)

            // called every time we need to reuse a container
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.day = day  // calendar day for this container
                val textView = container.textView
                textView.text = day.date.dayOfMonth.toString()

                if(day.owner == DayOwner.THIS_MONTH){
//                    Log.d("test", "selectedDate: $selectedDate")
//                    Log.d("test", "day.date: ${day.date}")
//                    Log.d("test", "selectedDate==day.date: ${selectedDate == day.date}")
//                    Log.d("test", "selectedDate == null: ${selectedDate == null}")
                    textView.setTextColor(Color.BLACK)

                    if(selectedDate == day.date)
                        textView.setBackgroundResource(R.drawable.calendar_selected_day)
                    else
                        textView.background = null
                }
                else {
                    textView.setTextColor(Color.GRAY)
                    textView.background = null
                }
            }
        }

        // 달력 스크롤 시 바뀐 년도, 월 반영
        binding.calendarView.monthScrollListener = {
            /* only week mode */
            // In week mode, we show the header a bit differently.
            // We show indices with dates from different months since
            // dates overflow and cells in one index can belong to different
            // months/years.
            val firstDate = it.weekDays.first().first().date
            //val firstDate = it.weekDays.get(0).get(0).date
            val lastDate = it.weekDays.last().last().date
            //val lastDate = it.weekDays[last].
            if (firstDate.yearMonth == lastDate.yearMonth) {
                binding.textCalendarYear.text = firstDate.yearMonth.year.toString()
                binding.textCalendarMonth.text = monthTitleFormatter.format(firstDate)
            } else {
                binding.textCalendarMonth.text =
                    "${monthTitleFormatter.format(firstDate)} - ${monthTitleFormatter.format(lastDate)}"
                if (firstDate.year == lastDate.year) {
                    binding.textCalendarYear.text = firstDate.yearMonth.year.toString()
                } else {
                    binding.textCalendarYear.text = "${firstDate.yearMonth.year} - ${lastDate.yearMonth.year}"
                }
            }
        }
    }
}