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
import android.widget.TextView;

/**
 * �ݒ�A�N�e�B�r�e�B
 *
 */
public class SettingActivity extends Activity {

	private final String TAG = "SettingActivity"; // �^�O
	private TextView mTextView_Meter; // ���[�^�[�s�b�J�[
	private NumberPicker mNumberPicker_SearchMeter; // ���[�^�[�s�b�J�[
	private Button mBtn_RegistUser; // �ݒ�{�^��
	
	int mMinTimeVal = 0;
	int mMinDistanceVal = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		Log.d(TAG, "onCreate : " + "Start");
		mNumberPicker_SearchMeter = (NumberPicker) findViewById(R.id.numberPicker_SearchMeter);
		mNumberPicker_SearchMeter.setMaxValue(10000);
		mNumberPicker_SearchMeter.setMinValue(1);
		mNumberPicker_SearchMeter.setValue(100);
		mBtn_RegistUser = (Button) findViewById(R.id.button_Setting);
		mBtn_RegistUser.setOnClickListener(registUser); // �ݒ�{�^����������
		Log.d(TAG, "onCreate : " + "End");
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
	 * �ݒ�o�^�{�^����������
	 */
	private final OnClickListener registUser = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Log.d(TAG, "onClick : " + "Start");
			MainActivity.mMeter = mNumberPicker_SearchMeter.getValue();
			finish();
		}
	};
}