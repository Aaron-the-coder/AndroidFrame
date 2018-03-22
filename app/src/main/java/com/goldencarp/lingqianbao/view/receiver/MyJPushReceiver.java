package com.goldencarp.lingqianbao.view.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.goldencarp.lingqianbao.view.util.Logger;

import cn.jpush.android.api.JPushInterface;

public class MyJPushReceiver extends BroadcastReceiver {

    private static final java.lang.String LOG_TAG = "MyJPushReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            if (intent.getAction().equals(JPushInterface.ACTION_REGISTRATION_ID)) {
                String registrationId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                Logger.e(LOG_TAG, "registrationId=" + registrationId);
            } else if (intent.getAction().equals(JPushInterface.ACTION_MESSAGE_RECEIVED)) {
                String title = bundle.getString(JPushInterface.EXTRA_TITLE);
                String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
                String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
                String file = bundle.getString(JPushInterface.EXTRA_MSG_ID);
                Logger.e(LOG_TAG, "MESSAGE_RECEIVED={" + "title:" + title + "   message:" + message + "   extras:" + extras + "file:" + file + "}");
            }else if (intent.getAction().equals(JPushInterface.ACTION_NOTIFICATION_RECEIVED)){
                String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
                String content = bundle.getString(JPushInterface.EXTRA_ALERT);
                String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
                int notificationId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                String fileHtml = bundle.getString(JPushInterface.EXTRA_RICHPUSH_HTML_PATH);
                String fileStr = bundle.getString(JPushInterface.EXTRA_RICHPUSH_HTML_RES);
                if (!TextUtils.isEmpty(fileStr)){
                    String[] fileNames = fileStr.split(",");
                }
                String file = bundle.getString(JPushInterface.EXTRA_MSG_ID);
                String bigText = bundle.getString(JPushInterface.EXTRA_BIG_TEXT);
                String bigPicPath = bundle.getString(JPushInterface.EXTRA_BIG_PIC_PATH);
                String inboxJson = bundle.getString(JPushInterface.EXTRA_INBOX);
                String prio = bundle.getString(JPushInterface.EXTRA_NOTI_PRIORITY);
                String prio1 = bundle.getString(JPushInterface.EXTRA_NOTI_CATEGORY);
            }else if (intent.getAction().equals(JPushInterface.ACTION_NOTIFICATION_OPENED)){
                String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
                String content = bundle.getString(JPushInterface.EXTRA_ALERT);
                String type = bundle.getString(JPushInterface.EXTRA_EXTRA);
                int notificationId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                String file = bundle.getString(JPushInterface.EXTRA_MSG_ID);
            }else if (intent.getAction().equals(JPushInterface.ACTION_CONNECTION_CHANGE)){
                boolean connected = bundle.getBoolean(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            }else {
                Logger.d(LOG_TAG, "非法的jpushAction:" + intent.getAction());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
