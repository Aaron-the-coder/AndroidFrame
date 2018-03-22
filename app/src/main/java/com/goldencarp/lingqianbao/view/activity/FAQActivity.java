package com.goldencarp.lingqianbao.view.activity;

import android.os.Bundle;
import android.widget.ExpandableListView;

import com.goldencarp.lingqianbao.R;
import com.goldencarp.lingqianbao.model.bean.FAQBean;
import com.goldencarp.lingqianbao.presenter.FAQActivityPresenter;
import com.goldencarp.lingqianbao.presenter.IFAQActivityPresenter;
import com.goldencarp.lingqianbao.view.adapter.FAQExpandableListViewAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FAQActivity extends BaseActivity {

    @BindView(R.id.faq_list)
    ExpandableListView faqExpandList;
    private IFAQActivityPresenter mPresenter;
    private List<List<FAQBean>> faqList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    protected void initData() {
        mPresenter = new FAQActivityPresenter();
        faqList = mPresenter.getFAQList();
    }

    protected void initView() {
        faqExpandList.setGroupIndicator(null);
        faqExpandList.setAdapter(new FAQExpandableListViewAdapter(this, faqList));
    }
}
