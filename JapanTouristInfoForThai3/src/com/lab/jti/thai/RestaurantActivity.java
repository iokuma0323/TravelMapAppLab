package com.lab.jti.thai;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 食事アクティビティ
 */
public class RestaurantActivity extends Activity {

	private final String TAG = "EatActivity"; // タグ

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.restaurant);
		Log.d(TAG, "onCreate: " + "Start");
		ListView listView_Tour = (ListView) findViewById(R.id.listView_Restaurant);
		ArrayList<String> labelList = new ArrayList<String>();

//		for (int i = 1; i <= 20; i++) {	// TODO "List Item + ??"を20個リストに追加
//			labelList.add("食事先 " + i);
//		}
		// DummyData
//		labelList.add("How to eat snow crab ");
//		labelList.add("China Moon Chinese Sanchome shop ");
//		labelList.add("CONA Shinjyuku East Exit");
//		labelList.add("Shabujyo");
//		labelList.add("Twist potato (potato tornado)");
//		labelList.add("Ochazuke BAR ZUZU ~private room away~");
		
		
		labelList.add("Japanese Food");
		labelList.add("Chinese Food");
		labelList.add("French Food");
		
		CustomAdapter_Restaurant customAdapter_Restaurant = new CustomAdapter_Restaurant(this, 0, labelList);
		listView_Tour.setAdapter(customAdapter_Restaurant);
		listView_Tour.setDivider(null);
		listView_Tour.setOnItemClickListener(clickEatList);	// 食事先リスト押下処理
		listView_Tour.setOnItemSelectedListener(selectEatList);	// 食事先リスト選択処理
		Log.d(TAG, "onCreate : " + "End");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume : " + "Start");
		Log.d(TAG, "onResume : " + "End");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.d(TAG, "onCreateOptionsMenu : " + "Start");
		getMenuInflater().inflate(R.menu.main, menu);
		menu.add(0, Constant.MENU_CHECK_USER_INFO, 0, Constant.Menu.MENU_CHECK_USER_INFO_TEXT.toString());
		menu.add(0, Constant.MENU_SWITCH_LANGUAGE, 0, Constant.Menu.MENU_SWITCH_LANGUAGE_TEXT.toString());
		Log.d(TAG, "onCreateOptionsMenu : " + "End");
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		Log.d(TAG, "onOptionsItemSelected : " + "Start");
		
		Intent intent = null;
		switch (menuItem.getItemId()) {
		case Constant.MENU_CHECK_USER_INFO:
			Log.d(TAG, "MENU_START : " + "Start");
			intent = new Intent(RestaurantActivity.this, CheckUserInfoActivity.class);
			startActivity(intent);
			Log.d(TAG, "MENU_START : " + "End");
			return true;
		case Constant.MENU_SWITCH_LANGUAGE:
			Log.d(TAG, "MENU_START : " + "Start");
			// 言語切替ダイアログ表示処理
			MainActivity.switchLanguageDialog();			
			Log.d(TAG, "MENU_START : " + "End");
			return true;
		}
		Log.d(TAG, "onOptionsItemSelected : " + "End");
		return false;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.d(TAG, "onActivityResult : " + "Start");
		Log.d(TAG, "onActivityResult : " + "End");
	}

	/**
	 * 食事先リスト押下処理
	 */
	ListView.OnItemClickListener clickEatList = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			ListView listView = (ListView) parent;
			String item = (String) listView.getItemAtPosition(position);
			
//			Intent intent = new Intent(RestaurantActivity.this, RestaurantDetailActivity.class);
			Intent intent = new Intent(RestaurantActivity.this, RestaurantCategoryActivity.class);
			intent.putExtra("position", position);
			intent.putExtra("category", position);
			startActivity(intent);
//			Toast.makeText(StayActivity.this, "onItemClick = " + item, Toast.LENGTH_LONG).show();
		}
	};

	/**
	 * 食事先リスト選択処理
	 */
	ListView.OnItemSelectedListener selectEatList = new AdapterView.OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			ListView listView = (ListView) parent;
			String item = (String) listView.getSelectedItem();
//			Toast.makeText(StayActivity.this, "onItemSelected = " + item, Toast.LENGTH_LONG).show();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
		}
	};
}
