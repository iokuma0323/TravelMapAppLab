package com.lab.jti.thai;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ユーザー情報確認 アクティビティ
 */
public class CheckUserInfoActivity  extends Activity {

	private final String TAG = "CheckUserInfoActivity"; // タグ
	private static TextView mTextView_ID; // ID
	private static TextView mTextView_UserID; // ユーザーID
	private static TextView mTextView_UerName; //ユーザー名
	private static TextView mTextView_UserGender; // ユーザー性別
	private static TextView mTextView_UserAge; // ユーザー年齢
	private static TextView mTextView_UserTel; // ユーザー電話番号

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.check_user_info);
		Log.d(TAG, "onCreate: " + "Start");
		mTextView_ID = (TextView) findViewById(R.id.TextView_CheckUserInfo_ID);
		mTextView_UserID = (TextView) findViewById(R.id.TextView_CheckUserInfo_USER_ID);
		mTextView_UerName = (TextView) findViewById(R.id.TextView_CheckUserInfo_USER_NAME);
		mTextView_UserGender = (TextView) findViewById(R.id.TextView_CheckUserInfo_USER_GENDER);
		mTextView_UserAge = (TextView) findViewById(R.id.TextView_CheckUserInfo_USER_AGE);
		mTextView_UserTel = (TextView) findViewById(R.id.TextView_CheckUserInfo_USER_TEL);
		checkUser();// ユーザー確認処理
		Log.d(TAG, "onCreate : " + "End");
	}
	
	@Override
	public void onUserLeaveHint() {
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			stopService(new Intent(getBaseContext(), SendRequestTimerService.class));
			finish();
			return true;
		}
		return false;
	}

	/**
	 * ユーザー登録確認処理
	 * @return 
	 */
	public static void checkUser() {
		Map<String, String> map = new HashMap<String, String>();
		map = MainActivity.getSharedPreferences_UserInfo(MainActivity.mContext);
		
		if (map.get(Constant.SharedPreferences.SP_EDIT_ID.toString()) == null) {
			Toast.makeText(MainActivity.mContext, "checkUserRegist : Not Found User" , Toast.LENGTH_SHORT).show();			
		} else {
			Toast.makeText(MainActivity.mContext, "checkUserRegist : User Registed", Toast.LENGTH_SHORT).show();
			mTextView_ID.setText(map.get(Constant.SharedPreferences.SP_EDIT_ID.toString()));
			mTextView_UserID.setText(map.get(Constant.SharedPreferences.SP_EDIT_USER_ID.toString()));
			mTextView_UerName.setText(map.get(Constant.SharedPreferences.SP_EDIT_USER_NAME.toString()));
			mTextView_UserGender.setText(map.get(Constant.SharedPreferences.SP_EDIT_USER_GENDER.toString()));
			mTextView_UserAge.setText(map.get(Constant.SharedPreferences.SP_EDIT_USER_AGE.toString()));
			mTextView_UserTel.setText(map.get(Constant.SharedPreferences.SP_EDIT_USER_TEL.toString()));
		}
	}
}