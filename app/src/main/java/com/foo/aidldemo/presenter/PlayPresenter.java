package com.foo.aidldemo.presenter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;

import com.foo.aidldemo.OnPlayListener;
import com.foo.aidldemo.PlayServiceAIDL;
import com.foo.aidldemo.aidl.Music;
import com.foo.aidldemo.listener.OnNetResultListener;
import com.foo.aidldemo.model.PlayModel;
import com.foo.aidldemo.service.PlayService;
import com.foo.aidldemo.utils.LogUtils;
import com.foo.aidldemo.view.PlayView;

import java.util.List;


/**
 * Created by Administrator on 2016/6/21.
 */
public class PlayPresenter {

    private PlayView mView;
    private PlayServiceAIDL mBinder;
    private Handler mHandler = new Handler();

    private PlayModel mModel = new PlayModel();

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
        ctx.startService(service);
        ctx.bindService(service, conn, Context.BIND_AUTO_CREATE);
    }

    public void destroy(Context ctx) {
        ctx.unbindService(conn);
    }

    public void play(int pos) throws RemoteException {
        mBinder.setOnPlayListener(new OnPlayListener.Stub() {

            @Override
            public void onPrepareStart() throws RemoteException {
                mView.setStatus("加载中...");
            }

            @Override
            public void onPrepared() throws RemoteException {
                final Music music = mBinder.getPlayMusic();
                // 其他线程
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showT("开始播放《" + music.name + "》");
                    }
                });
            }

            @Override
            public void onBufferingUpdate(int percent) {
                if (percent != 100) {
                    LogUtils.e("缓存进度 " + percent);
                }
            }

            @Override
            public void onUpdate(final int percent) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.setStatus("播放进度 " + percent + "%");
                    }
                });
            }

            @Override
            public void onPlayCompleted() throws RemoteException {
                mView.setStatus("播放结束");
            }
        });
        mBinder.play(pos);
    }

    public void pause() throws RemoteException {
        mBinder.pause();
        mView.setStatus("暂停"); // 应该写在回调中
    }

    public void resume() throws RemoteException {
        mBinder.resume();
        mView.setStatus("继续播放");
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

    public void setPlayList(List<Music> data) {
        try {
            mBinder.setPlayList(data);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void loadData(int page) {
        mModel.loadData(page, new OnNetResultListener() {
            @Override
            public void onSuccess(List<Music> data) {
                setPlayList(data);
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }
}
