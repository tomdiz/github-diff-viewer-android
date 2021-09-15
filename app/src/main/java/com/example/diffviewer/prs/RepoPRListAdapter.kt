package com.example.diffviewer.prs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.diffviewer.databinding.ListItemPullRequestBinding
import com.example.diffviewer.retrofit.model.PullRequestResponse
import com.example.diffviewer.R

class RepoPRListAdapter(private val reposPRList: ArrayList<PullRequestResponse>) : RecyclerView.Adapter<RepoPRListAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ListItemPullRequestBinding>(LayoutInflater.from(parent.context), R.layout.list_item_pull_request,
            parent, false)
        return ViewHolder(binding)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.pr = reposPRList[position]
        holder.binding.executePendingBindings()
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return reposPRList.size
    }

    fun addRepos(repos: List<PullRequestResponse>) {
        this.reposPRList.apply {
            addAll(repos)
        }
    }

    fun clearRepos() {
        this.reposPRList.apply {
            clear()
        }
    }

    fun getRepos(position: Int): PullRequestResponse {
        return reposPRList[position]
    }

    //the class is holding the list view
    class ViewHolder(val binding: ListItemPullRequestBinding) : RecyclerView.ViewHolder(binding.root)

}
