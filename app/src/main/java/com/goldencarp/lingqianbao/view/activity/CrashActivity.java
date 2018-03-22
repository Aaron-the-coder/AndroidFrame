package com.goldencarp.lingqianbao.view.activity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.goldencarp.lingqianbao.R;
import com.goldencarp.lingqianbao.view.custom.MultiProgressnbar;
import com.goldencarp.lingqianbao.view.custom.SlantSeal;
import com.goldencarp.lingqianbao.view.custom.wheelpicker.TimePickerView;
import com.goldencarp.lingqianbao.view.util.ToastUtil;
import com.netease.nis.captcha.Captcha;
import com.netease.nis.captcha.CaptchaListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CrashActivity extends BaseActivity {

    @BindView(R.id.btn_validate)
    Button btnValidate;
    @BindView(R.id.iv_head_image)
    ImageView ivHeadImage;
    @BindView(R.id.spinner_languages)
    Spinner spinnerLanguages;
    @BindView(R.id.btn_send_sms)
    Button btnSendSms;
    @BindView(R.id.chart1)
    BarChart chart1;
    @BindView(R.id.seekBar1)
    SeekBar seekBar1;
    @BindView(R.id.tvYMax)
    TextView tvYMax;
    @BindView(R.id.seekBar2)
    SeekBar seekBar2;
    @BindView(R.id.tvXMax)
    TextView tvXMax;
    @BindView(R.id.btn_show_date_wheel)
    Button btnShowDateWheel;
    @BindView(R.id.mpb_ac_progress)
    MultiProgressnbar mpbAcProgress;
    @BindView(R.id.ss_ac_seal)
    SlantSeal ssAcSeal;
    private TimePickerView pvTime;
    private Captcha mCaptcha;

    /*验证码SDK,该Demo采用异步获取方式*/
    private UserLoginTask mLoginTask = null;

    private static Handler mHandler = new Handler();

    public static final String LOG_TAG = "CrashActivity";

    //自定义Listener格式如下
    CaptchaListener myCaptchaListener = new CaptchaListener() {

        @Override
        public void onValidate(String result, String validate, String message) {
            //验证结果，valiadte，可以根据返回的三个值进行用户自定义二次验证
            if (validate.length() > 0) {
                toastMsg("验证成功，validate = " + validate);
            } else {
                toastMsg("验证失败：result = " + result + ", validate = " + validate + ", message = " + message);

            }
        }

        @Override
        public void closeWindow() {
            //请求关闭页面
            toastMsg("关闭页面");
        }

        @Override
        public void onError(String errormsg) {
            //出错
            toastMsg("错误信息：" + errormsg);
        }

        @Override
        public void onCancel() {
            toastMsg("取消线程");
            //用户取消加载或者用户取消验证，关闭异步任务，也可根据情况在其他地方添加关闭异步任务接口
            if (mLoginTask != null) {
                if (mLoginTask.getStatus() == AsyncTask.Status.RUNNING) {
                    Log.i(LOG_TAG, "stop mLoginTask");
                    mLoginTask.cancel(true);
                }
            }
        }

        @Override
        public void onReady(boolean ret) {
            //该为调试接口，ret为true表示加载Sdk完成
            if (ret) {
                toastMsg("验证码sdk加载成功");
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash);
        ButterKnife.bind(this);
//        bubbleSeekbar.setProgress(bubbleSeekbar.getMax());
        initView();

    }

    private void initView() {
        initTimePicker();
        initPintuValidate();
        initSpinner();
        initBarchart();
        initProgressBar();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initSeal();
    }

    /**
     * 初始化印章
     */
    private void initSeal() {
        ssAcSeal.setDegree(30);

    }

    private void initProgressBar() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mpbAcProgress.animate().rotation(360).setDuration(3000);
            }
        }, 500);

    }

    /**
     * 初始化柱状图
     */
    private void initBarchart() {

    }

    private void initSpinner() {
        spinnerLanguages.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                String[] languages = getResources().getStringArray(R.array.languages);
//                Toast.makeText(CrashActivity.this, "你点击的是:"+languages[pos], Toast.LENGTH_LONG).show();
                ToastUtil.showToast("你点击的是:" + languages[pos]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
    }

    /**
     * 初始化拼图验证
     */
    private void initPintuValidate() {
        String testCaptchaId = "d4f32e92c74e4363928fb791ecaa3a44";
        //初始化验证码SDK相关参数，设置CaptchaId、Listener最后调用start初始化。
        if (mCaptcha == null) {
            mCaptcha = new Captcha(this);
        }
        mCaptcha.setCaptchaId(testCaptchaId);
        mCaptcha.setCaListener(myCaptchaListener);
        //可选：开启debug
        mCaptcha.setDebug(false);
        //可选：设置超时时间
        mCaptcha.setTimeout(10000);
    }

    @OnClick({R.id.btn_send_sms, R.id.btn_show_date_wheel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_send_sms:
                break;
            case R.id.btn_show_date_wheel:
                pvTime.show(view);
                break;

        }
    }

    private void initTimePicker() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(2018, 3, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2018, 3, 25);
        //时间选择器
        pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                /*btn_Time.setText(getTime(date));*/
                Button btn = (Button) v;
                btn.setText(getTime(date));
            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{false, false, true, false, false, false})
                .setLabel("", "", "", "", "", "")
                .isCenterLabel(false)
                .setDividerColor(Color.DKGRAY)
                .setContentSize(21)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
//                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    @OnClick(R.id.btn_validate)
    public void onViewClicked() {
        //必填：初始化 captcha框架
        mCaptcha.start();
        mLoginTask = new UserLoginTask();
        //关闭mLoginTask任务可以放在myCaptchaListener的onCancel接口中处理
        mLoginTask.execute();
    }

    private void toastMsg(String msg) {
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_send_sms)
    public void onSendSmsClicked() {
    }

    @OnClick(R.id.btn_send_sms)
    public void onClicked() {
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        UserLoginTask() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            //可选：简单验证DeviceId、CaptchaId、Listener值
            return mCaptcha.checkParams();
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success) {
                //必填：开始验证
                mCaptcha.Validate();

            } else {
                toastMsg("验证码SDK参数设置错误,请检查配置");
            }
        }

        @Override
        protected void onCancelled() {
            mLoginTask = null;
        }
    }
}
