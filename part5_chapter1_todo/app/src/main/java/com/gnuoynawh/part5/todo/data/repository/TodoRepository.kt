package com.gnuoynawh.part5.todo.data.repository

import com.gnuoynawh.part5.todo.data.entity.TodoEntity

/**
 * 1. insertTodoList
 * 2. getTodoList
 * 3. updateTodoItem
 */
interface TodoRepository {

    suspend fun getTotoList(): List<TodoEntity>
    suspend fun insertTodoList(todoList: List<TodoEntity>)
    suspend fun updateTodoItem(todoItem: TodoEntity): Boolean
    suspend fun getTotoItem(itemId: Long): TodoEntity?
    suspend fun deleteAll()
}