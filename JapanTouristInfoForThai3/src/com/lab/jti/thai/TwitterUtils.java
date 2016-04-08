package com.lab.jti.thai;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

/**
 * Twitterユーティリティクラス
 */
public class TwitterUtils {

	private final static String TAG = "TwitterUtils"; // タグ
	private static final String TWITTER_CONSUMER_KEY = "uR4syr23waEHWG7DJP9Ymw";
	private static final String TWITTER_CONSUMER_SECRET = "eOMRTwCV6M15DebwCZmGr6pTGZUg27wRwR0EizwLfk";
	private static final String TOKEN = "token";
	private static final String TOKEN_SECRET = "token_secret";
	private static final String ACCESS_TOKEN = "twitter_access_token";

	/**
	 * Twitterインスタンス取得処理 アクセストークン設定処理
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
	 * アクセストークン プリファレンス保存処理
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
	 * アクセストークン　プリファレンス読込処理
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
	 * アクセストークン 存在チェック処理 <br>
	 * 存在時 ・・・ true
	 */
	public static boolean hasAccessToken(Context context) {
		Log.d(TAG, "hasAccessToken: " + "Start");
		Log.d(TAG, "hasAccessToken: " + "End");
		return loadAccessToken(context) != null;
	}
}
