package com.foo.aidldemo.service;

import android.media.MediaPlayer;
import android.os.RemoteException;

import com.foo.aidldemo.OnPlayListener;
import com.foo.aidldemo.PlayServiceAIDL;
import com.foo.aidldemo.aidl.Music;
import com.foo.aidldemo.utils.LogUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/12.
 */
public class PlayBinder extends PlayServiceAIDL.Stub {

    // 1 顺序播放；2 单曲循环；3 随机播放； 4 列表循环； 5 倒序播放; 6 单曲播放
    public static final int ORDER = 1;
    public static final int REPATE_ONCE = 2;
    public static final int SHUFFLE = 3;
    public static final int REPATE_LIST = 4;
    public static final int REPATE_LIST_REVERSE = 5;
    public static final int ONCE = 6;

    private MediaPlayer mPlayer;
    private int mSortBy; // 播放顺序
    private List<Music> mPlayList = new ArrayList<>();

    private OnPlayListener mPlayListener;
    private int mPosition = -1; // 当前播放位置

    public PlayBinder() {
        mPlayer = new MediaPlayer();
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (mPlayListener != null) {
                    try {
                        mPlayListener.onPlayCompleted();
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
                if (mPlayListener != null) {
                    try {
                        mPlayListener.onBufferingUpdate(percent);
                        mPlayListener.onUpdate((int) (mp.getCurrentPosition() * 100.0f / mp.getDuration()));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        mPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {

                LogUtils.e("what " + what + " extra " + extra);
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
                }
                return true;
            }
        });
    }

    // 实现自定义的方法
    @Override
    public void play(int pos) {
        mPosition = pos;
        if (mPlayer == null) {
            throw new IllegalStateException("mediaPlayer 为空");
        }
        if (pos < 0){
            throw new IllegalArgumentException("播放列表索引不能小于 0，当前为 " + pos);
        }
        if (mPlayList.size() - 1 < pos ) {
            LogUtils.e("准备数据中");
            return;
        }
        try {
            mPlayer.reset();
            mPlayer.setDataSource(mPlayList.get(pos).url);
            mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    // 其他线程
                    // 监听准备完成
                    if (mPlayListener != null) {
                        try {
                            mPlayListener.onPrepared();
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    mp.start();
                }
            });
            if (mPlayListener != null) {
                mPlayListener.onPrepareStart();
            }
            mPlayer.prepareAsync();
            // mPlayer.prepare();// 为耗时操作
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        LogUtils.e("play: ");
    }

    @Override
    public void pause() {
        if (mPlayer == null) {
            return;
        }
        if (mPlayer.isPlaying()) {
            mPlayer.pause();
            LogUtils.e("pause: ");
        }
    }

    @Override
    public void stop() {
        if (mPlayer == null) {
            return;
        }
        mPlayer.stop();
    }

    @Override
    public void previous() {
        LogUtils.e("previous: ");
    }

    @Override
    public void next() {
        LogUtils.e("next: ");
    }

    @Override
    public void setPlayList(List<com.foo.aidldemo.aidl.Music> data) {
        mPlayList.clear();
        mPlayList.addAll(data);
    }

    @Override
    public List<com.foo.aidldemo.aidl.Music> getPlayList() {
        return mPlayList;
    }

    @Override
    public Music getPlayMusic() throws RemoteException {
        return mPlayList.get(mPosition);
    }

    @Override
    public void setOrder(int sortBy) {
        mSortBy = sortBy;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public void resume() {
        if (mPlayer == null) {
            throw new IllegalStateException("mediaPlayer 为空");
        }
        if (!mPlayer.isPlaying()) {
            mPlayer.start();
        }
    }

    @Override
    public void setOnPlayListener(OnPlayListener listener) {
        mPlayListener = listener;
    }
}
