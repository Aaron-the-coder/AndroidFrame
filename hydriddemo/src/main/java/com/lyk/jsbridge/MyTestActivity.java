package com.lyk.jsbridge;

import android.Manifest;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MyTestActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MyTestActivity";
    @BindView(R.id.pb_progress)
    ProgressBar pbProgress;
    //    @BindView(webview)
    static WebView mWebview;
    @BindView(R.id.button)
    Button button;
    private String jsbridgeInitScript;

    private static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WebViewJavascriptBridge.MSG_CALL_JAVA_METHOD:
                    String data = msg.getData().getString("data");
                    String callback = msg.getData().getString("callback");
                    doLogin(data, callback);
                    break;
            }
        }
    };

    /**
     * 测试:处理登录信息
     */
    private static void doLogin(String data, String callback) {
        Gson gson = new Gson();
        JsBean jsBean = gson.fromJson(data, JsBean.class);
        Log.e(LOG_TAG, "jsBean:" + jsBean.toString());
        if (TextUtils.equals("18612709396", jsBean.getMobile())) {
//            Toast.makeText(MyApp.getApp(), "登录成功", Toast.LENGTH_SHORT).show();
            Util.jsCallback(mWebview, 0, "登录成功", callback);
        } else {
//            Toast.makeText(MyApp.getApp(), "登录失败", Toast.LENGTH_SHORT).show();
            Util.jsCallback(mWebview, 0, "登录失败", callback);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_test);
        ButterKnife.bind(this);
        mWebview = findViewById(R.id.webview);
        MyTestActivityPermissionsDispatcher.needPermissionWithCheck(this);
    }

    private void initWebview() {

        InputStream fs = this.getResources().openRawResource(
                R.raw.jsbridge_init);
        jsbridgeInitScript = Util.getLocalText(fs);

        mWebview.setWebViewClient(new MyWebViewClient());
        mWebview.setWebChromeClient(new MyWebChromeClient());

        WebSettings webSettings = mWebview.getSettings();

        try {
            // setPageCacheCapacity(webSettings);
            webSettings.setSavePassword(false);//是否保存密码,api18已经过时
            webSettings.setSaveFormData(false);//是否保存表单数据
            webSettings.setJavaScriptEnabled(true);//允许JavaScript运行
            webSettings.setTextZoom(100);//设置网页文本缩放百分比,默认100
            webSettings.setDomStorageEnabled(true);//是否允许网页向本地存储数据
            webSettings.setAppCacheEnabled(true);//这是允许缓存,包括session数据和持久化数据
            String path = Environment.getExternalStorageDirectory() + "jsTest";
            webSettings.setAppCachePath(path);//设置缓存路径,与setAppCacheEnabled配合使用
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//先从网络获取,如果网络不可用则从本地获取
            String userAgent = webSettings.getUserAgentString();
            webSettings.setUserAgentString(userAgent);//可以自定义用户代理
        } catch (Exception e) {
            e.printStackTrace();
        }


        WebViewJavascriptBridge ylBridge = new WebViewJavascriptBridge(this, mHandler, mWebview);
        mWebview.addJavascriptInterface(ylBridge,
                "AndroidWebViewJavascriptBridge");

//        if (android.os.Build.VERSION.SDK_INT <= 18) {
        mWebview.loadUrl("file:///android_asset/jstest.html");
//        mWebview.loadUrl("https://www.baidu.com/");
//        } else {
//            mWebview.evaluateJavascript("https://www.baidu.com/", null);
//        }
    }

    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE})
    void needPermission() {
        initWebview();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MyTestActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE})
    void showRationale(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("请允许存储权限")
                .setPositiveButton("允许", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .show();
    }

    @OnPermissionDenied({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE})
    void permissionDenied() {
        finish();
        Process.killProcess(Process.myPid());
    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void neverAsk() {

    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        mWebview.loadUrl("javascript:login()");
    }

    /**
     * 自定义webview客户端
     */
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
            Log.e(LOG_TAG, "------------onPageFinished----------");
            mWebview.loadUrl(url);
            return true;
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView webView, String s) {
            Log.e(LOG_TAG, "------------onPageFinished----------");
            return super.shouldInterceptRequest(webView, s);
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
            Log.e(LOG_TAG, "------------onPageFinished----------");
            return super.shouldInterceptRequest(webView, webResourceRequest);
        }

        @Override
        public void onPageFinished(WebView webView, String s) {
            Log.e(LOG_TAG, "------------onPageFinished----------");
            String js1 = "javascript:var evtAndroidJSB = document.createEvent('Event');"
                    + "evtAndroidJSB.initEvent('AndroidWebViewJavascriptBridgeReady', false, false); "
                    + "document.dispatchEvent(evtAndroidJSB);";
            String js2 = "javascript: " + jsbridgeInitScript;
            if (android.os.Build.VERSION.SDK_INT <= 18) {
                mWebview.loadUrl(js1);
                mWebview.loadUrl(js2);
            } else {
                mWebview.evaluateJavascript(js1, null);
                mWebview.evaluateJavascript(js2, null);
            }
        }

        @Override
        public void onReceivedError(WebView webView, int i, String s, String s1) {
            Log.e(LOG_TAG, "------------onReceivedError----------");
        }

        @Override
        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            Log.e(LOG_TAG, "------------onReceivedSslError----------");
            sslErrorHandler.proceed();
        }
    }


    private class MyWebChromeClient extends WebChromeClient {

        @Override
        public boolean onJsPrompt(WebView webView, String s, String s1, String s2, JsPromptResult jsPromptResult) {
            //可以在这个回调中与h5交互
            return super.onJsPrompt(webView, s, s1, s2, jsPromptResult);
        }

        @Override
        public void onReceivedTitle(WebView webView, String s) {
            String js = "javascript:var evt1 = document.createEvent('Event');"
                    + "evt1.initEvent('AndroidJavascriptBridgeReady', false, false);";
            if (android.os.Build.VERSION.SDK_INT <= 18) {
                mWebview.loadUrl(js);
            } else {
                mWebview.evaluateJavascript(js, null);
            }
        }

        @Override
        public void onProgressChanged(WebView webView, int progress) {
            if (progress > 0 && progress < 100) {
                pbProgress.setVisibility(View.VISIBLE);
                pbProgress.setProgress(progress);
            } else {
                pbProgress.setProgress(0);
                pbProgress.setVisibility(View.INVISIBLE);
            }
        }
    }
}
