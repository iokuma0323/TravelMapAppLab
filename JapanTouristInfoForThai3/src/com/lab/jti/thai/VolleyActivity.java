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
 * ネットワーク通信アクティビティ Volleyライブラリ採用
 */
public class VolleyActivity extends Activity {

	private final String TAG = "VolleyActivity"; // タグ
	@SuppressWarnings("unused")
	private String extraContents_ID; // コンテンツID
	private String extraContents_Name; // コンテンツ名
	@SuppressWarnings("unused")
	private String extraContents_Tel; // コンテンツ電話番号
	private String extraContents_URL; // コンテンツURL
	private String extraContents_Latitude; // コンテンツ経度
	private String extraContents_Loditude; // コンテンツ緯度
	
	private RequestQueue mRequestQueue; // リクエストキュー
	private String mResJson = null; // レスポンスJSON
	private ContentsInfo mContents; // コンテンツ情報
	private boolean mIscontents = false; // コンテンツ有無フラグ
	private NotificationManager mNotificationManager; // ノーティフィケーションマネージャー
	private int mNotificationID = 0; // ノーティフィケーションID
	private int requestQueueCnt = 0; // リクエストキュー回数

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate : " + "Start");
		
		if (SearchActivity.mIsGoogleMap) {
			getAreaInfo(); // 周辺情報取得処理
		}
		Log.d(TAG, "onCreate : " + "End");
		finish();
	}

	/**
	 * 周辺情報取得処理
	 */
	private void getAreaInfo() {
		Log.d(TAG, "response : " + "getAreaInfo@周辺情報取得処理 Start");
		
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
						Thread.sleep(1000); // 1秒Sleepする
						Log.d("Thread.sleep(1000)", " sleep");
					} catch (InterruptedException e) {
					}
					cnt++;
				}
				analysisGson_Contents(); // JSON解析処理
				int cntList = 0;
				
				extraContents_ID = null; // コンテンツID
				extraContents_Name = null; // コンテンツ名
				extraContents_Tel = null; // コンテンツ電話番号
				extraContents_URL = null; // コンテンツURL
				extraContents_Latitude = null; // コンテンツ経度
				extraContents_Loditude = null; // コンテンツ緯度

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
								Log.d(TAG, "サーバーから取得した表示URL : " + extraContents_URL + " : "+ cntList);
								
								if (ContentsInfo.getContentsDispList().size() == 0) { // 初回検知時
									sendNotification(extraContents_Name, extraContents_URL); // ノーティフィケーション実行処理
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
											Log.d("VolleyActivity", "表示済みコンテンツ情報 " + extraContents_URL);
										} else {
//											ContentsInfo.setContentsDispList(extraContents_ID);
//											sendNotification(extraContents_Name, extraContents_URL); // ノーティフィケーション実行処理
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
										sendNotification(extraContents_Name, extraContents_URL); // ノーティフィケーション実行処理
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
		Log.d(TAG, "response : " + "getAreaInfo@周辺情報取得処理 End");
	}

	/**
	 * （コンテンツ）JSON解析処理
	 */
	@SuppressWarnings("unchecked")
	private void analysisGson_Contents() {
		Log.d(TAG, "analysisGson_Contents : " + "Start");
		JsonResult sampleResult = new JsonResult();
		sampleResult = new Gson().fromJson(mResJson, JsonResult.class);
		sampleResult.response.get(Constant.MapKey.MAP_KEY_STATUS.toString());
		mContents = new ContentsInfo();
		mContents.setCode(sampleResult.response.get(Constant.MapKey.MAP_KEY_STATUS.toString()).get(Constant.MapKey.MAP_KEY_CODE.toString()).toString()); // 結果ステータス
		mContents.setMessage(sampleResult.response.get(Constant.MapKey.MAP_KEY_STATUS.toString()).get(Constant.MapKey.MAP_KEY_MESSAGE.toString()).toString()); // 結果メッセージ
		ArrayList<Object> list = new ArrayList<Object>();
		list = (ArrayList<Object>) sampleResult.response.get(Constant.MapKey.MAP_KEY_RESULT.toString()).get(Constant.MapKey.MAP_KEY_CONTENTS.toString());

		for (Object obj : list) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.putAll((Map<? extends String, ? extends String>) obj);
			mContents.setId(map.get(Constant.MapKey.MAP_KEY_ID.toString()));// ID
			mContents.setContentsID(map.get(Constant.MapKey.MAP_KEY_CONTENTS_ID.toString()));// コンテンツID
			mContents.setContentsName(map.get(Constant.MapKey.MAP_KEY_CONTENTS_NAME.toString()));// コンテンツ名
			mContents.setContentsAddress(map.get(Constant.MapKey.MAP_KEY_CONTENTS_ADDRESS.toString()));// コンテンツ住所
			mContents.setContentsTel(map.get(Constant.MapKey.MAP_KEY_CONTENTS_TEL.toString()));// コンテンツ電話番号
			mContents.setContentsMail(map.get(Constant.MapKey.MAP_KEY_CONTENTS_MAIL.toString()));// コンテンツメールアドレス
			mContents.setContentsUrl(map.get(Constant.MapKey.MAP_KEY_CONTENTS_URL.toString()));// コンテンツTEL番号
			mContents.setContentsLatitude(map.get(Constant.MapKey.MAP_KEY_CONTENTS_LATITUDE.toString()));// コンテンツFAX番号
			mContents.setContentsLongitude(map.get(Constant.MapKey.MAP_KEY_CONTENTS_LONGITUDE.toString()));// コンテンツ経度
			mContents.setContentsType(map.get(Constant.MapKey.MAP_KEY_CONTENTS_TYPE.toString()));// コンテンツ緯度
			mContents.setContentsMap();
			mContents.setContentsList();
		}
		Log.d(TAG, "analysisGson_Contents : " + "End");
	}

	/**
	 * ノーティフィケーション実行処理
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
		builder.setContentIntent(pendingIntent); // ノーティフィケーション テキスト設定

		if (contentsName == null) {
			builder.setTicker("NotificationSample");// ノーティフィケーション アイコン設定			
			builder.setContentTitle("NotificationTitle");// ノーティフィケーション サブタイトル設定
		} else {
			builder.setTicker(contentsName);// ノーティフィケーション アイコン設定			
			builder.setContentTitle(contentsName);// ノーティフィケーション サブタイトル設定
		}
		builder.setSmallIcon(R.drawable.ha_w);// ノーティフィケーション タイトル設定
		builder.setWhen(System.currentTimeMillis());// ノーティフィケーション 通知音・バイブ・ライト設定
		builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS);// ノーティフィケーションキャンセル操作タップ
		builder.setAutoCancel(true);

		try {
			mNotificationManager.notify(mNotificationID, builder.build());// Notificationを作成して通知
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mNotificationID++;
		Log.d(TAG, "sendNotification : " + "End");
	}
	
	/**
	 * Json返却値格納クラス
	 *
	 */
	public class JsonResult {
		public HashMap<String, HashMap<String, Object>> response;
	}
}
