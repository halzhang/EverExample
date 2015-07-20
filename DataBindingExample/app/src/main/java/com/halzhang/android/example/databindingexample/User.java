package com.halzhang.android.example.databindingexample;

/**
 * Created by zhanghanguo@yy.com on 2015/6/4.
 */
public class User {
    public final String username;
    public final String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
