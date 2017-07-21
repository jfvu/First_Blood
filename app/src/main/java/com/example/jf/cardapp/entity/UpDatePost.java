package com.example.jf.cardapp.entity;

/**
 * Created by jf on 2017/2/26.
 */

public class UpDatePost {
    private String UserName;
    private String Password;

    public UpDatePost(String userName, String password) {
        UserName = userName;
        Password = password;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @Override
    public String toString() {
        return "UpDatePost{" +
                "UserName='" + UserName + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }
}
