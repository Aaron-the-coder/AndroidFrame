package com.goldencarp.lingqianbao.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.goldencarp.lingqianbao.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lsp on 2017/4/27.
 * 有间距的交易密码输入框
 */

public class PasswordInputEdt extends android.support.v7.widget.AppCompatEditText {

    private Paint rectPaint;
    private Paint textPaint;
    private Rect textRect;
    private String text = "";
    private List<Rect> list = new ArrayList<>();
    private boolean isFocus = false;
    /**
     * 是否密文显示
     */
    private boolean isPwd;
    /**
     * 是否只能输入数字
     */
    private boolean isNumber;
    /**
     * 横向间距
     */
    private int widthSpace;
    /**
     * 纵向间距
     */
    private int heightSpace;
    /**
     * 密码框的宽度
     */
    private int rectStroke;
    /**
     * 字体大小
     */
    private int txtSize;
    /**
     * 边框或者实体框
     */
    private boolean isBgFill;
    /**
     * 密码长度
     */
    private int numLength;

    /**
     * 字体颜色
     *
     * @param context
     */
    private int textColor;

    /**
     * 默认框框颜色
     *
     * @param context
     */
    private int rectNormalColor;
    /**
     * 选中框框颜色
     *
     * @param context
     */
    private int rectChooseColor;

    /**
     * 密码显示方式
     */
    private PwdType pwdType;

    /**
     * 是否需要在输入完成后关闭键盘
     */
    private boolean isAutoCloseKeyBoard = true;

    public enum PwdType {
        CIRCLE,
        XINGHAO
    }

    public void setPwdType(PwdType pwdType) {
        this.pwdType = pwdType;
    }

    private int pwdType_CircleRadius;

    private onInputOverListener onInputOverListener;

    public void setOnInputOverListener(PasswordInputEdt.onInputOverListener onInputOverListener) {
        this.onInputOverListener = onInputOverListener;
    }

    public PasswordInputEdt(Context context) {
        super(context);

        setAttr(null, 0);

        init();
    }

    public PasswordInputEdt(Context context, AttributeSet attrs) {
        super(context, attrs);

        setAttr(attrs, 0);

        init();
    }

    public PasswordInputEdt(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setAttr(attrs, defStyleAttr);

        init();

    }

    public boolean isFocus() {
        return isFocus;
    }

    public void setFocus(boolean focus) {
        isFocus = focus;
    }

    public boolean isPwd() {
        return isPwd;
    }

    public void setIsPwd(boolean pwd) {
        isPwd = pwd;
    }


    public void setIsNumber(boolean number) {
        isNumber = number;
    }

    public void setWidthSpace(int widthSpace) {
        this.widthSpace = widthSpace;
    }

    public void setHeightSpace(int heightSpace) {
        this.heightSpace = heightSpace;
    }

    public void setRectStroke(int rectStroke) {
        this.rectStroke = rectStroke;
    }

    public void setTxtSize(int txtSize) {
        this.txtSize = txtSize;
    }

    public boolean isBgFill() {
        return isBgFill;
    }

    public void setIsBgFill(boolean bgFill) {
        this.isBgFill = bgFill;
    }

    public void setNumLength(int numLength) {
        this.numLength = numLength;
    }

    public void setRectChooseColor(int rectChooseColor) {
        this.rectChooseColor = rectChooseColor;
    }

    public void setRectNormalColor(int rectNormalColor) {
        this.rectNormalColor = rectNormalColor;
    }

