package com.goldencarp.lingqianbao.presenter;

import com.goldencarp.lingqianbao.model.bean.ProductBean;

import java.util.List;

/**
 * Created by sks on 2018/1/18.
 */

public interface IMainActivityPresenter {
    List<ProductBean> requestRecommendProducts();
}
