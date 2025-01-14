package com.gnuoynawh.prj.part4.github.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gnuoynawh.prj.part4.github.data.dao.RepositoryDao
import com.gnuoynawh.prj.part4.github.data.entity.GithubRepoEntity

@Database(entities = [GithubRepoEntity::class], version = 1)
abstract class MyGithubDatabase : RoomDatabase() {

    abstract fun repositoryDao(): RepositoryDao

}