package com.gnuoynawh.part5.todo.di

import com.gnuoynawh.part5.todo.data.repository.TestTodoRepository
import com.gnuoynawh.part5.todo.data.repository.TodoRepository
import com.gnuoynawh.part5.todo.domain.todo.DeleteAllTodoItemUseCase
import com.gnuoynawh.part5.todo.domain.todo.GetTodoItemUseCase
import com.gnuoynawh.part5.todo.domain.todo.GetTodoListUseCase
import com.gnuoynawh.part5.todo.domain.todo.InsertTodoItemUseCase
import com.gnuoynawh.part5.todo.domain.todo.InsertTodoListUseCase
import com.gnuoynawh.part5.todo.domain.todo.UpdateTodoUseCase
import com.gnuoynawh.part5.todo.presentation.detail.DetailViewModel
import com.gnuoynawh.part5.todo.presentation.detail.DetailMode
import com.gnuoynawh.part5.todo.presentation.list.ListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appTestModule = module {

    //View Model
    viewModel { ListViewModel(get(), get(), get()) }
    viewModel { (detailMode: DetailMode, id: Long) ->
        DetailViewModel(
            detailMode = detailMode,
            id = id,
            get(),
            get(),
            get(),
            get()
        )
    }

    //UseCase
    factory { GetTodoListUseCase(get()) }
    factory { InsertTodoItemUseCase(get()) }
    factory { InsertTodoListUseCase(get()) }
    factory { UpdateTodoUseCase(get()) }
    factory { GetTodoItemUseCase(get()) }
    factory { DeleteAllTodoItemUseCase(get()) }

    //Repository
    single<TodoRepository> { TestTodoRepository() }
}