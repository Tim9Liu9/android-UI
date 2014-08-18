package com.weixin.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AddressActivity extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.address);
		
		TextView titleView = (TextView) findViewById(R.id.title);
		titleView.setText(R.string.address);
	}
}
