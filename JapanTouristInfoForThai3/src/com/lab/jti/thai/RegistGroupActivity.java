package com.lab.jti.thai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.lab.jti.thai.MapActivity.ContentsInfo;
import com.lab.jti.thai.RegistUserActivity.RegistUserAPI_ResponseInfo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * �O���[�v�o�^�A�N�e�B�r�e�B
 * 
 */
public class RegistGroupActivity extends Activity {

	private final String TAG = "GroupRegistActivity"; // �^�O
	
	private static final String SP_REGIST_GROUP = "SharedPreferences_RegistGroup"; // SharedPreferencesKey:�쐬�O���[�v
	static final String SP_EDIT_GROUP_ID = "SPE_GROUP_ID"; // SharedPreferencesEditKey:�O���[�vID
	static final String SP_EDIT_GROUP_LEADER_ID = "SPE_GROUP_LEADER_ID"; // SharedPreferencesEditKey:�O���[�v���[�_�[ID
	static final String SP_EDIT_GROUP_NAME = "SPE_GROUP_NAME"; // SharedPreferencesEditKey:�O���[�v��
	
	private EditText mEditText_GroupName; // �O���[�v���e�L�X�g
	private Button mBtn_RegistGroup; // �O���[�v�o�^�{�^��
	private RequestQueue mRequestQueue; // ���N�G�X�g�L���[
	private String mResJson = null; // ���X�|���XJSON

	private RegistGroupAPI_ResponseInfo mRegistGroupAPI_ResponseInfo;	// �O���[�v���N���X

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.regist_group);
		Log.d(TAG, "onCreate: " + "Start");
		mEditText_GroupName = (EditText) findViewById(R.id.editText_RegistGroup_GroupName);
		mBtn_RegistGroup = (Button) findViewById(R.id.button_RegistGroup);
		mBtn_RegistGroup.setOnClickListener(groupRegist); // �O���[�v�o�^�{�^����������
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
	 * �O���[�v�o�^�{�^����������
	 */
	private final OnClickListener groupRegist = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Log.d(TAG, "onClick : " + "Start");
			registGroup(); // �O���[�v���o�^����
			Intent intent = new Intent(RegistGroupActivity.this, MapActivity.class);
			Log.d(TAG, "onClick : " + "End");
			startActivity(intent);
			finish();
		}
	};

	/**
	 * �O���[�v���o�^����
	 */
	private void registGroup() {
		Log.d(TAG, "registUserInfo : " + "Start");
		mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		String groupName = mEditText_GroupName.getText().toString();
		Log.d(TAG, "���[�U�[ID : " + MainActivity.UserInfo.getUserID());
		Log.d(TAG, "�O���[�v�� : " + groupName);
		// TODO ����(20140210)
		String url = "http://dev.airdocs.jp/his/addgroup.php?user_id=" + MainActivity.UserInfo.getUserID() + "&group_name=" + groupName;
		mRequestQueue.add(new JsonObjectRequest(Method.GET, url, null, new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				Log.d(TAG, "onResponse : " + "Start");
				mResJson = response.toString();
				analysisGson_RegistGroup();	// �i�O���[�v�쐬���ʁjJSON��͏���
//				putSharedPreferences_GroupInfo(MainActivity.mContext); // �v���t�@�����X�ۑ�����
				finish();
				Log.d(TAG, "onResponse : " + "End");
			}
		}, null));
		Log.d(TAG, "registUserInfo : " + "End");
	}
	
	/**
	 * �i���[�U�[�o�^���ʁjJSON��͏���
	 */
	private void analysisGson_RegistGroup() {
		Log.d(TAG, "analysisGson_Contents : " + "Start");
		JsonResult_RegistGroup sampleResult = new JsonResult_RegistGroup();
		sampleResult = new Gson().fromJson(mResJson, JsonResult_RegistGroup.class);
		sampleResult.response.get(Constant.MapKey.MAP_KEY_STATUS);
		
		mRegistGroupAPI_ResponseInfo = new RegistGroupAPI_ResponseInfo();
		mRegistGroupAPI_ResponseInfo.setCode(sampleResult.response.get(RegistGroupAPI_ResponseInfo.STATUS).get(RegistGroupAPI_ResponseInfo.CODE).toString()); // ���ʃX�e�[�^�X
		mRegistGroupAPI_ResponseInfo.setMessage(sampleResult.response.get(RegistGroupAPI_ResponseInfo.STATUS).get(RegistGroupAPI_ResponseInfo.MESSAGE).toString()); // ���ʃ��b�Z�[�W
		mRegistGroupAPI_ResponseInfo.setGroupID(sampleResult.response.get(RegistGroupAPI_ResponseInfo.RESULT).get(RegistGroupAPI_ResponseInfo.GROUP_ID).toString());// �O���[�vID
		mRegistGroupAPI_ResponseInfo.setGroupLeaderID(sampleResult.response.get(RegistGroupAPI_ResponseInfo.RESULT).get(RegistGroupAPI_ResponseInfo.GROUP_LEADER_ID).toString());// �O���[�v���[�_�[ID
		mRegistGroupAPI_ResponseInfo.setGroupName(sampleResult.response.get(RegistGroupAPI_ResponseInfo.RESULT).get(RegistGroupAPI_ResponseInfo.GROUP_NAME).toString());// �O���[�v��
		Log.d(TAG, "analysisGson_Contents : " + "End");
	}
	
	/**
	 * �i�O���[�v���ʁj�v���t�@�����X�ۑ�����
	 * 
	 * @param context
	 * @param accessToken
	 */
	private void putSharedPreferences_GroupInfo(Context context) {
		Log.d(TAG, "putSharedPreferences_UserID: " + "Start");
		SharedPreferences sharedPreferences = context.getSharedPreferences(SP_REGIST_GROUP, Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString(SP_EDIT_GROUP_ID, mRegistGroupAPI_ResponseInfo.getGroupID());
		editor.putString(SP_EDIT_GROUP_LEADER_ID, mRegistGroupAPI_ResponseInfo.getGroupLeaderID());
		editor.putString(SP_EDIT_GROUP_NAME, mRegistGroupAPI_ResponseInfo.getGroupName());
		editor.commit();
		Log.d(TAG, "putSharedPreferences_UserID: " + "End");
	}
	
	/**
	 * �O���[�v���N���X
	 */
    public class RegistGroupAPI_ResponseInfo {
    	
    	public static final String RESPONSE = "response"; // ���X�|���X
    	public static final String STATUS = "status"; // �X�e�[�^�X
    	public static final String RESULT = "result"; // ���U���g
    	
    	public static final String CODE = "code"; // �i���X�|���X�j�R�[�h
    	public static final String MESSAGE = "message";// �i���X�|���X�j�R���e���c���b�Z�[�W
    	public static final String GROUP_ID = "group_id";// �O���[�vID
    	public static final String GROUP_LEADER_ID = "group_leader_id";// �O���[�v���[�_�[ID
    	public static final String GROUP_NAME = "group_name";// �O���[�v��

    	private String code; // ���ʃX�e�[�^�X
    	private String message; // ���ʃ��b�Z�[�W
    	private String groupID; // �O���[�vID
    	private String groupLeaderID; // �O���[�v���[�_�[ID
    	private String groupName; // �O���[�v��

    	
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

    	public String getGroupID() {
    		return groupID;
    	}

    	public void setGroupID(String groupID) {
    		this.groupID = groupID;
    	}

    	public String getGroupLeaderID() {
    		return groupLeaderID;
    	}

    	public void setGroupLeaderID(String groupLeaderID) {
    		this.groupLeaderID = groupLeaderID;
    	}

    	public String getGroupName() {
    		return groupName;
    	}

    	public void setGroupName(String groupName) {
    		this.groupName = groupName;
    	}
    }
    
    /**
     * (ResultGroup)Json�ԋp�l�i�[�N���X
     *
     */
    public class JsonResult_RegistGroup {
    	public HashMap<String, HashMap<String, Object>> response;
    }
}
