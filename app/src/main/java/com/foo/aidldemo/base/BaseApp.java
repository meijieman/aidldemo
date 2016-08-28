package com.foo.aidldemo.base;

import android.app.Application;

/**
 * @Desc: TODO
 * @Author: Major
 * @Since: 2016/8/28 23:09
 */
public class BaseApp extends Application {

    private static BaseApp sInstance;

    public static BaseApp getInstance() {
        return sInstance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }
}
