package com.lab.jti.thai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.maps.GeoPoint;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * �}�b�v�A�N�e�B�r�e�B (�}�b�v)�T�[�`���
 */
public class SearchActivity extends FragmentActivity implements LocationListener {

	private final String TAG = "SearchActivity"; // �^�O
	static boolean mIsGoogleMap = false; // �O�[�O���}�b�v���p�ۃt���O
	private static int mMapType = 0;
	private static int mMinTimeVal = 0;
	private static int mMinDistanceVal = 0;
	private static GoogleMap mGoogleMap; // �O�[�O���}�b�v
	private int mGooglePlayServicesStatus = 99; // GooglePlayServices�X�e�[�^�X
	private boolean mIsAvailabilityProvider = false; // �v���o�C�_���p�ۃt���O

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		Log.d(TAG, "onCreate: " + "Start");
		getLocaltionProvider(); // �ʒu���v���o�C�_�擾����
		mGooglePlayServicesStatus = setGooglePlayServices(); // GooglePlayServices�ݒ菈��
		setDispLocation(); // ���ݒn�\���ݒ菈��
		updateLocation(); // ���ݒn���X�V����
		setZoomLocation(15); // �Y�[���l�ݒ菈��
		Log.d(TAG, "onCreate: " + "End");
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d(TAG, "onResume: " + "Start");
		stopService(new Intent(MainActivity.mContext, SendRequestTimerService.class));
		startService(new Intent(MainActivity.mContext, SendRequestTimerService.class));
		Log.d(TAG, "onResume: " + "Start");
	}
	
	@Override
	public void onUserLeaveHint() {
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			stopService(new Intent(getBaseContext(), SendRequestTimerService.class));
			finish();
			return true;
		}
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.d(TAG, "onCreateOptionsMenu : " + "Start");
		getMenuInflater().inflate(R.menu.main, menu);
		menu.add(0, Constant.MENU_SERVER_START, 0, Constant.Menu.MENU_SERVER_START_TEXT.toString());
		menu.add(0, Constant.MENU_SERVER_STOP, 0, Constant.Menu.MENU_SERVER_STOP_TEXT.toString());
		menu.add(0, Constant.MENU_SETTING, 0, Constant.Menu.MENU_SETTING_TEXT.toString());
		menu.add(0, Constant.MENU_CHECK_USER_INFO, 0, Constant.Menu.MENU_CHECK_USER_INFO_TEXT.toString());
		menu.add(0, Constant.MENU_USER_REGIST, 0, Constant.Menu.MENU_USER_REGIST_TEXT.toString());
		menu.add(0, Constant.MENU_GROUP_REGIST, 0, Constant.Menu.MENU_GROUP_REGIST_TEXT.toString());
		menu.add(0, Constant.MENU_GROUP_PART, 0, Constant.Menu.MENU_GROUP_PART_TEXT.toString());
		menu.add(0, Constant.MENU_GROUP_SEARCH, 0, Constant.Menu.MENU_GROUP_SEARCH_TEXT.toString());
		menu.add(0, Constant.MENU_ROUTE_SEARCH, 0, Constant.Menu.MENU_ROUTE_SEARCH_TEXT.toString()); // �o�H����
		Log.d(TAG, "onCreateOptionsMenu : " + "End");
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem menuItem) {
		Log.d(TAG, "onOptionsItemSelected : " + "Start");

		Intent intent = null;
		switch (menuItem.getItemId()) {
		case Constant.MENU_SERVER_START:
			Log.d(TAG, "MENU_START : " + "Start");
			startService(new Intent(MainActivity.mContext, SendRequestTimerService.class));
			Toast.makeText(this, "Communication Start", Toast.LENGTH_SHORT).show();
			Log.d(TAG, "MENU_START : " + "End");
			return true;
		case Constant.MENU_SERVER_STOP:
			Log.d(TAG, "MENU_STOP : " + "Start");
			stopService(new Intent(MainActivity.mContext, SendRequestTimerService.class));
			Toast.makeText(this, "Communication Stop", Toast.LENGTH_SHORT).show();
			Log.d(TAG, "MENU_STOP : " + "End");
			return true;
		 case Constant.MENU_SETTING:
			 Log.d(TAG, "MENU_SETTING : " + "Start");
			 stopService(new Intent(MainActivity.mContext, SendRequestTimerService.class));
			 intent = new Intent(SearchActivity.this, SettingActivity.class);
			 startActivity(intent);
			 Log.d(TAG, "MENU_SETTING : " + "End");
			 return true;
		case Constant.MENU_CHECK_USER_INFO:
			Log.d(TAG, "MENU_START : " + "Start");
			intent = new Intent(SearchActivity.this, CheckUserInfoActivity.class);
			startActivity(intent);
			Log.d(TAG, "MENU_START : " + "End");
			return true;
		case Constant.MENU_USER_REGIST:
			Log.d(TAG, "MENU_USER_REGIST : " + "Start");
			if (MainActivity.mIsSharedPreferences == true) {
				Toast.makeText(this, "Registered User", Toast.LENGTH_SHORT).show();
			} else {
				stopService(new Intent(MainActivity.mContext, SendRequestTimerService.class));
				intent = new Intent(SearchActivity.this, RegistUserActivity.class);
				startActivity(intent);
			}
			Log.d(TAG, "MENU_USER_REGIST : " + "End");
			return true;
		case Constant.MENU_GROUP_REGIST:
			Log.d(TAG, "MENU_GROUP_REGIST : " + "Start");
			if (MainActivity.mIsSharedPreferences == true) {
				stopService(new Intent(MainActivity.mContext, SendRequestTimerService.class));
				intent = new Intent(SearchActivity.this, RegistGroupActivity.class);
				startActivity(intent);
			} else {
				Toast.makeText(this, "Unregistered User", Toast.LENGTH_SHORT).show();
			}
			Log.d(TAG, "MENU_GROUP_REGIST : " + "End");
			return true;
		case Constant.MENU_GROUP_PART:
			Log.d(TAG, "MENU_GROUP_PART : " + "Start");
			if (MainActivity.mIsSharedPreferences == true) {
				stopService(new Intent(MainActivity.mContext, SendRequestTimerService.class));
				intent = new Intent(SearchActivity.this, PartGroupActivity.class);
				startActivity(intent);
			} else {
				Toast.makeText(this, "Unregistered User", Toast.LENGTH_SHORT).show();
			}
			Log.d(TAG, "MENU_GROUP_PART : " + "End");
			return true;
		case Constant.MENU_GROUP_SEARCH:
			Log.d(TAG, "MENU_GROUP_SEARCH : " + "Start");
			if (MainActivity.mIsSharedPreferences == true) {
				stopService(new Intent(MainActivity.mContext, SendRequestTimerService.class));
				intent = new Intent(SearchActivity.this, SearchGroupActivity.class);
				startActivity(intent);
			} else {
				Toast.makeText(this, "Unregistered User", Toast.LENGTH_SHORT).show();
			}
			Log.d(TAG, "MENU_GROUP_SEARCH : " + "End");
			return true;
		case Constant.MENU_ROUTE_SEARCH:
			Log.d(TAG, "MENU_ROUTE_SEARCH : " + "Start");
			stopService(new Intent(MainActivity.mContext, SendRequestTimerService.class));
			intent = new Intent(SearchActivity.this, RouteSearchActivity.class);
			startActivity(intent);
			Log.d(TAG, "MENU_ROUTE_SEARCH : " + "End");
			return true;
		}
		Log.d(TAG, "onOptionsItemSelected : " + "End");
		return false;
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onLocationChanged : " + "Start");

		LocationInfo.setLatitude((float) location.getLatitude()); // ���݈ʒu�̈ܓx���擾
		LocationInfo.setLongitude((float) location.getLongitude()); // ���݈ʒu�̌o�x���擾
		LocationInfo.setLatLng();
		LocationInfo.setActionHistoryList(LocationInfo.getLatLng());

		if (mIsGoogleMap) {
			// Toast.makeText(this, "onLocationChanged = " + location.getLatitude() + " " + location.getLongitude(), Toast.LENGTH_SHORT).show();
			updateLocation(); // ���ݒn���X�V����
			setDispLocation(); // ���ݒn�\���ݒ菈��
			drawPolyLine_ActionHistory(); // �s�������|�����C���`�揈��
		}
		Log.d(TAG, "onLocationChanged : " + "location.getLatitude() = " + location.getLatitude() + " / " + "location.getLongitude() = " + location.getLongitude());
		Log.d(TAG, "onLocationChanged : " + "End");
	}

	@Override
	public void onProviderDisabled(String provider) {
		Log.d(TAG, "onProviderDisabled : " + "Start");
		Log.d(TAG, "onProviderDisabled : " + "End");
	}

	@Override
	public void onProviderEnabled(String provider) {
		Log.d(TAG, "onProviderEnabled : " + "Start");
		Log.d(TAG, "onProviderEnabled : " + "End");
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		Log.d(TAG, "response : " + "onStatusChanged Start");
		Log.d(TAG, "response : " + "onStatusChanged End");
	}

	/**
	 * �ʒu���v���o�C�_�擾����
	 */
	private void getLocaltionProvider() {
		Log.d(TAG, "getLocaltion : " + "Start");
		@SuppressWarnings("deprecation")
		String providerStatus = android.provider.Settings.Secure.getString(this.getContentResolver(), Secure.LOCATION_PROVIDERS_ALLOWED); // ���p�\�v���o�C�_���J���}��؂�Ŏ擾
		LocationInfo.mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE); // LocationManager�̎擾
		Location location = null; // Location

		if (providerStatus.matches(".*" + LocationManager.GPS_PROVIDER + ".*")) { // GPS����擾���J�n����
			LocationInfo.mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10, this);
			// LocationInfo.mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
			// 5000, 5, this);
			location = LocationInfo.mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if (location == null) { // �l�b�g���[�N����擾���J�n����
				LocationInfo.mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 10, this);
				location = LocationInfo.mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
				if (location != null) {
					mIsAvailabilityProvider = true;
				}
			} else {
				mIsAvailabilityProvider = true;
			}
		} else if (providerStatus.matches(".*" + LocationManager.NETWORK_PROVIDER + ".*")) { // �l�b�g���[�N����擾���J�n����
			LocationInfo.mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 10, this);
			location = LocationInfo.mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			mIsAvailabilityProvider = true;
		}
		if (!mIsAvailabilityProvider) {
			Toast.makeText(this, "Can not get location information", Toast.LENGTH_SHORT).show();
		}
		onLocationChanged(location);
		Log.d(TAG, "getLocaltion : " + "End");
	}

	/**
	 * GooglePlayServices�ݒ菈��
	 */
	public int setGooglePlayServices() {
		Log.d(TAG, "setGooglePlayServices: " + "Start");
		int googlePlayServicesStatus = GooglePlayServicesUtil.isGooglePlayServicesAvailable(MainActivity.mContext); // GooglePlayServices��Ԕ���

		if (googlePlayServicesStatus == ConnectionResult.SUCCESS) {
			mGooglePlayServicesStatus = ConnectionResult.SUCCESS;
			SupportMapFragment smf = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.search_map);
			mGoogleMap = smf.getMap(); // fragment����GoogleMap object���擾
			mGoogleMap.setMyLocationEnabled(true);
			mGoogleMap.setIndoorEnabled(true);

			if (mMapType == 1) {

			} else if (mMapType == 2) {

			} else if (mMapType == 3) {

			} else if (mMapType == 4) {

			} else if (mMapType == 5) {

			}

			mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			mGoogleMap.setTrafficEnabled(true);

			LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
			Criteria criteria = new Criteria();
			LocationInfo.setBestProvider(locationManager.getBestProvider(criteria, true));
			mIsGoogleMap = true;
		} else {
			mGooglePlayServicesStatus = 10;
			GooglePlayServicesUtil.getErrorDialog(googlePlayServicesStatus, this, mGooglePlayServicesStatus).show();
		}
		Log.d(TAG, "setGooglePlayServices : " + "End");
		return mGooglePlayServicesStatus;
	}

	/**
	 * ���ݒn���X�V����
	 */
	public static void updateLocation() {
		Log.d("SearchActivity", "updateLocation : " + "Start");
		while (true) {
			if ((int) LocationInfo.getLatitude() > 0 && (int) LocationInfo.getLongitude() > 0) {
				LocationInfo.setGeoPoint(new GeoPoint((int) LocationInfo.getLatitude(), (int) LocationInfo.getLongitude()));
				break;
			}
		}
		Log.d("SearchActivity", "updateLocation : " + "End");
	}

	/**
	 * ���ݒn�\���ݒ菈��
	 */
	public static void setDispLocation() {
		Log.d("SearchActivity", "dispLocation : " + "Start");
		mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(LocationInfo.getLatLng())); // GoogleMap�Ɍ��ݒn��\��
		Log.d("SearchActivity", "dispLocation : " + "End");
	}

	/**
	 * ���ݒn�\������
	 */
	public static void dispLocation() {
		Log.d("SearchActivity", "dispLocation : " + "Start");
		// ���ݒn�ݒ菈��
		setDispLocation();
		CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(LocationInfo.getLatitude(), LocationInfo.getLongitude())).zoom(17.0f).bearing(0).build();
		mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
		mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(LocationInfo.getLatLng())); // GoogleMap�Ɍ��ݒn��\��
		Log.d("SearchActivity", "dispLocation : " + "End");
	}

	/**
	 * �Y�[���l�ݒ菈��
	 */
	public static void setZoomLocation(int zoomTo) {
		Log.d("SearchActivity", "setZoomLocation : " + "Start");
		mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(zoomTo)); // GoogleMap��Zoom�l���w��
		Log.d("SearchActivity", "setZoomLocation : " + "End");
	}

	/**
	 * �t���O�`�揈��
	 * 
	 * @param map
	 */
	public static void drawContentsFlag(String title, String snippet, double latitude, double longitude, int iconColor) {
		Log.d("SearchActivity", "setContentsFlag : " + "Start");
		MarkerOptions options = new MarkerOptions();
		LatLng latLng = new LatLng(latitude, longitude);
		options.position(latLng);
		options.title(title);
		options.snippet(snippet);

		BitmapDescriptor icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET);
		if (iconColor == 0) {
			icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE);
		} else if (iconColor == 1) {
			icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
		} else if (iconColor == 2) {
			icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN);
		} else if (iconColor == 3) {
			icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
		} else if (iconColor == 4) {
			icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA);
		} else if (iconColor == 5) {
			icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);
		} else if (iconColor == 6) {
			icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
		} else if (iconColor == 7) {
			icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE);
		} else if (iconColor == 8) {
			icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET);
		} else {
			icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);
		}
		options.icon(icon);
		LocationInfo.setMarker(mGoogleMap.addMarker(options)); // �}�b�v�Ƀ}�[�J�[��ǉ�
		Log.d("SearchActivity", "setContentsFlag : " + "End");
		
