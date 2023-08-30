package com.gnuoynawh.part5.todo.data.repository

import com.gnuoynawh.part5.todo.data.entity.TodoEntity

class TestTodoRepository: TodoRepository {

    private val todoList: MutableList<TodoEntity> = mutableListOf()

    override suspend fun getTotoList(): List<TodoEntity> {
        return todoList
    }

    override suspend fun insertTodoList(todoList: List<TodoEntity>) {
        this.todoList.addAll(todoList)
    }

    override suspend fun updateTodoItem(todoItem: TodoEntity): Boolean {
        val foundTodoEntity = this.todoList.find { it.id == todoItem.id }
        return if (foundTodoEntity == null) {
            false
        } else {
            this.todoList[todoList.indexOf(foundTodoEntity)] = todoItem
            true
        }
    }

    override suspend fun getTotoItem(itemId: Long): TodoEntity? {
        return this.todoList.find { it.id == itemId }
    }

    override suspend fun deleteAll() {
        todoList.clear()
    }

}