package com.lab.jti.thai;

/**
 * �ʒu��񃍃O�N���X
 */
public class LocationLog {

	private String id; // ID
	private String userID; // ���[�U�[ID
	private String groupID; // �O���[�vID
	private static double userLatitude; // ���[�U�[�ܓx
	private static double userLongitude; // ���[�U�[�o�x

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
