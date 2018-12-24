package com.example.baseapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


public abstract class BaseActivity extends AppCompatActivity {
    private Toast mToast;
    private final int LAYOUT_ID = 1;

    @SuppressLint("ResourceType")
    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //设置无标题

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  //设置全屏
        onCreateViewBefore(savedInstanceState);

        if (getContentViewLayoutId() < LAYOUT_ID) {
            throw new IllegalStateException("activity content ID not use");
        }
        setContentView(getContentViewLayoutId());
        bindView();
        onViewCreated(savedInstanceState);
        onCreateViewCompleted();
    }

    @LayoutRes
    protected abstract int getContentViewLayoutId();

    /**
     * 在setContentView之前
     *
     * @param savedInstanceState
     */
    protected void onCreateViewBefore(Bundle savedInstanceState) {
    }

    /**
     * 在setContentView之后
     */
    protected abstract void onViewCreated(Bundle savedInstanceState);

    /**
     * 在onViewCreate之后
     */
    protected void onCreateViewCompleted() {

    }

    protected void bindView() {

    }


    /**
     * 显示Toast
     *
     * @param res String资源
     */
    protected void showToast(@StringRes int res) {
        if (mToast == null) {
            mToast = Toast.makeText(this, res, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(res);
        }
        mToast.show();
    }

    /**
     * 显示Toast
     *
     * @param text 文本
     */
    protected void showToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
        }
        mToast.show();
    }


}
