package com.gnuoynawh.part5.todo.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gnuoynawh.part5.todo.data.entity.TodoEntity
import com.gnuoynawh.part5.todo.domain.todo.DeleteTodoItemUseCase
import com.gnuoynawh.part5.todo.domain.todo.GetTodoItemUseCase
import com.gnuoynawh.part5.todo.domain.todo.InsertTodoItemUseCase
import com.gnuoynawh.part5.todo.domain.todo.UpdateTodoUseCase
import com.gnuoynawh.part5.todo.presentation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

internal class DetailViewModel(
    var detailMode: DetailMode,
    var id: Long = -1,
    private val getTodoItemUseCase: GetTodoItemUseCase,
    private val deleteTodoItemUseCase: DeleteTodoItemUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase,
    private val insertTodoItemUseCase: InsertTodoItemUseCase
): BaseViewModel() {

    private var _todoDetailLiveData = MutableLiveData<TodoDetailState>(TodoDetailState.UnInitialized)
    var todoDetailLiveData: LiveData<TodoDetailState> = _todoDetailLiveData

    override fun fetchData(): Job = viewModelScope.launch {
        when(detailMode) {
            DetailMode.WRITE -> {
                _todoDetailLiveData.postValue(TodoDetailState.Write)
            }
            DetailMode.DETAIL -> {
                _todoDetailLiveData.postValue(TodoDetailState.Loading)
                try {
                    getTodoItemUseCase(id)?.let {
                        _todoDetailLiveData.postValue(TodoDetailState.Success(it))
                    } ?: kotlin.run {
                        _todoDetailLiveData.postValue(TodoDetailState.Error)
                    }
                }catch (e: Exception) {
                    e.printStackTrace()
                    _todoDetailLiveData.postValue(TodoDetailState.Error)
                }
            }
        }
    }

    fun setModifyMode() = viewModelScope.launch {
        _todoDetailLiveData.postValue(TodoDetailState.Modify)
    }

    fun deleteTodo() = viewModelScope.launch {
        _todoDetailLiveData.postValue(TodoDetailState.Loading)
        try {
            deleteTodoItemUseCase(id)
            _todoDetailLiveData.postValue(TodoDetailState.Delete)
//            if (deleteTodoItemUseCase(id)) {
//            } else {
//                _todoDetailLiveData.postValue(TodoDetailState.Error)
//            }
        }catch (e: Exception) {
            e.printStackTrace()
            _todoDetailLiveData.postValue(TodoDetailState.Error)
        }
    }

    fun writeTodo(title: String, description: String) = viewModelScope.launch {
        when(detailMode) {
            DetailMode.WRITE -> {
                try {
                    val todoEntity = TodoEntity(
                        title = title,
                        description = description
                    )
                    id = insertTodoItemUseCase(todoEntity)
                    _todoDetailLiveData.postValue(TodoDetailState.Success(todoEntity))
                    detailMode = DetailMode.DETAIL
                }catch (e: Exception) {
                    e.printStackTrace()
                    _todoDetailLiveData.postValue(TodoDetailState.Error)
                }
            }
            DetailMode.DETAIL -> {
                try {
                    getTodoItemUseCase(id)?.let {
                        val updateTodoEntity = it.copy(
                            title = title,
                            description = description
                        )
                        updateTodoUseCase(updateTodoEntity)
                        _todoDetailLiveData.postValue(TodoDetailState.Success(updateTodoEntity))
                    } ?: kotlin.run {
                        _todoDetailLiveData.postValue(TodoDetailState.Error)
                    }
                }catch (e: Exception) {
                    e.printStackTrace()
                    _todoDetailLiveData.postValue(TodoDetailState.Error)
                }
            }
        }
    }
}