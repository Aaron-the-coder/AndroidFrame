package com.goldencarp.lingqianbao.view.activity;


import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sks on 2017/11/9.
 */

public class ActivityCollector {
    static List<Activity> list = new ArrayList<>();
    public static void addActivity(Activity activity){
        list.add(activity);
    }
    public static void removeActivity(Activity activity){
        list.remove(activity);
    }

    public static void finishAll(){
        for (android.app.Activity Activity : list) {
            if (!Activity.isFinishing()){
                Activity.finish();
            }
        }
    }
}
