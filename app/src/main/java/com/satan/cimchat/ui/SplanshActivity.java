package com.satan.cimchat.ui;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;

import com.satan.cimchat.R;
import com.satan.cimchat.app.CIMMonitorActivity;
import com.satan.cimchat.app.Constant;
import com.satan.cimchat.core.android.CIMPushManager;

public class SplanshActivity extends CIMMonitorActivity {

	boolean initComplete = false;
	public void onCreate(Bundle savedInstanceState)
	{


		super.onCreate(savedInstanceState);

		//连接服务端

		CIMPushManager.init(SplanshActivity.this, Constant.CIM_SERVER_HOST, Constant.CIM_SERVER_PORT);


		final View view = View.inflate(this, R.layout.activity_splansh, null);
		setContentView(view);
		AlphaAnimation aa = new AlphaAnimation(0.3f,1.0f);
		aa.setDuration(2000);
		view.startAnimation(aa);


	}
	@Override
	public void onConnectionSucceed() {

		Intent intent = new Intent(SplanshActivity.this,LoginActivity.class);
		startActivity(intent);
		finish();
	}

	 @Override
		public void onBackPressed() {
		  finish();
		  CIMPushManager.destory(this);
		}
}
