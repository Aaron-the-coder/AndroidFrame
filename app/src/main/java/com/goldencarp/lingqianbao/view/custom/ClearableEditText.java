package com.goldencarp.lingqianbao.view.custom;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.goldencarp.lingqianbao.R;


/***
 * 带删除按钮的Edittext
 *
 * @author zhaohaibin
 */
public class ClearableEditText extends android.support.v7.widget.AppCompatEditText implements TextWatcher {

    // 增加点击范围
    private static final int X_REVISE_PIXS = 5;
    /**
     * 右侧位图
     **/
    private Drawable rightDrawable;
    private int dRightSideLength;

    /**
     * 该EditText的验证器
     */
    private EditTextContentValidator contentValidator;

    /**
     * 布局构造函数
     *
     * @param context
     * @param attrs
     */
    public ClearableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.addTextChangedListener(this);

        rightDrawable = getResources().getDrawable(R.drawable.selector_edit_clear);
        dRightSideLength = rightDrawable.getIntrinsicWidth();

        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (!TextUtils.isEmpty(getText().toString())) {
                        showDrawable();
                    }
                } else {
                    disDrawable();
                    if (contentValidator != null) {
                        if (contentValidator.validate(ClearableEditText.this,
                                getText().toString())) {
                            contentValidator
                                    .onValidateRight(ClearableEditText.this);
                        } else {
                            contentValidator
                                    .onValidateError(ClearableEditText.this);
                        }
                    }
                }
            }
        });
    }

    public void setContentValidator(EditTextContentValidator contentValidator) {
        this.contentValidator = contentValidator;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP && hasFocus()
                && rightDrawable != null) {
            final int x = (int) event.getX();
            final int y = (int) event.getY();

            if (x >= (this.getWidth() - (dRightSideLength
                    + this.getPaddingRight() + X_REVISE_PIXS))
                    && x <= (this.getWidth() - (this.getPaddingRight() - X_REVISE_PIXS))) {
                // disDrawable();
                this.setText("");
                event.setAction(MotionEvent.ACTION_CANCEL);// use this to
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!hasFocus()) {
            return;
        }

        String afterChangeText = s.toString();
        if (!TextUtils.isEmpty(afterChangeText)) {

            if (!TextUtils.isEmpty(strNumber)) {

                char lastContent = afterChangeText.charAt(afterChangeText.length() - 1);
                String strLastContent = String.valueOf(lastContent);
                if (!TextUtils.isEmpty(strLastContent) && !strLastContent.equals(strNumber)) {
                    String content = afterChangeText + strNumber;
                    if (content.contains(strNumber)) {
                        content = content.replace(strNumber, "");
                        content = content + strNumber;
                        setText(content);
                        setSelection(content.length());
                    }


                }
            }
        }
        if (!TextUtils.isEmpty(afterChangeText)) {
            showDrawable();
        } else {
            disDrawable();
        }
    }

    private String strNumber;

    /**
     * 设置单位 ¥，米，等
     *
     * @param strNumber
     */
    public void setOnCaseNumber(String strNumber) {
        this.strNumber = strNumber;

    }


    @Override
    public void afterTextChanged(Editable s) {


    }


    /***
     * 隐藏删除图片，防止重写onfocus引起的图片不隐藏问题
     */
    public void disDrawable() {
        this.setCompoundDrawables(null, null, null, null);
        // rightDrawable = null;
    }

    /***
     * 隐藏删除图片，防止重写onfocus引起的图片不隐藏问题
     */
    public void showDrawable() {
        this.setCompoundDrawablesWithIntrinsicBounds(null, null, rightDrawable,
                null);
    }

    /**
     * 该EditText的验证器
     *
     * @author zhaohaibin
     */
    public interface EditTextContentValidator {
        boolean validate(ClearableEditText targetView, String str);

        void onValidateRight(ClearableEditText targetView);

        void onValidateError(ClearableEditText targetView);
    }
}
