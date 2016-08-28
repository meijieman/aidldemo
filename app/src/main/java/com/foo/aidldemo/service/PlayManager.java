package com.foo.aidldemo.service;

import com.foo.aidldemo.OnPlayListener;
import com.foo.aidldemo.aidl.Music;

import java.util.List;

/**
 * @Desc: TODO
 * @Author: Major
 * @Since: 2016/8/27 23:24
 */
public interface PlayManager {

    void init(); // 初始化
    void destroy(); // 销毁

    void setPlayList(List<Music> data);
    List<Music> getPlayList();
    Music getCurrentMusic();
    int getCurrentPosition();

    int getPlayState();

    int nextPosition(); // -1 表示没有下一个
    int previousPosition(); // -1 表示没有上一个

    // false 没有下一个
    boolean playNext();
    // false 没有上一个
    boolean playPrevious();
    void play(int pos);
    void pause();
    void resume();
    void stop();

    void setOrder(int sortBy); // 播放顺序: 1 顺序播放；2 单曲循环；3 随机播放； 4 列表循环； 5 倒序播放

    void register(OnPlayListener listener);
    void unRegister(OnPlayListener listener);
}
