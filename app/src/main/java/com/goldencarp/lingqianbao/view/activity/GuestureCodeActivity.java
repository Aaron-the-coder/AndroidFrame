package com.goldencarp.lingqianbao.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.goldencarp.lingqianbao.R;
import com.goldencarp.lingqianbao.view.LQBApp;
import com.goldencarp.lingqianbao.view.custom.guesturepwdview.GestureLockDisplayView;
import com.goldencarp.lingqianbao.view.custom.guesturepwdview.GestureLockLayout;
import com.goldencarp.lingqianbao.view.custom.guesturepwdview.JDLockView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuestureCodeActivity extends BaseActivity {


    private static final String LOG_TAG = "GuestureCodeActivity";
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.l_display_view)
    GestureLockDisplayView lDisplayView;
    @BindView(R.id.tv_setting_hint)
    TextView tvSettingHint;
    @BindView(R.id.l_gesture_view)
    GestureLockLayout lGestureView;
    private static Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guesture_code);
        ButterKnife.bind(this);
        initViews();
        initEvents();
    }

    private void initEvents() {
        lGestureView.setOnLockResetListener(new GestureLockLayout.OnLockResetListener() {
            @Override
            public void onConnectCountUnmatched(int connectCount, int minCount) {
                //绘制的点数不够时调用
                tvSettingHint.setText("至少绘制"+minCount+"个点");
                resetGesture();
            }

            @Override
            public void onFirstPasswordFinished(List<Integer> answerList) {
                //第一次绘制完手势密码时调用
                tvSettingHint.setText("请再次绘制手势密码");
                lDisplayView.setAnswer(answerList);
                resetGesture();
            }

            @Override
            public void onSetPasswordFinished(boolean isMatched, List<Integer> answerList) {
                //绘制完成时调用
                if (isMatched){
                    //手势密码设置成功
                    String guestureCode = answerList.toString();
                    Log.e(LOG_TAG, "guestureCode:"+guestureCode);
                    LQBApp.answer = guestureCode;
                    LQBApp.isUnlocked = false;
                    finish();
                }else {
                    resetGesture();
                }
            }
        });
    }

    private void initViews() {
        //设置上方展示控件点的行列数
        lDisplayView.setDotCount(3);
        //展示图标选中点的颜色
        lDisplayView.setDotSelectedColor(Color.parseColor("#01A0E5"));
        //展示图标未选中点的颜色
        lDisplayView.setDotUnSelectedColor(Color.BLACK);
        //设置手势密码点的行列数
        lGestureView.setDotCount(3);
        //设置手势密码最少的点个数
        lGestureView.setMinCount(4);
        //设置解锁样式为京东手势样式
        lGestureView.setLockView(new JDLockView(this));
        lGestureView.setMode(GestureLockLayout.RESET_MODE);
    }

    /**
     * 重置
     */
    private void resetGesture() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                lGestureView.resetGesture();
            }
        }, 200);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null){
            mHandler.removeCallbacksAndMessages(null);
        }
    }
}
