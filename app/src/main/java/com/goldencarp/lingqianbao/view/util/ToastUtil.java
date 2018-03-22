package com.goldencarp.lingqianbao.view.util;

import android.widget.Toast;

import com.goldencarp.lingqianbao.view.LQBApp;

/**
 * Created by sks on 2018/3/5.
 */

public class ToastUtil {
    public static void showToast(String msg) {
        Toast.makeText(LQBApp.getApp(), msg, Toast.LENGTH_SHORT).show();
    }
}
