package com.gnuoynawh.part5.todo.viewmodel.todo

import com.gnuoynawh.part5.todo.ViewModelTest
import com.gnuoynawh.part5.todo.data.entity.TodoEntity
import com.gnuoynawh.part5.todo.domain.todo.GetTodoItemUseCase
import com.gnuoynawh.part5.todo.domain.todo.InsertTodoListUseCase
import com.gnuoynawh.part5.todo.presentation.list.ListViewModel
import com.gnuoynawh.part5.todo.presentation.list.TodoListState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.koin.test.inject
import kotlin.test.Test

/**
 * ListViewModel 을 테스트하기 위한 Unit Test Class
 * 1. initData()
 * 2. test viewModel fetch
 * 3. test Item Update
 * 4. test Item Delete All
 *
 */
@ExperimentalCoroutinesApi
internal class ListViewModelTest: ViewModelTest() {

    private val viewModel: ListViewModel by inject()
    private val insertTodoListUseCase: InsertTodoListUseCase by inject()
    private val getTodoItemUseCase: GetTodoItemUseCase by inject()
    private val mockList = (0 until 10).map {
        TodoEntity(
            id = it.toLong(),
            title = "title $it",
            description = "description = $it",
            hasCompleted = false
        )
    }

    /**
     * 필요한 UseCase
     * 1. InsertTodoListUseCase
     * 2. GetTodoItemUseCase
     */
    @Before
    fun init() {
        initData()
    }

    private fun initData() = runBlockingTest {
        insertTodoListUseCase(mockList)
    }

    //Test 입력된 데이터를 불러와서 검증한다.
    @Test
    fun testViewModelFetch(): Unit = runBlockingTest {
        val testObservable = viewModel.todoListLiveData.test()
        viewModel.fetchData()
        testObservable.assertValueSequence(
            listOf(
                TodoListState.UnInitialized,
                TodoListState.Loading,
                TodoListState.Success(mockList)
            )
        )
    }

    // Test: 데이터를 업데이트 했을 때 잘 반영되는가
    @Test
    fun testItemUpdate() : Unit = runBlockingTest {
        val todo = TodoEntity(
            id = 1,
            title = "title 1",
            description = "description = 1",
            hasCompleted = true
        )
        viewModel.updateEntity(todo)
        assert(getTodoItemUseCase(todo.id)?.hasCompleted ?: false == todo.hasCompleted)
    }

    // Test: 데이터를 다 날렸을 때 빈상태로 보여지는가
    @Test
    fun testItemDeleteAll(): Unit = runBlockingTest {
        val testObservable = viewModel.todoListLiveData.test()
        viewModel.deleteAll()
        testObservable.assertValueSequence(
            listOf(
                TodoListState.UnInitialized,
                TodoListState.Loading,
                TodoListState.Success(listOf())
            )
        )
    }

}