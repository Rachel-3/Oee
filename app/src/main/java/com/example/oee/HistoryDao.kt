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

    // 모든 운동 기록을 가져오는 쿼리 함수
    @Query("Select * from `history-table`")
    fun fetchALlDates():Flow<List<HistoryEntity>>
}