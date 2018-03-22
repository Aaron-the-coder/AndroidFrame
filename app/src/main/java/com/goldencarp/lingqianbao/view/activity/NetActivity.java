package com.goldencarp.lingqianbao.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.goldencarp.lingqianbao.R;
import com.goldencarp.lingqianbao.model.bean.Student;
import com.goldencarp.lingqianbao.model.net.Apis;
import com.goldencarp.lingqianbao.model.net.HttpUtil;
import com.goldencarp.lingqianbao.view.LQBApp;
import com.goldencarp.lingqianbao.view.util.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NetActivity extends BaseActivity {

    private static final String LOG_TAG = "NetActivity";
    @BindView(R.id.btn_upload_files)
    Button btnUploadFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_upload_files)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_upload_files:
                if (HttpUtil.isNetworkConnected(LQBApp.getApp())) {
                    Retrofit retrofit = HttpUtil.getRetrofit();
                    Apis apis = retrofit.create(Apis.class);
                    Call<Student> info = apis.getInfo();
                    info.enqueue(new Callback<Student>() {
                        @Override
                        public void onResponse(Call<Student> call, Response<Student> response) {
                            if (response.code() == 200) {
                                Student student = response.body();
                                Logger.e(LOG_TAG, student.toString());
                            } else {
                                Logger.e(LOG_TAG, "网络异常");
                            }
                        }

                        @Override
                        public void onFailure(Call<Student> call, Throwable t) {
                            Logger.e(LOG_TAG, "网络异常");
                        }
                    });
                }
                break;
        }
    }
}
