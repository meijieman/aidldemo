package com.foo.aidldemo.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/7/18.
 */
public class Music implements Parcelable {

    public String name;
    public int    id;
    public String url;
    public String imgUrl;
    public String singer;
    public int    level;

    public Music() {

    }

    protected Music(Parcel in) {
        name = in.readString();
        id = in.readInt();
        url = in.readString();
        imgUrl = in.readString();
        singer = in.readString();
        level = in.readInt();
    }

    public static final Creator<Music> CREATOR = new Creator<Music>() {
        @Override
        public Music createFromParcel(Parcel in) {
            return new Music(in);
        }

        @Override
        public Music[] newArray(int size) {
            return new Music[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(id);
        dest.writeString(url);
        dest.writeString(imgUrl);
        dest.writeString(singer);
        dest.writeInt(level);
    }

    @Override
    public String toString() {
        return "Music{" +
               "name='" + name + '\'' +
               ", id=" + id +
               ", url='" + url + '\'' +
               ", imgUrl='" + imgUrl + '\'' +
               '}';
    }
}
