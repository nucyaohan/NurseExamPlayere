package com.csic.player;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.MediaController;
//import android.widget.VideoView;

public class NurseExamPlayerActivity extends Activity
{
	/** Called when the activity is first created. */
	private Transfer tf;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		tf = new Transfer();
		final VideoView videoView = (VideoView) findViewById(R.id.videoView);
		Intent intent = getIntent();
		String path = intent.getStringExtra("path");
		try {
			tf.opt(path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MediaController mediaController = new MediaController(this);
		videoView.setMediaController(mediaController);
		videoView.setVideoPath(path);
		mediaController.setMediaPlayer(videoView);
		videoView.start();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME || event.getRepeatCount() == 0)
		{
			Intent intent = getIntent();
			String path = intent.getStringExtra("path");
			try {
				tf.opt(path);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
	
	
}
