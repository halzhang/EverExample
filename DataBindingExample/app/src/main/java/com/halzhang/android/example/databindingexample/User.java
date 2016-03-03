package com.halzhang.android.example.databindingexample;

/**
 * Created by zhanghanguo@yy.com on 2015/6/4.
 */
public class User {
    public final String username;
    public final String password;
    public final String avatar;

    public User(String username, String password, String avatar) {
        this.username = username;
        this.password = password;
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAvatar() {
        return avatar;
    }
}
