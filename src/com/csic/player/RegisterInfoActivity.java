package com.csic.player;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterInfoActivity extends Activity{

	private Button button1;
	private Button button2;
	@Override
	protected void onCreate(Bundle savedinstancestate) {
		// TODO Auto-generated method stub
		super.onCreate(savedinstancestate);
		setContentView(R.layout.register_info);
		button1 = (Button) findViewById(R.id.top_back);
		button2 = (Button) findViewById(R.id.register);
		button1.setOnClickListener(new ReturnOnClickListener());
		button2.setOnClickListener(new RegisterOnClickListener());
	}
	
	private class ReturnOnClickListener implements OnClickListener
	{
		public void onClick(View v)
		{
			Intent intent = new Intent(RegisterInfoActivity.this, VideoListActivity.class);
			startActivity(intent);
			finish();
		}
	}
	
	private class RegisterOnClickListener implements OnClickListener
	{
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(LoadingActivity.flag)
			{
				Toast.makeText(RegisterInfoActivity.this, "您已经注册，不需要重复注册", Toast.LENGTH_LONG).show();
			}
			else
			{
				Intent intent =new Intent(RegisterInfoActivity.this,CheckPasswordActivity.class);
				RegisterInfoActivity.this.startActivity(intent);
				RegisterInfoActivity.this.finish();
			}
		}
	}

	
}
