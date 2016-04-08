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
 * �ړ��A�N�e�B�r�e�B
 *
 */
public class RootActivity extends Activity {

	private final String TAG = "MoveActivity"; // �^�O

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.root);
		Log.d(TAG, "onCreate: " + "Start");
		ListView listView_Tour = (ListView) findViewById(R.id.listView_Move);
		ArrayList<String> labelList = new ArrayList<String>();

//		for (int i = 1; i <= 20; i++) {	// TODO "List Item + ??"��20���X�g�ɒǉ�
//			labelList.add("�ړ��� " + i);
//		}

		// DummyData
		labelList.add("Narita Japan rail bus exchange points");
		labelList.add("Way to the Narita Express from Narita Airport");
		labelList.add("Chiba Station information ");
		labelList.add("Shibuya Station information ");
		labelList.add("Shinagawa Station information ");
		labelList.add("Shinjuku Station information ");
		labelList.add("Tokyo Station information ");

		
//		CustomAdapter customAdapter = new CustomAdapter(this, 0, labelList);
		CustomAdapter_Root customAdapter_root = new CustomAdapter_Root(this, 0, labelList);
		listView_Tour.setAdapter(customAdapter_root);
		listView_Tour.setDivider(null);
		listView_Tour.setOnItemClickListener(clickMoveList);	// �ړ��惊�X�g��������
		listView_Tour.setOnItemSelectedListener(selectMoveList);	// �ړ��惊�X�g�I������
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
			intent = new Intent(RootActivity.this, CheckUserInfoActivity.class);
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
	 * �ړ��惊�X�g��������
	 */
	ListView.OnItemClickListener clickMoveList = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
//			Toast.makeText(StayActivity.this, "onItemClick = " + item, Toast.LENGTH_LONG).show();
			ListView listView = (ListView) parent;
			String item = (String) listView.getItemAtPosition(position);
			
			Intent intent = new Intent(RootActivity.this, RootDetailActivity.class);
			intent.putExtra("position", position);
			startActivity(intent);
		}
	};

	/**
	 * �ړ��惊�X�g�I������
	 */
	ListView.OnItemSelectedListener selectMoveList = new AdapterView.OnItemSelectedListener() {
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
