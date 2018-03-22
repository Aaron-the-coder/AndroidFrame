package com.goldencarp.lingqianbao.model.net;

import android.os.Handler;
import android.os.Message;
import android.webkit.JavascriptInterface;

import com.goldencarp.lingqianbao.view.activity.WebviewActivity;
import com.tencent.smtt.sdk.WebView;

import java.lang.reflect.Method;

public class WebViewJavascriptBridge {

    public final static int MSG_SETGEST = 6;
    private static final String TAG = "WebViewJavascriptBridge";

    private WebviewActivity activity = null;
    private WebView webView = null;
    private Handler handler = null;

    public WebViewJavascriptBridge(WebviewActivity activity, Handler handler, WebView webView) {
        this.activity = activity;
        this.handler = handler;
        this.webView = webView;
    }

    @JavascriptInterface
    public void callJavaHandler(String methodName, String data, String callback) {
        String logTag = "callJavaHandler";
        // Logger.i(logTag, methodName + ";" + data + ";" + callback);
        try {
            Method method = getClass().getMethod(methodName, new Class[]{String.class, String.class});
            method.invoke(this, data, callback);
        } catch (Exception e) {
            // Logger.e(logTag, e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 设置手势密码
     */
    public void lqbSetGest(String data, String callback) {
        Message msg = Message.obtain();
        msg.what = MSG_SETGEST;
        handler.sendMessage(msg);
    }

}
