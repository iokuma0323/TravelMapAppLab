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
 * グループ登録アクティビティ
 * 
 */
public class RegistGroupActivity extends Activity {

	private final String TAG = "GroupRegistActivity"; // タグ
	
	private static final String SP_REGIST_GROUP = "SharedPreferences_RegistGroup"; // SharedPreferencesKey:作成グループ
	static final String SP_EDIT_GROUP_ID = "SPE_GROUP_ID"; // SharedPreferencesEditKey:グループID
	static final String SP_EDIT_GROUP_LEADER_ID = "SPE_GROUP_LEADER_ID"; // SharedPreferencesEditKey:グループリーダーID
	static final String SP_EDIT_GROUP_NAME = "SPE_GROUP_NAME"; // SharedPreferencesEditKey:グループ名
	
	private EditText mEditText_GroupName; // グループ名テキスト
	private Button mBtn_RegistGroup; // グループ登録ボタン
	private RequestQueue mRequestQueue; // リクエストキュー
	private String mResJson = null; // レスポンスJSON

	private RegistGroupAPI_ResponseInfo mRegistGroupAPI_ResponseInfo;	// グループ情報クラス

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.regist_group);
		Log.d(TAG, "onCreate: " + "Start");
		mEditText_GroupName = (EditText) findViewById(R.id.editText_RegistGroup_GroupName);
		mBtn_RegistGroup = (Button) findViewById(R.id.button_RegistGroup);
		mBtn_RegistGroup.setOnClickListener(groupRegist); // グループ登録ボタン押下処理
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
	 * グループ登録ボタン押下処理
	 */
	private final OnClickListener groupRegist = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Log.d(TAG, "onClick : " + "Start");
			registGroup(); // グループ情報登録処理
			Intent intent = new Intent(RegistGroupActivity.this, MapActivity.class);
			Log.d(TAG, "onClick : " + "End");
			startActivity(intent);
			finish();
		}
	};

	/**
	 * グループ情報登録処理
	 */
	private void registGroup() {
		Log.d(TAG, "registUserInfo : " + "Start");
		mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		String groupName = mEditText_GroupName.getText().toString();
		Log.d(TAG, "ユーザーID : " + MainActivity.UserInfo.getUserID());
		Log.d(TAG, "グループ名 : " + groupName);
		// TODO 未決(20140210)
		String url = "http://dev.airdocs.jp/his/addgroup.php?user_id=" + MainActivity.UserInfo.getUserID() + "&group_name=" + groupName;
		mRequestQueue.add(new JsonObjectRequest(Method.GET, url, null, new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				Log.d(TAG, "onResponse : " + "Start");
				mResJson = response.toString();
				analysisGson_RegistGroup();	// （グループ作成結果）JSON解析処理
//				putSharedPreferences_GroupInfo(MainActivity.mContext); // プリファレンス保存処理
				finish();
				Log.d(TAG, "onResponse : " + "End");
			}
		}, null));
		Log.d(TAG, "registUserInfo : " + "End");
	}
	
	/**
	 * （ユーザー登録結果）JSON解析処理
	 */
	private void analysisGson_RegistGroup() {
		Log.d(TAG, "analysisGson_Contents : " + "Start");
		JsonResult_RegistGroup sampleResult = new JsonResult_RegistGroup();
		sampleResult = new Gson().fromJson(mResJson, JsonResult_RegistGroup.class);
		sampleResult.response.get(Constant.MapKey.MAP_KEY_STATUS);
		
		mRegistGroupAPI_ResponseInfo = new RegistGroupAPI_ResponseInfo();
		mRegistGroupAPI_ResponseInfo.setCode(sampleResult.response.get(RegistGroupAPI_ResponseInfo.STATUS).get(RegistGroupAPI_ResponseInfo.CODE).toString()); // 結果ステータス
		mRegistGroupAPI_ResponseInfo.setMessage(sampleResult.response.get(RegistGroupAPI_ResponseInfo.STATUS).get(RegistGroupAPI_ResponseInfo.MESSAGE).toString()); // 結果メッセージ
		mRegistGroupAPI_ResponseInfo.setGroupID(sampleResult.response.get(RegistGroupAPI_ResponseInfo.RESULT).get(RegistGroupAPI_ResponseInfo.GROUP_ID).toString());// グループID
		mRegistGroupAPI_ResponseInfo.setGroupLeaderID(sampleResult.response.get(RegistGroupAPI_ResponseInfo.RESULT).get(RegistGroupAPI_ResponseInfo.GROUP_LEADER_ID).toString());// グループリーダーID
		mRegistGroupAPI_ResponseInfo.setGroupName(sampleResult.response.get(RegistGroupAPI_ResponseInfo.RESULT).get(RegistGroupAPI_ResponseInfo.GROUP_NAME).toString());// グループ名
		Log.d(TAG, "analysisGson_Contents : " + "End");
	}
	
	/**
	 * （グループ結果）プリファレンス保存処理
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
	 * グループ情報クラス
	 */
    public class RegistGroupAPI_ResponseInfo {
    	
    	public static final String RESPONSE = "response"; // レスポンス
    	public static final String STATUS = "status"; // ステータス
    	public static final String RESULT = "result"; // リザルト
    	
    	public static final String CODE = "code"; // （レスポンス）コード
    	public static final String MESSAGE = "message";// （レスポンス）コンテンツメッセージ
    	public static final String GROUP_ID = "group_id";// グループID
    	public static final String GROUP_LEADER_ID = "group_leader_id";// グループリーダーID
    	public static final String GROUP_NAME = "group_name";// グループ名

    	private String code; // 結果ステータス
    	private String message; // 結果メッセージ
    	private String groupID; // グループID
    	private String groupLeaderID; // グループリーダーID
    	private String groupName; // グループ名

    	
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
     * (ResultGroup)Json返却値格納クラス
     *
     */
    public class JsonResult_RegistGroup {
    	public HashMap<String, HashMap<String, Object>> response;
    }
}
