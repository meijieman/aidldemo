package com.foo.aidldemo.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.RemoteException;

import com.foo.aidldemo.OnPlayListener;
import com.foo.aidldemo.aidl.Music;
import com.foo.aidldemo.listener.OnNetResultListener;
import com.foo.aidldemo.model.PlayModel;
import com.foo.aidldemo.service.PlayManager;
import com.foo.aidldemo.service.PlayManagerImpl;
import com.foo.aidldemo.service.PlayService;
import com.foo.aidldemo.service.constant.PlayState;
import com.foo.aidldemo.utils.LogUtils;
import com.foo.aidldemo.view.PlayView;

import java.util.List;


/**
 * Created by Administrator on 2016/6/21.
 */
public class PlayPresenter {

    private PlayView mView;
    private Handler   mHandler = new Handler();
    private PlayModel mModel   = new PlayModel();
    private PlayManager mManager;

    public PlayPresenter(PlayView view) {
        mView = view;
    }

    public void create(Context ctx) {
        Intent service = new Intent(ctx, PlayService.class);
        ctx.startService(service);
        mManager = new PlayManagerImpl(ctx);
        mManager.init();
        mView.setStatus("初始化播放器");
    }

    private OnPlayListener mListener = new OnPlayListener.Stub() {

        @Override
        public void onBufferingUpdate(final int percent) throws RemoteException {
            LogUtils.e("缓冲进度 " + percent + "%");
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mView.setBufferProcess("缓冲进度 " + percent + "%");
                }
            });
        }

        @Override
        public void onUpdate(final int percent) throws RemoteException {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mView.setPlayProcess("播放进度 " + percent + "%");
                }
            });
        }

        @Override
        public void onStateChanged(final int state, final String msg) throws RemoteException {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    String name = mManager.getCurrentMusic().name;
                    switch (state) {
                        case PlayState.STATE_PREPARE:
                            mView.showT("准备播放《" + name + "》");
                            break;
                        case PlayState.STATE_PLAYING:
                            mView.showT("正在播放《" + name + "》");
                            break;
                        case PlayState.STATE_STOP:
                            mView.showT("停止");
                            break;
                        case PlayState.STATE_PAUSE:
                            mView.setStatus("暂停");
                            break;
                        case PlayState.STATE_ERROR:
                            mView.setStatus("出错 " + msg);
                            break;
                    }
                }
            });
        }
    };

    public void destroy() {
        mManager.unRegister(mListener);
        mManager.destroy();
    }

    public void play(int pos) throws RemoteException {
        mManager.play(pos);
    }

    public void pause() throws RemoteException {
        mManager.pause();
    }

    public void resume() throws RemoteException {
        mManager.resume();
    }

    public void stop() throws RemoteException {
        mManager.stop();
    }

    public void previous() throws RemoteException {
        boolean isSuccess = mManager.playPrevious();
    }

    public void next() throws RemoteException {
        boolean isSuccess = mManager.playNext();
    }

    public void setPlayList(List<Music> data) {
        mManager.setPlayList(data);
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
