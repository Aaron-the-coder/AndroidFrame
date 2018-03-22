package com.goldencarp.lingqianbao.presenter;

import com.goldencarp.lingqianbao.model.bean.FAQBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sks on 2017/12/4.
 */

public class FAQActivityPresenter implements IFAQActivityPresenter {

    private List<List<FAQBean>> faqList;

    @Override
    public List<List<FAQBean>> getFAQList() {
        faqList = new ArrayList<>();
        int groupId = 0;
        String groupName = "了解零钱包";
        ArrayList<FAQBean> faqChildList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            faqChildList.add(new FAQBean(groupId, groupName, "question" + i, "answer" + i));
        }
        faqList.add(faqChildList);

        groupId = 1;
        groupName = "银行存管相关";
        ArrayList<FAQBean> faqChildList1 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            faqChildList1.add(new FAQBean(groupId, groupName, "question" + i, "answer" + i));
        }
        faqList.add(faqChildList1);

        return this.faqList;
    }
}
