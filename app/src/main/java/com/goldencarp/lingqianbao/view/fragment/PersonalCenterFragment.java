package com.goldencarp.lingqianbao.view.fragment;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.goldencarp.lingqianbao.R;
import com.goldencarp.lingqianbao.presenter.PersonalCenterFragmentPresenter;
import com.goldencarp.lingqianbao.view.LQBApp;
import com.goldencarp.lingqianbao.view.activity.FAQActivity;
import com.goldencarp.lingqianbao.view.custom.PersonalCenterItemView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalCenterFragment extends BaseFragment {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tb_intelligent_mode)
    ToggleButton tbIntelligentMode;
    Unbinder unbinder;
    @BindView(R.id.iv_head_portrait)
    ImageView ivHeadPortrait;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_tel)
    TextView tvTel;
    @BindView(R.id.expirence_money)
    PersonalCenterItemView expirenceMoney;
    @BindView(R.id.coupon_pack)
    PersonalCenterItemView couponPack;
    @BindView(R.id.faq)
    PersonalCenterItemView faq;
    @BindView(R.id.tv_switch_account)
    TextView tvSwitchAccount;
    private PersonalCenterFragmentPresenter mPresenter;

    public PersonalCenterFragment() {
        // Required empty public constructor
    }


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_personal_center, container, false);
//        unbinder = ButterKnife.bind(this, view);
//        super.onCreateView(inflater, container, savedInstanceState);
//        return view;
//    }

    @Override
    protected Object getContentView() {
        View view = LayoutInflater.from(LQBApp.getApp()).inflate(R.layout.fragment_personal_center, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initData() {
        mPresenter = new PersonalCenterFragmentPresenter();
        stateLayout.showContentView();
    }

    @Override
    protected void initView() {
        expirenceMoney.setMsgAmount(10);
        tbIntelligentMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getActivity(), "智能模式已开启", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "智能模式已关闭", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null){
            unbinder.unbind();
        }
    }

    @OnClick({R.id.expirence_money, R.id.coupon_pack, R.id.faq, R.id.tv_switch_account})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.expirence_money:
                Toast.makeText(getActivity(), "点击了体验金", Toast.LENGTH_SHORT).show();
                break;
            case R.id.coupon_pack:
                Toast.makeText(getActivity(), "点击了券包", Toast.LENGTH_SHORT).show();
                break;
            case R.id.faq:
                Intent intent = new Intent(getActivity(), FAQActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_switch_account:

                break;
        }
    }

}
