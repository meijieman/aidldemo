package com.foo.aidldemo.service.constant;

/**
 * @Desc: TODO
 * @Author: Major
 * @Since: 2016/8/28 9:58
 */
public interface PlayState {

    int STATE_PREPARE  = 0; // 准备播放

    int STATE_PLAYING    = 1; // 播放中
    int STATE_PAUSE      = 2; // 暂停
    int STATE_STOP       = 3; // 停止
    int STATE_COMPLETION = 4; // 播放完成
    int STATE_BUFFERING  = 5; // 缓冲中
    int STATE_BUFFERED   = 6; // 缓冲完成
    int STATE_ERROR      = 7; // 出错

}
