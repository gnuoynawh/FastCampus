package com.gnuoynawh.basicchapter04.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.gnuoynawh.basicchapter04.model.History

@Dao
interface HistoryDao {

    @Query(value = "SELECT * FROM history")
    fun getAll(): List<History>

    @Insert
    fun insertHistory(history: History)

    @Query(value = "DELETE FROM history")
    fun deleteAll()

}