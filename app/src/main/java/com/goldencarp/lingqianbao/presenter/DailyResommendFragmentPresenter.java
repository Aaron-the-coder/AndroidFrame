package com.goldencarp.lingqianbao.presenter;


import com.goldencarp.lingqianbao.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sks on 2017/12/1.
 */

public class DailyResommendFragmentPresenter implements IDailyResommendFragmentPresenter {

    /**
     * 获取首页bannar的图片
     */

    @Override
    public List<Integer> getBannerImages() {
        List<Integer> images = new ArrayList<>();
        images.add(R.mipmap.chrysanthemum);
        images.add(R.mipmap.desert);
        images.add(R.mipmap.hydrangeas);
        images.add(R.mipmap.jellyfish);
        images.add(R.mipmap.koala);
        return images;
    }

}
