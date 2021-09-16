package com.example.diffviewer.retrofit.model

data class PullRequestsResponse(
    val closed_at: String,
    val created_at: String,
    val title: String,
    val number: Int,
    val user: User
)

data class User(
    val avatar_url: String,
    val login: String,
)
