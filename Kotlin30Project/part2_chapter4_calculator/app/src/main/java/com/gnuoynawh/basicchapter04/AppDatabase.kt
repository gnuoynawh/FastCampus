package com.gnuoynawh.basicchapter04

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gnuoynawh.basicchapter04.dao.HistoryDao
import com.gnuoynawh.basicchapter04.model.History

@Database(entities = [History::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao

}