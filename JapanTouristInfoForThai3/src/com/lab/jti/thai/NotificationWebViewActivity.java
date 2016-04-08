package com.lab.jti.thai;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

/**
 * �m�[�e�B�t�B�P�[�V����WEB�\�� �A�N�e�B�r�e�B
 */
public class NotificationWebViewActivity extends Activity {

	private final String TAG = "NotificationActivity"; // �^�O
	private WebView mWebView_notification; // WebView

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nortification);
		Log.d(TAG, "onCreate: " + "Start");
		Intent intent = getIntent();
		String contentsURL = intent.getStringExtra(Constant.Notification.NOTIFICATION_URL.toString());
		mWebView_notification = (WebView) findViewById(R.id.webView_notificaton);
		if (contentsURL == null) {
			Toast.makeText(this, "Can Not Be Displayed WEB", Toast.LENGTH_SHORT).show();
			Log.d(TAG, "onCreate: " + "WEB��\���ł��܂���");
			finish();
		}
		mWebView_notification.loadUrl(contentsURL);
		Log.d(TAG, "onCreate: " + "End");
	}
}
