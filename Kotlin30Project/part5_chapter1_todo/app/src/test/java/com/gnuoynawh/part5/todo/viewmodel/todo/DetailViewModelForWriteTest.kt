package com.gnuoynawh.part5.todo.viewmodel.todo

import com.gnuoynawh.part5.todo.ViewModelTest
import com.gnuoynawh.part5.todo.data.entity.TodoEntity
import com.gnuoynawh.part5.todo.presentation.detail.DetailMode
import com.gnuoynawh.part5.todo.presentation.detail.DetailViewModel
import com.gnuoynawh.part5.todo.presentation.detail.TodoDetailState
import com.gnuoynawh.part5.todo.presentation.list.ListViewModel
import com.gnuoynawh.part5.todo.presentation.list.TodoListState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.koin.core.parameter.parametersOf
import org.koin.test.inject

/**
 * [DetailViewModel] 을 테스트하기 위한 Unit Test Class
 *
 * 1. test viewModel fetch
 * 2. test Insert todo
 */
@ExperimentalCoroutinesApi
internal class DetailViewModelForWriteTest: ViewModelTest() {

    private val id = 1L
    private val detailViewModel by inject<DetailViewModel>{ parametersOf(DetailMode.WRITE, id) }
    private val listViewModel: ListViewModel by inject()
    private val todo = TodoEntity(
        id = id,
        title = "title $id",
        description = "description = $id",
        hasCompleted = false
    )

    @Test
    fun testViewModelFetch2() = runBlockingTest {
        val testObservable = detailViewModel.todoDetailLiveData.test()
        detailViewModel.fetchData()
        testObservable.assertValueSequence(
            listOf(
                TodoDetailState.UnInitialized,
                TodoDetailState.Write
            )
        )
    }

    @Test
    fun testInsertTodo() = runBlockingTest {
        val detailTestObservable = detailViewModel.todoDetailLiveData.test()
        val listTestObservable = listViewModel.todoListLiveData.test()

        detailViewModel.writeTodo(
            title = todo.title,
            description = todo.description
        )
        detailTestObservable.assertValueSequence(
            listOf(
                TodoDetailState.UnInitialized,
                TodoDetailState.Loading,
                TodoDetailState.Success(todo)
            )
        )

        assert(detailViewModel.detailMode == DetailMode.DETAIL)
        assert(detailViewModel.id == id)

        //뒤로 나가서 리스트 보기
        listViewModel.fetchData()
        listTestObservable.assertValueSequence(
            listOf(
                TodoListState.UnInitialized,
                TodoListState.Loading,
                TodoListState.Success(listOf(
                    todo
                ))
            )
        )
    }
}