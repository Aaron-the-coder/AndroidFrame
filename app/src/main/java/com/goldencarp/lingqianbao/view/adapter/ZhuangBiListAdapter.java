package com.goldencarp.lingqianbao.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.goldencarp.lingqianbao.R;
import com.goldencarp.lingqianbao.model.net.ZhuangbiImage;
import com.goldencarp.lingqianbao.view.LQBApp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dale on 2018/2/17.
 */

public class ZhuangBiListAdapter extends RecyclerView.Adapter<ZhuangBiListAdapter.MyViewHolder> {

    private List<ZhuangbiImage> zhuangbiImages;

    public ZhuangBiListAdapter(List<ZhuangbiImage> zhuangbiImages) {
        this.zhuangbiImages = zhuangbiImages;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(LQBApp.getApp()).inflate(R.layout.grid_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.descriptionTv.setText(zhuangbiImages.get(position).getDescription());
        Glide.with(LQBApp.getApp()).load(zhuangbiImages.get(position).getImage_url()).into(holder.imageIv);
    }

    @Override
    public int getItemCount() {
        return zhuangbiImages == null ? 0 : zhuangbiImages.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageIv)
        ImageView imageIv;
        @BindView(R.id.descriptionTv)
        TextView descriptionTv;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void setZhuangbiImages(List<ZhuangbiImage> zhuangbiImages) {
        this.zhuangbiImages = zhuangbiImages;
        notifyDataSetChanged();
    }
}
