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
 * 食事詳細アクティビティ
 * 
 */
public class RestaurantDetailActivity extends Activity {

	private final String TAG = "EatDetailActivity"; // タグ
	private ViewPager mViewPager; // ビューページャー
	static int extraPosition = 0;
	static int extraCategory = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate: " + "Start");
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.restaurant_detail);
		
		Intent intent = getIntent();
		extraPosition = intent.getIntExtra("position", 0);
		extraCategory = intent.getIntExtra("category", 0);
		
//		Intent intent.gutExtra("position");

		this.mViewPager = (ViewPager) this.findViewById(R.id.viewpager_eat);
		this.mViewPager.setAdapter(new PagerAdapter_Eat(this));
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
 * 食事 ページャーアダプタ
 * 
 */
class PagerAdapter_Eat extends PagerAdapter {

	private final String TAG = "EatDetailActivity"; // タグ
	private static final int TOTAL_PAGE = 6;
	private LayoutInflater mLayoutInflater; // レイアウトを作るやつ
	private Activity mParentActivity; // アクティビティ

	/**
	 * コンストラクタ
	 * @param context
	 */
	public PagerAdapter_Eat(final Activity activity) {
		Log.d(TAG, "PagerAdapter_Eat: " + "Start");
		this.mLayoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mParentActivity = activity;
		Log.d(TAG, "PagerAdapter_Eat: " + "End");
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
		
		if (RestaurantDetailActivity.extraPosition != 0){
			position = RestaurantDetailActivity.extraPosition;
			RestaurantDetailActivity.extraPosition = 0;
		}
		
		if (MainActivity.mSwitchLanguage == 0) {
			if (RestaurantDetailActivity.extraCategory == 0) {
				String[] urlList = {
						new String("file:///android_asset/shinjyuku_day1_english/eat/eat1/kanidouraku/kanidourakushinjukuhonten.html"),
//						new String("file:///android_asset/shinjyuku_day1_english/eat/eat1/snow_crab/zuwaigani.html"),
						new String("file:///android_asset/shinjyuku_day1_english/eat/eat2/cona/conashinjukueastexitstore.html"),
						new String("file:///android_asset/shinjyuku_day1_english/eat/eat2/syabuyakko/ordinationheadofficeshabu.html"),
						new String("file:///android_asset/shinjyuku_day1_english/eat/eat2/zuzu/ochazukebarzuzuprivateroomaway.html"), };
				webView.loadUrl(urlList[position % urlList.length]);
			} else if (RestaurantDetailActivity.extraCategory == 1) {
				String[] urlList = {
						new String("file:///android_asset/shinjyuku_day1_english/eat/eat2/china_moon/chinesechinamoonsanchomeshop.html"), };
				webView.loadUrl(urlList[position % urlList.length]);
			} else if (RestaurantDetailActivity.extraCategory == 2) {
				String[] urlList = {
						new String("file:///android_asset/shinjyuku_day1_english/eat/eat2/twist_poteto/twistpotatopotatotornado.html"), };
				webView.loadUrl(urlList[position % urlList.length]);
			}
//			String[] urlList = {
//					new String("file:///android_asset/shinjyuku_day1_thai/eat/eat1/snow_crab/zuwaigani.html"),
//					new String("file:///android_asset/shinjyuku_day1_thai/eat/eat2/china_moon/chinesechinamoonsanchomeshop.html"),
//					new String("file:///android_asset/shinjyuku_day1_thai/eat/eat2/cona/conashinjukueastexitstore.html"),
//					new String("file:///android_asset/shinjyuku_day1_thai/eat/eat2/syabuyakko/ordinationheadofficeshabu.html"),
//					new String("file:///android_asset/shinjyuku_day1_thai/eat/eat2/twist_poteto/twistpotatopotatotornado.html"),
//					new String("file:///android_asset/shinjyuku_day1_thai/eat/eat2/zuzu/ochazukebarzuzuprivateroomaway.html"), };
//				webView.loadUrl(urlList[position % urlList.length]);
		} else if (MainActivity.mSwitchLanguage == 1) {
				if (RestaurantDetailActivity.extraCategory == 0) {
					String[] urlList = {
							new String("file:///android_asset/shinjyuku_day1_thai/eat/eat1/kanidouraku/kanidourakushinjukuhonten.html"),
//							new String("file:///android_asset/shinjyuku_day1_thai/eat/eat1/snow_crab/zuwaigani.html"),
							new String("file:///android_asset/shinjyuku_day1_thai/eat/eat2/cona/conashinjukueastexitstore.html"),
							new String("file:///android_asset/shinjyuku_day1_thai/eat/eat2/syabuyakko/ordinationheadofficeshabu.html"),
							new String("file:///android_asset/shinjyuku_day1_thai/eat/eat2/zuzu/ochazukebarzuzuprivateroomaway.html"), };
					webView.loadUrl(urlList[position % urlList.length]);
				} else if (RestaurantDetailActivity.extraCategory == 1) {
					String[] urlList = {
							new String("file:///android_asset/shinjyuku_day1_thai/eat/eat2/china_moon/chinesechinamoonsanchomeshop.html"), };
					webView.loadUrl(urlList[position % urlList.length]);
				} else if (RestaurantDetailActivity.extraCategory == 2) {
					String[] urlList = {
							new String("file:///android_asset/shinjyuku_day1_thai/eat/eat2/twist_poteto/twistpotatopotatotornado.html"), };
					webView.loadUrl(urlList[position % urlList.length]);
				}
//				String[] urlList = {
//						new String("file:///android_asset/shinjyuku_day1_thai/eat/eat1/snow_crab/zuwaigani.html"),
//						new String("file:///android_asset/shinjyuku_day1_thai/eat/eat2/china_moon/chinesechinamoonsanchomeshop.html"),
//						new String("file:///android_asset/shinjyuku_day1_thai/eat/eat2/cona/conashinjukueastexitstore.html"),
//						new String("file:///android_asset/shinjyuku_day1_thai/eat/eat2/syabuyakko/ordinationheadofficeshabu.html"),
//						new String("file:///android_asset/shinjyuku_day1_thai/eat/eat2/twist_poteto/twistpotatopotatotornado.html"),
//						new String("file:///android_asset/shinjyuku_day1_thai/eat/eat2/zuzu/ochazukebarzuzuprivateroomaway.html"), };
//					webView.loadUrl(urlList[position % urlList.length]);
		} else if (MainActivity.mSwitchLanguage == 2) {
			if (RestaurantDetailActivity.extraCategory == 0) {
				String[] urlList = {
						new String("file:///android_asset/shinjyuku_day1/eat/eat1/kanidouraku/kanidourakushinjukuhonten.html"),
//						new String("file:///android_asset/shinjyuku_day1/eat/eat1/snow_crab/zuwaigani.html"),
						new String("file:///android_asset/shinjyuku_day1/eat/eat2/cona/conashinjukueastexitstore.html"),
						new String("file:///android_asset/shinjyuku_day1/eat/eat2/syabuyakko/ordinationheadofficeshabu.html"),
						new String("file:///android_asset/shinjyuku_day1/eat/eat2/zuzu/ochazukebarzuzuprivateroomaway.html"), };
				webView.loadUrl(urlList[position % urlList.length]);
			} else if (RestaurantDetailActivity.extraCategory == 1) {
				String[] urlList = {
						new String("file:///android_asset/shinjyuku_day1/eat/eat2/china_moon/chinesechinamoonsanchomeshop.html"), };
				webView.loadUrl(urlList[position % urlList.length]);
			} else if (RestaurantDetailActivity.extraCategory == 2) {
				String[] urlList = {
						new String("file:///android_asset/shinjyuku_day1/eat/eat2/twist_poteto/twistpotatopotatotornado.html"), };
				webView.loadUrl(urlList[position % urlList.length]);
			}
			
//			String[] urlList = {
//					new String("file:///android_asset/shinjyuku_day1/eat/eat1/snow_crab/zuwaigani.html"),
//					new String("file:///android_asset/shinjyuku_day1/eat/eat2/china_moon/chinesechinamoonsanchomeshop.html"),
//					new String("file:///android_asset/shinjyuku_day1/eat/eat2/cona/conashinjukueastexitstore.html"),
//					new String("file:///android_asset/shinjyuku_day1/eat/eat2/syabuyakko/ordinationheadofficeshabu.html"),
//					new String("file:///android_asset/shinjyuku_day1/eat/eat2/twist_poteto/twistpotatopotatotornado.html"),
//					new String("file:///android_asset/shinjyuku_day1/eat/eat2/zuzu/ochazukebarzuzuprivateroomaway.html"), };
//			webView.loadUrl(urlList[position % urlList.length]);
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