package com.satan.cimchat.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.TextHttpResponseHandler;
import com.satan.cimchat.R;
import com.satan.cimchat.app.Constant;
import com.satan.cimchat.core.android.CIMPushManager;
import com.satan.cimchat.network.BaseAPI;

import org.apache.http.Header;

import java.util.List;
import java.util.Map;

public class MyFriendsActivity extends Activity {

    private ListView lv;
    public final static String SEND_MESSAGE_API_URL = Constant.SERVER_URL
            + "/cgi/friend_getAll.api";
    private String sender;
    private List<String> accounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friends);
        initialize();
    }


    private void initialize() {

        lv = (ListView) findViewById(R.id.lv);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MyFriendsActivity.this, ChatActivity.class);
                intent.putExtra("receiver", accounts.get(position));
                startActivity(intent);
            }
        });
    }


//    @Override
//    public void onSuccess(Object data, List<?> list, Page page, String code, String url) {
//        String json = (String) data;
//        Log.d("MyFriendsActivity", json + "-------");
//        accounts = JSON.parseArray(json, String.class);
//        lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, accounts));
//    }

    @Override
    protected void onResume() {
        super.onResume();
        BaseAPI.postAsyncHttpResponse(SEND_MESSAGE_API_URL, null, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("MyFriendsActivity", "statusCode:" + statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.e("MyFriendsActivity", responseString);
                accounts = JSON.parseArray(responseString, String.class);
                lv.setAdapter(new ArrayAdapter<String>(MyFriendsActivity.this, android.R.layout.simple_list_item_1, accounts));
            }
        });
    }


    @Override
    public void onBackPressed() {

        CIMPushManager.stop(this);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }
}
