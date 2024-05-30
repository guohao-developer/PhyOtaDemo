package com.phy.ota_demo.ui

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.phy.ota_demo.adapter.FileAdapter
import com.phy.ota_demo.basic.PhyActivity
import com.phy.ota_demo.bean.UpgradeFile
import com.phy.ota_demo.databinding.ActivityUpgradeFileSelectBinding
import com.phy.otalib.PhyCore


class UpgradeFileSelectActivity : PhyActivity<ActivityUpgradeFileSelectBinding>(), FileAdapter.OnItemClickListener {

    private lateinit var fileList :ArrayList<UpgradeFile>
    private lateinit var mAdapter: FileAdapter
    override fun onCreate() {

        fileList = intent.getSerializableExtra("fileList") as ArrayList<UpgradeFile>

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        // 设置点击事件
        mAdapter = FileAdapter(this,fileList)
        mAdapter.setOnItemClickListener(this)
        binding.recyclerView.adapter = mAdapter

    }

    override fun onItemClick(view: View?, position: Int) {

        val intent = Intent()
        intent.putExtra("position" , position)
        setResult(RESULT_OK,intent)
        finish()
    }
}