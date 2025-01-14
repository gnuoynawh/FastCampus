package com.gnuoynawh.part5.todo.domain.todo

import com.gnuoynawh.part5.todo.data.entity.TodoEntity
import com.gnuoynawh.part5.todo.data.repository.TodoRepository
import com.gnuoynawh.part5.todo.domain.UseCase

internal class UpdateTodoUseCase(
    private val todoRepository: TodoRepository
): UseCase {

    suspend operator fun invoke(toDoEntity: TodoEntity) {
        todoRepository.updateTodoItem(toDoEntity)
    }
}