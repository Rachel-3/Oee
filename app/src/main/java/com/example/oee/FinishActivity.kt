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
        val c = Calendar.getInstance() // 캘린더 인스턴스
        val year = c.get(Calendar.YEAR) // 현재 연도
        val month = c.get(Calendar.MONTH) + 1 // 현재 월 (Calendar.MONTH는 0부터 시작하므로 1을 더해줌)
        val day = c.get(Calendar.DAY_OF_MONTH) // 현재 일
        val dateTime = c.time // 현재 시간 및 날짜
        Log.e("Date : ", "" + dateTime)

        // 날짜 및 시간 포맷
        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        val date = sdf.format(dateTime) // 형식화된 날짜 문자열
        Log.e("Formatted Date : ", "" + date)

        // 코루틴을 사용하여 데이터베이스에 날짜 추가
        lifecycleScope.launch {
            historyDao.insert(
                HistoryEntity(
                    year = year,
                    month = month,
                    day = day,
                    date = date
                )
            ) // 수정된 부분
            Log.e("Date : ", "Added...")
        }
    }

}