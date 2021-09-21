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
import android.view.View.MeasureSpec





class FilesDiffsRecyclerViewAdapter(var diffLinesArrayList: ArrayList<String>, var context: Context) :
    RecyclerView.Adapter<FilesDiffsRecyclerViewAdapter.FilesViewHolder>() {

    class FilesViewHolder(itemView: View, var diffLinesArrayList: ArrayList<String>, cxt: Context) : RecyclerView.ViewHolder(itemView) {
        var context: Context? = null

        init {
            context = cxt
            var mListView = itemView.findViewById<ListView>(R.id.userlist)
            mListView.setEnabled(true)

            val arrayAdapter: ArrayAdapter<*>
            arrayAdapter = ArrayAdapter(cxt, android.R.layout.simple_list_item_1, diffLinesArrayList)
            mListView.adapter = arrayAdapter
/*
            val temp = diffLinesArrayList.count()
            var totalHeight = 0
            val desiredWidth = MeasureSpec.makeMeasureSpec(mListView.getWidth(), MeasureSpec.AT_MOST)
            for (i in 0 until arrayAdapter.getCount()) {
                val listItem: View = arrayAdapter.getView(i, null, mListView)
                listItem.measure(desiredWidth, MeasureSpec.UNSPECIFIED)
                totalHeight += listItem.measuredHeight
            }
            val params: ViewGroup.LayoutParams = mListView.getLayoutParams()
            params.height = totalHeight + mListView.getDividerHeight() * (arrayAdapter.getCount() - 1)
            mListView.setLayoutParams(params)
            mListView.requestLayout()
 */
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilesViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.diff_recyclerview_items, parent, false)
        return FilesViewHolder(view, diffLinesArrayList, parent.context)
    }

    override fun onBindViewHolder(holder: FilesViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        return 2
    }

}