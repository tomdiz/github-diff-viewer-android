package com.example.diffviewer.prdiff

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.diffviewer.GitRepository
import com.example.diffviewer.retrofit.model.Resource
import kotlinx.coroutines.Dispatchers

class PRViewModel (private val gitRepository: GitRepository) : ViewModel() {

    fun getPullRequest(username: String, repo_name: String, pullnumber: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = gitRepository.getPullRequest(username, repo_name, pullnumber)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}
