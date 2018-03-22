package com.goldencarp.lingqianbao.view.activity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.goldencarp.lingqianbao.R;
import com.goldencarp.lingqianbao.view.LQBApp;
import com.goldencarp.lingqianbao.view.adapter.ListsAdapter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListsActivity extends BaseActivity {

    @BindView(R.id.xrecyclerview)
    XRecyclerView xrecyclerview;
    private ArrayList<String> datas;
    private ListsAdapter adapter;
    private int mDividerHeight = 5; //分割线高度
    private Paint mDividerPaint = new Paint();//分割线画笔

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mDividerPaint.setColor(Color.parseColor("#3CBAFF"));
        xrecyclerview.setLayoutManager(new LinearLayoutManager(LQBApp.getApp()));
        xrecyclerview.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDraw(c, parent, state);
                //获取分割线的左上右下
                int left = parent.getLeft();
                int right = parent.getRight();
                int childCount = parent.getChildCount();
                for (int i = 2; i < childCount-1; i++) {
                    View child = parent.getChildAt(i);
                    int bottom = child.getTop();
                    int top = bottom - mDividerHeight;
                    c.drawRect(left, top, right, bottom, mDividerPaint);
                }
            }

            @Override
            public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDrawOver(c, parent, state);
            }

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
//                int position = parent.getChildAdapterPosition(view);
//                if (position != 0) {
//                    //不是第一个条目则向下偏移一个分割线的高度
//                    outRect.top = mDividerHeight;
//                }
            }
        });
        datas = new ArrayList<>();
        datas.add("联动布局列表");
        datas.add("复杂列表");
        datas.add("浮动列表");
        adapter = new ListsAdapter(datas);
        adapter.setClickCallBack(new ListsAdapter.ItemClickCallBack() {
            @Override
            public void onItemClick(int pos) {
                switch (pos) {
                    case 0:
                        //联动布局
//                        Intent intent = new Intent(LQBApp.getApp(), CollapseListActivity.class);
//                        startActivity(intent);
                        goTo(CollapseListActivity.class);
                        break;
                    case 1:
                        goTo(ComolexListActivity.class);
                        break;
                    case 2:
                        goTo(StickyListActivity.class);
                        break;
                }
            }
        });
        xrecyclerview.setAdapter(adapter);
    }
}
