package com.example.oee

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oee.databinding.ActivityHistoryBinding
import com.google.android.material.datepicker.DayViewDecorator
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

// History화면을 관리하는 클래스
class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarHistoryActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "HISTORY"

        binding.toolbarHistoryActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        setupCalendarView()
    }

    private fun setupCalendarView() {
        val calendarView = binding.calendarView
        val dao = (application as WorkOutApp).db.historyDao()

        lifecycleScope.launch {
            dao.fetchAllDates().collect { allCompletedDatesList ->
                val events = allCompletedDatesList.map { entity ->
                    CalendarDay.from(entity.year, entity.month, entity.day)
                }
                calendarView.addDecorator(EventDecorator(events))
            }
        }

        // 날짜 선택 리스너 추가
        calendarView.setOnDateChangedListener { widget, date, selected ->
            if (selected) {
                val selectedDate = CalendarDay.from(date.year, date.month, date.day)
                showExerciseTimePopup(selectedDate)
            }
        }
    }


    private fun showExerciseTimePopup(selectedDate: CalendarDay) {
        lifecycleScope.launch {
            val exerciseTimes = (application as WorkOutApp).db.historyDao().getExercisesForDate(selectedDate.year, selectedDate.month, selectedDate.day)
            val exerciseTimesString = exerciseTimes.joinToString("\n") { it.date }

            AlertDialog.Builder(this@HistoryActivity, R.style.CustomAlertDialog)
                .setTitle("운동 시간 목록: ${selectedDate.day}/${selectedDate.month}/${selectedDate.year}")
                .setMessage(exerciseTimesString)
                .setPositiveButton("확인", null)
                .show()
        }
    }

}