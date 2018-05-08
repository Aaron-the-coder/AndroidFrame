package com.goldencarp.lingqianbao.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.goldencarp.lingqianbao.R;
import com.goldencarp.lingqianbao.view.activity.AsyncTestActivity;
import com.goldencarp.lingqianbao.view.activity.CacheCleanActivity;
import com.goldencarp.lingqianbao.view.activity.CompressActivity;
import com.goldencarp.lingqianbao.view.activity.CrashActivity;
import com.goldencarp.lingqianbao.view.activity.DealCodeActivity;
import com.goldencarp.lingqianbao.view.activity.DemoActivity;
import com.goldencarp.lingqianbao.view.activity.GuestureCodeActivity;
import com.goldencarp.lingqianbao.view.activity.ListsActivity;
import com.goldencarp.lingqianbao.view.activity.LoginActivity;
import com.goldencarp.lingqianbao.view.activity.NetActivity;
import com.goldencarp.lingqianbao.view.activity.PermissionActivity;
import com.goldencarp.lingqianbao.view.activity.PushActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dale on 2018/1/21.
 * demo列表适配器
 */

public class DemoListAdapter extends RecyclerView.Adapter<DemoListAdapter.MyViewHolder> {
    private final List<String> demoNameList;
    private final Context mContext;


    public DemoListAdapter(Context context, List<String> demoNameList) {
        this.mContext = context;
        this.demoNameList = demoNameList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_demo, parent, false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tvDemoName.setText(demoNameList.get(position));
        holder.llItemRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (position) {
                    case 0:
                        Toast.makeText(mContext, "请移步hydriddemo演示", Toast.LENGTH_SHORT).show();
                        Intent passwordIntent = new Intent(mContext, LoginActivity.class);
                        mContext.startActivity(passwordIntent);
                        break;
                    case 1:
                        Toast.makeText(mContext, "请移步partyLoginDemo演示", Toast.LENGTH_SHORT).show();
                        Intent demoIntent = new Intent(mContext, DemoActivity.class);
                        mContext.startActivity(demoIntent);
                        break;
                    case 2:
                        Intent intent = new Intent(mContext, PushActivity.class);
                        mContext.startActivity(intent);
                        break;
                    case 3:
                        Intent cacheIntent = new Intent(mContext, CacheCleanActivity.class);
                        mContext.startActivity(cacheIntent);
                        break;
                    case 4:
                        Intent gestureCodeIntent = new Intent(mContext, GuestureCodeActivity.class);
                        mContext.startActivity(gestureCodeIntent);
                        break;
                    case 5:
                        Intent DealCodeIntent = new Intent(mContext, DealCodeActivity.class);
                        mContext.startActivity(DealCodeIntent);
                        break;
                    case 6:
                        Intent listIntent = new Intent(mContext, ListsActivity.class);
                        mContext.startActivity(listIntent);
                        break;
                    case 7:
                        Intent netIntent = new Intent(mContext, NetActivity.class);
                        mContext.startActivity(netIntent);
                        break;
                    case 8://异步处理
                        Intent asyncIntent = new Intent(mContext, AsyncTestActivity.class);
                        mContext.startActivity(asyncIntent);
                        break;
                    case 9://崩溃日志
                        Intent crashIntent = new Intent(mContext, CrashActivity.class);
                        mContext.startActivity(crashIntent);
                        break;
                    case 10://动态权限
                        Intent permissionIntent = new Intent(mContext, PermissionActivity.class);
                        mContext.startActivity(permissionIntent);
                        break;
                    case 11://图片压缩
                        Intent compressIntent = new Intent(mContext, CompressActivity.class);
                        mContext.startActivity(compressIntent);
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return demoNameList == null ? 0 : demoNameList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_demo_name)
        TextView tvDemoName;
        @BindView(R.id.ll_item_root)
        ViewGroup llItemRoot;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
