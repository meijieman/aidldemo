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
    void previous();
    void next();

    void setPlayList(in List<Music> data);
    List<Music> getPlayList();
    Music getPlayMusic();
    void setOrder(int sortBy); // 播放顺序: 1 顺序播放；2 单曲循环；3 随机播放； 4 列表循环； 5 倒序播放
    boolean hasPrevious();
    boolean hasNext();

    void setOnPlayListener(OnPlayListener listener);// 播放回调
}
