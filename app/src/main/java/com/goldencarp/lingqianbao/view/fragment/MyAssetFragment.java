package com.goldencarp.lingqianbao.view.fragment;


import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.goldencarp.lingqianbao.R;
import com.goldencarp.lingqianbao.presenter.IMyAssetFragmentPresenter;
import com.goldencarp.lingqianbao.presenter.MyAssetFragmentPresenter;
import com.goldencarp.lingqianbao.view.LQBApp;
import com.goldencarp.lingqianbao.view.adapter.DemoListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */

public class MyAssetFragment extends BaseFragment {

    private static final String LOG_TAG = "MyAssetFragment";

    private final IMyAssetFragmentPresenter mPresenter;
    Unbinder unbinder;

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    private List<String> demoNameList;

    public MyAssetFragment() {
        // Required empty public constructor
        mPresenter = new MyAssetFragmentPresenter();
    }


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        super.onCreateView(inflater, container, savedInstanceState);
//        View view = inflater.inflate(R.layout.fragment_my_asset, container, false);
//        unbinder = ButterKnife.bind(this, view);
//        return view;
//    }

    @Override
    protected Object getContentView() {
        View view = LayoutInflater.from(LQBApp.getApp()).inflate(R.layout.fragment_my_asset, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initData() {
        stateLayout.showContentView();
        demoNameList = mPresenter.getDemoData();
    }

    @Override
    protected void initView() {
        rvList.setLayoutManager(new LinearLayoutManager(LQBApp.getApp()));
        rvList.setAdapter(new DemoListAdapter(getActivity(), demoNameList));
    }





    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

}

