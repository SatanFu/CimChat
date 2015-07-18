package com.satan.cimchat.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.TextHttpResponseHandler;
import com.orhanobut.logger.Logger;
import com.satan.cimchat.R;
import com.satan.cimchat.adapter.MainPageAdapter;
import com.satan.cimchat.app.BaseApplication;
import com.satan.cimchat.core.android.CIMPushManager;
import com.satan.cimchat.db.ContactDao;
import com.satan.cimchat.model.Contact;
import com.satan.cimchat.model.ServerMessage;
import com.satan.cimchat.model.User;
import com.satan.cimchat.network.UserAPI;
import com.satan.cimchat.ui.fragment.ChatFragment;
import com.satan.cimchat.ui.fragment.ContactFragment;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private TabLayout mTabLayout;
    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private MainPageAdapter mPageAdapter;
    private Context mContext;
    private TextView tvUserName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        initTitle();
        initView();
        initListener();
        initData();
    }

    private void initData() {


    }

    private void initTitle() {
        setTitle("首页");
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_menu);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.LEFT);

            }
        });

    }

    private void initView() {

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_view);
        setupDrawerContent();
        setupViewPager();
    }


    private void setupViewPager() {
        mPageAdapter = new MainPageAdapter(getSupportFragmentManager(), getFragmentList(), new String[]{"聊天", "通讯录"});
        mViewPager.setAdapter(mPageAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setupDrawerContent() {
        tvUserName = (TextView) mNavigationView.findViewById(R.id.tv_username);
        SharedPreferences sp = mContext.getSharedPreferences("config", Context.MODE_PRIVATE);
        String username = sp.getString("username", "");
        tvUserName.setText(username);
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        mDrawerLayout.closeDrawers();
                        switch (menuItem.getItemId()) {
                            case R.id.nav_setting:

                                break;
                            case R.id.nav_sign_out:

                                break;
                        }
                        return true;
                    }
                });
    }

    private void initListener() {
    }


    private List<Fragment> getFragmentList() {

        List<Fragment> pagerFragmentList = new ArrayList<Fragment>();
        pagerFragmentList.add(ChatFragment.newInstance());
        pagerFragmentList.add(ContactFragment.newInstance());
        return pagerFragmentList;

    }


    @Override
    public void onBackPressed() {

        CIMPushManager.stop(this);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }

}
