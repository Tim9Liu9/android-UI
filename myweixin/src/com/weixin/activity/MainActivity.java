package com.weixin.activity;

import java.util.ArrayList;

import android.app.ActivityGroup;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;

public class MainActivity extends ActivityGroup implements OnClickListener {
	/***
	 * 退出应用的广播标识
	 */
	public static final String ACTION_EXIT = "cn.itcast.weixin.exit";
	private static final String TAG = "MainActivity";
	
	LinearLayout tabBar;
	ImageView bottomImage;
	ViewPager pager;
	/**
	 * 接收到ACTION_EXIT的广播就退出activity
	 */
	BroadcastReceiver receiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			finish();
		}
	};
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        /**
         * 给底部工具栏绑定监听器
         */
        LinearLayout tabBar = (LinearLayout) findViewById(R.id.tab_bar);
        int count = tabBar.getChildCount();
        for (int index = 0; index < count; index++) {
			View view = tabBar.getChildAt(index);
			view.setTag(index);
			view.setOnClickListener(this);
			view.setEnabled(index!=0);
		}
        this.tabBar = tabBar;
        
        /**
         * 修改底部绿色ImageView的宽度
         */
        ImageView bottomImage = (ImageView) findViewById(R.id.bottom_image); 
        LayoutParams params = (LayoutParams) bottomImage.getLayoutParams();
        params.width = getWindowManager().getDefaultDisplay().getWidth()/count;
        bottomImage.setLayoutParams(params);
        this.bottomImage = bottomImage;
        
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new MainAdapter(this));
        pager.setOnPageChangeListener(new PageListener());
        this.pager = pager;
        
        /**
         * 注册广播
         */
        registerReceiver(receiver, new IntentFilter(ACTION_EXIT));
    }
    
    protected void onDestroy() {
    	super.onDestroy();
    	/**
    	 * 取消注册
    	 */
    	unregisterReceiver(receiver);
    }
    
    private class PageListener implements OnPageChangeListener {
		public void onPageScrollStateChanged(int arg0) {}
		public void onPageScrolled(int arg0, float arg1, int arg2) {}
		
		int currentItem;
		public void onPageSelected(int position) {
			// TODO 底部绿色ImageView的平移动画 currentItem->position
			TranslateAnimation animation = new TranslateAnimation(
					TranslateAnimation.RELATIVE_TO_SELF, currentItem, 
					TranslateAnimation.RELATIVE_TO_SELF, position, 
					TranslateAnimation.ABSOLUTE, 0, TranslateAnimation.ABSOLUTE, 0);
			animation.setFillAfter(true);
			animation.setDuration(100);
			bottomImage.startAnimation(animation);
			
			LinearLayout tabBar = MainActivity.this.tabBar;
			
			// 让当前的TextView变白色
			tabBar.getChildAt(currentItem).setEnabled(true);
			
			// 让新的TextView变绿色
			tabBar.getChildAt(position).setEnabled(false);
			
			currentItem = position;
		}
    }
    
    @SuppressWarnings("rawtypes")
    private class MainAdapter extends PagerAdapter 
    {
		Class[] cs = {WeixinActivity.class, AddressActivity.class, FriendsActivity.class, SettingsActivity.class};
		ArrayList<View> activityViews = new ArrayList<View>();
		
		public MainAdapter(Context context) {
			int length = cs.length;
			for (int i = 0; i < length; i++) {
				Class c = cs[i];
				// TODO 得到activity对应的View
				View view = getLocalActivityManager().startActivity(i +"", new Intent(context, c)).getDecorView();
				Log.i(TAG,"addView:i=" + i);
				activityViews.add(view);
			}
		}
		
		public int getCount() {
			return cs.length;
		}

		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		public Object instantiateItem(ViewGroup container, int position) {
			View view = activityViews.get(position);
			container.addView(view);
			return view;
		}
    }
    
    /**
     * 监听底部TabBar的点击
     */
    @Override
	public void onClick(View v) {
		int index = (Integer) v.getTag();
		// 会触发OnPageChangeListener的onPageSelected(index)
		pager.setCurrentItem(index);
	}
	
	/**
	 * ActivityGroup监听按键点击
	 */
	static final int DIALOG_EXIT_ASK = 1;
	public boolean dispatchKeyEvent(KeyEvent event) {
		int keyCode = event.getKeyCode();
		int action = event.getAction();
		
		/**
		 * 点击了Back键，并且手指已经离开按键
		 */
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& action == KeyEvent.ACTION_UP) {
			showDialog(DIALOG_EXIT_ASK);
			return true; // 覆盖父类的默认行为（父类默认是关闭activity）
		}
		return super.dispatchKeyEvent(event);
	}
	
	protected Dialog onCreateDialog(int id) {
		if (id == DIALOG_EXIT_ASK) {
			final Dialog dialog = new Dialog(this, R.style.ExitDialog);
			dialog.setContentView(R.layout.exit_dialog);
			
			View.OnClickListener listener = new OnClickListener() {
				public void onClick(View v) {
					dialog.dismiss();
					
					if (v.getId() == R.id.exit) {
						finish();
					}
				}
			};
			
			dialog.findViewById(R.id.exit).setOnClickListener(listener);
			dialog.findViewById(R.id.cancel).setOnClickListener(listener);
			return dialog;
		}
		return super.onCreateDialog(id);
	}
}