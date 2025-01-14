package com.gnuoynawh.part5.todo.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.gnuoynawh.part5.todo.data.entity.TodoEntity

@Dao
interface TodoDao {

    @Query("SELECT * FROM TodoEntity")
    suspend fun getAll(): List<TodoEntity>

    @Query("SELECT * FROM TodoEntity WHERE id=:id")
    suspend fun getById(id: Long): TodoEntity

    @Insert
    suspend fun insert(todoEntity: TodoEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todoEntityList: List<TodoEntity>)

    @Query("DELETE FROM TodoEntity WHERE id=:id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM TodoEntity")
    suspend fun deleteAll()

    @Update
    suspend fun update(toDoEntity: TodoEntity)

}