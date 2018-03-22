package com.goldencarp.lingqianbao.view.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.goldencarp.lingqianbao.view.LQBApp;
import com.goldencarp.lingqianbao.view.util.DensityUtil;
import com.goldencarp.lingqianbao.view.util.Logger;

/**
 * Created by dale on 2018/3/11.
 * 倾斜的印章
 */

public class SlantSeal extends View {

    private int mWidth;
    private int mHeight;

    private Paint mPaint;

    private Paint mTextPaint;
    private int degree;//文字倾斜角度
    private java.lang.String Log_Tag = "SlantSeal";

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String date;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private String text;

    {

    }

    public void setDegree(int degree) {
        this.degree = degree;
        invalidate();
    }

    public int getDegree() {
        return degree;
    }

    public SlantSeal(Context context) {
        super(context);
    }

    public SlantSeal(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        mTextPaint = new Paint();
        mWidth = getWidth();
        mHeight = getHeight();
        //绘制最外层圆
        float radius = Math.min(mWidth / 2, mHeight / 2);
        canvas.drawCircle(mWidth / 2, mHeight / 2, radius, mPaint);
        //绘制最内层圆
        radius = Math.min(mWidth /2- DensityUtil.dip2px(LQBApp.getApp(), 20), mHeight /2-DensityUtil.dip2px(LQBApp.getApp(), 20));
        canvas.drawCircle(mWidth / 2, mHeight / 2, radius, mPaint);
        //绘制中间虚线圆
        PathEffect pathEffect = new DashPathEffect(new float[]{10, 5}, 10);
        mPaint.setPathEffect(pathEffect);
        radius = Math.min(mWidth/2 -DensityUtil.dip2px(LQBApp.getApp(), 10), mHeight/2 -DensityUtil.dip2px(LQBApp.getApp(), 10));
        canvas.drawCircle(mWidth / 2, mHeight / 2, radius, mPaint);

        //绘制第二行文字,y为第一行y坐标加上第二行文字高度,x为中心点x减去第二行文字宽度的一半
        canvas.save();
        canvas.rotate(degree, mWidth/2, mHeight/2);
        text = "已使用";
        date = "2017-12-30";
        mTextPaint.setTextSize(60);
        Rect bounds = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), bounds);
        int width = bounds.right - bounds.left;
        int height = bounds.bottom - bounds.top;
        Logger.e(Log_Tag, "width=" + width + ";height=" + height + ";mWidth=" + mWidth + ";mHeight=" + mHeight);
        mTextPaint.setTextSize(36);
        Rect dateBounds = new Rect();
        mTextPaint.getTextBounds(date, 0, date.length(), dateBounds);
        int dateWidth = dateBounds.right - dateBounds.left;
        int dateHeight = dateBounds.bottom - dateBounds.top;
        Logger.e(Log_Tag, "dateWidth=" + dateWidth + ";dateHeight=" + dateHeight );
        int sumWidth = width+dateWidth;
        int sumHeight = height+dateHeight;
        String sumText = text+"\n"+date;

        //绘制背景
        mPaint.setStyle(Paint.Style.FILL);
        int bgLeft = (mWidth - sumWidth) / 2;
        int bgTop = (mHeight - sumHeight) / 2;
        Rect textRect = new Rect(bgLeft, bgTop, bgLeft + sumWidth, bgTop + sumHeight);
        mPaint.setColor(Color.WHITE);
        canvas.drawRect(textRect, mPaint);

        //绘制文字

        int x = (mWidth - sumWidth) / 2;
        int y = (mHeight + sumHeight) / 2;
        Logger.e(Log_Tag, "x=" + x + ";y=" + y);
        canvas.drawText(sumText, x, y, mTextPaint);
        canvas.restore();
    }
}
