package com.gnuoynawh.part5.todo.di

import android.content.Context
import androidx.room.Room
import com.gnuoynawh.part5.todo.data.local.db.ToDoDatabase
import com.gnuoynawh.part5.todo.data.repository.DefaultTodoRepository
import com.gnuoynawh.part5.todo.data.repository.TodoRepository
import com.gnuoynawh.part5.todo.domain.todo.DeleteAllTodoItemUseCase
import com.gnuoynawh.part5.todo.domain.todo.DeleteTodoItemUseCase
import com.gnuoynawh.part5.todo.domain.todo.GetTodoItemUseCase
import com.gnuoynawh.part5.todo.domain.todo.GetTodoListUseCase
import com.gnuoynawh.part5.todo.domain.todo.InsertTodoItemUseCase
import com.gnuoynawh.part5.todo.domain.todo.InsertTodoListUseCase
import com.gnuoynawh.part5.todo.domain.todo.UpdateTodoUseCase
import com.gnuoynawh.part5.todo.presentation.detail.DetailMode
import com.gnuoynawh.part5.todo.presentation.detail.DetailViewModel
import com.gnuoynawh.part5.todo.presentation.list.ListViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appModule = module {

    single { Dispatchers.Main }
    single { Dispatchers.IO }

    //View Model
    viewModel { ListViewModel(get(), get(), get()) }
    viewModel { (detailMode: DetailMode, id: Long) ->
        DetailViewModel(detailMode, id, get(), get(), get(), get()) }

    //UseCase
    factory { GetTodoListUseCase(get()) }
    factory { InsertTodoItemUseCase(get()) }
    factory { InsertTodoListUseCase(get()) }
    factory { UpdateTodoUseCase(get()) }
    factory { GetTodoItemUseCase(get()) }
    factory { DeleteAllTodoItemUseCase(get()) }
    factory { DeleteTodoItemUseCase(get()) }

    //Repository
    single<TodoRepository> { DefaultTodoRepository(get(), get()) }
    single { provideDB(androidApplication()) }
    single { provideTodoDao(get()) }
}

internal fun provideDB(context: Context): ToDoDatabase =
    Room.databaseBuilder(context, ToDoDatabase::class.java, ToDoDatabase.DB_NAME).build()

internal fun provideTodoDao(database: ToDoDatabase) = database.todoDao()