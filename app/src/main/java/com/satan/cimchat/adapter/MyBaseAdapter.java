package com.satan.cimchat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.TextHttpResponseHandler;
import com.satan.cimchat.R;
import com.satan.cimchat.model.ServerMessage;
import com.satan.cimchat.model.User;
import com.satan.cimchat.network.UserAPI;
import com.satan.cimchat.util.DialogUtil;

import org.apache.http.Header;

import java.util.List;

/**
 * Created by Administrator on 2015/5/28.
 */
public class MyBaseAdapter extends BaseAdapter {

    private Context mContext;
    private List<User> mUsers;
    private String mType;

    public MyBaseAdapter(Context context, List<User> users, String type) {
        mContext = context;
        mUsers = users;
        mType = type;
    }

    @Override
    public int getCount() {
        return mUsers.size();
    }

    @Override
    public Object getItem(int position) {
        return mUsers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.my_friends_item, parent, false);
        TextView username = (TextView) convertView.findViewById(R.id.tv_username);
        final Button addFriend = (Button) convertView.findViewById(R.id.btn_add_friend);
        TextView msgCount = (TextView) convertView.findViewById(R.id.tv_msg_count);
        if (mType.equals("all")) {
            addFriend.setVisibility(View.VISIBLE);
        } else {
            addFriend.setVisibility(View.GONE);
        }
        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFriend(mContext.getSharedPreferences("config", Context.MODE_PRIVATE).getInt("id", 0), mUsers.get(position).getId());
            }
        });
        username.setText(mUsers.get(position).getUserName());
        return convertView;
    }


    private void addFriend(int userId, int friendId) {
        UserAPI.addFriend(userId, friendId, new TextHttpResponseHandler() {

            @Override
            public void onStart() {
                DialogUtil.showProgressDialog(mContext, "正在提交数据...");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(mContext, "添加好友失败", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                ServerMessage msg = JSON.parseObject(responseString, ServerMessage.class);
                Toast.makeText(mContext, msg.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinish() {
                DialogUtil.dismissProgressDialog();
            }
        });
    }
}
