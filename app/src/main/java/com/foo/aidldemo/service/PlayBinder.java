package com.foo.aidldemo.service;

import com.foo.aidldemo.PlayServiceAIDL;
import com.foo.aidldemo.utils.LogUtils;

/**
 * Created by Administrator on 2016/7/12.
 */
public class PlayBinder extends PlayServiceAIDL.Stub {

    // 实现自定义的方法
    @Override
    public void play() {
        LogUtils.e("play: ");
    }

    @Override
    public void pause() {
        LogUtils.e("pause: ");
    }

    @Override
    public void stop() {
        LogUtils.e("stop: ");
    }

    @Override
    public void previous() {
        LogUtils.e("previous: ");
    }

    @Override
    public void next() {
        LogUtils.e("next: ");
    }
}
