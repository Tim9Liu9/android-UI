package com.weixin.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;

public class ShakeActivity extends Activity {
	View shakeUp,shakeDown;
	View shakeTitleBar;
	SlidingDrawer drawer;
	Button handle;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shake);
		
		shakeUp = findViewById(R.id.shake_up);
		shakeDown = findViewById(R.id.shake_down);
		
		shakeTitleBar = findViewById(R.id.shake_title_bar);
		handle = (Button) findViewById(R.id.handle);
		
		drawer = (SlidingDrawer) findViewById(R.id.drawer);
		drawer.setOnDrawerCloseListener(new OnDrawerCloseListener() {
			public void onDrawerClosed() {
				// 箭头向上
				handle.setBackgroundResource(R.drawable.shake_report_dragger_up);
				
				// 标题栏向下走
				TranslateAnimation up = new TranslateAnimation(
						Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0,
						Animation.RELATIVE_TO_SELF, -1f, Animation.ABSOLUTE, 0);
				up.setDuration(200);
				up.setFillAfter(true);
				shakeTitleBar.startAnimation(up);
			}
		});
		drawer.setOnDrawerOpenListener(new OnDrawerOpenListener() {
			public void onDrawerOpened() {
				// 箭头向下
				handle.setBackgroundResource(R.drawable.shake_report_dragger_down);
				
				// 标题栏向上走
				TranslateAnimation up = new TranslateAnimation(
						Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0,
						Animation.ABSOLUTE, 0, Animation.RELATIVE_TO_SELF, -1f);
				up.setDuration(200);
				up.setFillAfter(true);
				shakeTitleBar.startAnimation(up);
			}
		});
	}
	
	public void back(View v) {
		finish();
	}
	
	public void start(View v) {
		final int duration = 1000;
		/**
		 * 上边的RelativeLayout
		 */
		TranslateAnimation shakeUpUp = new TranslateAnimation(
				Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0, 
				Animation.ABSOLUTE, 0, 
				Animation.RELATIVE_TO_SELF, -0.5f);
		shakeUpUp.setDuration(duration);
		shakeUpUp.setAnimationListener(new AnimationListener() {
			public void onAnimationStart(Animation animation) {}
			public void onAnimationRepeat(Animation animation) {}
			public void onAnimationEnd(Animation animation) {
				TranslateAnimation shakeUpDown = new TranslateAnimation(
						Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0, 
						Animation.RELATIVE_TO_SELF, -0.5f, 
						Animation.ABSOLUTE, 0);
				shakeUpDown.setDuration(duration);
				shakeUp.startAnimation(shakeUpDown);
			}
		});
		shakeUp.startAnimation(shakeUpUp);
		/**
		 * 下边的RelativeLayout
		 */
		TranslateAnimation shakeDownDown = new TranslateAnimation(
				Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0, 
				Animation.ABSOLUTE, 0, 
				Animation.RELATIVE_TO_SELF, 0.5f);
		shakeDownDown.setDuration(duration);
		shakeDownDown.setAnimationListener(new AnimationListener() {
			public void onAnimationStart(Animation animation) {}
			public void onAnimationRepeat(Animation animation) {}
			public void onAnimationEnd(Animation animation) {
				TranslateAnimation shakeDownUp = new TranslateAnimation(
						Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0, 
						Animation.RELATIVE_TO_SELF, 0.5f, 
						Animation.ABSOLUTE, 0);
				shakeDownUp.setDuration(duration);
				shakeDown.startAnimation(shakeDownUp);
			}
		});
		shakeDown.startAnimation(shakeDownDown);
	}
}
