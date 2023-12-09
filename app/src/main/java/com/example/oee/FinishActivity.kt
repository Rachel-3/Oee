package com.example.oee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.oee.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

// 사용자가 운동을 완료한 후 마무리 화면을 관리하는 액티비티
class FinishActivity : AppCompatActivity() {
    private var binding: ActivityFinishBinding? = null // 뷰 바인딩 객체

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater) // 레이아웃 인플레이터
        setContentView(binding?.root) // 레이아웃 설정
        setSupportActionBar(binding?.toolbarFinishActivity) // 툴바 설정
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true) // 뒤로 가기 버튼 활성화
        }
        binding?.toolbarFinishActivity?.setNavigationOnClickListener {
            onBackPressed() // 뒤로 가기 이벤트 처리
        }
        binding?.btnFinish?.setOnClickListener {
            finish() // 액티비티 종료 이벤트 처리
        }

        // 데이터베이스 액세스 객체
        val dao = (application as WorkOutApp).db.historyDao()
        addDateToDatabase(dao) // 데이터베이스에 날짜 추가
    }

    // 데이터베이스에 날짜를 추가하는 함수
    private fun addDateToDatabase(historyDao: HistoryDao) {
        val c = Calendar.getInstance() // 캘린더 인스턴스
        val dateTime = c.time // 현재 시간 및 날짜
        Log.e("Date : ", "" + dateTime) // 로그 출력

        // 날짜 및 시간 포맷
        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        val date = sdf.format(dateTime) // 형식화된 날짜 문자열
        Log.e("Formatted Date : ", "" + date) // 로그 출력

        // 코루틴을 사용하여 데이터베이스에 날짜 추가
        lifecycleScope.launch {
            historyDao.insert(HistoryEntity(date)) // 날짜 엔티티 삽입
            Log.e("Date : ", "Added...") // 로그 출력
        }
    }
}