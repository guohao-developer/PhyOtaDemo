package com.phy.ota_demo.basic;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;

import com.phy.ota_demo.OtaApp;
import com.phy.otalib.bean.OtaType;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 基类Activity
 */
public abstract class PhyActivity<T extends ViewBinding> extends BasicActivity {

    public T binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        onRegister();
        super.onCreate(savedInstanceState);
        setStatusBar(true);

        Type type = this.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            try {
                Class<T> clazz = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
                //反射
                Method method = clazz.getMethod("inflate", LayoutInflater.class);
                binding = (T) method.invoke(null, getLayoutInflater());
            } catch (Exception e) {
                e.printStackTrace();
            }
            setContentView(binding.getRoot());
        }
        onCreate();

    }

    protected void onRegister(){

    }

    protected abstract void onCreate();

    protected void showMsg(CharSequence llw) {
        Toast.makeText(OtaApp.context, llw, Toast.LENGTH_SHORT).show();
    }

    /**
     * 跳转页面
     *
     * @param clazz 目标页面
     */
    protected void jumpActivity(final Class<?> clazz) {
        startActivity(new Intent(context, clazz));
    }

    protected void jumpActivity(OtaType otaType, final  Class<?> clazz) {
        Intent intent = new Intent(this,clazz);
        Bundle bundle = new Bundle();
        bundle.putSerializable("otaType",otaType);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 跳转页面并关闭当前页面
     *
     * @param clazz 目标页面
     */
    protected void jumpActivityFinish(final Class<?> clazz) {
        startActivity(new Intent(context, clazz));
        finish();
    }

    protected void back(Toolbar toolbar) {
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    protected void backAndFinish(Toolbar toolbar) {
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    /**
     * 状态栏文字图标颜色
     *
     * @param dark 深色 false 为浅色
     */
    protected void setStatusBar(boolean dark) {
        View decor = getWindow().getDecorView();
        if (dark) {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    /**
     * 检查是有拥有某权限
     *
     * @param permission 权限名称
     * @return true 有  false 没有
     */
    protected boolean hasPermission(String permission) {
        return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 蓝牙是否打开
     *
     * @return true or false
     */
    protected boolean isOpenBluetooth() {
        BluetoothManager mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        if (mBluetoothManager == null) {
            return false;
        }
        BluetoothAdapter adapter = mBluetoothManager.getAdapter();
        if (adapter == null) {
            return false;
        }

        return adapter.isEnabled();
    }

    protected boolean isOpenLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        return gps || network;
    }

    protected boolean hasStorage() {
        return hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    protected boolean hasAccessFineLocation() {
        return hasPermission(Manifest.permission.ACCESS_FINE_LOCATION);
    }

    protected boolean hasCoarseLocation() {
        return hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    protected boolean hasBluetoothConnect() {
        return hasPermission(Manifest.permission.BLUETOOTH_CONNECT);
    }

    protected boolean hasBluetoothScan() {
        return hasPermission(Manifest.permission.BLUETOOTH_SCAN);
    }

    protected boolean isStorageManager() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        }
        return false;
    }

    /**
     * 当前是否在Android11.0及以上
     */
    protected boolean isAndroid11() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.R;
    }

    /**
     * 当前是否在Android12.0及以上
     */
    protected boolean isAndroid12() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.S;
    }


    /**
     * 退出应用程序，销毁所有Activity
     */
    protected void exitTheProgram() {
        OtaApp.getActivityManager().finishAllActivity();
    }
}
