package com.goldencarp.lingqianbao.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.goldencarp.lingqianbao.R;
import com.goldencarp.lingqianbao.model.net.HttpUtil;
import com.goldencarp.lingqianbao.model.net.Item;
import com.goldencarp.lingqianbao.model.net.ZhuangbiImage;
import com.goldencarp.lingqianbao.view.LQBApp;
import com.goldencarp.lingqianbao.view.adapter.ItemListAdapter;
import com.goldencarp.lingqianbao.view.util.GankBeautyResultToItemsMapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ZipFragment extends RxBaseFragment {

    @BindView(R.id.gridRv)
    RecyclerView gridRv;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    Unbinder unbinder;
    private ItemListAdapter mAdapter = new ItemListAdapter(null);

    public ZipFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zip, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    private void initView() {
        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN);
        swipeRefreshLayout.setEnabled(false);
        gridRv.setLayoutManager(new GridLayoutManager(LQBApp.getApp(), 2));
        gridRv.setAdapter(mAdapter);
    }

    private void initData() {
        unsuscribe();
        swipeRefreshLayout.setRefreshing(true);
        disposable = Observable.zip(HttpUtil.getBeautyApi().getBeauties(200, 1).map(new GankBeautyResultToItemsMapper()), HttpUtil.getZhuangBiApi().search("装逼"),
                new BiFunction<List<Item>, List<ZhuangbiImage>, List<Item>>() {
                    @Override
                    public List<Item> apply(@NonNull List<Item> gankItems, @NonNull List<ZhuangbiImage> zhuangbiImages) throws Exception {
                        List<Item> items = new ArrayList<>();
                        for (int i = 0; i < gankItems.size() / 2 && i < zhuangbiImages.size(); i++) {
                            items.add(gankItems.get(i * 2));
                            items.add(gankItems.get(i * 2 + 1));
                            Item zhuangbiItem = new Item();
                            ZhuangbiImage zhuangbiImage = zhuangbiImages.get(i);
                            zhuangbiItem.description = zhuangbiImage.description;
                            zhuangbiItem.imageUrl = zhuangbiImage.image_url;
                            items.add(zhuangbiItem);
                        }
                        return items;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Item>>() {
                    @Override
                    public void accept(@NonNull List<Item> items) throws Exception {
                        swipeRefreshLayout.setRefreshing(false);
                        mAdapter.setImages(items);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(LQBApp.getApp(), "数据异常", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
