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
	
	//����½ںź��½�����
	public ArrayList<HashMap<String,String>> getChapterAndNum(int courseId)throws Exception
	{
		Cursor c = null;
		c = mdb.query(TABLE_NAME, new String[]{"chapternumber","chaptername","count(chapternumber)"}, "coursenumber='"+courseId+"'", null, "chapternumber", null, "chapternumber");
		ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		while(c.moveToNext())
		{
			String i = c.getString(0);
			String chaptername = "����"+i+"�¡�";
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("�½�", chaptername);
			map.put("��Ƶ����", "����"+String.valueOf(c.getInt(2))+ "��Ƶ");
			map.put("chapter", c.getString(0));
			list.add(map);
		}
		return list;
	}
	
	
	//�����½ںźͿγ̺Ż�������Ѿ����صĿγ�
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
	
	//������е��ļ�����
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
	
	//�����ļ��Ƿ����
	public void updateIsExist(String path,int i)
	{
		String sql = "update Video set isdownloaded='"+i+"' where filename = '"+path+"'";
		mdb.execSQL(sql);
	}
	
	
}
