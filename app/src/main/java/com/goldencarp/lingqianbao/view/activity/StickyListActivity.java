package com.goldencarp.lingqianbao.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.goldencarp.lingqianbao.R;
import com.goldencarp.lingqianbao.model.bean.City;
import com.goldencarp.lingqianbao.view.custom.stickydecoration.PowerfulStickyDecoration;
import com.goldencarp.lingqianbao.view.custom.stickydecoration.listener.OnGroupClickListener;
import com.goldencarp.lingqianbao.view.custom.stickydecoration.listener.PowerGroupListener;
import com.goldencarp.lingqianbao.view.util.CityUtil;
import com.goldencarp.lingqianbao.view.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sks on 2018/3/6.
 * 浮动列表
 */

public class StickyListActivity extends BaseActivity {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    List<City> dataList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_list);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        //模拟数据  啦啦啦啦
        dataList.addAll(CityUtil.getCityList());
        dataList.addAll(CityUtil.getCityList());

        //------------- PowerfulStickyDecoration 使用部分  ----------------
        PowerGroupListener listener = new PowerGroupListener() {
            @Override
            public String getGroupName(int position) {
                //获取组名，用于判断是否是同一组
                if (dataList.size() > position) {
                    return dataList.get(position).getProvince();
                }
                return null;
            }

            @Override
            public View getGroupView(int position) {
                //获取自定定义的组View
                if (dataList.size() > position) {
                    View view = getLayoutInflater().inflate(R.layout.item_group, null, false);
                    ((TextView) view.findViewById(R.id.tv)).setText(dataList.get(position).getProvince());
                    return view;
                } else {
                    return null;
                }
            }
        };
        PowerfulStickyDecoration decoration = PowerfulStickyDecoration.Builder
                .init(listener)
                .setGroupHeight(DensityUtil.dip2px(this, 40))     //设置高度
                .setGroupBackground(Color.parseColor("#48BDFF"))        //设置背景   默认透明
                .setDivideColor(Color.parseColor("#CCCCCC"))            //分割线颜色
                .setDivideHeight(DensityUtil.dip2px(this, 1))     //分割线高度
                .setStrongReference(true)                                          //设置强引用
                .setOnClickListener(new OnGroupClickListener() {                   //点击事件，返回当前分组下的第一个item的position
                    @Override
                    public void onClick(int position) {                                 //Group点击事件
                        String content = "onGroupClick --> " + dataList.get(position).getProvince();
                        Toast.makeText(StickyListActivity.this, content, Toast.LENGTH_LONG).show();
                    }
                })
                .build();
        //-------------                  ----------------
        //下面是平时的RecyclerView操作
        RecyclerView.LayoutManager manager;
        String type = getIntent().getStringExtra("type");
        if (TextUtils.equals(type, "grid")) {
            manager = new GridLayoutManager(this, 3);
            decoration.resetSpan(recyclerView, (GridLayoutManager) manager);
        } else {
            manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        }
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(decoration);
        mAdapter = new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view, parent, false);
                return new Holder(view);
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
                Holder holder = (Holder) viewHolder;
                holder.mTextView.setText(dataList.get(position).getName());
            }

            @Override
            public int getItemCount() {
                return dataList.size();
            }
        };
        recyclerView.setAdapter(mAdapter);
    }

    static class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv)
        TextView mTextView;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
