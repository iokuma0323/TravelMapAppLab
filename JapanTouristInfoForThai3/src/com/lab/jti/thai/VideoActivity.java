package com.lab.jti.thai;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

/**
 * 動画アクティビティ
 */
public class VideoActivity extends Activity {

	private final String TAG = "VideoActivity"; // タグ

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video);
		Log.d(TAG, "onCreate: " + "Start");
		String filePath = "android.resource://" + getPackageName() + "/" + R.raw.musical_at_narita_airport;
		VideoView videoView = (VideoView) findViewById(R.id.videoView_Video);
//		videoView.setVideoPath("http://www.youtube.com/embed/B6v_BeSWgPg");
//		videoView.setVideoPath(Environment.getExternalStorageDirectory().getPath() + "/" + "musical_at_narita_airport.mp4");
		videoView.setVideoURI(Uri.parse(filePath));
		
		videoView.setMediaController(new MediaController(this));
		
		videoView.start();
		Log.d(TAG, "onCreate : " + "End");
	}
}
