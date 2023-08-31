package com.gnuoynawh.part5.todo.data.repository

import com.gnuoynawh.part5.todo.data.entity.TodoEntity

/**
 * 1. insertTodoList
 * 2. getTodoList
 * 3. updateTodoItem
 */
interface TodoRepository {

    suspend fun getTotoList(): List<TodoEntity>
    suspend fun insertTodoItem(todoItem: TodoEntity): Long
    suspend fun insertTodoList(todoList: List<TodoEntity>)
    suspend fun updateTodoItem(todoItem: TodoEntity)
    suspend fun getTotoItem(itemId: Long): TodoEntity?
    suspend fun deleteAll()
    suspend fun deleteTodoItem(itemId: Long)
}