package com.goldencarp.lingqianbao.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.goldencarp.lingqianbao.R;
import com.goldencarp.lingqianbao.model.net.HttpUtil;
import com.goldencarp.lingqianbao.model.net.ZhuangbiImage;
import com.goldencarp.lingqianbao.view.LQBApp;
import com.goldencarp.lingqianbao.view.adapter.ZhuangBiListAdapter;
import com.goldencarp.lingqianbao.view.util.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ElementaryFragment extends RxBaseFragment {

    private static final java.lang.String LOG_TAG = "ElementaryFragment";
    @BindView(R.id.searchRb1)
    AppCompatRadioButton searchRb1;
    @BindView(R.id.searchRb2)
    AppCompatRadioButton searchRb2;
    @BindView(R.id.searchRb3)
    AppCompatRadioButton searchRb3;
    @BindView(R.id.searchRb4)
    AppCompatRadioButton searchRb4;
    @BindView(R.id.gridRv)
    RecyclerView gridRv;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    Unbinder unbinder;
    @BindView(R.id.rgChangeMode)
    RadioGroup rgChangeMode;
    private ZhuangBiListAdapter zhuangBiListAdapter;

    public ElementaryFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_elementary, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    private void initView() {
        zhuangBiListAdapter = new ZhuangBiListAdapter(null);
        //初始化列表
        gridRv.setLayoutManager(new GridLayoutManager(LQBApp.getApp(), 2));
        gridRv.setAdapter(zhuangBiListAdapter);
        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN);
        rgChangeMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                unsuscribe();//rxjava取消订阅
                switch (checkedId) {
                    case R.id.searchRb1:
                        search(searchRb1.getText().toString());
                        break;
                    case R.id.searchRb2:
                        search(searchRb2.getText().toString());
                        break;
                    case R.id.searchRb3:
                        search(searchRb3.getText().toString());
                        break;
                    case R.id.searchRb4:
                        search(searchRb4.getText().toString());
                        break;
                }
            }
        });
    }

    private void initData() {
        //开始默认网络请求
        search("可爱");
    }

    /**
     * 搜索图片
     */
    private void search(String key) {
        swipeRefreshLayout.setRefreshing(true);
        unsuscribe();
        disposable = HttpUtil.getZhuangBiApi().search(key).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ZhuangbiImage>>() {
                    @Override
                    public void accept(@NonNull List<ZhuangbiImage> zhuangbiImages) throws Exception {
                        Logger.e(LOG_TAG, "==网络请求成功==");
                        //进度条停止
                        swipeRefreshLayout.setRefreshing(false);
                        //给列表设置数据
                        zhuangBiListAdapter.setZhuangbiImages(zhuangbiImages);
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(LQBApp.getApp(), "请求失败,请刷新重试", Toast.LENGTH_SHORT).show();
                        Logger.e(LOG_TAG, "==网络请求错误==");
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
