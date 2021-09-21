package com.example.diffviewer.prdiff

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList
import com.example.diffviewer.R


class FilesDiffsRecyclerViewAdapter(var diffLinesArrayList: ArrayList<String>, var cxt: Context) :
    RecyclerView.Adapter<FilesDiffsRecyclerViewAdapter.FilesViewHolder>() {

    class FilesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var diff_one: TextView
        var diff_two: TextView

        init {
            diff_one = itemView.findViewById(R.id.diff_line_one)
            diff_two = itemView.findViewById(R.id.diff_line_two)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilesViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.diff_recyclerview_items, parent, false)
        return FilesViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilesViewHolder, position: Int) {
        val currentItem = diffLinesArrayList[position]
        holder.diff_one.text = diffLinesArrayList[0]
        holder.diff_two.text = diffLinesArrayList[1]
    }

    override fun getItemCount(): Int {
        return diffLinesArrayList.size
    }
}