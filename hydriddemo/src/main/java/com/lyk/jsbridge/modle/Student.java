package com.lyk.jsbridge.modle;

/**
 * Created by LIUYONGKUI726 on 2016-06-01.
 */
public class Student {
    private String name = "Bruce";
    private String pass = "Bruce";

    public Student() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }
}
