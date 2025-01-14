package com.gnuoynawh.part5.todo.presentation.list

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gnuoynawh.part5.todo.data.entity.TodoEntity
import com.gnuoynawh.part5.todo.domain.todo.DeleteAllTodoItemUseCase
import com.gnuoynawh.part5.todo.domain.todo.GetTodoListUseCase
import com.gnuoynawh.part5.todo.domain.todo.InsertTodoListUseCase
import com.gnuoynawh.part5.todo.domain.todo.UpdateTodoUseCase
import com.gnuoynawh.part5.todo.presentation.BaseViewModel
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
//    ,
//    private val insertToDoListUseCase: InsertTodoListUseCase
): BaseViewModel() {

    private val _toDoListLiveData = MutableLiveData<TodoListState>(TodoListState.UnInitialized)
    val todoListLiveData: LiveData<TodoListState> = _toDoListLiveData

    override fun fetchData(): Job = viewModelScope.launch {
        _toDoListLiveData.postValue(TodoListState.Loading)
//        insertToDoListUseCase(
//            (0 until 10).map {
//                TodoEntity(
//                    id = it.toLong(),
//                    title = "title $it",
//                    description = "description = $it",
//                    hasCompleted = false
//                )
//            }
//        )
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