package com.lab.jti.thai;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

/**
 * Twitter���[�e�B���e�B�N���X
 */
public class TwitterUtils {

	private final static String TAG = "TwitterUtils"; // �^�O
	private static final String TWITTER_CONSUMER_KEY = "uR4syr23waEHWG7DJP9Ymw";
	private static final String TWITTER_CONSUMER_SECRET = "eOMRTwCV6M15DebwCZmGr6pTGZUg27wRwR0EizwLfk";
	private static final String TOKEN = "token";
	private static final String TOKEN_SECRET = "token_secret";
	private static final String ACCESS_TOKEN = "twitter_access_token";

	/**
	 * Twitter�C���X�^���X�擾���� �A�N�Z�X�g�[�N���ݒ菈��
	 * 
	 * @param context
	 * @return Twitter
	 */
	public static Twitter getTwitterInstance(Context context) {
		Log.d(TAG, "getTwitterInstance: " + "Start");
		TwitterFactory factory = new TwitterFactory();
		Twitter twitter = factory.getInstance();
		twitter.setOAuthConsumer(TWITTER_CONSUMER_KEY, TWITTER_CONSUMER_SECRET);

		if (hasAccessToken(context)) {
			twitter.setOAuthAccessToken(loadAccessToken(context));
		}
		Log.d(TAG, "getTwitterInstance: " + "End");
		return twitter;
	}

	/**
	 * �A�N�Z�X�g�[�N�� �v���t�@�����X�ۑ�����
	 * 
	 * @param context
	 * @param accessToken
	 */
	public static void storeAccessToken(Context context, AccessToken accessToken) {
		Log.d(TAG, "storeAccessToken: " + "Start");
		SharedPreferences sharedPreferences = context.getSharedPreferences(ACCESS_TOKEN, Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString(TOKEN, accessToken.getToken());
		editor.putString(TOKEN_SECRET, accessToken.getTokenSecret());
		editor.commit();
		Log.d(TAG, "storeAccessToken: " + "End");
	}

	/**
	 * �A�N�Z�X�g�[�N���@�v���t�@�����X�Ǎ�����
	 * 
	 * @param context
	 * @return AccessToken
	 */
	public static AccessToken loadAccessToken(Context context) {
		Log.d(TAG, "loadAccessToken: " + "Start");
		SharedPreferences preferences = context.getSharedPreferences(ACCESS_TOKEN, Context.MODE_PRIVATE);
		String token = preferences.getString(TOKEN, null);
		String tokenSecret = preferences.getString(TOKEN_SECRET, null);

		if (token != null && tokenSecret != null) {
			if (new AccessToken(token, tokenSecret).getScreenName() == null) {
				return null;
			}
			return new AccessToken(token, tokenSecret);
		}
		Log.d(TAG, "loadAccessToken: " + "End");
		return null;
	}

	/**
	 * �A�N�Z�X�g�[�N�� ���݃`�F�b�N���� <br>
	 * ���ݎ� �E�E�E true
	 */
	public static boolean hasAccessToken(Context context) {
		Log.d(TAG, "hasAccessToken: " + "Start");
		Log.d(TAG, "hasAccessToken: " + "End");
		return loadAccessToken(context) != null;
	}
}
