package com.example.admin.sample.models;

public class UserBean {
    String name;
    String phno;

    public UserBean(String name, String phno) {
        this.name = name;
        this.phno = phno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhno() {
        return phno;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }
}
