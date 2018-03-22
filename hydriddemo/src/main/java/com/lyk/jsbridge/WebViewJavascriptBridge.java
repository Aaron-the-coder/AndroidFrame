package com.lyk.jsbridge;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.tencent.smtt.sdk.WebView;

import java.lang.reflect.Method;

public class WebViewJavascriptBridge {

    public final static int MSG_CALL_JAVA_METHOD = 6;
    private static final String LOG_TAG = "WebViewJavascriptBridge";

    private Context mContext = null;
    private WebView webView = null;
    private Handler handler = null;
    private long startTime;

    public WebViewJavascriptBridge(Context context, Handler handler, WebView webView) {
        this.mContext = context;
        this.handler = handler;
        this.webView = webView;
    }

    @JavascriptInterface
    public void callJavaHandler(String methodName, String data, String callback) {
        String logTag = "callJavaHandler";
        // Logger.i(logTag, methodName + ";" + data + ";" + callback);
        try {
            startTime = System.currentTimeMillis();
            Method method = getClass().getMethod(methodName, String.class, String.class);
            method.invoke(this, data, callback);

//            CglibDynamicProxy cglib = new CglibDynamicProxy();
//            WebViewJavascriptBridge realSubject = (WebViewJavascriptBridge) cglib.getProxyInstance(this);
//            realSubject.lqbLogined(data, callback);
            long endTime = System.currentTimeMillis();
            Log.e(LOG_TAG, "运行耗时:" + (endTime - startTime) + "毫秒");
        } catch (Exception e) {
            // Logger.e(logTag, e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 设置手势密码
     */
    @JavascriptInterface
    public void lqbLogined(String data, String callback) {
        Log.e(LOG_TAG, "-----------------lqbLogined------------------");
        Log.e(LOG_TAG, "lqbLogined->data:" + data);

        Message msg = Message.obtain();
        Bundle b = new Bundle();
        b.putString("data", data);
        b.putString("callback", callback);
        msg.setData(b);
        msg.what = MSG_CALL_JAVA_METHOD;
        handler.sendMessage(msg);

    }

}
