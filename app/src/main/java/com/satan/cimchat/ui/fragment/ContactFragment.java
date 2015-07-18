package com.satan.cimchat.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.TextHttpResponseHandler;
import com.orhanobut.logger.Logger;
import com.satan.cimchat.R;
import com.satan.cimchat.adapter.ContactAdapter;
import com.satan.cimchat.app.BaseApplication;
import com.satan.cimchat.db.ContactDao;
import com.satan.cimchat.model.Contact;
import com.satan.cimchat.model.ServerMessage;
import com.satan.cimchat.model.User;
import com.satan.cimchat.network.UserAPI;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ContactFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static ContactFragment mContactFragment;
    private RecyclerView rvContact;
    private Context mContext;
    private List<Contact> mContacts;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ContactAdapter mContactAdapter;

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
        mContacts = BaseApplication.getContactDao(mContext).loadAll();
        mContactAdapter = new ContactAdapter(mContext, mContacts);
        rvContact.setAdapter(mContactAdapter);
        getData();
    }


    private void getData() {
        SharedPreferences sp = mContext.getSharedPreferences("config", Context.MODE_PRIVATE);
        UserAPI.getFriend(sp.getLong("id", 0), new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Logger.e(responseString);
                ServerMessage msg = JSON.parseObject(responseString, ServerMessage.class);
                if ("success".equals(msg.getStatus())) {
                    List<Contact> contacts = JSON.parseArray(msg.getData(), Contact.class);
                    ContactDao contactDao = BaseApplication.getContactDao(mContext);
                    contactDao.deleteAll();
                    contactDao.insertInTx(contacts);

                    mContacts.clear();
                    mContacts.addAll(contacts);
                    mContactAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

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
        getData();
    }
}
