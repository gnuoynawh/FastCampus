package com.gnuoynawh.part5.todo.presentation.list

import com.gnuoynawh.part5.todo.data.entity.TodoEntity

sealed class TodoListState {
    object UnInitialized: TodoListState()
    object Loading: TodoListState()
    data class Success(
        val toDoList: List<TodoEntity>
    ): TodoListState()
    object Error: TodoListState()
}