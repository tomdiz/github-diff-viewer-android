package com.example.diffviewer.repos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.diffviewer.databinding.ListItemReposBinding
import com.example.diffviewer.retrofit.model.UserReposResponse
import com.example.diffviewer.R

class UserRepoListAdapter(private val reposList: ArrayList<UserReposResponse>) : RecyclerView.Adapter<UserRepoListAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ListItemReposBinding>(LayoutInflater.from(parent.context), R.layout.list_item_repos,
            parent, false)
        return ViewHolder(binding)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.user = reposList[position]
        holder.binding.executePendingBindings()
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return reposList.size
    }

    fun addRepos(repos: List<UserReposResponse>) {
        this.reposList.apply {
            addAll(repos)
        }
    }

    fun clearRepos() {
        this.reposList.apply {
            clear()
        }
    }

    fun getRepos(position: Int): UserReposResponse {
        return reposList[position]
    }

    //the class is holding the list view
    class ViewHolder(val binding: ListItemReposBinding) : RecyclerView.ViewHolder(binding.root)

}
