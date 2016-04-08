package com.lab.jti.thai;

/**
 * �萔��`�C���^�t�F�[�X
 */
public interface Constant {

	/**
	 * API
	 */
	public static enum API {
		API_REGIST_USER("http://dev.airdocs.jp/his/adduser.php"), // ���[�U�[�o�^
		
		API_GET_GROUPS("http://dev.airdocs.jp/his/getgroups.php"), // �O���[�v���X�g�擾
		API_ADD2G("http://dev.airdocs.jp/his/addu2g.php"), // �O���[�v�Q��

		
		
		GENDER_FEMALE_VALUE("SPE_UserTel"), ; // ���� ����
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
		MAP_KEY_RESPONSE("response"), // ���X�|���X
		MAP_KEY_STATUS("status"), // �X�e�[�^�X
		MAP_KEY_CODE("code"), // �i���X�|���X�j�R�[�h
		MAP_KEY_MESSAGE("message"), // �i���X�|���X�j�R���e���c���b�Z�[�W
		MAP_KEY_RESULT("result"), // ���U���g
		MAP_KEY_GROUPS("groups"),	// �O���[�v�X
		MAP_KEY_USERS("users"),	// ���[�U�[�Y
		MAP_KEY_CONTENTS("contents"),	// �R���e���c
		MAP_KEY_ID("id"), // ID
		MAP_KEY_USER_ID("user_id"), // ���[�U�[ID
		MAP_KEY_USER_NAME("user_name"), // ���[�U�[��
		MAP_KEY_GENDER("gender"), // ����
		MAP_KEY_AGE("age"), // �N��
		MAP_KEY_TEL("tel"), // �d�b�ԍ�
		MAP_KEY_USER_LATITUDE("user_latitude"),	// ���[�U�[�ܓx
		MAP_KEY_USER_LONGITUDE("user_longitude"),	// ���[�U�[�o�x
		MAP_KEY_GROUP_ID("group_id"),	// �O���[�v���[�_�[ID
		MAP_KEY_GROUP_LEADER_ID("group_leader_id"),	// �O���[�v���[�_�[ID
		MAP_KEY_GROUP_NAME("group_name"), // �O���[�v��ID
		MAP_KEY_CONTENTS_ID("contents_id"),	// �R���e���cID
		MAP_KEY_CONTENTS_NAME("contents_name"),	// �R���e���c��
		MAP_KEY_CONTENTS_ADDRESS("contents_address"),	// �R���e���c�Z��
		MAP_KEY_CONTENTS_TYPE("contents_type"),	// �R���e���c�^�C�v
		MAP_KEY_CONTENTS_TEL("contents_tel"),	// �R���e���c�d�b�ԍ�
		MAP_KEY_CONTENTS_FAX("contents_fax"),	// �R���e���cFAX�ԍ�
		MAP_KEY_CONTENTS_MAIL("contents_mail"),	// �R���e���c���[���A�h���X
		MAP_KEY_CONTENTS_URL("contents_url"),	// �R���e���cURL
		MAP_KEY_CONTENTS_LATITUDE("contents_latitude"),	// �R���e���c�ܓx
		MAP_KEY_CONTENTS_LONGITUDE("contents_longitude"),;	// �R���e���c�o�x

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
	 * �R���e���c�^�C�v
	 */
	public static enum Contents {
		CONTENTS_TYPE_MOVE("move"), // �ړ�
		CONTENTS_TYPE_SHOP("shop"), // �V���b�v
		CONTENTS_TYPE_TOUR("tour"), // �ό�
		CONTENTS_TYPE_STAY("stay"), // �h��
		CONTENTS_TYPE_EAT("eat"), // �H��
		CONTENTS_TYPE_WHATHER("weather"), ; // �V�C
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
	 * �m�[�e�B�t�B�P�[�V����
	 */
	public static enum Notification {
		NOTIFICATION_ID("notification_id"), // ID
		NOTIFICATION_UESR_ID("notification_user_id"), // ���[�U�[ID
		NOTIFICATION_USER_NAME("notification_user_name"), // ���[�U�[��
		NOTIFICATION__GENDER("notification_gender"), // ����
		NOTIFICATION__AGE("notification_age"), // �N��
		NOTIFICATION_TEL("notification_tel"), // �d�b�ԍ�
		NOTIFICATION_URL("notification_url"), // URL
		NOTIFICATION_LATITUDE("notification_latitude"),	// ���[�U�[�ܓx
		NOTIFICATION_LONGITUDE("notification_longitude"), ;	// ���[�U�[�o�x
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
	 * ���j���[
	 */
	public static int MENU_CHECK_USER_INFO = 0; // ���[�U�[���m�F
	public static int MENU_SERVER_START = 1; // �ʐM�J�n
	public static int MENU_SERVER_STOP = 2; // �ʐM��~
	public static int MENU_SETTING = 3; // �ݒ�
	public static int MENU_USER_REGIST = 4; // ���[�U�[�o�^
	public static int MENU_GROUP_REGIST = 5; // �O���[�v�ǉ�
	public static int MENU_GROUP_PART = 6; // �O���[�v�Q��
	public static int MENU_GROUP_SEARCH = 7; // �O���[�v����
	public static int MENU_ROUTE_SEARCH = 8; // �o�H�T��
	public static int MENU_SWITCH_LANGUAGE = 9; // �o�H�T��

	public static enum Menu {
//		MENU_CHECK_USER_INFO_TEXT("���[�U�[���m�F"), // ���[�U�[���m�F
//		MENU_SERVER_START_TEXT("�ʐM�J�n"), // �ʐM�J�n
//		MENU_SERVER_STOP_TEXT("�ʐM��~"), // �ʐM��~
//		MENU_SETTING_TEXT("�ݒ�"), // �ݒ�
//		MENU_USER_REGIST_TEXT("���[�U�[�o�^"), // ���[�U�[�o�^
//		MENU_GROUP_REGIST_TEXT("�O���[�v�ǉ�"), // �O���[�v�ǉ�
//		MENU_GROUP_PART_TEXT("�O���[�v�Q��"), // �O���[�v�Q��
//		MENU_GROUP_SEARCH_TEXT("�O���[�v����"), // �O���[�v����
//		MENU_ROUTE_SEARCH_TEXT("���[�g�T��"), // �o�H����
//		MENU_SWITCH_LANGUAGE_TEXT("����ؑ�"), ; // ����ؑ�
		
		
		MENU_CHECK_USER_INFO_TEXT("UserInformation"), // ���[�U�[���m�F
		MENU_SERVER_START_TEXT("CommunicationStart"), // �ʐM�J�n
		MENU_SERVER_STOP_TEXT("CommunicationStop "), // �ʐM��~
		MENU_SETTING_TEXT("Setting"), // �ݒ�
		MENU_USER_REGIST_TEXT("UserRegistration"), // ���[�U�[�o�^
		MENU_GROUP_REGIST_TEXT("AddGroup"), // �O���[�v�ǉ�
		MENU_GROUP_PART_TEXT("GroupParticipation"), // �O���[�v�Q��
		MENU_GROUP_SEARCH_TEXT("GroupSearch"), // �O���[�v����
		MENU_ROUTE_SEARCH_TEXT("RouteSearch"), // �o�H����
		MENU_SWITCH_LANGUAGE_TEXT("LanguageSwitching"), ; // ����ؑ�
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
		SP_USER("SharedPreferences_User"), // SharedPreferencesKey:���[�U�[
		SP_EDIT_ID("SPE_ID"), // SharedPreferencesEditKey:ID
		SP_EDIT_USER_ID("SPE_UserID"), // SharedPreferencesEditKey:���[�U�[ID
		SP_EDIT_USER_NAME("SPE_UserName"), // SharedPreferencesEditKey:���[�U�[��
		SP_EDIT_USER_GENDER("SPE_UserGender"), // SharedPreferencesEditKey:���[�U�[����
		SP_EDIT_USER_AGE("SPE_UserAge"), // SharedPreferencesEditKey:���[�U�[�N��
		SP_EDIT_USER_TEL("SPE_UserTel"), ; // SharedPreferencesEditKey:���[�U�[�d�b�ԍ�
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
	 * ����
	 */
	public static enum Gender {
		GENDER_MEN_TEXT("SPE_ID"), // ���ʃe�L�X�g �j��
		GENDER_FEMALE_TEXT("SPE_UserID"), // ���ʃe�L�X�g ����
		GENDER_MEN_VALUE("SPE_UserName"), // ���� �j��
		GENDER_FEMALE_VALUE("SPE_UserTel"), ; // ���� ����
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
