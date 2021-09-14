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

}