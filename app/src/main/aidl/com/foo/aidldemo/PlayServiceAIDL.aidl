// PlayAIDL.aidl
package com.foo.aidldemo;

// Declare any non-default types here with import statements
import com.foo.aidldemo.aidl.Music;
import com.foo.aidldemo.OnPlayListener;

interface PlayServiceAIDL {

    void play(int pos);
    void pause();
    void resume();
    void stop();

    int getPlayState(); // 播放状态

    void setPlayList(in List<Music> data);
    List<Music> getPlayList();

    int getCurrentPosition(); // 正在播放的音乐的位置 -1 表示没有播放音乐
    Music getCurrentMusic(); // 正在播放的音乐

    void registerPlayListener(OnPlayListener listener);// 播放回调
    void unRegisterPlayListener(OnPlayListener listener);
}
