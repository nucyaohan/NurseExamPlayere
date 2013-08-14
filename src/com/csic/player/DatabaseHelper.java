package com.csic.player;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{

	private static final String DATABASE_NAME = "Video";
	private static final int DATABASE_VERSION = 1;
	
	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	
	public DatabaseHelper(Context context,String name)
	{
		this(context, name, DATABASE_VERSION);
	}
	
	public DatabaseHelper(Context context, String name, int version)
	{
		this(context, name, null, version);
	}
	
	public DatabaseHelper(Context ctx)
	{
		this(ctx, DATABASE_NAME);
	}
	
	//该函数是在第一次创建数据库的时候执行，实际上是在第一次得到SQLiteDatabase对象的时候才会调用，
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("create table Video(id integer primary key autoincrement," +
				"coursename varchar(100),coursenumber varchar(10),chapternumber varchar(100)," +
				"chaptername varchar(400),filename varchar(200),isdownloaded varchar(10),item1 varchar(50))");
		
		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
