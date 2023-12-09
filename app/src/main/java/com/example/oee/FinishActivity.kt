package com.example.oee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.oee.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {
    private var binding: ActivityFinishBinding? = null
    private val eventDays: MutableMap<Date, Boolean> = mutableMapOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarFinishActivity)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarFinishActivity?.setNavigationOnClickListener {
            onBackPressed()
        }
        binding?.btnFinish?.setOnClickListener {
            finish()
        }


        val dao = (application as WorkOutApp).db.historyDao()
        addDateToDatabase(dao)
    }

    private fun addDateToDatabase(historyDao: HistoryDao) {
        val c = Calendar.getInstance()
        val dateTime = c.time
        Log.e("Date : ", "" + dateTime)

        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        val date = sdf.format(dateTime)
        Log.e("Formatted Date : ", "" + date)

        lifecycleScope.launch {
            historyDao.insert(HistoryEntity(date))
            Log.e("Date : ", "Added...")

            // 운동 기록을 추가한 날짜에 대해 eventDays 맵 업데이트
            val workoutDate = c.clone() as Calendar
            workoutDate.set(Calendar.HOUR_OF_DAY, 0)
            workoutDate.set(Calendar.MINUTE, 0)
            workoutDate.set(Calendar.SECOND, 0)
            workoutDate.set(Calendar.MILLISECOND, 0)

            // eventDays 맵에서 해당 날짜에 대한 값 업데이트
            eventDays[workoutDate.time] = true
            Log.e("eventDays", "Updated...")
        }
    }
}