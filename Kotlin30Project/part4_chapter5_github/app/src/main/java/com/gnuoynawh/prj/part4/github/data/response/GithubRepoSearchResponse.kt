package com.gnuoynawh.prj.part4.github.data.response

import com.gnuoynawh.prj.part4.github.data.entity.GithubRepoEntity

data class GithubRepoSearchResponse(
    val totalCount: Int,
    val items: List<GithubRepoEntity>
)