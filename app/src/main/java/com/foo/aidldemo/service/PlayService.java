package com.foo.aidldemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.foo.aidldemo.utils.LogUtils;
/**
 * Created by Administrator on 2016/6/21.
 */
public class PlayService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.e( "onCreate: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.e( "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.e( "onDestroy: ");
    }

    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.e( "onBind: ");
        return new PlayBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtils.e( "onUnbind: ");
        return super.onUnbind(intent);
    }
}
