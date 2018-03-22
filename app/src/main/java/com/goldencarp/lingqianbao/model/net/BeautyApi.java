// (c)2016 Flipboard Inc, All Rights Reserved.

package com.goldencarp.lingqianbao.model.net;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BeautyApi {
    @GET("data/福利/{number}/{page}")
    Observable<GankBeautyResult> getBeauties(@Path("number") int number, @Path("page") int page);
}
