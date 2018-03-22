package com.goldencarp.lingqianbao.view.util;

import android.text.TextUtils;
import android.util.Log;

import static com.goldencarp.lingqianbao.model.Constants.isDebug;

/**
 * Created by sks on 2017/10/24.
 *
 */

public class Logger {

    private static final String TAG = "LingQianBaoLogger";

    public static void e(String TAG, String content) {
        if (isDebug()) {
            if (!TextUtils.isEmpty(content)){
                Log.e(TAG, content);
            }
        }
    }

    public static void e(String content) {
        if (isDebug()) {
            if (!TextUtils.isEmpty(content)){
                Log.e(TAG, content);
            }
        }
    }

    public static void w(String TAG, String content) {
        if (isDebug()) {
            if (!TextUtils.isEmpty(content)){

                Log.w(TAG, content);
            }
        }
    }

    public static void w(String content) {
        if (isDebug()) {
            if (!TextUtils.isEmpty(content)){

                Log.w(TAG, content);
            }
        }
    }

    public static void i(String TAG, String content) {
        if (isDebug()) {
            if (!TextUtils.isEmpty(content)){

                Log.i(TAG, content);
            }
        }
    }

    public static void i(String content) {
        if (isDebug()) {
            if (!TextUtils.isEmpty(content)){

                Log.i(TAG, content);
            }
        }
    }

    public static void d(String TAG, String content) {
        if (isDebug()) {
            if (!TextUtils.isEmpty(content)){

                Log.d(TAG, content);
            }
        }
    }

    public static void d(String content) {
        if (isDebug()) {
            if (!TextUtils.isEmpty(content)){

                Log.d(TAG, content);
            }
        }
    }

    public static void v(String TAG, String content) {
        if (isDebug()) {
            if (!TextUtils.isEmpty(content)){

                Log.v(TAG, content);
            }
        }
    }

    public static void v(String content) {
        if (isDebug()) {
            if (!TextUtils.isEmpty(content)){

                Log.v(TAG, content);
            }
        }
    }

}
