package com.foo.aidldemo.service.constant;

/**
 * @Desc: TODO
 * @Author: Major
 * @Since: 2016/8/27 23:51
 */
public interface PlayOrder {

    // 1 顺序播放（按照列表顺序播放，列表放完停止播放）；
    // 2 单曲循环；
    // 3 随机播放（随机播放列表中的歌曲，保证每首歌曲都能播放到）；
    // 4 列表循环；
    // 5 倒序播放（相对于顺序播放）;
    // 6 单曲播放（只播放一首，放完停止）
    int ORDER_INORDER     = 1;
    int ORDER_REPATE_ONCE = 2;
    int ORDER_SHUFFLE     = 3;
    int ORDER_REPATE_LIST = 4;
    int ORDER_REVERSE     = 5;
    int ORDER_ONCE        = 6;
}
