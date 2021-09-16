package com.example.diffviewer.retrofit.model

data class PullRequestResponse(
    val diff_url: String,
    val changed_files: Int,
    val additions: Int,
    val deletions: Int,
    val commits: Int
)
