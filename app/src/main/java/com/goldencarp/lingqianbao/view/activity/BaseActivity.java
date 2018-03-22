package com.goldencarp.lingqianbao.view.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.goldencarp.lingqianbao.view.LQBApp;

/**
 * Created by sks on 2017/12/4.
 * 所有页面的基类
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

    /**
     * 隐藏状态栏和标题栏
     */
    protected void hideStatusBar() {
//        if (!(this instanceof SplashActivity)) {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option =
//                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
//            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
//        }

    }

    @Override
    protected void onResume() {
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();
        if (!TextUtils.isEmpty(LQBApp.answer) && !LQBApp.isUnlocked) {
//            Intent intent = new Intent(LQBApp.getApp(), LockActivity.class);
//            startActivity(intent);
            goTo(LockActivity.class);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 监控返回键
//            new AlertDialog.Builder(this).setTitle("提示")
//                    .setIconAttribute(android.R.attr.alertDialogIcon)
//                    .setMessage("确定要退出吗?")
//                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            ActivityCollector.finishAll();
//                        }})
//                    .setNegativeButton("取消", null)
//                    .create().show();
//            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void goTo(Class clazz) {
        Intent intent = new Intent(LQBApp.getApp(), clazz);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

}
