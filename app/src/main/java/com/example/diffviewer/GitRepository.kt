package com.example.diffviewer

import com.example.diffviewer.retrofit.rest.ApiHelper

class GitRepository(private val apiHelper: ApiHelper) {

    suspend fun getUserRepos(username: String, sort: String, per_page: Int, page: Int) =
        apiHelper.getUserRepos(username, sort, per_page, page)
}
