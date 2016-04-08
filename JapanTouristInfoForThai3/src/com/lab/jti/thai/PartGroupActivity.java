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
 * �O���[�v�Q���A�N�e�B�r�e�B
 *
 */
public class PartGroupActivity extends Activity {

	private final String TAG = "GroupPartActivity"; // �^�O
	private String mResJson = null; // ���X�|���XJSON
	private RequestQueue mRequestQueue; // ���N�G�X�g�L���[
	private Button button_PartGroup_Back; // �����߂�{�^��
	private ListView mListViewGroupList;
	private GetGroupsAPI_ResponseInfo_Part mGetGroupsAPI_ResponseInfo_Part; // �O���[�v���擾�N���X
	private PartGroupsAPI_ResponseInfo mPartGroupsAPI_ResponseInfo; // �O���[�v�Q���N���X

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_group);
		Log.d(TAG, "onCreate: " + "Start");
		getGroupList();	// �O���[�v���X�g�擾����
		button_PartGroup_Back = (Button)findViewById(R.id.button_SearchGroup_Back);
		button_PartGroup_Back.setOnClickListener(partGroup); // �O���[�v�Q�� �߂�{�^����������
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
	 * �O���[�v���X�g�擾����
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
						analysisGson_GetGroups_Part(); // �i�O���[�v�擾�擾���ʁjJSON��͏���
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
						analysisGson_GetGroups_Part(); // �i�O���[�v�擾�擾���ʁjJSON��͏���
					}
				}));
		Log.d(TAG, "registUserInfo : " + "End");
	}

	/**
	 * �i�O���[�v�擾�擾���ʁjJSON��͏���
	 */
	@SuppressWarnings("unchecked")
	private void analysisGson_GetGroups_Part() {
		Log.d(TAG, "analysisGson_GetGroups : " + "Start");
		JsonResult sampleResult = new JsonResult();
		sampleResult = new Gson().fromJson(mResJson, JsonResult.class);
		sampleResult.response.get(Constant.MapKey.MAP_KEY_STATUS.toString());
		mGetGroupsAPI_ResponseInfo_Part = new GetGroupsAPI_ResponseInfo_Part();
		mGetGroupsAPI_ResponseInfo_Part.setCode(sampleResult.response.get(Constant.MapKey.MAP_KEY_STATUS.toString()).get(Constant.MapKey.MAP_KEY_CODE.toString()).toString()); // ���ʃX�e�[�^�X
		mGetGroupsAPI_ResponseInfo_Part.setMessage(sampleResult.response.get(Constant.MapKey.MAP_KEY_STATUS.toString()).get(Constant.MapKey.MAP_KEY_MESSAGE.toString()).toString()); // ���ʃ��b�Z�[�W
		ArrayList<Object> list = new ArrayList<Object>();
		list = (ArrayList<Object>) sampleResult.response.get(Constant.MapKey.MAP_KEY_RESULT.toString()).get(Constant.MapKey.MAP_KEY_GROUPS.toString());

		for (Object obj : list) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.putAll((Map<? extends String, ? extends String>) obj);
			mGetGroupsAPI_ResponseInfo_Part.setId(map.get(Constant.MapKey.MAP_KEY_ID.toString()));// ID
			mGetGroupsAPI_ResponseInfo_Part.setGroupLeaderID(map.get(Constant.MapKey.MAP_KEY_GROUP_LEADER_ID.toString()));// �O���[�v���[�_�[ID
			mGetGroupsAPI_ResponseInfo_Part.setGroupName(map.get(Constant.MapKey.MAP_KEY_GROUP_NAME.toString()));// �O���[�v��
			mGetGroupsAPI_ResponseInfo_Part.setGroupMap();
			mGetGroupsAPI_ResponseInfo_Part.setGroupList();
		}
		Log.d(TAG, "analysisGson_GetGroups : " + "End");
		
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		int i = 1;
		for (HashMap<String, String> group : mGetGroupsAPI_ResponseInfo_Part.getGroupList()) { // �A�C�e���ǉ�����
			String groupName = group.get(Constant.MapKey.MAP_KEY_GROUP_NAME.toString());
			if (groupName.length() > 0) {
				arrayAdapter.add(groupName);
			} else {
				arrayAdapter.add("Group" + i);
			}
			i++;
		}
		mListViewGroupList = (ListView) findViewById(R.id.listview_SearchGroup_GroupList);
		mListViewGroupList.setAdapter(arrayAdapter);	// �A�_�v�^�[�ݒ�
		mListViewGroupList.setOnItemClickListener(clickGroupList);	// ���[�U�[�o�^�{�^����������
		mListViewGroupList.setOnItemSelectedListener(selectGroupList);	// ���[�U�[�o�^�{�^���I������
	}
	
	/**
	 * �O���[�v�Q������
	 */