    @Override
    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        switch (heightMode){
            case MeasureSpec.EXACTLY:
                heightSize = MeasureSpec.getSize(heightMeasureSpec);
                break;
            case MeasureSpec.AT_MOST:
                heightSize = widthSize/numLength;
                break;
        }
        setMeasuredDimension(widthSize,heightSize);

    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        isFocus = focused;
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);

        if (this.text == null) {
            return;
        }

        if (this.text.length() < numLength) {
            this.text = this.text + text.toString();
        } else {
            if (onInputOverListener != null) {
                onInputOverListener.onInputOver(this.text);
                if (isAutoCloseKeyBoard) {
//                    closeKeybord();
                    hideSoftInput(this);
                }
            }

        }

        if (text.toString().length() != 0) {
            setText("");
        }

    }

    private void init() {

        rectPaint = new Paint();
        textPaint = new Paint();
        textRect = new Rect();

        setBackgroundDrawable(null);
        setLongClickable(false);
        setTextIsSelectable(false);
        setCursorVisible(false);

        textPaint.setStyle(Paint.Style.FILL);

    }

    private void setAttr(AttributeSet attrs, int defStyleAttr) {

        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.PasswordInputEdt, defStyleAttr, 0);
        isPwd = a.getBoolean(R.styleable.PasswordInputEdt_isPwd, true);
        isAutoCloseKeyBoard = a.getBoolean(R.styleable.PasswordInputEdt_autoCloseKeyBoard, true);
        isNumber = a.getBoolean(R.styleable.PasswordInputEdt_isNumber, true);
        widthSpace = a.getDimensionPixelSize(R.styleable.PasswordInputEdt_widthSpace, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
        pwdType_CircleRadius = a.getDimensionPixelSize(R.styleable.PasswordInputEdt_circleRadius, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
        heightSpace = a.getDimensionPixelSize(R.styleable.PasswordInputEdt_heightSpace, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
        rectStroke = a.getDimensionPixelSize(R.styleable.PasswordInputEdt_rectStroke, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
        txtSize = a.getDimensionPixelSize(R.styleable.PasswordInputEdt_txtSize, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 18, getResources().getDisplayMetrics()));
        isBgFill = a.getBoolean(R.styleable.PasswordInputEdt_bgFill, false);
        numLength = a.getInt(R.styleable.PasswordInputEdt_numLength, 6);
        textColor = a.getColor(R.styleable.PasswordInputEdt_textColor, 0xff666666);
        rectNormalColor = a.getColor(R.styleable.PasswordInputEdt_rectNormalColor, 0xff808080);
        rectChooseColor = a.getColor(R.styleable.PasswordInputEdt_rectChooseColor, 0xff44ce61);
        pwdType = a.getInt(R.styleable.PasswordInputEdt_pwdType, 0) == 0 ? PwdType.CIRCLE : PwdType.XINGHAO;


        a.recycle();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 67 && text.length() != 0) {
            text = text.substring(0, text.length() - 1);
            invalidate();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!isBgFill) {
            rectPaint.setStyle(Paint.Style.STROKE);
        }

        rectPaint.setStrokeWidth(rectStroke);
        textPaint.setColor(textColor);
        textPaint.setTextSize(txtSize);

        if (isNumber) {
            setInputType(InputType.TYPE_CLASS_NUMBER);
        }

        int width = Math.min(getMeasuredHeight(), getMeasuredWidth() / numLength);


        for (int i = 0; i < numLength; i++) {
            if (i <= text.length() && isFocus) {
                rectPaint.setColor(rectChooseColor);
            } else {
                rectPaint.setColor(rectNormalColor);
            }
            Rect rect = new Rect(i * width + widthSpace, heightSpace, i * width + width - widthSpace, width - heightSpace);
            canvas.drawRect(rect, rectPaint);
            list.add(rect);
        }

        for (int i = 0; i < text.length(); i++) {

            if (isPwd) {

                switch (pwdType) {
                    case CIRCLE:
                        canvas.drawCircle(list.get(i).centerX(), list.get(i).centerY(), pwdType_CircleRadius, textPaint);
                        break;
                    case XINGHAO:
                        textPaint.getTextBounds("*", 0, 1, textRect);
                        canvas.drawText("*", list.get(i).left + (list.get(i).right - list.get(i).left) / 2 - textRect.width() / 2,
                                list.get(i).top + ((list.get(i).bottom - list.get(i).top) / 2) + textRect.height(), textPaint);
                        break;
                }

            } else {
                textPaint.getTextBounds(text.substring(i, i + 1), 0, 1, textRect);
                canvas.drawText(text.substring(i, i + 1), list.get(i).left + (list.get(i).right - list.get(i).left) / 2 - textRect.width() / 2,
                        list.get(i).top + ((list.get(i).bottom - list.get(i).top) / 2) + textRect.height() / 2, textPaint);
            }

        }

    }

    public interface onInputOverListener {
        void onInputOver(String text);
    }

    /**
     * 关闭软键盘
     */
    public void closeKeybord() {
        InputMethodManager imm = (InputMethodManager) getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(getWindowToken(), 0);
    }

    /**
     * 隐藏软键盘
     */
    protected void hideSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
