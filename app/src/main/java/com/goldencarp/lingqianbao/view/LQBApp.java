package com.goldencarp.lingqianbao.view;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.goldencarp.lingqianbao.model.dao.gen.DaoMaster;
import com.goldencarp.lingqianbao.model.dao.gen.DaoSession;
import com.goldencarp.lingqianbao.view.util.CrashHandler;
import com.tencent.smtt.sdk.QbSdk;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by sks on 2017/12/4.
 */

public class LQBApp extends Application {
    private static LQBApp mApp;
    private static DaoSession daoSession;//greendao会话对象
    public static boolean isUnlocked = false;//手势密码已解锁
    public static String answer;//手势密码

    @Override
    public void onCreate() {
        super.onCreate();
        if (mApp == null) {
            mApp = this;
        }
        initCrashHandler();
        initX5Webview();
        initJpushSDK();
        setupDatabase();
    }

    /**
     * 配置数据库
     */
    private void setupDatabase() {
        //创建数据库shop.db"
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "shop.db", null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }

    /**
     * 初始化极光推送sdk
     */
    private void initJpushSDK() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    /**初始化x5内核环境*/
    private void initX5Webview() {
//搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。

        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(),  cb);
    }

    public static LQBApp getApp() {
        return mApp;
    }
    /**
     * 初始化崩溃处理器
     */
    private void initCrashHandler() {
        CrashHandler crashHandler = CrashHandler.getInstance();
        // 注册crashHandler
        crashHandler.init(getApplicationContext());
    }

}
