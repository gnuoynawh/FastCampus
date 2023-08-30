package com.gnuoynawh.part5.todo.presentation.list

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gnuoynawh.part5.todo.data.entity.TodoEntity
import com.gnuoynawh.part5.todo.domain.todo.DeleteAllTodoItemUseCase
import com.gnuoynawh.part5.todo.domain.todo.GetTodoListUseCase
import com.gnuoynawh.part5.todo.domain.todo.UpdateTodoUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * 필요한 UseCase
 * 1. [GetTodoListUseCase]
 * 2. [UpdateTodoUseCase]
 * 3. [DeleteAllTodoItemUsecase]
 */
internal class ListViewModel(
    private val getTodoListUseCase: GetTodoListUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase,
    private val deleteAllTodoItemUseCase: DeleteAllTodoItemUseCase
): ViewModel() {

    private val _toDoListLiveData = MutableLiveData<TodoListState>(TodoListState.UnInitialized)
    val todoListLiveData: LiveData<TodoListState> = _toDoListLiveData

    fun fetchData(): Job = viewModelScope.launch {
        _toDoListLiveData.postValue(TodoListState.Loading)
        _toDoListLiveData.postValue(TodoListState.Success(getTodoListUseCase()))
    }

    fun updateEntity(todoEntity: TodoEntity) = viewModelScope.launch {
        updateTodoUseCase(todoEntity)
    }

    fun deleteAll() = viewModelScope.launch {
        _toDoListLiveData.postValue(TodoListState.Loading)
        deleteAllTodoItemUseCase()
        _toDoListLiveData.postValue(TodoListState.Success(getTodoListUseCase()))
    }
}