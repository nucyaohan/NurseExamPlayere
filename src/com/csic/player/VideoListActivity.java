package com.csic.player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class VideoListActivity extends Activity
{
	// TODO 表头 注册界面 主界面（几大类） 列表界面（扫描文件，下载）
	// TODO N个大项。第一个试用的，其余的是买的
	// TODO 提供下载功能，是否断点下载
	// TODO 更新列表
	// TODO 先是灰的，要是没注册不能点击。能点击的话，可以下载。
	// TODO 多线程下载
	private ListView listView;
	private SimpleAdapter simpleAdapter;
	private List<Map<String, String>> data;
	private Button courseButton;
	private Button registerButton;
	private RelativeLayout relativelayout;
	private RelativeLayout mainList;
	private VideoDbAdapter db;
	private LayoutInflater inflater;
	public MyApp appState;
	private TextView tv = null;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_main);
		db = new VideoDbAdapter(this);
		courseButton = (Button) findViewById(R.id.main_left);
		courseButton.setOnClickListener(new CourseListener());
		registerButton = (Button)findViewById(R.id.main_right);
		registerButton.setOnClickListener(new RegisterListener());
		
		Intent intent = getIntent();
		int state = intent.getIntExtra("appState", 0);
		relativelayout = (RelativeLayout)findViewById(R.id.main_panel_list);
		inflater = LayoutInflater.from(this);
		
		mainList = (RelativeLayout)inflater.inflate(R.layout.video_list, null).findViewById(R.id.video_list_layout);
		listView = (ListView)mainList.findViewById(R.id.video_list);
		data = new ArrayList<Map<String, String>>();
		
		
		if(state==0)
		{
			mainList = (RelativeLayout)inflater.inflate(R.layout.select_course_style, null).findViewById(R.id.course_list_layout);
		    tv = (TextView)inflater.inflate(R.layout.select_course_style, null).findViewById(R.id.coursedesc);
			RadioGroup radioGroup = (RadioGroup)mainList.findViewById(R.id.radio_group);
			radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener()
			{
				public void onCheckedChanged(RadioGroup group, int checkedid) {
					// TODO Auto-generated method stub
					if(checkedid == R.id.radio1)
					{
						appState.setState(1);
						Toast.makeText(VideoListActivity.this, "您选择的是基础精讲", Toast.LENGTH_SHORT).show();
					}
					if(checkedid == R.id.radio2)
					{
						appState.setState(2);
						Toast.makeText(VideoListActivity.this, "您选择的是串讲点睛", Toast.LENGTH_SHORT).show();
					}
					if(checkedid == R.id.radio3)
					{
						appState.setState(3);
						Toast.makeText(VideoListActivity.this, "您选择的是冲刺特训", Toast.LENGTH_SHORT).show();
					}
				}
				
			});
			relativelayout.removeAllViews();
			relativelayout.addView(mainList);
			Button back = (Button)mainList.findViewById(R.id.course_start);
			back.setOnClickListener(new OnClickListener()
			{

				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(VideoListActivity.this,VideoListActivity.class);
					intent.putExtra("appState", appState.getState());
					VideoListActivity.this.startActivity(intent);
					VideoListActivity.this.finish();
				}
			});
		}
		else
		{
			relativelayout = (RelativeLayout)findViewById(R.id.main_panel_list);
			inflater = LayoutInflater.from(this);
			if(LoadingActivity.flag)
			{
				try {
					db.open();
					ArrayList<HashMap<String, String>> list = db.getChapterAndNum(state);
					db.close();
					for(int i=0;i<list.size();i++)
					{
						Map map = new HashMap<String,String>();
						map.put("chapter", list.get(i).get("章节"));
						map.put("count", list.get(i).get("视频数量"));
						map.put("chapternumber", list.get(i).get("chapter"));
						data.add(map);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				Map map = new  HashMap<String,String>();
				map.put("chapter", "样例");
				map.put("count", 1);
				map.put("chapternumber", "0");
				data.add(map);
			}
			
			simpleAdapter = new SimpleAdapter(this,data,R.layout.chapter_item,new String[]{"chapter","count"}, new int[]{R.id.chapter_title,R.id.video_count});
			listView.setAdapter(simpleAdapter);
			listView.setOnItemClickListener(new OnItemClickListener(){

			public void onItemClick(AdapterView<?> parent, View view,int position, long id){
					// TODO Auto-generated method stub
					Intent intent = new Intent(VideoListActivity.this,VideosActivity.class);
					int chapterId = Integer.parseInt(data.get(position).get("chapternumber"));
					intent.putExtra("chapterId", chapterId);
					startActivity(intent);
				}
			});
		}
		
		relativelayout.removeAllViews();
		relativelayout.addView(mainList);
		appState = (MyApp)getApplicationContext();  
	}
	
	private class CourseListener implements OnClickListener
	{

		public void onClick(View v) {
			// TODO Auto-generated method stub
			mainList = (RelativeLayout)inflater.inflate(R.layout.select_course_style, null).findViewById(R.id.course_list_layout);
			RadioGroup radioGroup = (RadioGroup)mainList.findViewById(R.id.radio_group);
			radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener()
			{
				public void onCheckedChanged(RadioGroup group, int checkedid) {
					// TODO Auto-generated method stub
					if(checkedid == R.id.radio1)
					{
						appState.setState(1);
						Toast.makeText(VideoListActivity.this, "您选择的是基础精讲", Toast.LENGTH_SHORT).show();
					}
					if(checkedid == R.id.radio2)
					{
						appState.setState(2);
						Toast.makeText(VideoListActivity.this, "您选择的是串讲点睛", Toast.LENGTH_SHORT).show();
					}
					if(checkedid == R.id.radio3)
					{
						appState.setState(3);
						Toast.makeText(VideoListActivity.this, "您选择的是冲刺特训", Toast.LENGTH_SHORT).show();
					}
				}
				
			});
			relativelayout.removeAllViews();
			relativelayout.addView(mainList);
			Button back = (Button)mainList.findViewById(R.id.course_start);
			back.setOnClickListener(new OnClickListener()
			{

				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(VideoListActivity.this,VideoListActivity.class);
					intent.putExtra("appState", appState.getState());
					VideoListActivity.this.startActivity(intent);
					VideoListActivity.this.finish();
				}
			});
		}
	}
	
	private class RegisterListener implements OnClickListener
	{
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(VideoListActivity.this,RegisterInfoActivity.class);
			VideoListActivity.this.startActivity(intent);
			VideoListActivity.this.finish();
		}
		
	}

	@Override
	public boolean onKeyDown(int keycode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyDown(keycode, event);
	}
	
	
	
	
}
