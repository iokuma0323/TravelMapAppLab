package com.lab.jti.thai;

import java.util.ArrayList;
import java.util.HashMap;

import com.lab.jti.thai.MapActivity.LocationInfo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

/**
 * 経路探索アクティビティ
 *
 */
public class RouteSearchActivity extends Activity {

	private final String TAG = "SearchActivity"; // タグ
	ListView mListViewGroupList;
	HashMap<String, String> dummyData_GeoMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate: " + "Start");

		// ダミーデータ設定処理
		setDummyData();
		
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		intent.setClassName("com.google.android.apps.maps","com.google.android.maps.MapsActivity");
//		intent.setData(Uri.parse("http://maps.google.com/maps?saddr=" + LocationInfo.getLatitude() + "," + LocationInfo.getLongitude() + " &daddr=" + LocationInfo.getLatitude() + "," + LocationInfo.getLongitude() +"&dirflg=r"));
		intent.setData(Uri.parse("http://maps.google.com/maps?saddr=35.69379,139.701744&daddr=35.689207,139.6993&dirflg=w"));
		startActivity(intent);
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
	 * ダミーデータ設定処理
	 */
	public void setDummyData() {
		super.onPause();
		Log.d(TAG, "setDummyData: " + "Start");
		LocationLog.setUserLatitude(35.660537);
		LocationLog.setUserLongitude(139.729243);
		
		dummyData_GeoMap = new HashMap<String, String>();
		dummyData_GeoMap.put("shop_latitude", String.valueOf(LocationLog.getUserLatitude()));
		dummyData_GeoMap.put("shop_longitude", String.valueOf(LocationLog.getUserLongitude()));

		Log.d(TAG, "setDummyData: " + "End");
	}
}
