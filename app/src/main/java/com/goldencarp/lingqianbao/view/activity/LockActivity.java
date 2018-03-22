package com.goldencarp.lingqianbao.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.goldencarp.lingqianbao.R;
import com.goldencarp.lingqianbao.view.LQBApp;
import com.goldencarp.lingqianbao.view.custom.guesturepwdview.GestureLockLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LockActivity extends AppCompatActivity {

    private static final String LOG_TAG = "LockActivity";
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_setting_hint)
    TextView tvSettingHint;
    @BindView(R.id.l_gesture_view)
    GestureLockLayout lGestureView;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        ButterKnife.bind(this);
        initViews();
        initEvents();
    }

    private void initEvents() {
        lGestureView.setOnLockVerifyListener(new GestureLockLayout.OnLockVerifyListener() {
            @Override
            public void onGestureSelected(int id) {
                //每选中一个点时调用
                Log.d(LOG_TAG, "id:"+id);//这个id是从1开始的不是0,千万注意
            }

            @Override
            public void onGestureFinished(boolean isMatched) {
                //绘制完成时调用
                if (isMatched){
                    //密码正确
                    LQBApp.isUnlocked = true;
                    finish();
                }else {
                    //不匹配
                    tvSettingHint.setText("还有"+lGestureView.getTryTimes()+"次机会");
                    resetGesture();
                }
            }

            @Override
            public void onGestureTryTimesBoundary() {
                //超过尝试次数
                lGestureView.setTouchable(false);
            }
        });
    }

    private void initViews() {
        //设置密码键盘为验证模式
        lGestureView.setMode(GestureLockLayout.VERIFY_MODE);
        //设置点的行列数
        lGestureView.setDotCount(3);
        //设置最大尝试次数
        lGestureView.setTryTimes(5);
        //设置正确解锁答案
        lGestureView.setAnswer(LQBApp.answer);
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

    @Override
    public void onBackPressed() {

    }
}
