package com.foo.aidldemo.listener;

import com.foo.aidldemo.aidl.Music;

import java.util.List;

/**
 * Created by Administrator on 2016/7/18.
 */
public interface OnNetResultListener {

    void onSuccess(List<Music> data);
    void onFailure(String msg);
}
