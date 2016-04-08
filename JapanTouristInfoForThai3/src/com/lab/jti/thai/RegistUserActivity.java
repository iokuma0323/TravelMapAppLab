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
 * ���[�U�[�o�^�A�N�e�B�r�e�B
 * 
 */
@SuppressLint("SimpleDateFormat")
public class RegistUserActivity extends Activity {

	private final String TAG = "UserRegistActivity"; // �^�O
	private final String GENDER_MEN_TEXT = "�j��"; // ���ʃe�L�X�g �j��
	private final String GENDER_FEMALE_TEXT = "����"; // ���ʃe�L�X�g ����
	private final String GENDER_MEN_VALUE = "M"; // ���� �j��
	private final String GENDER_FEMALE_VALUE = "F"; // ���� ����
	private EditText mEditText_Name; // ���[�U�[���e�L�X�g
	private RadioGroup mRadioGroup_Gender; // ���ʃ��W�I�O���[�v
	private RadioButton mRadioButton_GenderSelect; // ���ʃ��W�I�{�^�� �I��
	private NumberPicker mNumberPicker_Age_1; // �N��s�b�J�[�i1�̈ʁj
	private NumberPicker mNumberPicker_Age_10; // �N��s�b�J�[�i10�̈ʁj
	private EditText mEditText_Tel; // ���[�U�[�d�b�ԍ�
	private Button mBtn_RegistUser; // ���[�U�[�o�^�{�^��
	private RequestQueue mRequestQueue; // ���N�G�X�g�L���[
	private String mResJson = null; // ���X�|���XJSON
//	private String mGender; // ����
//	private String mAge; // �N��
	private String[] strNumberPicker_Age_1 = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" }; // �N��i1�̈ʁj
	private String[] strNumberPicker_Age_10 = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" }; // �N��i10�̈ʁj
	private RegistUserAPI_ResponseInfo mRegistUserAPI_ResponseInfo;	// RegistUserAPI �ԋp�l�i�[�N���X

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.regist_user);
		Log.d(TAG, "onCreate: " + "Start");
		mEditText_Name = (EditText) findViewById(R.id.editText_RegistUser_UserName);
		mRadioGroup_Gender = (RadioGroup) findViewById(R.id.radioGroup_RegistUser_Gender);
		mRadioButton_GenderSelect = (RadioButton) findViewById(mRadioGroup_Gender.getCheckedRadioButtonId());
		mRadioGroup_Gender.setOnCheckedChangeListener(changeGender); // ���ʃ��W�I�{�^���ύX������
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
		mBtn_RegistUser.setOnClickListener(registUser); // ���[�U�[�o�^�{�^����������
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
	 * ���[�U�[�o�^�{�^����������
	 */
	private final OnClickListener registUser = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Log.d(TAG, "onClick : " + "Start");
			registUser(); // ���[�U�[���o�^����
			Map<String, String> map = new HashMap<String, String>();
			map = MainActivity.getSharedPreferences_UserInfo(getApplicationContext());	// (���[�U�[���)�v���t�@�����X�Ǎ�����
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
	 * ���ʃ��W�I�{�^���ύX������
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
	 * ���[�U�[�o�^����
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
		Log.d(TAG, "���[�U�[ID : " + userID);
		Log.d(TAG, "���[�U�[�� : " + userName);
		Log.d(TAG, "���� : " + gender);
		Log.d(TAG, "�N�� : " + age);
		
		if (!Util.isEmpty(userName)) {
			message = "���[�U�[������͂��ĉ�����";
		}
		if (!Util.isEmpty(gender)) {
			message = "���ʂ�I�����ĉ�����";
		}
		if (!Util.isEmpty(age)) {
			message = "�N���I�����ĉ�����";
		}
		if (!Util.isEmpty(tel)) {
			message = "�d�b�ԍ�����͂��ĉ�����";
//		} else if (!Util.validatePhoneNumberWithOutSeparator()) {
//			message = "�d�b�ԍ�����͂��ĉ�����";
//		} else if (!Util.validatePhoneNumber()) {
//			message = "�d�b�ԍ�����͂��ĉ�����";
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
					analysisGson_RegistUser();	//�i���[�U�[�o�^���ʁjJSON��͏���
					putSharedPreferences_UserInfo(); //�i���[�U�[�o�^���ʁj�v���t�@�����X�ۑ�����
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
	 * �i���[�U�[�o�^���ʁjJSON��͏���
	 */
	private void analysisGson_RegistUser() {
		
		// TODO 20140214
		// �ԋp�l���@{"response":{"result":{"id":"35","gender":"M","user_name":"tedt","tel":"0801234567","age":"11"},"status":{"message":"message_test","code":2000}}}
		// �ɂ��Ă��炤�B
		Log.d(TAG, "analysisGson_Contents : " + "Start");
		try {
			JsonResult_RegistUser sampleResult = new JsonResult_RegistUser();
			sampleResult = new Gson().fromJson(mResJson, JsonResult_RegistUser.class);
			sampleResult.response.get(Constant.MapKey.MAP_KEY_STATUS);
			mRegistUserAPI_ResponseInfo = new RegistUserAPI_ResponseInfo();
			mRegistUserAPI_ResponseInfo.setCode(sampleResult.response.get(Constant.MapKey.MAP_KEY_STATUS.toString()).get(Constant.MapKey.MAP_KEY_CODE.toString())); // ���ʃX�e�[�^�X
			mRegistUserAPI_ResponseInfo.setMessage(sampleResult.response.get(Constant.MapKey.MAP_KEY_STATUS.toString()).get(Constant.MapKey.MAP_KEY_MESSAGE.toString())); // ���ʃ��b�Z�[�W
			mRegistUserAPI_ResponseInfo.setId(sampleResult.response.get(Constant.MapKey.MAP_KEY_RESULT.toString()).get(Constant.MapKey.MAP_KEY_ID.toString())); // ID
			mRegistUserAPI_ResponseInfo.setUserID(sampleResult.response.get(Constant.MapKey.MAP_KEY_RESULT.toString()).get(Constant.MapKey.MAP_KEY_USER_ID.toString())); // ���[�U�[ID
			mRegistUserAPI_ResponseInfo.setUserName(sampleResult.response.get(Constant.MapKey.MAP_KEY_RESULT.toString()).get(Constant.MapKey.MAP_KEY_USER_NAME.toString())); // ���[�U�[��
			mRegistUserAPI_ResponseInfo.setUserGender(sampleResult.response.get(Constant.MapKey.MAP_KEY_RESULT.toString()).get(Constant.MapKey.MAP_KEY_GENDER.toString())); // ����
			mRegistUserAPI_ResponseInfo.setUserAge(sampleResult.response.get(Constant.MapKey.MAP_KEY_RESULT.toString()).get(Constant.MapKey.MAP_KEY_AGE.toString())); // �N��
			mRegistUserAPI_ResponseInfo.setUserTel(sampleResult.response.get(Constant.MapKey.MAP_KEY_RESULT.toString()).get(Constant.MapKey.MAP_KEY_TEL.toString())); // �d�b�ԍ�
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(MainActivity.mContext, "analysisGson_RegistUser - Miss :" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
		}
		Log.d(TAG, "analysisGson_Contents : " + "End");
	}

	/**
	 * ���[�U�[ID�̔ԏ���
	 */
	private String getNumberingUserID() {
		Log.d(TAG, "getNumberingUserID : " + "Start");
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmsss");
		Log.d(TAG, "getNumberingUserID : " + "End");
		return sdf.format(date);
	}

	/**
	 * �i���[�U�[�o�^���ʁj�v���t�@�����X�ۑ�����
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
	 * RegistUserAPI �ԋp�l�i�[�N���X
	 */
    public static class RegistUserAPI_ResponseInfo {
    	private String code; // ���ʃX�e�[�^�X
    	private String message; // ���ʃ��b�Z�[�W
    	private String id; // ID
    	private String userID; // ���[�U�[ID
    	private String userName; // ���[�U�[��
    	private String userGender; // ����
    	private String userAge; // �N��
    	private String userTel; // �d�b�ԍ�
    	
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
     * (ResultUser)Json�ԋp�l�i�[�N���X
     */
    public class JsonResult_RegistUser {
    	public HashMap<String, HashMap<String, String>> response;
    }
}
