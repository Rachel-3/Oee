package com.example.oee;

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import com.example.oee.databinding.ActivityMainBinding

// 애플리케이션의 메인 액티비티
class MainActivity : AppCompatActivity() {

    // 뷰 바인딩 객체
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // 운동 시작 버튼 클릭 이벤트 리스너
        binding?.flStart?.setOnClickListener {
            val intent = Intent(this, ExerciseActivity::class.java)
            startActivity(intent)
        }

        // BMI 계산 버튼 클릭 이벤트 리스너
        binding?.flBMI?.setOnClickListener {
            val intent = Intent(this, BMIActivity::class.java)
            startActivity(intent)
        }

        // 운동 기록 버튼 클릭 이벤트 리스너
        binding?.flHistory?.setOnClickListener {
            val intent = Intent(this, CalendarActivity::class.java)
            startActivity(intent)
        }
    }

    // 액티비티 파괴 시 실행되는 함수
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}