package com.lab.jti.thai;

/**
 * 定数定義インタフェース
 */
public interface Constant {

	/**
	 * API
	 */
	public static enum API {
		API_REGIST_USER("http://dev.airdocs.jp/his/adduser.php"), // ユーザー登録
		
		API_GET_GROUPS("http://dev.airdocs.jp/his/getgroups.php"), // グループリスト取得
		API_ADD2G("http://dev.airdocs.jp/his/addu2g.php"), // グループ参加

		
		
		GENDER_FEMALE_VALUE("SPE_UserTel"), ; // 性別 女性
		private final String gender;
		private API(final String gender) {
			this.gender = gender;
		}
		@Override
		public String toString() {
			return this.gender;
		}
	}
	
	
	/**
	 * Map Key
	 */
	public static enum MapKey {
		MAP_KEY_RESPONSE("response"), // レスポンス
		MAP_KEY_STATUS("status"), // ステータス
		MAP_KEY_CODE("code"), // （レスポンス）コード
		MAP_KEY_MESSAGE("message"), // （レスポンス）コンテンツメッセージ
		MAP_KEY_RESULT("result"), // リザルト
		MAP_KEY_GROUPS("groups"),	// グループス
		MAP_KEY_USERS("users"),	// ユーザーズ
		MAP_KEY_CONTENTS("contents"),	// コンテンツ
		MAP_KEY_ID("id"), // ID
		MAP_KEY_USER_ID("user_id"), // ユーザーID
		MAP_KEY_USER_NAME("user_name"), // ユーザー名
		MAP_KEY_GENDER("gender"), // 性別
		MAP_KEY_AGE("age"), // 年齢
		MAP_KEY_TEL("tel"), // 電話番号
		MAP_KEY_USER_LATITUDE("user_latitude"),	// ユーザー緯度
		MAP_KEY_USER_LONGITUDE("user_longitude"),	// ユーザー経度
		MAP_KEY_GROUP_ID("group_id"),	// グループリーダーID
		MAP_KEY_GROUP_LEADER_ID("group_leader_id"),	// グループリーダーID
		MAP_KEY_GROUP_NAME("group_name"), // グループ名ID
		MAP_KEY_CONTENTS_ID("contents_id"),	// コンテンツID
		MAP_KEY_CONTENTS_NAME("contents_name"),	// コンテンツ名
		MAP_KEY_CONTENTS_ADDRESS("contents_address"),	// コンテンツ住所
		MAP_KEY_CONTENTS_TYPE("contents_type"),	// コンテンツタイプ
		MAP_KEY_CONTENTS_TEL("contents_tel"),	// コンテンツ電話番号
		MAP_KEY_CONTENTS_FAX("contents_fax"),	// コンテンツFAX番号
		MAP_KEY_CONTENTS_MAIL("contents_mail"),	// コンテンツメールアドレス
		MAP_KEY_CONTENTS_URL("contents_url"),	// コンテンツURL
		MAP_KEY_CONTENTS_LATITUDE("contents_latitude"),	// コンテンツ緯度
		MAP_KEY_CONTENTS_LONGITUDE("contents_longitude"),;	// コンテンツ経度

		private final String mapKey;
		private MapKey(final String mapKey) {
			this.mapKey = mapKey;
		}
		@Override
		public String toString() {
			return this.mapKey;
		}
	}

	/**
	 * コンテンツタイプ
	 */
	public static enum Contents {
		CONTENTS_TYPE_MOVE("move"), // 移動
		CONTENTS_TYPE_SHOP("shop"), // ショップ
		CONTENTS_TYPE_TOUR("tour"), // 観光
		CONTENTS_TYPE_STAY("stay"), // 宿泊
		CONTENTS_TYPE_EAT("eat"), // 食事
		CONTENTS_TYPE_WHATHER("weather"), ; // 天気
		private final String contents;
		private Contents(final String contents) {
			this.contents = contents;
		}
		@Override
		public String toString() {
			return this.contents;
		}
	}

	/**
	 * ノーティフィケーション
	 */
	public static enum Notification {
		NOTIFICATION_ID("notification_id"), // ID
		NOTIFICATION_UESR_ID("notification_user_id"), // ユーザーID
		NOTIFICATION_USER_NAME("notification_user_name"), // ユーザー名
		NOTIFICATION__GENDER("notification_gender"), // 性別
		NOTIFICATION__AGE("notification_age"), // 年齢
		NOTIFICATION_TEL("notification_tel"), // 電話番号
		NOTIFICATION_URL("notification_url"), // URL
		NOTIFICATION_LATITUDE("notification_latitude"),	// ユーザー緯度
		NOTIFICATION_LONGITUDE("notification_longitude"), ;	// ユーザー経度
		private final String notification;
		private Notification(final String notification) {
			this.notification = notification;
		}
		@Override
		public String toString() {
			return this.notification;
		}
	}
	
