package com.satan.cimchat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.satan.cimchat.R;
import com.satan.cimchat.model.User;

import java.util.List;

/**
 * Created by Administrator on 2015/5/28.
 */
public class MyBaseAdapter extends BaseAdapter {

    private Context mContext;
    private List<User> users;

    public MyBaseAdapter(Context context, List<User> users) {
        mContext = context;
        this.users = users;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.my_friends_item, parent, false);
        TextView username = (TextView) convertView.findViewById(R.id.tv_username);
        username.setText(users.get(position).getUserName());
        return convertView;
    }
}
