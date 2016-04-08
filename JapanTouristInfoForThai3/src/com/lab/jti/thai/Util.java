package com.lab.jti.thai;

import java.util.regex.Pattern;

public class Util {

	/**
	 * Property.
	 */
	private static String value;

	/**
	 * Accessor: valueを取得します.
	 * @return String
	 */
	public static String getValue() {
		return (value != null) ? value : "";
	}

	/**
	 * Accessor: valueをセットします.
	 * @param strValue
	 */
	public void setValue(String strValue) {
		this.value = strValue;
	}

//---------------------------------------------------------------------------------------------------- 
	
	/**
	 * 必須チェック　String
	 * 
	 * @param value
	 * @return Null/空 ・・・ true
	 */
	public static boolean isEmpty(String value) {
		if (value == null || value.length() == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 電話番号として正しいフォーマットか確認します.<br>
	 * 「-」（ハイフン）付きのフォーマットである必要があります.
	 * @return 形式外 ・・・ false
	 */
	public static boolean validatePhoneNumber() {
		String strPattern = "(?!^(090|080|070))(?=^\\d{2,5}?-\\d{1,4}?-\\d{4}$)[\\d-]{12}|" + "(?=^(090|080|070)-\\d{4}-\\d{4}$)[\\d-]{13}|" + "(?=^0120-\\d{2,3}-\\d{3,4})[\\d-]{12}|"
				+ "^0800-\\d{3}-\\d{4}";
		return Util.checkMatch(Util.getValue(), strPattern);
	}

	/**
	 * 電話番号として正しいフォーマットか確認します.<br>
	 * 「-」（ハイフン）無しのフォーマットである必要があります.
	 * @return 形式外 ・・・ false
	 */
	public static boolean validatePhoneNumberWithOutSeparator() {
		String strPattern = "(?!^(090|080|070))^[\\d]{10}|" + "^(090|080|070)[\\d]{8}";
		return Util.checkMatch(Util.getValue(), strPattern);
	}

	/**
	 * 正規表現でマッチするか確認します.
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
