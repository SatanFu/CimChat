package com.satan.cimchat.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.TextHttpResponseHandler;
import com.satan.cimchat.R;
import com.satan.cimchat.app.CIMMonitorActivity;
import com.satan.cimchat.core.android.CIMPushManager;
import com.satan.cimchat.core.nio.constant.CIMConstant;
import com.satan.cimchat.core.nio.mutual.ReplyBody;
import com.satan.cimchat.model.ServerMessage;
import com.satan.cimchat.model.User;
import com.satan.cimchat.network.UserAPI;

import org.apache.http.Header;

public class LoginActivity extends CIMMonitorActivity implements
        OnClickListener {

    private EditText mAccount;
    private EditText mPassword;
    private Button mLogin;
    private Button mRegister;
    private Context mContext;
    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_login);
        mContext = this;
        initViews();
    }

    private void initViews() {

        mAccount = (EditText) this.findViewById(R.id.et_number);
        mPassword = (EditText) this.findViewById(R.id.et_password);
        mLogin = (Button) this.findViewById(R.id.btn_login);
        mRegister = (Button) this.findViewById(R.id.btn_register);
        mLogin.setOnClickListener(this);
        mRegister.setOnClickListener(this);

    }

    private void doLogin() {
        if (!"".equals(mAccount.getText().toString().trim()) && !"".equals(mPassword.getText().toString().trim())) {
            showProgressDialog("提示", "正在登陆，请稍后......");

            UserAPI.login(mAccount.getText().toString().trim(), mPassword.getText().toString().trim(), new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    hideProgressDialog();
                    Toast.makeText(mContext, "登录失败", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    ServerMessage msg = JSON.parseObject(responseString, ServerMessage.class);
                    if (msg.getStatus().equals("success")) {
                        user = JSON.parseObject(msg.getData(), User.class);
                        CIMPushManager.setAccount(LoginActivity.this, user.getAccount());
                    } else {
                        hideProgressDialog();
                        Toast.makeText(mContext, msg.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });

        }

    }

    private void doRegister() {
        Intent intent = new Intent(mContext, RegisterActivity.class);
        startActivity(intent);
    }


    @Override
    public void onReplyReceived(final ReplyBody reply) {
        if (reply.getKey().equals(CIMConstant.RequestKey.CLIENT_BIND)) {
            if (reply.getCode().equals(CIMConstant.ReturnCode.CODE_200)) {
                hideProgressDialog();
                SharedPreferences spf = getSharedPreferences("config", MODE_PRIVATE);
                SharedPreferences.Editor editor = spf.edit();
                editor.putString("account", user.getAccount());
                editor.putString("username", user.getUserName());
                editor.putInt("id", user.getId());
                editor.commit();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                this.finish();
            }
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_login:
                doLogin();
                break;

            case R.id.btn_register:
                doRegister();
                break;
        }

    }


    @Override
    public void onBackPressed() {

        CIMPushManager.destory(this);
        this.finish();
    }


}
