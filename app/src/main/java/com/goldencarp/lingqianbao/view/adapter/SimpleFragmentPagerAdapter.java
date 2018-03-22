package com.goldencarp.lingqianbao.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.goldencarp.lingqianbao.R;
import com.goldencarp.lingqianbao.view.fragment.DiscoverFragment;
import com.goldencarp.lingqianbao.view.fragment.HomePageFragment;
import com.goldencarp.lingqianbao.view.fragment.MyAssetFragment;
import com.goldencarp.lingqianbao.view.fragment.PersonalCenterFragment;

/**
 * Created by sks on 2017/12/1.
 *
 */

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private String tabTitles[] = new String[]{"零钱包", "发现", "我的", "demo"};
    private Fragment fragments[] = new Fragment[]{new HomePageFragment(), new DiscoverFragment(), new PersonalCenterFragment(), new MyAssetFragment()};

    private int[] imageResId = {
            R.drawable.tab_selecter,
            R.drawable.tab_selecter,
            R.drawable.tab_selecter,
            R.drawable.tab_selecter
    };

    public SimpleFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
//        Drawable image = context.getResources().getDrawable(imageResId[position]);
//        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
//        // Replace blank spaces with image icon
//        SpannableString sb = new SpannableString(tabTitles[position]);
//        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
//        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return null;
    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_item, null);
        TextView tv = view.findViewById(R.id.textView);
        tv.setText(tabTitles[position]);
        ImageView img = view.findViewById(R.id.imageView);
        img.setImageResource(imageResId[position]);
        return view;
    }

    public Fragment[] getFragmentLists() {
        return fragments;
    }
}
