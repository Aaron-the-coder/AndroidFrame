package com.goldencarp.lingqianbao.view.activity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goldencarp.lingqianbao.R;
import com.goldencarp.lingqianbao.view.custom.PasswordInputEdt;
import com.goldencarp.lingqianbao.view.custom.visualkeyboard.AliPayWindow;
import com.goldencarp.lingqianbao.view.custom.dealpassword.GridPasswordView;
import com.goldencarp.lingqianbao.view.custom.visualkeyboard.OnPasswordFinishedListener;
import com.goldencarp.lingqianbao.view.util.Logger;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DealCodeActivity extends BaseActivity implements OnPasswordFinishedListener {

    private static final String LOG_TAG = "DealCodeActivity";
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.password_view)
    PasswordInputEdt passwordView;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;

    public String password;//交易密码
    @BindView(R.id.btn_show)
    Button btnShow;
    @BindView(R.id.rl_parent)
    RelativeLayout rlParent;

    private InputMethodManager imm;
    private AliPayWindow aliPayWindow;
    private static Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_code);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        passwordView.setOnInputOverListener(new PasswordInputEdt.onInputOverListener() {


            @Override
            public void onInputOver(String text) {
                password = text;
            }
        });
    }

    @OnClick({R.id.btn_confirm, R.id.btn_show})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                Logger.e(LOG_TAG, "password==" + password);
                break;
            case R.id.btn_show:
//                showDialogPlus();
                aliPayWindow = new AliPayWindow(this);
                aliPayWindow.setOnPasswordFinishedListener(this);
                aliPayWindow.show(rlParent);

                break;
        }

    }

    private void showDialogPlus() {
        DialogPlus dialog = DialogPlus.newDialog(this)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setContentWidth(ViewGroup.LayoutParams.MATCH_PARENT)
                .setContentHolder(new ViewHolder(R.layout.deal_pass_dialog))
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {
                        switch (view.getId()) {
                            case R.id.btn_show:
                                Toast.makeText(DealCodeActivity.this, "hello", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                break;
                        }
                    }
                })

                .setExpanded(true)  // This will enable the expand feature, (similar to android L share dialog)
                .create();
        View holderView = dialog.getHolderView();
        final GridPasswordView gridPasswordView = holderView.findViewById(R.id.gridpasswordview);

        gridPasswordView.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String psw) {
                Logger.i(LOG_TAG, "------onTextChanged------");
            }

            @Override
            public void onInputFinish(String psw) {
                Logger.e(LOG_TAG, "pwd:" + psw);
                hideSoftInput(gridPasswordView);
            }
        });
        dialog.show();
    }

    /**
     * 显示软键盘
     */
    protected void showSoftInput(View view) {
        //弹出软键盘
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        // imm.showSoftInput(passwordInput, InputMethodManager.SHOW_FORCED);
//		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        if (imm != null) {
            view.requestFocus();
            imm.showSoftInput(view, 0);
        }
    }

    /**
     * 隐藏软键盘
     */
    protected void hideSoftInput(View view) {
        if (imm == null) {
            imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onFinish(String password) {
        if (TextUtils.equals("123456", password)) {
            Toast.makeText(this, "密码正确：" + password, Toast.LENGTH_SHORT).show();
            aliPayWindow.dismiss();
        } else {
            Toast.makeText(this, "密码错误：" + password, Toast.LENGTH_SHORT).show();
            final View tvTip = aliPayWindow.getTvTip();
            tvTip.setVisibility(View.VISIBLE);
            View passwordView = aliPayWindow.getPasswordView();
            ObjectAnimator animator = ObjectAnimator.ofFloat(passwordView, "translationX", 30, -30, 30, -30, 30, -30, 0);
            animator.setDuration(500);
            animator.start();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tvTip.setVisibility(View.INVISIBLE);
                }
            }, 2000);
        }

    }

//    private class MyHolder extends ViewHolder {
//
//        public MyHolder(int viewResourceId) {
//            super(viewResourceId);
//            View view = LayoutInflater.from(LQBApp.getApp()).inflate(viewResourceId, null);
//            GridPasswordView gridPasswordView = view.findViewById(R.id.gridpasswordview);
//            gridPasswordView.requestFocus();
//            gridPasswordView.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
//                @Override
//                public void onTextChanged(String psw) {
//
//                }
//
//                @Override
//                public void onInputFinish(String psw) {
//                    // TODO: 2018/2/27 关闭软键盘
//                    Toast.makeText(DealCodeActivity.this, "输入的密码是:" + psw, Toast.LENGTH_SHORT).show();
//                    Logger.e(LOG_TAG, "输入的密码是:" + psw);
//                }
//            });
//        }
//    }

}
