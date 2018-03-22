package com.goldencarp.lingqianbao.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.goldencarp.lingqianbao.R;

import org.apache.commons.codec1.digest.DigestUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DemoActivity extends AppCompatActivity {

    @BindView(R.id.et_raw_str)
    EditText etRawStr;
    @BindView(R.id.tv_encode_result)
    TextView tvEncodeResult;
    @BindView(R.id.btn_test_doonnext)
    Button btnTestDoonnext;
    private boolean isEncrypt;//已经加密

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_test_doonnext)
    public void onViewClicked() {
        encodeTest();
    }

    /**
     * 测试sha1加密
     */
    private void encodeTest() {
        if (isEncrypt){
//            CharSequence encryptedText = tvEncodeResult.getText();
//            DigestUtils.sha1Hex()
        }else {
            String rawStr = etRawStr.getText().toString().trim();
            if (!TextUtils.isEmpty(rawStr)){
                tvEncodeResult.setText(DigestUtils.sha1Hex(rawStr));
//                tvEncodeResult.setText(StringUtil.encode(rawStr));
            }
        }

    }
}
