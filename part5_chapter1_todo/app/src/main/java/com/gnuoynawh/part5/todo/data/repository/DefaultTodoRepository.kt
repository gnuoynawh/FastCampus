package com.gnuoynawh.part5.todo.data.repository

import com.gnuoynawh.part5.todo.data.entity.TodoEntity

class DefaultTodoRepository: TodoRepository {

    override suspend fun getTotoList(): List<TodoEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun insertTodoList(todoList: List<TodoEntity>) {
        TODO("Not yet implemented")
    }

    override suspend fun updateTodoItem(todoItem: TodoEntity): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getTotoItem(itemId: Long): TodoEntity? {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }

}