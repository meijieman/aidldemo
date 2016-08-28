package com.foo.aidldemo.model;

import android.os.SystemClock;
import android.support.annotation.NonNull;

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
                List<Music> data = getMusics();
                listener.onSuccess(data);
            }
        }).start();
    }

    @NonNull
    private List<Music> getMusics() {
        List<Music> data = new ArrayList<>();
        Music music = new Music();
        music.name = "渡红尘";
        music.id = 1000;
        music.imgUrl = "http://img.xiami.net/images/album/img96/683379096/21003568111466524385_1.jpg";
        music.url = "http://m5.file.xiami.com/201/1506278201/2100356811/1776200826_60350905_l.mp3?auth_key=5f8e6a87570d5bc6c1c88eeda8d8c4cb-1477065600-0-null";
        data.add(music);

        Music music1 = new Music();
        music1.name = "";
        music1.id = 1001;
        music1.url = "http://m5.file.xiami.com/672/119672/1609297031/1773484877_15715337_l.mp3?auth_key=df8ee76c6357a2fc57203cf454fda42f-1474560000-0-null";
        data.add(music1);

        Music music2 = new Music();
        music2.name = "";
        music2.id = 1002;
        music2.url = "http://m5.file.xiami.com/258/23258/1329241248/1774185888_16584888_l.mp3?auth_key=7f44fb99d26ec5cc12974fe6f10373f6-1474560000-0-null";
        data.add(music2);

        Music music3 = new Music();
        music3.name = "";
        music3.id = 1003;
        music3.url = "http://m5.file.xiami.com/845/915604845/2100337872/1776083208_60218314_l.mp3?auth_key=9cb89fd9a8596bfe5daedad02df1157f-1476201600-0-null";
        data.add(music3);

        Music music4 = new Music();
        music4.name = "";
        music4.id = 1004;
        music4.url = "http://m5.file.xiami.com/845/915604845/2100342628/1776112408_60272182_l.mp3?auth_key=7857f081f35554c7ef77dc62ff057b81-1476201600-0-null";
        data.add(music4);

        Music music5 = new Music();
        music5.name = "";
        music5.id = 1005;
        music5.url = "http://m5.file.xiami.com/256/23256/2100326216/1776002808_60306282_l.mp3?auth_key=5a16b01027c08ba4db182afdebfabc73-1476201600-0-null";
        data.add(music5);

        Music music6 = new Music();
        music6.name = "";
        music6.id = 1006;
        music6.url = "http://m5.file.xiami.com/40/1905327040/2100350207/1776156054_60302449_l.mp3?auth_key=98a52b1354ed72d85aadbef1b94d5802-1476201600-0-null";
        data.add(music6);

        return data;
    }
}
