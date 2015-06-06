package com.satan.cimchat.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
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
import com.satan.cimchat.util.DialogUtil;

import org.apache.http.Header;

public class RegisterActivity extends Activity implements
        OnClickListener {

    private EditText mPassword;
    private EditText mUserName;
    private Button mRegister;
    private Context mContext;
    private User mUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mContext = this;
        initViews();
    }

    private void initViews() {

        mPassword = (EditText) this.findViewById(R.id.et_password);
        mRegister = (Button) this.findViewById(R.id.btn_register);
        mUserName = (EditText) this.findViewById(R.id.et_nickname);
        mRegister.setOnClickListener(this);

    }

    private void register() {
        if (!"".equals(mUserName.getText().toString().trim()) && !"".equals(mPassword.getText().toString().trim())) {

            UserAPI.register(mUserName.getText().toString().trim(), mPassword.getText().toString().trim(), new TextHttpResponseHandler() {

                @Override
                public void onStart() {
                    DialogUtil.showProgressDialog(mContext, "正在提交数据...");
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.e("RegisterActivity", "statusCode:" + statusCode);
                    Toast.makeText(mContext, "注册失败", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    ServerMessage msg = JSON.parseObject(responseString, ServerMessage.class);
                    if (msg.getStatus().equals("success")) {
                        mUser = JSON.parseObject(msg.getData(), User.class);
                        new MaterialDialog.Builder(mContext).titleColorRes(R.color.blue).title(msg.getMessage()).backgroundColor(Color.WHITE).content("您的CIM号为:" + mUser.getAccount() + "，请记住此号码，登录时需要用到").positiveText("登录").negativeText("取消").callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
//                                Intent intent = new Intent(mContext, LoginActivity.class);
//                                startActivity(intent);
                                dialog.dismiss();
                                finish();
                            }

                            @Override
                            public void onNegative(MaterialDialog dialog) {
                                dialog.dismiss();
                            }
                        }).show();
                    }
                }

                @Override
                public void onFinish() {
                    DialogUtil.dismissProgressDialog();
                }
            });

        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                register();
                break;
        }

    }


}
