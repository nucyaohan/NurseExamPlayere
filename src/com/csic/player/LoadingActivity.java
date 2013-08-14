package com.csic.player;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.Toast;

public class LoadingActivity extends Activity
{
	private AlphaAnimation alphaAnimation;
	private VideoDbAdapter db;
	private ImageView image;
	public final static String databasepath = "/data/data/com.csic.player/databases/";
	public final static String videopath = "/mnt/sdcard/video/";
	public static boolean flag;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);
		db = new VideoDbAdapter(this);
		SharedPreferences sharedPreferences = getSharedPreferences("Setting", 0);
		flag = sharedPreferences.getBoolean("REGISTERED", false);
		//ArrayList<HashMap<String, String>> list = db.updateIsExist(path);
		try {
			updateIsExist(videopath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
		image = (ImageView) findViewById(R.id.loading_background);
		image.setAnimation(alphaAnimation);
		alphaAnimation.setDuration(2000);
		alphaAnimation.setAnimationListener(new AnimationListener()
		{

			public void onAnimationStart(Animation animation)
			{

			}

			public void onAnimationRepeat(Animation animation)
			{

			}

			public void onAnimationEnd(Animation animation)
			{
				boolean flag = init();
				if (!flag)
				{
					Toast.makeText(LoadingActivity.this, "未注册版本，可以免费使用试听包；欢迎注册使用精解包", Toast.LENGTH_SHORT).show();
				}
				Intent intent = new Intent(LoadingActivity.this, VideoListActivity.class);
				startActivity(intent);
				LoadingActivity.this.finish();
			}
		});
	}

	protected boolean init()
	{
		SharedPreferences sharedPreferences = getSharedPreferences("Setting", 0);
		boolean registered = sharedPreferences.getBoolean("VIDEO", false);
		File file = new File(videopath);
		if(!isExistDatabase())
		{
			copyDatabase();
		}
		if(!isExistvideo1())
		{
			copyvideo1();
		}
		if(!isExistvideo2())
		{
			copyvideo2();
		}
		if(!isExistvideo3())
		{
			copyvideo3();
		}
		if (!file.exists())
		{
			file.mkdir();
		}
		if (registered)
		{
			return true;
		}
		return false;
	}
	
	protected void updateIsExist(String path) throws Exception
	{
		ArrayList<String> list = null;
		ArrayList<String> filelist = new ArrayList<String>();
		File file = new File(path);
		File []files = file.listFiles();
		for(int i=0;i<files.length;i++)
		{
			int start = files[i].toString().lastIndexOf("/");
			int end = files[i].toString().indexOf(".mp4");
			filelist.add(files[i].toString().substring(start+1, end));
		}
		
		
		db.open();
		list = db.getAllFiles();
		db.close();
		for(int i=0;i<list.size();i++)
		{
			if(filelist.contains(list.get(i).toString()))
			{
				db.open();
				db.updateIsExist(list.get(i).toString(), 1);
				db.close();
			}
			else
			{
				db.open();
				db.updateIsExist(list.get(i).toString(), 0);
				db.close();
			}
		}
	}
	
	protected boolean isExistDatabase()
	{
		try
		{
			String realthPath = databasepath + "Video";
			File file = new File(realthPath);
			if (file.exists())
			{
				return true;
			}
		}
		catch (Exception e)
		{
			Log.d("database wrong", e.getMessage());
		}

		return false;
	}
	
	protected void copyDatabase()
	{
		File file = new File(databasepath+"Video");
		File dir = new File(databasepath);
		FileOutputStream fos = null;
		InputStream fis = null;
		if (!dir.exists())
		{
			dir.mkdir();
		}
		try
		{
			fos = new FileOutputStream(file);
			fis = LoadingActivity.this.getResources().openRawResource(R.raw.video);
			byte[] buffer = new byte[1024];
			int count = 0;
			while ((count = fis.read(buffer)) > 0)
			{
				fos.write(buffer, 0, count);
				fos.flush();
			}

			fis.close();
			fos.close();
		}
		catch (Exception e)
		{
			Log.d("file output wrong", e.getMessage());
		}

	}
	
	protected boolean isExistvideo1()
	{
		try
		{
			String realthPath = videopath+"video1.mp4";
			File file = new File(realthPath);
			if (file.exists())
			{
				return true;
			}
		}
		catch (Exception e)
		{
			Log.d("database wrong", e.getMessage());
		}

		return false;
	}
	
	protected void copyvideo1()
	{
		File file = new File(videopath+"video1.mp4");
		File dir = new File(videopath);
		FileOutputStream fos = null;
		InputStream fis = null;
		if (!dir.exists())
		{
			dir.mkdir();
		}
		try
		{
			fos = new FileOutputStream(file);
			fis = LoadingActivity.this.getResources().openRawResource(R.raw.video1);
			byte[] buffer = new byte[1024];
			int count = 0;
			while ((count = fis.read(buffer)) > 0)
			{
				fos.write(buffer, 0, count);
				fos.flush();
			}

			fis.close();
			fos.close();
		}
		catch (Exception e)
		{
			Log.d("file output wrong", e.getMessage());
		}
	}
	
	protected boolean isExistvideo2()
	{
		try
		{
			String realthPath = videopath+"video2.mp4";
			File file = new File(realthPath);
			if (file.exists())
			{
				return true;
			}
		}
		catch (Exception e)
		{
			Log.d("database wrong", e.getMessage());
		}

		return false;
	}
	
	protected void copyvideo2()
	{
		File file = new File(videopath+"video2.mp4");
		File dir = new File(videopath);
		FileOutputStream fos = null;
		InputStream fis = null;
		if (!dir.exists())
		{
			dir.mkdir();
		}
		try
		{
			fos = new FileOutputStream(file);
			fis = LoadingActivity.this.getResources().openRawResource(R.raw.video2);
			byte[] buffer = new byte[1024];
			int count = 0;
			while ((count = fis.read(buffer)) > 0)
			{
				fos.write(buffer, 0, count);
				fos.flush();
			}

			fis.close();
			fos.close();
		}
		catch (Exception e)
		{
			Log.d("file output wrong", e.getMessage());
		}
	}
	
	protected boolean isExistvideo3()
	{
		try
		{
			String realthPath = videopath+"video3.mp4";
			File file = new File(realthPath);
			if (file.exists())
			{
				return true;
			}
		}
		catch (Exception e)
		{
			Log.d("database wrong", e.getMessage());
		}

		return false;
	}
	
	protected void copyvideo3()
	{
		File file = new File(videopath+"video3.mp4");
		File dir = new File(videopath);
		FileOutputStream fos = null;
		InputStream fis = null;
		if (!dir.exists())
		{
			dir.mkdir();
		}
		try
		{
			fos = new FileOutputStream(file);
			fis = LoadingActivity.this.getResources().openRawResource(R.raw.video3);
			byte[] buffer = new byte[1024];
			int count = 0;
			while ((count = fis.read(buffer)) > 0)
			{
				fos.write(buffer, 0, count);
				fos.flush();
			}

			fis.close();
			fos.close();
		}
		catch (Exception e)
		{
			Log.d("file output wrong", e.getMessage());
		}
	}
	
	
}
