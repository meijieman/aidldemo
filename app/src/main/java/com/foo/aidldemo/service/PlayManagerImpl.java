package com.foo.aidldemo.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.foo.aidldemo.OnPlayListener;
import com.foo.aidldemo.PlayServiceAIDL;
import com.foo.aidldemo.aidl.Music;
import com.foo.aidldemo.service.constant.PlayOrder;

import java.util.List;

/**
 * @Desc: TODO
 * @Author: Major
 * @Since: 2016/8/27 23:30
 */
public class PlayManagerImpl implements PlayManager {

    private int mSortBy = PlayOrder.ORDER_INORDER; // 播放顺序，默认顺序播放

    private Context         mContext;
    private PlayServiceAIDL mBinder;

    public PlayManagerImpl(Context ctx) {
        mContext = ctx;
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
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBinder = null;
        }
    };

    public void init() {
        Intent service = new Intent(mContext, PlayService.class);
        mContext.bindService(service, conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void destroy() {
        mContext.unbindService(conn);
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

    @Override
    public int nextPosition() {
        return -1;
    }

    @Override
    public int previousPosition() {
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

        return false;
    }

    @Override
    public boolean playPrevious() {

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

    }

    @Override
    public void resume() {

    }

    @Override
    public void stop() {

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
