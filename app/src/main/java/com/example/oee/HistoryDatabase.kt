package com.example.oee

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// 운동 기록을 저장하기 위한 Room 데이터베이스 클래스
@Database(entities = [HistoryEntity::class],version = 1)
abstract class HistoryDatabase:RoomDatabase(){

    // HistoryDao 인터페이스에 대한 추상 함수
    abstract fun historyDao(): HistoryDao

    companion object {
        // 데이터베이스 인스턴스를 단일 인스턴스로 유지하기 위한 변수
        @Volatile
        private var INSTANCE: HistoryDatabase? = null

        // 데이터베이스 인스턴스를 가져오거나 생성하는 함수
        fun getInstance(context: Context): HistoryDatabase {

            synchronized(this) {

                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        HistoryDatabase::class.java,
                        "history_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }

}