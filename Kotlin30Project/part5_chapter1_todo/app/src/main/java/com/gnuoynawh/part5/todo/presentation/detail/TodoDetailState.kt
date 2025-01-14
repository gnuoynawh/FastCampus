package com.gnuoynawh.part5.todo.presentation.detail

import com.gnuoynawh.part5.todo.data.entity.TodoEntity

sealed class TodoDetailState {
    object UnInitialized: TodoDetailState()
    object Loading: TodoDetailState()
    data class Success(
        val toDoItem: TodoEntity
    ): TodoDetailState()
    object Delete: TodoDetailState()
    object Modify: TodoDetailState()
    object Error: TodoDetailState()
    object Write: TodoDetailState()
}