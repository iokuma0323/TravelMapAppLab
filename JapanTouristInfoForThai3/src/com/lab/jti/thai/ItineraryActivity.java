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

/**
 * 旅程アクティビティ
 *
 */
public class ItineraryActivity extends Activity {

	private final String TAG = "EatActivity"; // タグ

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.restaurant);
		Log.d(TAG, "onCreate: " + "Start");
		ListView listView_Tour = (ListView) findViewById(R.id.listView_Restaurant);
		ArrayList<String> labelList = new ArrayList<String>();

//		for (int i = 1; i <= 20; i++) {	// TODO "List Item + ??"を20個リストに追加
//			labelList.add("旅程 " + i);
//		}
		// DummyData
		labelList.add("ズワイガニの食べ方");
		labelList.add("中華料理チャイナムーン＜霞月樓＞新宿三丁目店");
		labelList.add("CONA新宿東口店");
		labelList.add("しゃぶ叙 本店");
		labelList.add("ツイストポテト（竜巻ポテト）");
		labelList.add("お茶漬けBAR・～離れ個室～ZUZU");
		
		CustomAdapter customAdapter = new CustomAdapter(this, 0, labelList);
		listView_Tour.setAdapter(customAdapter);
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
			intent = new Intent(ItineraryActivity.this, CheckUserInfoActivity.class);
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
	 * 旅程リスト押下処理
	 */
	ListView.OnItemClickListener clickEatList = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			ListView listView = (ListView) parent;
			String item = (String) listView.getItemAtPosition(position);
			
			Intent intent = new Intent(ItineraryActivity.this, RestaurantDetailActivity.class);
			intent.putExtra("position", position);
			startActivity(intent);
//			Toast.makeText(StayActivity.this, "onItemClick = " + item, Toast.LENGTH_LONG).show();
		}
	};

	/**
	 * 旅程リスト選択処理
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
