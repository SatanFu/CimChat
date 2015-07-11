package com.satan.cimchat.ui.fragment;

import android.content.Context;
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

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;


public class ContactFragment extends Fragment {

    private static ContactFragment mContactFragment;
    private ListView lvAllUser;
    private Context mContext;
    private List<User> users;

    public static ContactFragment newInstance() {
        if (mContactFragment == null) {
            mContactFragment = new ContactFragment();
        }
        return mContactFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all, container, false);
        mContext = getActivity();
        initView(view);
        initListener();
        initData();
        return view;
    }

    private void initData() {
        users = new ArrayList<User>();
        for (int i = 0; i < 30; i++) {
            User user = new User();
            user.setAccount("000" + i);
            user.setId(i);
            user.setUserName("000" + i);
            user.setPassword("123");
            users.add(user);
        }
        lvAllUser.setAdapter(new MyBaseAdapter(mContext, users, "all"));

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
//                    users = JSON.parseArray(msg.getData(), User.class);
//                    lvAllUser.setAdapter(new MyBaseAdapter(mContext, users, "all"));
//                }
//            }
//        });
    }


    private void initView(View view) {
        lvAllUser = (ListView) view.findViewById(R.id.lv_all_user);
    }

    private void initListener() {
        lvAllUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

}
