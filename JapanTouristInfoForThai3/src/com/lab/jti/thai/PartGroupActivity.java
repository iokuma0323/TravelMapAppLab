package com.lab.jti.thai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

/**
 * グループ参加アクティビティ
 *
 */
public class PartGroupActivity extends Activity {

	private final String TAG = "GroupPartActivity"; // タグ
	private String mResJson = null; // レスポンスJSON
	private RequestQueue mRequestQueue; // リクエストキュー
	private Button button_PartGroup_Back; // 検索戻るボタン
	private ListView mListViewGroupList;
	private GetGroupsAPI_ResponseInfo_Part mGetGroupsAPI_ResponseInfo_Part; // グループ情報取得クラス
	private PartGroupsAPI_ResponseInfo mPartGroupsAPI_ResponseInfo; // グループ参加クラス

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_group);
		Log.d(TAG, "onCreate: " + "Start");
		getGroupList();	// グループリスト取得処理
		button_PartGroup_Back = (Button)findViewById(R.id.button_SearchGroup_Back);
		button_PartGroup_Back.setOnClickListener(partGroup); // グループ参加 戻るボタン押下処理
		Log.d(TAG, "onCreate: " + "End");
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d(TAG, "onResume: " + "Start");
		Log.d(TAG, "onResume: " + "End");
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.d(TAG, "onPause: " + "Start");
		Log.d(TAG, "onPause: " + "End");
	}

	/**
	 * グループリスト取得処理
	 */
	private void getGroupList() {
		Log.d(TAG, "getGroupList : " + "Start");
		String url = Constant.API.API_GET_GROUPS.toString();
		mRequestQueue = Volley.newRequestQueue(MainActivity.mContext);
		mRequestQueue.add(new JsonObjectRequest(Method.GET, url, null,
				new Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d(TAG, "onResponse : " + "Start");
						mResJson = response.toString();
						analysisGson_GetGroups_Part(); // （グループ取得取得結果）JSON解析処理
						Log.d(TAG, "onResponse : " + "End");
					}
				},

				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						error.toString();
						error.getMessage();
						error.getLocalizedMessage();
						Log.d(TAG, "onResponse : " + "Start");
						
						String str = error.toString();
						int index = str.indexOf("of ");
						mResJson = str.substring(index + 2);
						analysisGson_GetGroups_Part(); // （グループ取得取得結果）JSON解析処理
					}
				}));
		Log.d(TAG, "registUserInfo : " + "End");
	}

	/**
	 * （グループ取得取得結果）JSON解析処理
	 */
	@SuppressWarnings("unchecked")
	private void analysisGson_GetGroups_Part() {
		Log.d(TAG, "analysisGson_GetGroups : " + "Start");
		JsonResult sampleResult = new JsonResult();
		sampleResult = new Gson().fromJson(mResJson, JsonResult.class);
		sampleResult.response.get(Constant.MapKey.MAP_KEY_STATUS.toString());
		mGetGroupsAPI_ResponseInfo_Part = new GetGroupsAPI_ResponseInfo_Part();
		mGetGroupsAPI_ResponseInfo_Part.setCode(sampleResult.response.get(Constant.MapKey.MAP_KEY_STATUS.toString()).get(Constant.MapKey.MAP_KEY_CODE.toString()).toString()); // 結果ステータス
		mGetGroupsAPI_ResponseInfo_Part.setMessage(sampleResult.response.get(Constant.MapKey.MAP_KEY_STATUS.toString()).get(Constant.MapKey.MAP_KEY_MESSAGE.toString()).toString()); // 結果メッセージ
		ArrayList<Object> list = new ArrayList<Object>();
		list = (ArrayList<Object>) sampleResult.response.get(Constant.MapKey.MAP_KEY_RESULT.toString()).get(Constant.MapKey.MAP_KEY_GROUPS.toString());

		for (Object obj : list) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.putAll((Map<? extends String, ? extends String>) obj);
			mGetGroupsAPI_ResponseInfo_Part.setId(map.get(Constant.MapKey.MAP_KEY_ID.toString()));// ID
			mGetGroupsAPI_ResponseInfo_Part.setGroupLeaderID(map.get(Constant.MapKey.MAP_KEY_GROUP_LEADER_ID.toString()));// グループリーダーID
			mGetGroupsAPI_ResponseInfo_Part.setGroupName(map.get(Constant.MapKey.MAP_KEY_GROUP_NAME.toString()));// グループ名
			mGetGroupsAPI_ResponseInfo_Part.setGroupMap();
			mGetGroupsAPI_ResponseInfo_Part.setGroupList();
		}
		Log.d(TAG, "analysisGson_GetGroups : " + "End");
		
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		int i = 1;
		for (HashMap<String, String> group : mGetGroupsAPI_ResponseInfo_Part.getGroupList()) { // アイテム追加処理
			String groupName = group.get(Constant.MapKey.MAP_KEY_GROUP_NAME.toString());
			if (groupName.length() > 0) {
				arrayAdapter.add(groupName);
			} else {
				arrayAdapter.add("Group" + i);
			}
			i++;
		}
		mListViewGroupList = (ListView) findViewById(R.id.listview_SearchGroup_GroupList);
		mListViewGroupList.setAdapter(arrayAdapter);	// アダプター設定
		mListViewGroupList.setOnItemClickListener(clickGroupList);	// ユーザー登録ボタン押下処理
		mListViewGroupList.setOnItemSelectedListener(selectGroupList);	// ユーザー登録ボタン選択処理
	}
	
	/**
	 * グループ参加処理
	 */
