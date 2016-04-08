package com.lab.jti.thai;

import java.util.HashMap;
import java.util.Map;

import com.example.android.sip.WalkieTalkieActivity;
import com.rakuraku.android.imasora.ImaSoraActivity;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * ���C���A�N�e�B�r�e�B ���O�C�����
 */
public class MainActivity extends Activity {

	public static Context mContext = null; // �R���e�L�X�g
	public static boolean mIsSharedPreferences = false; // SharedPreferences�i���[�U�[�f�[�^�j���݃t���O
	public static int mSwitchLanguage = 0; // ���ꂫ�肩��
	public static int mMeter = 1000; // Push���a���[�^�[

	private final String TAG = "MainActivity"; // �^�O
	private Button mBtn_Search; // Search�{�^��
	private Button mBtn_Itinerary; // Itinerary�{�^��
	private Button mBtn_Root; // Root�{�^��
	private Button mBtn_Hotel; // Hotel�{�^��
	private Button mBtn_Spot; // Spot�{�^��
	private Button mBtn_Shopping; // Shopping�{�^��
	private Button mBtn_Restaurant; // Restaurant�{�^��
	private Button mBtn_Call; // Call�{�^��
	// private Button mBtn_Weather; // Weather�{�^��
	// private Button mBtn_WifiSetting; // Wi-Fi Setting�{�^��
	// private Button mBtn_Guidance; // Guidance�{�^��

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.d(TAG, "onCreate: " + "Start");
		mContext = this;
		mBtn_Search = (Button) findViewById(R.id.button_Main_Search);
		mBtn_Itinerary = (Button) findViewById(R.id.button_Main_Itinerary);
		mBtn_Root = (Button) findViewById(R.id.button_Main_Root);
		mBtn_Hotel = (Button) findViewById(R.id.button_Main_Hotel);
		mBtn_Spot = (Button) findViewById(R.id.button_Main_Spot);
		mBtn_Shopping = (Button) findViewById(R.id.button_Main_Shopping);
		mBtn_Restaurant = (Button) findViewById(R.id.button_Main_Restaurant);
		mBtn_Call = (Button) findViewById(R.id.button_Main_Call);
		mBtn_Search.setOnClickListener(action); // Search�{�^����������
		mBtn_Itinerary.setOnClickListener(action); // Itinerary�{�^����������
		mBtn_Root.setOnClickListener(action); // Move�{�^����������
		mBtn_Hotel.setOnClickListener(action);// Stay�{�^����������
		mBtn_Spot.setOnClickListener(action); // Tour�{�^����������
		mBtn_Shopping.setOnClickListener(action); // Shopping�{�^����������
		mBtn_Restaurant.setOnClickListener(action);// Eat�{�^����������
		mBtn_Call.setOnClickListener(action); // Call�{�^����������
		checkUserRegist(); // ���[�U�[�o�^�m�F����
		Log.d(TAG, "onCreate : " + "End");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume : " + "Start");
		stopService(new Intent(getBaseContext(), SendRequestTimerService.class));
		Log.d(TAG, "onResume : " + "End");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.d(TAG, "onCreateOptionsMenu : " + "Start");
		getMenuInflater().inflate(R.menu.main, menu);
		menu.add(0, Constant.MENU_CHECK_USER_INFO, 0, Constant.Menu.MENU_CHECK_USER_INFO_TEXT.toString());
		menu.add(0, Constant.MENU_SETTING, 0, Constant.Menu.MENU_SETTING_TEXT.toString());
		menu.add(0, Constant.MENU_SWITCH_LANGUAGE, 0, Constant.Menu.MENU_SWITCH_LANGUAGE_TEXT.toString());
		menu.add(0, Constant.MENU_USER_REGIST, 0, Constant.Menu.MENU_USER_REGIST_TEXT.toString());
		menu.add(0, Constant.MENU_GROUP_REGIST, 0, Constant.Menu.MENU_GROUP_REGIST_TEXT.toString());
		menu.add(0, Constant.MENU_GROUP_PART, 0, Constant.Menu.MENU_GROUP_PART_TEXT.toString());
//		menu.add(0, Constant.MENU_GROUP_SEARCH, 0, Constant.Menu.MENU_GROUP_SEARCH_TEXT.toString());
		menu.add(0, Constant.MENU_ROUTE_SEARCH, 0, Constant.Menu.MENU_ROUTE_SEARCH_TEXT.toString()); // �o�H����
		Log.d(TAG, "onCreateOptionsMenu : " + "End");
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem menuItem) {
		Log.d(TAG, "onOptionsItemSelected : " + "Start");

		Intent intent = null;
		switch (menuItem.getItemId()) {
		case Constant.MENU_CHECK_USER_INFO:
			Log.d(TAG, "MENU_START : " + "Start");
			intent = new Intent(MainActivity.this, CheckUserInfoActivity.class);
			startActivity(intent);
			Log.d(TAG, "MENU_START : " + "End");
			return true;
		 case Constant.MENU_SETTING:
			 Log.d(TAG, "MENU_SETTING : " + "Start");
			 intent = new Intent(MainActivity.this, SettingActivity.class);
			 startActivity(intent);
			 Log.d(TAG, "MENU_SETTING : " + "End");
			 return true;
		case Constant.MENU_USER_REGIST:
			Log.d(TAG, "MENU_USER_REGIST : " + "Start");
			if (MainActivity.mIsSharedPreferences == true) {
				Toast.makeText(this, "Is Registered Users", Toast.LENGTH_SHORT).show();
			} else {
				stopService(new Intent(getBaseContext(), SendRequestTimerService.class));
				// ���[�U�[�o�^����
				intent = new Intent(MainActivity.this, RegistUserActivity.class);
				startActivity(intent);
			}
			Log.d(TAG, "MENU_USER_REGIST : " + "End");
			return true;
		case Constant.MENU_GROUP_REGIST:
			Log.d(TAG, "MENU_GROUP_REGIST : " + "Start");
			if (MainActivity.mIsSharedPreferences == true) {
				stopService(new Intent(MainActivity.mContext, SendRequestTimerService.class));
				intent = new Intent(MainActivity.this, RegistGroupActivity.class);
				startActivity(intent);
			} else {
				Toast.makeText(this, "Unregistered User", Toast.LENGTH_SHORT).show();
			}
			Log.d(TAG, "MENU_GROUP_REGIST : " + "End");
			return true;
		case Constant.MENU_GROUP_PART:
			Log.d(TAG, "MENU_GROUP_PART : " + "Start");
			if (MainActivity.mIsSharedPreferences == true) {
				stopService(new Intent(MainActivity.mContext, SendRequestTimerService.class));
				intent = new Intent(MainActivity.this, PartGroupActivity.class);
				startActivity(intent);
			} else {
				Toast.makeText(this, "Unregistered User", Toast.LENGTH_SHORT).show();
			}
			Log.d(TAG, "MENU_GROUP_PART : " + "End");
			return true;
//		case Constant.MENU_GROUP_SEARCH:
//			Log.d(TAG, "MENU_GROUP_SEARCH : " + "Start");
//			if (MainActivity.mIsSharedPreferences == true) {
//				stopService(new Intent(MainActivity.mContext, SendRequestTimerService.class));
//				intent = new Intent(MainActivity.this, SearchGroupActivity.class);
//				startActivity(intent);
//			} else {
//				Toast.makeText(this, "Unregistered User", Toast.LENGTH_SHORT).show();
//			}
//			Log.d(TAG, "MENU_GROUP_SEARCH : " + "End");
//			return true;
		case Constant.MENU_ROUTE_SEARCH:
			Log.d(TAG, "MENU_ROUTE_SEARCH : " + "Start");
			stopService(new Intent(MainActivity.mContext, SendRequestTimerService.class));
			intent = new Intent(MainActivity.this, RouteSearchActivity.class);
			startActivity(intent);
			Log.d(TAG, "MENU_ROUTE_SEARCH : " + "End");
			return true;
		case Constant.MENU_SWITCH_LANGUAGE:
			Log.d(TAG, "MENU_START : " + "Start");
			// ����ؑփ_�C�A���O�\������
			switchLanguageDialog();
			Log.d(TAG, "MENU_START : " + "End");
			return true;
		}
		Log.d(TAG, "onOptionsItemSelected : " + "End");
		return false;
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.d(TAG, "onActivityResult : " + "Start");
		Log.d(TAG, "onActivityResult : " + "End");
	}

	/**
	 * �{�^����������
	 */
	private final OnClickListener action = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Log.d(TAG, "onClick : " + "Start");
			Intent intent = null;
			switch (v.getId()) {
			case R.id.button_Main_Search:
				Log.d(TAG, "button_TetsLogin : " + "Start");
				intent = new Intent(MainActivity.this, SearchActivity.class);
				startActivity(intent);
				// finish();
				Log.d(TAG, "button_TetsLogin : " + "End");
				break;
			case R.id.button_Main_Itinerary:
				Log.d(TAG, "button_Main_Itinerary : " + "Start");
//				intent = new Intent(MainActivity.this, ItineraryActivity.class);
//				startActivity(intent);
				Log.d(TAG, "button_Main_Itinerary : " + "End");
				break;
			case R.id.button_Main_Root:
				Log.d(TAG, "button_Main_Root : " + "Start");
				intent = new Intent(MainActivity.this, RootActivity.class);
				startActivity(intent);
				Log.d(TAG, "button_Main_Root : " + "End");
				break;
			case R.id.button_Main_Hotel:
				Log.d(TAG, "button_Main_Hotel : " + "Start");
				intent = new Intent(MainActivity.this, HotelActivity.class);
				startActivity(intent);
				Log.d(TAG, "button_Main_Hotel : " + "End");
				break;
			case R.id.button_Main_Spot:
				Log.d(TAG, "button_Main_Spot : " + "Start");
				intent = new Intent(MainActivity.this, SpotActivity.class);
				startActivity(intent);
				Log.d(TAG, "button_Main_Spot : " + "End");
				break;
			case R.id.button_Main_Shopping:
				Log.d(TAG, "button_Main_Shopping : " + "Start");
				intent = new Intent(MainActivity.this, ShoppingActivity.class);
				startActivity(intent);
				Log.d(TAG, "button_Main_Shopping : " + "End");
				break;
			case R.id.button_Main_Restaurant:
				Log.d(TAG, "button_Main_Restaurant : " + "Start");
				intent = new Intent(MainActivity.this, RestaurantActivity.class);
				startActivity(intent);
				Log.d(TAG, "button_Main_Restaurant : " + "End");
				break;
			case R.id.button_Main_Call:
				Log.d(TAG, "button_Main_Call : " + "Start");
				intent = new Intent(MainActivity.this, WalkieTalkieActivity.class);
				startActivity(intent);
				Log.d(TAG, "button_Main_Call : " + "End");
				break;
			case R.id.button_Main_Weather:
				Log.d(TAG, "button_Main_Weather : " + "Start");
				intent = new Intent(MainActivity.this, ImaSoraActivity.class);
				startActivity(intent);
				Log.d(TAG, "button_Main_Weather : " + "End");
				break;
			}
			Log.d(TAG, "onClick : " + "End");
		}
	};

	/**
	 * ���[�U�[�o�^�m�F����
	 * 
	 * @return boolean
	 */
	public static boolean checkUserRegist() {
		Log.d("MainActivity", "checkUserRegist : " + "Start");
		Map<String, String> map = new HashMap<String, String>();
		map = getSharedPreferences_UserInfo(mContext); // (���[�U�[���)�v���t�@�����X�Ǎ�����

		if (map.get(Constant.SharedPreferences.SP_EDIT_ID.toString()) == null) {
			mIsSharedPreferences = false;
			Toast.makeText(mContext, "checkUserRegist : Not Found User", Toast.LENGTH_SHORT).show();
		} else {
			mIsSharedPreferences = true;
			if (map.get(Constant.SharedPreferences.SP_EDIT_ID.toString()) == null) {
				Toast.makeText(mContext, "checkUserRegist : Not Found User", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(mContext, "checkUserRegist : User Registed", Toast.LENGTH_SHORT).show();
				UserInfo.setID(map.get(Constant.SharedPreferences.SP_EDIT_ID.toString()));
				UserInfo.setUserID(map.get(Constant.SharedPreferences.SP_EDIT_USER_ID.toString()));
				UserInfo.setUserName(map.get(Constant.SharedPreferences.SP_EDIT_USER_NAME.toString()));
				UserInfo.setUserGender(map.get(Constant.SharedPreferences.SP_EDIT_USER_GENDER.toString()));
				UserInfo.setUserAge(map.get(Constant.SharedPreferences.SP_EDIT_USER_AGE.toString()));
				UserInfo.setUserTel(map.get(Constant.SharedPreferences.SP_EDIT_USER_TEL.toString()));
			}
		}
		Log.d("MainActivity", "checkUserRegist : " + "End");
		return mIsSharedPreferences;
	}

	/**
	 * (���[�U�[���)�v���t�@�����X�Ǎ�����
	 * 
	 * @param context
	 * @return AccessToken
	 */
	public static Map<String, String> getSharedPreferences_UserInfo(Context context) {
		Log.d("MainActivity", "getSharedPreferences_UserInfo: " + "Start");
		SharedPreferences preferences = MainActivity.mContext.getSharedPreferences(Constant.SharedPreferences.SP_USER.toString(), Context.MODE_PRIVATE);
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.SharedPreferences.SP_EDIT_ID.toString(), preferences.getString(Constant.SharedPreferences.SP_EDIT_ID.toString(), null));
		map.put(Constant.SharedPreferences.SP_EDIT_USER_ID.toString(), preferences.getString(Constant.SharedPreferences.SP_EDIT_USER_ID.toString(), null));
		map.put(Constant.SharedPreferences.SP_EDIT_USER_NAME.toString(), preferences.getString(Constant.SharedPreferences.SP_EDIT_USER_NAME.toString(), null));
		map.put(Constant.SharedPreferences.SP_EDIT_USER_GENDER.toString(), preferences.getString(Constant.SharedPreferences.SP_EDIT_USER_GENDER.toString(), null));
		map.put(Constant.SharedPreferences.SP_EDIT_USER_AGE.toString(), preferences.getString(Constant.SharedPreferences.SP_EDIT_USER_AGE.toString(), null));
		map.put(Constant.SharedPreferences.SP_EDIT_USER_TEL.toString(), preferences.getString(Constant.SharedPreferences.SP_EDIT_USER_TEL.toString(), null));
		Log.d("MainActivity", "getSharedPreferences_UserInfo: " + "End");
		return map;
	}

	/**
	 * Toast�\������
	 */
	public void dispToast(String message) {
		Log.d(TAG, "dispToast : " + "Start");
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		Log.d(TAG, "dispToast : " + "End");
	}

	/**
	 * ����ؑփ_�C�A���O�\������
	 */
	public static void switchLanguageDialog() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
		alertDialogBuilder.setTitle("����ؑ�");
		// alertDialogBuilder.setMessage("���b�Z�[�W");
		alertDialogBuilder.setPositiveButton("Thai Language", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mSwitchLanguage = 1;
			}
		});
		 alertDialogBuilder.setNeutralButton("English Language", new DialogInterface.OnClickListener() {
		 @Override
		 public void onClick(DialogInterface dialog, int which) {
			 mSwitchLanguage = 0;
		 }
		 });
		alertDialogBuilder.setNegativeButton("Japanese Language", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mSwitchLanguage = 2;
			}
		});
		alertDialogBuilder.setCancelable(true);
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	// ----------------------------------------------------------------------------------------------------
	/**
	 * ���[�U�[���N���X
	 */
	public static class UserInfo {
		private static String id; // ID
		private static String userID; // ���[�U�[ID
		private static String userName; // ���[�U�[��
		private static String userGender; // ���[�U�[����
		private static String userAge; // ���[�U�[�N��
		private static String userTel; // ���[�U�[�d�b�ԍ�

		public static String getID() {
			return id;
		}

		public static void setID(String _id) {
			id = _id;
		}

		public static String getUserID() {
			return userID;
		}

		public static void setUserID(String _userID) {
			userID = _userID;
		}

		public static String getUserName() {
			return userName;
		}

		public static void setUserName(String _userName) {
			userName = _userName;
		}

		public static String getUserGender() {
			return userGender;
		}

		public static void setUserGender(String _userGender) {
			userGender = _userGender;
		}

		public static String getUserAge() {
			return userAge;
		}

		public static void setUserAge(String _userAge) {
			userAge = _userAge;
		}

		public static String getUserTel() {
			return userTel;
		}

		public static void setUserTel(String _userTel) {
			userTel = _userTel;
		}
	}
}
