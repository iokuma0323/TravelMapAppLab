package com.lab.jti.thai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
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
 * �O���[�v�����A�N�e�B�r�e�B
 * 
 */
public class SearchGroupActivity extends Activity {

	private final String TAG = "GroupSearchActivity"; // �^�O
	private String mResJson = null; // ���X�|���XJSON
	private RequestQueue mRequestQueue; // ���N�G�X�g�L���[
	private Button button_SearchGroup_Back; // �����߂�{�^��
	private ListView mListViewGroupList;
	private GetGroupsAPI_ResponseInfo_Search mGetGroupsAPI_ResponseInfo_Search; // �O���[�v�擾���
	private GetGroupGeoAPI_ResponseInfo mGetGroupGeoAPI_ResponseInfo; // �O���[�v���݈ʒu���
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_group);
		Log.d(TAG, "onCreate: " + "Start");
		getGroupList();	// �O���[�v���X�g�擾����
		button_SearchGroup_Back = (Button)findViewById(R.id.button_SearchGroup_Back);
		button_SearchGroup_Back.setOnClickListener(searchGroup); // �O���[�v���� �߂�{�^����������
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
	 * �O���[�v���X�g�擾����
	 */
	private void getGroupList() {
		Log.d(TAG, "getGroupList : " + "Start");
		String url = Constant.API.API_GET_GROUPS.toString();

		mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		mRequestQueue.add(new JsonObjectRequest(Method.GET, url, null,
				new Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d(TAG, "onResponse : " + "Start");
						mResJson = response.toString();
						analysisGson_GetGroups_Search(); // �i�O���[�v�擾�擾���ʁjJSON��͏���
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
						analysisGson_GetGroups_Search(); // �i�O���[�v�擾�擾���ʁjJSON��͏���
					}
				}));
		Log.d(TAG, "getGroupList : " + "End");
	}

	/**
	 * �i�O���[�v�擾�擾���ʁjJSON��͏���
	 */
	@SuppressWarnings("unchecked")
	private void analysisGson_GetGroups_Search() {
		Log.d(TAG, "analysisGson_GetGroups : " + "Start");
		JsonResult sampleResult = new JsonResult();
		sampleResult = new Gson().fromJson(mResJson, JsonResult.class);
		sampleResult.response.get(Constant.MapKey.MAP_KEY_STATUS.toString());
		mGetGroupsAPI_ResponseInfo_Search = new GetGroupsAPI_ResponseInfo_Search();
		mGetGroupsAPI_ResponseInfo_Search.setCode(sampleResult.response.get(Constant.MapKey.MAP_KEY_STATUS.toString()).get(Constant.MapKey.MAP_KEY_CODE.toString()).toString()); // ���ʃX�e�[�^�X
		mGetGroupsAPI_ResponseInfo_Search.setMessage(sampleResult.response.get(Constant.MapKey.MAP_KEY_STATUS.toString()).get(Constant.MapKey.MAP_KEY_MESSAGE.toString()).toString()); // ���ʃ��b�Z�[�W
		sampleResult.response.get(Constant.MapKey.MAP_KEY_STATUS.toString());
		
		ArrayList<Object> list = new ArrayList<Object>();
		list = (ArrayList<Object>) sampleResult.response.get(Constant.MapKey.MAP_KEY_RESULT.toString()).get(Constant.MapKey.MAP_KEY_GROUPS.toString());
		
		for (Object obj : list) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.putAll((Map<? extends String, ? extends String>) obj);
			mGetGroupsAPI_ResponseInfo_Search.setId(map.get(Constant.MapKey.MAP_KEY_ID.toString()));// ID
			mGetGroupsAPI_ResponseInfo_Search.setGroupLeaderID(map.get(Constant.MapKey.MAP_KEY_GROUP_LEADER_ID.toString()));// �O���[�v���[�_�[ID
			mGetGroupsAPI_ResponseInfo_Search.setGroupName(map.get(Constant.MapKey.MAP_KEY_GROUP_NAME.toString()));// �O���[�v��
			
			mGetGroupsAPI_ResponseInfo_Search.setGroupMap();
			mGetGroupsAPI_ResponseInfo_Search.setGroupList();
		}
		Log.d(TAG, "analysisGson_GetGroups : " + "End");
		
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		int i = 1;
		for (HashMap<String, String> group : mGetGroupsAPI_ResponseInfo_Search.getGroupList()) { // �A�C�e���ǉ�����
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
	 * �O���[�v���݈ʒu�擾����
	 */
	private void getGroupGeo(String groupID ) {
		Log.d(TAG, "getGroupGeo : " + "Start");
//		String url = "http://dev.airdocs.jp/his/getgroupgeo.php?&group_id=" + groupID; // �O���[�v���݈ʒu�擾API
//		String url = "http://dev.airdocs.jp/his/getgroupgeo.php?&group_id=" + groupID + "&meter=" + MainActivity.mMeter; // �O���[�v���݈ʒu�擾API
		String url = "http://dev.airdocs.jp/his/getgroupgeo.php?&group_id=" + groupID; // �O���[�v���݈ʒu�擾API
		
		// TODO �O���[�vID���s�����������O���[�v�ł��邩�̊m�F���K�v
		
//		String url = "http://dev.airdocs.jp/his/getgroupgeo.php?&group_id=5"; // �O���[�v���݈ʒu�擾API

		mRequestQueue = Volley.newRequestQueue(MainActivity.mContext);
		mRequestQueue.add(new JsonObjectRequest(Method.GET, url, null,
				new Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d(TAG, "onResponse : " + "Start");
						mResJson = response.toString();
						analysisGson_searchGroup(); // �i�O���[�v�������ʁjJSON��͏���
						Log.d(TAG, "onResponse : " + "End");
					}
				},

				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// �G���[���� error.networkResponse�Ŋm�F
						// �G���[�\���Ȃ�
						error.toString();
						error.getMessage();
						error.getLocalizedMessage();
						Log.d(TAG, "onResponse : " + "Start");
						
						String str = error.toString();
						int index = str.indexOf("of ");
						System.out.println("���o��������->" + str.substring(index + 3));
						mResJson = str.substring(index + 2);
//						analysisGson_searchGroup(); // �i�O���[�v�擾�擾���ʁjJSON��͏���
					}
				}));
		Log.d(TAG, "getGroupGeo : " + "End");
	}

	/**
	 * �i�O���[�v���݈ʒu�擾���ʁjJSON��͏���
	 */
	private void analysisGson_searchGroup() {
		Log.d(TAG, "analysisGson_GetGroups : " + "Start");
		JsonResult_GetGroupGeo sampleResult = new JsonResult_GetGroupGeo();
		sampleResult = new Gson().fromJson(mResJson, JsonResult_GetGroupGeo.class);
		sampleResult.response.get(Constant.MapKey.MAP_KEY_STATUS.toString());

		mGetGroupGeoAPI_ResponseInfo = new GetGroupGeoAPI_ResponseInfo();
		mGetGroupGeoAPI_ResponseInfo.setCode(sampleResult.response.get(Constant.MapKey.MAP_KEY_STATUS.toString()).get(Constant.MapKey.MAP_KEY_CODE.toString()).toString()); // ���ʃX�e�[�^�X
		mGetGroupGeoAPI_ResponseInfo.setMessage(sampleResult.response.get(Constant.MapKey.MAP_KEY_STATUS.toString()).get(Constant.MapKey.MAP_KEY_MESSAGE.toString()).toString()); // ���ʃ��b�Z�[�W
		
		
		ArrayList<Object> list = new ArrayList<Object>();
		list = (ArrayList<Object>) sampleResult.response.get(Constant.MapKey.MAP_KEY_RESULT.toString()).get(Constant.MapKey.MAP_KEY_USERS.toString());

		for (Object obj : list) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.putAll((Map<? extends String, ? extends String>) obj);
			mGetGroupGeoAPI_ResponseInfo.setId(map.get(Constant.MapKey.MAP_KEY_ID.toString()).toString());// ID
			
			if (map.get(Constant.MapKey.MAP_KEY_USER_ID.toString()).toString() == null) {
				Toast.makeText(SearchGroupActivity.this, "No Action History", Toast.LENGTH_LONG).show();	
				break;
			} 
			
			mGetGroupGeoAPI_ResponseInfo.setUserID(map.get(Constant.MapKey.MAP_KEY_USER_ID.toString()).toString());// ���[�U�[ID
			// TODO �ԋp���Ă��炤�\��
			mGetGroupGeoAPI_ResponseInfo.setUserName(map.get(Constant.MapKey.MAP_KEY_USER_NAME.toString()).toString());// ���[�U�[��
			mGetGroupGeoAPI_ResponseInfo.setUserGender(map.get(Constant.MapKey.MAP_KEY_GENDER.toString()).toString());// ����
			mGetGroupGeoAPI_ResponseInfo.setUserAge(map.get(Constant.MapKey.MAP_KEY_AGE.toString()).toString());// �N��
			mGetGroupGeoAPI_ResponseInfo.setUserTel(map.get(Constant.MapKey.MAP_KEY_TEL.toString()).toString());// �d�b�ԍ�
			mGetGroupGeoAPI_ResponseInfo.setUserLatitude(map.get(Constant.MapKey.MAP_KEY_USER_LATITUDE.toString()).toString());// �ܓx
			mGetGroupGeoAPI_ResponseInfo.setUserLongitude(map.get(Constant.MapKey.MAP_KEY_USER_LONGITUDE.toString()).toString());// �o�x
			mGetGroupGeoAPI_ResponseInfo.setGroupID(map.get(Constant.MapKey.MAP_KEY_GROUP_ID.toString()).toString());// �O���[�vID
			mGetGroupGeoAPI_ResponseInfo.setGroupLeaderID(map.get(Constant.MapKey.MAP_KEY_GROUP_LEADER_ID.toString()).toString());// �O���[�v���[�_�[ID
			mGetGroupGeoAPI_ResponseInfo.setGroupName(map.get(Constant.MapKey.MAP_KEY_GROUP_NAME.toString()).toString());// �O���[�v��
			mGetGroupGeoAPI_ResponseInfo.setGroupMap();
			mGetGroupGeoAPI_ResponseInfo.setGroupList();
			
//			if (mGetGroupGeoAPI_ResponseInfo.getUserID().equals(mGetGroupGeoAPI_ResponseInfo.getGroupLeaderID())) {
			
			String flagID = mGetGroupGeoAPI_ResponseInfo.getGroupID();
			int flagID_LastIndex = Integer.parseInt(String.valueOf(flagID.charAt(flagID.length()-1)));
			
				// �t���O�`�揈��
				SearchActivity.drawContentsFlag(
//						mGetGroupGeoAPI_ResponseInfo.getGroupName(), 
						mGetGroupGeoAPI_ResponseInfo.getUserName(),
						mGetGroupGeoAPI_ResponseInfo.getUserTel(),
						Double.parseDouble(mGetGroupGeoAPI_ResponseInfo.getUserLatitude()),
						Double.parseDouble(mGetGroupGeoAPI_ResponseInfo.getUserLongitude()),
//						flagID_LastIndex);
						7);
//			}
		}
		Log.d(TAG, "analysisGson_GetGroups : " + "End");

		if (mGetGroupGeoAPI_ResponseInfo.getUserLatitude() == null && mGetGroupGeoAPI_ResponseInfo.getUserLongitude() == null) {
			Toast.makeText(SearchGroupActivity.this, "Acquisition Failure", Toast.LENGTH_LONG).show();			
		} else {
			Toast.makeText(SearchGroupActivity.this, "Acquisition Success", Toast.LENGTH_LONG).show();
		}
		finish();
	}
	
	
	/**
	 * �O���[�v���X�g��������
	 */
	ListView.OnItemClickListener clickGroupList = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			ListView listView = (ListView) parent;
			String groupName = (String) listView.getItemAtPosition(position);
