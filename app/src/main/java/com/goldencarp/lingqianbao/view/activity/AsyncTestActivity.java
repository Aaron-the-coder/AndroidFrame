package com.goldencarp.lingqianbao.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.goldencarp.lingqianbao.R;
import com.goldencarp.lingqianbao.view.fragment.CacheFragment;
import com.goldencarp.lingqianbao.view.fragment.ElementaryFragment;
import com.goldencarp.lingqianbao.view.fragment.MapFragment;
import com.goldencarp.lingqianbao.view.fragment.OtherFragment;
import com.goldencarp.lingqianbao.view.fragment.TokenAdvancedFragment;
import com.goldencarp.lingqianbao.view.fragment.TokenFragment;
import com.goldencarp.lingqianbao.view.fragment.ZipFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AsyncTestActivity extends BaseActivity {

    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(android.R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_test);
        ButterKnife.bind(this);
        setSupportActionBar(toolBar);
        initView();
    }

    private void initView() {
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new ElementaryFragment();
                    case 1:
                        return new MapFragment();
                    case 2:
                        return new ZipFragment();
                    case 3:
                        return new TokenFragment();
                    case 4:
                        return new TokenAdvancedFragment();
                    case 5:
                        return new CacheFragment();
                    case 6:
                        return new OtherFragment();
                    default:
                        return new ElementaryFragment();

                }
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "基本";
                    case 1:
                        return "转换(map)";
                    case 2:
                        return "压合(zip)";

                    case 3:
                        return "TOKEN(FLATMAP)";

                    case 4:
                        return "TOKEN(高级retrywhen)";

                    case 5:
                        return "缓存(behaviorsubject)";
                    case 6:
                        return "自测";
                    default:
                        return "基本";
                }
            }

            @Override
            public int getCount() {
                return 7;
            }
        });

        tabs.setupWithViewPager(viewPager);
    }
}
