package com.foo.aidldemo.ui;

import android.view.View;
import android.widget.TextView;

import com.foo.aidldemo.R;
import com.foo.aidldemo.base.BaseActivity;
import com.foo.aidldemo.presenter.PlayPresenter;
import com.foo.aidldemo.utils.ToastUtil;
import com.foo.aidldemo.view.PlayView;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements PlayView {

    @BindView(R.id.tv_status)
    TextView mStatusTv;
    @BindView(R.id.tv_play_process)
    TextView mPlayProcessTv;
    @BindView(R.id.tv_buffer_process)
    TextView mBufferProcessTv;

    private PlayPresenter mPresenter;

    @Override
    protected void init() {
        mPresenter = new PlayPresenter(this);
        mPresenter.create();

        mPresenter.loadData(0);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @OnClick({R.id.btn_play, R.id.btn_pause, R.id.btn_resume, R.id.btn_stop, R.id.btn_previous, R.id.btn_next})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_play:
                mPresenter.play(-1);
                break;
            case R.id.btn_pause:
                mPresenter.pause();
                break;
            case R.id.btn_resume:
                mPresenter.resume();
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
    }

    @Override
    public void setStatus(String msg) {
        mStatusTv.setText(msg);
    }

    @Override
    public void setPlayProcess(String process) {
        mPlayProcessTv.setText(process);
    }

    @Override
    public void setBufferProcess(String process) {
        mBufferProcessTv.setText(process);
    }

    @Override
    public void showT(String msg) {
        ToastUtil.showToast(msg);
    }
}
