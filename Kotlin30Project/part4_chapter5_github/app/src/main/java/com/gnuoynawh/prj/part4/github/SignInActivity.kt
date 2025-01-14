package com.gnuoynawh.prj.part4.github

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.view.isGone
import com.gnuoynawh.prj.part4.github.databinding.ActivitySignInBinding
import com.gnuoynawh.prj.part4.github.utility.AuthTokenProvider
import com.gnuoynawh.prj.part4.github.utillity.RetrofitUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class SignInActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var binding: ActivitySignInBinding

    private val authTokenProvider by lazy {
        AuthTokenProvider(this)
    }

    private val job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.e("TEST", "checkAuthCodeExist() = ${checkAuthCodeExist()}")
        if (checkAuthCodeExist()) {
            launchMainActivity()
        } else {
            initViews()
        }
    }

    private fun initViews() {
        binding.loginButton.setOnClickListener {
            loginGithub()
        }
    }

    private fun launchMainActivity() {
        Log.e("TEST", "launchMainActivity()")
        startActivity(Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        })
    }

    private fun checkAuthCodeExist(): Boolean = authTokenProvider.token.isNullOrEmpty().not()

    private fun loginGithub() {
        Log.e("TEST", "loginGithub")
        val loginUrl = Uri.Builder().scheme("https").authority("github.com")
            .appendPath("login")
            .appendPath("oauth")
            .appendPath("authorize")
            .appendQueryParameter("client_id", BuildConfig.GITHUB_CLIENT_ID)
            .build()

//        CustomTabsIntent.Builder().build().also {
//            it.launchUrl(this, loginUrl)
//        }
        val browserInt = Intent(Intent.ACTION_VIEW, loginUrl)
        browserInt.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(browserInt)

    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        Log.e("TEST", "onNewIntent = $intent")
        intent.data?.getQueryParameter("code")?.let { code ->
            GlobalScope.launch {
                showProgress()
                val progressJob = getAccessToken(code)
                progressJob.join()
                dismissProgress()
                if (checkAuthCodeExist()) {
                    launchMainActivity()
                }
            }
        }
    }

    private suspend fun showProgress() = GlobalScope.launch {
        Log.e("TEST", "showProgress")
        withContext(Dispatchers.Main) {
            with(binding) {
                loginButton.isGone = true
                progressBar.isGone = false
                progressTextView.isGone = false
            }
        }
    }

    private fun getAccessToken(code: String) = launch(coroutineContext) {
        try {
            withContext(Dispatchers.IO) {
                val response = RetrofitUtil.authApiService.getAccessToken(
                    clientId = BuildConfig.GITHUB_CLIENT_ID,
                    clientSecret = BuildConfig.GITHUB_CLIENT_SECRET,
                    code = code
                )
                val accessToken = response.accessToken
                Log.e("TEST", "accessToken = $accessToken")
                if (accessToken.isNotEmpty()) {
                    withContext(coroutineContext) {
                        authTokenProvider.updateToken(accessToken)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this@SignInActivity, "로그인 과정에서 에러가 발생했습니다. : ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun dismissProgress() = GlobalScope.launch {
        Log.e("TEST", "dismissProgress")
        withContext(Dispatchers.Main) {
            with(binding) {
                loginButton.isGone = false
                progressBar.isGone = true
                progressTextView.isGone = true
            }
        }
    }
}