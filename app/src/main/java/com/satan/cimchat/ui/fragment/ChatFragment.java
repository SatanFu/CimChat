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
import android.widget.Toast;

import com.satan.cimchat.R;
import com.satan.cimchat.adapter.ChatAdapter;
import com.satan.cimchat.model.RecentItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ChatFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static ChatFragment mChatFragment;
    private RecyclerView rvChats;
    private Context mContext;
    private List<RecentItem> mRecentItems;
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

    public static ChatFragment newInstance() {
        if (mChatFragment == null) {
            mChatFragment = new ChatFragment();
        }
        return mChatFragment;
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
        mRecentItems = new ArrayList<RecentItem>();
        for (int i = 0; i < 30; i++) {
            RecentItem recentItem = new RecentItem();
            recentItem.setUserId("000" + i);
            recentItem.setHeadImg(i % 19);
            recentItem.setMessage("消息。。。。" + i);
            recentItem.setName("000" + i);
            recentItem.setTime(System.currentTimeMillis());
            recentItem.setNewNum(i);
            mRecentItems.add(recentItem);
        }
        rvChats.setAdapter(new ChatAdapter(mContext, mRecentItems));
//        SharedPreferences preferences = mContext.getSharedPreferences("config", Context.MODE_PRIVATE);
//        UserAPI.getFriend(preferences.getInt("id", -1), new TextHttpResponseHandler() {
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
//                    lvMyFriends.setAdapter(new MyBaseAdapter(mContext, userOlds, "my"));
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

        rvChats = (RecyclerView) view.findViewById(R.id.rv_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        rvChats.setLayoutManager(layoutManager);

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
