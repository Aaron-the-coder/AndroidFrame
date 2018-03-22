package com.goldencarp.lingqianbao.view.custom.visualkeyboard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.goldencarp.lingqianbao.R;


/**
 * 密码输入布局（6位密码）
 * Created by ayuhani on 2017/6/29.
 */
public class AliPasswordView extends RelativeLayout {

    private String[] numbers;   // 用来保存输入的密码
    private ImageView[] points; // 用来保存每个小黑点
    private FrameLayout[] frameLayouts;

    public AliPasswordView(Context context) {
        this(context, null);
    }

    public AliPasswordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        numbers = new String[6];
        points = new ImageView[6];
        frameLayouts = new FrameLayout[6];

        LayoutInflater.from(context).inflate(R.layout.layout_password, this);
        points[0] = findViewById(R.id.iv_0);
        points[1] = findViewById(R.id.iv_1);
        points[2] = findViewById(R.id.iv_2);
        points[3] = findViewById(R.id.iv_3);
        points[4] = findViewById(R.id.iv_4);
        points[5] = findViewById(R.id.iv_5);

        // 这里获取外层的FrameLayout，是因为之后要给它们添加点击事件
        frameLayouts[0] = findViewById(R.id.fl_0);
        frameLayouts[1] = findViewById(R.id.fl_1);
        frameLayouts[2] = findViewById(R.id.fl_2);
        frameLayouts[3] = findViewById(R.id.fl_3);
        frameLayouts[4] = findViewById(R.id.fl_4);
        frameLayouts[5] = findViewById(R.id.fl_5);
    }

    // 获取保存6位密码的数组
    public String[] getNumbers() {
        return numbers;
    }

    // 获取保存小黑点的数组
    public ImageView[] getPoints() {
        return points;
    }

    // 获取小黑点密码父布局的数组（用来添加点击事件）
    public FrameLayout[] getFrameLayouts() {
        return frameLayouts;
    }

    // 获取6位支付密码
    public String getPassword() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] != null) {
                builder.append(numbers[i]);
            }
        }
        return builder.toString();
    }

    // 清空密码
    public void clear() {
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = null;
        }
        for (int i = 0; i < points.length; i++) {
            points[i].setVisibility(GONE);
        }
    }
}
