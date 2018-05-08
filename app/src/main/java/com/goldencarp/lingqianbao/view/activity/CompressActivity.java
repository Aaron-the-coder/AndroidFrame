package com.goldencarp.lingqianbao.view.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.goldencarp.lingqianbao.R;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CompressActivity extends AppCompatActivity {

    private static final String LOG = "CompressActivity";
    @BindView(R.id.compress_img_quality)
    Button compressImg;
    @BindView(R.id.et_compress_rate)
    EditText etCompressRate;
    @BindView(R.id.iv_show_result)
    ImageView ivShowResult;
    @BindView(R.id.sample_compress)
    Button sampleCompress;
    @BindView(R.id.btn_scale_compress)
    Button btnScaleCompress;
    @BindView(R.id.btn_rgb_compress)
    Button btnRgbCompress;
    @BindView(R.id.btn_scale_reset)
    Button btnScaledReset;
    private Bitmap bit;
    private String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compress);
        ButterKnife.bind(this);
        filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Camera/a.png";
        bit = BitmapFactory.decodeFile(filePath);
        ivShowResult.setImageBitmap(bit);
        Log.i(LOG, "压缩前的图片大小为:" + bit.getByteCount() / 1024 / 1024 + "M\n" + "图片的宽为:" + bit.getWidth() + "图片的高为:" + bit.getHeight());
    }


    @OnClick({R.id.compress_img_quality, R.id.sample_compress, R.id.btn_scale_compress, R.id.btn_rgb_compress, R.id.btn_scale_reset, R.id.btn_reset})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.compress_img_quality:
                qualityCompress();
                break;
            case R.id.sample_compress:
                sampleCompress();
                break;
            case R.id.btn_scale_compress:
                scaleCompress();
                break;
            case R.id.btn_rgb_compress:
                rgbCompress();
                break;
            case R.id.btn_scale_reset:
                resetScale();
                break;
            case R.id.btn_reset:
                ivShowResult.setImageBitmap(bit);
                break;
        }
    }

    /**
     * 将图片压缩到一个固定的像素值
     */
    private void resetScale() {
        Bitmap bitmap = Bitmap.createScaledBitmap(bit, 200, 200, true);
        ivShowResult.setImageBitmap(bitmap);
        Log.i(LOG, "resetScale压缩后的图片大小为:" + bitmap.getByteCount() / 1024 / 1024 + "M\n" + "图片的宽为:" + bitmap.getWidth() + "图片的高为:" + bitmap.getHeight());
    }

    /**
     * 将图片像素位数降低为16位,比如RGB_565或者ARGB_4444
     */
    private void rgbCompress() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        ivShowResult.setImageBitmap(bitmap);
        Log.i(LOG, "rgbCompress压缩后的图片大小为:" + bitmap.getByteCount() / 1024 / 1024 + "M\n" + "图片的宽为:" + bitmap.getWidth() + "图片的高为:" + bitmap.getHeight());
    }

    /**
     * 利用matrix将图片按照一定的比例缩放
     */
    private void scaleCompress() {
        Matrix matrix = new Matrix();
        matrix.setScale(0.5f, 0.5f);
        Bitmap bitmap = Bitmap.createBitmap(bit, 0, 0, bit.getWidth(), bit.getHeight(), matrix, true);
        ivShowResult.setImageBitmap(bitmap);
        Log.i(LOG, "scaleCompress压缩后的图片大小为:" + bitmap.getByteCount() / 1024 / 1024 + "M\n" + "图片的宽为:" + bitmap.getWidth() + "图片的高为:" + bitmap.getHeight());
    }

    /**
     * 取样法压缩
     */
    private void sampleCompress() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        ivShowResult.setImageBitmap(bitmap);
        Log.i(LOG, "sampleCompress压缩后的图片大小为:" + bitmap.getByteCount() / 1024 / 1024 + "M\n" + "图片的宽为:" + bitmap.getWidth() + "图片的高为:" + bitmap.getHeight());
    }

    /**
     * 质量压缩,内存大小不变,字节长度变短
     */
    private void qualityCompress() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Integer quality = Integer.valueOf(etCompressRate.getText().toString());
        bit.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        byte[] data = baos.toByteArray();
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        ivShowResult.setImageBitmap(bitmap);
        Log.i(LOG, "qualityCompress压缩后的图片大小为:" + bitmap.getByteCount() / 1024 / 1024 + "M\n" + "图片的宽为:" + bitmap.getWidth() + "图片的高为:" + bitmap.getHeight());
    }
}
