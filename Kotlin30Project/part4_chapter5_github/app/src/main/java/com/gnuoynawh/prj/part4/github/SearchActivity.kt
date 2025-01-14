package com.gnuoynawh.prj.part4.github

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import com.gnuoynawh.prj.part4.github.RepositoryActivity.Companion.REPOSITORY_NAME_KEY
import com.gnuoynawh.prj.part4.github.RepositoryActivity.Companion.REPOSITORY_OWNER_KEY
import com.gnuoynawh.prj.part4.github.data.entity.GithubRepoEntity
import com.gnuoynawh.prj.part4.github.databinding.ActivitySearchBinding
import com.gnuoynawh.prj.part4.github.utillity.RetrofitUtil
import com.gnuoynawh.prj.part4.github.view.RepositoryRecyclerAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class SearchActivity: AppCompatActivity(), CoroutineScope {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var adapter: RepositoryRecyclerAdapter

    private val job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()
        initViews()
        bindViews()
    }

    private fun initAdapter() {
        adapter = RepositoryRecyclerAdapter()
    }

    private fun initViews() = with(binding) {
        emptyResultTextView.isGone = true
        recyclerView.adapter = adapter
    }

    private fun bindViews() = with(binding) {
        searchButton.setOnClickListener {
            searchKeyword(searchBarInputView.text.toString())
        }
    }

    private fun searchKeyword(keyword: String) {
        showLoading(true)
        launch(coroutineContext) {
            try {
                withContext(Dispatchers.IO) {
                    var response = RetrofitUtil.githubApiService.searchRepositories(keyword)
                    if (response.isSuccessful) {
                        var body = response.body()
                        withContext(Dispatchers.Main) {
                            body?.let { searchResponse ->
                                setData(searchResponse.items)
                            }
                        }
                    }
                }
            }catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@SearchActivity, "검색하는 과정에서 에러가 발생했습니다. : ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setData(items: List<GithubRepoEntity>) = with(binding) {
        showLoading(false)
        if (items.isEmpty()) {
            emptyResultTextView.isGone = false
            recyclerView.isGone = true
        } else {
            emptyResultTextView.isGone = true
            recyclerView.isGone = false
            adapter.setSearchResultList(items) {
                startActivity(
                    Intent(this@SearchActivity, RepositoryActivity::class.java).apply {
                        putExtra(REPOSITORY_OWNER_KEY, it.owner.login)
                        putExtra(REPOSITORY_NAME_KEY, it.name)
                    }
                )
            }
        }
    }

    private fun showLoading(isShown: Boolean) = with(binding) {
        progressBar.isGone = isShown.not()
    }
}