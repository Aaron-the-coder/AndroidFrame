package com.goldencarp.lingqianbao.presenter;

import android.app.Activity;

import com.goldencarp.lingqianbao.view.activity.CollapseListActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by sks on 2018/2/9.
 */

public class CollapseListActivityPresenter {

    private final WeakReference<Activity> activity;
    private int refreshTimes;//刷新的次数
    private int loadMoreTimes;//加载更多的次数

    public CollapseListActivityPresenter(CollapseListActivity activity) {
        this.activity = new WeakReference<Activity>(activity);
    }

    /**
     * 列表刷新
     */
    public ArrayList<String> refreshData() {
        final ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add("第" + refreshTimes + "次刷新," + "第" + i + "条数据");
        }

        refreshTimes++;
        return data;
    }

    /**
     * 加载更多
     */
    public ArrayList<String> loadMore() {
        final ArrayList<String> data = new ArrayList<>();
        if (loadMoreTimes < 2) {
            for (int i = 0; i < 10; i++) {
                data.add("第" + loadMoreTimes + "次加载更多," + "第" + i + "条数据");
            }
        }
        loadMoreTimes++;
        return data;
    }
}
