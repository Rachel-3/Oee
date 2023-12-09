package com.example.oee

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

// Room 데이터베이스를 위한 데이터 액세스 객체 인터페이스
@Dao
interface HistoryDao {
    @Insert
    suspend fun insert(historyEntity: HistoryEntity)

    @Query("SELECT * FROM `history-table`")
    fun fetchAllDates(): Flow<List<HistoryEntity>>

    @Query("SELECT * FROM `history-table` WHERE year = :year AND month = :month AND day = :day")
    suspend fun getExercisesForDate(year: Int, month: Int, day: Int): List<HistoryEntity>
}
