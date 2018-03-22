// (c)2016 Flipboard Inc, All Rights Reserved.

package com.goldencarp.lingqianbao.model.net;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ZhuangBiApi {
    @GET("search")
    Observable<List<ZhuangbiImage>> search(@Query("q") String query);
}
