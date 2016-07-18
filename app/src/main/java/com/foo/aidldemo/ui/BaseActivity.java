package com.foo.aidldemo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/6/21.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private static Toast sToast;
    protected static BaseActivity sActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        ButterKnife.bind(this);
        sActivity = this;

        initEventAndListener();
    }

    /**
     * 初始化
     */
    protected abstract void initEventAndListener();

    /**
     * 获取布局文件的 id
     * @return
     */
    protected abstract int getContentView();

    /**
     * toast
     * @param msg
     */
    public static void showToast(String msg) {
        if (sToast == null) {
            sToast = Toast.makeText(sActivity, "", Toast.LENGTH_SHORT);
        }
        sToast.setText(msg);
        sToast.show();
    }

}
