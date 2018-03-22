package com.goldencarp.lingqianbao.view.custom.visualkeyboard;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.goldencarp.lingqianbao.R;
import com.goldencarp.lingqianbao.view.LQBApp;
import com.goldencarp.lingqianbao.view.util.Util;

import java.util.List;

/**
 * 仿支付宝支付布局
 * Created by ayuhani on 2017/7/4.
 */
public class AliPayWindow extends PopupWindow implements KeyboardAdapter.OnKeyboardClickListener {

    private AliPasswordView passwordView;
    private KeyboardView keyboardView;
    private ImageView ivClose;
    private List<String> datas;
    private String[] numbers;
    private ImageView[] points;
    private int currentIndex;   // 当前即将要输入密码的格子的索引
    public OnPasswordFinishedListener listener;
    private TextView tvTip;

    public AliPayWindow(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.layout_ali_pay, null);
        setContentView(contentView);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setFocusable(true);
        setClippingEnabled(false); // 让PopupWindow同样覆盖状态栏
        setBackgroundDrawable(new ColorDrawable(0x88000000)); // 加上一层黑色透明背景
        initView(contentView);
    }

    private void initView(View contentView) {
        passwordView = contentView.findViewById(R.id.password_view);
        keyboardView = contentView.findViewById(R.id.keyboard_view);
        tvTip = contentView.findViewById(R.id.tv_lap_tip);
//        keyboardView.getLlBack().setVisibility(View.GONE); // 隐藏收起按键
        keyboardView.setOnKeyBoardClickListener(this);
        ivClose = contentView.findViewById(R.id.iv_close);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        datas = keyboardView.getDatas();
        numbers = passwordView.getNumbers();
        points = passwordView.getPoints();
    }

    // 弹出PopupWindow
    public void show(View rootView) {
        Point position = Util.getNavigationBarSize(LQBApp.getApp());
        showAtLocation(rootView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, position.y);
//        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    @Override
    public void onKeyClick(View view, RecyclerView.ViewHolder holder, int position) {
        switch (position) {
            case 9: // 点击小数点没有作用，最好是把小数点隐藏掉，我这里偷懒了
                break;
            default:
                if (currentIndex >= 0 && currentIndex < numbers.length) {
                    numbers[currentIndex] = datas.get(position);
                    points[currentIndex].setVisibility(View.VISIBLE);
                    currentIndex++; // 当前位置的密码输入后，位置加一

                    if (currentIndex == numbers.length && listener != null) {
                        // 已经输入了六位数的密码了，回调方法
                        listener.onFinish(passwordView.getPassword());
                    }
                }
        }
    }

    @Override
    public void onDeleteClick(View view, RecyclerView.ViewHolder holder, int position) {
        // 点击删除按钮
        if (currentIndex > 0 && currentIndex <= numbers.length) {
            currentIndex--;
            numbers[currentIndex] = "";
            points[currentIndex].setVisibility(View.GONE);
        }
    }

    public void setOnPasswordFinishedListener(OnPasswordFinishedListener listener) {
        this.listener = listener;
    }

    public View getPasswordView(){
        return passwordView;
    }

    public View getTvTip(){
        return tvTip;
    }
}
