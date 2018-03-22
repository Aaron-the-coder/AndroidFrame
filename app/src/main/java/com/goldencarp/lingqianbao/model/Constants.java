package com.goldencarp.lingqianbao.model;

/**
 * Created by sks on 2017/12/1.
 *
 */

public class Constants {
    public static final int TIME_OUT = 30 * 1000;
    public static final String EMPTY_STRING = "";
    public static final String SP_FILE_NAME = "lqb_sp_file_name";
    public static final String KEY_SP_TOKEN = "key_sp_token";
    private static String homeUrl = "http://10.0.2.2:8080/";//测试环境
//    private static String homeUrl = "https://app.51lqb.com/";//正式环境

    /**判断环境*/
    public static boolean isDebug(){
        return !getHomeUrl().equals("");
    }

    public static String getHomeUrl() {
        return homeUrl;
    }

    public static String getZhuangBiUrl() {
        return "http://www.zhuangbi.info/";
    }

    public static String getGankApi() {
        return "http://gank.io/api/";
    }
}
