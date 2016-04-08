package com.lab.jti.thai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.lab.jti.thai.SearchActivity.ContentsInfo;
import com.lab.jti.thai.SearchActivity.LocationInfo;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * �l�b�g���[�N�ʐM�A�N�e�B�r�e�B Volley���C�u�����̗p
 */
public class VolleyActivity extends Activity {

	private final String TAG = "VolleyActivity"; // �^�O
	@SuppressWarnings("unused")
	private String extraContents_ID; // �R���e���cID
	private String extraContents_Name; // �R���e���c��
	@SuppressWarnings("unused")
	private String extraContents_Tel; // �R���e���c�d�b�ԍ�
	private String extraContents_URL; // �R���e���cURL
	private String extraContents_Latitude; // �R���e���c�o�x
	private String extraContents_Loditude; // �R���e���c�ܓx
	
	private RequestQueue mRequestQueue; // ���N�G�X�g�L���[
	private String mResJson = null; // ���X�|���XJSON
	private ContentsInfo mContents; // �R���e���c���
	private boolean mIscontents = false; // �R���e���c�L���t���O
	private NotificationManager mNotificationManager; // �m�[�e�B�t�B�P�[�V�����}�l�[�W���[
	private int mNotificationID = 0; // �m�[�e�B�t�B�P�[�V����ID
	private int requestQueueCnt = 0; // ���N�G�X�g�L���[��

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate : " + "Start");
		
		if (SearchActivity.mIsGoogleMap) {
			getAreaInfo(); // ���ӏ��擾����
		}
		Log.d(TAG, "onCreate : " + "End");
		finish();
	}

	/**
	 * ���ӏ��擾����
	 */
	private void getAreaInfo() {
		Log.d(TAG, "response : " + "getAreaInfo@���ӏ��擾���� Start");
		
//		mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		mRequestQueue = Volley.newRequestQueue(MainActivity.mContext);
		mRequestQueue.getCache().clear();
		String url = "http://dev.airdocs.jp/his/geo.php?user_latitude=" + LocationInfo.getLatitude() + "&user_longitude=" + LocationInfo.getLongitude() + "&user_id=" + MainActivity.UserInfo.getUserID() + "&meter=" + MainActivity.mMeter;
//		String url = "http://dev.airdocs.jp/his/geo.php?user_latitude=" + "35.606425" + "&user_longitude=" + "139.679104" + "&user_id=" + MainActivity.UserInfo.getUserID();		
		
		mRequestQueue.add(new JsonObjectRequest(Method.GET, url, null, new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				mResJson = response.toString();
				Log.d(TAG, "response : " + response.toString());
				int cnt = 0;
				while (true) {
					if (mResJson != null) {
						mIscontents = true;
						break;
					} else if (cnt <= 10) {
						mIscontents = false;
						break;
					}
					try {
						Thread.sleep(1000); // 1�bSleep����
						Log.d("Thread.sleep(1000)", " sleep");
					} catch (InterruptedException e) {
					}
					cnt++;
				}
				analysisGson_Contents(); // JSON��͏���
				int cntList = 0;
				
				extraContents_ID = null; // �R���e���cID
				extraContents_Name = null; // �R���e���c��
				extraContents_Tel = null; // �R���e���c�d�b�ԍ�
				extraContents_URL = null; // �R���e���cURL
				extraContents_Latitude = null; // �R���e���c�o�x
				extraContents_Loditude = null; // �R���e���c�ܓx

				try {
					while (cntList < mContents.getContentsList().size()) {
						if (mIscontents) {
							mRequestQueue.start();
							HashMap<String, String> map = new HashMap<String, String>();
							
							// TODO
							if (mContents.getContentsList().get(cntList).size() == 0) {
								Log.d(TAG, "response : " + mContents.getContentsList().get(cntList).size());
								break;
							}
							
							map = (HashMap<String, String>) mContents.getContentsList().get(cntList);
							extraContents_ID = map.get(Constant.MapKey.MAP_KEY_CONTENTS_ID.toString());
							extraContents_Name = map.get(Constant.MapKey.MAP_KEY_CONTENTS_NAME.toString());
							extraContents_Tel = map.get(Constant.MapKey.MAP_KEY_CONTENTS_TEL.toString());
							extraContents_URL = map.get(Constant.MapKey.MAP_KEY_CONTENTS_URL.toString());
							extraContents_Latitude = map.get(Constant.MapKey.MAP_KEY_CONTENTS_LATITUDE.toString());
							extraContents_Loditude = map.get(Constant.MapKey.MAP_KEY_CONTENTS_LONGITUDE.toString());
							cntList++;
							
							if (extraContents_URL != null && requestQueueCnt <= 10) {
								Log.d(TAG, "�T�[�o�[����擾�����\��URL : " + extraContents_URL + " : "+ cntList);
								
								if (ContentsInfo.getContentsDispList().size() == 0) { // ���񌟒m��
									sendNotification(extraContents_Name, extraContents_URL); // �m�[�e�B�t�B�P�[�V�������s����
//									ContentsInfo.setContentsDispList(extraContents_URL);
									ContentsInfo.setContentsDispList(extraContents_ID);
									SearchActivity.drawContentsFlag(
											extraContents_Name,
											extraContents_URL,
											Double.parseDouble(extraContents_Latitude),
											Double.parseDouble(extraContents_Loditude),
											0);
								} else {
									boolean b = false; 
									for (String contents : ContentsInfo.getContentsDispList()) {
//										if (extraContents_URL.equals(contents)) {
										if (extraContents_ID == contents) {
											Log.d("VolleyActivity", "�\���ς݃R���e���c��� " + extraContents_URL);
										} else {
//											ContentsInfo.setContentsDispList(extraContents_ID);
//											sendNotification(extraContents_Name, extraContents_URL); // �m�[�e�B�t�B�P�[�V�������s����
//											SearchActivity.drawContentsFlag(
//													extraContents_Name,
//													extraContents_URL,
//													Double.parseDouble(extraContents_Latitude),
//													Double.parseDouble(extraContents_Loditude),
//													0);
											b = true;
											break;
										}
									}
									if (b) {
										ContentsInfo.setContentsDispList(extraContents_ID);
										sendNotification(extraContents_Name, extraContents_URL); // �m�[�e�B�t�B�P�[�V�������s����
										SearchActivity.drawContentsFlag(
												extraContents_Name,
												extraContents_URL,
												Double.parseDouble(extraContents_Latitude),
												Double.parseDouble(extraContents_Loditude),
												0);
									}
								}
								extraContents_URL = null;
							}
//							if (requestQueueCnt <= 10) {
//								mRequestQueue.stop();
//								break;
//							}
						}
						mRequestQueue.stop();
						mRequestQueue.getCache().clear();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				requestQueueCnt++;
			}
		}, null));
		Log.d(TAG, "response : " + "getAreaInfo@���ӏ��擾���� End");
	}

	/**
	 * �i�R���e���c�jJSON��͏���
	 */
	@SuppressWarnings("unchecked")
	private void analysisGson_Contents() {
		Log.d(TAG, "analysisGson_Contents : " + "Start");
		JsonResult sampleResult = new JsonResult();
		sampleResult = new Gson().fromJson(mResJson, JsonResult.class);
		sampleResult.response.get(Constant.MapKey.MAP_KEY_STATUS.toString());
		mContents = new ContentsInfo();
		mContents.setCode(sampleResult.response.get(Constant.MapKey.MAP_KEY_STATUS.toString()).get(Constant.MapKey.MAP_KEY_CODE.toString()).toString()); // ���ʃX�e�[�^�X
		mContents.setMessage(sampleResult.response.get(Constant.MapKey.MAP_KEY_STATUS.toString()).get(Constant.MapKey.MAP_KEY_MESSAGE.toString()).toString()); // ���ʃ��b�Z�[�W
		ArrayList<Object> list = new ArrayList<Object>();
		list = (ArrayList<Object>) sampleResult.response.get(Constant.MapKey.MAP_KEY_RESULT.toString()).get(Constant.MapKey.MAP_KEY_CONTENTS.toString());

		for (Object obj : list) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.putAll((Map<? extends String, ? extends String>) obj);
			mContents.setId(map.get(Constant.MapKey.MAP_KEY_ID.toString()));// ID
			mContents.setContentsID(map.get(Constant.MapKey.MAP_KEY_CONTENTS_ID.toString()));// �R���e���cID
			mContents.setContentsName(map.get(Constant.MapKey.MAP_KEY_CONTENTS_NAME.toString()));// �R���e���c��
			mContents.setContentsAddress(map.get(Constant.MapKey.MAP_KEY_CONTENTS_ADDRESS.toString()));// �R���e���c�Z��
			mContents.setContentsTel(map.get(Constant.MapKey.MAP_KEY_CONTENTS_TEL.toString()));// �R���e���c�d�b�ԍ�
			mContents.setContentsMail(map.get(Constant.MapKey.MAP_KEY_CONTENTS_MAIL.toString()));// �R���e���c���[���A�h���X
			mContents.setContentsUrl(map.get(Constant.MapKey.MAP_KEY_CONTENTS_URL.toString()));// �R���e���cTEL�ԍ�
			mContents.setContentsLatitude(map.get(Constant.MapKey.MAP_KEY_CONTENTS_LATITUDE.toString()));// �R���e���cFAX�ԍ�
			mContents.setContentsLongitude(map.get(Constant.MapKey.MAP_KEY_CONTENTS_LONGITUDE.toString()));// �R���e���c�o�x
			mContents.setContentsType(map.get(Constant.MapKey.MAP_KEY_CONTENTS_TYPE.toString()));// �R���e���c�ܓx
			mContents.setContentsMap();
			mContents.setContentsList();
		}
		Log.d(TAG, "analysisGson_Contents : " + "End");
	}

	/**
	 * �m�[�e�B�t�B�P�[�V�������s����
	 * 
	 * @param contents_url
	 * @param extraContents_URL2 
	 */
	private void sendNotification(String contentsName, String extraContents_URL) {
		Log.d(TAG, "sendNotification : " + "Start Param@contents_url = " + extraContents_URL);
		mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		Intent intent = new Intent(VolleyActivity.this, NotificationWebViewActivity.class);
		intent.putExtra(Constant.Notification.NOTIFICATION_URL.toString(), extraContents_URL);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
		NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
		builder.setContentIntent(pendingIntent); // �m�[�e�B�t�B�P�[�V���� �e�L�X�g�ݒ�

		if (contentsName == null) {
			builder.setTicker("NotificationSample");// �m�[�e�B�t�B�P�[�V���� �A�C�R���ݒ�			
			builder.setContentTitle("NotificationTitle");// �m�[�e�B�t�B�P�[�V���� �T�u�^�C�g���ݒ�
		} else {
			builder.setTicker(contentsName);// �m�[�e�B�t�B�P�[�V���� �A�C�R���ݒ�			
			builder.setContentTitle(contentsName);// �m�[�e�B�t�B�P�[�V���� �T�u�^�C�g���ݒ�
		}
		builder.setSmallIcon(R.drawable.ha_w);// �m�[�e�B�t�B�P�[�V���� �^�C�g���ݒ�
		builder.setWhen(System.currentTimeMillis());// �m�[�e�B�t�B�P�[�V���� �ʒm���E�o�C�u�E���C�g�ݒ�
		builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS);// �m�[�e�B�t�B�P�[�V�����L�����Z������^�b�v
		builder.setAutoCancel(true);

		try {
			mNotificationManager.notify(mNotificationID, builder.build());// Notification���쐬���Ēʒm
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mNotificationID++;
		Log.d(TAG, "sendNotification : " + "End");
	}
	
	/**
	 * Json�ԋp�l�i�[�N���X
	 *
	 */
	public class JsonResult {
		public HashMap<String, HashMap<String, Object>> response;
	}
}
