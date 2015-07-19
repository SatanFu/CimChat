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

import com.orhanobut.logger.Logger;
import com.satan.cimchat.R;
import com.satan.cimchat.app.BaseApplication;
import com.satan.cimchat.db.ChatDao;
import com.satan.cimchat.db.ContactDao;
import com.satan.cimchat.model.Chat;
import com.satan.cimchat.model.Contact;
import com.satan.cimchat.ui.ChatActivity;
import com.satan.cimchat.util.DateUtil;

import java.util.Date;
import java.util.List;

/**
 * 聊天适配器
 * Created by satan on 2015/7/12.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    private List<Chat> mRecentItems;
    private Context mContext;

    public ChatAdapter(Context context, List<Chat> recentItems) {
        mRecentItems = recentItems;
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder viewHolder = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.chat_item, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Logger.e(mRecentItems.get(position).getContent() + "----" + mRecentItems.get(position).getNewNum());
        holder.tvMsg.setText(mRecentItems.get(position).getContent());
        if (mRecentItems.get(position).getNewNum() == 0) {
            holder.tvMsgCount.setVisibility(View.GONE);
        } else {
            holder.tvMsgCount.setVisibility(View.VISIBLE);
            holder.tvMsgCount.setText(String.valueOf(mRecentItems.get(position).getNewNum()));
        }
        holder.tvTime.setText(DateUtil.formatHourWithMinute(new Date(mRecentItems.get(position).getTime())));
        Contact contact = BaseApplication.getContactDao(mContext).queryBuilder().where(ContactDao.Properties.Account.eq(mRecentItems.get(position).getSender())).unique();
        holder.tvUserName.setText(contact.getUsername());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ChatActivity.class);
                intent.putExtra("receiver", mRecentItems.get(position).getSender());
                mContext.startActivity(intent);
            }
        });
//        holder.ivHead.setImageResource(RecentItem.getHeads()[mRecentItems.get(position).getHeadImg()]);
    }


    @Override
    public int getItemCount() {
        return mRecentItems.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvUserName;
        private TextView tvMsg;
        private TextView tvMsgCount;
        private TextView tvTime;
        private ImageView ivHead;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvMsg = (TextView) itemView.findViewById(R.id.tv_msg);
            tvUserName = (TextView) itemView.findViewById(R.id.tv_username);
            tvMsgCount = (TextView) itemView.findViewById(R.id.tv_msg_count);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            ivHead = (ImageView) itemView.findViewById(R.id.iv_head);

        }
    }

}
