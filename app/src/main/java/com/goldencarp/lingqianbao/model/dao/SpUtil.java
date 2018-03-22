package com.goldencarp.lingqianbao.model.dao;

import android.content.Context;
import android.content.SharedPreferences;

import com.goldencarp.lingqianbao.model.Constants;
import com.goldencarp.lingqianbao.view.LQBApp;

/**
 * Created by sks on 2018/3/3.
 * 帮助sharedpreferrences快速存取的类
 */

public class SpUtil {

    private static SharedPreferences mSp;

    private SpUtil(){}

    public static void saveString(String key, String content) {
        if (mSp == null) {
            mSp = LQBApp.getApp().getSharedPreferences(Constants.SP_FILE_NAME, Context.MODE_PRIVATE);
        }
        mSp.edit().putString(key, content).apply();
    }

    public static String getString(String key) {
        if (mSp == null) {
            mSp = LQBApp.getApp().getSharedPreferences(Constants.SP_FILE_NAME, Context.MODE_PRIVATE);
        }
        return mSp.getString(key, "");
    }

    public static void saveBoolean(String key, boolean content) {
        if (mSp == null) {
            mSp = LQBApp.getApp().getSharedPreferences(Constants.SP_FILE_NAME, Context.MODE_PRIVATE);
        }
        mSp.edit().putBoolean(key, content).apply();
    }

    public static boolean getBoolean(String key) {
        if (mSp == null) {
            mSp = LQBApp.getApp().getSharedPreferences(Constants.SP_FILE_NAME, Context.MODE_PRIVATE);
        }
        return mSp.getBoolean(key, false);
    }
}
