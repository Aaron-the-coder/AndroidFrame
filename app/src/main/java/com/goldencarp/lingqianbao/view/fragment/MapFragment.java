package com.goldencarp.lingqianbao.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goldencarp.lingqianbao.R;
import com.goldencarp.lingqianbao.model.net.HttpUtil;
import com.goldencarp.lingqianbao.model.net.Item;
import com.goldencarp.lingqianbao.view.LQBApp;
import com.goldencarp.lingqianbao.view.adapter.ItemListAdapter;
import com.goldencarp.lingqianbao.view.util.GankBeautyResultToItemsMapper;
import com.goldencarp.lingqianbao.view.util.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MapFragment extends RxBaseFragment {

    private static final java.lang.String LOG_TAG = "MapFragment";
    @BindView(R.id.pageTv)
    TextView pageTv;
    @BindView(R.id.previousPageBt)
    AppCompatButton previousPageBt;
    @BindView(R.id.nextPageBt)
    AppCompatButton nextPageBt;
    @BindView(R.id.gridRv)
    RecyclerView gridRv;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    Unbinder unbinder;
    private ItemListAdapter mAdapter = new ItemListAdapter(null);
    private int pageIndex = 0;//当前页数

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    private void initView() {
        //初始化列表和刷新头
        gridRv.setLayoutManager(new GridLayoutManager(LQBApp.getApp(), 2));
        gridRv.setAdapter(mAdapter);
        swipeRefreshLayout.setColorSchemeColors(Color.GREEN, Color.BLUE, Color.YELLOW, Color.RED);
        swipeRefreshLayout.setEnabled(false);

    }

    private void initData() {
        searchNewPage(++pageIndex);
    }

    private void searchNewPage(int page) {
        unsuscribe();
        disposable = HttpUtil.getBeautyApi()
                .getBeauties(10, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new GankBeautyResultToItemsMapper()
//                        new Function<GankBeautyResult, List<Item>>() {
//                    @Override
//                    public List<Item> apply(@NonNull GankBeautyResult gankBeautyResult) throws Exception {
//                        List<GankBeautyResult.ResultsBean> results = gankBeautyResult.getResults();
//                        List<Item> itemList = new ArrayList<>();
//                        //2018-01-29T07:40:56.269Z
//                        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'");
//                        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//                        for (GankBeautyResult.ResultsBean result : results) {
//                            Date inDate = sdf1.parse(result.getCreatedAt());
//                            String outDateStr = sdf2.format(inDate);
//                            Item item = new Item();
//                            item.description = outDateStr;
//                            item.imageUrl = result.getUrl();
//                            itemList.add(item);
//                        }
//                        return itemList;
//                    }
//                }
                ).subscribe(new Consumer<List<Item>>() {
            @Override
            public void accept(@NonNull List<Item> items) throws Exception {
                mAdapter.setImages(items);
                pageTv.setText(getString(R.string.page_index, pageIndex));
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                Logger.e(LOG_TAG, "请求失败:"+throwable.getMessage());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.previousPageBt, R.id.nextPageBt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.previousPageBt:
                searchNewPage(--pageIndex);
                if (pageIndex <= 1) {
                    previousPageBt.setEnabled(false);
                }
                break;
            case R.id.nextPageBt:
                searchNewPage(++pageIndex);
                if (pageIndex >= 2) {
                    previousPageBt.setEnabled(true);
                }
                break;
        }
    }
}
