package com.goldencarp.lingqianbao.view.fragment;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

import com.goldencarp.lingqianbao.R;
import com.goldencarp.lingqianbao.presenter.DailyResommendFragmentPresenter;
import com.goldencarp.lingqianbao.presenter.IDailyResommendFragmentPresenter;
import com.goldencarp.lingqianbao.view.LQBApp;
import com.goldencarp.lingqianbao.view.activity.WebviewActivity;
import com.goldencarp.lingqianbao.view.util.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomePageFragment extends BaseFragment implements OnBannerListener {

    @BindView(R.id.banner)
    Banner banner;
    Unbinder unbinder;
    private IDailyResommendFragmentPresenter mPresenter;
    private List<Integer> images;

    public HomePageFragment() {
        // Required empty public constructor
    }

    @Override
    protected Object getContentView() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getActivity().getWindow();
//            window.setFlags(
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
        View view = LayoutInflater.from(LQBApp.getApp()).inflate(R.layout.fragment_home_page, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_daily_recommend, container, false);
//        unbinder = ButterKnife.bind(this, view);
//        super.onCreateView(inflater, container, savedInstanceState);
//        return view;
//    }

    protected void initData() {
        mPresenter = new DailyResommendFragmentPresenter();
        images = mPresenter.getBannerImages();
        stateLayout.showContentView();
    }

    protected void initView() {
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        //给banner设置点击事件
        banner.setOnBannerListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }

    @Override
    public void OnBannerClick(int position) {
        Intent intent = new Intent(getActivity(), WebviewActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null)
            unbinder.unbind();
    }
}
