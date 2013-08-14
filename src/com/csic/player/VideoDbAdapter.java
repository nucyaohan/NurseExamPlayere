package com.csic.player;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class VideoDbAdapter {

	public static final String TABLE_NAME = "Video";
	private DatabaseHelper dbHelper;
	private SQLiteDatabase mdb;
	private Context ctx;
	
	
	public VideoDbAdapter(Context ctx)
	{
		this.ctx = ctx;
	}
	
	public VideoDbAdapter open()
	{
		dbHelper = new DatabaseHelper(ctx);
		mdb = dbHelper.getWritableDatabase();
		return this;
	}
	
	public void close()
	{
		mdb.close();
		dbHelper.close();
	}
	
	//获得章节号和章节数量
	public ArrayList<HashMap<String,String>> getChapterAndNum(int courseId)throws Exception
	{
		Cursor c = null;
		c = mdb.query(TABLE_NAME, new String[]{"chapternumber","chaptername","count(chapternumber)"}, "coursenumber='"+courseId+"'", null, "chapternumber", null, "chapternumber");
		ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		while(c.moveToNext())
		{
			String i = c.getString(0);
			String chaptername = "【第"+i+"章】";
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("章节", chaptername);
			map.put("视频数量", "共有"+String.valueOf(c.getInt(2))+ "视频");
			map.put("chapter", c.getString(0));
			list.add(map);
		}
		return list;
	}
	
	
	//根据章节号和课程号获得所有已经下载的课程
	public ArrayList<HashMap<String,String>> getVideosByChapterAndCourse(int courseId,int chapterId)throws Exception
	{
		ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		Cursor c = null;
		c = mdb.query(TABLE_NAME, new String[] {"chaptername","filename","isdownloaded"}, "coursenumber='"+courseId+"' and chapternumber='"+chapterId+"' and isdownloaded = 1 ", null, null, null, null);
		while(c.moveToNext())
		{
			String chaptername = new String(c.getBlob(c.getColumnIndex("chaptername")),"GBK");
			String filename = new String(c.getBlob(c.getColumnIndex("filename")),"GBK");
			String isdownloaded = new String(c.getBlob(c.getColumnIndex("isdownloaded")),"GBK");
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("chaptername", chaptername);
			map.put("filename", filename);
			map.put("isdownloaded", isdownloaded);
			list.add(map);
		}
		return list;
	}
	
	//获得所有的文件名称
	public ArrayList<String> getAllFiles()throws Exception
	{
		ArrayList<String> list = new ArrayList<String>();
		Cursor c = null;
		c = mdb.query(TABLE_NAME, new String[]{"id","filename"}, null, null, null, null, null);
		while(c.moveToNext())
		{
			String filename = new String(c.getBlob(c.getColumnIndex("filename")),"GBK");
			list.add(filename.trim());
		}
		
		return list;
	}
	
	//更新文件是否存在
	public void updateIsExist(String path,int i)
	{
		String sql = "update Video set isdownloaded='"+i+"' where filename = '"+path+"'";
		mdb.execSQL(sql);
	}
	
	
}
