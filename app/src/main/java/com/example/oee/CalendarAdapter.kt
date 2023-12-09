package com.example.oee

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import java.util.Calendar
import java.util.Date

class CalendarAdapter(
    context: Context,
    private val days: List<Date?>, // Date? 타입의 날짜 목록 (null 포함)
    private val eventDays: Map<Date, Boolean>, // 이벤트가 있는 날짜 정보 (Date 타입의 키)
    private val inputMonth: Int // 입력된 월 (정수 형식)
) : ArrayAdapter<Date?>(context, R.layout.calendar_day_layout, days) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView ?: inflater.inflate(R.layout.calendar_day_layout, parent, false)

        val textView = view.findViewById<TextView>(R.id.dayText)
        val imageView = view.findViewById<ImageView>(R.id.dayIcon)

        val date = getItem(position)
        if (date == null) {
            textView.visibility = View.INVISIBLE
            imageView.visibility = View.INVISIBLE // 빈 칸에는 아이콘 숨김
        } else {
            val calendar = Calendar.getInstance()
            calendar.time = date

            val day = calendar.get(Calendar.DATE)
            val month = calendar.get(Calendar.MONTH)
            val year = calendar.get(Calendar.YEAR)

            textView.visibility = View.VISIBLE
            textView.setTypeface(null, Typeface.NORMAL)
            textView.setTextColor(Color.parseColor("#56a6a9"))

            if (month != inputMonth || year != Calendar.getInstance().get(Calendar.YEAR)) {
                textView.visibility = View.INVISIBLE
                imageView.visibility = View.INVISIBLE
            } else {
                textView.visibility = View.VISIBLE

                // 이벤트 데이터에 따라 텍스트 설정
                if (eventDays[date] == true) { // 운동 기록 있을 때
                    imageView.setImageDrawable(null) // 이미지 없음
                    imageView.visibility = View.VISIBLE
                    textView.text = "v"
                } else { // 운동 기록 없을 때
                    imageView.visibility = View.INVISIBLE
                    textView.text = ""
                }

                textView.text = day.toString()
            }
        }

        return view
    }
}