package com.goldencarp.lingqianbao.view.custom.stickydecoration;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.goldencarp.lingqianbao.view.custom.stickydecoration.cache.CacheUtil;
import com.goldencarp.lingqianbao.view.custom.stickydecoration.listener.OnGroupClickListener;
import com.goldencarp.lingqianbao.view.custom.stickydecoration.listener.PowerGroupListener;


/**
 * Created by gavin
 * Created date 17/5/24
 * View悬浮
 * 利用分割线实现悬浮
 */

public class PowerfulStickyDecoration extends BaseDecoration {

    private Paint mGroutPaint;

    /**
     * 缓存图片
     * 使用软引用，防止内存泄露
     */
    private CacheUtil<Bitmap> mBitmapCache = new CacheUtil<>();

    /**
     * 缓存View
     * 若不使用软引用，内存会明显增多
     * (注意：在异步显示图片时，建议使用强引用)
     */
    private CacheUtil<View> mHeadViewCache = new CacheUtil<>();

    private PowerGroupListener mGroupListener;

    private PowerfulStickyDecoration(PowerGroupListener groupListener) {
        super();
        this.mGroupListener = groupListener;
        //设置悬浮栏的画笔---mGroutPaint
        mGroutPaint = new Paint();
        mGroutPaint.setColor(mGroupBackground);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        //绘制
        int itemCount = state.getItemCount();
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(childView);
            if (isFirstInGroup(position) || i == 0) {
                int viewBottom = childView.getBottom();
                //top 决定当前顶部第一个悬浮Group的位置
                int bottom = Math.max(mGroupHeight, childView.getTop() + parent.getPaddingTop());
                if (position + 1 < itemCount) {
                    //下一组的第一个View接近头部
                    if (isLastLineInGroup(parent, position) && viewBottom < bottom) {
                        bottom = viewBottom;
                    }
                }
                drawDecoration(c, position, left, right, bottom);
                //将头部信息放进array，用于处理点击事件
                stickyHeaderPosArray.put(position, bottom);
            } else {
                //绘制分割线
                drawDivide(c, parent, childView, position, left, right);
            }
        }
    }

    /**
     * 绘制悬浮框
     *
     * @param c        Canvas
     * @param position position
     * @param left     left
     * @param right    right
     * @param bottom   bottom
     */
    private void drawDecoration(Canvas c, int position, int left, int right, int bottom) {
        c.drawRect(left, bottom - mGroupHeight, right, bottom, mGroutPaint);
        //根据position获取View
        View groupView;
        int firstPositionInGroup = getFirstInGroupWithCash(position);
        if (mHeadViewCache.get(firstPositionInGroup) == null) {
            groupView = getGroupView(firstPositionInGroup);
            if (groupView == null) {
                return;
            }
            measureAndLayoutView(groupView, left, right);
            mHeadViewCache.put(firstPositionInGroup, groupView);
        } else {
            groupView = mHeadViewCache.get(firstPositionInGroup);
        }
        Bitmap bitmap;
        if (mBitmapCache.get(firstPositionInGroup) != null && mBitmapCache.get(firstPositionInGroup) != null) {
            bitmap = mBitmapCache.get(firstPositionInGroup);
        } else {
            bitmap = Bitmap.createBitmap(groupView.getDrawingCache());
            mBitmapCache.put(firstPositionInGroup, bitmap);
        }
        c.drawBitmap(bitmap, left, bottom - mGroupHeight, null);
    }

    /**
     * 对view进行测量和布局
     *
     * @param groupView groupView
     * @param left      left
     * @param right     right
     */
    private void measureAndLayoutView(View groupView, int left, int right) {
        groupView.setDrawingCacheEnabled(true);
        //手动对view进行测量，指定groupView的高度、宽度
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(right, mGroupHeight);
        groupView.setLayoutParams(layoutParams);
        groupView.measure(
                View.MeasureSpec.makeMeasureSpec(right, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(mGroupHeight, View.MeasureSpec.EXACTLY));
        groupView.layout(left, 0 - mGroupHeight, right, 0);
    }

    /**
     * 获取组名
     *
     * @param position position
     * @return 组名
     */
    @Override
    String getGroupName(int position) {
        if (mGroupListener != null) {
            return mGroupListener.getGroupName(position);
        } else {
            return null;
        }
    }

    /**
     * 获取组View
     *
     * @param position position
     * @return 组名
     */
    private View getGroupView(int position) {
        if (mGroupListener != null) {
            return mGroupListener.getGroupView(position);
        } else {
            return null;
        }
    }

    /**
     * 是否使用强引用
     * @param b b
     */
    public void setStrongReference(boolean b) {
        mHeadViewCache.isStrongReference(b);
    }

    /**
     * 通知重新绘制
     * 使用场景：网络图片加载后调用
     * 建议：配合{@link #setStrongReference(boolean)}方法使用，体验更佳
     * @param recyclerView recyclerView
     * @param position position
     */
    public void notifyRedraw(RecyclerView recyclerView, View viewGroup, int position) {
        viewGroup.setDrawingCacheEnabled(false);
        mBitmapCache.remove(position);
        mHeadViewCache.remove(position);
        int left = recyclerView.getPaddingLeft();
        int right = recyclerView.getWidth() - recyclerView.getPaddingRight();
        measureAndLayoutView(viewGroup, left, right);
        mHeadViewCache.put(position, viewGroup);
        recyclerView.invalidate();
    }

    public static class Builder {
        PowerfulStickyDecoration mDecoration;

        private Builder(PowerGroupListener listener) {
            mDecoration = new PowerfulStickyDecoration(listener);
        }

        public static Builder init(PowerGroupListener listener) {
            return new Builder(listener);
        }

        /**
         * 设置Group高度
         *
         * @param groutHeight 高度
         * @return this
         */
        public Builder setGroupHeight(int groutHeight) {
            mDecoration.mGroupHeight = groutHeight;
            return this;
        }


        /**
         * 设置Group背景
         *
         * @param background 背景色
         */
        public Builder setGroupBackground(@ColorInt int background) {
            mDecoration.mGroupBackground = background;
            mDecoration.mGroutPaint.setColor(mDecoration.mGroupBackground);
            return this;
        }

        /**
         * 设置分割线高度
         *
         * @param height 高度
         * @return this
         */
        public Builder setDivideHeight(int height) {
            mDecoration.mDivideHeight = height;
            return this;
        }

        /**
         * 设置分割线颜色
         *
         * @param color color
         * @return this
         */
        public Builder setDivideColor(@ColorInt int color) {
            mDecoration.mDivideColor = color;
            return this;
        }

        /**
         * 设置点击事件
         *
         * @param listener 点击事件
         * @return this
         */
        public Builder setOnClickListener(OnGroupClickListener listener) {
            mDecoration.setOnGroupClickListener(listener);
            return this;
        }

        /**
         * 重置span
         *
         * @param recyclerView      recyclerView
         * @param gridLayoutManager gridLayoutManager
         * @return this
         */
        public Builder resetSpan(RecyclerView recyclerView, GridLayoutManager gridLayoutManager) {
            mDecoration.resetSpan(recyclerView, gridLayoutManager);
            return this;
        }

        /**
         * 是否使用强引用
         * @param b b
         */
        public Builder setStrongReference(boolean b){
            mDecoration.setStrongReference(b);
            return this;
        }

        public PowerfulStickyDecoration build() {
            return mDecoration;
        }
    }

}