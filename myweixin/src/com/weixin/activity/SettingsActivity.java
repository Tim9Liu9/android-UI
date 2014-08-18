package com.weixin.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SettingsActivity extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		
		TextView titleView = (TextView) findViewById(R.id.title);
		titleView.setText(R.string.setting);
	}
	
	/**
	 * 退出
	 */
	public void exit(View v) {
		Intent intent = new Intent(this, ExitLoginActivity.class);
		startActivity(intent);
	}
}
