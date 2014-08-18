package com.weixin.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.weixin.model.ChatMessage;

public class ChatActivity extends ListActivity {
	
	private String[] msgArray = new String[]{"哥，问你个问题", "说吧", "为什么两点之间线段最短？", "你养过狗么？", 
			"曾经养过", "你丢一块骨头出去，你说狗是绕个圈去捡还是直接跑过去捡呢？”",
			"当然是直接跑过去捡了！", "连狗都知道的问题你还问？"};

	private String[] dateArray = new String[]{"2012-10-01 18:00", "2012-10-01 18:10", 
			"2012-10-01 18:11", "2012-10-01 18:20", 
			"2012-10-01 18:30", "2012-10-01 18:35", 
			"2012-10-01 18:40", "2012-10-01 18:50"}; 
	
	ArrayList<ChatMessage> messages = new ArrayList<ChatMessage>();
	EditText contentView;
	ChatAdapter adapter;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat);
		
		TextView titleView = (TextView) findViewById(R.id.title);
		titleView.setText("2B");
		
		/**
		 * 初始化数据
		 */
		int count = msgArray.length;
		for (int i = 0; i < count; i++) {
			ChatMessage msg = new ChatMessage();
			msg.message = msgArray[i]; // 消息内容
			msg.sendTime = dateArray[i]; // 发送时间
			if (i%2==0) { // 2B
				msg.name = "2B";
				msg.isFromMessage = true;
			} else {
				msg.name = "itcast";
				msg.isFromMessage = false;
			}
			
			messages.add(msg);
		}
		
		setListAdapter(adapter = new ChatAdapter());
		
		contentView = (EditText) findViewById(R.id.content);
	}
	
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 发送
	 */
	public void send(View v) {
		String text = contentView.getText().toString();
		if (!"".equals(text)) {
			ChatMessage msg = new ChatMessage();
			msg.isFromMessage = false;
			msg.message = text;
			msg.sendTime = format.format(new Date());
			msg.name = "itcast";
			messages.add(msg);
			
			adapter.notifyDataSetChanged();
			
			// 清空文本框的内容
			contentView.setText(null);
			
			getListView().setSelection(messages.size()-1);
		}
	}
	
	public void back(View v) {
		finish();
	}
	
	private class ChatAdapter extends BaseAdapter {

		public int getCount() {
			return messages.size();
		}

		public Object getItem(int position) {
			return messages.get(position);
		}

		public long getItemId(int position) {
			return position;
		}
		
		/**
		 * 有2中不同类型的Item
		 */
		public int getViewTypeCount() {
			return 2;
		}
		
		/**
		 * position位置对应的item是什么类型
		 */
		public int getItemViewType(int position) {
			ChatMessage msg = messages.get(position);
			/**
			 * 根据消息的性质返回不同的int值
			 */
			return msg.isFromMessage?0:1;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ChatMessage msg = messages.get(position);
			
			ViewHolder holder = null;
			if (convertView == null) {
				if (msg.isFromMessage) { // 别人发的消息就用左边的布局文件
					convertView = getLayoutInflater().inflate(R.layout.chatting_item_msg_text_left, parent, false);
				} else { // 自己发的消息就用右边的布局文件
					convertView = getLayoutInflater().inflate(R.layout.chatting_item_msg_text_right, parent, false);
				}
			
				holder = new ViewHolder();
				holder.sendTimeView = (TextView) convertView.findViewById(R.id.date);
				holder.messageView = (TextView) convertView.findViewById(R.id.message);
				holder.nameView = (TextView) convertView.findViewById(R.id.name);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			holder.sendTimeView.setText(msg.sendTime);
			holder.messageView.setText(msg.message);
			holder.nameView.setText(msg.name);
			
			return convertView;
		}
		
		private class ViewHolder {
			public TextView sendTimeView;
			public TextView messageView;
			public TextView nameView;
		}
	} 
}