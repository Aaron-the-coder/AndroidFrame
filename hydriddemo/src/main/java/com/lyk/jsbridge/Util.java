package com.lyk.jsbridge;

import android.util.Log;

import com.tencent.smtt.sdk.WebView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by dale on 2018/1/20.
 */

public class Util {

    private static final String LOG_TAG = "Util";

    /**
     * 将本地文件读出来
     */
    public static String getLocalText(InputStream is) {
        String resultData = "";
        try {
            InputStreamReader in = new InputStreamReader(is);
            BufferedReader buffer = new BufferedReader(in);
            String inputLine = null;
            while ((inputLine = buffer.readLine()) != null) {
                resultData += inputLine;
            }
            in.close();
            is.close();
        } catch (FileNotFoundException e) {
            Log.e(LOG_TAG, e.getMessage());
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        }
        return resultData;
    }

    /**
     * 处理了前端回调
     */
    public static void jsCallback(WebView webView, int status, String msg,
                                  String callback) {
        if (webView != null && callback != null && callback.length() > 0) {
            try {
                JSONObject param = new JSONObject();
                param.put("status", status);
                param.put("msg", msg);
                String js = "javascript: ~" + callback + "('"
                        + param.toString() + "');";
                Log.e(LOG_TAG, "Util中与web前端交互的参数param.toString()=="+param.toString());
                if (android.os.Build.VERSION.SDK_INT <= 18) {
                    webView.loadUrl(js);
                } else {
                    webView.evaluateJavascript(js, null);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
