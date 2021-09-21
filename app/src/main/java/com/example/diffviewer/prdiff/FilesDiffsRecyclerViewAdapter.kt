package com.example.diffviewer.prdiff

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList
import com.example.diffviewer.R


class FilesDiffsRecyclerViewAdapter(var diffLinesArrayList: ArrayList<String>, var context: Context) :
    RecyclerView.Adapter<FilesDiffsRecyclerViewAdapter.FilesViewHolder>() {

    class FilesViewHolder(itemView: View, cxt: Context) : RecyclerView.ViewHolder(itemView) {
//        var diff_one: TextView
//        var diff_two: TextView
        var context: Context? = null

        init {
            context = cxt
//            diff_one = itemView.findViewById(R.id.diff_line_one)
//            diff_two = itemView.findViewById(R.id.diff_line_two)
            var mListView = itemView.findViewById<ListView>(R.id.userlist)

            val arrayAdapter: ArrayAdapter<*>
            val users = arrayOf(
                "Virat Kohli", "Rohit Sharma", "Steve Smith",
                "Kane Williamson", "Ross Taylor"
            )

            arrayAdapter = ArrayAdapter(cxt, android.R.layout.simple_list_item_1, users)
            mListView.adapter = arrayAdapter

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilesViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.diff_recyclerview_items, parent, false)
        return FilesViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: FilesViewHolder, position: Int) {
        val currentItem = diffLinesArrayList[position]
//        holder.diff_one.text = diffLinesArrayList[0]
//        holder.diff_two.text = diffLinesArrayList[1]
    }

    override fun getItemCount(): Int {
        return 2//diffLinesArrayList.size
    }

}