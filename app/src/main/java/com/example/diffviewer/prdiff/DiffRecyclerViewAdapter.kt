package com.example.diffviewer.prdiff

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList
import com.example.diffviewer.R


class DiffRecyclerViewAdapter(diffFileList: ArrayList<PRDiff>, changedFile: Int, context: Context?) :
    RecyclerView.Adapter<DiffRecyclerViewAdapter.DiffViewHolder>() {
    private val filesArrayList: ArrayList<PRDiff>
    var cxt: Context?
    private var change_files = 0

    class DiffViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var fileschanged: TextView
        var filename: TextView
        var diffindex: TextView
        var diffsRecyclerView: RecyclerView

        init {
            fileschanged = itemView.findViewById(R.id.files_changed)
            filename = itemView.findViewById(R.id.diff_file_name)
            diffindex = itemView.findViewById(R.id.diff_file_index)
            diffsRecyclerView = itemView.findViewById(R.id.Diff_RV)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiffViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.diff_file_recyclerview_items, parent, false)
        return DiffViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filesArrayList.size
    }

    override fun onBindViewHolder(holder: DiffViewHolder, position: Int) {
        val currentItem: PRDiff = filesArrayList[position]
        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(cxt, LinearLayoutManager.HORIZONTAL, false)
        holder.diffsRecyclerView.layoutManager = layoutManager
        holder.diffsRecyclerView.setHasFixedSize(true)
        holder.filename.setText(currentItem.diffFileName)
        holder.diffindex.setText(currentItem.indexString)
        holder.fileschanged.setText("Changed Files: " + currentItem.changedfile)
        val diffsRecyclerViewAdapter = FilesDiffsRecyclerViewAdapter(currentItem.lines, holder.diffsRecyclerView.context)
        holder.diffsRecyclerView.adapter = diffsRecyclerViewAdapter
    }

    init {
        filesArrayList = diffFileList
        cxt = context
        change_files = changedFile
    }
}