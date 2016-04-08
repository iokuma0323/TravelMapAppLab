package com.lab.jti.thai;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * 宿泊アクティビティ
 *
 */
public class HotelActivity extends Activity {

	private final String TAG = "StayActivity"; // タグ

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hotel);
		Log.d(TAG, "onCreate: " + "Start");
		ListView listView_Tour = (ListView) findViewById(R.id.listView_Stay);
		ArrayList<String> labelList = new ArrayList<String>();

//		for (int i = 1; i <= 20; i++) {	// TODO "List Item + ??"を20個リストに追加
//			labelList.add("宿泊先 " + i);
//		}
		// DummyData
//		labelList.add("ハイアットリージェンシー東京");
//		labelList.add("京王プラザホテル");
//		labelList.add("西新宿ホテルマイステイズ");
//		labelList.add("新宿駅から新宿プリンスホテルまでの道のり");
//		labelList.add("ヴィアイン新宿");
		
		labelList.add("Way to the Shinjuku Prince Hotel from Shinjuku Station");
		labelList.add("Hyatt Regency Tokyo");
		labelList.add("Keio Plaza Hotel");
		labelList.add("Nishi Shinjuku Hotel Mystays ");
		labelList.add("Via Inn Shinjuku");
		
		
//		CustomAdapter customAdapter = new CustomAdapter(this, 0, labelList);
		CustomAdapter_Hotel customAdapter_hotel = new CustomAdapter_Hotel(this, 0, labelList);
		listView_Tour.setAdapter(customAdapter_hotel);
		listView_Tour.setDivider(null);
		listView_Tour.setOnItemClickListener(clickStayList);	// 宿泊先リスト押下処理
		listView_Tour.setOnItemSelectedListener(selectStayList);	// 宿泊先リスト選択処理
		Log.d(TAG, "onCreate : " + "End");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume : " + "Start");
		Log.d(TAG, "onResume : " + "End");
	}

	public boolean onOptionsItemSelected(MenuItem menuItem) {
		Log.d(TAG, "onOptionsItemSelected : " + "Start");
		
		Intent intent = null;
		switch (menuItem.getItemId()) {
		case Constant.MENU_CHECK_USER_INFO:
			Log.d(TAG, "MENU_START : " + "Start");
			intent = new Intent(HotelActivity.this, CheckUserInfoActivity.class);
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
	 * 宿泊先リスト押下処理
	 */
	ListView.OnItemClickListener clickStayList = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			ListView listView = (ListView) parent;
			String item = (String) listView.getItemAtPosition(position);
			
			Intent intent = new Intent(HotelActivity.this, HotelDetailActivity.class);
			intent.putExtra("position", position);
			startActivity(intent);
//			Toast.makeText(StayActivity.this, "onItemClick = " + item, Toast.LENGTH_LONG).show();
		}
	};

	/**
	 * 宿泊先リスト選択処理
	 */
	ListView.OnItemSelectedListener selectStayList = new AdapterView.OnItemSelectedListener() {
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
