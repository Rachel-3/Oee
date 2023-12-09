package com.example.oee

import androidx.room.Entity
import androidx.room.PrimaryKey

// 운동 기록을 저장하는 Room 데이터베이스의 엔티티 클래스
@Entity(tableName = "history-table")
data class HistoryEntity(
    // 기록 날짜를 기본 키로 사용
    @PrimaryKey
    val date:String
)