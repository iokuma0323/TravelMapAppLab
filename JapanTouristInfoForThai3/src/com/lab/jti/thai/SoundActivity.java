package com.lab.jti.thai;

import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 音声アクティビティ
 */
public class SoundActivity extends Activity {

	private final String TAG = "SoundActivity"; // タグ
	private MediaPlayer mMediaPlayer;
	private Button mBtn_SoundStart; // SoundStartボタン
	private Button mBtn_SoundStop; // SoundStopボタン
	private Button mBtn_SoundPause; // SoundPauseボタン


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sound);
		Log.d(TAG, "onCreate: " + "Start");
		
		mBtn_SoundStart = (Button) findViewById(R.id.button_Sound_Start);
		mBtn_SoundStop = (Button) findViewById(R.id.button_Sound_Stop);
		mBtn_SoundPause = (Button) findViewById(R.id.button_Sound_Pause);
		mBtn_SoundStart.setOnClickListener(sound); // SoundStartボタン押下処理
		mBtn_SoundStop.setOnClickListener(sound); // SoundStopボタン押下処理
		mBtn_SoundPause.setOnClickListener(sound); // SoundPauseボタン押下処理
		
		String filePath = "android.resource://" + getPackageName() + "/" + R.raw.tenzan;
		
		mMediaPlayer = new MediaPlayer();
		try {
//			mMediaPlayer.setDataSource(Environment.getExternalStorageDirectory().getPath() + "/" + "jazzfunk.mp3");
			mMediaPlayer.setDataSource(this, Uri.parse(filePath));
			mMediaPlayer.prepare();
		} catch (IllegalArgumentException e) {
		} catch (SecurityException e) {
		} catch (IllegalStateException e) {
		} catch (IOException e) {
		}
		mMediaPlayer.start();
		Log.d(TAG, "onCreate : " + "End");
	}
	
	/**
	 * ログインボタン押下処理
	 */
	private final OnClickListener sound = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Log.d(TAG, "onClick : " + "Start");
			switch (v.getId()) {
			case R.id.button_Sound_Start:
				Log.d(TAG, "button_Sound_Start : " + "Start");
				mMediaPlayer.start();
				Log.d(TAG, "button_Sound_Start : " + "End");
				break;
			case R.id.button_Sound_Stop:
				Log.d(TAG, "button_Sound_Stop : " + "Start");
				mMediaPlayer.stop();
				Log.d(TAG, "button_Sound_Stop : " + "End");
				break;
			case R.id.button_Sound_Pause:
				Log.d(TAG, "button_Sound_Stop : " + "Start");
				mMediaPlayer.pause();
				mMediaPlayer.seekTo(0);
				Log.d(TAG, "button_Sound_Stop : " + "End");
				break;
			}
			Log.d(TAG, "onClick : " + "End");
		}
	};
}
