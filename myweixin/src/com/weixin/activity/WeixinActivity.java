package com.weixin.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class WeixinActivity extends Activity {
	RelativeLayout top;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weixin);
		
		TextView titleView = (TextView) findViewById(R.id.title);
		titleView.setText(R.string.weixin);
		
		top = (RelativeLayout) findViewById(R.id.top);
		Log.i("WeixinActivity","WeixinActivity-->onCreate");
	}
	
	
	
	@Override
	protected void onResume() {
		Log.i("WeixinActivity","WeixinActivity-->onResume");
		super.onResume();
	}



	/**
	 * 点击了RelativeLayout
	 */
	public void chat(View v) {
		Intent intent = new Intent(this, ChatActivity.class);
		startActivity(intent);
	}
	
	PopupWindow rightTopWindow;
	/**
	 * 右上角按钮
	 */
	public void rightTop(View v) {
		if (rightTopWindow == null) {
			/**
			 * 实例化布局文件(窗口里面要显示的内容)
			 */
			View contentView = getLayoutInflater().inflate(R.layout.right_top, null);
			/**
			 * 初始化窗口
			 */
			PopupWindow rightTopWindow = new PopupWindow(contentView, 
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			/**
			 * 点击窗口外部  就  关闭窗口
			 */
			rightTopWindow.setFocusable(true);
			rightTopWindow.setBackgroundDrawable(new BitmapDrawable());
			rightTopWindow.update();
			
			this.rightTopWindow = rightTopWindow;
		}
		
		rightTopWindow.showAsDropDown(v, 0, -10);
	}
}
