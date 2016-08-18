package com.foo.aidldemo.model;

import android.os.SystemClock;

import com.foo.aidldemo.aidl.Music;
import com.foo.aidldemo.listener.OnNetResultListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/18.
 */
public class PlayModel {

    public void loadData(int page, final OnNetResultListener listener) {
        // 模拟在线加载数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(3000);
                List<Music> data = new ArrayList<>();
                Music music = new Music();
                music.name = "渡红尘";
                music.id = 1001;
                music.imgUrl = "http://img.xiami.net/images/album/img96/683379096/21003568111466524385_1.jpg";
                music.url = "http://m5.file.xiami.com/201/1506278201/2100356811/1776200826_60350905_l.mp3?auth_key=5f8e6a87570d5bc6c1c88eeda8d8c4cb-1477065600-0-null";
                data.add(music);
                listener.onSuccess(data);
            }
        }).start();
    }
}