//	private void partGroup(String userID, String groupID ) {
	private void partGroup(String groupID ) {
		Log.d(TAG, "partGroup : " + "Start");
//		String url = Constant.API.API_ADD2G + "?user_id=" + userID + "&group_id=" + groupID; // グループ参加API
		String url = Constant.API.API_ADD2G + "?user_id=" + MainActivity.UserInfo.getUserID() + "&group_id=" + groupID; // グループ参加API
		
		mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		mRequestQueue.add(new JsonObjectRequest(Method.GET, url, null,
				new Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d(TAG, "onResponse : " + "Start");
						mResJson = response.toString();
						analysisGson_partGroup(); // （グループ取得取得結果）JSON解析処理
						Log.d(TAG, "onResponse : " + "End");
					}
				},

				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						error.toString();
						error.getMessage();
						error.getLocalizedMessage();
						Log.d(TAG, "onResponse : " + "Start");
						
						String str = error.toString();
						int index = str.indexOf("of ");
						mResJson = str.substring(index + 2);
						analysisGson_GetGroups_Part(); // （グループ取得取得結果）JSON解析処理
					}
				}));
		Log.d(TAG, "partGroup : " + "End");
	}

	/**
	 * （グループ参加結果）JSON解析処理
	 */
	private void analysisGson_partGroup() {
		Log.d(TAG, "analysisGson_GetGroups : " + "Start");
		JsonResult_Part sampleResult = new JsonResult_Part();
		sampleResult = new Gson().fromJson(mResJson, JsonResult_Part.class);
		sampleResult.response.get(Constant.MapKey.MAP_KEY_STATUS.toString());
		mPartGroupsAPI_ResponseInfo = new PartGroupsAPI_ResponseInfo();
		mPartGroupsAPI_ResponseInfo.setCode(sampleResult.response.get(Constant.MapKey.MAP_KEY_STATUS.toString()).get(Constant.MapKey.MAP_KEY_CODE.toString()).toString()); // 結果ステータス
		mPartGroupsAPI_ResponseInfo.setMessage(sampleResult.response.get(Constant.MapKey.MAP_KEY_STATUS.toString()).get(Constant.MapKey.MAP_KEY_MESSAGE.toString()).toString()); // 結果メッセージ
		mPartGroupsAPI_ResponseInfo.setId(sampleResult.response.get(Constant.MapKey.MAP_KEY_RESULT.toString()).get(Constant.MapKey.MAP_KEY_ID.toString()).toString());// ID
		mPartGroupsAPI_ResponseInfo.setUserID(sampleResult.response.get(Constant.MapKey.MAP_KEY_RESULT.toString()).get(Constant.MapKey.MAP_KEY_USER_ID.toString()).toString());// ユーザーID
		mPartGroupsAPI_ResponseInfo.setUserName(sampleResult.response.get(Constant.MapKey.MAP_KEY_RESULT.toString()).get(Constant.MapKey.MAP_KEY_USER_NAME.toString()).toString());// ユーザー名
		mPartGroupsAPI_ResponseInfo.setUserGender(sampleResult.response.get(Constant.MapKey.MAP_KEY_RESULT.toString()).get(Constant.MapKey.MAP_KEY_GENDER.toString()).toString());// 性別
		mPartGroupsAPI_ResponseInfo.setUserAge(sampleResult.response.get(Constant.MapKey.MAP_KEY_RESULT.toString()).get(Constant.MapKey.MAP_KEY_AGE.toString()).toString());// 年齢
		mPartGroupsAPI_ResponseInfo.setUserTel(sampleResult.response.get(Constant.MapKey.MAP_KEY_RESULT.toString()).get(Constant.MapKey.MAP_KEY_TEL.toString()).toString());// 電話番号
//		mPartGroupsAPI_ResponseInfo.setGroupID(sampleResult.response.get(PartGroupsAPI_ResponseInfo.RESULT).get(PartGroupsAPI_ResponseInfo.GROUP_ID).toString());// グループID
//		mPartGroupsAPI_ResponseInfo.setGroupLeaderID(sampleResult.response.get(PartGroupsAPI_ResponseInfo.RESULT).get(PartGroupsAPI_ResponseInfo.GROUP_LEADER_ID).toString());// グループリーダーID
//		mPartGroupsAPI_ResponseInfo.setGroupName(sampleResult.response.get(PartGroupsAPI_ResponseInfo.RESULT).get(PartGroupsAPI_ResponseInfo.GROUP_NAME).toString());// グループ名
		Log.d(TAG, "analysisGson_GetGroups : " + "End");

		if (mPartGroupsAPI_ResponseInfo.getUserID() == null) {
			Toast.makeText(PartGroupActivity.this, "Participation Failure", Toast.LENGTH_LONG).show();			
		} else {
			Toast.makeText(PartGroupActivity.this, "Participation", Toast.LENGTH_LONG).show();
			finish();
		}
	}
	
	/**
	 * グループリスト押下処理
	 */
	ListView.OnItemClickListener clickGroupList = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			ListView listView = (ListView) parent;
			String groupName = (String) listView.getItemAtPosition(position);
