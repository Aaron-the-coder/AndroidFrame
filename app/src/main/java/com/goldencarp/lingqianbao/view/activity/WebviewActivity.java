package com.goldencarp.lingqianbao.view.activity;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.goldencarp.lingqianbao.R;
import com.goldencarp.lingqianbao.model.net.WebViewJavascriptBridge;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 可通过debugtbs.qqcom查看x5内核版本
 * x5基础功能集成完毕,本地视频播放器没有集成
 */
public class WebviewActivity extends BaseActivity {

    private static final String TAG = "WebviewActivity";
    private static final String AGENG_STR = "Lingqianbao";
    private static final String AGENG_STR_SUFFIX = "_android";
    private WebView mWebView;
    private WebViewJavascriptBridge mBridge;//与h5交互的桥
    public WebviewHandler sHandler = new WebviewHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
//        webSetting.setUserAgentString(webSetting.getUserAgentString() + APP_NAME_UA);// 其中 APP_NAME_UA 是 app 自定义 UA
        initWebview();
//        mWebView.loadUrl("http://weixindev.51lqb.com/frontend/web/");
        mWebView.loadUrl("http://www.baidu.com");
    }

    private void initWebview() {
        mWebView = findViewById(R.id.webview);
        mBridge = new WebViewJavascriptBridge(this, sHandler, mWebView);
        mWebView.addJavascriptInterface(mBridge, "AndroidWebViewJavascriptBridge");
        WebSettings webSetting = mWebView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        String userAgent = webSetting.getUserAgentString() + " " + AGENG_STR
                + AGENG_STR_SUFFIX + "/" + getVersionName() + "/QD"
                + getQudaoName() + "/";
        webSetting.setUserAgentString(userAgent);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView webView, String s) {
                InputStream fs = WebviewActivity.this.getResources().openRawResource(
                        R.raw.jsbridge_init);
                String jsbridgeInitScript = getLocalText(fs);
                String js1 = "javascript:var evtAndroidJSB = document.createEvent('Event');"
                        + "evtAndroidJSB.initEvent('AndroidWebViewJavascriptBridgeReady', false, false); "
                        + "document.dispatchEvent(evtAndroidJSB);";
                String js2 = "javascript: " + jsbridgeInitScript;
                if (android.os.Build.VERSION.SDK_INT <= 18) {
                    mWebView.loadUrl(js1);
                    mWebView.loadUrl(js2);
                } else {
                    mWebView.evaluateJavascript(js1, null);
                    mWebView.evaluateJavascript(js2, null);
                }
                super.onPageFinished(webView, s);
            }
        });
    }

    private String getVersionName() {
        try {
            PackageManager packageManager = getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(
                    getPackageName(), 0);
            String version = packInfo.versionName;
            return version;
        } catch (Exception e) {
            return "0.0";
        }
    }

    private String getQudaoName() {
        String qudaoName = "unknown";
        try {
            ApplicationInfo appInfo = this.getPackageManager()
                    .getApplicationInfo(getPackageName(),
                            PackageManager.GET_META_DATA);
            qudaoName = appInfo.metaData.getString("UMENG_CHANNEL");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return qudaoName;
    }

    private String getLocalText(InputStream is) {
        String resultData = "";
        try {
            InputStreamReader in = new InputStreamReader(is);
            BufferedReader buffer = new BufferedReader(in);
            String inputLine;
            while ((inputLine = buffer.readLine()) != null) {
                resultData += inputLine;
            }
            in.close();
            is.close();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return resultData;
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            finish();
        }
    }

    private static class WebviewHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

        }
    }
}
