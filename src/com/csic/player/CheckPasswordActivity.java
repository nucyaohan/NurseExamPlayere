package com.csic.player;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CheckPasswordActivity extends Activity{

	private String userSerialNumber;
	private String USER_PASSWORD = "userPwd";
	private Button button;
	private Button cancel;
	private TextView codeText;
	private TextView myNumber;
	private TextView registerText;
	private ImageView registerImage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.input_code);
		button = (Button) findViewById(R.id.btn_ok);
		cancel = (Button) findViewById(R.id.btn_cancel);
		registerText = (TextView) findViewById(R.id.register_text);
		registerText.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(CheckPasswordActivity.this,RegisterInfoActivity.class);
				startActivity(intent);
				finish();
			}
		});
		myNumber = (TextView) findViewById(R.id.android_id_edit);
		codeText = (TextView) findViewById(R.id.input_code_edit);
		TelephonyManager  telephonyManager = (TelephonyManager ) this.getSystemService(Context.TELEPHONY_SERVICE);
		userSerialNumber = telephonyManager.getDeviceId();
		//userSerialNumber = Secure.getString(CheckPasswordActivity.this.getContentResolver(), Secure.ANDROID_ID);
		myNumber.setText(userSerialNumber);
		button.setOnClickListener(new OnClickListener()
		{

			public void onClick(View v)
			{
				String inputCode = codeText.getText().toString();
				SharedPreferences sharedPreferences = getSharedPreferences("Setting", 0);
				if (inputCode.equals(md5(userSerialNumber)))
				{
					Editor editor = sharedPreferences.edit();
					editor.putString(USER_PASSWORD, inputCode);
					editor.putBoolean("REGISTERED", true);
					editor.commit();
					Toast.makeText(CheckPasswordActivity.this, "注册成功，正在加载", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(CheckPasswordActivity.this, VideoListActivity.class);
					startActivity(intent);
					CheckPasswordActivity.this.finish();
				}
				else
				{
					Toast.makeText(CheckPasswordActivity.this, "注册失败，请重试", Toast.LENGTH_SHORT).show();
				}
			}
		});

		cancel.setOnClickListener(new OnClickListener()
		{

			public void onClick(View v)
			{
				Toast.makeText(CheckPasswordActivity.this, "注册失败，请及时购买，注册后支持所有视频", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(CheckPasswordActivity.this, VideoListActivity.class);
				startActivity(intent);
				CheckPasswordActivity.this.finish();
			}
		});
	}

	
	
	
	protected String md5(String string)
	{
		String pass = "";
		int step = string.length() / 10;
		for (int i = 0; i < 10; i += step)
		{
			char s = string.charAt(i);
			if (s >= 97 && s <= 122)
			{
				pass += (s - 97) % 12;
			}
			else if (s >= 48 && s <= 53)
			{
				pass += (s + i) * i % 12;
			}
			else if (s > 53 && s <= 57)
			{
				if (i == 0)
				{
					pass += (s - i) / 1 % 12;
				}
				else
				{
					pass += (s - i) / i % 12;
				}
			}
			else
			{
				pass += 0;
			}
		}
		return pass;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
		{
			Intent intent = new Intent(CheckPasswordActivity.this, VideoListActivity.class);
			CheckPasswordActivity.this.startActivity(intent);
			CheckPasswordActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
		
		
		
	
	
	
}
