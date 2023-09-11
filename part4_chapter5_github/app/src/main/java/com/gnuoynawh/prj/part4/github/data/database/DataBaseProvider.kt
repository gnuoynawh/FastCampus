package com.gnuoynawh.prj.part4.github.data.database

import android.app.Application
import androidx.room.Room
import com.gnuoynawh.prj.part4.github.RepositoryActivity

object DataBaseProvider {

    private const val DB_NAME = "github_app.db"

    fun provideDB(application: Application) = Room.databaseBuilder(
        application,
        MyGithubDatabase::class.java, DB_NAME
    ).build()

}