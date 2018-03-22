package com.goldencarp.lingqianbao.model.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.goldencarp.lingqianbao.model.Constants;
import com.goldencarp.lingqianbao.model.dao.SpUtil;
import com.goldencarp.lingqianbao.view.util.Logger;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sks on 2017/12/1.
 * 网络请求工具类
 */

public class HttpUtil {

    private static Retrofit retrofit;
    private static BeautyApi beautyApi;
    private static OkHttpClient.Builder okHttpClient = new OkHttpClient().newBuilder();
    private static final java.lang.String LOG = "HttpUtil";
    //声明日志类
    private static HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            Logger.d(LOG, "http_message:" + message);
        }
    });

    private static Interceptor mAuthenticationIntercepter = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originRequest = chain.request();
            String token = SpUtil.getString(Constants.KEY_SP_TOKEN);
            if (TextUtils.isEmpty(token) && alreadyHasAuthorizationHeader(originRequest))
                //如果本地没有token或者请求头已经有了token,则不对请求做任何处理
                return chain.proceed(originRequest);
            //添加请求头token
            Request newRequest = originRequest.newBuilder()
                    .header("Authorization", token)
                    .build();
            return chain.proceed(newRequest);
        }
    };

    private static boolean alreadyHasAuthorizationHeader(Request originRequest) {
        return !TextUtils.isEmpty(originRequest.header("Authorization"));
    }

    private static CallAdapter.Factory callAdapterFactory = RxJava2CallAdapterFactory.create();
    private static ZhuangBiApi zhuangBiApi;
    private static FakeApi fakeApi;


    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            //设定日志级别
            if (Constants.isDebug()) {
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            } else {
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
            }
            okHttpClient.addInterceptor(httpLoggingInterceptor)
                    .addInterceptor(mAuthenticationIntercepter);
            retrofit = new Retrofit.Builder()
                    .client(okHttpClient.build())
                    .baseUrl(Constants.getHomeUrl())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(callAdapterFactory)
                    .build();
        }
        return retrofit;
    }

    public static ZhuangBiApi getZhuangBiApi() {
        if (zhuangBiApi == null) {
            //设定日志级别
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClient.addInterceptor(httpLoggingInterceptor);
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient.build())
                    .baseUrl(Constants.getZhuangBiUrl())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(callAdapterFactory)
                    .build();
            zhuangBiApi = retrofit.create(ZhuangBiApi.class);
        }
        return zhuangBiApi;
    }

    public static BeautyApi getBeautyApi() {
        if (beautyApi == null) {
            //设定日志级别
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClient.addInterceptor(httpLoggingInterceptor);
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient.build())
                    .baseUrl(Constants.getGankApi())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(callAdapterFactory)
                    .build();
            beautyApi = retrofit.create(BeautyApi.class);
        }
        return beautyApi;
    }

    public static FakeApi getFakeApi() {
        if (fakeApi == null) {
            fakeApi = new FakeApi();
        }
        return fakeApi;
    }

    /**
     * 网络可用
     *
     * @param context 上下文
     * @return true网络可用
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo != null)
                return networkInfo.isAvailable();
        }
        return false;
    }
}
