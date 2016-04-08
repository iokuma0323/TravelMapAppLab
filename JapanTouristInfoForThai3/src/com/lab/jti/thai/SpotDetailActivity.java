package com.lab.jti.thai;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

/**
 * 観光詳細アクティビティ
 * 
 */
public class SpotDetailActivity extends Activity {

	private final String TAG = "TourDetailActivity"; // タグ
	private ViewPager mViewPager; // ビューページャー
	static int extraPosition = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate: " + "Start");
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.spot_detail);
		
		Intent intent = getIntent();
		extraPosition = intent.getIntExtra("position", 0);

		this.mViewPager = (ViewPager) this.findViewById(R.id.viewpager_tour);
		this.mViewPager.setAdapter(new PagerAdapter_Tour(this));
		Log.d(TAG, "onCreate: " + "End");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.d(TAG, "onCreateOptionsMenu: " + "Start");
		// getMenuInflater().inflate(R.menu.activity_try_view_pager00, menu);
		Log.d(TAG, "onCreateOptionsMenu: " + "End");
		return true;
	}
}

/**
 * 観光 ページャーアダプタ
 * 
 */
class PagerAdapter_Tour extends PagerAdapter {

	private final String TAG = "TourDetailActivity"; // タグ
	private static final int TOTAL_PAGE = 5;
	private LayoutInflater mLayoutInflater; // レイアウトを作るやつ
	private Activity mParentActivity; // アクティビティ

	/**
	 * コンストラクタ
	 * @param context
	 */
	public PagerAdapter_Tour(final Activity activity) {
		Log.d(TAG, "PagerAdapter_Tour: " + "Start");
		this.mLayoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mParentActivity = activity;
		Log.d(TAG, "PagerAdapter_Tour: " + "End");
	}

	/**
	 * ページ作成処理
	 * 
	 * @param container
	 * @param position
	 * @return
	 */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		Log.d(TAG, "instantiateItem: " + "Start");
		LinearLayout linearLayout = (LinearLayout) this.mLayoutInflater.inflate(R.layout.page0, null);
		Random random = new Random();
		linearLayout.setBackgroundColor(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
		container.addView(linearLayout);

		WebView webView = (WebView) linearLayout.findViewById(R.id.webView_page0);
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				Log.d(TAG, "shouldOverrideUrlLoading: " + "Start");
				return false;
			}
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				Log.d(TAG, "onPageStarted: " + "Start");
				mParentActivity.setProgressBarIndeterminateVisibility(true);
				super.onPageStarted(view, url, favicon);
				Log.d(TAG, "onPageStarted: " + "End");
			}
			@Override
			public void onPageFinished(WebView view, String url) {
				Log.d(TAG, "onPageFinished: " + "Start");
				mParentActivity.setProgressBarIndeterminateVisibility(false);
				super.onPageFinished(view, url);
				Log.d(TAG, "onPageFinished: " + "End");
			}
		});
		
		if (SpotDetailActivity.extraPosition != 0){
			position = SpotDetailActivity.extraPosition;
			SpotDetailActivity.extraPosition = 0;
		}
		
		if (MainActivity.mSwitchLanguage == 0) {
			String[] urlList = {
					new String("file:///android_asset/shinjyuku_day1_english/tour/shinjuku_gyoen/shinjukuimperialgarden.html"),
					new String("file:///android_asset/shinjyuku_day1_english/tour/southern_terrace/shinjukusouthernterrace.html"),
					new String("file:///android_asset/shinjyuku_day1_english/tour/the_artcomplex_center_of_tokyo/theartcomplexcenteroftokyo.html"),
					new String("file:///android_asset/shinjyuku_day1_english/tour/tokyo_government_office/tokyometropolitangovernmentobservationroom.html"),
					new String("file:///android_asset/shinjyuku_day1_english/tour/yoyogi_village_by_kurkku/yoyogivillagebykurkku.html"), };
			webView.loadUrl(urlList[position % urlList.length]);
		} else if (MainActivity.mSwitchLanguage == 1) {
			String[] urlList = {
					new String("file:///android_asset/shinjyuku_day1_thai/tour/shinjuku_gyoen/shinjukuimperialgarden.html"),
					new String("file:///android_asset/shinjyuku_day1_thai/tour/southern_terrace/shinjukusouthernterrace.html"),
					new String("file:///android_asset/shinjyuku_day1_thai/tour/the_artcomplex_center_of_tokyo/theartcomplexcenteroftokyo.html"),
					new String("file:///android_asset/shinjyuku_day1_thai/tour/tokyo_government_office/tokyometropolitangovernmentobservationroom.html"),
					new String("file:///android_asset/shinjyuku_day1_thai/tour/yoyogi_village_by_kurkku/yoyogivillagebykurkku.html"), };
				webView.loadUrl(urlList[position % urlList.length]);
		} else if (MainActivity.mSwitchLanguage == 2) {
			String[] urlList = {
					new String("file:///android_asset/shinjyuku_day1/tour/shinjuku_gyoen/shinjukuimperialgarden.html"),
					new String("file:///android_asset/shinjyuku_day1/tour/southern_terrace/shinjukusouthernterrace.html"),
					new String("file:///android_asset/shinjyuku_day1/tour/the_artcomplex_center_of_tokyo/theartcomplexcenteroftokyo.html"),
					new String("file:///android_asset/shinjyuku_day1/tour/tokyo_government_office/tokyometropolitangovernmentobservationroom.html"),
					new String("file:///android_asset/shinjyuku_day1/tour/yoyogi_village_by_kurkku/yoyogivillagebykurkku.html"), };
			webView.loadUrl(urlList[position % urlList.length]);
		}
		Log.d(TAG, "instantiateItem: " + "End");
		return linearLayout;
	}

	/**
	 * アイテム削除処理
	 */
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		Log.d(TAG, "destroyItem: " + "Start");
		ViewPager viewPager = (ViewPager) container;
		viewPager.removeView((View) object);
		Log.d(TAG, "destroyItem: " + "Start");
	}

	/**
	 * ページ数返却処理
	 */
	@Override
	public int getCount() {
		// TODO 自動生成されたメソッド・スタブ
		Log.d(TAG, "getCount: " + "Start");
		return TOTAL_PAGE;
	}

	/**
	 * ビュー比較処理
	 */
	@Override
	public boolean isViewFromObject(View view, Object object) {
		// TODO 自動生成されたメソッド・スタブ
		Log.d(TAG, "isViewFromObject: " + "Start");
		return view.equals(object);
	}
}