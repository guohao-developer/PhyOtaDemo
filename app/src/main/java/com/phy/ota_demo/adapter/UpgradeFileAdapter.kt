package com.phy.ota_demo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.phy.ota_demo.R
import com.phy.ota_demo.bean.UpgradeFile

class UpgradeFileAdapter(private val fileList: List<UpgradeFile>):
    RecyclerView.Adapter<UpgradeFileAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fileName: TextView = view.findViewById(R.id.tv_upgradefile_name)
        val filePath: TextView = view.findViewById(R.id.tv_upgradefile_path)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_file_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val file = fileList[position]
        holder.fileName.text = file.file.name
        holder.filePath.text = file.file.absolutePath
    }

    override fun getItemCount() = fileList.size


    //点击事件
    interface OnItemClickListerner{
        fun onItemClick(position: Int)
    }
    private var mOnItemClickListerner:OnItemClickListerner? = null

    public fun setOnItemClickListerner(onItemClickListerner:OnItemClickListerner?){
        mOnItemClickListerner = onItemClickListerner
    }
}