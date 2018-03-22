package com.goldencarp.lingqianbao.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.goldencarp.lingqianbao.R;
import com.goldencarp.lingqianbao.view.util.StringUtil;

/**
 * Created by sks on 2018/3/8.
 *
 */

public class PwdClearableLayout extends LinearLayout implements View.OnClickListener {

    private static final java.lang.String TAG = "PwdClearableLayout";
    private final float textSize;
    private final int hintColor;
    private final int textColor;
    private final String hint;
    private final String text;
    private final Drawable checkMark;
    private final String digits;
    private ImageView ivHead;
    private EditText etPwd;
    private CheckBox cbToggle;
    private EditText et_password;
    private CheckedTextView ctv;
    private ImageView img_clear;

    public PwdClearableLayout(Context context) {
        this(context, null);
    }

    public PwdClearableLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.PwdClearableLayout);
        textSize = a.getDimension(R.styleable.PwdClearableLayout_textSize, -1);
        hintColor = a.getColor(R.styleable.PwdClearableLayout_textColorHint, -1);
        textColor = a.getColor(R.styleable.PwdClearableLayout_textColor, -1);
        hint = a.getString(R.styleable.PwdClearableLayout_hint);
        text = a.getString(R.styleable.PwdClearableLayout_text);
        checkMark = a.getDrawable(R.styleable.PwdClearableLayout_checkMark);
        digits = a.getString(R.styleable.PwdClearableLayout_digits);

        a.recycle();
        initView();
    }

    private void initView() {

        LinearLayout layout = (LinearLayout) LayoutInflater.from(getContext())
                .inflate(R.layout.layout_pwd_visible, this);
        et_password =  layout.findViewById(R.id.et_password);
        ctv =  layout.findViewById(R.id.ctv);
        img_clear =  layout.findViewById(R.id.img_clear);
        //默认密码是密码样式并且由于进入页面未获得焦距，小眼睛应隐藏
        hidePassword();
        hideDrawable();
        if (textSize != -1) {
            et_password.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        }
        if (textColor != -1) {
            et_password.setTextColor(textColor);
        }
        if (!TextUtils.isEmpty(hint)) {
            et_password.setHint(hint);
        }
        if (!TextUtils.isEmpty(text)) {
            et_password.setText(text);
        }
        if (hintColor != -1) {
            et_password.setHintTextColor(hintColor);
        }
        if (checkMark != null) {
            ctv.setCheckMarkDrawable(checkMark);
        }
        if (digits != null) {
            try {
                et_password.setOnKeyListener((OnKeyListener) DigitsKeyListener.getInstance(digits));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ctv.setOnClickListener(this);
        img_clear.setOnClickListener(this);

        et_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (!TextUtils.isEmpty(getPwd())) {
                        //如果获取焦距并且密码不为空，则显示小眼睛和清除图片
                        showDrawable();
                    }
                } else {
                    if (ctv.isChecked()) {
                        ctv.toggle();
                        hidePassword();
                    }
                    hideDrawable();
                }
            }
        });

        et_password.addTextChangedListener(new TextWatcher() {

            private String beforeText;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                String editable = editText.getText().toString();
                // 输入后的字符
                String afterText = s.toString();
                Log.d(TAG, "afterText =>>>" + afterText);

                boolean isValid = true;
                if (!TextUtils.isEmpty(afterText)) {
                    isValid = StringUtil.isValidPassword1(afterText);
                }
                if (!isValid) {
                    // 用户现在输入的字符数减去之前输入的字符数，等于新增的字符数
                    int differ = afterText.length() - beforeText.length();
                    // 如果用户的输入不符合规范，则显示之前输入的文本
                    et_password.setText(beforeText);
                    // 光标移动到文本末尾
                    et_password.setSelection(afterText.length() - differ);
                }
            }


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                beforeText = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!hasFocus()) {
                    return;
                }
                String afterChangeText = s.toString();
                if (!TextUtils.isEmpty(afterChangeText)) {
                    showDrawable();
                } else {
                    hideDrawable();
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    /**
     * 切换密码可见状态
     */
    private void changePwdVisibleState(boolean visible) {
        if (visible) {
            etPwd.setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            etPwd.setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
        }
        etPwd.setSelection(etPwd.getText().length());
    }

    /**
     * 隐藏密码
     */
    private void hidePassword() {
        et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        et_password.postInvalidate();
//		et_password.setInputType(InputType.TYPE_CLASS_TEXT
//				| InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    /**
     * 显示密码
     */
    private void showPassword() {
        et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        et_password.postInvalidate();
//		et_password.setInputType(InputType.TYPE_CLASS_TEXT
//				| InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
    }

    /**
     * 隐藏小眼睛和清除图片
     */
    private void hideDrawable() {
        img_clear.setVisibility(View.GONE);
        ctv.setVisibility(View.GONE);
    }

    /**
     * 显示小眼睛和清除图片
     */
    private void showDrawable() {
        img_clear.setVisibility(View.VISIBLE);
        ctv.setVisibility(View.VISIBLE);
    }

    public String getPwd() {
        return et_password.getText().toString();
    }

    public EditText getEditText() {
        return et_password;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ctv:
                ctv.toggle();
                if (!ctv.isChecked()) {
                    hidePassword();
                } else {
                    showPassword();
                }
                et_password.setSelection(et_password.getText().length());
                break;

            case R.id.img_clear:
                et_password.setText("");
                hidePassword();
                hideDrawable();
                if (ctv.isChecked()) {
                    ctv.toggle();
                }
                break;
        }

        et_password.requestFocus();
    }
}
