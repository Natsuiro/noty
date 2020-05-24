package com.szmy.noty;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public abstract class BaseActivity extends AppCompatActivity {

    protected static final int REQUEST_NEW = 0;
    protected static final int REQUEST_UPDATE = 1;
    protected static final int REQUEST_SEARCH = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!hasWriteExternalStorage()){
            applyWriteExternalStorage();
        }else {
            init();
        }

    }
    private void init() {
        setContentView(layoutResId());
        initData();
        initView();
    }

    //判断应用当前是否对指定权限授权
    private boolean hasWriteExternalStorage() {
        int checkSelfPermission =
                ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return checkSelfPermission == PackageManager.PERMISSION_GRANTED;
    }
    //动态申请未授权的权限
    private void applyWriteExternalStorage() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
    }
    //申请权限的回调方法，处理申请结果


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
            init();
        }else{
            toast("permission Denied");
            finish();
        }

    }

    protected void toast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    abstract void initData();
    abstract void initView();
    abstract int layoutResId();
}
