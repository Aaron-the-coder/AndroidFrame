package com.goldencarp.lingqianbao.presenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dale on 2018/1/21.
 */

public class MyAssetFragmentPresenter implements IMyAssetFragmentPresenter {
    public MyAssetFragmentPresenter() {

    }

    /**
     * 获取demo列表数据
     */
    @Override
    public List<String> getDemoData() {
        ArrayList<String> list = new ArrayList<>();
        list.add("混合交互方案");
        list.add("三方分享");
        list.add("消息推送");
        list.add("缓存清理");
        list.add("手势密码");
        list.add("交易密码");
        list.add("各种复杂列表");
        list.add("网络请求框架");
        list.add("异步处理");
        list.add("崩溃日志");
        list.add("android6.0动态权限");
        list.add("图片压缩");
        return list;
    }
}
