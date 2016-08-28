// OnPrepareListener.aidl
package com.foo.aidldemo;

 // 因为开始播放需要解码，是耗时操作，提供回调
interface OnPlayListener {

    void onBufferingUpdate(int percent); // 缓存进度
    void onUpdate(int percent); // 歌曲进度
    void onStateChanged(int state, String msg); // 播放器状态改变
}
