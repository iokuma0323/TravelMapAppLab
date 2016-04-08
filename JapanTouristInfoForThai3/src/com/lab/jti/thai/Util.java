package com.lab.jti.thai;

import java.util.regex.Pattern;

public class Util {

	/**
	 * Property.
	 */
	private static String value;

	/**
	 * Accessor: value���擾���܂�.
	 * @return String
	 */
	public static String getValue() {
		return (value != null) ? value : "";
	}

	/**
	 * Accessor: value���Z�b�g���܂�.
	 * @param strValue
	 */
	public void setValue(String strValue) {
		this.value = strValue;
	}

//---------------------------------------------------------------------------------------------------- 
	
	/**
	 * �K�{�`�F�b�N�@String
	 * 
	 * @param value
	 * @return Null/�� �E�E�E true
	 */
	public static boolean isEmpty(String value) {
		if (value == null || value.length() == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * �d�b�ԍ��Ƃ��Đ������t�H�[�}�b�g���m�F���܂�.<br>
	 * �u-�v�i�n�C�t���j�t���̃t�H�[�}�b�g�ł���K�v������܂�.
	 * @return �`���O �E�E�E false
	 */
	public static boolean validatePhoneNumber() {
		String strPattern = "(?!^(090|080|070))(?=^\\d{2,5}?-\\d{1,4}?-\\d{4}$)[\\d-]{12}|" + "(?=^(090|080|070)-\\d{4}-\\d{4}$)[\\d-]{13}|" + "(?=^0120-\\d{2,3}-\\d{3,4})[\\d-]{12}|"
				+ "^0800-\\d{3}-\\d{4}";
		return Util.checkMatch(Util.getValue(), strPattern);
	}

	/**
	 * �d�b�ԍ��Ƃ��Đ������t�H�[�}�b�g���m�F���܂�.<br>
	 * �u-�v�i�n�C�t���j�����̃t�H�[�}�b�g�ł���K�v������܂�.
	 * @return �`���O �E�E�E false
	 */
	public static boolean validatePhoneNumberWithOutSeparator() {
		String strPattern = "(?!^(090|080|070))^[\\d]{10}|" + "^(090|080|070)[\\d]{8}";
		return Util.checkMatch(Util.getValue(), strPattern);
	}

	/**
	 * ���K�\���Ń}�b�`���邩�m�F���܂�.
	 * @param strTarget
	 * @param strPattern
	 * @return boolean
	 */
	protected static boolean checkMatch(String strTarget, String strPattern) {
		java.util.regex.Pattern objPattern;
		java.util.regex.Matcher objMatcher;

		if ((strTarget == null) || (strPattern == null)) {
			return false;
		}
		objPattern = Pattern.compile(strPattern);
		objMatcher = objPattern.matcher(strTarget);
		return objMatcher.matches();
	}
}
