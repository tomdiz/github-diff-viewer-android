package com.example.diffviewer.prdiff

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.diffviewer.R
import com.example.diffviewer.ViewModelFactory
import com.example.diffviewer.databinding.FragmentRecyclerBinding
import com.example.diffviewer.prs.RepoPRFragment
import com.example.diffviewer.retrofit.model.PullRequestResponse
import com.example.diffviewer.retrofit.model.Status
import com.example.diffviewer.retrofit.rest.ApiClient
import com.example.diffviewer.retrofit.rest.ApiHelper
import com.google.android.material.snackbar.Snackbar

class PRFragment  : Fragment() {

    private lateinit var mBinding: FragmentRecyclerBinding

    private var username = ""
    private var repoName = ""
    private var pullnumber = 0
    private lateinit var viewModel: PRViewModel

    companion object {
        val TAG: String = PRFragment::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {
            username = it.getString("username", "")
            repoName = it.getString("repoName", "")
            pullnumber = it.getInt("pullnumber", 0)
        }
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(ApiClient.apiService))
        ).get(PRViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Define the listener for binding
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recycler, container, false)

        setupObservers()

        return mBinding.root
    }

    fun Fragment.isNetworkAvailable(): Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }

    private fun setupObservers() {
        if (activity?.baseContext?.let { isNetworkAvailable() }!!) {
            viewModel.getPullRequest(username, repoName, pullnumber).observe(viewLifecycleOwner, {
                it?.let {  resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            mBinding.swipeRefreshLayout.isRefreshing = false
                            resource.data?.let { pr -> retrieve(pr) }
                        }
                        Status.ERROR -> {
                            mBinding.swipeRefreshLayout.isRefreshing = false
                            Snackbar.make(
                                activity?.window?.decorView?.rootView!!,
                                R.string.error,
                                Snackbar.LENGTH_LONG
                            ).show()
                            Log.d(RepoPRFragment.TAG, it.message.toString())
                        }
                        Status.LOADING -> {
                            mBinding.swipeRefreshLayout.isRefreshing = true
                        }
                    }
                }
            })
        } else {
            // network is not present then show message
            Snackbar.make(
                activity?.window?.decorView?.rootView!!,
                R.string.network_error,
                Snackbar.LENGTH_LONG
            ).setAction("Retry") {
                setupObservers()
            }.show()
        }
    }

    private fun retrieve(repos: PullRequestResponse) {
    }

}