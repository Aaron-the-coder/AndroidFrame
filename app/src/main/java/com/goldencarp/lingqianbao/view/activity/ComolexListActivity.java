package com.goldencarp.lingqianbao.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goldencarp.lingqianbao.R;
import com.goldencarp.lingqianbao.view.LQBApp;
import com.goldencarp.lingqianbao.view.adapter.ComplexListAdapter;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ComolexListActivity extends AppCompatActivity {

    @BindView(R.id.complex_recyclerview)
    XRecyclerView complexRecyclerview;
    private ArrayList<String> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comolex_list);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        datas = new ArrayList<>();
        for(int i = 0; i < 15 ;i++){
            datas.add("item" + i);
        }
        complexRecyclerview.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        complexRecyclerview.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        complexRecyclerview.setArrowImageView(R.mipmap.iconfont_downgrey);
        View header1 = LayoutInflater.from(LQBApp.getApp()).inflate(R.layout.recyclerview_header, (ViewGroup)findViewById(android.R.id.content),false);
        complexRecyclerview.addHeaderView(header1);
        View header2 = LayoutInflater.from(LQBApp.getApp()).inflate(R.layout.recyclerview_header, (ViewGroup)findViewById(android.R.id.content),false);
        complexRecyclerview.addHeaderView(header2);
        header2.setBackgroundColor(Color.parseColor("#7AD237"));
        View header3 = LayoutInflater.from(LQBApp.getApp()).inflate(R.layout.recyclerview_header, (ViewGroup)findViewById(android.R.id.content),false);
        complexRecyclerview.addHeaderView(header3);
        header2.setBackgroundColor(Color.parseColor("#919191"));
        complexRecyclerview.setLayoutManager(new LinearLayoutManager(LQBApp.getApp()));
        complexRecyclerview.setAdapter(new ComplexListAdapter(datas));
    }
}
