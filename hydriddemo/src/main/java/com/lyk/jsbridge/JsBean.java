package com.lyk.jsbridge;

/**
 * Created by dale on 2018/1/20.
 */

public class JsBean {

    /**
     * mobile : 18612709396
     */

    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "JsBean{" +
                "mobile='" + mobile + '\'' +
                '}';
    }
}
