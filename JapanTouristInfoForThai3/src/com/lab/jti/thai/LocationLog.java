package com.lab.jti.thai;

/**
 * 位置情報ログクラス
 */
public class LocationLog {

	private String id; // ID
	private String userID; // ユーザーID
	private String groupID; // グループID
	private static double userLatitude; // ユーザー緯度
	private static double userLongitude; // ユーザー経度

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	public static double getUserLatitude() {
		return userLatitude;
	}

	public static void setUserLatitude(double userLatitude_) {
		userLatitude = userLatitude_;
	}

	public static double getUserLongitude() {
		return userLongitude;
	}

	public static void setUserLongitude(double userLongitude_) {
		userLongitude = userLongitude_;
	}
}
