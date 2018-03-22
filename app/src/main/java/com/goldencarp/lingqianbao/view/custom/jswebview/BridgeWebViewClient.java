package com.goldencarp.lingqianbao.view.custom.jswebview;

import android.graphics.Bitmap;

import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 如果要自定义WebViewClient必须要集成此类
 * Created by bruce on 10/28/15.
 */
public class BridgeWebViewClient extends WebViewClient {
    private BridgeWebView webView;

    public BridgeWebViewClient(BridgeWebView webView) {
        this.webView = webView;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        try {
            url = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (url.startsWith(BridgeUtil.YY_RETURN_DATA)) { // 如果是返回数据
            webView.handlerReturnData(url);
            return true;
        } else if (url.startsWith(BridgeUtil.YY_OVERRIDE_SCHEMA)) { //
            webView.flushMessageQueue();
            return true;
        } else {
            return super.shouldOverrideUrlLoading(view, url);
        }
    }
//@Override
    // 增加shouldOverrideUrlLoading在api》=24时
//    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            String url = request.getUrl().toString();
//            try {
//                url = URLDecoder.decode(url, "UTF-8");
//            } catch (UnsupportedEncodingException ex) {
//                ex.printStackTrace();
//            }
//            if (url.startsWith(BridgeUtil.YY_RETURN_DATA)) { // 如果是返回数据
//                webView.handlerReturnData(url);
//                return true;
//            } else if (url.startsWith(BridgeUtil.YY_OVERRIDE_SCHEMA)) { //
//                webView.flushMessageQueue();
//                return true;
//            } else {
//                return super.shouldOverrideUrlLoading(view, request);
//            }
//        }else {
//            return super.shouldOverrideUrlLoading(view, request);
//        }
//    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);

        if (BridgeWebView.toLoadJs != null) {
            BridgeUtil.webViewLoadLocalJs(view, BridgeWebView.toLoadJs);
        }

        //
        if (webView.getStartupMessage() != null) {
            for (Message m : webView.getStartupMessage()) {
                webView.dispatchMessage(m);
            }
            webView.setStartupMessage(null);
        }
    }
}