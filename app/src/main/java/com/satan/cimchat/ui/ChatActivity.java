package com.satan.cimchat.ui;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.satan.cimchat.R;
import com.satan.cimchat.adapter.ChatMsgListViewAdapter;
import com.satan.cimchat.app.Constant;
import com.satan.cimchat.core.android.CIMListenerManager;
import com.satan.cimchat.core.android.CIMPushManager;
import com.satan.cimchat.core.android.OnCIMMessageListener;
import com.satan.cimchat.core.nio.mutual.Message;
import com.satan.cimchat.core.nio.mutual.ReplyBody;
import com.satan.cimchat.model.User;
import com.satan.cimchat.network.BaseAPI;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatActivity extends Activity implements OnCIMMessageListener {

    private Button send;
    private TextView title;
    private EditText msg;
    private ListView lv;
    protected ChatMsgListViewAdapter adapter;
    private ArrayList<Message> list;
    private String sender;
    //    private String receiver;
    private Message message;
    private User user;


    private HashMap<String, Object> apiParams = new HashMap<String, Object>();
    public final static String SEND_MESSAGE_API_URL = Constant.SERVER_URL
            + "/cgi/message_send.api";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CIMListenerManager.registerMessageListener(this, this);
        setContentView(R.layout.activity_chat);
        initialize();

        initData();
    }


    private void initialize() {

        list = new ArrayList<Message>();


        send = (Button) findViewById(R.id.send);
        title = (TextView) findViewById(R.id.title);
        msg = (EditText) findViewById(R.id.msg);
        lv = (ListView) findViewById(R.id.lv);

        adapter = new ChatMsgListViewAdapter(this, list);
        lv.setAdapter(adapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("ChatActivity", "send:" + sender + " content:" + msg.getText().toString() + " receiver:" + receiver + " type:" + Constant.MessageType.TYPE_0);
//                apiParams.put("content", msg.getText().toString());
//                apiParams.put("sender", sender);
//                apiParams.put("receiver", receiver);
//                apiParams.put("type", Constant.MessageType.TYPE_0);
                message = new Message();
                message.setContent(msg.getText().toString());
                message.setSender(sender);
                message.setReceiver(user.getAccount());
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
//        receiver = getIntent().getStringExtra("receiver");
        user = (User) getIntent().getSerializableExtra("receiver");
        title.setText(user.getUsername());


    }


    private void sendMessage() throws Exception {

        RequestParams params = new RequestParams();
        params.put("content", msg.getText().toString());
        params.put("sender", sender);
        params.put("receiver", user.getAccount());
        params.put("type", Constant.MessageType.TYPE_0);

        Log.d("ChatActivity", params.toString());
        BaseAPI.postAsyncHttpResponse(SEND_MESSAGE_API_URL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.e("ChatActivity", responseString);

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
