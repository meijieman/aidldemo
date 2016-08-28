package com.foo.aidldemo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.foo.aidldemo.base.BaseApp;

/**
 * @Desc: TODO
 * @Author: Major
 * @Since: 2016/8/28 23:50
 */
public class SPUtil {

    public static final String SP_NAME = "sp_name";

    public static void putInt(String key, int value) {
        SharedPreferences sp = BaseApp.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getInt(String key, int defVal) {
        SharedPreferences sp = BaseApp.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getInt(key, defVal);
    }

}
