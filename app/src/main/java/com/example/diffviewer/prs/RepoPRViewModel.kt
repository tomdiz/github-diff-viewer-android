package com.example.diffviewer.prs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.diffviewer.GitRepository
import com.example.diffviewer.retrofit.model.Resource
import kotlinx.coroutines.Dispatchers

class RepoPRViewModel (private val gitRepository: GitRepository) : ViewModel() {

    fun getRepoPRs(username: String, repo_name: String, per_page: Int, page: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = gitRepository.getPullRequestForRepo(username, repo_name, per_page, page)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}
