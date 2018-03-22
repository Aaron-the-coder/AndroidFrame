package com.goldencarp.lingqianbao.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;

import com.goldencarp.lingqianbao.R;
import com.goldencarp.lingqianbao.view.custom.ClearableEditText;
import com.goldencarp.lingqianbao.view.util.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    private static final java.lang.String LOG_TAG = "LoginActivity";
    @BindView(R.id.et_al_phone)
    ClearableEditText etAlPhone;
    @BindView(R.id.et_al_pwd)
    ClearableEditText etAlPwd;
    @BindView(R.id.cb_pwd_visible_toggle)
    CheckBox cbPwdVisibleToggle;
    private boolean showPwd;//密码可见

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        changePwdVisibleState(cbPwdVisibleToggle.isChecked());
    }

    @OnClick(R.id.cb_pwd_visible_toggle)
    public void onViewClicked() {
        Logger.e(LOG_TAG, "-------onViewClicked--------");
//        boolean checked = cbPwdVisibleToggle.isChecked();
//        cbPwdVisibleToggle.setChecked(!checked);
        changePwdVisibleState(cbPwdVisibleToggle.isChecked());
    }

    /**
     * 切换密码可见状态
     */
    private void changePwdVisibleState(boolean visible) {
        if (visible) {
            etAlPwd.setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            etAlPwd.setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
        }
        etAlPwd.setSelection(etAlPwd.getText().length());
    }
}
