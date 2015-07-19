package com.satan.cimchat.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.satan.cimchat.R;
import com.satan.cimchat.model.Contact;
import com.satan.cimchat.model.User;
import com.satan.cimchat.ui.ChatActivity;

import java.util.List;

/**
 * 通讯录适配器
 * Created by satan on 2015/7/12.
 */
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {

    private List<Contact> mContact;
    private Context mContext;

    public ContactAdapter(Context context, List<Contact> contact) {
        mContact = contact;
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder viewHolder = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.contact_item, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tvUserName.setText(mContact.get(position).getUsername());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ChatActivity.class);
                intent.putExtra("receiver", mContact.get(position).getAccount());
                mContext.startActivity(intent);
            }
        });
//        holder.ivHead.setImageResource(RecentItem.getHeads()[mUsers.get(position).getHeadIcon()]);
    }


    @Override
    public int getItemCount() {
        return mContact.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvUserName;
        private ImageView ivHead;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvUserName = (TextView) itemView.findViewById(R.id.tv_username);
            ivHead = (ImageView) itemView.findViewById(R.id.iv_head);

        }
    }

}
