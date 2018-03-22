package com.lyk.jsbridge.widget;


import android.util.Log;

public class DefaultHandler implements BridgeHandler {

    private static final String LOG_TAG = "DefaultHandler";

    @Override
    public void handler(String data, CallBackFunction function) {
        Log.e(LOG_TAG, "h5发送的data==" + data);
        if (function != null) {
            function.onCallBack("DefaultHandler response data");
        }
    }

}