//			Toast.makeText(PartGroupActivity.this, "onItemClick = " + groupName, Toast.LENGTH_LONG).show();

			String userID = null;
			String groupID = null;
			List<HashMap<String, String>> groupOne = mGetGroupsAPI_ResponseInfo_Part.getGroupList(position);
			for (HashMap<String, String> group : groupOne) {
//				userID = group.get(Constant.MapKey.MAP_KEY_ID.toString());
				// TODO 設計はGROUP_ID
				// groupID = group.get(PartGroupsAPI_ResponseInfo.GROUP_ID);
				groupID = group.get(Constant.MapKey.MAP_KEY_GROUP_LEADER_ID.toString());
				
				if (groupName == group.get(Constant.MapKey.MAP_KEY_GROUP_NAME.toString())) { 
					groupID = group.get(Constant.MapKey.MAP_KEY_ID.toString());
					// グループ参加処理
//					partGroup(userID, groupID);
					partGroup(groupID);
					break;
				}
			}

		}
	};
	
	/*
	 * グループリスト選択処理
	 */
	ListView.OnItemSelectedListener selectGroupList = new AdapterView.OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			ListView listView = (ListView) parent;
			String item = (String) listView.getSelectedItem();
//			Toast.makeText(PartGroupActivity.this, "onItemSelected = " + item, Toast.LENGTH_LONG).show();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
		}
	};
	
	/**
	 * グループ参加ボタン押下処理
	 */
	private final OnClickListener partGroup = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Log.d(TAG, "onClick : " + "Start");
			finish();
		}
	};
// ----------------------------------------------------------------------------------------------------
	/**
	 * グループ情報取得クラス
	 */
    public class GetGroupsAPI_ResponseInfo_Part {

    	private String code; // 結果ステータス
    	private String message; // 結果メッセージ
    	private String id; // ID
    	private String groupLeaderID; // グループリーダーID
    	private String groupName; // グループ名
    	private HashMap<String, String> groupMap = new HashMap<String, String>(); // グループマップ
    	private List<HashMap<String, String>> groupList = new ArrayList<HashMap<String, String>>(); // グループリスト

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

    	public void setId(String id) {
    		this.id = id;
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

    	public HashMap<String, String> getGroupMap() {
    		return groupMap;
    	}

    	public void setGroupMap() {
    		groupMap = new HashMap<String, String>();
    		groupMap.put(Constant.MapKey.MAP_KEY_ID.toString(), getId());
    		groupMap.put(Constant.MapKey.MAP_KEY_GROUP_LEADER_ID.toString(), getGroupLeaderID());
    		groupMap.put(Constant.MapKey.MAP_KEY_GROUP_NAME.toString(), getGroupName());
    	}	

    	public List<HashMap<String, String>> getGroupList() {
    		return groupList;
    	}

    	public List<HashMap<String, String>> getGroupList(int location) {
    		return groupList;
    	}
    	
    	public void setGroupList() {
    		groupList.add(getGroupMap());
    	}
    }
 // ----------------------------------------------------------------------------------------------------
	/**
	 * グループ参加クラス
	 */
    public class PartGroupsAPI_ResponseInfo {

    	private String code; // 結果ステータス
    	private String message; // 結果メッセージ
    	private String id; // ID
    	private String userID; // ユーザーID
    	private String userName; // ユーザー名
    	private String userGender; // ユーザー性別
    	private String userAge; // ユーザー年齢
    	private String userTel; // ユーザー電話番号
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
    	public String getId() {
    		return id;
    	}
    	public void setId(String id) {
    		this.id = id;
    	}
    	public String getUserID() {
    		return userID;
    	}
    	public void setUserID(String userID) {
    		this.userID = userID;
    	}
    	public String getUserName() {
    		return userName;
    	}
    	public void setUserName(String userName) {
    		this.userName = userName;
    	}
    	public String getUserGender() {
    		return userGender;
    	}
    	public void setUserGender(String userGender) {
    		this.userGender = userGender;
    	}
    	public String getUserAge() {
    		return userAge;
    	}
    	public void setUserAge(String userAge) {
    		this.userAge = userAge;
    	}
    	public String getUserTel() {
    		return userTel;
    	}
    	public void setUserTel(String userTel) {
    		this.userTel = userTel;
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
 // ----------------------------------------------------------------------------------------------------
    /**
     * Json返却値格納クラス
     *
     */
    public class JsonResult {
    	public HashMap<String, HashMap<String, Object>> response;
    }
 // ----------------------------------------------------------------------------------------------------
    /**
     * (Part)Json返却値格納クラス
     *
     */
    public class JsonResult_Part {
    	public HashMap<String, HashMap<String, Object>> response;
    }
}
