package com.gnuoynawh.part5.todo.data.repository

import com.gnuoynawh.part5.todo.data.entity.TodoEntity
import com.gnuoynawh.part5.todo.data.local.db.dao.TodoDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DefaultTodoRepository(
    private val todoDao: TodoDao,
    private val ioDispatcher: CoroutineDispatcher
): TodoRepository {

    override suspend fun getTotoList(): List<TodoEntity> = withContext(ioDispatcher) {
        todoDao.getAll()
    }

    override suspend fun getTotoItem(itemId: Long): TodoEntity?  = withContext(ioDispatcher) {
        todoDao.getById(itemId)
    }

    override suspend fun insertTodoItem(todoItem: TodoEntity) : Long = withContext(ioDispatcher) {
        todoDao.insert(todoItem)
    }

    override suspend fun insertTodoList(todoList: List<TodoEntity>)  = withContext(ioDispatcher) {
        todoDao.insert(todoList)
    }

    override suspend fun updateTodoItem(todoItem: TodoEntity) = withContext(ioDispatcher) {
        todoDao.update(todoItem)
    }

    override suspend fun deleteAll()  = withContext(ioDispatcher) {
        todoDao.deleteAll()
    }

    override suspend fun deleteTodoItem(itemId: Long)  = withContext(ioDispatcher) {
        todoDao.delete(itemId)
    }

}