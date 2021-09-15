package com.example.diffviewer.retrofit.model

data class PullRequestResponse(
    val closed_at: String,
    val created_at: String,
    val title: String,
    val user: User
)

data class User(
    val avatar_url: String,
    val login: String,
)
