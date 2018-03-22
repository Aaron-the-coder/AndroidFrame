package com.goldencarp.lingqianbao.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goldencarp.lingqianbao.R;


/**
 * Created by sks on 2017/12/4.
 */

public class PersonalCenterItemView extends RelativeLayout {

    private ImageView ivIcon;
    private TextView tvItemName;
    private TextView tvMsgAmount;
    private TextView tvTipRight;
    private int icon;//图标资源id
    private int name;//条目名称id
    private boolean showMsgAmount;//显示消息数量
    private boolean showTipRight;//显示右侧提示信息

    public PersonalCenterItemView(Context context) {
        this(context, null);
    }

    public PersonalCenterItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PersonalCenterItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.personalcenter_item_attr);
        icon = mTypedArray.getResourceId(R.styleable.personalcenter_item_attr_icon, -1);
        name = mTypedArray.getResourceId(R.styleable.personalcenter_item_attr_name, -1);
        showMsgAmount = mTypedArray.getBoolean(R.styleable.personalcenter_item_attr_show_msg_amount, false);
        showTipRight = mTypedArray.getBoolean(R.styleable.personalcenter_item_attr_show_tip_right, false);
        //获取资源后要及时回收
        mTypedArray.recycle();
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_personal_center, this, true);
        ivIcon = view.findViewById(R.id.iv_icon);
        tvItemName = view.findViewById(R.id.tv_item_name);
        tvMsgAmount = view.findViewById(R.id.tv_new_msg_amount);
        tvTipRight = view.findViewById(R.id.tv_tip_right);
        if (icon != -1) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), icon);
            ivIcon.setImageBitmap(bitmap);
        }
        if (name != -1) {
            String itemName = getResources().getString(name);
            tvItemName.setText(itemName);
        }
        if (showMsgAmount) {
            tvMsgAmount.setVisibility(View.VISIBLE);
        }
        if (showTipRight) {
            tvTipRight.setVisibility(View.VISIBLE);
        }
    }

    public void setMsgAmount(int msgAmount) {
        tvMsgAmount.setText(String.valueOf(msgAmount));
    }
}
