package com.goldencarp.lingqianbao.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import com.goldencarp.lingqianbao.R;
import com.goldencarp.lingqianbao.presenter.CollapseListActivityPresenter;
import com.goldencarp.lingqianbao.view.LQBApp;
import com.goldencarp.lingqianbao.view.adapter.CollapseListAdapter;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CollapseListActivity extends BaseActivity {

    private static final String LOG_TAG = "CollapseListActivity";
    @BindView(R.id.backdrop)
    ImageView backdrop;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.recyclerview)
    XRecyclerView recyclerview;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;
    private CollapseListActivityPresenter mPresenter;
    private CollapseListAdapter mAdapter;
    private ArrayList<String> listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collapse_list);
        ButterKnife.bind(this);
        mPresenter = new CollapseListActivityPresenter(this);
        initView();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(LQBApp.getApp()));
        recyclerview.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recyclerview.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        recyclerview.setArrowImageView(R.mipmap.iconfont_downgrey);
        recyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                ArrayList<String> data = mPresenter.refreshData();
//                listData = data == null || data.isEmpty() ? listData : data;
                if (data != null && !data.isEmpty()) {
                    listData.clear();
                    listData.addAll(data);
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                        recyclerview.refreshComplete();
                    }
                }, 1000);

            }

            @Override
            public void onLoadMore() {
                Log.e(LOG_TAG, "---------onLoadMore---------");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<String> data = mPresenter.loadMore();
                        if (data != null && !data.isEmpty()) {
                            listData.addAll(data);
                            recyclerview.loadMoreComplete();
                        } else {
                            recyclerview.setNoMore(true);
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                }, 1000);
            }
        });

        listData = new ArrayList<String>();
        for (int i = 0; i < 15; i++) {
            listData.add("item" + i);
        }
        mAdapter = new CollapseListAdapter(listData);
        recyclerview.setAdapter(mAdapter);
    }
}
