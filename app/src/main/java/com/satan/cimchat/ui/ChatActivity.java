package com.satan.cimchat.ui;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.orhanobut.logger.Logger;
import com.satan.cimchat.R;
import com.satan.cimchat.adapter.ChatMsgListViewAdapter;
import com.satan.cimchat.app.BaseApplication;
import com.satan.cimchat.app.Constant;
import com.satan.cimchat.core.android.CIMListenerManager;
import com.satan.cimchat.core.android.CIMPushManager;
import com.satan.cimchat.core.android.OnCIMMessageListener;
import com.satan.cimchat.core.nio.mutual.Message;
import com.satan.cimchat.core.nio.mutual.ReplyBody;
import com.satan.cimchat.db.MessageDao;
import com.satan.cimchat.model.Contact;
import com.satan.cimchat.network.BaseAPI;
import com.satan.cimchat.util.ChangeUtil;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements OnCIMMessageListener {

    private Button send;
    private EditText msg;
    private ListView lv;
    protected ChatMsgListViewAdapter adapter;
    private ArrayList<Message> list;
    private String sender;
    //    private String receiver;
    private Message message;
    private Contact contact;
    private Toolbar mToolbar;
    private Context mContext;


    private HashMap<String, Object> apiParams = new HashMap<String, Object>();
    public final static String SEND_MESSAGE_API_URL = Constant.SERVER_URL
            + "/cgi/message_send.api";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CIMListenerManager.registerMessageListener(this, this);
        setContentView(R.layout.activity_chat);
        mContext = this;
        initTitle();
        initView();

        initData();
    }

    private void initTitle() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.icon_arrow);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void initView() {

        list = new ArrayList<Message>();


        send = (Button) findViewById(R.id.send);
        msg = (EditText) findViewById(R.id.msg);
        lv = (ListView) findViewById(R.id.lv);

        adapter = new ChatMsgListViewAdapter(this, list);
        lv.setAdapter(adapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = new Message();
                message.setContent(msg.getText().toString());
                message.setSender(sender);
                message.setReceiver(contact.getAccount());
                message.setType(Constant.MessageType.TYPE_0);
                try {
                    sendMessage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void initData() {
        sender = getSharedPreferences("config", MODE_PRIVATE).getString("account", "");
        contact = (Contact) getIntent().getSerializableExtra("receiver");
        setTitle(contact.getUsername());


        List<com.satan.cimchat.model.Message> messages = BaseApplication
                .getMessageDao(mContext)
                .queryBuilder()
                .whereOr(MessageDao.Properties.Sender.eq(contact.getAccount()),MessageDao.Properties.Sender.eq(sender)).list();
        for (com.satan.cimchat.model.Message msg : messages) {
            list.add(ChangeUtil.MyMsgToNioMsg(msg));
            Logger.e(msg.getContent() + "---" + msg.getSender() + "----" + msg.getReceiver());
        }
        adapter.notifyDataSetChanged();
    }


    private void sendMessage() throws Exception {

        RequestParams params = new RequestParams();
        params.put("content", msg.getText().toString());
        params.put("sender", sender);
        params.put("receiver", contact.getAccount());
        params.put("type", Constant.MessageType.TYPE_0);

        BaseAPI.postAsyncHttpResponse(SEND_MESSAGE_API_URL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.e("ChatActivity", responseString);
                BaseApplication.getMessageDao(mContext).insert(ChangeUtil.NioMsgToMyMsg(message));
                list.add(message);
                adapter.notifyDataSetChanged();
                lv.setSelection(lv.getTop());
                Log.e("TAG", list.toString());
                msg.setText("");
            }
        });


    }

    @Override
    public void onMessageReceived(Message message) {
        Log.e("ChatActivity", message.toString());
        if (message.getType().equals(Constant.MessageType.TYPE_999)) {
            CIMPushManager.stop(this);
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            this.finish();
        } else {
            MediaPlayer.create(this, R.raw.classic).start();
            list.add(message);
            adapter.notifyDataSetChanged();
            lv.setSelection(lv.getTop());

        }
    }

    @Override
    public void onReplyReceived(ReplyBody replybody) {

    }

    @Override
    public void onNetworkChanged(NetworkInfo info) {

    }

    @Override
    public void onConnectionStatus(boolean isConnected) {

    }

    @Override
    public void onConnectionSucceed() {

    }

    @Override
    public void onConnectionClosed() {

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        CIMListenerManager.registerMessageListener(this, this);
    }

    @Override
    public void finish() {
        super.finish();
        CIMListenerManager.removeMessageListener(this);
    }
}
