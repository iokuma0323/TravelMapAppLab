package com.lab.jti.thai;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	static final int DB_VERSION = 1;
	public static final String DB_NAME = "smusic.db";

	public static class DB {
		public static class ACTION_HISTORY {
			public static final String TABLE_NAME = "ACTION_HISTORY";
			public static final String _ID = "_ID";
			public static final String ARTIST_ID = "ARTIST_ID";
			public static final String ARTIST_NAME = "ARTIST_NAME";
			public static final String ARTIST_PAGE_URL = "ARTIST_PAGE_URL";
			public static final String SONG_ID = "SONG_ID";
			public static final String IMAGE = "IMAGE";
			public static final String LIKE = "LIKE";
			public static final String LYRICS_URL = "LYRICS_URL";
			public static final String PACKAGE_ID = "PACKAGE_ID";
			public static final String PACKAGE_NAME = "PACKAGE_NAME";
			public static final String PACKAGE_PAGE_URL = "PACKAGE_PAGE_URL";
			public static final String TELOP = "TELOP";
			public static final String TITLE = "TITLE";
			public static final String TYPE = "TYPE";
		}
	}

	// ‚±‚êŽg‚í‚È‚¢‚©‚à
	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);

	}

	public DBHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.beginTransaction();
		try {
			StringBuilder sb2 = new StringBuilder();

			sb2.append("CREATE TABLE ").append(DB.ACTION_HISTORY.TABLE_NAME);
			sb2.append("(");
			sb2.append(DB.ACTION_HISTORY._ID).append(" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,");
			sb2.append(DB.ACTION_HISTORY.ARTIST_ID).append(" TEXT,");
			sb2.append(DB.ACTION_HISTORY.ARTIST_NAME).append(" TEXT,");
			sb2.append(DB.ACTION_HISTORY.ARTIST_PAGE_URL).append(" TEXT,");
			sb2.append(DB.ACTION_HISTORY.SONG_ID).append(" TEXT,");
			sb2.append(DB.ACTION_HISTORY.IMAGE).append(" TEXT,");
			sb2.append(DB.ACTION_HISTORY.LIKE).append(" TEXT,");
			sb2.append(DB.ACTION_HISTORY.LYRICS_URL).append(" TEXT,");
			sb2.append(DB.ACTION_HISTORY.PACKAGE_ID).append(" TEXT ,");
			sb2.append(DB.ACTION_HISTORY.PACKAGE_NAME).append(" TEXT,");
			sb2.append(DB.ACTION_HISTORY.PACKAGE_PAGE_URL).append(" TEXT,");
			sb2.append(DB.ACTION_HISTORY.TELOP).append(" TEXT,");
			sb2.append(DB.ACTION_HISTORY.TITLE).append(" TEXT, ");
			sb2.append(DB.ACTION_HISTORY.TYPE).append(" TEXT ");
			sb2.append(");");
			db.execSQL(sb2.toString());
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}
