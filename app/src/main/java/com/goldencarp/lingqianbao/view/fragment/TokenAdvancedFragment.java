package com.goldencarp.lingqianbao.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.goldencarp.lingqianbao.R;
import com.goldencarp.lingqianbao.model.net.FakeApi;
import com.goldencarp.lingqianbao.model.net.FakeThing;
import com.goldencarp.lingqianbao.model.net.FakeToken;
import com.goldencarp.lingqianbao.model.net.HttpUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class TokenAdvancedFragment extends Fragment {


    @BindView(R.id.tokenTv)
    TextView tokenTv;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.requestBt)
    Button requestBt;
    @BindView(R.id.invalidateTokenBt)
    Button invalidateTokenBt;
    Unbinder unbinder;

    final FakeToken cachedFakeToken = new FakeToken(true);
    boolean tokenUpdated;

    public TokenAdvancedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_token_advanced, container, false);
        unbinder = ButterKnife.bind(this, view);
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        swipeRefreshLayout.setEnabled(false);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.requestBt, R.id.invalidateTokenBt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.requestBt:
                //从网络获取token,然后根据token获取user信息
                final FakeApi fakeApi = HttpUtil.getFakeApi();
                swipeRefreshLayout.setRefreshing(true);
                Observable.just(1)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMap(
                                new Function<Object, Observable<FakeThing>>() {
                                    @Override
                                    public Observable<FakeThing> apply(@NonNull Object o) throws Exception {
                                        //根据token获取用户信息
                                        return cachedFakeToken.token == null ? Observable.<FakeThing>error(new NullPointerException("token为空!!")) : fakeApi.getFakeData(cachedFakeToken);
                                    }
                                }
//                                new Function<FakeToken, ObservableSource<?>>() {
//                            @Override
//                            public ObservableSource<?> apply(@NonNull FakeToken fakeToken) throws Exception {
//                                //根据token获取用户信息
//                                return cachedFakeToken.token == null ? Observable.error(new NullPointerException("token为空!!")) : fakeApi.getFakeData(fakeToken);
//                            }
//                        }
                        )
//                        .retryWhen(new Function<Throwable, ObservableSource<?>>() {
//                            @Override
//                            public ObservableSource<?> apply(@NonNull Throwable throwableObservable) {
//                                return null;
//                            }
//                        })
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(@NonNull Object o) throws Exception {

                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {

                            }
                        });
                break;
            case R.id.invalidateTokenBt:
                break;
        }
    }
}
