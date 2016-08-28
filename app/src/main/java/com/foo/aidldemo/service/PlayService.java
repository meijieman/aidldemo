package com.foo.aidldemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.foo.aidldemo.utils.LogUtil;

/**
 * Created by Administrator on 2016/6/21.
 */
public class PlayService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.e( "onCreate: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.e( "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.e( "onDestroy: ");
    }

    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.e( "onBind: ");
        return new PlayBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtil.e( "onUnbind: ");
        return super.onUnbind(intent);
    }
}
