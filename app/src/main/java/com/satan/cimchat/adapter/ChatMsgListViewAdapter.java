package com.satan.cimchat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.satan.cimchat.R;
import com.satan.cimchat.core.nio.mutual.Message;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class ChatMsgListViewAdapter extends BaseAdapter {

    protected List<Message> list;
    protected Context scactivity;

    public ChatMsgListViewAdapter(Context context, List<Message> list) {
        super();
        this.scactivity = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Message getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View chatItemView, ViewGroup parent) {
        String myAccount = scactivity.getSharedPreferences("config", Context.MODE_PRIVATE).getString("account", "");

        Message msg = list.get(position);

        if (msg.getSender().equals(myAccount)) {

            chatItemView = LayoutInflater.from(scactivity).inflate(R.layout.chat_item_right, null);

        } else {
            chatItemView = LayoutInflater.from(scactivity).inflate(R.layout.chat_item_left, null);
        }

        ((TextView) chatItemView.findViewById(R.id.content)).setText(msg.getContent());


        return chatItemView;
    }

    public List<Message> getList() {
        return list;
    }

    public void setList(List<Message> list) {
        this.list = list;
    }


}
