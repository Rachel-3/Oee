package com.example.oee

import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import android.graphics.Color

class EventDecorator(private val dates: Collection<CalendarDay>) : DayViewDecorator {
    override fun shouldDecorate(day: CalendarDay): Boolean {
        return dates.contains(day)
    }

    override fun decorate(view: DayViewFacade) {
        val customColor = Color.parseColor("#eb455f") // HEX 코드를 색상 값으로 변환
        view.addSpan(DotSpan(7f, customColor)) // 변환된 색상을 사용하여 점 표기
    }
}

