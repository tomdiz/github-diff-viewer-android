package com.example.diffviewer.repos

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.example.diffviewer.databinding.FragmentRecyclerBinding
import com.example.diffviewer.retrofit.model.Status
import com.example.diffviewer.retrofit.model.UserReposResponse
import com.example.diffviewer.retrofit.rest.ApiClient
import com.example.diffviewer.retrofit.rest.ApiHelper
import com.example.diffviewer.ViewModelFactory
import com.example.diffviewer.R
import com.example.diffviewer.utils.RecyclerTouchListener
import com.example.diffviewer.utils.EndlessRecyclerOnScrollListener


class UserReposFragment : Fragment() {

    private lateinit var mBinding: FragmentRecyclerBinding
    private lateinit var viewModel: UserReposViewModel
    private var username = "magicalpanda"
    private var pageno = 1
    private lateinit var adapter: UserRepoListAdapter

    companion object {
        val TAG: String = UserReposFragment::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {
            username = it.getString("username", "")
        }
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(ApiClient.apiService))
        ).get(UserReposViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Define the listener for binding
        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_recycler, container, false)

        setupUI()
        setupObservers()

        return mBinding.root
    }

    @SuppressLint("WrongConstant")
    private fun setupUI() {
        val context = activity as Context
        activity?.title = "$username Repositories"

        //Connect adapter with recyclerView
        adapter = UserRepoListAdapter(arrayListOf())
        mBinding.recyclerView.adapter = adapter

        //Add a LayoutManager
        mBinding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)

        mBinding.recyclerView.addOnItemTouchListener(
            RecyclerTouchListener(
                context,
                mBinding.recyclerView,
                object : RecyclerTouchListener.ClickListener {
                    override fun onClick(view: View, position: Int) {
                        //openCustomTabs(adapter.getRepos(position).html_url)
                    }

                    override fun onLongClick(view: View?, position: Int) {
                    }
                })
        )

        mBinding.recyclerView.addOnScrollListener(object : EndlessRecyclerOnScrollListener() {
            override fun onLoadMore() {
                pageno += 1
                setupObservers()
            }
        })

        mBinding.swipeRefreshLayout.setColorSchemeResources(
            R.color.blue,
            R.color.green,
            R.color.orange,
            R.color.red
        )
        mBinding.swipeRefreshLayout.setOnRefreshListener {
            pageno = 1
            setupObservers()
        }
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
            viewModel.getUserRepos(username, "updated", 25, pageno).observe(viewLifecycleOwner, {
                it?.let {  resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            mBinding.swipeRefreshLayout.isRefreshing = false
                            resource.data?.let { users -> retrieveList(users) }
                        }
                        Status.ERROR -> {
                            mBinding.swipeRefreshLayout.isRefreshing = false
                            Snackbar.make(
                                activity?.window?.decorView?.rootView!!,
                                R.string.error,
                                Snackbar.LENGTH_LONG
                            ).show()
                            Log.d(TAG, it.message.toString())
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

    private fun retrieveList(repos: List<UserReposResponse>) {
        adapter.apply {
            if (pageno == 1) {
                clearRepos()
            }
            addRepos(repos)
            notifyDataSetChanged()
        }
    }

}
