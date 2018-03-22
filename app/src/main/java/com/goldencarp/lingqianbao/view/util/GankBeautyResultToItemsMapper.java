package com.goldencarp.lingqianbao.view.util;

import com.goldencarp.lingqianbao.model.net.GankBeautyResult;
import com.goldencarp.lingqianbao.model.net.Item;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by dale on 2018/2/19.
 */

public class GankBeautyResultToItemsMapper implements Function<GankBeautyResult, List<Item>> {
    @Override
    public List<Item> apply(@NonNull GankBeautyResult gankBeautyResult) throws Exception {
        List<GankBeautyResult.ResultsBean> results = gankBeautyResult.getResults();
        List<Item> itemList = new ArrayList<>();
        //2018-01-29T07:40:56.269Z
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        for (GankBeautyResult.ResultsBean result : results) {
            Date inDate = sdf1.parse(result.getCreatedAt());
            String outDateStr = sdf2.format(inDate);
            Item item = new Item();
            item.description = outDateStr;
            item.imageUrl = result.getUrl();
            itemList.add(item);
        }
        return itemList;
    }
}
