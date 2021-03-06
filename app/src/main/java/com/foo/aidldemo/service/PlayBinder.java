package com.foo.aidldemo.service;

import android.media.MediaPlayer;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

import com.foo.aidldemo.OnPlayListener;
import com.foo.aidldemo.OnPlayedListener;
import com.foo.aidldemo.PlayServiceAIDL;
import com.foo.aidldemo.aidl.Music;
import com.foo.aidldemo.service.constant.PlayState;
import com.foo.aidldemo.utils.LogUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/12.
 */
public class PlayBinder extends PlayServiceAIDL.Stub {

    private MediaPlayer mPlayer;
    private List<Music> mPlayList = new ArrayList<>(); // 播放列表

    private OnPlayedListener mPlayedListener; // 播放完成回调，供自动播放下一曲使用

    private RemoteCallbackList<OnPlayListener> mCallbackList = new RemoteCallbackList<>(); // 播放状态回调

    private int mPosition = -1; // 当前播放位置
    private int mState;

    public PlayBinder() {
        mPlayer = new MediaPlayer();
        initListener();
    }

    private void initListener() {
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                changeState(PlayState.STATE_COMPLETION, null);
                if (mPlayedListener != null) {
                    try {
                        mPlayedListener.OnPlayed();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        // 得到缓冲的百分比
        mPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                int count = mCallbackList.beginBroadcast();
                for (int i = 0; i < count; i++) {
                    try {
                        mCallbackList.getBroadcastItem(i).onBufferingUpdate(percent);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                mCallbackList.finishBroadcast();
            }
        });
        mPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                LogUtil.e("what " + what + " extra " + extra);
                // 当一些特定信息出现或者警告时触发
                switch (what) {
                    case MediaPlayer.MEDIA_INFO_BAD_INTERLEAVING:

                        break;
                    case MediaPlayer.MEDIA_INFO_METADATA_UPDATE:

                        break;
                    case MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:

                        break;
                    case MediaPlayer.MEDIA_INFO_NOT_SEEKABLE:

                        break;
                    case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                        // 未知错误

                        break;
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                        // 暂停播放等待缓冲更多数据

                        break;
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                        // 在缓冲完后继续播放

                        break;
                    default:

                        break;
                }
                return true;
            }
        });
        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                changeState(PlayState.STATE_PLAYING, null);
                mp.start();
            }
        });
    }

    // 改变状态，发送回调
    private void changeState(int state, String msg) {
        mState = state;
        int count = mCallbackList.beginBroadcast();
        for (int i = 0; i < count; i++) {
            try {
                mCallbackList.getBroadcastItem(i).onStateChanged(mState, msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        mCallbackList.finishBroadcast();
    }

    @Override
    public void play(int pos) {
        if (mPosition == pos && mState == PlayState.STATE_PLAYING) {
            return;
        }
        if (mPlayList.size() == 0) {
            int count = mCallbackList.beginBroadcast();
            for (int i = 0; i < count; i++) {
                try {
                    mCallbackList.getBroadcastItem(i).onMessage("播放列表为空，请先选择歌曲");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            mCallbackList.finishBroadcast();
            return;
        }
        mPosition = pos;
        if (mPlayer == null) {
            throw new IllegalStateException("mediaPlayer 为空");
        }
        if (pos < 0 || pos > mPlayList.size() - 1) {
            throw new IllegalArgumentException("播放列表索引越界，当前为 " + pos + "，最大为 " + (mPlayList.size() - 1));
        }
        try {
            mPlayer.reset();
            mPlayer.setDataSource(mPlayList.get(pos).url);

            // 准备播放
            changeState(PlayState.STATE_PREPARE, null);
            mPlayer.prepareAsync();
            // mPlayer.prepare();// 为耗时操作
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pause() {
        if (mPlayer == null) {
            return;
        }
        if (mPlayer.isPlaying()) {
            mPlayer.pause();
            changeState(PlayState.STATE_PAUSE, null);
        }
    }

    @Override
    public void resume() {
        if (mPlayer == null) {
            throw new IllegalStateException("mediaPlayer 为空");
        }
        if (mState == PlayState.STATE_PAUSE) {
            mPlayer.start();
            changeState(PlayState.STATE_PLAYING, null);
        }
    }

    @Override
    public void stop() {
        if (mPlayer == null) {
            return;
        }
        mPlayer.stop();
        changeState(PlayState.STATE_STOP, null);
    }

    @Override
    public int getPlayState() throws RemoteException {
        return mState;
    }

    @Override
    public void setPlayList(List<com.foo.aidldemo.aidl.Music> data) {
        if (data != null) {
            mPlayList.clear();
            mPlayList.addAll(data);
        }
    }

    @Override
    public List<com.foo.aidldemo.aidl.Music> getPlayList() {
        return mPlayList;
    }

    @Override
    public int getCurrentPosition() throws RemoteException {
        if (mPosition < mPlayList.size() && mPosition >= 0) {
            return mPosition;
        }
        return -1;
    }

    @Override
    public Music getCurrentMusic() throws RemoteException {
        if (mPosition < mPlayList.size() && mPosition >= 0) {
            return mPlayList.get(mPosition);
        }
        return null;
    }

    @Override
    public void registerPlayListener(OnPlayListener listener) throws RemoteException {
        if (listener != null) {
            mCallbackList.register(listener);
        }
    }

    @Override
    public void unRegisterPlayListener(OnPlayListener listener) throws RemoteException {
        if (listener != null) {
            mCallbackList.unregister(listener);
        }
    }

    @Override
    public void setPlayedListener(OnPlayedListener listener) throws RemoteException {
        if (listener != null) {
            mPlayedListener = listener;
        }
    }

    @Override
    public void removePlayedListener(OnPlayedListener listener) throws RemoteException {
        mPlayedListener = null;
    }
}
