package com.lyk.jsbridge;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lyk.jsbridge.widget.BridgeHandler;
import com.lyk.jsbridge.widget.BridgeWebView;
import com.lyk.jsbridge.widget.CallBackFunction;
import com.lyk.jsbridge.widget.DefaultHandler;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;

import static com.lyk.jsbridge.R.id.webView;

public class JsBridgeActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "JsBridgeActivity";
    private static final int RESULT_CODE = 1;
    private Button button;
    private BridgeWebView mWebView;
    ValueCallback<Uri> mUploadMessage;
    private CallBackFunction callBack;
    private Button buttonDefault;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_js_bridge);
        mWebView = findViewById(webView);

        button = findViewById(R.id.button);

        button.setOnClickListener(this);

        buttonDefault = findViewById(R.id.button_default);

        buttonDefault.setOnClickListener(this);

        mWebView.setDefaultHandler(new DefaultHandler());

        mWebView.setWebChromeClient(new WebChromeClient() {

            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType, String capture) {
                this.openFileChooser(uploadMsg);
            }

            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType) {
                this.openFileChooser(uploadMsg);
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                mUploadMessage = uploadMsg;
                pickFile();
            }
        });

        mWebView.loadUrl("http://192.168.1.142/jjcwx/h5_native_demo/demo.html");
//        mWebView.loadUrl("file:///android_asset/demo.html");

        mWebView.registerHandler("submitFromWeb", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i(TAG, "h5通过submitFromWeb方法传来的data==" + data);
                function.onCallBack("submitFromWeb exe, response data 中文 from Java");
            }

        });
        //js调native
        mWebView.registerHandler("submitLogin", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i(TAG, "h5通过submitFromWeb方法传来的data==" + data);
                function.onCallBack("登录成功");
            }

        });
//        mWebView.registerHandler("functionOpen", new BridgeHandler() {
//
//            @Override
//            public void handler(String data, CallBackFunction function) {
//                Log.i(TAG, "h5通过functionOpen方法传来的data==" + data);
//                callBack = function;
//                pickFile();
////                function.onCallBack("submitFromWeb exe, response data 中文 from Java");
//            }
//
//        });

        User user = new User();
        Location location = new Location();
        location.address = "北京";
        user.location = location;
        user.name = "王晓乐";

        mWebView.callHandler("functionInJs", new Gson().toJson(user), new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                Toast.makeText(JsBridgeActivity.this, data, Toast.LENGTH_SHORT).show();
            }
        });


        mWebView.send("hello");
    }

    public void pickFile() {
        Intent chooserIntent = new Intent(Intent.ACTION_GET_CONTENT);
        chooserIntent.setType("image/*");
        startActivityForResult(chooserIntent, RESULT_CODE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                User user = new User();
                Location location = new Location();
                location.address = "北京";
                user.location = location;
                user.name = "王晓乐";
//                mWebView.callHandler("functionInJs", new Gson().toJson(user), new CallBackFunction() {
//
//                    @Override
//                    public void onCallBack(String data) {
//                        Toast.makeText(JsBridgeActivity.this, data, Toast.LENGTH_SHORT).show();
//                    }
//
//                });
                //native调js
                mWebView.callHandler("loginResult", new Gson().toJson(user), new CallBackFunction() {
                    @Override
                    public void onCallBack(String data) {
                        Toast.makeText(JsBridgeActivity.this, data, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.button_default:
                mWebView.send("hello");
                break;
        }

    }

    static class Location {
        String address;
    }

    static class User {
        String name;
        Location location;
        String testStr;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_CODE:
//                data.getBundleExtra()
                callBack.onCallBack("这是一张图片");
                break;
        }
    }
}
