package com.weixin.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FriendsActivity extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friends);
		
		TextView titleView = (TextView) findViewById(R.id.title);
		titleView.setText(R.string.friends);
	}
	
	public void shake(View v) {
		Intent intent = new Intent(this, ShakeActivity.class);
		startActivity(intent);
	}
}
