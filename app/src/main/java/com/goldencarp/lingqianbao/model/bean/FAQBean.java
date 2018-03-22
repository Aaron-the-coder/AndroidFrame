package com.goldencarp.lingqianbao.model.bean;

/**
 * Created by sks on 2017/12/4.
 */

public class FAQBean {
    private int groupId;
    private String groupName;
    private String question;
    private String answer;

    public FAQBean(int groupId, String groupName, String question, String answer) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return "FAQBean{" +
                "groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
