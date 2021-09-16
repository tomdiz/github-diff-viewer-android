package com.example.diffviewer.prdiff

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.diffviewer.ViewModelFactory
import com.example.diffviewer.retrofit.rest.ApiClient
import com.example.diffviewer.retrofit.rest.ApiHelper

class PRFragment  : Fragment() {

    private var pullnumber = 0
    private lateinit var viewModel: PRViewModel

    companion object {
        val TAG: String = PRFragment::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {
            pullnumber = it.getInt("pullnumber", 0)
        }
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(ApiClient.apiService))
        ).get(PRViewModel::class.java)
    }

}