//	private void partGroup(String userID, String groupID ) {
	private void partGroup(String groupID ) {
		Log.d(TAG, "partGroup : " + "Start");
//		String url = Constant.API.API_ADD2G + "?user_id=" + userID + "&group_id=" + groupID; // �O���[�v�Q��API
		String url = Constant.API.API_ADD2G + "?user_id=" + MainActivity.UserInfo.getUserID() + "&group_id=" + groupID; // �O���[�v�Q��API
		
		mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		mRequestQueue.add(new JsonObjectRequest(Method.GET, url, null,
				new Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d(TAG, "onResponse : " + "Start");
						mResJson = response.toString();
						analysisGson_partGroup(); // �i�O���[�v�擾�擾���ʁjJSON��͏���
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
						analysisGson_GetGroups_Part(); // �i�O���[�v�擾�擾���ʁjJSON��͏���
					}
				}));
		Log.d(TAG, "partGroup : " + "End");
	}

	/**
	 * �i�O���[�v�Q�����ʁjJSON��͏���
	 */
	private void analysisGson_partGroup() {
		Log.d(TAG, "analysisGson_GetGroups : " + "Start");
		JsonResult_Part sampleResult = new JsonResult_Part();
		sampleResult = new Gson().fromJson(mResJson, JsonResult_Part.class);
		sampleResult.response.get(Constant.MapKey.MAP_KEY_STATUS.toString());
		mPartGroupsAPI_ResponseInfo = new PartGroupsAPI_ResponseInfo();
		mPartGroupsAPI_ResponseInfo.setCode(sampleResult.response.get(Constant.MapKey.MAP_KEY_STATUS.toString()).get(Constant.MapKey.MAP_KEY_CODE.toString()).toString()); // ���ʃX�e�[�^�X
		mPartGroupsAPI_ResponseInfo.setMessage(sampleResult.response.get(Constant.MapKey.MAP_KEY_STATUS.toString()).get(Constant.MapKey.MAP_KEY_MESSAGE.toString()).toString()); // ���ʃ��b�Z�[�W
		mPartGroupsAPI_ResponseInfo.setId(sampleResult.response.get(Constant.MapKey.MAP_KEY_RESULT.toString()).get(Constant.MapKey.MAP_KEY_ID.toString()).toString());// ID
		mPartGroupsAPI_ResponseInfo.setUserID(sampleResult.response.get(Constant.MapKey.MAP_KEY_RESULT.toString()).get(Constant.MapKey.MAP_KEY_USER_ID.toString()).toString());// ���[�U�[ID
		mPartGroupsAPI_ResponseInfo.setUserName(sampleResult.response.get(Constant.MapKey.MAP_KEY_RESULT.toString()).get(Constant.MapKey.MAP_KEY_USER_NAME.toString()).toString());// ���[�U�[��
		mPartGroupsAPI_ResponseInfo.setUserGender(sampleResult.response.get(Constant.MapKey.MAP_KEY_RESULT.toString()).get(Constant.MapKey.MAP_KEY_GENDER.toString()).toString());// ����
		mPartGroupsAPI_ResponseInfo.setUserAge(sampleResult.response.get(Constant.MapKey.MAP_KEY_RESULT.toString()).get(Constant.MapKey.MAP_KEY_AGE.toString()).toString());// �N��
		mPartGroupsAPI_ResponseInfo.setUserTel(sampleResult.response.get(Constant.MapKey.MAP_KEY_RESULT.toString()).get(Constant.MapKey.MAP_KEY_TEL.toString()).toString());// �d�b�ԍ�
//		mPartGroupsAPI_ResponseInfo.setGroupID(sampleResult.response.get(PartGroupsAPI_ResponseInfo.RESULT).get(PartGroupsAPI_ResponseInfo.GROUP_ID).toString());// �O���[�vID
//		mPartGroupsAPI_ResponseInfo.setGroupLeaderID(sampleResult.response.get(PartGroupsAPI_ResponseInfo.RESULT).get(PartGroupsAPI_ResponseInfo.GROUP_LEADER_ID).toString());// �O���[�v���[�_�[ID
//		mPartGroupsAPI_ResponseInfo.setGroupName(sampleResult.response.get(PartGroupsAPI_ResponseInfo.RESULT).get(PartGroupsAPI_ResponseInfo.GROUP_NAME).toString());// �O���[�v��
		Log.d(TAG, "analysisGson_GetGroups : " + "End");

		if (mPartGroupsAPI_ResponseInfo.getUserID() == null) {
			Toast.makeText(PartGroupActivity.this, "Participation Failure", Toast.LENGTH_LONG).show();			
		} else {
			Toast.makeText(PartGroupActivity.this, "Participation", Toast.LENGTH_LONG).show();
			finish();
		}
	}
	
	/**
	 * �O���[�v���X�g��������
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
				// TODO �݌v��GROUP_ID
				// groupID = group.get(PartGroupsAPI_ResponseInfo.GROUP_ID);
				groupID = group.get(Constant.MapKey.MAP_KEY_GROUP_LEADER_ID.toString());
				
				if (groupName == group.get(Constant.MapKey.MAP_KEY_GROUP_NAME.toString())) { 
					groupID = group.get(Constant.MapKey.MAP_KEY_ID.toString());
					// �O���[�v�Q������
//					partGroup(userID, groupID);
					partGroup(groupID);
					break;
				}
			}

		}
	};
	
	/*
	 * �O���[�v���X�g�I������
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
	 * �O���[�v�Q���{�^����������
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
	 * �O���[�v���擾�N���X
	 */
    public class GetGroupsAPI_ResponseInfo_Part {

    	private String code; // ���ʃX�e�[�^�X
    	private String message; // ���ʃ��b�Z�[�W
    	private String id; // ID
    	private String groupLeaderID; // �O���[�v���[�_�[ID
    	private String groupName; // �O���[�v��
    	private HashMap<String, String> groupMap = new HashMap<String, String>(); // �O���[�v�}�b�v
    	private List<HashMap<String, String>> groupList = new ArrayList<HashMap<String, String>>(); // �O���[�v���X�g

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
	 * �O���[�v�Q���N���X
	 */
    public class PartGroupsAPI_ResponseInfo {

    	private String code; // ���ʃX�e�[�^�X
    	private String message; // ���ʃ��b�Z�[�W
    	private String id; // ID
    	private String userID; // ���[�U�[ID
    	private String userName; // ���[�U�[��
    	private String userGender; // ���[�U�[����
    	private String userAge; // ���[�U�[�N��
    	private String userTel; // ���[�U�[�d�b�ԍ�
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
     * Json�ԋp�l�i�[�N���X
     *
     */
    public class JsonResult {
    	public HashMap<String, HashMap<String, Object>> response;
    }
 // ----------------------------------------------------------------------------------------------------
    /**
     * (Part)Json�ԋp�l�i�[�N���X
     *
     */
    public class JsonResult_Part {
    	public HashMap<String, HashMap<String, Object>> response;
    }
}
