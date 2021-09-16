package com.example.diffviewer.retrofit.rest

import com.example.diffviewer.retrofit.model.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// Github Doc - https://developer.github.com/v3/

interface ApiService {

    @GET("users/{username}/repos")
    suspend fun getUserRepos(
        @Path("username") username: String,
        @Query("sort") sort: String,
        @Query("per_page") per_page: Int,
        @Query("page") page: Int
    ): ArrayList<UserReposResponse>

    @GET("/repos/{username}/{repo}/pulls")
    suspend fun getPullRequestsForRepo(
        @Path("username") username: String,
        @Path("repo") repoName: String,
        @Query("per_page") per_page: Int,
        @Query("page") page: Int,
        @Query("state") state: String
    ) : ArrayList<PullRequestsResponse>

    @GET("/repos/{username}/{repo}/pulls/{pull_number}")
    suspend fun getPullRequest(
        @Path("username") username: String,
        @Path("repo") repoName: String,
        @Path("pull_number") pullnumber: Int,
    ) : PullRequestResponse

}