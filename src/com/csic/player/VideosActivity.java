package com.csic.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class VideosActivity extends Activity {
	
	private ListView listView;
	private SimpleAdapter simpleAdapter;
	private List<Map<String, String>> data;
	private Button courseButton;
	private RelativeLayout relativelayout;
	private RelativeLayout mainList;
	private VideoDbAdapter db;
	private LayoutInflater inflater;
	private CheckPasswordActivity check;
	public MyApp appState;
	public final static String path = "/mnt/sdcard/video/";
	public static int Chapter;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedinstancestate) {
		// TODO Auto-generated method stub
		super.onCreate(savedinstancestate);
		setContentView(R.layout.video_main);
		check = new CheckPasswordActivity();
		db = new VideoDbAdapter(this);
		appState = (MyApp)getApplicationContext();  
		Intent intent = getIntent();
		int chapterId = intent.getIntExtra("chapterId", 1);
		courseButton = (Button) findViewById(R.id.main_left);
		courseButton.setOnClickListener(new CourseListener());
		inflater = LayoutInflater.from(this);
		relativelayout = (RelativeLayout)findViewById(R.id.main_panel_list);
		mainList = (RelativeLayout)inflater.inflate(R.layout.video_list, null).findViewById(R.id.video_list_layout);
		listView = (ListView)mainList.findViewById(R.id.video_list);
		data = new ArrayList<Map<String,String>>();
		VideosActivity.Chapter = chapterId;
		if(chapterId!=0)
		{
			try {
				db.open();
				ArrayList<HashMap<String,String>> list = db.getVideosByChapterAndCourse(appState.getState(), chapterId);
				for(int i=0;i<list.size();i++)
				{
					Map map = new HashMap<String,String>();
					map.put("chaptername", list.get(i).get("chaptername"));
					map.put("filename", list.get(i).get("filename"));
					String isdownloaded = list.get(i).get("isdownloaded").toString().trim();
					if(isdownloaded.equals("0"))
					{
						map.put("isdownloaded", "未下载");
					}
					else 
					{
						map.put("isdownloaded", "已下载");
					}
					data.add(map);
				}
				db.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			Map map = new HashMap<String,String>();
			map.put("chaptername", "样例");
			map.put("isdownloaded", "已下载");
			map.put("filename", "video"+appState.getState());
			data.add(map);
		}
		
		simpleAdapter  = new SimpleAdapter(this, data, R.layout.video_item, new String[]{"chaptername","isdownloaded"}, new int[]{R.id.video_title,R.id.video_download});
		listView.setAdapter(simpleAdapter);
		listView.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
				// TODO Auto-generated method stub
				String isdownloaded = data.get(position).get("isdownloaded").toString();
				if(isdownloaded.equals("未下载"))
				{
					Toast.makeText(VideosActivity.this, "视频下载，请下载相应视频放到目录/mnt/sdcard/video/", Toast.LENGTH_SHORT).show();
				}
				else if(isdownloaded.equals("已下载")&&VideosActivity.Chapter==0)
				{
					Intent intent = new Intent(VideosActivity.this,NurseExamPlayerActivity.class);
					String filename = data.get(position).get("filename").toString();
					String filepath = path+filename+".mp4";
					intent.putExtra("path", filepath);
					Toast.makeText(VideosActivity.this, "欢迎同学注册使用护考宝典视频辅导软件，此视频为样例，时长两分种", Toast.LENGTH_LONG).show();
					startActivity(intent);
				}
				else if(isdownloaded.equals("已下载")&&LoadingActivity.flag&&VideosActivity.Chapter!=0)
				{
					Intent intent = new Intent(VideosActivity.this,NurseExamPlayerActivity.class);
					String filename = data.get(position).get("filename").toString();
					String filepath = path+filename+".mp4";
					intent.putExtra("path", filepath);
					startActivity(intent);
				}
			}
		});
		
		relativelayout.removeAllViews();
		relativelayout.addView(mainList);
		
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
						Toast.makeText(VideosActivity.this, "您选择的是基础精讲", Toast.LENGTH_SHORT).show();
					}
					if(checkedid == R.id.radio2)
					{
						appState.setState(2);
						Toast.makeText(VideosActivity.this, "您选择的是串讲点睛", Toast.LENGTH_SHORT).show();
					}
					if(checkedid == R.id.radio3)
					{
						appState.setState(3);
						Toast.makeText(VideosActivity.this, "您选择的是冲刺特训", Toast.LENGTH_SHORT).show();
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
					Intent intent = new Intent(VideosActivity.this,VideoListActivity.class);
					intent.putExtra("appState", appState.getState());
					VideosActivity.this.startActivity(intent);
					VideosActivity.this.finish();
				}
			});
		}
	}
	
}
