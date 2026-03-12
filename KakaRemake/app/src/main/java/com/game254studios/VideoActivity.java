package com.game254studios;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.VideoView;

public class VideoActivity extends Activity implements OnCompletionListener {

	private VideoView video;

	private ImageView splash;
	
	private RelativeLayout container;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_main);

		splash = (ImageView) findViewById(R.id.imageView1);
		video = (VideoView) findViewById(R.id.videoView1);
		container = (RelativeLayout)findViewById(R.id.container);
		
		video.setOnCompletionListener(this);
		video.setVisibility(View.GONE);
		

		String path = "android.resource://" + getPackageName() + "/";
				//+ R.raw.intro_mpeg4;

		String levelComplete = "android.resource://" + getPackageName() + "/";
			//	+ R.raw.level_complete;

		if (getIntent().hasExtra("complete")){
			video.setVideoURI(Uri.parse(levelComplete));
			
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					splash.setVisibility(View.GONE);

					video.setVisibility(View.VISIBLE);

					video.start();

				}
			});
			
		}else{
			video.setVideoURI(Uri.parse(path));
			// splash
			splashThread.start();
		}

		

		

		

	}

	@Override
	public void onCompletion(MediaPlayer mp) {

		video.setVisibility(View.GONE);
		container.setBackgroundColor(Color.WHITE);
		//splash.setImageDrawable(getResources().getDrawable(
		//		R.drawable.loading));
		splash.setVisibility(View.VISIBLE);
		
		
				startActivity(new Intent(VideoActivity.this,
						MainActivity.class));
				overridePendingTransition(0, 0);
				finish();
				overridePendingTransition(0, 0);
				

				
		

		// overridePendingTransition(android.R.anim.fade_out,
		// android.R.anim.fade_in);
	}

	@Override
	protected void onPause() {
		super.onPause();
		video.stopPlayback();

	}

	private Thread splashThread = new Thread(new Runnable() {

		@Override
		public void run() {
			try {
				Thread.sleep(4000);

				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						splash.setVisibility(View.GONE);

						video.setVisibility(View.VISIBLE);

						video.start();

					}
				});

				// startActivity(new
				// Intent(getApplicationContext(),MainActivity.class));
				//

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	});

}
