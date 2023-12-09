package com.example.oee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oee.databinding.ActivityHistoryBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

// History화면을 관리하는 클래스
class HistoryActivity : AppCompatActivity() {

    // 뷰 바인딩 객체
    private var binding: ActivityHistoryBinding? = null

    // 액티비티가 생성될 때 호출되는 함수
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 레이아웃 인플레이터를 사용하여 뷰 바인딩 초기화
        binding = ActivityHistoryBinding.inflate(layoutInflater)

        // 생성된 뷰를 화면에 표시
        setContentView(binding?.root)

        // 툴바 설정
        setSupportActionBar(binding?.toolbarHistoryActivity)

        // 액션바 설정: 뒤로 가기 버튼과 제목 설정
        val actionbar = supportActionBar
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.title = "HISTORY"
        }

        // 툴바의 뒤로 가기 버튼에 리스너 설정
        binding?.toolbarHistoryActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

        // 데이터베이스 접근 객체
        val dao = (application as WorkOutApp).db.historyDao()

        // 완료된 운동 날짜 가져오기
        getAllCompletedDates(dao)
    }

    // 완료된 운동 날짜를 모두 가져오는 함수
    private fun getAllCompletedDates(historyDao: HistoryDao) {
        lifecycleScope.launch {
            historyDao.fetchALlDates().collect { allCompletedDatesList ->

                // 데이터가 있을 경우 뷰 설정
                if (allCompletedDatesList.isNotEmpty()) {

                    // 텍스트와 리사이클러뷰 보이기 설정
                    binding?.tvHistory?.visibility = View.VISIBLE
                    binding?.rvHistory?.visibility = View.VISIBLE
                    binding?.tvNoDataAvailable?.visibility = View.GONE

                    // 리사이클러뷰의 레이아웃 매니저 설정
                    binding?.rvHistory?.layoutManager = LinearLayoutManager(this@HistoryActivity)

                    // 날짜 데이터 리스트
                    val dates = ArrayList<String>()
                    for (date in allCompletedDatesList){
                        dates.add(date.date)
                    }
                    // 리사이클러뷰 어댑터 설정
                    val historyAdapter = HistoryAdapter(ArrayList(dates))

                    // 리사이클러뷰에 어댑터 연결
                    binding?.rvHistory?.adapter = historyAdapter
                } else {
                    // 데이터가 없을 경우 뷰 숨김 설정
                    binding?.tvHistory?.visibility = View.GONE
                    binding?.rvHistory?.visibility = View.GONE
                    binding?.tvNoDataAvailable?.visibility = View.VISIBLE
                }
            }
        }
    }

    // 액티비티 소멸시 호출되는 함수
    override fun onDestroy() {
        super.onDestroy()

        // 뷰 바인딩 해제
        binding = null
    }
}