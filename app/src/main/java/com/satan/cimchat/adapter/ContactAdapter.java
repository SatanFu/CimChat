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
import com.satan.cimchat.model.User;

import java.util.List;

/**
 * 通讯录适配器
 * Created by satan on 2015/7/12.
 */
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {

    private List<User> mUsers;
    private Context mContext;

    public ContactAdapter(Context context, List<User> users) {
        mUsers = users;
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder viewHolder = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.contact_item, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvUserName.setText(mUsers.get(position).getUsername());
//        holder.ivHead.setImageResource(RecentItem.getHeads()[mUsers.get(position).getHeadIcon()]);
    }


    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvUserName;
        private ImageView ivHead;

        public MyViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "onItemClick", Toast.LENGTH_SHORT).show();
                }
            });

            tvUserName = (TextView) itemView.findViewById(R.id.tv_username);
            ivHead = (ImageView) itemView.findViewById(R.id.iv_head);

        }
    }

}
