package com.ezio.imtest;

import android.app.Application;

import io.rong.imkit.RongIM;

/**
 * Created by Ezio on 2016/5/16.
 */
public class App extends Application {
    public static String sourceUserId;
    @Override
    public void onCreate() {
        super.onCreate();
        RongIM.init(this);
        sourceUserId = "当前用户的账号(userId)";
    }
}
