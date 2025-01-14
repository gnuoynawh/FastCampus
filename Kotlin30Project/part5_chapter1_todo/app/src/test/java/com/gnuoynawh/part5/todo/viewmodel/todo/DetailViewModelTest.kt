package com.gnuoynawh.part5.todo.viewmodel.todo

import com.gnuoynawh.part5.todo.ViewModelTest
import com.gnuoynawh.part5.todo.data.entity.TodoEntity
import com.gnuoynawh.part5.todo.domain.todo.InsertTodoItemUseCase
import com.gnuoynawh.part5.todo.presentation.detail.DetailViewModel
import com.gnuoynawh.part5.todo.presentation.detail.DetailMode
import com.gnuoynawh.part5.todo.presentation.detail.TodoDetailState
import com.gnuoynawh.part5.todo.presentation.list.ListViewModel
import com.gnuoynawh.part5.todo.presentation.list.TodoListState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.koin.core.parameter.parametersOf
import org.koin.test.inject
import java.lang.Exception

/**
 * [DetailViewModel] 을 테스트하기 위한 Unit Test Class
 * 1. initData()
 * 2. test viewModel fetch
 * 3. test Update todo
 * 4. test Delete todo
 */
@ExperimentalCoroutinesApi
internal class DetailViewModelTest: ViewModelTest() {

    private val id = 1L
    private val detailViewModel by inject<DetailViewModel>{ parametersOf(DetailMode.DETAIL, id) }
    private val listViewModel: ListViewModel by inject()
    private val insertTodoItemUseCase: InsertTodoItemUseCase by inject()
    private val todo = TodoEntity(
        id = id,
        title = "title $id",
        description = "description = $id",
        hasCompleted = false
    )

    @Before
    fun init() {
        initData()
    }

    private fun initData() = runBlockingTest {
        insertTodoItemUseCase(todo)
    }

    @Test
    fun testViewModelFetch1() = runBlockingTest {
        val testObservable = detailViewModel.todoDetailLiveData.test()
        detailViewModel.fetchData()
        testObservable.assertValueSequence(
            listOf(
                TodoDetailState.UnInitialized,
                TodoDetailState.Loading,
                TodoDetailState.Success(todo)
            )
        )
    }

    @Test
    fun testDeleteTodo1() = runBlockingTest {
        val detailTestObservable = detailViewModel.todoDetailLiveData.test()
        detailViewModel.deleteTodo()

        detailTestObservable.assertValueSequence(
            listOf(
                TodoDetailState.UnInitialized,
                TodoDetailState.Loading,
                TodoDetailState.Delete
            )
        )

        val listTestObservable = listViewModel.todoListLiveData.test()
        listViewModel.fetchData()
        listTestObservable.assertValueSequence(
            listOf(
                TodoListState.UnInitialized,
                TodoListState.Loading,
                TodoListState.Success(listOf())
            )
        )

    }

    @Test
    fun testUpdateTodo1() = runBlockingTest {
        val testObservable = detailViewModel.todoDetailLiveData.test()

        val updateTitle = "title 1 update"
        val updateDescription = "description 1 update"
        val updateTodo = todo.copy(
            title = updateTitle,
            description = updateDescription
        )
        detailViewModel.writeTodo(
            title = updateTitle,
            description = updateDescription
        )
        testObservable.assertValueSequence(
            listOf(
                TodoDetailState.UnInitialized,
                TodoDetailState.Loading,
                TodoDetailState.Success(updateTodo)
            )
        )
    }
}