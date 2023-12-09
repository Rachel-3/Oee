package com.example.oee

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

// Room 데이터베이스를 위한 데이터 액세스 객체 인터페이스
@Dao
interface HistoryDao {

    // 운동 기록을 데이터베이스에 삽입하는 함수
    @Insert
    suspend fun insert(historyEntity: HistoryEntity)

    // 선택한 날짜만 가져오게 변경
    @Query("SELECT * FROM `history-table` WHERE SUBSTR(date, 1, 11) = SUBSTR(:date, 1, 11)")
    fun fetchHistoryByDate(date: String): Flow<List<HistoryEntity>>
}