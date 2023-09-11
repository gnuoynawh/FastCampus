package com.gnuoynawh.prj.part4.github.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverters
import com.gnuoynawh.prj.part4.github.data.entity.GithubRepoEntity

@Dao
interface RepositoryDao {

    @Insert
    fun insert(repo: GithubRepoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(repoList: List<GithubRepoEntity>)

    @Query("SELECT * FROM GithubRepository")
    fun getHistory(): List<GithubRepoEntity>

    @Query("SELECT * FROM GithubRepository WHERE fullName = :repoName")
    fun getRepository(repoName: String): GithubRepoEntity?

    @Query("DELETE FROM GithubRepository WHERE fullName = :repoName")
    fun remove(repoName: String)

    @Query("DELETE FROM GithubRepository")
    fun clearAll()

}