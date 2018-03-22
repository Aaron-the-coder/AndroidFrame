package com.goldencarp.lingqianbao.view.custom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.goldencarp.lingqianbao.R;
import com.goldencarp.lingqianbao.view.LQBApp;

/**
 * Created by sks on 2018/1/18.
 */

public class StateLayout extends FrameLayout {

    private View loadingView;//加载控件
    private View emptyView;//无数据控件
    private View failView;//加载失败控件
    private View contentView;//正常内容界面

    public StateLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        showLoadingView();
    }

    /**
     * 显示加载界面
     */
    public void showLoadingView() {
        showView(loadingView);
    }

    /**
     * 显示失败界面
     */
    public void showFailureView() {
        showView(failView);
    }

    /**
     * 显示空白界面
     */
    public void showEmptyView() {
        showView(emptyView);
    }

    /**
     * 显示内容界面
     */
    public void showContentView() {
        showView(contentView);
    }

    /**
     * 显示某个view,并隐藏其他的view
     */
    public void showView(View view) {
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            if (childView == view) {
                childView.setVisibility(View.VISIBLE);
            } else {
                childView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //布局填充完成,可以寻找控件了
        loadingView = findViewById(R.id.pb_loading_view);
        emptyView = findViewById(R.id.iv_empty_view);
        failView = findViewById(R.id.ll_failure_view);
    }

    public void setContentView(Object viewOrLayoutId) {
        if (viewOrLayoutId == null) {
            throw new IllegalArgumentException();
        }
        if (viewOrLayoutId instanceof Integer) {
            int id = (int) viewOrLayoutId;
            contentView = LayoutInflater.from(LQBApp.getApp()).inflate(id, null);
        } else {
            contentView = (View) viewOrLayoutId;
        }
        addView(contentView);
        contentView.setVisibility(View.GONE);
    }
}
