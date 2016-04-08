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
 * �ό��A�N�e�B�r�e�B 
 *
 */
public class SpotActivity extends Activity {

	private final String TAG = "TourActivity"; // �^�O

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.spot);
		Log.d(TAG, "onCreate: " + "Start");
		ListView listView_Tour = (ListView) findViewById(R.id.listView_Tour);
		ArrayList<String> labelList = new ArrayList<String>();

//		for (int i = 1; i <= 20; i++) {	// TODO "List Item + ??"��20���X�g�ɒǉ�
//			labelList.add("�ό��� " + i);
//		}
		// DummyData
		labelList.add("Shinjuku Gyoen");
		labelList.add("Shinjuku Southern Terrace");
		labelList.add("TThe Artcomplex Center Of Tokyo");
		labelList.add("Tokyo Metropolitan Government observation room");
		labelList.add("Yoyogi VILLAGE by Kurkku");

		CustomAdapter_Spot customAdapter_spot = new CustomAdapter_Spot(this, 0, labelList);
		listView_Tour.setAdapter(customAdapter_spot);
		listView_Tour.setDivider(null);
		listView_Tour.setOnItemClickListener(clickTourList);	// �c�A�[���X�g��������
		listView_Tour.setOnItemSelectedListener(selectTourList);	// �c�A�[���X�g�I������
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
			intent = new Intent(SpotActivity.this, CheckUserInfoActivity.class);
			startActivity(intent);
			Log.d(TAG, "MENU_START : " + "End");
			return true;
		case Constant.MENU_SWITCH_LANGUAGE:
			Log.d(TAG, "MENU_START : " + "Start");
			// ����ؑփ_�C�A���O�\������
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
	 * �c�A�[���X�g��������
	 */
	ListView.OnItemClickListener clickTourList = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			ListView listView = (ListView) parent;
			String item = (String) listView.getItemAtPosition(position);
			
			Intent intent = new Intent(SpotActivity.this, SpotDetailActivity.class);
			intent.putExtra("position", position);
			startActivity(intent);
//			Toast.makeText(TourActivity.this, "onItemClick = " + item, Toast.LENGTH_LONG).show();
		}
	};

	/**
	 * �c�A�[���X�g�I������
	 */
	ListView.OnItemSelectedListener selectTourList = new AdapterView.OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			ListView listView = (ListView) parent;
			String item = (String) listView.getSelectedItem();
//			Toast.makeText(TourActivity.this, "onItemSelected = " + item,�@Toast.LENGTH_LONG).show();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
		}
	};
}
