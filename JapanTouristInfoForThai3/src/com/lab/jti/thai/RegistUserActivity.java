package com.lab.jti.thai;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lab.jti.thai.MapActivity.ContentsInfo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * ユーザー登録アクティビティ
 * 
 */
@SuppressLint("SimpleDateFormat")
public class RegistUserActivity extends Activity {

	private final String TAG = "UserRegistActivity"; // タグ
	private final String GENDER_MEN_TEXT = "男性"; // 性別テキスト 男性
	private final String GENDER_FEMALE_TEXT = "女性"; // 性別テキスト 女性
	private final String GENDER_MEN_VALUE = "M"; // 性別 男性
	private final String GENDER_FEMALE_VALUE = "F"; // 性別 女性
	private EditText mEditText_Name; // ユーザー名テキスト
	private RadioGroup mRadioGroup_Gender; // 性別ラジオグループ
	private RadioButton mRadioButton_GenderSelect; // 性別ラジオボタン 選択
	private NumberPicker mNumberPicker_Age_1; // 年代ピッカー（1の位）
	private NumberPicker mNumberPicker_Age_10; // 年代ピッカー（10の位）
	private EditText mEditText_Tel; // ユーザー電話番号
	private Button mBtn_RegistUser; // ユーザー登録ボタン
	private RequestQueue mRequestQueue; // リクエストキュー
	private String mResJson = null; // レスポンスJSON
//	private String mGender; // 性別
//	private String mAge; // 年齢
	private String[] strNumberPicker_Age_1 = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" }; // 年齢（1の位）
	private String[] strNumberPicker_Age_10 = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" }; // 年齢（10の位）
	private RegistUserAPI_ResponseInfo mRegistUserAPI_ResponseInfo;	// RegistUserAPI 返却値格納クラス

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.regist_user);
		Log.d(TAG, "onCreate: " + "Start");
		mEditText_Name = (EditText) findViewById(R.id.editText_RegistUser_UserName);
		mRadioGroup_Gender = (RadioGroup) findViewById(R.id.radioGroup_RegistUser_Gender);
		mRadioButton_GenderSelect = (RadioButton) findViewById(mRadioGroup_Gender.getCheckedRadioButtonId());
		mRadioGroup_Gender.setOnCheckedChangeListener(changeGender); // 性別ラジオボタン変更時処理
		mNumberPicker_Age_1 = (NumberPicker) findViewById(R.id.numberPicker_RegistUser_Age1);
		mNumberPicker_Age_1.setMaxValue(strNumberPicker_Age_1.length - 1);
		mNumberPicker_Age_1.setMinValue(0);
		mNumberPicker_Age_1.setDisplayedValues(strNumberPicker_Age_1);
		mNumberPicker_Age_1.setFocusable(true);
		mNumberPicker_Age_1.setFocusableInTouchMode(true);
		mNumberPicker_Age_10 = (NumberPicker) findViewById(R.id.numberPicker_RegistUser_Age10);
		mNumberPicker_Age_10.setMaxValue(strNumberPicker_Age_10.length - 1);
		mNumberPicker_Age_10.setMinValue(0);
		mNumberPicker_Age_10.setDisplayedValues(strNumberPicker_Age_10);
		mNumberPicker_Age_10.setFocusable(true);
		mNumberPicker_Age_10.setFocusableInTouchMode(true);
		mEditText_Tel = (EditText) findViewById(R.id.editText_RegistUser_tel);
		mBtn_RegistUser = (Button) findViewById(R.id.button_RegistUser_Regist);
		mBtn_RegistUser.setOnClickListener(registUser); // ユーザー登録ボタン押下処理
		Log.d(TAG, "onCreate: " + "End");
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d(TAG, "onResume: " + "Start");
		Log.d(TAG, "onResume: " + "Start");
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.d(TAG, "onPause: " + "Start");
		Log.d(TAG, "onPause: " + "End");
	}

	/**
	 * ユーザー登録ボタン押下処理
	 */
	private final OnClickListener registUser = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Log.d(TAG, "onClick : " + "Start");
			registUser(); // ユーザー情報登録処理
			Map<String, String> map = new HashMap<String, String>();
			map = MainActivity.getSharedPreferences_UserInfo(getApplicationContext());	// (ユーザー情報)プリファレンス読込処理
			Log.d(TAG, "SP_EDIT_ID : " + map.get(Constant.SharedPreferences.SP_EDIT_ID.toString()));
			Log.d(TAG, "SP_EDIT_USER_ID : " + map.get(Constant.SharedPreferences.SP_EDIT_USER_ID.toString()));
			Log.d(TAG, "SP_EDIT_USER_NAME : " + map.get(Constant.SharedPreferences.SP_EDIT_USER_NAME.toString()));
			Log.d(TAG, "SP_EDIT_USER_GENDER : " + map.get(Constant.SharedPreferences.SP_EDIT_USER_GENDER.toString()));
			Log.d(TAG, "SP_EDIT_USER_AGE : " + map.get(Constant.SharedPreferences.SP_EDIT_USER_AGE.toString()));
			Log.d(TAG, "SP_EDIT_USER_TEL : " + map.get(Constant.SharedPreferences.SP_EDIT_USER_TEL.toString()));
			Log.d(TAG, "getSharedPreferences_UserInfo : " + "Start");
		}
	};

	/**
	 * 性別ラジオボタン変更時処理
	 */
	private final RadioGroup.OnCheckedChangeListener changeGender = new RadioGroup.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			Log.d(TAG, "onCheckedChanged : " + "Start");
			mRadioButton_GenderSelect = (RadioButton) findViewById(checkedId);
			Log.d(TAG, "onCheckedChanged : " + "End");
		}
	};

	/**
	 * ユーザー登録処理
	 */
	private void registUser() {
		Log.d(TAG, "registUserInfo : " + "Start");
		String message = null;
		mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		String userID = getNumberingUserID();
		String userName = mEditText_Name.getText().toString();
		String gender = mRadioButton_GenderSelect.getText().toString();
		if (mRadioButton_GenderSelect.getText().toString().equals(GENDER_MEN_TEXT)) {
			gender = GENDER_MEN_VALUE;
		} else if (mRadioButton_GenderSelect.getText().toString().equals(GENDER_FEMALE_TEXT)) {
			gender = GENDER_FEMALE_VALUE;
		} else {
			
		}
		String age1 = String.valueOf(strNumberPicker_Age_1[mNumberPicker_Age_1.getValue()]);
		String age10 = String.valueOf(strNumberPicker_Age_10[mNumberPicker_Age_10.getValue()]);
		String tel = mEditText_Tel.getText().toString();
		String age = age10 + age1;
		Log.d(TAG, "ユーザーID : " + userID);
		Log.d(TAG, "ユーザー名 : " + userName);
		Log.d(TAG, "性別 : " + gender);
		Log.d(TAG, "年齢 : " + age);
		
		if (!Util.isEmpty(userName)) {
			message = "ユーザー名を入力して下さい";
		}
		if (!Util.isEmpty(gender)) {
			message = "性別を選択して下さい";
		}
		if (!Util.isEmpty(age)) {
			message = "年齢を選択して下さい";
		}
		if (!Util.isEmpty(tel)) {
			message = "電話番号を入力して下さい";
//		} else if (!Util.validatePhoneNumberWithOutSeparator()) {
//			message = "電話番号を入力して下さい";
//		} else if (!Util.validatePhoneNumber()) {
//			message = "電話番号を入力して下さい";
		}
		
		
		if (!Util.isEmpty(message)) {
			String url = Constant.API.API_REGIST_USER + "?user_name=" + userName + "&gender=" + gender + "&age=" + age + "&tel=" + tel;
			
			mRequestQueue.add(new JsonObjectRequest(Method.GET, url, null, new Listener<JSONObject>() {
				@Override
				public void onResponse(JSONObject response) {
					Log.d(TAG, "onResponse : " + "Start");
					mResJson = response.toString();
					if (mResJson == null) {
						return;
					}
					analysisGson_RegistUser();	//（ユーザー登録結果）JSON解析処理
					putSharedPreferences_UserInfo(); //（ユーザー登録結果）プリファレンス保存処理
					MainActivity.mIsSharedPreferences = true;
					Log.d(TAG, "onResponse : " + "End");
				}
			}, null));
		} else {
			Toast.makeText(MainActivity.mContext, message, Toast.LENGTH_SHORT).show();
		}
		Log.d(TAG, "registUserInfo : " + "End");
		finish();
	}
	
	/**
	 * （ユーザー登録結果）JSON解析処理
	 */
	private void analysisGson_RegistUser() {
		
		// TODO 20140214
		// 返却値を　{"response":{"result":{"id":"35","gender":"M","user_name":"tedt","tel":"0801234567","age":"11"},"status":{"message":"message_test","code":2000}}}
		// にしてもらう。
		Log.d(TAG, "analysisGson_Contents : " + "Start");
		try {
			JsonResult_RegistUser sampleResult = new JsonResult_RegistUser();
			sampleResult = new Gson().fromJson(mResJson, JsonResult_RegistUser.class);
			sampleResult.response.get(Constant.MapKey.MAP_KEY_STATUS);
			mRegistUserAPI_ResponseInfo = new RegistUserAPI_ResponseInfo();
			mRegistUserAPI_ResponseInfo.setCode(sampleResult.response.get(Constant.MapKey.MAP_KEY_STATUS.toString()).get(Constant.MapKey.MAP_KEY_CODE.toString())); // 結果ステータス
			mRegistUserAPI_ResponseInfo.setMessage(sampleResult.response.get(Constant.MapKey.MAP_KEY_STATUS.toString()).get(Constant.MapKey.MAP_KEY_MESSAGE.toString())); // 結果メッセージ
			mRegistUserAPI_ResponseInfo.setId(sampleResult.response.get(Constant.MapKey.MAP_KEY_RESULT.toString()).get(Constant.MapKey.MAP_KEY_ID.toString())); // ID
			mRegistUserAPI_ResponseInfo.setUserID(sampleResult.response.get(Constant.MapKey.MAP_KEY_RESULT.toString()).get(Constant.MapKey.MAP_KEY_USER_ID.toString())); // ユーザーID
			mRegistUserAPI_ResponseInfo.setUserName(sampleResult.response.get(Constant.MapKey.MAP_KEY_RESULT.toString()).get(Constant.MapKey.MAP_KEY_USER_NAME.toString())); // ユーザー名
			mRegistUserAPI_ResponseInfo.setUserGender(sampleResult.response.get(Constant.MapKey.MAP_KEY_RESULT.toString()).get(Constant.MapKey.MAP_KEY_GENDER.toString())); // 性別
			mRegistUserAPI_ResponseInfo.setUserAge(sampleResult.response.get(Constant.MapKey.MAP_KEY_RESULT.toString()).get(Constant.MapKey.MAP_KEY_AGE.toString())); // 年齢
			mRegistUserAPI_ResponseInfo.setUserTel(sampleResult.response.get(Constant.MapKey.MAP_KEY_RESULT.toString()).get(Constant.MapKey.MAP_KEY_TEL.toString())); // 電話番号
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(MainActivity.mContext, "analysisGson_RegistUser - Miss :" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
		}
		Log.d(TAG, "analysisGson_Contents : " + "End");
	}

	/**
	 * ユーザーID採番処理
	 */
	private String getNumberingUserID() {
		Log.d(TAG, "getNumberingUserID : " + "Start");
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmsss");
		Log.d(TAG, "getNumberingUserID : " + "End");
		return sdf.format(date);
	}

	/**
	 * （ユーザー登録結果）プリファレンス保存処理
	 * 
	 * @param context
	 * @param accessToken
	 */
	@SuppressLint("SimpleDateFormat")
	private void putSharedPreferences_UserInfo() {
		Log.d(TAG, "putSharedPreferences_UserInfo: " + "Start");
		SharedPreferences sharedPreferences = MainActivity.mContext.getSharedPreferences(Constant.SharedPreferences.SP_USER.toString(), Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString(Constant.SharedPreferences.SP_EDIT_ID.toString(), mRegistUserAPI_ResponseInfo.getId());
		editor.putString(Constant.SharedPreferences.SP_EDIT_USER_ID.toString(), mRegistUserAPI_ResponseInfo.getUserID());
		editor.putString(Constant.SharedPreferences.SP_EDIT_USER_NAME.toString(), mRegistUserAPI_ResponseInfo.getUserName());
		editor.putString(Constant.SharedPreferences.SP_EDIT_USER_GENDER.toString(), mRegistUserAPI_ResponseInfo.getUserGender());
		editor.putString(Constant.SharedPreferences.SP_EDIT_USER_AGE.toString(), mRegistUserAPI_ResponseInfo.getUserAge());
		editor.putString(Constant.SharedPreferences.SP_EDIT_USER_TEL.toString(), mRegistUserAPI_ResponseInfo.getUserTel());
		editor.commit();
		Log.d(TAG, "putSharedPreferences_UserInfo: " + "End");
	}	
// ----------------------------------------------------------------------------------------------------
	/**
	 * RegistUserAPI 返却値格納クラス
	 */
    public static class RegistUserAPI_ResponseInfo {
    	private String code; // 結果ステータス
    	private String message; // 結果メッセージ
    	private String id; // ID
    	private String userID; // ユーザーID
    	private String userName; // ユーザー名
    	private String userGender; // 性別
    	private String userAge; // 年齢
    	private String userTel; // 電話番号
    	
    	public String getCode() {
    		return code;
    	}
    	public void setCode(String code) {
    		this.code = code;
    	}
    	public String getMessage() {
    		return message;
    	}
    	public void setMessage(String message) {
    		this.message = message;
    	}
    	public String getId() {
    		return id;
    	}
    	public void setId(String _id) {
    		this.id = _id;
    	}
    	public String getUserID() {
    		return userID;
    	}
    	public void setUserID(String _userID) {
    		this.userID = _userID;
    	}
    	public String getUserName() {
    		return userName;
    	}
    	public void setUserName(String _userName) {
    		this.userName = _userName;
    	}
    	public String getUserGender() {
    		return userGender;
    	}
    	public void setUserGender(String _userGender) {
    		this.userGender = _userGender;
    	}
    	public String getUserAge() {
    		return userAge;
    	}
    	public void setUserAge(String _userAge) {
    		this.userAge = _userAge;
    	}
    	public String getUserTel() {
    		return userTel;
    	}
    	public void setUserTel(String _userTel) {
    		this.userTel = _userTel;
    	}
    }
 // ----------------------------------------------------------------------------------------------------
    /**
     * (ResultUser)Json返却値格納クラス
     */
    public class JsonResult_RegistUser {
    	public HashMap<String, HashMap<String, String>> response;
    }
}
