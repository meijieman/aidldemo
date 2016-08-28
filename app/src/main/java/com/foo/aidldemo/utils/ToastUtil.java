package com.foo.aidldemo.utils;

import android.widget.Toast;

import com.foo.aidldemo.base.BaseApp;

/**
 * @Desc: TODO
 * @Author: Major
 * @Since: 2016/8/28 23:30
 */
public class ToastUtil {

    private static Toast sToast;

    /**
     * toast
     * @param msg
     */
    public static void showToast(String msg) {
        if (sToast == null) {
            sToast = Toast.makeText(BaseApp.getInstance(), "", Toast.LENGTH_SHORT);
        }
        sToast.setText(msg);
        sToast.show();
    }
}
