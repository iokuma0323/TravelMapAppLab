package com.lab.jti.thai;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * twitter OAuth�F�؏���
 */
public class TwitterOAuthActivity extends Activity {

	private final String TAG = "TwitterOAuthActivity"; // �^�O
	private static final String TWITTER_CALLBACK_URL = "gabu://twitter"; // twitter�R�[���o�b�NURL
	private Twitter mTwitter; // twitter
	private RequestToken mRequestToken; // ���N�G�X�g�g�[�N��

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.twitter_oauth);
		Log.d(TAG, "onCreate: " + "Start");
		mTwitter = TwitterUtils.getTwitterInstance(this);
		OAuth_Twitter(); // OAuth�F�؏���
		Log.d(TAG, "onCreate: " + "End");
	}

	/**
	 * OAuth�F�؏���
	 */
	private void OAuth_Twitter() {
		Log.d(TAG, "OAuth_Twitter: " + "Start");
		AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... params) {
				Log.d(TAG, "doInBackground: " + "Start");
				try {
					mRequestToken = mTwitter.getOAuthRequestToken(TWITTER_CALLBACK_URL);
					return mRequestToken.getAuthorizationURL();
				} catch (TwitterException e) {
					e.printStackTrace();
					stopService(new Intent(getBaseContext(), SendRequestTimerService.class)); // ���N�G�X�g�^�C�}�[����
																								// ��~
				}
				Log.d(TAG, "doInBackground: " + "End");
				return null;
			}

			@Override
			protected void onPostExecute(String url) {
				Log.d(TAG, "onPostExecute: " + "Start");
				if (url != null) {
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
					startActivity(intent);
				} else {
					Log.d(TAG, "onPostExecute: " + "�F�؎��s");
					stopService(new Intent(getBaseContext(), SendRequestTimerService.class)); // ���N�G�X�g�^�C�}�[����
																								// ��~
				}
				Log.d(TAG, "onPostExecute: " + "End");
			}
		};
		task.execute();
		Log.d(TAG, "OAuth_Twitter: " + "End");
	}

	@Override
	public void onNewIntent(Intent intent) {
		Log.d(TAG, "onNewIntent: " + "Start");
		if (intent == null || intent.getData() == null || !intent.getData().toString().startsWith(TWITTER_CALLBACK_URL)) {
			return;
		}
		String verifier = intent.getData().getQueryParameter("oauth_verifier");

		AsyncTask<String, Void, AccessToken> task = new AsyncTask<String, Void, AccessToken>() {
			@Override
			protected AccessToken doInBackground(String... params) {
				Log.d(TAG, "doInBackground: " + "Start");
				try {
					return mTwitter.getOAuthAccessToken(mRequestToken, params[0]);
				} catch (TwitterException e) {
					e.printStackTrace();
					stopService(new Intent(getBaseContext(), SendRequestTimerService.class)); // ���N�G�X�g�^�C�}�[����
																								// ��~
				}
				Log.d(TAG, "doInBackground: " + "End");
				return null;
			}

			@Override
			protected void onPostExecute(AccessToken accessToken) {
				Log.d(TAG, "onPostExecute: " + "Start");
				if (accessToken != null) {
					showToast("�F�ؐ���");
					Log.d(TAG, "onPostExecute: " + "�F�ؐ���");
					successOAuth(accessToken);
				} else {
					showToast("�F�؎��s");
					Log.d(TAG, "onPostExecute: " + "�F�؎��s");
					stopService(new Intent(getBaseContext(), SendRequestTimerService.class)); // ���N�G�X�g�^�C�}�[����
																								// ��~
				}
				Log.d(TAG, "onPostExecute: " + "End");
			}
		};
		task.execute(verifier);
		Log.d(TAG, "onNewIntent: " + "End");
	}

	/**
	 * OAuth�F�ؐ���������
	 * 
	 * @param accessToken
	 */
	private void successOAuth(AccessToken accessToken) {
		Log.d(TAG, "successOAuth: " + "Start");
		TwitterUtils.storeAccessToken(this, accessToken);
		accessToken.getUserId(); // TODO ��ӏ���
		Intent intent = new Intent(this, RegistUserActivity.class);
		startActivity(intent);
		finish();
		Log.d(TAG, "successOAuth: " + "End");
	}

	/**
	 * �g�[�X�g�\������
	 */
	private void showToast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}
}
