package com.goldencarp.lingqianbao.view.fragment;


import android.support.v4.app.Fragment;
import android.view.LayoutInflater;

import com.goldencarp.lingqianbao.R;
import com.goldencarp.lingqianbao.presenter.DiscoverFragmentPresenter;
import com.goldencarp.lingqianbao.view.LQBApp;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoverFragment extends BaseFragment {


    private DiscoverFragmentPresenter mPresenter;

    public DiscoverFragment() {
        // Required empty public constructor
    }

    @Override
    protected Object getContentView() {
        return LayoutInflater.from(LQBApp.getApp()).inflate(R.layout.fragment_discover, null);
    }

    @Override
    protected void initData() {
        mPresenter = new DiscoverFragmentPresenter();
        stateLayout.showContentView();
    }

    @Override
    protected void initView() {

    }

}
