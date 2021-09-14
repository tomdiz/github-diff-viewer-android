package com.example.diffviewer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.diffviewer.retrofit.rest.ApiHelper
import com.example.diffviewer.repos.UserReposViewModel

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(UserReposViewModel::class.java)) {
            return UserReposViewModel(GitRepository(apiHelper)) as T
        }

        throw IllegalArgumentException("Unknown class name")
    }
}

