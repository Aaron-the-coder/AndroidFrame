package com.goldencarp.lingqianbao.view.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goldencarp.lingqianbao.R;
import com.goldencarp.lingqianbao.view.custom.StateLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment {


    protected StateLayout stateLayout;

    public BaseFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        stateLayout = (StateLayout) inflater.inflate(R.layout.state_layout, null);
        stateLayout.setContentView(getContentView());
        initData();
        initView();
        return stateLayout;
    }

    /**
     * 设置正常显示的布局
     */
    protected abstract Object getContentView();

    protected abstract void initData();

    protected abstract void initView();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
