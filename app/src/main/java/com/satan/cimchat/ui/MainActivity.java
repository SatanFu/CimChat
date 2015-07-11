package com.satan.cimchat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.satan.cimchat.R;
import com.satan.cimchat.adapter.MainPageAdapter;
import com.satan.cimchat.base.BaseToolBarActivity;
import com.satan.cimchat.core.android.CIMPushManager;
import com.satan.cimchat.ui.fragment.ContactFragment;
import com.satan.cimchat.ui.fragment.ChatFragment;
import com.satan.cimchat.ui.fragment.ProfileFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tvTitle;
    private RadioGroup rgTab;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private TabLayout mTabLayout;
    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private MainPageAdapter mPageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTitle();
        initView();
        initListener();
    }

    private void initTitle() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("首页");
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

//        tvTitle = (TextView) findViewById(R.id.tv_title);
//        rgTab = (RadioGroup) findViewById(R.id.rg_tab);
//        tvTitle.setText(getSharedPreferences("config", MODE_PRIVATE).getString("username", ""));
//        switchContent(ChatFragment.newInstance());
    }


    private void setupViewPager() {
        mPageAdapter = new MainPageAdapter(getSupportFragmentManager(), getFragmentList(), new String[]{"聊天", "通讯录"});
        mViewPager.setAdapter(mPageAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setupDrawerContent() {
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
//        rgTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId) {
//                    case R.id.rb_chat:
//                        switchContent(ChatFragment.newInstance());
//                        break;
//                    case R.id.rb_contact:
//                        switchContent(ContactFragment.newInstance());
//                        break;
//                    case R.id.rb_profile:
//                        switchContent(ProfileFragment.newInstance());
//                        break;
//                }
//            }
//        });
    }


//    private void switchContent(Fragment fragment) {
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.replace(R.id.ll_content, fragment);
//        ft.commit();
//    }

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
