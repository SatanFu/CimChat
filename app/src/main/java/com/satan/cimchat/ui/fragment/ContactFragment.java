package com.satan.cimchat.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.satan.cimchat.R;
import com.satan.cimchat.adapter.ContactAdapter;
import com.satan.cimchat.adapter.MyBaseAdapter;
import com.satan.cimchat.model.RecentItem;
import com.satan.cimchat.model.User;
import com.satan.cimchat.model.UserOld;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ContactFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static ContactFragment mContactFragment;
    private RecyclerView rvContact;
    private Context mContext;
    private List<User> mUsers;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 100) {
                mSwipeRefreshLayout.setRefreshing(false);
                Toast.makeText(mContext, "刷新完成", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public static ContactFragment newInstance() {
        if (mContactFragment == null) {
            mContactFragment = new ContactFragment();
        }
        return mContactFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view_swipe_refresh_layout, container, false);
        mContext = getActivity();
        initView(view);
        initListener();
        initData();
        return view;
    }

    private void initData() {
        mUsers = new ArrayList<User>();
        for (int i = 0; i < 30; i++) {
            User user = new User();
            user.setHeadIcon(i % 19);
            user.setNick("000" + i);
            user.setUserId("000" + i);

            mUsers.add(user);
        }

        rvContact.setAdapter(new ContactAdapter(mContext, mUsers));

//        UserAPI.getAllUser(new TextHttpResponseHandler() {
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, String responseString) {
//                ServerMessage msg = JSON.parseObject(responseString, ServerMessage.class);
//                if (msg.getStatus().equals("success")) {
//                    userOlds = JSON.parseArray(msg.getData(), UserOld.class);
//                    lvAllUser.setAdapter(new MyBaseAdapter(mContext, userOlds, "all"));
//                }
//            }
//        });
    }


    private void initView(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_view);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        rvContact = (RecyclerView) view.findViewById(R.id.rv_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        rvContact.setLayoutManager(layoutManager);
    }

    private void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep((new Random().nextInt(10) + 1) * 1000);
                    mHandler.sendEmptyMessage(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
