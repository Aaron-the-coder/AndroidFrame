package com.goldencarp.lingqianbao.view.activity;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.goldencarp.lingqianbao.R;
import com.goldencarp.lingqianbao.model.bean.ProductBean;
import com.goldencarp.lingqianbao.presenter.IMainActivityPresenter;
import com.goldencarp.lingqianbao.presenter.MainActivityPresenter;
import com.goldencarp.lingqianbao.view.adapter.SimpleFragmentPagerAdapter;
import com.goldencarp.lingqianbao.view.service.FloatWindowService;
import com.goldencarp.lingqianbao.view.util.MyWindowManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    private static final int OVERLAY_PERMISSION_REQ_CODE = 1000;
    private static final String TAG = "MainActivity";
    private static final String LOG_TAG = "MainActivity";
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.sliding_tabs)
    TabLayout slidingTabs;
    @BindView(R.id.et_test)
    EditText etTest;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    private SimpleFragmentPagerAdapter mPagerAdapter;
    private IMainActivityPresenter mPresenter;//数据处理类
    private WindowManager.LayoutParams mParams;//悬浮窗的参数
    private boolean hasOverlayPermission;//有悬浮窗权限

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initData();
        initView();
    }

    protected void initData() {
        mPresenter = new MainActivityPresenter();
        List<ProductBean> products = mPresenter.requestRecommendProducts();
    }

    protected void initView() {
        hideStatusBar();
        mPagerAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), this);
        viewpager.setAdapter(mPagerAdapter);
        slidingTabs.setupWithViewPager(viewpager);
        slidingTabs.setTabMode(TabLayout.MODE_FIXED);
        slidingTabs.setTabGravity(TabLayout.GRAVITY_FILL);
        for (int i = 0; i < slidingTabs.getTabCount(); i++) {
            TabLayout.Tab tab = slidingTabs.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(mPagerAdapter.getTabView(i));
            }
            if (i == 0) {
                if (tab != null) {
                    tab.select();
                }
            }
        }
        navigationView.setItemIconTintList(null);
        requestDrawOverLays();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 显示一个请求悬浮窗功能的dialog
     */
    private void requestOverlayPermissionDialog() {
        new AlertDialog.Builder(this).setMessage("开启悬浮窗才能通过摇一摇功能获得更多优惠券").setPositiveButton("开启", new DialogInterface.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + MainActivity.this.getPackageName()));
                startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
            }
        }).setNegativeButton("寡人偏偏不要", null).show();
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (hasOverlayPermission) {
            //开启悬浮窗
            MyWindowManager.createSmallWindow(getApplicationContext());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //关闭悬浮窗服务
        Intent intent = new Intent(this, FloatWindowService.class);
        stopService(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (hasOverlayPermission) {
            //关闭悬浮窗
            MyWindowManager.removeSmallWindow(getApplicationContext());
        }

    }

    /**
     * 请求显示悬浮窗权限
     */
    public void requestDrawOverLays() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(MainActivity.this)) {
                hasOverlayPermission = false;
                //没有悬浮窗权限,去申请
                requestOverlayPermissionDialog();
            } else {
                // Already hold the SYSTEM_ALERT_WINDOW permission, do addview or something.
                hasOverlayPermission = true;
                showShakeByShake();
            }
        } else {
            hasOverlayPermission = true;
            showShakeByShake();
        }
    }

    /**
     * 显示摇一摇
     */
    private void showShakeByShake() {
//        mWM = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
//        mParams = new WindowManager.LayoutParams();
//        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
//        mParams.format = PixelFormat.TRANSLUCENT;
//        mParams.type = WindowManager.LayoutParams.TYPE_PRIORITY_PHONE;
//        mParams.setTitle("Toast");
//        mParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
//                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
////                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
////        TextView mView = new TextView(this);
////        mView.setText("摇一摇");
////        mView.setTextColor(Color.RED);
////        mView.setTextSize(20);
//        mView.setOnTouchListener(this);
//        mWM.addView(mView, mParams);
        Intent intent = new Intent(MainActivity.this, FloatWindowService.class);
        startService(intent);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
            if (!Settings.canDrawOverlays(this)) {
                // SYSTEM_ALERT_WINDOW permission not granted...
                hasOverlayPermission = false;
            } else {
                // Already hold the SYSTEM_ALERT_WINDOW permission, do addview or something.
                hasOverlayPermission = true;
                showShakeByShake();
            }
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 监控返回键
            new AlertDialog.Builder(this).setTitle("提示")
                    .setIconAttribute(android.R.attr.alertDialogIcon)
                    .setMessage("确定要退出吗?")
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCollector.finishAll();
                        }
                    })
                    .setNegativeButton("取消", null)
                    .create().show();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public void crash(View view) {
//        int i = 1 / 0;
    }


}
