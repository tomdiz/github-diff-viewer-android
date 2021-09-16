package com.example.diffviewer.retrofit.rest

class ApiHelper(private val apiService: ApiService) {

    suspend fun getUserRepos(username: String, sort: String, per_page: Int, page: Int) =
        apiService.getUserRepos(username, sort, per_page, page)

    suspend fun getPullRequestsForRepo(username: String, repo_name: String, per_page: Int, page: Int, state: String) =
        apiService.getPullRequestsForRepo(username, repo_name, per_page, page, state)

    suspend fun getPullRequest(username: String, repo_name: String, pullnumber: Int) =
        apiService.getPullRequest(username, repo_name, pullnumber)
}
