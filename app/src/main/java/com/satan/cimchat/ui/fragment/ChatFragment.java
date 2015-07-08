package com.satan.cimchat.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.TextHttpResponseHandler;
import com.satan.cimchat.R;
import com.satan.cimchat.adapter.MyBaseAdapter;
import com.satan.cimchat.model.ServerMessage;
import com.satan.cimchat.model.User;
import com.satan.cimchat.network.UserAPI;
import com.satan.cimchat.ui.ChatActivity;

import org.apache.http.Header;

import java.util.List;


public class ChatFragment extends Fragment {

    private static ChatFragment mChatFragment;
    private ListView lvMyFriends;
    private Context mContext;
    private List<User> users;

    public static ChatFragment newInstance() {
        if (mChatFragment == null) {
            mChatFragment = new ChatFragment();
        }
        return mChatFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        mContext = getActivity();
        initView(view);
        initListener();
        initData();
        return view;
    }

    private void initData() {
        SharedPreferences preferences = mContext.getSharedPreferences("config", Context.MODE_PRIVATE);
        UserAPI.getFriend(preferences.getInt("id", -1), new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                ServerMessage msg = JSON.parseObject(responseString, ServerMessage.class);
                if (msg.getStatus().equals("success")) {
                    users = JSON.parseArray(msg.getData(), User.class);
                    lvMyFriends.setAdapter(new MyBaseAdapter(mContext, users, "my"));
                }
            }
        });
    }


    private void initView(View view) {
        lvMyFriends = (ListView) view.findViewById(R.id.lv_my_friends);

    }

    private void initListener() {
        lvMyFriends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, ChatActivity.class);
                intent.putExtra("receiver", users.get(position));
                mContext.startActivity(intent);
            }
        });
    }


}
