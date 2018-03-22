package com.goldencarp.lingqianbao.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.goldencarp.lingqianbao.R;
import com.goldencarp.lingqianbao.view.LQBApp;
import com.goldencarp.lingqianbao.view.util.Logger;

import cn.jpush.android.api.JPushInterface;

public class PushActivity extends BaseActivity {

    private static final java.lang.String TAG = "PushActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);
    }
    public void stopPush(View view) {
        JPushInterface.stopPush(LQBApp.getApp());
        Toast.makeText(this, "推送已关闭", Toast.LENGTH_SHORT).show();
    }
    public void resumePush(View view) {
        if (JPushInterface.isPushStopped(LQBApp.getApp())){
            JPushInterface.resumePush(LQBApp.getApp());
            Toast.makeText(this, "推送已恢复", Toast.LENGTH_SHORT).show();
        }else {
            Logger.d(TAG, "-----推送未关闭,无需恢复------");
        }
    }
    public void setAlias(View view) {
        JPushInterface.setAlias(LQBApp.getApp(), 1000, "woshi007");
        Toast.makeText(this, "别名已设置", Toast.LENGTH_SHORT).show();
    }
    public void deleteAlias(View view) {
        JPushInterface.deleteAlias(LQBApp.getApp(), 1000);
        Toast.makeText(this, "别名已删除", Toast.LENGTH_SHORT).show();
    }
    public void getAlias(View view) {
        JPushInterface.getAlias(LQBApp.getApp(), 1000);
        Toast.makeText(this, "别名是:", Toast.LENGTH_SHORT).show();
    }
}
