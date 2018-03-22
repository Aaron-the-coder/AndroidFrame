package com.goldencarp.lingqianbao.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.goldencarp.lingqianbao.R;
import com.goldencarp.lingqianbao.model.bean.ProductBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sks on 2017/12/2.
 */

public class ProductListAdapter extends RecyclerView.Adapter {

    private List<ProductBean> mList;

    public ProductListAdapter(List<ProductBean> mList) {
        this.mList = mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_category)
        TextView tvCategory;
        @BindView(R.id.tv_desc)
        TextView tvDesc;
        @BindView(R.id.iv_open_detail)
        ImageView ivOpenDetail;
        @BindView(R.id.ll_head)
        LinearLayout llHead;
        @BindView(R.id.tv_product_name)
        TextView tvProductName;
        @BindView(R.id.tv_pre_rate)
        TextView tvPreRate;
        @BindView(R.id.tv_rate_left)
        TextView tvRateLeft;
        @BindView(R.id.tv_rate_right)
        TextView tvRateRight;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_buy_now)
        TextView tvBuyNow;
        @BindView(R.id.pb_rest)
        ProgressBar pbRest;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
