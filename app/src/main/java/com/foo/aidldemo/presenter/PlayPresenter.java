package com.foo.aidldemo.presenter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.foo.aidldemo.PlayServiceAIDL;
import com.foo.aidldemo.service.PlayService;
import com.foo.aidldemo.view.PlayView;


/**
 * Created by Administrator on 2016/6/21.
 */
public class PlayPresenter {

    private PlayView mView;
    private PlayServiceAIDL mBinder;


    public PlayPresenter(PlayView view) {
        mView = view;
        mView.setStatus("初始化");
    }

    private ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = PlayServiceAIDL.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public void create(Context ctx) {
        Intent service = new Intent(ctx, PlayService.class);
        ctx.bindService(service, conn, Context.BIND_AUTO_CREATE);
    }

    public void destroy(Context ctx) {
        ctx.unbindService(conn);
    }

    public void play() throws RemoteException {
        mBinder.play();
        mView.setStatus("播放");
    }

    public void pause() throws RemoteException {
        mBinder.pause();
        mView.setStatus("暂停");
    }

    public void stop() throws RemoteException {
        mBinder.stop();
        mView.setStatus("停止");
    }

    public void previous() throws RemoteException {
        mBinder.previous();
        mView.setStatus("上一首");
    }

    public void next() throws RemoteException {
        mBinder.next();
        mView.setStatus("下一首");
    }
}
