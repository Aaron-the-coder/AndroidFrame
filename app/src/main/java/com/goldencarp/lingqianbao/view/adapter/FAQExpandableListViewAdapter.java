package com.goldencarp.lingqianbao.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.goldencarp.lingqianbao.R;
import com.goldencarp.lingqianbao.model.bean.FAQBean;

import java.util.List;

/**
 * Created by sks on 2017/12/4.
 */

public class FAQExpandableListViewAdapter extends BaseExpandableListAdapter {

    private Context mContext = null;
    //    private List<String> mGroupList = null;
    private List<List<FAQBean>> mItemList = null;

    public FAQExpandableListViewAdapter(Context mContext, List<List<FAQBean>> mItemList) {
        this.mContext = mContext;
//        this.mGroupList = mGroupList;
        this.mItemList = mItemList;
    }

    @Override
    public int getGroupCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        List<FAQBean> childList = mItemList.get(groupPosition);
        return childList == null ? 0 : childList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mItemList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mItemList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder groupHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.expendlist_group, null);
            groupHolder = new GroupHolder();
            groupHolder.groupNameTv = convertView.findViewById(R.id.groupname_tv);
            groupHolder.groupImg = convertView.findViewById(R.id.group_img);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }

        if (isExpanded) {
            groupHolder.groupImg.setImageResource(R.mipmap.arrow_up);
        } else {
            groupHolder.groupImg.setImageResource(R.mipmap.arrow_down);
        }
        groupHolder.groupNameTv.setText(mItemList.get(groupPosition).get(0).getGroupName());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ItemHolder itemHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.expendlist_item, null);
            itemHolder = new ItemHolder();
            itemHolder.nameTv = convertView.findViewById(R.id.itemname_tv);
            itemHolder.iconImg = convertView.findViewById(R.id.icon_img);
            itemHolder.infoTv = convertView.findViewById(R.id.info_tv);
            convertView.setTag(itemHolder);
        } else {
            itemHolder = (ItemHolder) convertView.getTag();
        }
        itemHolder.nameTv.setText(mItemList.get(groupPosition).get(childPosition).getQuestion());
        itemHolder.iconImg.setBackgroundResource(R.mipmap.icon1);
        itemHolder.infoTv.setText(mItemList.get(groupPosition).get(childPosition).getAnswer());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "点击了第" + childPosition + "个条目", Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private class GroupHolder {
        TextView groupNameTv;
        ImageView groupImg;
    }

    private class ItemHolder {
        ImageView iconImg;
        TextView nameTv;
        TextView infoTv;
    }
}
