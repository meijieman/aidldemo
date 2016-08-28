package com.foo.aidldemo.utils;

import android.util.Log;

/**
 * Created by Administrator on 2016/7/12.
 */
public class LogUtil {

    private static final boolean isDebug = true;
    private static final String  TAG     = "LogUtil";

    public static void e(String msg) {
        if (!isDebug) {
            return;
        }
        String prefix = "";
        StackTraceElement[] traces = Thread.currentThread().getStackTrace();
        if (traces.length >= 3) {
            String filename = traces[3].getFileName().replace(".java", "");
            String methodName = traces[3].getMethodName();
            int lineNumber = traces[3].getLineNumber();
            prefix = "[" + filename + "#" + methodName + "()Line:" + lineNumber + "] ";
        }
        Log.e(TAG, prefix + msg);
    }
}
