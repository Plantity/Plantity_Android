package plantity.plantity_android.plantlogs

import android.content.Context
import android.graphics.drawable.Drawable
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import plantity.plantity_android.R

class OneDayDecorator(context: Context?): DayViewDecorator {
    private val drawable: Drawable = context?.getDrawable(R.drawable.calendar_background)!!

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return true
    }

    override fun decorate(view: DayViewFacade?) {
        // view?.setBackgroundDrawable(drawable)  // 이거 쓰면 기존 circle이 그대로 남음!!!
        view?.setSelectionDrawable(drawable)
    }

    init{
        // set background for decorator via drawable here
    }
}