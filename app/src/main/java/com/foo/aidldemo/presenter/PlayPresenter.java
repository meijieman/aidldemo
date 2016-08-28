package com.foo.aidldemo.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.foo.aidldemo.OnPlayListener;
import com.foo.aidldemo.aidl.Music;
import com.foo.aidldemo.base.BaseApp;
import com.foo.aidldemo.listener.OnNetResultListener;
import com.foo.aidldemo.model.PlayModel;
import com.foo.aidldemo.service.PlayManager;
import com.foo.aidldemo.service.PlayManagerImpl;
import com.foo.aidldemo.service.PlayService;
import com.foo.aidldemo.service.constant.PlayState;
import com.foo.aidldemo.utils.SPUtil;
import com.foo.aidldemo.view.PlayView;

import java.util.List;


/**
 * Created by Administrator on 2016/6/21.
 */
public class PlayPresenter {

    public static final String CURR_POSITION = "curr_position";
    private PlayView mView;
    private Handler   mHandler = new Handler();
    private PlayModel mModel   = new PlayModel();
    private PlayManager mManager;

    public PlayPresenter(PlayView view) {
        mView = view;
    }

    public void create() {
        Context ctx = BaseApp.getInstance();
        Intent service = new Intent(ctx, PlayService.class);
        ctx.startService(service);
        mManager = PlayManagerImpl.getInstance();
        mManager.init(ctx);
        mView.setStatus("初始化播放器");
        mManager.register(mListener);
    }

    private OnPlayListener mListener = new OnPlayListener.Stub() {

        @Override
        public void onBufferingUpdate(final int percent) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mView.setBufferProcess("缓冲进度 " + percent + "%");
                }
            });
        }

        @Override
        public void onUpdate(final int percent) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mView.setPlayProcess("播放进度 " + percent + "%");
                }
            });
        }

        @Override
        public void onStateChanged(final int state, final String msg) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    String name = mManager.getCurrentMusic().name;
                    switch (state) {
                        case PlayState.STATE_PREPARE:
                            mView.showT("准备播放《" + name + "》");
                            break;
                        case PlayState.STATE_PLAYING:
                            SPUtil.putInt(CURR_POSITION, mManager.getCurrentPosition());
                            mView.setStatus("正在播放《" + name + "》");
                            break;
                        case PlayState.STATE_STOP:
                            mView.setStatus("停止");
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

        @Override
        public void onMessage(String msg) {
            mView.showT(msg);
        }
    };

    public void destroy() {
        mManager.unRegister(mListener);
        mManager.destroy(BaseApp.getInstance());
    }

    public void play(int pos) {
        // 获取上次播放的歌曲位置
        if (pos == -1) {
            pos = SPUtil.getInt(CURR_POSITION, 0);
        }
        mManager.play(pos);
    }

    public void pause() {
        mManager.pause();
    }

    public void resume() {
        mManager.resume();
    }

    public void stop() {
        mManager.stop();
    }

    public void previous() {
        boolean isSuccess = mManager.playPrevious();
        if (!isSuccess) {
            mView.showT("没有上一曲");
        }
    }

    public void next() {
        boolean isSuccess = mManager.playNext();
        if (!isSuccess) {
            mView.showT("没有下一曲");
        }
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
