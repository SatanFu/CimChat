package com.satan.cimchat.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.satan.cimchat.R;

public class BaseToolBarActivity extends AppCompatActivity {

    Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolBar();
    }

    private void initToolBar() {
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolBar != null) {
            setSupportActionBar(mToolBar);
        }
    }

    public Toolbar getToolBar() {
        return mToolBar;
    }

    public void setTitleLeftIcon(int resId) {
        if (mToolBar != null) {
            mToolBar.setNavigationIcon(resId);
        }
    }

    public void setTitleText(CharSequence title) {
        if (mToolBar != null) {
            mToolBar.setTitle(title);
        }
    }

    public void setTitleLeftIconListener(View.OnClickListener listener) {
        if (mToolBar != null) {
            mToolBar.setNavigationOnClickListener(listener);
        }
    }

}
