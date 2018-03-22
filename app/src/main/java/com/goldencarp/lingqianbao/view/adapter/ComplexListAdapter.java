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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jianghejie on 15/11/26.
 */
public class ComplexListAdapter extends RecyclerView.Adapter {

    private static final int TYPE_SPECIAL_RECOMMEND = 100;

    public void setClickCallBack(ItemClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }

    public interface ItemClickCallBack {
        void onItemClick(int pos);
    }

    public ArrayList<String> datas = null;
    private ItemClickCallBack clickCallBack;

    public ComplexListAdapter(ArrayList<String> datas) {
        this.datas = datas;
    }

    public void setDatas(ArrayList<String> datas) {
        this.datas = datas;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view;
        RecyclerView.ViewHolder holder;
        if (viewType == TYPE_SPECIAL_RECOMMEND) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product, viewGroup, false);
            holder = new ViewHolder1(view);
        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
            holder = new ViewHolder(view);
        }
        return holder;
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int newPos = position;
        if (position == 6){
            ComplexListAdapter.ViewHolder1 viewHolder = (ViewHolder1) holder;
        }else {
            ComplexListAdapter.ViewHolder viewHolder = (ViewHolder) holder;
            if (position<=6){
                newPos = position;
            }else {
                newPos = position-1;
            }
            viewHolder.mTextView.setText(datas.get(newPos));
            viewHolder.mTextView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (clickCallBack != null) {
                                clickCallBack.onItemClick(position);
                            }
                        }
                    }
            );
        }
    }

//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
//        super.onBindViewHolder(holder, position, payloads);
//    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        int type;
        switch (position) {
            case 6:
                type = TYPE_SPECIAL_RECOMMEND;
                break;
            default:
                type = super.getItemViewType(position);
                break;
        }
        return type;
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public ViewHolder(View view) {
            super(view);
            mTextView = view.findViewById(R.id.text);
        }
    }

    class ViewHolder1 extends RecyclerView.ViewHolder{
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
        @BindView(R.id.bottom_line)
        View bottomLine;

        ViewHolder1(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}





