	/**
	 * メニュー
	 */
	public static int MENU_CHECK_USER_INFO = 0; // ユーザー情報確認
	public static int MENU_SERVER_START = 1; // 通信開始
	public static int MENU_SERVER_STOP = 2; // 通信停止
	public static int MENU_SETTING = 3; // 設定
	public static int MENU_USER_REGIST = 4; // ユーザー登録
	public static int MENU_GROUP_REGIST = 5; // グループ追加
	public static int MENU_GROUP_PART = 6; // グループ参加
	public static int MENU_GROUP_SEARCH = 7; // グループ検索
	public static int MENU_ROUTE_SEARCH = 8; // 経路探索
	public static int MENU_SWITCH_LANGUAGE = 9; // 経路探索

	public static enum Menu {
//		MENU_CHECK_USER_INFO_TEXT("ユーザー情報確認"), // ユーザー情報確認
//		MENU_SERVER_START_TEXT("通信開始"), // 通信開始
//		MENU_SERVER_STOP_TEXT("通信停止"), // 通信停止
//		MENU_SETTING_TEXT("設定"), // 設定
//		MENU_USER_REGIST_TEXT("ユーザー登録"), // ユーザー登録
//		MENU_GROUP_REGIST_TEXT("グループ追加"), // グループ追加
//		MENU_GROUP_PART_TEXT("グループ参加"), // グループ参加
//		MENU_GROUP_SEARCH_TEXT("グループ検索"), // グループ検索
//		MENU_ROUTE_SEARCH_TEXT("ルート探索"), // 経路検索
//		MENU_SWITCH_LANGUAGE_TEXT("言語切替"), ; // 言語切替
		
		
		MENU_CHECK_USER_INFO_TEXT("UserInformation"), // ユーザー情報確認
		MENU_SERVER_START_TEXT("CommunicationStart"), // 通信開始
		MENU_SERVER_STOP_TEXT("CommunicationStop "), // 通信停止
		MENU_SETTING_TEXT("Setting"), // 設定
		MENU_USER_REGIST_TEXT("UserRegistration"), // ユーザー登録
		MENU_GROUP_REGIST_TEXT("AddGroup"), // グループ追加
		MENU_GROUP_PART_TEXT("GroupParticipation"), // グループ参加
		MENU_GROUP_SEARCH_TEXT("GroupSearch"), // グループ検索
		MENU_ROUTE_SEARCH_TEXT("RouteSearch"), // 経路検索
		MENU_SWITCH_LANGUAGE_TEXT("LanguageSwitching"), ; // 言語切替
		private final String menu;
		private Menu(final String menu) {
			this.menu = menu;
		}
		@Override
		public String toString() {
			return this.menu;
		}
	}

	/**
	 * SharedPreferences
	 */
	public static enum SharedPreferences {
		SP_USER("SharedPreferences_User"), // SharedPreferencesKey:ユーザー
		SP_EDIT_ID("SPE_ID"), // SharedPreferencesEditKey:ID
		SP_EDIT_USER_ID("SPE_UserID"), // SharedPreferencesEditKey:ユーザーID
		SP_EDIT_USER_NAME("SPE_UserName"), // SharedPreferencesEditKey:ユーザー名
		SP_EDIT_USER_GENDER("SPE_UserGender"), // SharedPreferencesEditKey:ユーザー性別
		SP_EDIT_USER_AGE("SPE_UserAge"), // SharedPreferencesEditKey:ユーザー年齢
		SP_EDIT_USER_TEL("SPE_UserTel"), ; // SharedPreferencesEditKey:ユーザー電話番号
		private final String sharedPreferences;
		private SharedPreferences(final String sharedPreferences) {
			this.sharedPreferences = sharedPreferences;
		}
		@Override
		public String toString() {
			return this.sharedPreferences;
		}
	}

	/**
	 * 性別
	 */
	public static enum Gender {
		GENDER_MEN_TEXT("SPE_ID"), // 性別テキスト 男性
		GENDER_FEMALE_TEXT("SPE_UserID"), // 性別テキスト 女性
		GENDER_MEN_VALUE("SPE_UserName"), // 性別 男性
		GENDER_FEMALE_VALUE("SPE_UserTel"), ; // 性別 女性
		private final String gender;
		private Gender(final String gender) {
			this.gender = gender;
		}
		@Override
		public String toString() {
			return this.gender;
		}
	}
}
