package com.example.oee;

import android.app.Application

// 전체 애플리케이션에 걸쳐 사용되는 글로벌 애플리케이션 클래스
class WorkOutApp: Application() {

    // 지연 초기화를 사용하여 HistoryDatabase의 인스턴스를 생성
    // 애플리케이션 컨텍스트가 필요할 때만 HistoryDatabase의 인스턴스를 생성
    val db: HistoryDatabase by lazy {
        HistoryDatabase.getInstance(this)
    }
}