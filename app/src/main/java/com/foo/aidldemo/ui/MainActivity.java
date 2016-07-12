package com.foo.aidldemo.ui;

import android.os.Bundle;
import android.os.RemoteException;
import android.view.View;
import android.widget.TextView;

import com.foo.aidldemo.R;
import com.foo.aidldemo.presenter.PlayPresenter;
import com.foo.aidldemo.view.PlayView;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements PlayView {

    @BindView(R.id.tv_status)
    TextView mStatusTv;

    private PlayPresenter mPresenter;

    @Override
    protected void initEventAndListener() {
        mPresenter = new PlayPresenter(this);
        mPresenter.create(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy(this);
    }

    @OnClick({R.id.btn_play, R.id.btn_pause, R.id.btn_stop, R.id.btn_previous, R.id.btn_next})
    void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.btn_play:
                    mPresenter.play();
                    break;
                case R.id.btn_pause:
                    mPresenter.pause();
                    break;
                case R.id.btn_stop:
                    mPresenter.stop();
                    break;
                case R.id.btn_previous:
                    mPresenter.previous();
                    break;
                case R.id.btn_next:
                    mPresenter.next();
                    break;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setStatus(String msg) {
        mStatusTv.setText(msg);
    }
}
