package com.satan.cimchat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.satan.cimchat.R;
import com.satan.cimchat.model.Chat;
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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvMsg.setText(mRecentItems.get(position).getContent());
        holder.tvMsgCount.setText(String.valueOf(mRecentItems.get(position).getNewNum()));
        holder.tvTime.setText(DateUtil.formatHourWithMinute(new Date(mRecentItems.get(position).getTime())));
        holder.tvUserName.setText(mRecentItems.get(position).getSender());
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "onItemClick", Toast.LENGTH_SHORT).show();
                }
            });

            tvMsg = (TextView) itemView.findViewById(R.id.tv_msg);
            tvUserName = (TextView) itemView.findViewById(R.id.tv_username);
            tvMsgCount = (TextView) itemView.findViewById(R.id.tv_msg_count);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            ivHead = (ImageView) itemView.findViewById(R.id.iv_head);

        }
    }

}
