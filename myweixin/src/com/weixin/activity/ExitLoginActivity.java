package com.weixin.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ExitLoginActivity extends Activity {
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exit_login);
	}
	
	public void cancel(View v) {
		finish();
	}
	
	public void exit(View v) {
		finish();
		
		/**
		 * 发出退出应用的广播
		 */
		sendBroadcast(new Intent(MainActivity.ACTION_EXIT));
	}
}
