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
 * メインアクティビティ ログイン画面
 */
public class MainActivity extends Activity {

	public static Context mContext = null; // コンテキスト
	public static boolean mIsSharedPreferences = false; // SharedPreferences（ユーザーデータ）存在フラグ
	public static int mSwitchLanguage = 0; // 言語きりかえ
	public static int mMeter = 1000; // Push半径メーター

	private final String TAG = "MainActivity"; // タグ
	private Button mBtn_Search; // Searchボタン
	private Button mBtn_Itinerary; // Itineraryボタン
	private Button mBtn_Root; // Rootボタン
	private Button mBtn_Hotel; // Hotelボタン
	private Button mBtn_Spot; // Spotボタン
	private Button mBtn_Shopping; // Shoppingボタン
	private Button mBtn_Restaurant; // Restaurantボタン
	private Button mBtn_Call; // Callボタン
	// private Button mBtn_Weather; // Weatherボタン
	// private Button mBtn_WifiSetting; // Wi-Fi Settingボタン
	// private Button mBtn_Guidance; // Guidanceボタン

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
		mBtn_Search.setOnClickListener(action); // Searchボタン押下処理
		mBtn_Itinerary.setOnClickListener(action); // Itineraryボタン押下処理
		mBtn_Root.setOnClickListener(action); // Moveボタン押下処理
		mBtn_Hotel.setOnClickListener(action);// Stayボタン押下処理
		mBtn_Spot.setOnClickListener(action); // Tourボタン押下処理
		mBtn_Shopping.setOnClickListener(action); // Shoppingボタン押下処理
		mBtn_Restaurant.setOnClickListener(action);// Eatボタン押下処理
		mBtn_Call.setOnClickListener(action); // Callボタン押下処理
		checkUserRegist(); // ユーザー登録確認処理
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
		menu.add(0, Constant.MENU_ROUTE_SEARCH, 0, Constant.Menu.MENU_ROUTE_SEARCH_TEXT.toString()); // 経路検索
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
				// ユーザー登録処理
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
			// 言語切替ダイアログ表示処理
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
	 * ボタン押下処理
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
	 * ユーザー登録確認処理
	 * 
	 * @return boolean
	 */
	public static boolean checkUserRegist() {
		Log.d("MainActivity", "checkUserRegist : " + "Start");
		Map<String, String> map = new HashMap<String, String>();
		map = getSharedPreferences_UserInfo(mContext); // (ユーザー情報)プリファレンス読込処理

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
	 * (ユーザー情報)プリファレンス読込処理
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
	 * Toast表示処理
	 */
	public void dispToast(String message) {
		Log.d(TAG, "dispToast : " + "Start");
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		Log.d(TAG, "dispToast : " + "End");
	}

	/**
	 * 言語切替ダイアログ表示処理
	 */
	public static void switchLanguageDialog() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
		alertDialogBuilder.setTitle("言語切替");
		// alertDialogBuilder.setMessage("メッセージ");
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
	 * ユーザー情報クラス
	 */
	public static class UserInfo {
		private static String id; // ID
		private static String userID; // ユーザーID
		private static String userName; // ユーザー名
		private static String userGender; // ユーザー性別
		private static String userAge; // ユーザー年齢
		private static String userTel; // ユーザー電話番号

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
