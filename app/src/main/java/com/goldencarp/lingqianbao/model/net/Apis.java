package com.goldencarp.lingqianbao.model.net;

import com.goldencarp.lingqianbao.model.bean.Student;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by dale on 2017/11/21.
 */

public interface Apis {
    //    @GET("about/version?currentversion=5.1.1&platform=and")//原始方式
    @Headers("Authorization: authorization")
    @HTTP(method = "GET", path = "about/version/{currentversion, platform}", hasBody = false)
    Call<ResponseBody> checkUpgrade(@Header("Authorization") String authorization, @Path("currentversion") String currentversion, @Path("platform") String platform);

//    Call<ResponseBody> checkUpgrade();

    @POST("add/uploadfiles")
    @FormUrlEncoded
//表示这个接口提交表单
    Call<ResponseBody> uploadCrashFiles(@Field("file") String fileContent, @Field("from") String platform);

    @POST("add/upload-pictures")
    @Multipart
//表示这个接口有文件上传
    Call<ResponseBody> uploadFiles(@Part MultipartBody.Part idcardImg, @Part MultipartBody.Part bankcardImg);

    @GET("test.txt")
    Call<Student> getInfo();
}
