package com.example.diffviewer.retrofit.rest

class ApiHelper(private val apiService: ApiService) {

    suspend fun getUserRepos(username: String, sort: String, per_page: Int, page: Int) =
        apiService.getUserRepos(username, sort, per_page, page)

    suspend fun getPullRequestForGithubRepo(username: String, repo_name: String) =
        apiService.getPullRequestForGithubRepo(username, repo_name)
}