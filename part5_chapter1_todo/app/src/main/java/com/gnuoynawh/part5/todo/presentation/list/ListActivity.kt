package com.gnuoynawh.part5.todo.presentation.list

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import com.gnuoynawh.part5.todo.R
import com.gnuoynawh.part5.todo.databinding.ActivityListBinding
import com.gnuoynawh.part5.todo.presentation.BaseActivity
import com.gnuoynawh.part5.todo.presentation.detail.DetailActivity
import com.gnuoynawh.part5.todo.presentation.detail.DetailMode
import com.gnuoynawh.part5.todo.presentation.view.ToDoAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class ListActivity: BaseActivity<ListViewModel>(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

    private lateinit var binding: ActivityListBinding
    private val adapter = ToDoAdapter()

    override val viewModel: ListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initView(binding: ActivityListBinding) = with(binding) {
        recyclerView.layoutManager = LinearLayoutManager(this@ListActivity, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        refreshLayout.setOnRefreshListener {
            viewModel.fetchData()
        }

        addToDoButton.setOnClickListener {
            startActivityForResult(
                DetailActivity.getIntent(this@ListActivity, DetailMode.WRITE),
                DetailActivity.FETCH_REQUEST_CODE
            )
        }
    }

    override fun observeData() {
        viewModel.todoListLiveData.observe(this) {
            when(it) {
                is TodoListState.UnInitialized -> {
                    initView(binding)
                }
                is TodoListState.Loading -> {
                    handleLoadingState()
                }
                is TodoListState.Success -> {
                    handleSuccessState(it)
                }
                is TodoListState.Error -> {
                    handleError()
                }
            }
        }
    }

    private fun handleLoadingState() = with(binding) {
        refreshLayout.isRefreshing = true
    }

    private fun handleSuccessState(state: TodoListState.Success) = with(binding) {
        refreshLayout.isEnabled = state.toDoList.isNotEmpty()
        refreshLayout.isRefreshing = false

        if (state.toDoList.isEmpty()) {
            emptyResultTextView.isGone = false
            recyclerView.isGone = true
        } else {
            emptyResultTextView.isGone = true
            recyclerView.isGone = false
            adapter.setToDoList(
                state.toDoList,
                toDoItemClickListener = {
                    startActivityForResult(
                        DetailActivity.getIntent(this@ListActivity, it.id, DetailMode.DETAIL),
                        DetailActivity.FETCH_REQUEST_CODE
                    )
                }, toDoCheckListener = {
                    viewModel.updateEntity(it)
                }
            )
        }
    }

    private fun handleError() {
        Toast.makeText(this, "에러가 발생했습니다.", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.list_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_delete_all -> {
                viewModel.deleteAll()
                true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == DetailActivity.FETCH_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            viewModel.fetchData()
        }
    }
}