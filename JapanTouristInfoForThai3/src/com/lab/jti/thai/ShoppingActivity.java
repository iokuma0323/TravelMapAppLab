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
 * �����A�N�e�B�r�e�B
 *
 */
public class ShoppingActivity extends Activity {

	private final String TAG = "ShoppingActivity"; // �^�O

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shopping);
		Log.d(TAG, "onCreate: " + "Start");
		ListView listView_Tour = (ListView) findViewById(R.id.listView_Shopping);
		ArrayList<String> labelList = new ArrayList<String>();

//		for (int i = 1; i <= 20; i++) {	// TODO "List Item + ??"��20���X�g�ɒǉ�
//			labelList.add("������ " + i);
//		}
		
		// DummyData
		labelList.add("Don Quixote Shinjuku East Exit ");
		labelList.add("Lumine Shinjuku");
		labelList.add("Shinjuku Alta ");
		labelList.add("Bikkuro Shinjuku East Exit ");
		labelList.add("Matsumotokiyoshi Shinjuku East Exit ");

		CustomAdapter_Shopping customAdapter_shopping = new CustomAdapter_Shopping(this, 0, labelList);
		listView_Tour.setAdapter(customAdapter_shopping);
		listView_Tour.setDivider(null);
		listView_Tour.setOnItemClickListener(clickShoppingList);	// �����惊�X�g��������
		listView_Tour.setOnItemSelectedListener(selectShoppingList);	// �����惊�X�g�I������
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
			intent = new Intent(ShoppingActivity.this, CheckUserInfoActivity.class);
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
	 * �����惊�X�g��������
	 */
	ListView.OnItemClickListener clickShoppingList = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			ListView listView = (ListView) parent;
			String item = (String) listView.getItemAtPosition(position);
			
			Intent intent = new Intent(ShoppingActivity.this, ShoppingDetailActivity.class);
			intent.putExtra("position", position);
			startActivity(intent);
			
//			Toast.makeText(StayActivity.this, "onItemClick = " + item, Toast.LENGTH_LONG).show();
		}
	};

	/**
	 * �����惊�X�g�I������
	 */
	ListView.OnItemSelectedListener selectShoppingList = new AdapterView.OnItemSelectedListener() {
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
