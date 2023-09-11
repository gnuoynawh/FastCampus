package com.gnuoynawh.prj.part4.github

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import com.gnuoynawh.prj.part4.github.data.database.DataBaseProvider
import com.gnuoynawh.prj.part4.github.data.entity.GithubRepoEntity
import com.gnuoynawh.prj.part4.github.databinding.ActivityMainBinding
import com.gnuoynawh.prj.part4.github.view.RepositoryRecyclerAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class MainActivity: AppCompatActivity(), CoroutineScope {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: RepositoryRecyclerAdapter

    private val repositoryDao by lazy {
        DataBaseProvider.provideDB(application).repositoryDao()
    }
    private val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        launch {
//            addMockData()
//            val githubRepositories = loadGithubRepositories()
//            withContext(coroutineContext) {
//                Log.e("TEST",githubRepositories.toString())
//            }
//        }

        initAdapter()
        initViews()
    }

    private fun initAdapter() {
        adapter = RepositoryRecyclerAdapter()
    }

    private fun initViews() = with(binding) {
        emptyResultTextView.isGone = true
        recyclerView.adapter = adapter
        searchButton.setOnClickListener {
            startActivity(
                Intent(this@MainActivity, SearchActivity::class.java)
            )
        }
    }

    override fun onResume() {
        super.onResume()
        launch(coroutineContext) {
            loadRepositories()
        }
    }

    private suspend fun loadRepositories() = withContext(Dispatchers.IO) {
        val repositories = repositoryDao.getHistory()
        withContext(Dispatchers.Main) {
            setData(repositories)
        }
    }

    private fun setData(githubRepoList: List<GithubRepoEntity>) = with(binding) {
        if (githubRepoList.isEmpty()) {
            emptyResultTextView.isGone = false
            recyclerView.isGone = true
        } else {
            emptyResultTextView.isGone = true
            recyclerView.isGone = false
            adapter.setSearchResultList(githubRepoList) {
                startActivity(
                    Intent(this@MainActivity, RepositoryActivity::class.java).apply {
                        putExtra(RepositoryActivity.REPOSITORY_OWNER_KEY, it.owner.login)
                        putExtra(RepositoryActivity.REPOSITORY_NAME_KEY, it.name)
                    }
                )
            }
        }
    }
//    private suspend fun addMockData() = withContext(Dispatchers.IO) {
//        val mockData = (0 until 10).map {
//            GithubRepoEntity(
//                name = "repo $it",
//                fullName = "name $it",
//                owner = GithubOwner(
//                    "login",
//                    "avatarUrl"
//                ),
//                description = null,
//                language = null,
//                updatedAt = Date().toString(),
//                stargazersCount = it
//            )
//        }
//        repositoryDao.insertAll(mockData)
//    }

}