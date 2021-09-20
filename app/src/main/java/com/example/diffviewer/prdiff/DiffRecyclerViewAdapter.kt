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


class DiffRecyclerViewAdapter(diffFileList: ArrayList<PRDiffLines>, context: Context?) :
    RecyclerView.Adapter<DiffRecyclerViewAdapter.MyViewHolder>() {
    private val filesArrayList: ArrayList<PRDiffLines>
    var cxt: Context?

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var filename: TextView
        var diffindex: TextView
        var diffsRecyclerView: RecyclerView

        init {
            filename = itemView.findViewById(R.id.diff_file_name)
            diffindex = itemView.findViewById(R.id.diff_file_index)
            diffsRecyclerView = itemView.findViewById(R.id.Diff_RV)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.diff_file_recyclerview_items, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filesArrayList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem: PRDiffLines = filesArrayList[position]
        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(cxt, LinearLayoutManager.HORIZONTAL, false)
        holder.diffsRecyclerView.layoutManager = layoutManager
        holder.diffsRecyclerView.setHasFixedSize(true)
        holder.filename.setText(currentItem.getDiffFileName())
        val arrayList: ArrayList<ChildModel> = ArrayList<ChildModel>()

        if (filesArrayList[position].getDiffFileName().equals("File name 1")) {
            arrayList.add(ChildModel("diff line 1", "diff line 2"))
            arrayList.add(ChildModel("diff line 1", "diff line 2"))
            arrayList.add(ChildModel("diff line 1", "diff line 2"))
            arrayList.add(ChildModel("diff line 1", "diff line 2"))
        }

        if (filesArrayList[position].getDiffFileName().equals("File name 2")) {
            arrayList.add(ChildModel("diff line 1", "diff line 2"))
            arrayList.add(ChildModel("diff line 1", "diff line 2"))
        }

        val diffsRecyclerViewAdapter =
            FilesDiffsRecyclerViewAdapter(arrayList, holder.diffsRecyclerView.context)
        holder.diffsRecyclerView.adapter = diffsRecyclerViewAdapter
    }

    init {
        filesArrayList = diffFileList
        cxt = context
    }
}