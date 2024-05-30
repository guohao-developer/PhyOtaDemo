package com.phy.ota_demo.basic;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.phy.ota_demo.OtaApp;
import com.phy.otalib.PhyCore;
import com.phy.otalib.bean.PhyDevice;

public class BasicActivity extends AppCompatActivity {

    protected Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        OtaApp.getActivityManager().addActivity(this);
    }

}