//			Toast.makeText(SearchGroupActivity.this, "onItemClick = " + groupName, Toast.LENGTH_LONG).show();
			
			String userID = null;
			String groupID = null;
			List<HashMap<String, String>> groupOne = mGetGroupsAPI_ResponseInfo_Search.getGroupList();
			for (HashMap<String, String> group : groupOne) {
				userID = group.get(Constant.MapKey.MAP_KEY_ID.toString());
				// TODO �݌v��GROUP_ID
//				groupID = group.get(PartGroupsAPI_ResponseInfo.GROUP_ID);
				
				if (groupName == group.get(Constant.MapKey.MAP_KEY_GROUP_NAME.toString())) { 
					groupID = group.get(Constant.MapKey.MAP_KEY_ID.toString());
					// �O���[�v���݈ʒu�擾����
					getGroupGeo(groupID);
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
//			Toast.makeText(SearchGroupActivity.this, "onItemSelected = " + item, Toast.LENGTH_LONG).show();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
		}
	};
	
	/**
	 * �O���[�v�����{�^����������
	 */
	private final OnClickListener searchGroup = new OnClickListener() {
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
    public class GetGroupsAPI_ResponseInfo_Search {

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

    	public List<HashMap<String, String>> getGroupList(int position) {
    		return groupList;
    	}
    	
    	public void setGroupList() {
    		groupList.add(getGroupMap());
    	}
    }
    
 // ----------------------------------------------------------------------------------------------------
	/**
	 * �O���[�v���݈ʒu�N���X
	 */
    public class GetGroupGeoAPI_ResponseInfo {

    	private String code; // ���ʃX�e�[�^�X
    	private String message; // ���ʃ��b�Z�[�W
    	private String id; // ID
    	private String userID; // ���[�U�[ID
    	private String userName; // ���[�U�[��
    	private String userGender; // ���[�U�[����
    	private String userAge; // ���[�U�[�N��
    	private String userTel; // ���[�U�[�d�b�ԍ�
    	private String userLatitude; // ���[�U�[�ܓx
    	private String userLongitude; // ���[�U�[�o�x
    	private String groupID; // �O���[�vID
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
		public String getUserLatitude() {
			return userLatitude;
		}
		public void setUserLatitude(String userLatitude) {
			this.userLatitude = userLatitude;
		}
		public String getUserLongitude() {
			return userLongitude;
		}
		public void setUserLongitude(String userLongitude) {
			this.userLongitude = userLongitude;
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
    	public HashMap<String, String> getGroupMap() {
    		return groupMap;
    	}

    	public void setGroupMap() {
    		groupMap = new HashMap<String, String>();
    		groupMap.put(Constant.MapKey.MAP_KEY_ID.toString(), getId());
    		groupMap.put(Constant.MapKey.MAP_KEY_USER_ID.toString(), getUserID());
    		groupMap.put(Constant.MapKey.MAP_KEY_USER_NAME.toString(), getUserName());
    		groupMap.put(Constant.MapKey.MAP_KEY_GENDER.toString(), getUserGender());
    		groupMap.put(Constant.MapKey.MAP_KEY_AGE.toString(), getUserAge());
    		groupMap.put(Constant.MapKey.MAP_KEY_TEL.toString(), getUserTel());
    		groupMap.put(Constant.MapKey.MAP_KEY_USER_LATITUDE.toString(), getUserLatitude());
    		groupMap.put(Constant.MapKey.MAP_KEY_USER_LONGITUDE.toString(), getUserLongitude());
    		groupMap.put(Constant.MapKey.MAP_KEY_GROUP_ID.toString(), getGroupID());
    		groupMap.put(Constant.MapKey.MAP_KEY_GROUP_LEADER_ID.toString(), getGroupLeaderID());
    		groupMap.put(Constant.MapKey.MAP_KEY_GROUP_NAME.toString(), getGroupName());
    	}	

    	public List<HashMap<String, String>> getGroupList() {
    		return groupList;
    	}

    	public List<HashMap<String, String>> getGroupList(int position) {
    		return groupList;
    	}
    	
    	public void setGroupList() {
    		groupList.add(getGroupMap());
    	}
    }

 // ----------------------------------------------------------------------------------------------------
    /**
     * Json�ԋp�l�i�[�N���X
     */
    public class JsonResult {
    	public HashMap<String, HashMap<String, Object>> response;
    }
    
 // ----------------------------------------------------------------------------------------------------
    /**
     * (getGroupGeo)Json�ԋp�l�i�[�N���X
     */
    public class JsonResult_GetGroupGeo {
    	public HashMap<String, HashMap<String, Object>> response;
    }
}
