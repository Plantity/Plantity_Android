package plantity.plantity_android.plantlogs

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.Drawable
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import plantity.plantity_android.R


class CalendarLogDecorator(context: Activity?, private val dates: ArrayList<CalendarDay>): DayViewDecorator {
    // private val drawable: Drawable = context?.getDrawable(R.drawable.calendar_background)!!
    //private val color = R.color.light_green // not working..
    private val color = Color.parseColor("#34C759")

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return dates.contains(day)
    }

    override fun decorate(view: DayViewFacade?) {
        // view?.setBackgroundDrawable(drawable)  // 이거 쓰면 기존 circle이 그대로 남음!!!
        // view?.setSelectionDrawable(drawable)
        view?.addSpan(DotSpan(5F, color))  // 크기 단위가 dp인가?
    }

    init{
        // set background for decorator via drawable here
    }
}