//		// ���������O���[�v�Ɏ��_�����炷
//		CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latitude, longitude)).zoom(17.0f).bearing(0).build();
//		mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//		mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude))); // GoogleMap�Ɍ��ݒn��\��
	}

	/**
	 * �s�������|�����C���`�揈��
	 * 
	 */
	private void drawPolyLine_ActionHistory() {
		Log.d("SearchActivity", "drawPolyLine_ActionHistory : " + "Start");
		PolylineOptions polylineOptions = new PolylineOptions();
		float latitude = 0;
		float longitude = 0;

		for (LatLng actionHistory : LocationInfo.getActionHistoryList()) {
			float latitudeDiff = (float) (actionHistory.latitude - latitude);
			float longitudeDiff = (float) (actionHistory.longitude - longitude);
			Log.d("SearchActivity", "�擾�ʒu���� : " + "latitudeDiff = " + latitudeDiff + " / " + "longitudeDiff = " + longitudeDiff);

			if (Math.abs(latitudeDiff) >= 0.001 && Math.abs(longitudeDiff) >= 0.001) { // 100m�̍��قɂ��Ă͕`�悵�Ȃ�
				latitude = (float) actionHistory.latitude;
				longitude = (float) actionHistory.longitude;
				polylineOptions.add(actionHistory);
			}
		}
		mGoogleMap.addPolyline(polylineOptions.width(2).color(Color.RED));
		Log.d("SearchActivity", "drawPolyLine_ActionHistory : " + "End");
	}

	/**
	 * �s�������|�����C���`�揈��
	 */
	public static void drawPolyLine_SearchGroup() {
		PolylineOptions options = new PolylineOptions();
		options.add(new LatLng(LocationInfo.getLatitude(), LocationInfo.getLongitude()));
		options.add(new LatLng(LocationLog.getUserLatitude(), LocationLog.getUserLongitude()));
		options.color(Color.BLUE);
		options.width(4);
		mGoogleMap.addPolyline(options);
	}

	/**
	 * �ʒu���N���X
	 */
	public static class LocationInfo {

		private static double latitude; // �ܓx
		private static double longitude; // �o�x
		private static LatLng latLng; // �ܓx�o�x
		private static GeoPoint geoPoint; // GoogleMap���W
		private static Marker marker; // �}�[�J�[
		private static String bestProvider; // �v���o�C�_
		public static LocationManager mLocationManager; // LocationManager

		private static List<LatLng> actionHistoryList = new ArrayList<LatLng>();

		private Location location;

		public static double getLatitude() {
			return latitude;
		}

		public static void setLatitude(float lati) {
			latitude = lati;
		}

		public static double getLongitude() {
			return longitude;
		}

		public static void setLongitude(float longi) {
			longitude = longi;
		}

		public static LatLng getLatLng() {
			return latLng;
		}

		public static void setLatLng() {
			latLng = new LatLng(latitude, longitude);
		}

		public GeoPoint getGeoPoint() {
			return geoPoint;
		}

		public static void setGeoPoint(GeoPoint _geoPoint) {
			geoPoint = _geoPoint;
		}

		public Marker getMarker() {
			return marker;
		}

		public static void setMarker(Marker _marker) {
			marker = _marker;
		}

		public String getBestProvider() {
			return bestProvider;
		}

		public static void setBestProvider(String _bestProvider) {
			bestProvider = _bestProvider;
		}

		public static List<LatLng> getActionHistoryList() {
			return actionHistoryList;
		}

		public static void setActionHistoryList(LatLng actionHistory) {
			actionHistoryList.add(actionHistory);
		}

		public Location getLocation() {
			return location;
		}

		public void setLocation(Location location) {
			this.location = location;
		}
	}

	/**
	 * �R���e���c���N���X
	 */
	public static class ContentsInfo {

		private String code; // ���ʃX�e�[�^�X
		private String message; // ���ʃ��b�Z�[�W
		private String id; // ID
		private String contentsID; // �R���e���cID
		private String contentsName;// �R���e���c��
		private String contentsAddress;// �R���e���c�Z��
		private String contentsTel;// �R���e���c�d�b�ԍ�
		private String contentsMail;// �R���e���c���[���A�h���X
		private String contentsUrl;// �R���e���cURL
		private String contentsLatitude;// �R���e���c�ܓx
		private String contentsLongitude;// �R���e���c�o�x
		private String contentsType;// �R���e���c�^�C�v
		private HashMap<String, String> contentsMap = new HashMap<String, String>(); // �R���e���c�}�b�v
		private List<HashMap<String, String>> contentsList = new ArrayList<HashMap<String, String>>(); // �R���e���c���X�g
		private static List<String> contentsDispList = new ArrayList<String>(); // �R���e���c�\���σ��X�g

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getContentsID() {
			return contentsID;
		}

		public void setContentsID(String contentsID) {
			this.contentsID = contentsID;
		}

		public String getContentsName() {
			return contentsName;
		}

		public void setContentsName(String contentsName) {
			this.contentsName = contentsName;
		}

		public String getContentsAddress() {
			return contentsAddress;
		}

		public void setContentsAddress(String contentsAddress) {
			this.contentsAddress = contentsAddress;
		}

		public String getContentsTel() {
			return contentsTel;
		}

		public void setContentsTel(String contentsTel) {
			this.contentsTel = contentsTel;
		}

		public String getContentsMail() {
			return contentsMail;
		}

		public void setContentsMail(String contentsMail) {
			this.contentsMail = contentsMail;
		}

		public String getContentsUrl() {
			return contentsUrl;
		}

		public void setContentsUrl(String contentsUrl) {
			this.contentsUrl = contentsUrl;
		}

		public String getContentsLatitude() {
			return contentsLatitude;
		}

		public void setContentsLatitude(String contentsLatitude) {
			this.contentsLatitude = contentsLatitude;
		}

		public String getContentsLongitude() {
			return contentsLongitude;
		}

		public void setContentsLongitude(String contentsLongitude) {
			this.contentsLongitude = contentsLongitude;
		}

		public String getContentsType() {
			return contentsType;
		}

		public void setContentsType(String contentsType) {
			this.contentsType = contentsType;
		}

		public HashMap<String, String> getContentsMap() {
			return contentsMap;
		}

		public void setContentsMap() {
			contentsMap = new HashMap<String, String>();
			contentsMap.put(Constant.MapKey.MAP_KEY_CONTENTS_ID.toString(), getContentsID());
			contentsMap.put(Constant.MapKey.MAP_KEY_CONTENTS_NAME.toString(), getContentsName());
			contentsMap.put(Constant.MapKey.MAP_KEY_CONTENTS_ADDRESS.toString(), getContentsAddress());
			contentsMap.put(Constant.MapKey.MAP_KEY_CONTENTS_TEL.toString(), getContentsTel());
			contentsMap.put(Constant.MapKey.MAP_KEY_CONTENTS_MAIL.toString(), getContentsMail());
			contentsMap.put(Constant.MapKey.MAP_KEY_CONTENTS_URL.toString(), getContentsUrl());
			contentsMap.put(Constant.MapKey.MAP_KEY_CONTENTS_LATITUDE.toString(), getContentsLatitude());
			contentsMap.put(Constant.MapKey.MAP_KEY_CONTENTS_LONGITUDE.toString(), getContentsLongitude());
			contentsMap.put(Constant.MapKey.MAP_KEY_CONTENTS_TYPE.toString(), getContentsType());
		}

		public List<HashMap<String, String>> getContentsList() {
			return contentsList;
		}

		public void setContentsList() {
			contentsList.add(getContentsMap());
		}

		public static List<String> getContentsDispList() {
			return contentsDispList;
		}

		public static void setContentsDispList(String contentsDisp) {
			contentsDispList.add(contentsDisp);
		}
	}

}
