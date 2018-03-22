package com.goldencarp.lingqianbao.model.bean;

/**
 * Created by sks on 2017/12/2.
 */

public class ProductBean {
    private String name;//产品名称
    private String rate;//预期年化收益率
    private int time;//投资时间

    public ProductBean() {
        super();
    }

    public ProductBean(String name, String rate, int time) {
        this.name = name;
        this.rate = rate;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "ProductBean{" +
                "name='" + name + '\'' +
                ", rate='" + rate + '\'' +
                ", time=" + time +
                '}';
    }
}
