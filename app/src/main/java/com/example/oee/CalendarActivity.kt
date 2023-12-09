package com.example.oee

import HistoryFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import android.widget.GridView
import android.widget.ImageButton
import android.widget.TextView
import java.text.SimpleDateFormat

class CalendarActivity : AppCompatActivity() {

    private lateinit var calendar: Calendar

    private fun getDaysOfMonth(): List<Date?> {
        val days = mutableListOf<Date?>()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK)

        for (i in 1 until firstDayOfMonth) {
            days.add(null)
        }

        val maxDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        for (day in 1..maxDayOfMonth) {
            val date = Calendar.getInstance()
            date.set(year, month, day)
            days.add(date.time)
        }

        return days
    }

    private fun updateMonthYearDisplay(tvMonthYear: TextView) {
        val monthYearFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        tvMonthYear.text = monthYearFormat.format(calendar.time)
    }

    private fun updateCalendarGrid(gridView: GridView) {
        val daysOfMonth = getDaysOfMonth()

        // 여기에 운동 기록 데이터를 추가하거나 수정하세요
        val eventDays = mutableMapOf<Date, Boolean>().apply {
            // 예시: 이벤트 데이터
            this[SimpleDateFormat("yyyy-MM-dd").parse("2023-12-10")] = true
            this[SimpleDateFormat("yyyy-MM-dd").parse("2023-12-07")] = false
        }

        val inputMonth = calendar.get(Calendar.MONTH)
        val adapter = CalendarAdapter(this, daysOfMonth, eventDays, inputMonth)
        gridView.adapter = adapter

        gridView.setOnItemClickListener { _, _, position, _ ->
            val selectedDate = daysOfMonth[position]

            val historyFragment = HistoryFragment().apply {
                arguments = Bundle().apply {
                    putString("selectedDate", SimpleDateFormat("dd MMM yyyy HH:mm:ss").format(selectedDate))
                }
            }
            historyFragment.show(supportFragmentManager, "historyFragment")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calendar)

        calendar = Calendar.getInstance()
        val gridView: GridView = findViewById(R.id.calendar_grid)
        val tvMonthYear: TextView = findViewById(R.id.tv_month_year)
        val btnPrevMonth: ImageButton = findViewById(R.id.btn_prev_month)
        val btnNextMonth: ImageButton = findViewById(R.id.btn_next_month)

        updateMonthYearDisplay(tvMonthYear)

        btnPrevMonth.setOnClickListener {
            calendar.add(Calendar.MONTH, -1)
            updateCalendarGrid(gridView)
            updateMonthYearDisplay(tvMonthYear)
        }

        btnNextMonth.setOnClickListener {
            calendar.add(Calendar.MONTH, 1)
            updateCalendarGrid(gridView)
            updateMonthYearDisplay(tvMonthYear)
        }

        updateCalendarGrid(gridView)
    }
}
