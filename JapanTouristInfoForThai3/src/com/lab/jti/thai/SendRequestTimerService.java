package com.lab.jti.thai;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * リクエスト送信サービスクラス
 */
public class SendRequestTimerService extends Service {

	final static String TAG = "SendRequestTimerService";
	final int INTERVAL_PERIOD = 30000; // 30秒
//	final int INTERVAL_PERIOD = 5000; // 5秒
//	final int INTERVAL_PERIOD = 10000; // 10秒
	Timer timer = new Timer();

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onBind : " + "Start");
		Log.d(TAG, "onBind : " + "End");
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate : " + "Start");
		Log.d(TAG, "onCreate : " + "End");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "onStartCommand : " + "Start");
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Log.d(TAG, "run start");
				Intent intent = new Intent(getApplicationContext(), VolleyActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				stopService(new Intent(getBaseContext(), SendRequestTimerService.class));
				Log.d(TAG, "run end");
			}
		}, 0, INTERVAL_PERIOD);
		Log.d(TAG, "onStartCommand : " + "End");
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy : " + "Start");
		if (timer != null) {
			timer.cancel();
		}
		Log.d(TAG, "onDestroy : " + "End");
	}
}
