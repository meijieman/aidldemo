package com.foo.aidldemo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/6/21.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected static BaseActivity sActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        ButterKnife.bind(this);
        sActivity = this;

        init();
    }

    /**
     * 初始化
     */
    protected abstract void init();

    /**
     * 获取布局文件的 id
     * @return
     */
    protected abstract int getContentView();

}
