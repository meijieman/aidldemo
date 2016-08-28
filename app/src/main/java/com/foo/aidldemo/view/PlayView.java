package com.foo.aidldemo.view;

/**
 * Created by Administrator on 2016/6/21.
 */
public interface PlayView {

    void setStatus(String msg); // 播放状态
    void setPlayProcess(String process); // 播放进度
    void setBufferProcess(String process); // 缓冲进度

    void showT(String msg);
}
