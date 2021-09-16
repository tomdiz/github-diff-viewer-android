package com.example.diffviewer.prdiff

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.diffviewer.R
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.diffviewer.ViewModelFactory
import com.example.diffviewer.prs.RepoPRFragment
import com.example.diffviewer.retrofit.model.PullRequestResponse
import com.example.diffviewer.retrofit.model.Status
import com.example.diffviewer.retrofit.rest.ApiClient
import com.example.diffviewer.retrofit.rest.ApiHelper
import com.google.android.material.snackbar.Snackbar

class PRFragment  : Fragment() {

    private lateinit var mView: View
    private lateinit var diffWebtView: WebView
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
        mView = inflater.inflate(R.layout.fragment_pull_request, container, false)
        diffWebtView = mView.findViewById(R.id.diffWebView)
        diffWebtView.settings.setJavaScriptEnabled(true)

        setupObservers()

        return mView;
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
                            resource.data?.let { pr -> retrieve(pr) }
                        }
                        Status.ERROR -> {
                            Snackbar.make(
                                activity?.window?.decorView?.rootView!!,
                                R.string.error,
                                Snackbar.LENGTH_LONG
                            ).show()
                            Log.d(RepoPRFragment.TAG, it.message.toString())
                        }
                        Status.LOADING -> {
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

    private fun retrieve(pr: PullRequestResponse) {
        diffWebtView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                url?.let { view?.loadUrl(it) }
                return true
            }
        }
        diffWebtView.loadUrl(pr.diff_url)
    }

}