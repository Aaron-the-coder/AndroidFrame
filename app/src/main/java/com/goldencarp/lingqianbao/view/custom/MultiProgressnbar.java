package com.goldencarp.lingqianbao.view.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.goldencarp.lingqianbao.view.LQBApp;
import com.goldencarp.lingqianbao.view.util.DensityUtil;

/**
 * Created by sks on 2018/3/9.
 */

public class MultiProgressnbar extends View {

    final float radius1 = DensityUtil.dip2px(LQBApp.getApp(), 80);//外环半径
    final float radius2 = DensityUtil.dip2px(LQBApp.getApp(), 60);//内环半径

    public MultiProgressnbar(Context context) {
        this(context, null);
    }

    public MultiProgressnbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private Paint mPaint;

    {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(DensityUtil.dip2px(LQBApp.getApp(), 15));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float centerX = getWidth() / 2;
        float centerY = getHeight() / 2;
        mPaint.setColor(Color.parseColor("#E91E63"));
        RectF outerRectF = new RectF(centerX - radius1, centerY - radius1, centerX + radius1, centerY + radius1);
        RectF innerRectF = new RectF(centerX - radius2, centerY - radius2, centerX + radius2, centerY + radius2);
        canvas.drawArc(outerRectF, 0, 120, false, mPaint);
        mPaint.setColor(Color.parseColor("#0090ED"));
        canvas.drawArc(innerRectF, 60, 270, false, mPaint);
    }
}
