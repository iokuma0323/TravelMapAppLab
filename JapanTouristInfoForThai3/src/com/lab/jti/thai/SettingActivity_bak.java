package com.lab.jti.thai;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

/**
 * 設定アクティビティ
 *
 */
public class SettingActivity_bak extends Activity {

	private final String TAG = "SettingActivity"; // タグ
	private final String SP_USER = "SharedPreferences_User"; // SharedPreferencesKey:ユーザー
	private final String SP_EDIT_USER_ID = "SharedPreferencesEdit_UserID"; // SharedPreferencesEditKey:ユーザーID
	private SeekBar mSeekBar_MinTime;	// ロケーション変更時 MinTime値
	private SeekBar mSeekBar_MinDistance;	// ロケーション変更時　MinTime値
	private NumberPicker mNumberPicker_MapType; // MapTypeピッカー
	private Button mBtn_RegistUser; // ユーザー登録ボタン
	private String[] strNumberPicker_MapType; // MapType
	
	int mMinTimeVal = 0;
	int mMinDistanceVal = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_bak);
//		Log.d(TAG, "onCreate : " + "Start");
//		
//		strNumberPicker_MapType = new String[] { "Normal", "Satellite", "Satellite", "Terrain", "None" }; // MapType
//		
//		mSeekBar_MinTime = (SeekBar) findViewById(R.id.seekBar_MinTime);
//		mSeekBar_MinTime.setOnSeekBarChangeListener(minTime);	// ロケーション変更時 MinTime値変更処理
//		mSeekBar_MinDistance = (SeekBar) findViewById(R.id.seekBar_MinDistance);
//		mSeekBar_MinDistance.setOnSeekBarChangeListener(minDistance);	// ロケーション変更時 MinDistance値変更処理
//		mNumberPicker_MapType = (NumberPicker) findViewById(R.id.numberPicker_MapType);
//		mNumberPicker_MapType.setMaxValue(strNumberPicker_MapType.length - 1);
//		mNumberPicker_MapType.setMinValue(0);
//		mNumberPicker_MapType.setDisplayedValues(strNumberPicker_MapType);
//		mNumberPicker_MapType.setFocusable(true);
//		mNumberPicker_MapType.setFocusableInTouchMode(true);
//		mBtn_RegistUser = (Button) findViewById(R.id.button_RegistSetting);
//		mBtn_RegistUser.setOnClickListener(registUser); // ユーザー登録ボタン押下処理
//		Log.d(TAG, "onCreate : " + "End");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume : " + "Start");
		Log.d(TAG, "onResume : " + "End");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		Log.d(TAG, "onCreateOptionsMenu : " + "Start");
		Log.d(TAG, "onCreateOptionsMenu : " + "End");
		return true;
	}

	
	/**
	 * 設定登録ボタン押下処理
	 */
	private final OnClickListener registUser = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Log.d(TAG, "onClick : " + "Start");
			
			startService(new Intent(getBaseContext(), SendRequestTimerService.class));
			
			MapActivity.mMapType = mNumberPicker_MapType.getValue() + 1;
			MapActivity.mMinTimeVal = mMinTimeVal;
			MapActivity.mMinDistanceVal = mMinDistanceVal;
			finish();
		}
	};
	
	/**
	 * MapLocation更新時間シークバー変更処理
	 */
	private final OnSeekBarChangeListener minTime = new OnSeekBarChangeListener() {
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			// TODO Auto-generated method stub
			Log.d(TAG, "onProgressChanged : " + "Start");
			mMinTimeVal = seekBar.getProgress();
			Log.d(TAG, "onProgressChanged : " + "End");
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			Log.d(TAG, "onStartTrackingTouch : " + "Start");
			Log.d(TAG, "onStartTrackingTouch : " + "End");
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			Log.d(TAG, "onStopTrackingTouch : " + "Start");
			Log.d(TAG, "onStopTrackingTouch : " + "End");
		}
	};
	
	/**
	 * MapLocation更新時間シークバー変更処理
	 */
	private final OnSeekBarChangeListener minDistance = new OnSeekBarChangeListener() {
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			// TODO Auto-generated method stub
			Log.d(TAG, "onProgressChanged : " + "Start");
			mMinDistanceVal = seekBar.getProgress();
			Log.d(TAG, "onProgressChanged : " + "End");
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			Log.d(TAG, "onStartTrackingTouch : " + "Start");
			Log.d(TAG, "onStartTrackingTouch : " + "End");
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			Log.d(TAG, "onStopTrackingTouch : " + "Start");
			Log.d(TAG, "onStopTrackingTouch : " + "End");
		}
	};
	


	/**
	 * userID プリファレンス保存処理
	 * 
	 * @param context
	 * @param accessToken
	 */
	private void putSharedPreferences_UserID(Context context, String userID) {
		Log.d(TAG, "putSharedPreferences_UserID: " + "Start");
		SharedPreferences sharedPreferences = context.getSharedPreferences(SP_USER, Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString(SP_EDIT_USER_ID, userID);
		editor.commit();
		Log.d(TAG, "putSharedPreferences_UserID: " + "End");
	}

	/**
	 * userID　プリファレンス読込処理
	 * 
	 * @param context
	 * @return AccessToken
	 */
	private boolean getSharedPreferences_UserID(Context context) {
		Log.d(TAG, "getSharedPreferences_UserID: " + "Start");
		boolean b = false;
		SharedPreferences preferences = context.getSharedPreferences(SP_USER, Context.MODE_PRIVATE);
		String userID = preferences.getString(SP_EDIT_USER_ID, null);
		if (userID != null) {
			b = true;
		}
		Log.d(TAG, "getSharedPreferences_UserID: " + "End");
		return b;
	}
}