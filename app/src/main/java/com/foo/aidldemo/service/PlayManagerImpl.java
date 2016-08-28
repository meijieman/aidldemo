package com.foo.aidldemo.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.foo.aidldemo.OnPlayListener;
import com.foo.aidldemo.OnPlayedListener;
import com.foo.aidldemo.PlayServiceAIDL;
import com.foo.aidldemo.aidl.Music;
import com.foo.aidldemo.service.constant.PlayOrder;
import com.foo.aidldemo.utils.LogUtil;

import java.util.List;

/**
 * @Desc: playMusic 管理类 单例
 * @Author: Major
 * @Since: 2016/8/27 23:30
 */
public class PlayManagerImpl implements PlayManager {

    private int mSortBy = PlayOrder.ORDER_INORDER; // 播放顺序，默认顺序播放

    private PlayServiceAIDL mBinder;

    private PlayManagerImpl() {

    }

    private static PlayManager sInstance = new PlayManagerImpl();

    public static PlayManager getInstance() {
        return sInstance;
    }

    public void setSortBy(int sortBy) {
        mSortBy = sortBy;
    }

    public int getSortBy() {
        return mSortBy;
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = PlayServiceAIDL.Stub.asInterface(service);
            try {
                mBinder.registerPlayListener(mListener);
                mBinder.setPlayedListener(new OnPlayedListener.Stub(){
                    @Override
                    public void OnPlayed() throws RemoteException {
                        switch (mSortBy) {
                            case PlayOrder.ORDER_INORDER:
                                boolean isSuccess = playNext();
                                LogUtil.e("ORDER_INORDER 自动播放下一曲 " + isSuccess);
                                break;
                        }
                    }
                });
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            try {
                mBinder.removePlayedListener(null);
            } catch (RemoteException e) {
                e.printStackTrace();
            } finally {
                mBinder = null;
            }
        }
    };

    @Override
    public void init(Context ctx) {
        Intent service = new Intent(ctx, PlayService.class);
        ctx.bindService(service, conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void destroy(Context ctx) {
        ctx.unbindService(conn);
    }


    @Override
    public void setPlayList(List<Music> data) {
        if (mBinder != null) {
            try {
                mBinder.setPlayList(data);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Music> getPlayList() {
        if (mBinder != null) {
            try {
                return mBinder.getPlayList();
            } catch (RemoteException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    @Override
    public Music getCurrentMusic() {
        if (mBinder != null) {
            try {
                return mBinder.getCurrentMusic();
            } catch (RemoteException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    @Override
    public int getCurrentPosition() {
        if (mBinder != null) {
            try {
                return mBinder.getCurrentPosition();
            } catch (RemoteException e) {
                e.printStackTrace();
                return -1;
            }
        }
        return -1;

    }

    @Override
    public int getPlayState() {
        if (mBinder != null) {
            try {
                return mBinder.getPlayState();
            } catch (RemoteException e) {
                e.printStackTrace();
                return 0;
            }
        }
        return 0;
    }

    // -1 表示没有下一个
    private int nextPosition() {
        int total = 0;
        try {
            List<Music> playList = mBinder.getPlayList();
            if (playList != null) {
                total = playList.size();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        int pos = getCurrentPosition();
        switch (mSortBy) {
            case PlayOrder.ORDER_INORDER:
                if (pos >= 0 && pos < total - 1) {
                    return pos + 1;
                }
                break;
        }
        return -1;
    }

    // -1 表示没有上一个
    private int previousPosition() {
        int total = 0;
        try {
            List<Music> playList = mBinder.getPlayList();
            if (playList != null) {
                total = playList.size();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        int pos = getCurrentPosition();

        switch (mSortBy) {
            case PlayOrder.ORDER_INORDER:
                if (pos > 0 && pos <= total - 1) {
                    return pos - 1;
                }
                break;
        }
        return -1;
    }

    @Override
    public boolean playNext() {
        int next = nextPosition();
        if (next != -1) {
            play(next);
            return true;
        }
        return false;
    }

    @Override
    public boolean playPrevious() {
        int previous = previousPosition();
        if (previous != -1) {
            play(previous);
            return true;
        }
        return false;
    }

    @Override
    public void play(int pos) {
        try {
            mBinder.play(pos);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pause() {
        try {
            mBinder.pause();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void resume() {
        try {
            mBinder.resume();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        try {
            mBinder.stop();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setOrder(int sortBy) {

    }

    OnPlayListener mListener;

    @Override
    public void register(OnPlayListener listener) {
        mListener = listener;
    }

    @Override
    public void unRegister(OnPlayListener listener) {
        if (mListener != null) {
            try {
                mBinder.unRegisterPlayListener(mListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
