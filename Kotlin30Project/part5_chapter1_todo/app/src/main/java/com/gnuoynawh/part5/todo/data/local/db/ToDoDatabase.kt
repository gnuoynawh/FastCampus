package com.gnuoynawh.part5.todo.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gnuoynawh.part5.todo.data.entity.TodoEntity
import com.gnuoynawh.part5.todo.data.local.db.dao.TodoDao

@Database(
    entities = [TodoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ToDoDatabase: RoomDatabase() {

    companion object {
        const val DB_NAME = "TodoDatabase.db"
    }

    abstract fun todoDao(): TodoDao
}